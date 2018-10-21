package com.jumbo.wms.manager.task;

import java.util.Date;
import java.util.List;

import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnAdjustment;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.WarehouseMsgSku;

public interface ThreePLManager {

    /**
     * 物流宝商品同步
     */
    public void syncWmsSkuToHab(WarehouseMsgSku msg);


    void executeIDSInvAdjustADJ(Long id);

    /**
     * 外包仓商品同步失败邮件通知
     * 
     * @param optionKey
     * @param code
     */
    public void msgThreePLEmailNotice(String optionKey, String codeKey, List<WarehouseMsgSku> msgSkus, List<MsgInboundOrder> msgInbound, List<MsgOutboundOrder> msgOutbound, List<MsgRtnInboundOrder> msgRtnInbound, List<MsgRtnOutbound> msgRtnOutbound,
            List<MsgOutboundOrderCancel> msgOutCancel, String source);


    /**
     * 外包仓入库信息同步hab 物流宝
     * 
     * @param orderList
     */
    public void sendWlbInboundToHab(MsgInboundOrder orderList);


    /**
     * 物流宝新入库查询
     */
    public void queryWlbNewInbound(StockTransApplicationCommand wlbOrderList);


    /**
     * 外包仓出库信息同步hab 物流宝
     * 
     * @param orderList
     */
    public void sendWlbOutboundToHab(MsgOutboundOrder order);

    /**
     * 
     * 物流宝出库信息查询
     * 
     * @param orderList
     */
    public void queryWlbOutbound(StockTransApplicationCommand sta);

    /**
     * 入库差异查询
     * 
     * @param beginDate
     * @param endDate
     */
    // public void queryInboundNotice(Date beginDate, Date endDate);

    /**
     * 单据取消结果查询
     * 
     * @param beginDate
     * @param endDate
     */
    public void queryOrderCancel(Date beginDate, Date endDate, Long channel);

    /**
     * 物流宝单据取消推送
     * 
     * @param skuList
     */
    public void wlbOutboundCancelToHab(List<MsgOutboundOrderCancel> skuList);

    /**
     * 发送wx出库通知
     */
    public void wxOutountMq(String staCode, String mailno);

    /**
     * hub 反馈数据
     * 
     * @param msg
     * @throws Exception
     */
    void wxShipmentsReturnInfoMsg(String msg) throws Exception;

    /**
     * 创建库存调整作业单
     * 
     * @param msgRtnADJ
     */
    StockTransApplication createAdjustmentSta(MsgRtnAdjustment msgRtnADJ);

    /**
     * 执行库存调整作业单
     * 
     * @param msgRtnADJ
     */
    void executeAdjustmentSta(Long staId);

    /**
     * 库存数量调整
     * 
     * @param msgRtnADJ
     * @return
     */
    void createThreePLAdjustment(MsgRtnAdjustment msgRtnADJ);


    /**
     * 外包仓 3.1 商品接口 -- 宝尊WMS通知第三方仓储实际仓库作业中的商品信息，当商品信息发生变更也使用此接口推送信息
     * 
     * @param requst
     * @return
     */
    String wmsSku(String customer, String requst);

    /**
     * 外包仓 3.2 入库单接口-- 入库单接口提供给第三方仓储系统入库单数据，以便第三方仓储根据入库单据数据处理后续的收货流程。
     * 
     * @param request
     * @return
     */
    String wmsInboundOrder(String customer, String requst);

    /**
     * 外包仓 3.3 入库单收货反馈接口-- 第三方仓库实际接收完成后将收货数据反馈给宝尊WMS系统。
     * 
     * @param request
     * @return
     */
    String uploadWmsInboundOrder(String customer, String request);

    /**
     * 外包仓 3.4 宝尊WMS通知第三方仓储出库单接口。 非销售出、换货出
     * 
     * @param customer
     * @param request
     * @return
     */
    String wmsOutBoundOrder(String customer, String request);

    /**
     * 外包仓 3.5 第三方仓库实际接出库完成后将出库数据反馈给宝尊WMS系统。 非销售出、换货出
     * 
     * @param customer
     * @param request
     * @return
     */
    String uploadWmsOutboundOrder(String customer, String request);

    /**
     * 3.6. 销售出库单接口 宝尊WMS通知第三方仓储销售出库单接口。
     * 
     * @param customer
     * @param sign
     * @param request
     * @return
     */
    String wmsSalesOrder(String customer, String request);

    /**
     * 3.7. 销售出库单反馈接口 第三方仓库实际接出库完成后将出库数据反馈给宝尊WMS系统。 宝尊WMS同1出库单只允许反馈1次出库信息。
     * 
     * @param customer
     * @param request
     * @return
     */
    String uploadWmsSalesOrder(String customer, String request);

    /**
     * 3.8.单据取消接口 宝尊WMS通知第三方仓储单据取消。
     * 
     * @param customer
     * @param request
     * @return
     */
    String wmsOrderCancel(String customer, String request);

    /**
     * 3.9.单据取消反馈接口 第三方仓储单反馈宝尊订单取消结果。
     * 
     * @param customer
     * @param request
     * @return
     */
    String uploadWmsOrderCancel(String customer, String request);

    /**
     * 3.10.销售退货接口 入库单接口提供给第三方仓储系统入库单数据，以便第三方仓储根据入库单据数据处理后续的收货流程。
     * 
     * @param customer
     * @param request
     * @return
     */
    String wmsSalesReturnOrder(String customer, String request);

    /**
     * 3.11.销售退货反馈接口 第三方仓库实际接收完成后将收货数据反馈给宝尊WMS系统。 销售退货只允许按计划量执行反馈，可以调整库存状态。
     * 
     * @param customer
     * @param request
     * @return
     */
    String uploadWmsSalesReturnOrder(String customer, String request);

    /**
     * 3.12.库存状态修改反馈接口 第三方仓库将库内商品状态调整推送给宝尊WMS
     * 
     * @param customer
     * @param request
     * @return
     */
    String uploadWmsInvStatusChange(String customer, String request);

    /**
     * 3.13.库存数量调整反馈接口 第三方仓库将库内商品数量调整推送给宝尊WMS。
     * 
     * @param customer
     * @param sign
     * @param request
     * @return
     */
    String uploadWmsInvChange(String customer, String sign, String request);
}
