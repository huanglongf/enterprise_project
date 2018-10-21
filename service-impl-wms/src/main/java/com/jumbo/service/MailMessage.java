package com.jumbo.service;

import java.util.List;

import javax.mail.internet.MimeMessage;

import com.jumbo.wms.model.mail.MailLog;

public class MailMessage {
    /**
     * 邮件
     */
    private MimeMessage mimeMessage;
    /**
     * 收件人
     */
    private List<String> recipients;
    /**
     * 邮件日志
     */
    private List<MailLog> mailLog;

    public MimeMessage getMimeMessage() {
        return mimeMessage;
    }

    public void setMimeMessage(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    public List<MailLog> getMailLog() {
        return mailLog;
    }

    public void setMailLog(List<MailLog> mailLog) {
        this.mailLog = mailLog;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

}
