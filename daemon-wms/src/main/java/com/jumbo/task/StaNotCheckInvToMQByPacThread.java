package com.jumbo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.hub2wms.CreateStaTaskManager;
import com.jumbo.wms.manager.task.TaskOmsOutManager;

/**
 * 直连创单完成通知
 */
public class StaNotCheckInvToMQByPacThread extends Thread {
    protected static final Logger log = LoggerFactory.getLogger(StaNotCheckInvToMQByPacThread.class);

    private CreateStaTaskManager createStaTaskManager;

    private Long id;

    private TaskOmsOutManager taskOmsOutManager;


    
    public StaNotCheckInvToMQByPacThread(Long id) {
        this.id = id;
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        createStaTaskManager = (CreateStaTaskManager) webContext.getBean("createStaTaskManager");
        taskOmsOutManager = (TaskOmsOutManager) webContext.getBean("taskOmsOutManager");

    }

    @Override
    public void run() {
        try {
            // taskOmsOutManager.staNotCheckInvToMQByPac(id);
            // createStaTaskManager.setFlagToOrderPac(wq.getId(), id2);
            String str = createStaTaskManager.beflag5ToOme(id);
            if ("1".equals(str)) {
                createStaTaskManager.createOrdersendToMq(id);
            } else {
                taskOmsOutManager.staNotCheckInvToMQByPac(id);
            }
        } catch (BusinessException e) {
            log.error("StaNotCheckInvToMQByPacThread error code is : " + e.getErrorCode());
        } catch (Exception e) {
            log.error("StaNotCheckInvToMQByPacThread error: " + e.toString());
        }
    }

}
