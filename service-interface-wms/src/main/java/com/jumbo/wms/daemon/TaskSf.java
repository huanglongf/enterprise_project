package com.jumbo.wms.daemon;

/***
 * 
 * @author jinlong.ke
 * 
 */
public interface TaskSf {

	/**
	 * 单个推送商品到顺丰
	 */
	public void sendSingleSkuToSf();

	/**
	 * 入库推送定时任务入口
	 */
	public void sendInboundOrderToSf();

	/**
	 * 入库推送定时任务入口 加字段
	 */
	public void sendInboundOrderToSf1();

	/**
	 * 出库推送定时任务入口
	 */
	public void sendOutboundOrderToSf();

	/**
	 * 出库推送定时任务入口 加字段
	 */
	public void sendOutboundOrderToSf1();

	/**
	 * 批量推送商品到顺丰
	 */
	public void sendBatchSkuToSf();

	/**
	 * SF出库反馈定时任务入口
	 */
	public void vmiSfOutboundRtn();

	/**
	 * SF入库反馈定时任务入口
	 */
	public void inOutBoundRtn();

	/**
	 * SF库存调整定时任务
	 */
	public void vmiAdjustMentRtn();

	/**
	 * 补充出库
	 */
	public void replenishOutbound();
}
