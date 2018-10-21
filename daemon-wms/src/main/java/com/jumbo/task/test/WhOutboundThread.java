package com.jumbo.task.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.manager.warehouse.WareHouseManager;

/**
 * 
 * 出库线程
 * 
 * @author sjk
 *
 */
public class WhOutboundThread extends Thread {
    protected static final Logger log = LoggerFactory.getLogger(WhOutboundThread.class);

    private Long staId;
    private WareHouseManager wareHouseManager;

    public WhOutboundThread(Long staId) {
        this.staId = staId;
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        wareHouseManager = (WareHouseManager) webContext.getBean("wareHouseManager");
    }

    @Override
    public void run() {
        // 通知PAC出库
        try {
            log.info("outbound start,id:{}", staId);
            wareHouseManager.testAutoSalesStaOutBound(staId);
        } catch (Exception e) {
            log.info("outbound error,id:{}", staId, e);
        }
        log.info("id:{}", staId);
    }
}
