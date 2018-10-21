package com.jumbo.wms.manager.vmi;


import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;

public class VmiGuess extends VmiBaseBrand {

    /**
     * 
     */
    private static final long serialVersionUID = 2856724262888432897L;

    @Override
    public SkuCommand findSkuCommond(String skuCode) {
        return null;
    }

    @Override
    public void generateRtnWh(StockTransApplication sta) {

    }

    @Override
    public void generateReceivingWhenInbound(StockTransApplication sta, StockTransVoucher stv) {}

    @Override
    public void generateReceivingWhenShelv(StockTransApplication sta, StockTransVoucher stv) {
        // DO NOTHING
    }

    @Override
    public String generateRtnStaSlipCode(String vmiCode, StockTransApplicationType type) {
        return null;
    }

    @Override
    public void generateReceivingWhenFinished(StockTransApplication sta) {}

    /**
     * Vmi调整单据反馈
     * 
     * @param inv
     */
    public void generateVMIReceiveInfoByInvCk(InventoryCheck inv) {
        // converReceiveManager.generateVMIReceiveInfoByInvCk(inv);

    }

    @Override
    public void generateReceivingTransfer(StockTransApplication sta) {
        log.debug("guess transfer do nothing");
    }
}
