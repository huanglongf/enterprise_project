package com.jumbo.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.task.TaskOmsOutManager;
import com.jumbo.wms.model.warehouse.InvetoryChangeCommand;

public class InventoryStatusChangeToOmsTask {



    @Autowired
    private TaskOmsOutManager taskOmsOutManager;
    @Autowired
    private ThreadPoolService threadPoolService;


    protected static final Logger logger = LoggerFactory.getLogger(InventoryStatusChangeToOmsTask.class);

    public void InventoryStatusChangeToOms() {
        logger.error("InventoryStatusChangeToOms start");
        List<InvetoryChangeCommand> invetoryChangeList = taskOmsOutManager.findInvAllByErrorCount();
        if (invetoryChangeList != null && invetoryChangeList.size() > 0) {
            for (InvetoryChangeCommand invetoryChange : invetoryChangeList) {
                InventoryStatusChangeToOmsThread inventoryStatusChangeToOmsThread = new InventoryStatusChangeToOmsThread(invetoryChange.getStaId(), invetoryChange.getStvId(), invetoryChange.getInvetoryChangeId());
                threadPoolService.executeThread("InventoryStatusChangeToOms", inventoryStatusChangeToOmsThread);
            }
        }
        threadPoolService.waitToFinish("InventoryStatusChangeToOms");
        logger.error("InventoryStatusChangeToOms end");
    }
}
