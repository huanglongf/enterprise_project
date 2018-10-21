package com.jumbo.wms.daemon;


public interface CCHTask {

	public void generateInbountSta();

	/**
	 * 收货确认
	 */
	public void mqSendASNReceive();

	/**
	 * 退大仓反馈
	 */
	public void mqSendStockReturn();

	public void mqSendCCHSales();

}
