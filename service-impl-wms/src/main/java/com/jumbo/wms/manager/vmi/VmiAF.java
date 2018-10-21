package com.jumbo.wms.manager.vmi;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;

public class VmiAF extends VmiBaseBrand {

    /**
     * 
     */
    private static final long serialVersionUID = 2856724262888432897L;
    @Autowired
    private SkuDao skuDao;

    @Override
    public SkuCommand findSkuCommond(String skuCode) {
        return skuDao.findSKUCommandFromCoachData(skuCode, new BeanPropertyRowMapperExt<SkuCommand>(SkuCommand.class));
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
}
