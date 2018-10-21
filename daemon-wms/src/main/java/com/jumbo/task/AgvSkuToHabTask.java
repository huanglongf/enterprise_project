package com.jumbo.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.manager.task.TaskOmsOutManager;
import com.jumbo.wms.model.warehouse.AgvSku;

public class AgvSkuToHabTask {

    protected static final Logger log = LoggerFactory.getLogger(AgvSkuToHabTask.class);

    @Autowired
    private TransOlManager transOlManager;

    @Autowired
    private ThreadPoolService threadPoolService;

    @Autowired
    private TaskOmsOutManager taskOmsOutManager;

    public void AgvSkuToHub() {
        log.error("AgvSkuToHub start");
        if (log.isInfoEnabled()) {
            log.info("AgvSkuToHub start");
        }
        List<AgvSku> list = taskOmsOutManager.agvSkuToHub();
        if (null != list && list.size() > 0) {
            for (AgvSku agvSku : list) {
                try {
                    AgvSkuToHubThread t = new AgvSkuToHubThread(agvSku);
                    threadPoolService.executeThread("AgvSkuToHub", t);
                } catch (Exception e) {
                    log.error("AgvSkuToHub", e);
                }
            }
        }
        threadPoolService.waitToFinish("AgvSkuToHub");
        if (log.isInfoEnabled()) {
            log.info("AgvSkuToHub end");
        }
    }

}
