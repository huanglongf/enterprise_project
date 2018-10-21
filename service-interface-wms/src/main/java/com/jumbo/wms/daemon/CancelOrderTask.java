package com.jumbo.wms.daemon;

/**
 * 取消七天之前未完成的作业单
 * 
 * @author jinlong.ke
 * 
 */
public interface CancelOrderTask {

	void cancelOrderAndReleaseInventory();
}
