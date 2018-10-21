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
public class OutboundNoticeOmsThread extends Thread {
    protected static final Logger log = LoggerFactory.getLogger(OutboundNoticeOmsThread.class);

    private Long orderid;
	private TaskOmsOutManager taskOmsOutManager;
    
    public OutboundNoticeOmsThread(Long orderid) {
    	this.orderid = orderid;
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        taskOmsOutManager = (TaskOmsOutManager) webContext.getBean("taskOmsOutManager");
    }

    @Override
    public void run() {
        // 通知OMS出库
        try {
            taskOmsOutManager.outboudNoticeOmsById(orderid);
        } catch (BusinessException e) {
            log.error("sfIntefaceByWarehouse error code is : " + e.getErrorCode());
        } catch (Exception e) {
            log.error("TransGetNoThread error: " + e.toString());
        }
    }

}
