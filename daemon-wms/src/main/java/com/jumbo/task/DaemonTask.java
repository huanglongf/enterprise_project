package com.jumbo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.util.FileUtils;
import com.jumbo.util.MailUtil;

public class DaemonTask {
    private static final Logger log = LoggerFactory.getLogger(DaemonTask.class);
    /**
     * 异常定时任务 邮件通知
     */
    public void errorTaskMailNotify() {
        log.error("errorTaskMailNotify");
        String mailMsg = null;
        int index = 1;
        do {
            try {
                mailMsg = FileUtils.operateFile(FileUtils.TYPE_READ, null, null, null);
                index = 6;
            } catch (Exception e) {
                index++;
            }
        } while (index < 6);
        if (mailMsg != null && mailMsg.length() > 0) {
            if (MailUtil.byDaemonSendMail(mailMsg)) {
                FileUtils.operateFile(FileUtils.TYPE_DELETE, null, null, null);
            }
        }
    }
}
