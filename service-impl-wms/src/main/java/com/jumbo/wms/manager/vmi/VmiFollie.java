package com.jumbo.wms.manager.vmi;

import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;

public class VmiFollie extends VmiBaseBrand {
    private static final long serialVersionUID = 8021152419455741602L;


    @Override
    public SkuCommand findSkuCommond(String skuCode) {
        // return skuDao.findSKUCommandFromCoachData(skuCode, new
        // BeanPropertyRowMapperExt<SkuCommand>(SkuCommand.class));
        return null;
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
    public void generateReceivingWhenFinished(StockTransApplication sta) {

    }

    /**
     * Vmi调整单据反馈
     * 
     * @param inv
     */
    public void generateVMIReceiveInfoByInvCk(InventoryCheck inv) {
    // converReceiveManager.generateVMIReceiveInfoByInvCk(inv);

    }
}
