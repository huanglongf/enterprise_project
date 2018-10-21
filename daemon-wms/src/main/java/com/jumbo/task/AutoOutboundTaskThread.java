package com.jumbo.task;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.manager.expressDelivery.TransOlManager;


public class AutoOutboundTaskThread extends Thread {

    private int num;

    private TransOlManager transOlManager;

    public AutoOutboundTaskThread(int num) {
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        // ClassPathXmlApplicationContext webContext = new
        // ClassPathXmlApplicationContext("spring.xml");
        transOlManager = (TransOlManager) webContext.getBean("transOlManager");
        this.num = num;
    }

    @Override
    public void run() {
        transOlManager.findStaByStatus(num);
    }
}
