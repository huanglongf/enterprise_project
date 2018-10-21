package com.jumbo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.task.TaskOmsOutManager;
import com.jumbo.wms.model.hub2wms.WmsOrderStatusOms;

/**
 * 直连创单完成通知
 */
public class WmsOrderStatusOmsThread extends Thread {
    protected static final Logger log = LoggerFactory.getLogger(WmsOrderStatusOmsThread.class);

	private TaskOmsOutManager taskOmsOutManager;

    private WmsOrderStatusOms wmsOrderStatusOms;
    
    public WmsOrderStatusOmsThread(WmsOrderStatusOms wmsOrderStatusOms) {
        this.wmsOrderStatusOms = wmsOrderStatusOms;
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        taskOmsOutManager = (TaskOmsOutManager) webContext.getBean("taskOmsOutManager");
    }

    @Override
    public void run() {
        try {
            taskOmsOutManager.outboudNoticePacId(wmsOrderStatusOms);

        } catch (BusinessException e) {
            log.error("WmsOrderStatusOmsThread error code is : " + e.getErrorCode());
        } catch (Exception e) {
            log.error("WmsOrderStatusOmsThread error: " + e.toString());
        }
    }

}
