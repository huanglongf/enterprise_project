package com.jumbo.wms.daemon;

public interface InvTask {

	/**
	 * EBS库存同步
	 */
	public void ebsInventory();

	/**
	 * 每日可销售库存同步
	 */
	public void salesInventory();

	/**
	 * 实时增量库存同步
	 */
	public void salesInventoryOms();

	/**
	 * 全量库存补充文件
	 */
	public void replenishForSalesInventory();

	/**
	 * 每30分钟汇总一次过仓失败的单数，发送邮件通知OMS
	 */
	public void emailToOmsForIncrementInvFailure();

	/**
	 * 每30分钟汇总一次增量库存错误批次，发送邮件通知OMS
	 */
	public void salesInventoryOmsEmail();
}
