package com.jumbo.wms.manager.task.ytoorder;

import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.wms.daemon.YtoOrderTask;
import com.jumbo.wms.manager.BaseManagerImpl;

/**
 * 圆通接口
 * @author sj
 *
 */
public class YtoOrderTaskImpl extends BaseManagerImpl  implements YtoOrderTask{
    /**
     * 
     */
    private static final long serialVersionUID = -2614682036554069115L;
    @Autowired
    private YtoOrderTaskManager ytoOrderTaskManager;
    
    /**
     * 所有仓库调用圆通接口
     */
	@SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
	public void ytoInterfaceAllWarehouse() {
		ytoOrderTaskManager.ytoInterfaceAllWarehouse();
	}
	
	/**
     * 特定仓库调用圆通接口
     * @param whId
     */
	public void ytoInterfaceByWarehouse(Long whId) {
		ytoOrderTaskManager.ytoIntefaceByWarehouse(whId);
	}
}
