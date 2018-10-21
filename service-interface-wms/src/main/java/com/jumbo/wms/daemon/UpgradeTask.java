package com.jumbo.wms.daemon;

public interface UpgradeTask {
	/**
	 * 定时发送未升单单据
	 */
	public void upgradeEmail();

}
