package com.jumbo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.manager.task.TaskOmsOutManager;
import com.jumbo.wms.model.warehouse.InboundAgvToHub;

public class AgvInBoundToHubThread extends Thread {

    private static final Logger log = LoggerFactory.getLogger(AgvInBoundToHubThread.class);

    private InboundAgvToHub inboundAgvToHub;

    private TaskOmsOutManager taskOmsOutManager;



    public AgvInBoundToHubThread(InboundAgvToHub inboundAgvToHub) {
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        // ClassPathXmlApplicationContext webContext = new
        // ClassPathXmlApplicationContext("spring.xml");
        taskOmsOutManager = (TaskOmsOutManager) webContext.getBean("taskOmsOutManager");
        this.inboundAgvToHub = inboundAgvToHub;
    }



    @Override
    public void run() {
        try {
            taskOmsOutManager.AgvInBoundToHub(inboundAgvToHub);
        } catch (Exception e) {
            log.error("AgvSkuToHubThread" + inboundAgvToHub.getId() + e);
        }
    }
}
