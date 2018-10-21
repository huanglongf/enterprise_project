/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.jumbo.webservice.ids.manager;



import java.io.File;
import java.io.IOException;
import java.util.List;

import com.jumbo.webservice.ids.command.OrderListConfirm;
import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.vmi.ids.BaozunOrderRequest;
import com.jumbo.wms.model.vmi.ids.IdsInventorySynchronous;
import com.jumbo.wms.model.vmi.ids.IdsServerInformation;
import com.jumbo.wms.model.vmi.ids.OrderConfirm;
import com.jumbo.wms.model.vmi.ids.WmsOrder;
import com.jumbo.wms.model.vmi.ids.WmsPreOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnAdjustment;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnReturn;
import com.jumbo.wms.model.warehouse.AdvanceOrderAddInfo;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.WarehouseSourceSkuType;

public interface IdsManager extends BaseManager {
    public String iDSPullSo();

    /**
     * 反馈给IDS收货信息
     */
    void feedbackIDSInfo();

    /**
     * 接收收货确认的反馈信息
     */
    void receiveRECFeedback(File file) throws IOException;

    /**
     * 接收发货确认的反馈信息
     */
    void receiveSHPFeedback(File file) throws IOException;

    /**
     * 接收库存调整的反馈信息
     */
    void receiveADJFeedback(File file) throws IOException;

    /**
     * 将收货确认(REC)文件数据表里面的数据写入到中间表
     */
    void synchronizationREC();

    /**
     * 收货确认(SHP)文件数据表里面的数据写入到中间表
     */
    void synchronizationSHP();

    /**
     * 将库存调整(ADJ)文件数据表里面的数据写入到中间表
     */
    void synchronizationADJ();

    /**
     * 根据IDS反馈退出出库文件数据执行退仓出库
     * 
     * @param msgRtnReturn
     */
    void executeVmiReturnOutBound(MsgRtnReturn msgRtnReturn);

    /**
     * 根据IDS反馈退出出库文件数据执行退仓出库错误
     * 
     * @param msgRtnReturn
     */
    void executeError(MsgRtnReturn msgRtnReturn);

    /**
     * 创建库存调整
     * 
     * @param msgRtnADJ
     */
    InventoryCheck createVmiAdjustment(MsgRtnAdjustment msgRtnADJ);

    /**
     * 执行盘点
     * 
     * @param ic
     */
    void executionVmiAdjustment(InventoryCheck ic);

    /**
     * 跟新库存调整指令信息状态
     * 
     * @param msgRtnADJ
     * @param status
     */
    void updateADJStatus(MsgRtnAdjustment msgRtnADJ, DefaultStatus status);

    void iDSCfOrder(OrderListConfirm comfirm, String filename);

    /**
     * 获取销售出库单据
     * 
     * @param resultXml
     * @return
     */
    List<MsgRtnOutbound> findoutboundOrder(String resultXml) throws Exception;

    /**
     * 执行销售出库
     * 
     * @param msgList
     * @return
     */
    void outOrderToSale(List<MsgRtnOutbound> msgoutList, String filename);

    String vimIdsnoticeCancelOrderBound();

    /**
     * 确认取消
     * 
     * @param resultXml
     * @return
     */
    void vimExecuteCreateCancelOrder(String resultXml);

    /**
     * IDS出库通知
     * 
     * @return
     * @throws Exception
     */
    String findinboundReturnRequest() throws Exception;


    /**
     * 退货入库通知
     * 
     * @param resultXml
     * @return
     * @throws Exception
     */
    List<MsgRtnInboundOrder> transactionRtnInbound(String resultXml) throws Exception;

    void orderCancelRespone(String resultXml);

    String orderCancelResponse(String source, IdsServerInformation idsServerInformation);

    /**
     * 单个订单取消请求
     * 
     * @param idsServerInformation
     * @return
     */
    String siginOrderCancelResponse(MsgOutboundOrderCancel order, IdsServerInformation idsServerInformation);

    /**
     * 退货单取消
     * 
     * @param source
     * @param idsServerInformation
     * @return
     */
    String cancelReturnResponseLF(IdsServerInformation idsServerInformation, Long msgLog);

    OrderConfirm returnArrivalRequest(String resultXml, String source);

    String returnNotifyReques(String IDS, IdsServerInformation idsServerInformation);

    OrderConfirm orderDeliveryRequest(String resultXml, String source);

    OrderConfirm orderConfirmResponse(String resultXml);

    String inboundIdsNike(String IDS, IdsServerInformation idsServerInformation);



    void updateAfreshSendOrder(String batchID, String source);

    void idsInvSynInfoSave(String requestXML);

    OrderConfirm vmiInventory(String requestXML, String source);

    void receiveAEOSkuMasterByMq(String message);

    StockTransApplication vmiInventorySynchronous(IdsInventorySynchronous idsInvSyn);


    InventoryCheck vmiInvAdjustByWh(IdsInventorySynchronous idsInvSyn);

    WmsOrder createOrderRequestStr(long batchNo, List<MsgOutboundOrder> orderList, IdsServerInformation idsServerInformation, WarehouseSourceSkuType skuType);

    BaozunOrderRequest createAeoOrderRequestStr(long batchNo, List<MsgOutboundOrder> orderList, WarehouseSourceSkuType skuType, IdsServerInformation idsServerInformation);

    /**
     * 
     */
    public int updateFixNumberData();


    public WmsPreOrder createPreOrderRequestStr(Long batchNo, List<AdvanceOrderAddInfo> ad, IdsServerInformation idsServerInformation, String source);

}
