package com.jumbo.wms.manager.boc;

import java.util.List;

import com.jumbo.wms.model.boc.VmiInventoryMovementData;
import com.jumbo.wms.model.boc.VmiInventorySnapshotData;
import com.jumbo.wms.model.boc.VmiInventorySnapshotDataCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.InventoryCheck;


public interface MasterDataManager {

    /**
     * 商品主档信息接收 mqSendSalesOrder
     * 
     * @param message
     */
    void receiveMasterDateByMq(String message);

    /**
     * 根据仓库查询order
     * 
     * @param source
     */
    void mqSendSalesOrder(String source);
    
    
    /**
	 * 接收 InventoryMovement mq执行出入库
	 * 
	 *  @param message
	 */
	public void receiveInventoryMovement(String message);
    

    /**
     * 接收 Inventory mq 消息
     * 
     * @param message
     * @return
     */
    List<VmiInventorySnapshotData> receiveInventorySnapshot(String message);



    /**
     * 接收出出库信息反馈
     * 
     * @param message
     * @return
     */
    List<MsgRtnOutbound> receiveMsgRtnOutBoundData(String message);

    void executeMsgRtnOutbound(List<MsgRtnOutbound> msgoutList);

    /**
     * 获取退货入库
     * 
     * @param source
     */
    void receiveMsgInboundOrder(String source);

    void receivePriceData(String message);

    /**
     * 获取退货入库
     * 
     * @param message
     * @return
     */
    List<MsgRtnInboundOrder> findMsgRtnOutbounds(String message);

    InventoryCheck executeInventoryChecks(List<VmiInventorySnapshotDataCommand> datas);
    
    InventoryCheck executeInventoryCheck(List<VmiInventoryMovementData> datas);

    void executionVmiAdjustment(InventoryCheck ic);
    
    void executionVmiAdjMovement(InventoryCheck ic);

    /**
     * 发送取消订单
     * 
     * @param source
     */
    void bocExecuteCreateCancelOrder(String source);

    void receiveOrderCancelConfirmation(String message);


    void updateBocPrice();

}
