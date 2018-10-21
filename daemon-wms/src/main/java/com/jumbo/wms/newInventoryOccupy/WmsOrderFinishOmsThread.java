package com.jumbo.wms.newInventoryOccupy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.task.TaskOmsOutManager;

/**
 * 直连创单完成通知
 */
public class WmsOrderFinishOmsThread extends Thread {
    protected static final Logger log = LoggerFactory.getLogger(WmsOrderFinishOmsThread.class);

    private Long orderid;
	private TaskOmsOutManager taskOmsOutManager;
    
    public WmsOrderFinishOmsThread(Long orderid) {
    	this.orderid = orderid;
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        taskOmsOutManager = (TaskOmsOutManager) webContext.getBean("taskOmsOutManager");
    }

    @Override
    public void run() {
        // 通知 直连创单反馈
        try {
            taskOmsOutManager.wmsOrderFinishOms(orderid);
        } catch (BusinessException e) {
            log.error("WmsOrderFinishOmsThread error code is : " + e.getErrorCode());
        } catch (Exception e) {
            log.error("WmsOrderFinishOmsThread error: " + e.toString());
        }
    }

}
