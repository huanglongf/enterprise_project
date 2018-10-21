package com.jumbo.wms.daemon;

public interface TaskBiaogan {

	/**
	 * 发送发票信息
	 */
	public void sendInvoices();

	/**
	 * 发送订单信息
	 */
	public void sendSalesOutOrder();

	/**
	 * VML 销售出库通知，推送发票
	 */
	public void vmiSaleOutbound();

	/**
	 * 销售出库反馈
	 */
	public void vmiSaleOutboundRtn();

	/**
	 * SKU同步
	 */
	public void singleSkuToWms();

	/**
	 * 处理流程为判断当日出库作业单是否都同步到，发现未同步的订单直接通过查询接口反馈信息同步出库信息
	 */
	public void orderDetailQueryTodayTask();

	/**
	 * 入库反馈
	 */
	public void inOutBoundRtn();

	/**
	 * 入库通知
	 */
	public void taskAnsToWms();

	/**
	 * 退换货入库取消通知
	 */
	public void cancelReturnRequest();

	/**
	 * 通知标杆其他出库
	 */
	public void taskOutBoundReturn();
}
