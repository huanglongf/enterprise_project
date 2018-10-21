package com.jumbo.wms.manager.task.inv;

import java.text.MessageFormat;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.warehouse.LogQueueDao;
import com.jumbo.util.MailUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.InvTask;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.warehouse.QueueStaManagerProxy;
import com.jumbo.wms.model.mail.MailTemplate;

public class InvTaskImpl implements InvTask {
    private final String INCREMENTINV_EMAIL = "INCREMENTINV_EMAIL";
    @Autowired
    private TaskEbsManager taskEbsManager;
    @Autowired
    private QueueStaManagerProxy queueStaManagerProxy;

    @Autowired
    private LogQueueDao logQueueDao;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private MailTemplateDao mailTemplateDao;

    /**
     * EBS库存同步
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void ebsInventory() {
        taskEbsManager.ebsInventory();
    }

    /**
     * 每日可销售库存同步
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void salesInventory() {
        // taskEbsManager.salesInventory();
    }

    /**
     * 实时增量库存同步
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void salesInventoryOms() {
        queueStaManagerProxy.salesInventoryOms();
    }

    /**
     * 全量库存补充文件
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void replenishForSalesInventory() {
        taskEbsManager.replenishForSalesInventory();
    }

    /**
     * 每30分钟汇总一次过仓失败的单数，发送邮件通知OMS
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void emailToOmsForIncrementInvFailure() {
        Integer count = logQueueDao.findErrorCount(new SingleColumnRowMapper<Integer>(Integer.class));
        if (count != null && count > 0) {
            Map<String, String> option = chooseOptionManager.getOptionByCategoryCode(INCREMENTINV_EMAIL);
            MailTemplate template = mailTemplateDao.findTemplateByCode(INCREMENTINV_EMAIL);
            String mailBody = MessageFormat.format(template.getMailBody(), count);
            MailUtil.sendMail(template.getSubject(), option.get(Constants.RECEIVER_ADDRESS), option.get(Constants.CARBON_COPY), mailBody, false, null);
        }
    }

    /**
     * 每30分钟汇总一次增量库存错误批次，发送邮件通知OMS
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void salesInventoryOmsEmail() {
        taskEbsManager.salesInventoryOmsEmail();
    }

}
