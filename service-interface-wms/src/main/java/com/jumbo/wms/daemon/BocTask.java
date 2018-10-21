package com.jumbo.wms.daemon;

import java.util.List;

import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;

/**
 * Boc Task
 * 
 * @author wudan
 * 
 */
public interface BocTask {

	/**
	 * 15分钟
	 */
	public void bocfollieOrderTask();

	/**
	 * 15分钟
	 */
	public void bocfollieReturnTask();

	/**
	 * 每天晚上2.30
	 */
	public void updateBocPriceTask();

	/**
	 * 取消
	 */
	public void sendBocCancelOrder();

	public void executeMsgRtnOutbound();

	public void executeRtnInboundOrderTask();

	public void executeMsgRtnOutbound(List<MsgRtnOutbound> msgoutList);
	
	/**
	 * AF库存比对定时任务
	 */
	public void afInvCompareMethod();
}
