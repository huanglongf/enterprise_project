package com.jumbo.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.event.TransactionalEvent;
import com.jumbo.wms.model.warehouse.InventoryCheck;

public class InventoryCheckListener implements TransactionalEventListener<TransactionalEvent> {
    private static final Logger logger = LoggerFactory.getLogger(StaListener.class);

    @Override
    public void onEvent(TransactionalEvent event) {
        logger.debug(event.getSource() + "'s has been changed!");
        if (event.getSource() instanceof InventoryCheck) {
            InventoryCheck ic = (InventoryCheck) event.getSource();
            switch (ic.getStatus()) {
                case FINISHED:
                    // inventoryCheckListenerManager.inventoryCheckFinished(ic);
                    break;
                default:
                    break;
            }
        }

    }
}
