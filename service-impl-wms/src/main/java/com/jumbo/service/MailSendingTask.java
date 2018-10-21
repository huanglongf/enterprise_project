package com.jumbo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * 发送邮件任务
 * 
 * @author sjk
 * 
 */
public class MailSendingTask implements Runnable {
    protected static final Logger logger = LoggerFactory.getLogger(MailSendingTask.class);

    private MailMessage mailMessage;
    private JavaMailSender javaMailSender;

    public MailSendingTask(JavaMailSender javaMailSender, MailMessage mailMessage) {
        this.mailMessage = mailMessage;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void run() {
        try {
            javaMailSender.send(mailMessage.getMimeMessage());
        } catch (MailException e) {
            logger.error("Error occurs while sending mail,recipient : {},e.getMessage():{}", mailMessage.getRecipients(), e.getMessage());
        }
    }
}
