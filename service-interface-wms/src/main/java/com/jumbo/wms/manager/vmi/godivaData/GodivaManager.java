package com.jumbo.wms.manager.vmi.godivaData;

import java.util.List;
import java.util.Map;

import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.boc.VmiInventorySnapshotData;
import com.jumbo.wms.model.boc.VmiInventorySnapshotDataCommand;
import com.jumbo.wms.model.vmi.godivaData.GodivaInventoryAdjustment;
import com.jumbo.wms.model.vmi.warehouse.CompanyShopShare;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StockTransApplication;

public interface GodivaManager {

    /**
     * 获取出库单据
     * 
     * @param message
     */
    MsgRtnOutbound receiveGodivaOutboundByMq(String message);

    /**
     * 移动入库
     * 
     * @param message
     */
    List<MsgRtnInboundOrder> receiveGodivaOutInventoryMovement(String message);

    void createStaByInboundOrder(MsgRtnInboundOrder msgRtnorder);

    /**
     * vmi 库存调整
     * 
     * @param message
     */
    Map<String, List<GodivaInventoryAdjustment>> receiveGodivaInventoryAdjustment(String message);

    InventoryCheck creteInventoryChecks(GodivaInventoryAdjustment godival);

    void executionVmiAdjustment(InventoryCheck ic);

    // void updateInOrderSkuIdByBarCode(Long rtnOrderId)throws Exception;
    void executeGdvMovement(List<MsgRtnInboundOrder> msgRtnorder);

    void createStaFroGdvRtn(MsgRtnInboundOrder rtnOrder);

    void executeGdvInventoryAdjustment(Map<String, List<GodivaInventoryAdjustment>> adjustMap) throws Exception;

    void executeSaveCarrier();

//    void executeSaveLineSku();

    StockTransApplication createOutSalesOrder(Long rtnOutOrderId);

    /**
     * 库存全量覆盖
     * 
     * @param message
     */
    List<VmiInventorySnapshotData> receiveGodivaInventory(String message);
    
    List<InventoryCheck> gdvCreateInventoryCheck(String fileName,String vimSource,List<CompanyShopShare> sharesList );
    List<InventoryCheck> executeInventoryChecks(List<VmiInventorySnapshotDataCommand> datas, List<CompanyShopShare> sharesList, Warehouse wh);
}
