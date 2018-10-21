package com.jumbo.wms.newInventoryOccupy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.task.TaskOmsOutManager;

/**
 * 物流获取运单号线程
 * @author sjk
 *
 */
public class OutboundNoticePacThread extends Thread {
    protected static final Logger log = LoggerFactory.getLogger(OutboundNoticePacThread.class);

    private Long staId;
    private Long orderid;
	private TaskOmsOutManager taskOmsOutManager;
    
    public OutboundNoticePacThread(Long staId,Long orderid) {
    	this.orderid = orderid;
    	this.staId = staId;
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        taskOmsOutManager = (TaskOmsOutManager) webContext.getBean("taskOmsOutManager");
    }

    @Override
    public void run() {
    	//通知PAC出库
        try {
        	taskOmsOutManager.outboudNoticePacById(staId, orderid);
        } catch (BusinessException e) {
            log.error("sfIntefaceByWarehouse error code is : " + e.getErrorCode());
        } catch (Exception e) {
            log.error("TransGetNoThread error: " + e.toString());
        }
    }

}
