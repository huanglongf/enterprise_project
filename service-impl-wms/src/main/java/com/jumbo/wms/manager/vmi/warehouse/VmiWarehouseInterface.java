package com.jumbo.wms.manager.vmi.warehouse;

import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.WarehouseLocation;


public interface VmiWarehouseInterface {
    String getSourceCode();

    /**
     * 取消销售出库作业单
     */
    boolean cancelSalesOutboundSta(String staCode, MsgOutboundOrderCancel msg);

    /**
     * 入库通知
     */
    void inboundNotice(MsgInboundOrder msgInorder);

    /**
     * 退货通知
     * 
     * @param sta
     * @param wh
     */
    void inboundReturnRequestAnsToWms(MsgInboundOrder msg);

    /**
     * 退货入库取消通知
     * 
     * @param sta
     * @param wh
     */
    boolean cancelReturnRequest(Long msgLog);


    WarehouseLocation findLocByInvStatus(InventoryStatus invStatus);

}
