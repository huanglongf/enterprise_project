package com.jumbo.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import loxia.service.VelocityTemplateService;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.ConfigurableMimeFileTypeMap;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.mail.MailLogDao;
import com.jumbo.dao.mail.MailTemplateParamAttachParamDao;
import com.jumbo.dao.mail.MailTemplateParamCustomParamDao;
import com.jumbo.wms.model.mail.MailLog;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.mail.MailTemplateParamAttachParam;
import com.jumbo.wms.model.mail.MailTemplateParamCustomParam;
import com.jumbo.wms.model.mail.MailType;

@Service("mailService")
public class MailServiceImpl implements MailService {

    protected static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    private static final long serialVersionUID = -619731473178566488L;

    public static final String MAIL_TEMPLATE_EXTENSION_NAME = ".txt";

    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private MailLogDao mailLogDao;
    @Autowired
    private MailTemplateParamAttachParamDao mailTemplateParamAttachParamDao;
    @Autowired
    private MailTemplateParamCustomParamDao mailTemplateParamCustomParamDao;
    @Autowired
    private VelocityTemplateService velocityTemplateService;

    private JavaMailSenderImpl javaMailSender;

    public void sendMail(MailTemplate mailTemplate, List<String> recipients, Map<String, Object> params) {
        MailMessage msg = construtMail(mailTemplate, recipients, params);
        sendMail(msg);
    }

    /**
     * 保存邮件日志
     * 
     * @param msg
     */
    private void addMailLog(MailMessage msg) {
        for (MailLog log : msg.getMailLog()) {
            mailLogDao.save(log);
        }
    }

    /**
     * 保存邮件模板内容
     * 
     * @param templateCode
     * @param context
     */
    public void saveMailBody(String templateCode, String context) {
        File file = new File(MAIL_PATH_TEMPLATE + File.separator + templateCode + MAIL_TEMPLATE_EXTENSION_NAME);
        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            fw = new FileWriter(file);
            writer = new BufferedWriter(fw);
            writer.write(context);
            writer.flush();
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error("saveMailBody-IOException", e);
            }
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("writer.close-IOException", e);
                    }
                }
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("fw.close-IOException", e);
                    }
                }
            }
        }

    }

    /**
     * 获取邮件内容
     * 
     * @param templateCode
     * @return
     */
    public String getMailBody(String templateCode) {
        File file = new File(MAIL_PATH_TEMPLATE + File.separator + templateCode + MAIL_TEMPLATE_EXTENSION_NAME);
        StringBuilder sb = new StringBuilder();
        FileReader fr = null;
        BufferedReader reader = null;
        try {
            fr = new FileReader(file);
            reader = new BufferedReader(fr);
            String line = null;
            do {
                line = reader.readLine();
                if (line != null) {
                    sb.append(line).append("\r\n");
                }
            } while (line != null);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException : file is :" + templateCode);
        } catch (IOException e) {
            throw new RuntimeException("IOException :" + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("reader.close-IOException", e);
                    }
                }
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("fr.close-IOException", e);
                    }
                }
            }
        }
        return sb.toString();
    }

    /**
     * 构造邮件消息
     * 
     * @param mailTemplate
     * @param recipients
     * @param params
     * @return
     */
    private MailMessage construtMail(MailTemplate mailTemplate, List<String> recipients, Map<String, Object> params) {
        boolean isHtml = MailType.HTML.equals(mailTemplate.getMailType());
        MailMessage msg = new MailMessage();
        MimeMessage mimeMsg = javaMailSender.createMimeMessage();
        msg.setMimeMessage(mimeMsg);
        boolean multipart = true; // 是否带附件邮件
        List<MailTemplateParamAttachParam> attParams = mailTemplateParamAttachParamDao.findByTemplateId(mailTemplate.getId());
        if (attParams == null || attParams.size() == 0) {
            multipart = false;
        }
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        List<MailTemplateParamCustomParam> cusParams = mailTemplateParamCustomParamDao.findByTemplateId(mailTemplate.getId());
        // 设置参数
        // 如果参数传入取传入值，否则取预定义值
        for (MailTemplateParamCustomParam param : cusParams) {
            if (params.get(param.getName()) == null) {
                params.put(param.getName(), param.getDefaultValue());
            }
        }
        int accachmentNum = 0; // 附件数
        String attachments = null; // 邮件日志附件名
        // 构造邮件
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(msg.getMimeMessage(), multipart, "utf-8");
            messageHelper.setSubject(mailTemplate.getSubject() == null ? "" : velocityTemplateService.parseVMContent(mailTemplate.getSubject(), params));
            messageHelper.setFrom(new InternetAddress(MAIL_FROM_ADDR, MimeUtility.encodeText(MAIL_FROM_NAME, MAIL_ENCODING, "B")));
            messageHelper.setTo(recipients.toArray(new String[0]));
            String body = velocityTemplateService.parseVMContent(getMailBody(mailTemplate.getCode()), params);
            messageHelper.setText(body, isHtml);
            // add attachments
            // 只发送模板定义的附件
            // 如果定义而非传入则不发送该附件
            // 如果未传入未定义附件则被抛弃
            List<MailLog> logs = new ArrayList<MailLog>();
            if (attParams != null) {
                for (MailTemplateParamAttachParam param : attParams) {
                    if (params.get(param.getName()) != null) {
                        Object obj = params.get(param.getName());
                        // 附件参数值接受byte数组
                        if (obj instanceof byte[]) {
                            String contentType = ConfigurableMimeFileTypeMap.getDefaultFileTypeMap().getContentType(param.getDefaultValue());
                            messageHelper.addAttachment(param.getDefaultValue(), new ByteArrayResource((byte[]) obj), contentType);
                            logger.debug("add attachment,file name : {} ,contentType is {}", param.getDefaultValue(), contentType);
                            attachments = attachments == null ? param.getDefaultValue() : attachments + "," + param.getDefaultValue();
                            accachmentNum++;
                        }
                    }
                }
            }
            // 构造邮件日志
            for (String recipient : recipients) {
                MailLog log = new MailLog();
                log.setMailTemplateId(mailTemplate.getId());
                log.setSendTime(new Date());
                log.setAttachmentsNum(accachmentNum);
                log.setAttachments(attachments);
                log.setRecipient(recipient);
                logs.add(log);
            }
            msg.setMailLog(logs);
        } catch (MessagingException e) {
            if (logger.isErrorEnabled()) {
                logger.error("construtMail-MessagingException", e);
            }
        } catch (UnsupportedEncodingException e) {
            if (logger.isErrorEnabled()) {
                logger.error("construtMail-UnsupportedEncodingException", e);
            }
        }
        return msg;
    }

    /**
     * 发送邮件
     * 
     * @param mailMessage
     */
    private void sendMail(MailMessage mailMessage) {
        // javaMailSender.send(mailMessage.getMimeMessage());
        Runnable runnable = new MailSendingTask(javaMailSender, mailMessage);
        taskExecutor.execute(runnable);
        addMailLog(mailMessage);

    }

    @PostConstruct
    protected void afterPropertiesSet() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("[Mail Parameters]");
            logger.debug("From : {}[{}]", MAIL_FROM_ADDR, MAIL_FROM_NAME);
            logger.debug("Host : {}", MAIL_HOST);
            logger.debug("Auth : {}", !"false".equalsIgnoreCase(MAIL_AUTH));
        }
        javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setDefaultEncoding(MAIL_ENCODING);
        javaMailSender.setHost(MAIL_HOST);
        javaMailSender.setPort(Integer.parseInt(MAIL_HOST_PORT));
        Properties mailProperties = new Properties();
        if (MAIL_CONN_TIMEOUT != null && MAIL_CONN_TIMEOUT.length() > 0) {
            mailProperties.put("mail.smtp.connectiontimeout", Integer.parseInt(MAIL_CONN_TIMEOUT));
        }
        if (MAIL_TIMEOUT != null && MAIL_TIMEOUT.length() > 0) {
            mailProperties.put("mail.smtp.timeout", Integer.parseInt(MAIL_TIMEOUT));
        }
        boolean auth = true;
        if ("false".equalsIgnoreCase(MAIL_AUTH)) {
            auth = false;
        }
        mailProperties.put("mail.smtp.auth", new Boolean(auth).toString());
        javaMailSender.setJavaMailProperties(mailProperties);
        if (auth) {
            javaMailSender.setUsername(MAIL_USER);
            javaMailSender.setPassword(MAIL_PWD);
        }
    }

    public String parseVMContent(String templateContent, Map<String, Object> contextParameters) {

        try {
            VelocityContext context = new VelocityContext();
            for (String key : contextParameters.keySet()) {
                context.put(key, contextParameters.get(key));
            }
            StringWriter writer = new StringWriter();
            Velocity.evaluate(context, writer, "jumbovm", templateContent);
            String result = writer.getBuffer().toString();
            return result;
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("parseVMContent:" + templateContent, e);
            }
            throw new RuntimeException("Parse Velocity Template Error");
        }
    }

    private ResourceBundle config = ResourceBundle.getBundle("mail");
    private final String MAIL_FROM_NAME = config.getString("from.name");
    private final String MAIL_FROM_ADDR = config.getString("from.addr");
    private final String MAIL_HOST = config.getString("smtp.host.addr");
    private final String MAIL_HOST_PORT = config.getString("smtp.host.port");
    private final String MAIL_CONN_TIMEOUT = config.getString("mail.connect.timeout");
    private final String MAIL_TIMEOUT = config.getString("mail.timeout");
    private final String MAIL_ENCODING = config.getString("mail.encoding");
    private final String MAIL_AUTH = config.getString("mail.auth");
    private final String MAIL_USER = config.getString("user.login");
    private final String MAIL_PWD = config.getString("user.password");
    private final String MAIL_PATH_TEMPLATE = config.getString("mail.path.template");
}
