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
 * 
 */

package com.jumbo.wms.model.mail;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.jumbo.wms.model.BaseModel;

/**
 * 邮件发送日志，记录邮件每次发送，发送的内容和附件以文件形式保存在服务器中，如${邮件日志目录}/${年月}/${邮件日志标识}.txt，而附件记录为${邮件日志目录}/${年月}/${
 * 邮件日志标识}-${附件流水标识}.obj
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_MAIL_LOG")
public class MailLog extends BaseModel {
    private static final long serialVersionUID = -270772529429079549L;
    /**
     * 邮件日志标识
     */
    private Long id;
    /**
     * 邮件发送时间
     */
    private Date sendTime;
    /**
     * 收件人
     */
    private String recipient;
    /**
     * 附件数量
     */
    private Integer attachmentsNum;
    /**
     * 附件名称，如果有多个以“,”分隔
     */
    private String attachments;
    /**
     * 对应邮件模板Id
     */
    private Long mailTemplateId;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_MLOG", sequenceName = "S_T_MAIL_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MLOG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SEND_TIME")
    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Column(name = "RECIPIENT", length = 200)
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Column(name = "ATTACHMENTS_NUM")
    public Integer getAttachmentsNum() {
        return attachmentsNum;
    }

    public void setAttachmentsNum(Integer attachmentsNum) {
        this.attachmentsNum = attachmentsNum;
    }

    @Column(name = "ATTACHMENTS", length = 300)
    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    @Column(name = "MAIL_TEMPLATE_ID")
    public Long getMailTemplateId() {
        return mailTemplateId;
    }

    public void setMailTemplateId(Long mailTemplateId) {
        this.mailTemplateId = mailTemplateId;
    }
}
