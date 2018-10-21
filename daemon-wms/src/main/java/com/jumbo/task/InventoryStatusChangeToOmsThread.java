package com.jumbo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.manager.task.TaskOmsOutManager;

public class InventoryStatusChangeToOmsThread extends Thread {

    private static final Logger log = LoggerFactory.getLogger(InventoryStatusChangeToOmsThread.class);

    private Long staId;

    private Long stvId;

    private Long id;

    private TaskOmsOutManager taskOmsOutManager;

    public InventoryStatusChangeToOmsThread(Long staId, Long stvId, Long id) {
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        // ClassPathXmlApplicationContext webContext = new
        // ClassPathXmlApplicationContext("spring.xml");
        taskOmsOutManager = (TaskOmsOutManager) webContext.getBean("taskOmsOutManager");
        this.staId = staId;
        this.stvId = stvId;
        this.id = id;
    }



    @Override
    public void run() {
        try {
            taskOmsOutManager.invChangeLogNoticePac(staId, stvId, id);
        } catch (Exception e) {
            log.error("InventoryStatusChangeToOmsThread" + staId + e);

        }
    }
}
