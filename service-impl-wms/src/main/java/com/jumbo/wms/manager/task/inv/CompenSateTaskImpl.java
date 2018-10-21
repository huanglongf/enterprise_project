package com.jumbo.wms.manager.task.inv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.wms.daemon.CompenSateTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.compensation.CompensationManager;
import com.jumbo.wms.model.compensation.WhCompensationCommand;

public class CompenSateTaskImpl extends BaseManagerImpl implements CompenSateTask {

    /**
     * 
     */
    private static final long serialVersionUID = 3400470336059364524L;

    @Autowired
    private CompensationManager compensationManager;

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void changeCompenSateState() {
        List<WhCompensationCommand> list = compensationManager.findStatesIsCheck();
        for (WhCompensationCommand whCompensationCommandList : list) {
            if (null != whCompensationCommandList.getWarn() && 0 >= whCompensationCommandList.getWarn()) {
                compensationManager.updateStatesIsSucceed(whCompensationCommandList.getId());
            }
        }

    }


}
