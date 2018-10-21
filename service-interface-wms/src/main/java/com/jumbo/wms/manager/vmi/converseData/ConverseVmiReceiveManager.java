package com.jumbo.wms.manager.vmi.converseData;


import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.vmi.itData.VMIReceiveInfoType;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StockTransApplication;


public interface ConverseVmiReceiveManager extends BaseManager {
    void generateVMIReceiveInfoBySta(StockTransApplication sta);

    void generateVMIReceiveFile(String fileDir, String date, VMIReceiveInfoType type);

    void generateVMIAdjustmentFile(String fileDir, String date);

    void generateInvStatusChangeFile(String fileDir, String date);

    void generateVMIReceiveInfoByInvCk(InventoryCheck inv);

    /**
     * 根据样品出入库生成调整数据
     */
    public void generateVMIReceiveInfoBySample(StockTransApplication sta);

    void exportInventorySnapShot(String vmiCode, String dir);

    void generateInvStatusChange(StockTransApplication sta);

    void generateInvStatusChangeByInboundSta(StockTransApplication sta);

    void receiveProductPriceByMq(String message);
    
    void generateVMIReceiveInfoBySlipCode1(StockTransApplication sta);
    
    void generateToshopForPos();
}
