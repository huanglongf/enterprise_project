package com.jumbo.wms.manager.task.cancelorder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.daemon.CancelOrderTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.hub2wms.HubWmsService;

/**
 * 取消七天之前未完成的作业单
 * 
 * @author jinlong.ke
 * 
 */
public class CancelOrderTaskImpl extends BaseManagerImpl implements CancelOrderTask {
    /**
     * 
     */
    private static final long serialVersionUID = -8617029998943262481L;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private CancelOrderTaskManager cancelOrderTaskManager;
    @Autowired
    private HubWmsService hubWmsService;

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void cancelOrderAndReleaseInventory() {
        List<Long> idList = staDao.findNeedCancelOrderIdList(new SingleColumnRowMapper<Long>(Long.class));
        for (Long id : idList) {
            try {
                log.debug("CancelOrderAndReleaseInventory begin.....Sta Id:" + id);
                cancelOrderTaskManager.cancelOrderById(id);
                // 取消数据写入推送im中间表
                hubWmsService.insertOccupiedAndRelease(id);
            } catch (Exception e) {
                log.error("CancelOrderAndReleaseInventory Exception......Sta Id:" + id);
                log.error("", e);
            }
        }
    }
}
