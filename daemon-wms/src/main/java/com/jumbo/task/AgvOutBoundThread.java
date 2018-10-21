package com.jumbo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.task.TaskOmsOutManager;
import com.jumbo.wms.model.warehouse.agv.AgvOutBoundDto;

/**
 * 直连创单完成通知
 */
public class AgvOutBoundThread extends Thread {
    protected static final Logger log = LoggerFactory.getLogger(AgvOutBoundThread.class);

	private TaskOmsOutManager taskOmsOutManager;

    private AgvOutBoundDto agvOutBoundDto;
    
    public AgvOutBoundThread(AgvOutBoundDto agvOutBoundDto) {
        this.agvOutBoundDto = agvOutBoundDto;
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        taskOmsOutManager = (TaskOmsOutManager) webContext.getBean("taskOmsOutManager");
    }

    @Override
    public void run() {
        try {
            taskOmsOutManager.agvOutBoundDaemon(agvOutBoundDto);
        } catch (BusinessException e) {
            log.error("AgvOutBoundThread error code is : " + e.getErrorCode());
        } catch (Exception e) {
            log.error("AgvOutBoundThread error: " + e.toString());
        }
    }

}
