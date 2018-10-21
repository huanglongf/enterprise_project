package com.jumbo.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.webservice.ids.manager.IdsManagerProxy;
import com.jumbo.wms.model.vmi.ids.IdsServerInformation;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;

public class IdsThread2 extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(IdsThread2.class);
    @Autowired
    private IdsManagerProxy idsManagerProxy;

    /*
     * 批次号
     */
    private String batchNo;


    public IdsThread2() {
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        idsManagerProxy = (IdsManagerProxy) context.getBean("idsManagerProxy");
    }

    public IdsThread2( String batchNo) {
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        idsManagerProxy = (IdsManagerProxy) context.getBean("idsManagerProxy");
        this.batchNo = batchNo;
    }

    public void run() {//
        try {
            idsManagerProxy.createRtnOrderBatchCode( batchNo);
        } catch (Exception e) {
            logger.error("createRtnOrderBatchCode1",e);
        }
    }


}
