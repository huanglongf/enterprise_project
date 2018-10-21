package com.jumbo.wms.newInventoryOccupy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.task.TaskOmsOutManager;

/**
 * 
 */
public class WmsCommonMessageProducerErrorMqThread extends Thread {
    protected static final Logger log = LoggerFactory.getLogger(WmsCommonMessageProducerErrorMqThread.class);

    private Long orderid;
	private TaskOmsOutManager taskOmsOutManager;
    
    public WmsCommonMessageProducerErrorMqThread(Long orderid) {
    	this.orderid = orderid;
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        taskOmsOutManager = (TaskOmsOutManager) webContext.getBean("taskOmsOutManager");
    }

    @Override
    public void run() {
        try {
            taskOmsOutManager.wmsCommonMessageProducerErrorMq(orderid);
        } catch (BusinessException e) {
            log.error("WmsCommonMessageProducerErrorMqThread error code is : " + e.getErrorCode());
        } catch (Exception e) {
            log.error("WmsCommonMessageProducerErrorMqThread error: " + e.toString());
        }
    }

}
