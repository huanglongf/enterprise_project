package com.jumbo.task;

import java.util.List;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.daemon.BocTask;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;

public class BocDaemonTaskThread extends Thread {


    private List<MsgRtnOutbound> msgoutList;

    private BocTask bocTask;

    @Override
    public void run() {
        bocTask.executeMsgRtnOutbound(msgoutList);
    }


    public BocDaemonTaskThread(List<MsgRtnOutbound> msgoutList) {
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        // ClassPathXmlApplicationContext webContext = new
        // ClassPathXmlApplicationContext("spring.xml");
        bocTask = (BocTask) webContext.getBean("bocTask");
        this.msgoutList = msgoutList;
    }
}
