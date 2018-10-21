package com.jumbo.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.manager.task.TaskOmsOutManager;
import com.jumbo.wms.model.warehouse.InboundAgvToHub;

public class AgvInBoundToHabTask {

    protected static final Logger log = LoggerFactory.getLogger(AgvInBoundToHabTask.class);

    @Autowired
    private TransOlManager transOlManager;

    @Autowired
    private ThreadPoolService threadPoolService;

    @Autowired
    private TaskOmsOutManager taskOmsOutManager;

    public void AgvInBoundToHub() {
        log.error("AgvInBoundToHub start");
        if (log.isInfoEnabled()) {
            log.info("AgvInBoundToHub start");
        }
        List<InboundAgvToHub> list = taskOmsOutManager.findAllByErrorCount();
        if (null != list && list.size() > 0) {
            for (InboundAgvToHub inboundAgvToHub : list) {
                try {
                    AgvInBoundToHubThread agvInBoundToHubThread = new AgvInBoundToHubThread(inboundAgvToHub);
                    threadPoolService.executeThread("AgvInBoundToHub", agvInBoundToHubThread);
                } catch (Exception e) {
                    log.error("AgvInBoundToHub", e);
                }
            }
        }
        threadPoolService.waitToFinish("AgvInBoundToHub");
        if (log.isInfoEnabled()) {
            log.info("AgvInBoundToHub end");
        }
    }

}
