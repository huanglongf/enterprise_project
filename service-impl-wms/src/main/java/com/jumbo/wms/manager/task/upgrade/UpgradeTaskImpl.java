package com.jumbo.wms.manager.task.upgrade;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.daemon.UpgradeTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.inv.TaskEbsManager;


public class UpgradeTaskImpl extends BaseManagerImpl implements UpgradeTask {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6461099290503032701L;
	@Autowired
	private TaskEbsManager taskEbsManager;
	@Override
	public void upgradeEmail() {
		taskEbsManager.upgradeEmail();
		
	}

}
