package com.jumbo.wms.newInventoryOccupy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.manager.pickZone.NewInventoryOccupyManager;


public class SkuOcpThread implements Runnable {
    protected static final Logger log = LoggerFactory.getLogger(SkuOcpThread.class);
    private NewInventoryOccupyManager newInventoryOccupyManager;

    private Long woolId;

    public SkuOcpThread(Long woolId, NewInventoryOccupyManager newInventoryOccupyManager) {
        this.woolId = woolId;
        this.newInventoryOccupyManager = newInventoryOccupyManager;
    }



    public void run() {
        try {
            // SKU占用库存
            newInventoryOccupyManager.ocpInvByWoolId(woolId);
        } catch (Exception e) {
            newInventoryOccupyManager.updateWoolConformQtyById(woolId, 0L);
            log.error(woolId + " skuOcp start..........", e);
        }
    }

}
