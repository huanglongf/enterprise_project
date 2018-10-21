package com.jumbo.wms.manager.task.transRoleAccord;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.wms.daemon.TransRoleAccordTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.TransRoleAccordManager;
import com.jumbo.wms.model.trans.TransRoleAccordCommand;

@Service("transRoleAccordTask")
public class TransRoleAccordTaskImpl extends BaseManagerImpl implements TransRoleAccordTask {

    private static final long serialVersionUID = 4741423912493393416L;

    @Autowired
    private TransRoleAccordManager transRoleAccordManager;


    @SuppressWarnings("static-access")
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void transRoleAccordSwitch() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(c.MINUTE, +12);
        String changeTime = format.format(c.getTime());
        List<TransRoleAccordCommand> tracList = transRoleAccordManager.findAvailableTransRoleAccord(changeTime);
        for (TransRoleAccordCommand t : tracList) {
            transRoleAccordManager.updateTransRole(t.getId(), t.getlRoleId(), t.getIntRoleIsAvailable(), t.getPriority());
        }
    }
}
