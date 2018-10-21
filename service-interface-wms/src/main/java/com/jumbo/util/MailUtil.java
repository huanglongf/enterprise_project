package com.jumbo.util;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.exception.BusinessException;

/**
 * Send mail util,Edit by KJL 2015-06-29
 * 
 * @author jinlong.ke
 * 
 */
public class MailUtil implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3574460518530608693L;
    protected static final Logger log = LoggerFactory.getLogger(MailUtil.class);
    // 从mail.propertis读取信息
    private static ResourceBundle config = ResourceBundle.getBundle("mail");
    // smtp服务器
    private static final String MAIL_HOST = config.getString("host");
    // 用户名
    private static final String MAIL_USER = config.getString("user");
    // 密码
    private static final String MAIL_PWD = config.getString("pwd");
    // 邮件收件人
    private static String MAIL_RECEIVER = config.getString("mail.receiver");
    // 邮件抄送人
    private static String MAIL_CARBONCOPY = config.getString("mail.carbonCopy");
    // 邮件主题
    private static String MAIL_SUBJECT = config.getString("mail.subject");

    public final static boolean byDaemonSendMail(String mailBody) {
        int index = 0;
        do {
            try {
                sendMail(MAIL_SUBJECT, MAIL_RECEIVER, MAIL_CARBONCOPY, mailBody, false, null);
                break;
            } catch (Exception e) {
                log.error("", e);
                index++;
            }
        } while (index < 5);
        return index < 5;
    }

    /**
     * 通用发送邮件方法 edit by KJL 2014-10-28
     * 
     * @param subject 邮件主题
     * @param receiver 收件人
     * @param carbonCopy 抄送人
     * @param mailBody 邮件内容
     * @return
     */
    public static boolean sendMail(String subject, String receiver, String carbonCopy, String mailBody, Boolean isHtmlContent, List<File> attachment) {
        Properties props = new Properties();
        // 设置发送邮件的邮件服务器的属性（这里使用公司邮箱的的smtp服务器）
        props.put("mail.smtp.host", MAIL_HOST);
        // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.connectiontimeout", "600000");
        props.put("mail.smtp.timeout", "600000");
        // 用刚刚设置好的props对象构建一个session
        Session session = Session.getDefaultInstance(props);
        // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
        session.setDebug(true);
        // 用session为参数定义消息对象
        MimeMessage message = new MimeMessage(session);
        try {
            // 加载发件人地址
            message.setFrom(new InternetAddress(MAIL_USER));
            InternetAddress[] receiverAddress = null;
            InternetAddress[] carbonCopyAddress = null;
            String[] arr = null;
            if (receiver != null) {
                arr = receiver.split(";");
                if (arr.length > 0) {
                    receiverAddress = new InternetAddress[arr.length];
                    for (int i = 0; i < arr.length; i++) {
                        receiverAddress[i] = new InternetAddress(arr[i]);
                    }
                } else {
                    log.error("No receiver...........");
                    throw new BusinessException("");
                }
            } else {
                log.error("No receiver...........");
                throw new BusinessException("");
            }
            if (carbonCopy != null && !("").equals(carbonCopy)) {
                arr = carbonCopy.split(";");
                if (arr.length > 0) {
                    carbonCopyAddress = new InternetAddress[arr.length];
                    for (int i = 0; i < arr.length; i++) {
                        carbonCopyAddress[i] = new InternetAddress(arr[i]);
                    }
                }
            }
            message.setRecipients(Message.RecipientType.TO, receiverAddress);
            if (carbonCopyAddress != null) {
                message.setRecipients(Message.RecipientType.CC, carbonCopyAddress);
            }
            // 加载标题
            message.setSubject(subject);
            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();
            BodyPart contentPart = new MimeBodyPart();
            if (isHtmlContent != null) {
                if (isHtmlContent) {
                    // 设置HTML内容
                    contentPart.setContent(mailBody, "text/html;charset=UTF-8");
                } else {
                    // 设置邮件的文本内容
                    contentPart.setText(mailBody);
                }
            }
            multipart.addBodyPart(contentPart);
            if (attachment != null) {
                for (int i = 0; i < attachment.size(); i++) {
                    BodyPart attachmentBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(attachment.get(i));
                    attachmentBodyPart.setDataHandler(new DataHandler(source));
                    // MimeUtility.encodeWord可以避免文件名乱码
                    attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.get(i).getName()));
                    multipart.addBodyPart(attachmentBodyPart);
                }
            }
            // 将multipart对象放到message中
            message.setContent(multipart);
            // 保存邮件
            message.saveChanges();
            // 发送邮件
            Transport transport = session.getTransport("smtp");
            // 连接服务器的邮箱
            transport.connect(MAIL_HOST, MAIL_USER, MAIL_PWD);
            // 把邮件发送出去
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        } catch (BusinessException e) {
            log.info("sendMail ,error code : {}", e.getErrorCode());
            return false;
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }

}
