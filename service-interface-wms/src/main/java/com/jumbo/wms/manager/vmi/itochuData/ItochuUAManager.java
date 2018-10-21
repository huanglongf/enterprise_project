package com.jumbo.wms.manager.vmi.itochuData;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnReturn;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/*
 * import com.jumbo.manager.BaseManager; import com.jumbo.model.vmi.warehouse.MsgRtnInboundOrder;
 * import com.jumbo.model.vmi.warehouse.MsgRtnReturn; import
 * com.jumbo.model.warehouse.StockTransApplication;
 */

public interface ItochuUAManager extends BaseManager {

    // UA 出库通知
    void uaOutBoundNotify(String localFileDir);

    // UA 读出库反馈
    void readUAOutBoundRtnData(String localFileDir, String bakFileDir);

    // UA 退货入库通知
    void uaRtnInBoundNotify(String localFileDir);


    void insertConverseInventoryLog();

    // UA 读退货入库反馈数据
    void readUARtnInBoundData(String localFileDir, String bakFileDir);

    // UA 采购入库
    void readUAInBoundRtn(String localFileDir, String bakFileDir);

    // UA 采购入库执行
    void createStaForInBoundRtnExecute(StockTransApplication sta, MsgRtnInboundOrder msg);

    // UA 读退仓数据
    void readUAReturnData(String localFileDir, String bakFileDir);

    // UA 退仓执行
    void uaReturnExecute(MsgRtnReturn rtn);

    void insertIDSVSInventoryLog();

    /**
     * 退货入库取消
     * 
     * @param localFileDir
     */
    void rtnCancelNotify(String localFileDir);

    void saveCheckInventory(String localFileDir, String bakFileDir);

    void readItochuRtnInvToDB(String localFileDir, String bakFileDir);

    /**
     * 从mq读取ua库存
     * 
     * @param message
     */
    void receiveWhUaInventoryByMq(String message);

    /**
     * 插入数据到日志表
     * 
     */
    void insertUaInventoryLog();

    /**
     * 插入数据到日志表
     * 
     */
    void insertAfInventoryLog();

    /**
     * 插入数据到日志表
     * 
     */
    void insertNikeNewInventoryLog();//WH_NIKE_WH_SF
    
    void insertNikeNewInventoryLog2();//WH_OOCL


    void insertNikeCrwInventoryLog();

    /**
     * 插入数据到日志表
     * 
     */
    void insertAeoInventoryLog();

    void insertAeoJDInventoryLog();

    /**
     * 插入数据到日志表
     * 
     */
    void insertNewLookInventoryLog();

    /**
     * 插入数据到日志表
     * 
     */
    void insertUaNbaInventoryLog();

    /**
     * 插入数据到日志表
     */
    void insertNikeInventoryLog();

    /**
     * 插入数据到日志表
     */
    void insertNikeInventoryLogGZ();

    /**
     * 插入数据到日志表TM
     */
    void insertNikeInventoryLogTM();

    /**
     * 插入数据到日志表TM
     */
    void insertNikeInventoryLogGZTM();

    /**
     * 插入数据到日志表
     */
    void insertNewLookJDInventoryLog();
}
