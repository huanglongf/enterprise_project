package com.jumbo.wms.manager.mq;

import java.util.Date;
import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.mq.MqMdPriceLog;
import com.jumbo.wms.model.mq.MqSkuPriceLog;
import com.jumbo.wms.model.vmi.cch.CchStockReturnInfo;
import com.jumbo.wms.model.vmi.cch.CchStockTransConfirm;
import com.jumbo.wms.model.vmi.order.VMIOrderCommand;
import com.jumbo.wms.model.vmi.philipsData.PhilipsCustomerReturnReceipt;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsIssue;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsMovement;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsReceipt;
import com.jumbo.wms.model.vmi.philipsData.PhilipsStockComparison;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCommand;
import com.jumbo.mq.FlowInvoicePrintMsg;

public interface MqManager extends BaseManager {

    void sendMqSkuPrice(List<MqSkuPriceLog> tmp, String queueName, Long shopId);

    void sendGDVMqOrder(VMIOrderCommand ord, String mqName, Long shopId,String vmiCode);

    void createFullInventoryLog(Long ouid);

    void sendMqMdPrice(List<MqMdPriceLog> logs, String queueName, Long shopId);

    void sendMqCCHAsnReceive(List<CchStockTransConfirm> headers, String mqName, Long shopId);

    void sendMqCCHSale(Long shopId, String shopCode, Date date, String mqName);

    void sendMqPhilipsGoodsIssue(PhilipsGoodsIssue pGoodsIssue, String mqName, Long shopId);

    void sendMqPhilipsGoodsStockComparison(PhilipsStockComparison pStockComparison, String mqName, Long shopId);

    void sendMqPhilipsGoodsMovement(PhilipsGoodsMovement pGoodsMovement, String mqName, Long shopId);

    void sendMqPhilipsGoodsReceiptReturn(PhilipsCustomerReturnReceipt pCustomerReturnReceipt, String mqName, Long shopId);

    void sendMqPhilipsGoodsReceipt(PhilipsGoodsReceipt philipsGoodsReceipt, String mqName, Long shopId);

    void sendMqGDVSalesOrder(MsgOutboundOrderCommand order, String mqName);

    /**
     * 构造MQ发票回传消息日志信息
     * 
     * @param printMsg
     */
    void constructMqInvoicePrintMsgLog(FlowInvoicePrintMsg printMsg);
    
    //按批次退仓
	void sendMqCCHRTVList(List<CchStockReturnInfo> cchStockReturnInfo, String mqName, Long shopId, Long staId);

}
