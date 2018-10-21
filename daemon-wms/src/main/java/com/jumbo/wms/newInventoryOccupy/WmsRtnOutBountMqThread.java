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
public class WmsRtnOutBountMqThread extends Thread {
    protected static final Logger log = LoggerFactory.getLogger(WmsRtnOutBountMqThread.class);

    private Long orderid;
	private TaskOmsOutManager taskOmsOutManager;
    
    public WmsRtnOutBountMqThread(Long orderid) {
    	this.orderid = orderid;
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        taskOmsOutManager = (TaskOmsOutManager) webContext.getBean("taskOmsOutManager");
    }

    @Override
    public void run() {
        try {
            taskOmsOutManager.wmsRtnOutBountMq(orderid);
        } catch (BusinessException e) {
            log.error("WmsRtnOutBountMqThread error code is : " + e.getErrorCode());
        } catch (Exception e) {
            log.error("WmsRtnOutBountMqThread error: " + e.toString());
        }
    }

}
