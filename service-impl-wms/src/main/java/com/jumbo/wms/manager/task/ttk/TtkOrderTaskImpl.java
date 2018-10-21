/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.jumbo.wms.manager.task.ttk;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.TtkConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.util.StringUtil;
import com.jumbo.webservice.ttk.TransNoSegmentsResponse;
import com.jumbo.webservice.ttk.TtkOrderClient;
import com.jumbo.wms.daemon.TtkOrderTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.warehouse.TtkConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.WhTransProvideNo;

/**
 * @author lichuan
 * 
 */
public class TtkOrderTaskImpl extends BaseManagerImpl implements TtkOrderTask {
    private static final long serialVersionUID = -7262407303853556146L;
    @Autowired
    private WhTransProvideNoDao transProvideNoDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private TtkOrderTaskManager ttkOrderTaskManager;
    @Autowired
    private TtkConfirmOrderQueueDao ttkConfirmOrderQueueDao;
    @Autowired
    private TransOlManager transOlManager;

    /**
     * 天天快递运单号段提取
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void ttkTransNo() {
        log.info("====>TTK,get ttk trans no trigger begin");
        Long count = transProvideNoDao.getTranNoNumberByLpCode(Transportator.TTKDEX, null, new SingleColumnRowMapper<Long>(Long.class));
        if (count < 5000) {
            log.info("====>TTK,get ttk trans no trigger begin, there still have trans no amount is:[{}]", count);
            try {
                Integer getTotal = 20000;
                Integer getCount = 100;
                for (int k = 100; k <= getTotal; k += 100) {
                    TransNoSegmentsResponse resp = TtkOrderClient.getTransNoSegments(getCount, TTK_SITE, TTK_CUS, TTK_PASSWORD, TTK_GET_URL);// 获取getCount个运单号
                    if (null != resp) {
                        String tnSegements = resp.getData();
                        if ((TtkOrderClient.STATUS_SUCCESS).equals(resp.getResultcode()) && !StringUtil.isEmpty(tnSegements)) {
                            List<String> allSegements = new ArrayList<String>();
                            if (tnSegements.contains(",")) {
                                String[] segements = {};
                                segements = tnSegements.split(",");
                                for (String s : segements) {
                                    allSegements.add(s);
                                }
                            } else {
                                allSegements.add(tnSegements);
                            }
                            for (String seg : allSegements) {
                                String[] tns = seg.split("-");
                                if (2 == tns.length) {
                                    BigInteger from = new BigInteger(tns[0]);
                                    BigInteger to = new BigInteger(tns[1]);
                                    for (BigInteger i = from; i.compareTo(to) != 1; i = i.add(new BigInteger("1"))) {
                                        try {// 运单号已存在则跳过
                                            WhTransProvideNo whTransProvideNo = new WhTransProvideNo();
                                            whTransProvideNo.setLpcode(Transportator.TTKDEX);
                                            whTransProvideNo.setTransno(i.toString());
                                            transProvideNoDao.save(whTransProvideNo);
                                        } catch (Exception e) {}
                                    }
                                } else {
                                    log.debug("TTK,get ttk trans no response param [data] exception,error trans segement is:[{}]", seg);
                                    // throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("====>TTK,get ttk trans no throw exception:", e);
            }
        }
        log.info("====>TTK,get ttk trans no trigger end");
    }

    /**
     * 设置天天快递运单号
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void setTransNoByWarehouse() {
        Long count = transProvideNoDao.getTranNoNumberByLpCode(Transportator.TTKDEX, null, new SingleColumnRowMapper<Long>(Long.class));
        if (count > 0) {
            List<Long> idList = warehouseDao.getAllTTKWarehouse(new SingleColumnRowMapper<Long>(Long.class));
            for (Long id : idList) {
                try {
                    Warehouse wh = warehouseDao.getTtkWarehouseByOuId(id, new BeanPropertyRowMapper<Warehouse>(Warehouse.class));
                    if (wh == null) {
                        throw new BusinessException(ErrorCode.WAREHOUSE_NOT_FOUND);
                    }
                    Boolean flag = true;
                    while (flag) {
                        List<String> lpList = new ArrayList<String>();
                        lpList.add(Transportator.TTKDEX);
                        List<Long> staList = staDao.findStaByOuIdAndStatus(id, lpList, new SingleColumnRowMapper<Long>(Long.class));
                        if (staList.size() < 100) {
                            flag = false;
                        }
                        for (Long staId : staList) {
                            try {
                                // 匹配TTK单据号及设置大头笔信息
                                transOlManager.matchingTransNo(staId);
                                // 设置大头笔
                                // ttkOrderTaskManager.setTransBigWord(staId);
                                // ttkOrderTaskManager.setTransNoAndTransBigWord(staId);
                            } catch (Exception e) {
                                log.error("====>TTK,matching ttk trans no or set trans big word throw exception, staId is:[{}]", staId);
                                log.error("====>TTK,matching ttk trans no or set trans big word throw exception:", e);
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("====>TTK,matching ttk trans no or set trans big word throw exception:", e);
                }
            }
        }
    }

    /**
     * 订单信息回传，出库通知
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void exeTtkConfirmOrderQueue() {
        List<TtkConfirmOrderQueue> qs = ttkConfirmOrderQueueDao.findExtOrder(5L);
        for (TtkConfirmOrderQueue q : qs) {
            try {
                ttkOrderTaskManager.exeTtkConfirmOrder(q);
            } catch (Exception e) {
                log.error("====>TTK,comfirm order throw exception:", e);
            }
        }
    }

}
