package com.jumbo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.manager.task.TaskOmsOutManager;
import com.jumbo.wms.model.warehouse.AgvSku;

public class AgvSkuToHubThread extends Thread {


    protected static final Logger log = LoggerFactory.getLogger(AgvSkuToHubThread.class);

    private TaskOmsOutManager taskOmsOutManager;

    private AgvSku agvSku;

    public AgvSkuToHubThread(AgvSku agvSku) {
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        // ClassPathXmlApplicationContext webContext = new
        // ClassPathXmlApplicationContext("spring.xml");
        taskOmsOutManager = (TaskOmsOutManager) webContext.getBean("taskOmsOutManager");
        this.agvSku = agvSku;
    }

    @Override
    public void run() {
        try {
            taskOmsOutManager.AgvSkuToHub(agvSku);
        } catch (Exception e) {
            log.error("AgvSkuToHubThread" + agvSku.getId() + e);
        }
    }
}
