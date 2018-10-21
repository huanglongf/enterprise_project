package com.jumbo.wms.manager.task;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnAdjustment;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.vmi.warehouse.MsgSKUSync;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.webservice.sfwarehouse.command.WmsVendorResponse;

/**
 * 
 * @author jinlong.ke
 * 
 */
public interface TaskSfManager extends BaseManager {
    /**
     * 推送单个商品到顺丰
     * 
     * @param s
     * @throws Exception
     */
    void sendSingleSkuToSf(MsgSKUSync s) throws Exception;

    /**
     * 批量推送商品到顺丰
     * 
     * @param skus
     * @throws Exception
     */
    void sendBatchSkuToSf(List<MsgSKUSync> skus, String sfFlag) throws Exception;

    /**
     * 推送供应商到顺丰
     * 
     * @param supplier
     * @throws Exception
     */
    WmsVendorResponse sendVendorToSf() throws Exception;

    /**
     * 采购订单接口
     * 
     * @param inOrder
     * @throws Exception
     */
    void sendInboundOrderToSf(MsgInboundOrder inOrder) throws Exception;

    /**
     * 取消订单接口
     * 
     * @param code
     * @throws Exception
     */
    void cancelOrderNoticeSf(String code) throws Exception;

    /**
     * 
     * 销售出库接口
     * 
     * @param outOrder
     * @throws Exception
     */
    void sendOutboundOrderToSf(MsgOutboundOrder outOrder) throws Exception;

    /**
     * 入库单明细推送接口 数据保存
     * 
     * @param requestXml
     * @return
     */
    void wmsPurchaseOrderPushInfo(String requestXml);

    /**
     * 出库单明细推送接口 数据保存
     * 
     * @param requestXml
     */
    void wmsSailOrderPushInfo(String requestXml);

    /**
     * 库存调整推送接口 数据保存
     * 
     * @param requestXml
     */
    void wmsInventoryAdjustPushInfo(String requestXml);

    void sendInboundOrderToSf1(MsgInboundOrder inOrder) throws Exception;

    void sendOutboundOrderToSf1(MsgOutboundOrder outOrder) throws Exception;

    InventoryCheck vmiAdjustMentRtn(MsgRtnAdjustment msgRtnADJ) throws Exception;

    /**
     * SF补充出库，sn号 重量
     * @param m
     */
    void exeReplenishOutbound(MsgRtnOutbound m);
    
    String getSfCheckWork();
}
