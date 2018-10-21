/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.jumbo.wms.manager.taskLock;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.scm.upperservice.wms3.task.NewTaskLogManager;
import com.baozun.task.aop.SingleTaskLockAop;
import com.baozun.task.bean.ZkExpired;
import com.baozun.task.listener.IZkStateListenerImpl;
import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.taskLock.TaskLockLogDao;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.taskLock.TaskLockLog;

/**
 * @author lichuan
 * 
 */
public class NewTaskLogManagerImpl implements NewTaskLogManager {
    protected static final Logger logger = LoggerFactory.getLogger(NewTaskLogManagerImpl.class);
    private static final String TASKLOCK_NOTICE = "TASKLOCK_NOTICE";
    private static final String TASKLOCK_ZK_NOTICE = "TASKLOCK_ZK_NOTICE";
    @Autowired
    private TaskLockLogDao taskLockLogDao;
    @Autowired
    private MailTemplateDao mailTemplateDao;

    /**
     * 定时任务执行超时或ZooKeeper状态变更记录日志
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void log(String con, Class clazz) {
        // logger.error("wmsTaskLog:" + con);
        if (StringUtils.isEmpty(con)) return;
        String descreption = "";
        MailTemplate template = mailTemplateDao.findTemplateByCode(TASKLOCK_NOTICE);
        if (null != template) descreption = template.getSubject();
        try {
            if (clazz.newInstance() instanceof SingleTaskLockAop) {
                descreption = "定时任务执行状态";
                // if (null != template) descreption = template.getSubject();
            } else if (clazz.newInstance() instanceof ZkExpired) {
                // descreption = "ZooKeeper状态变更";
                template = mailTemplateDao.findTemplateByCode(TASKLOCK_ZK_NOTICE);
                if (null != template) descreption = template.getSubject();
            } else if (clazz.newInstance() instanceof IZkStateListenerImpl) {
                template = mailTemplateDao.findTemplateByCode(TASKLOCK_ZK_NOTICE);
                if (null != template) descreption = template.getSubject();
            }
            TaskLockLog tLog = new TaskLockLog();
            tLog.setCreateTime(new Date());
            tLog.setInfo(con);
            tLog.setDescreption(descreption);
            taskLockLogDao.save(tLog);
        } catch (InstantiationException e) {
            if (logger.isErrorEnabled()) {
                logger.error("Tasklog InstantiationException:", e);
            }
        } catch (IllegalAccessException e) {
            if (logger.isErrorEnabled()) {
                logger.error("Tasklog IllegalAccessException:", e);
            }
        }
    }

}
