package com.jumbo.wms.manager.warehouse;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.QueueSta;
import com.jumbo.wms.model.warehouse.QueueStaDeliveryInfo;
import com.jumbo.wms.model.warehouse.QueueStaInvoice;
import com.jumbo.wms.model.warehouse.QueueStaLine;
import com.jumbo.wms.model.warehouse.QueueStaLineCommand;



public interface CreateStaManager extends BaseManager{
	/**
	 * 过仓
	 * 
	 * @param batchCode
	 */
	public boolean createStaForSales(QueueSta sta,QueueStaDeliveryInfo deliveryInfo,List<QueueStaLine> queueStaLines,List<QueueStaInvoice> invoices);
	/**
	 * A&F过仓
	 * 
	 * @param batchCode
	 */
	public boolean createStaForSalesAF(QueueSta sta,QueueStaDeliveryInfo deliveryInfo,List<QueueStaLineCommand> commands ,List<QueueStaInvoice> invoices,List<QueueStaLine> staLine);



	
	/**
	 * 更新过仓队失败次数
	 * 
	 * @param soCode
	 */
	public void updateQueueErrorCount(String soCode);
	
	/**
	 * 获取过仓失败次数
	 * 
	 * @param soCode
	 */
	public int getQueueErrorCount(String soCode);
	/**
	 * VMI转店
	 * @param queueSta
	 * @return
	 */
	public String createOwnerTransForSta(QueueSta queueSta);
	
}
