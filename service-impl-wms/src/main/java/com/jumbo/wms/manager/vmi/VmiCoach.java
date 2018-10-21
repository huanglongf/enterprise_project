package com.jumbo.wms.manager.vmi;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;

public class VmiCoach extends VmiBaseBrand {
    private static final long serialVersionUID = 8021152419455741602L;
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
    public void generateReceivingWhenFinished(StockTransApplication sta) {

    }
    
    /**
     * VMI转店反馈数据
     * 
     * @param sta
     */
    @Override
    public void generateReceivingTransfer(StockTransApplication sta) {
        log.info("coach transfer do nothing");
    }
}
