package com.jumbo.wms.manager.task.replenish;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.wms.daemon.WhReplenishTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.WhReplenishManager;
import com.jumbo.wms.model.warehouse.WhReplenish;

public class WhReplenishTaskImpl extends BaseManagerImpl implements WhReplenishTask {

    /**
     * 
     */
    private static final long serialVersionUID = -5210729007244917183L;
    @Autowired
    private WhReplenishManager whReplenishManager;

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void generateWhReplenishReportDetail() {
        List<WhReplenish> wh = whReplenishManager.findAllNeedDetailOrder();
        for (WhReplenish wr : wh) {
            try {
                Boolean b = false;
                whReplenishManager.updateWrStatusAndReplenishTime(wr);
                b = true;
                if (b) {
                    whReplenishManager.generateDetail(wr);
                }
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("generateWhReplenishReportDetail", e);
                }
            }
        }
    }

}
