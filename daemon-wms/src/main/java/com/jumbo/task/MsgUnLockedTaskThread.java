package com.jumbo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;

public class MsgUnLockedTaskThread extends Thread {


    protected static final Logger log = LoggerFactory.getLogger(MsgUnLockedTaskThread.class);


    private TransOlManager transOlManager;

    private Long id;

    private WareHouseManagerExecute wareHouseManagerExecute;

    public MsgUnLockedTaskThread(Long id) {
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        // ClassPathXmlApplicationContext webContext = new
        // ClassPathXmlApplicationContext("spring.xml");
        transOlManager = (TransOlManager) webContext.getBean("transOlManager");
        wareHouseManagerExecute = (WareHouseManagerExecute) webContext.getBean("wareHouseManagerExecute");
        this.id = id;
    }


    @Override
    public void run() {
        try {
            transOlManager.msgUnLocked(id);
        } catch (BusinessException e) {
            // 记录获取运单失败的单据
            wareHouseManagerExecute.failedToGetTransno(id);
            wareHouseManagerExecute.msgUnLockedError(id);
        } catch (Exception e) {
            log.error("msgUnLocked", e);
        }
    }
}
