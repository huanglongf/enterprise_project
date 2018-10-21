package com.jumbo.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.task.TaskOmsOutManager;
import com.jumbo.wms.model.odo.Odo;

public class OdoInboundCreateStaTask {

    public static final Logger logger = LoggerFactory.getLogger(OdoInboundCreateStaTask.class);

    @Autowired
    private TaskOmsOutManager taskOmsOutManager;

    @Autowired
    private ThreadPoolService threadPoolService;

    public void createStaByODO() {
        logger.error("createStaByODO statrt");
        List<Odo> odoList = taskOmsOutManager.findCreateInBoundStaList();
        if (odoList != null && odoList.size() > 0) {
            for (Odo odo : odoList) {
                try {
                    OdoInboundCreateStaThread odoInboundCreateStaThread = new OdoInboundCreateStaThread(odo.getId());
                    threadPoolService.executeThread("createStaByODO", odoInboundCreateStaThread);
                } catch (Exception e) {
                    logger.error("createStaByODO", e);
                }
            }
        }
        threadPoolService.waitToFinish("createStaByODO");
        logger.error("createStaByODO end");
    }
}
