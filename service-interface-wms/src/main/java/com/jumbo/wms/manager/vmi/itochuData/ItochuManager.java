package com.jumbo.wms.manager.vmi.itochuData;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.vmi.warehouse.ItochuMsgInboundOrder;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.TransactionType;

public interface ItochuManager extends BaseManager {
    void saveCheckInventory(String localFileDir, String bakFileDir);

    void outBoundNotify(String localFileDir);

    void rtnInBoundNotify(String localFileDir);

    /**
     * 退换货取消通知
     * 
     * @param localFileDir
     */
    void rtnCancelNotify(String localFileDir);

    void readFileIntoDBInOutBoundRtn(String localFileDir, String bakFileDir, String condition, String fileStart, String fileEnd);

    void createInBoundSta(List<ItochuMsgInboundOrder> boxs);

    boolean returnToEtam(String localFileDir, String bakFileDir);

    /**
     * 创建退仓作业单
     */
    void createStaFroEtamRtn(Long id);

    // void occupyInventory();

    void readItochuRtnInvToDB(String localFileDir, String bakFileDir);

    StockTransVoucher occupyInventoryByStaId(Long id, TransactionType transactionType, OperationUnit ou);
    
}
