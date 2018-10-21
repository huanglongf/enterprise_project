package com.jumbo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.jumbo.wms.model.mail.MailTemplate;

public interface MailService extends Serializable {

	/**
	 * 根据指定邮件模板发送邮件，记录邮件日志
	 * 
	 * @param mailTemplate 邮件模板
	 * @param recipients 收件人
	 * @param params 参数
	 */
	void sendMail(MailTemplate mailTemplate, List<String> recipients, Map<String, Object> params);
    /**
     * 获取邮件内容
     * 
     * @param templateCode
     * @return
     */
    String getMailBody(String templateCode);

    /**
     * 保存邮件模板内容
     * 
     * @param templateCode
     * @param context
     */
    void saveMailBody(String templateCode, String context);
}
