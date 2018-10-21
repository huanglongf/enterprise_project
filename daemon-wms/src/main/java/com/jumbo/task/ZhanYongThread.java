package com.jumbo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.task.TaskOmsOutManager;
import com.jumbo.wms.model.warehouse.StockTransApplication;


public class ZhanYongThread extends Thread {
    protected static final Logger log = LoggerFactory.getLogger(ZhanYongThread.class);

	private TaskOmsOutManager taskOmsOutManager;

    private StockTransApplication stockTransApplication;
    
    public ZhanYongThread(StockTransApplication stockTransApplication) {
        this.stockTransApplication = stockTransApplication;
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        taskOmsOutManager = (TaskOmsOutManager) webContext.getBean("taskOmsOutManager");
    }

    @Override
    public void run() {
        try {
            taskOmsOutManager.zhanYongToMq(stockTransApplication);
        } catch (BusinessException e) {
            log.error("ZhanYongThread error code is : " + e.getErrorCode());
        } catch (Exception e) {
            log.error("ZhanYongThread error: " + e.toString());
        }
    }

}
