package com.jumbo.wms.daemon;
/**
 * 如风达任务
 */
public interface RfdOrderTask {
	
	/**
	 * 所有仓库调用圆通接口
	 */
	void rfdInterfaceAllWarehouse();
	
	/**
	 * 确认核对Rfd订单
	 */
	void exeRfdConfirmOrderQueue();
}
