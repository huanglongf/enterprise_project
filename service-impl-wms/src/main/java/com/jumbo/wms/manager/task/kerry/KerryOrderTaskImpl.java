package com.jumbo.wms.manager.task.kerry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.warehouse.KerryConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.daemon.KerryOrderTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.warehouse.KerryConfirmOrderQueue;

public class KerryOrderTaskImpl extends BaseManagerImpl implements KerryOrderTask {

    private static final long serialVersionUID = -4443186641169898945L;

    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private KerryConfirmOrderQueueDao kerryConfirmOrderQueueDao;
    @Autowired
    private KerryOrderTaskManager kerryOrderTaskManager;
    
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void exeKerryConfirmOrderQueue() {
        List<KerryConfirmOrderQueue> findExtOrder = kerryConfirmOrderQueueDao.findKerryOrderByType(1, 5L);
        for (KerryConfirmOrderQueue q : findExtOrder) {
            try {
                kerryOrderTaskManager.exeKerryConfirmOrder(q);
            } catch (Exception e) {
            	q.setExeCount(q.getExeCount() + 1);
                kerryConfirmOrderQueueDao.save(q);
                log.error("kerry confirm order error:", e);
            }
        }
    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void exeKerryCancelOrderQueue() {
        // 1.查出前一天作业单类型是21,25,42的  状态取消了的  物流快递是KERRY的作业单  
        List<Long> idList = staDao.getCancelStaByLpcode("KERRY_A", new SingleColumnRowMapper<Long>(Long.class));
        kerryOrderTaskManager.saveKerryCancelOrder(idList);
        // 2.给这些查出来的数据进行反馈给物流商(取消快递,释放快递单号)删除消息表  并记录到日志表中
        List<KerryConfirmOrderQueue> qlist = kerryConfirmOrderQueueDao.findKerryOrderByType(2, 5L);
        for (KerryConfirmOrderQueue q : qlist) {
            try {
                kerryOrderTaskManager.exeKerryCancelOrder(q);
            } catch (Exception e) {
            	q.setExeCount(q.getExeCount() + 1);
                kerryConfirmOrderQueueDao.save(q);
                log.error("kerry cancel order error:", e);
            }
        }
    }

}
