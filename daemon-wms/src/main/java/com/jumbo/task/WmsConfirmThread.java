package com.jumbo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.task.TaskOmsOutManager;
import com.jumbo.wms.model.hub2wms.WmsConfirmOrderQueue;

/**
 * 直连创单完成通知
 */
public class WmsConfirmThread extends Thread {
    protected static final Logger log = LoggerFactory.getLogger(WmsConfirmThread.class);

	private TaskOmsOutManager taskOmsOutManager;

    private WmsConfirmOrderQueue wmsConfirmOrderQueue;
    
    public WmsConfirmThread(WmsConfirmOrderQueue wmsConfirmOrderQueue) {
        this.wmsConfirmOrderQueue = wmsConfirmOrderQueue;
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        taskOmsOutManager = (TaskOmsOutManager) webContext.getBean("taskOmsOutManager");
    }

    @Override
    public void run() {
        try {
            taskOmsOutManager.wmsConfirmOrderService3(wmsConfirmOrderQueue);
        } catch (BusinessException e) {
            log.error("WmsConfirmThread error code is : " + e.getErrorCode());
        } catch (Exception e) {
            log.error("WmsConfirmThread error: " + e.toString());
        }
    }

}
