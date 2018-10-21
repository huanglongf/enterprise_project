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
package com.jumbo.wms.manager.warehouse;

import java.util.Date;
import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.vmi.warehouse.RtnSnDetailDao;
import com.jumbo.dao.warehouse.SkuSnDao;
import com.jumbo.dao.warehouse.SkuSnLogDao;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.SkuSn;
import com.jumbo.wms.model.warehouse.SkuSnCommand;
import com.jumbo.wms.model.warehouse.SkuSnLog;
import com.jumbo.wms.model.warehouse.SkuSnStatus;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.TransactionDirection;

/**
 * @author lichuan
 *
 */
@Transactional
@Service("skuSnManager")
public class SkuSnManagerImpl implements SkuSnManager {

    private static final long serialVersionUID = 4285964739788869090L;
    @Autowired
    private SkuSnDao skuSnDao;
    @Autowired
    private SkuSnLogDao skuSnLogDao;
    @Autowired
    private RtnSnDetailDao rtnSnDetailDao;

    /** 
     *
     */
    @Override
    public List<SkuSnCommand> getSkuSnBySku(String snnumber, Long whId) {
        return skuSnDao.getSkuSnBySku(snnumber, whId, new BeanPropertyRowMapperExt<SkuSnCommand>(SkuSnCommand.class));
    }

    /**
     * 添加SkuSn和SkuSnLog到数据表中，并更新T_WH_MSG_RTN_SN_DETAIL表is_send的状态
     */
    @Override
    public void saveSkuSnAndLog(SkuSnCommand ssc) {
        // 创建SkuSn
        SkuSn skuSn = new SkuSn();
        skuSn.setOu(new OperationUnit(ssc.getOuId()));
        skuSn.setSku(new Sku(ssc.getSkuid()));
        skuSn.setStv(new StockTransVoucher(ssc.getStvId()));
        skuSn.setStaId(ssc.getStaId());
        skuSn.setStatus(SkuSnStatus.USING);
        skuSn.setVersion(0);
        skuSn.setLastModifyTime(new Date());
        skuSn.setSn(ssc.getSn());
        SkuSn skuSn2 = skuSnDao.findSkuSnBySnSingle(ssc.getSn());
        if (skuSn2 == null) {
            skuSnDao.save(skuSn);// 保存到T_WH_SKU_SN
            saveSkuSnLog(ssc, TransactionDirection.INBOUND);
        }
        // 修改T_WH_MSG_RTN_SN_DETAIL表is_send的状态
        rtnSnDetailDao.updateIsSendOkByRtnId(ssc.getRtnSnId());
    }

    @Override
    public void deleteSkuSnAndLog(SkuSnCommand ssc) {
        SkuSn skuSn2 = skuSnDao.findSkuSnBySnSingle(ssc.getSn());
        if (skuSn2 != null) {
            skuSnDao.deleteSkuSnBySn(ssc.getSn());
            saveSkuSnLog(ssc, TransactionDirection.OUTBOUND);
        }
        // 修改T_WH_MSG_RTN_SN_DETAIL表is_send的状态
        rtnSnDetailDao.updateIsSendOkByRtnId(ssc.getRtnSnId());
    }

    private void saveSkuSnLog(SkuSnCommand ssc, TransactionDirection direction) {
        // 创建日志对象
        SkuSnLog skuSnLog = new SkuSnLog();
        skuSnLog.setStvId(ssc.getStvId());
        skuSnLog.setSn(ssc.getSn());
        skuSnLog.setOuId(ssc.getOuId());
        skuSnLog.setSkuId(ssc.getSkuid());
        skuSnLog.setDirection(direction);
        skuSnLog.setTransactionTime(new Date());
        skuSnLogDao.save(skuSnLog);// 保存到T_WH_SKU_SN_LOG
    }

}
