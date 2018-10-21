package com.bt.common.util;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

/**
 * @author Will.Wang
 * @date 2017年12月19日
 */

public class MailUtil {
	
	private static final String fromMail = "lmis@baozun.com";
	private static final String password = "lmis2018#";
	
	
	public static boolean sendMail(String toMail, String tmpPassword) {
		try {
			Properties properties = new Properties();
            properties.put("mail.transport.protocol", "smtp");// 连接协议       
            properties.put("mail.smtp.port", 25);// 端口号       
            properties.put("mail.smtp.auth", "true");    
            properties.put("mail.smtp.host", "smtp.baozun.com");
			// 得到回话对象
			Session session = Session.getInstance(properties);
			// 获取邮件对象
			Message message = new MimeMessage(session);
			// 设置发件人邮箱地址
			message.setFrom(new InternetAddress(fromMail));
			// 设置收件人地址
			message.setRecipients(RecipientType.TO, new InternetAddress[] { new InternetAddress(toMail) });
			// 设置邮件标题
			message.setSubject("LMIS临时密码");
			// 设置邮件内容

			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			String content = "您的临时密码为："+tmpPassword+"<br/>"+"请尽快登录系统修改密码。";
			// 设置HTML内容
			html.setContent(content, "text/html; charset=UTF-8");
			mainPart.addBodyPart(html);
			// 将MiniMultipart对象设置为邮件内容
			message.setContent(mainPart);
			// 得到邮差对象
			Transport transport = session.getTransport();
			// 连接自己的邮箱账户
			transport.connect("smtp.baozun.com", fromMail, password);
			// 发送邮件
			transport.sendMessage(message, message.getAllRecipients());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean sendWorkOrderMail(String toMail,String title, String content) {
		try {
			Properties properties = new Properties();
            properties.put("mail.transport.protocol", "smtp");// 连接协议       
            properties.put("mail.smtp.port", 25);// 端口号       
            properties.put("mail.smtp.auth", "true");    
            properties.put("mail.smtp.host", "smtp.baozun.com");
			// 得到回话对象
			Session session = Session.getInstance(properties);
			// 获取邮件对象
			Message message = new MimeMessage(session);
			// 设置发件人邮箱地址
			message.setFrom(new InternetAddress(fromMail));
			// 设置收件人地址
			message.setRecipients(RecipientType.TO, new InternetAddress[] { new InternetAddress(toMail) });
			// 设置邮件标题
			message.setSubject(title);
			// 设置邮件内容
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(content, "text/html; charset=UTF-8");
			mainPart.addBodyPart(html);
			// 将MiniMultipart对象设置为邮件内容
			message.setContent(mainPart);
			// 得到邮差对象
			Transport transport = session.getTransport();
			// 连接自己的邮箱账户
			transport.connect("smtp.baozun.com", fromMail, password);
			// 发送邮件
			transport.sendMessage(message, message.getAllRecipients());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
