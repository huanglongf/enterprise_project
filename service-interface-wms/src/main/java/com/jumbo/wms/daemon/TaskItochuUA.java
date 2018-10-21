package com.jumbo.wms.daemon;


public interface TaskItochuUA {

	/**
	 * 读文件数据 （UA伊藤忠库存数据）
	 */
	public void uaReadItochuRtnInvToDB();
	
	/**
	 * 退货入库通知、出库通知
	 */
	public void uaInOutBoundNotify();
	
	/**
	 * 出库反馈，入库反馈，退货入库反馈，退仓
	 */
	public void uaInOutBoundRtn();
	
	/**
	 * 库存盘点
	 */
	public void uaSaveCheckInventory();
	
	/**
	 * UA库存
	 */
	public void insertUaInventory();
	
}
