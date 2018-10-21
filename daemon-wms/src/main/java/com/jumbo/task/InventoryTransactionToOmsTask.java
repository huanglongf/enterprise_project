package com.jumbo.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.task.inventoryTransactionToOms.InventoryTransactionToOmsTaskManager;
import com.jumbo.wms.manager.task.inventoryTransactionToOms.WmsInvChangeToOmsManager;
import com.jumbo.wms.model.warehouse.InventoryTransactionToOms;
import com.jumbo.wms.model.warehouse.WmsInvChangeToOms;

public class InventoryTransactionToOmsTask {
    private static final Logger log = LoggerFactory.getLogger(InventoryTransactionToOmsTask.class);
    @Autowired
    private InventoryTransactionToOmsTaskManager itoManager;
    @Autowired
    private WmsInvChangeToOmsManager wmsInvChangeToOmsManager;

    public void inventoryTransationToOmsTask() {
        if (log.isDebugEnabled()) {
            log.debug("inventoryTransationToOmsTask begin......");
        }
        List<InventoryTransactionToOms> ilist = itoManager.getNeedExcuteData();
        for (InventoryTransactionToOms ito : ilist) {
            try {
                itoManager.noticePacsData(ito.getId(), ito.getStvId());
            } catch (Exception e) {
                log.error("", e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("inventoryTransationToOmsTask end......");
        }
    }

    public void sendEmailForITTO() {
        if (log.isDebugEnabled()) {
            log.debug("sendEmailForITTO begin......");
        }
        List<InventoryTransactionToOms> ilist = itoManager.getNeedSendData();
        if (ilist.size() > 0) {
            itoManager.sendEmailForITTO(ilist);
        }
        if (log.isDebugEnabled()) {
            log.debug("sendEmailForITTO end......");
        }
    }

    /**
     * 库存变更通知PACS/原库存共享逻辑
     */
    public void inventoryChangeToOms() {
        log.error("inventoryChangeToOms begin....");
        List<WmsInvChangeToOms> wiol = wmsInvChangeToOmsManager.getAllNeedData();
        log.error("inventoryChangeToOms list size...." + wiol.size());
        for (WmsInvChangeToOms wio : wiol) {
            wmsInvChangeToOmsManager.noticeOmsThisInvChange(wio.getId());
        }
        log.error("inventoryChangeToOms end....");
    }
}
