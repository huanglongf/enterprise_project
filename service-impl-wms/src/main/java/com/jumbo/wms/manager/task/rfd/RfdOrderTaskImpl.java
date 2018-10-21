package com.jumbo.wms.manager.task.rfd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.warehouse.RfdConfirmOrderQueueDao;
import com.jumbo.wms.daemon.RfdOrderTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.warehouse.RfdConfirmOrderQueue;

public class RfdOrderTaskImpl extends BaseManagerImpl implements RfdOrderTask {

	private static final long serialVersionUID = 5684170691897546755L;
	
	@Autowired
	private RfdOrderTaskManager rfdOrderTaskManager;
	@Autowired
	private RfdConfirmOrderQueueDao rfdConfirmOrderQueueDao;
	/**
     * 所有仓库调用如风达接口
     */
	@Override
	@SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
	public void rfdInterfaceAllWarehouse() {
		rfdOrderTaskManager.rfdInterfaceAllWarehouse();
	}

	@Override
	@SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
	public void exeRfdConfirmOrderQueue() {
		Boolean flag = true;
		while (flag) {
			List<RfdConfirmOrderQueue> list = rfdConfirmOrderQueueDao.findExtOrderIdSeo(5L, new BeanPropertyRowMapper<RfdConfirmOrderQueue>(RfdConfirmOrderQueue.class));
			if (list.size() < 30) {
				flag = false;
			}
			try {
				rfdOrderTaskManager.exeRfdConfirmOrder(list);
			} catch (Exception e) {
                rfdConfirmOrderQueueDao.updateExeCountByError(list);
				log.error("confirm rfd queue fail : " + e);
			}
		}
		
	}

}
