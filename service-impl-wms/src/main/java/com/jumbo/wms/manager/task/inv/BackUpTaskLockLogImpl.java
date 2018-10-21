package com.jumbo.wms.manager.task.inv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.taskLock.TaskLockLogDao;
import com.jumbo.wms.daemon.BackUpTaskLockLog;
import com.jumbo.wms.manager.BaseManagerImpl;

@Transactional
public class BackUpTaskLockLogImpl extends BaseManagerImpl implements BackUpTaskLockLog {

    @Autowired
    private TaskLockLogDao taskLockLogDao;
    /**
	 * 
	 */
    private static final long serialVersionUID = -1769927046713109724L;

    @Override
    public void backUpTackLock() {
        taskLockLogDao.insertTaskLockLogBackUp();
        taskLockLogDao.deleteTaskLockLog();
    }

}
