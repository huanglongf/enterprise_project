package com.jumbo.wms.manager.task.sforder;

import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.wms.daemon.SfOrderTask;
import com.jumbo.wms.manager.BaseManagerImpl;

/**
 * 顺丰接口
 * @author jinlong.ke
 *
 */
public class SfOrderTaskImpl extends BaseManagerImpl  implements SfOrderTask{
    /**
     * 
     */
    private static final long serialVersionUID = -2614682036554069115L;
    @Autowired
    private SfOrderTaskManager sfOrderTaskManager;
    /**
     * 所有仓库调用顺丰接口
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sfInterfaceAllWarehouse(){
        sfOrderTaskManager.sfInterfaceAllWarehouse();
    }
    /**
     * 特定仓库调用顺丰接口
     * @param whId
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sfInterfaceByWarehouse(Long whId){
        sfOrderTaskManager.sfIntefaceByWarehouse(whId);
    }
}
