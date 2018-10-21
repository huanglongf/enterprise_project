package com.jumbo.wms.manager.warehouse.vmi;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * VMI作业单创建
 * 
 * @author sjk
 * 
 */
public interface VmiStaCreateManager extends BaseManager {

    /**
     * VMI 品牌根据slipcode 创建入库作业单
     * 
     * @param vmiCode
     * @param slipCode
     */
    void generateVmiInboundStaBySlipCode(String vmiCode, String slipCode);

    /**
     * VMI 品牌根据slipcode 创建入库作业单(通用)
     */
    void generateVmiInboundStaBySlipCodeDefault(String vmiCode, String vmiSource, String slipCode, String asnType);
    
    /**
     * VMI 品牌根据slipcode 创建入库作业单(通用)
     */
    void generateVmiInboundStaBySlipCodeDefault(String vmiCode, String vmiSource, String slipCode, BiChannelCommand shopCmd);

    /**
     * vmi入库后自动转店
     * 
     * @param sta
     */
    public void autoTransferByVmiInbound(StockTransApplication sta);

}
