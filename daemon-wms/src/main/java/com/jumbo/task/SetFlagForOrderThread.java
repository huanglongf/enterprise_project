package com.jumbo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.manager.hub2wms.CreateStaTaskManager;

public class SetFlagForOrderThread extends Thread {

    @Autowired
    private CreateStaTaskManager createStaTaskManager;

    private static final Logger logger = LoggerFactory.getLogger(SetFlagForOrderThread.class);

    // T_WH_SO_OR_RO.id
    private Long ids;

    public SetFlagForOrderThread() {
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        createStaTaskManager = (CreateStaTaskManager) context.getBean("createStaTaskManager");
        // createStaTaskManager = SpringUtil.getBean("createStaTaskManager");
    }

    @Override
    public void run() {
        if (logger.isDebugEnabled()) {
            logger.debug("SetFlagForOrderThread Begin");
        }
        createStaTaskManager.setFlagToOrderNew(ids);
        createStaTaskManager.createTomsOrdersendToMq(ids);
        if (logger.isDebugEnabled()) {
            logger.debug("SetFlagForOrderThread end");
        }
    }

    public Long getIds() {
        return ids;
    }

    public void setIds(Long ids) {
        this.ids = ids;
    }



}
