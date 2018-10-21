package com.jumbo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.manager.task.TaskOmsOutManager;

public class OdoInboundCreateStaThread extends Thread {


    protected static final Logger logger = LoggerFactory.getLogger(OdoInboundCreateStaThread.class);

    private TaskOmsOutManager taskOmsOutManager;

    private Long id;



    public OdoInboundCreateStaThread(Long id) {
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        taskOmsOutManager = (TaskOmsOutManager) webContext.getBean("taskOmsOutManager");
        this.id = id;
    }



    @Override
    public void run() {
        try {
            taskOmsOutManager.createStaByOdOId(id);
        } catch (Exception e) {
            logger.error("OdoInboundCreateStaThread" + id);
        }

    }

}
