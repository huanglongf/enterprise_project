package com.jumbo.wms.daemon;

public interface CKTask {

	/**
	 * 退货入库通知、出库通知
	 */
	public void inOutBoundNotify();

	/**
	 * 执行出入库
	 */
	public void executeOutAndInbound();

	public void downloadAndImput();

	public void downloadInvDataAndImput();

	// 初始化库存
	public void synchronizeInventory();

	public void generateCKSku();

	/**
	 * 初始化库存
	 */
	public void generateInventoryCheck();
}
