package com.jumbo.wms.manager.taskLock;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.aop.SingleTaskLockAop;
import com.baozun.task.bean.TaskWarningManager;
import com.baozun.task.bean.ZkExpired;
import com.baozun.task.listener.IZkStateListenerImpl;
import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.util.MailUtil;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.model.mail.MailTemplate;

public class WarningManagerImpl extends BaseManagerImpl implements TaskWarningManager {
    /**
     * 
     */
    private static final long serialVersionUID = 624454592106657215L;
    protected static final Logger logger = LoggerFactory.getLogger(WarningManagerImpl.class);
    private static final String TASKLOCK_NOTICE = "TASKLOCK_NOTICE";
    private static final String TASKLOCK_ZK_NOTICE = "TASKLOCK_ZK_NOTICE";
    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private ChooseOptionManager chooseOptionManager;

    @SuppressWarnings("rawtypes")
    @Override
    public void warning(String con, Class clazz) {
        logger.error("wmsTaskMail:" + con);
        try {
            Map<String, String> option = chooseOptionManager.getOptionByCategoryCode(TASKLOCK_NOTICE);
            MailTemplate template = mailTemplateDao.findTemplateByCode(TASKLOCK_NOTICE);
            String receiver = "";
            String carboncopy = "";
            String subject = "";
            String mailBody = "";
            if (null != option) {
                receiver = option.get("RECEIVER");
                carboncopy = option.get("CARBONCOPY");
            }
            if (null != template) {
                subject = template.getSubject();
                mailBody = template.getMailBody();
            }
            if (clazz.newInstance() instanceof SingleTaskLockAop) {
                if (null != template) {
                    subject = template.getSubject();
                    mailBody = template.getMailBody();
                }
            } else if (clazz.newInstance() instanceof ZkExpired) {
                template = mailTemplateDao.findTemplateByCode(TASKLOCK_ZK_NOTICE);
                if (null != template) {
                    subject = template.getSubject();
                    mailBody = template.getMailBody();
                }
            } else if (clazz.newInstance() instanceof IZkStateListenerImpl) {
                template = mailTemplateDao.findTemplateByCode(TASKLOCK_ZK_NOTICE);
                if (null != template) {
                    subject = template.getSubject();
                    mailBody = template.getMailBody();
                }
            }
            if ("".equals(receiver) || "".equals(subject) || "".equals(mailBody)) {
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(mailBody).append("\n").append(con);
            MailUtil.sendMail(subject, receiver, carboncopy, sb.toString(), false, null);
        } catch (InstantiationException e) {
            if (logger.isErrorEnabled()) {
                logger.error("warning InstantiationException:", e);
            }
        } catch (IllegalAccessException e) {
            if (logger.isErrorEnabled()) {
                logger.error("warning IllegalAccessException:", e);
            }
        }
    }

}
