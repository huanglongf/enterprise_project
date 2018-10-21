package com.jumbo.wms.manager.vmi.warehouse;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

public class VmiWarehouseSpdzNBA extends AbstractVmiWarehouse {

    private static final long serialVersionUID = 9016854813596966265L;
    protected static final Logger log = LoggerFactory.getLogger(VmiWarehouseSpdzNBA.class);

    public void inboundNotice(MsgInboundOrder msgInorder) {}

    public void inboundReturnRequestAnsToWms(MsgInboundOrder msg) {

    }

    public boolean cancelSalesOutboundSta(String staCode, MsgOutboundOrderCancel msg) {
        log.info("NBA商品定制外包仓不可直接取消！staCode：" + staCode);
        getLocalhostIP();
        return false;

    }

    @Override
    public WarehouseLocation findLocByInvStatus(InventoryStatus invStatus) {
        return null;
    }


    @Override
    public boolean cancelReturnRequest(Long msgLog) {
        log.info("NBA商品定制外包仓退货入库取消通知！msgLog：" + msgLog);
        getLocalhostIP();
        return true;
    }

    public String getLocalhostIP() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            log.info("当前主机ip：" + addr.getHostAddress());
            return addr.getHostAddress();
        } catch (Exception e) {
            log.error("获取当前主机ip异常！", e);
        }
        return null;
    }
}
