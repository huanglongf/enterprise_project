package com.jumbo.wms.manager.task.stoorder;

import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.wms.daemon.StoOrderTask;
import com.jumbo.wms.manager.BaseManagerImpl;

/**
 * 申通接口
 * 
 * @author jinlong.ke
 * 
 */
public class StoOrderTaskImpl extends BaseManagerImpl implements StoOrderTask {
	/**
     * 
     */
	private static final long serialVersionUID = -2614682036554069115L;
	@Autowired
	private StoOrderTaskManager stoOrderTaskManager;

	/**
	 * 特定仓库调用STO接口
	 * 
	 * @param whId
	 */
	public void stoInterfaceByWarehouse(Long whId) {
		stoOrderTaskManager.stoIntefaceByWarehouse(whId);
	}

	/**
	 * 所有仓库调用STO接口
	 */
	@SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
	public void stoInterfaceAllWarehouse() {
		stoOrderTaskManager.stoInterfaceAllWarehouse();
	}
}
