package com.jumbo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.task.TaskOmsOutManager;
import com.jumbo.wms.model.warehouse.AdPackageLineDealDto;
import com.jumbo.wms.model.warehouse.agv.AgvOutBoundDto;

/**
 * 直连创单完成通知
 */
public class AdPackageLineDealThread extends Thread {
    protected static final Logger log = LoggerFactory.getLogger(AdPackageLineDealThread.class);

	private TaskOmsOutManager taskOmsOutManager;

    private AdPackageLineDealDto adPackageLineDealDto;
    
    public AdPackageLineDealThread(AdPackageLineDealDto adPackageLineDealDto) {
        this.adPackageLineDealDto = adPackageLineDealDto;
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        taskOmsOutManager = (TaskOmsOutManager) webContext.getBean("taskOmsOutManager");
    }

    @Override
    public void run() {
        try {
           // taskOmsOutManager.agvOutBoundDaemon(adPackageLineDealDto);
            taskOmsOutManager.adPackageUpdate(adPackageLineDealDto);
        } catch (BusinessException e) {
            log.error("AdPackageLineDealThread error code is : " + e.getErrorCode());
        } catch (Exception e) {
            log.error("AdPackageLineDealThread error: " + e.toString());
        }
    }

}
