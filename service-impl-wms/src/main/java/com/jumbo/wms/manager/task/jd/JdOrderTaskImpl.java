package com.jumbo.wms.manager.task.jd;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.wms.daemon.JdOrderTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;

public class JdOrderTaskImpl extends BaseManagerImpl implements JdOrderTask{
    /**
	 * 
	 */
    private static final long serialVersionUID = 2781749352292243948L;

    @Autowired
    private JdOrderManager jdOrderManager;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private WhTransProvideNoDao transProvideNoDao;
    @Autowired
    private TransOlManager transOlManager;


    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void toHubGetJdTransNo() {
        log.debug("获取运单号开始");
        jdOrderManager.jdTransOnlineNo();

    }

    // 设置JD单据号
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void jdInterfaceByWarehouse() {
        log.debug("设置运单号开始");
        Long count = transProvideNoDao.getTranNoNumberByLpCode(Transportator.JD, null, new SingleColumnRowMapper<Long>(Long.class));
        if (count > 0) {
            List<String> idList = biChannelDao.getAllJDbIcHannel(new SingleColumnRowMapper<String>(String.class));
            for (String owner : idList) {
                try {
                    Boolean flag = true;
                    while (flag) {
                        List<String> lpList = new ArrayList<String>();
                        lpList.add("JD");
                        lpList.add("JDCOD");
                        List<Long> staList = staDao.findStaByOwnerAndStatus(owner, lpList, StockTransApplicationStatus.CREATED.getValue(), new SingleColumnRowMapper<Long>(Long.class));
                        if (staList.size() < 100) {
                            flag = false;
                        }
                        for (Long staId : staList) {
                            // JD下单
                            try {
                                // 设置JD单据号
                                transOlManager.matchingTransNo(staId);
                            } catch (Exception e) {
                                log.error("", e);
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
        // jdOrderManager.jdInterfaceByWarehouse();
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void jdReceiveOrder() {
        log.debug("同步运单号开始");
        jdOrderManager.createJdOlTransOrder();
    }


}
