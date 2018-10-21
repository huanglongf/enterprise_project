package com.jumbo.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.event.TransactionalEvent;
import com.jumbo.wms.manager.listener.StvListenerManager;
import com.jumbo.wms.model.warehouse.StockTransVoucher;

/**
 * 
 * @author jinlong.ke
 * 
 */
public class StvListener implements TransactionalEventListener<TransactionalEvent> {
    private static final Logger logger = LoggerFactory.getLogger(StvListener.class);
    @Autowired
    private StvListenerManager stvListenerManager;

    @Override
    public void onEvent(TransactionalEvent event) {
        logger.debug(event.getSource() + "'s has been changed!");
        if (event.getSource() instanceof StockTransVoucher) {
            StockTransVoucher stv = (StockTransVoucher) event.getSource();
            switch (stv.getStatus()) {
                case FINISHED:// 完成
                    stvListenerManager.stvFinished(stv);
                    break;
                default:
                    break;
            }
        }
    }
}
