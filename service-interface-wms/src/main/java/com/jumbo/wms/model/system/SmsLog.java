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

package com.jumbo.wms.model.system;

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
 * 系统登录日志
 * 
 */
@Entity
@Table(name = "T_SYS_SMS_LOG")
public class SmsLog extends BaseModel {

    private static final long serialVersionUID = 4848586243045648651L;
    /**
     * PK
     */
    private Long id;

    /**
     * 单据号
     */
    private String staCode;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 发送时间
     */
    private String sendTime;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 内容
     */
    private String smsContent;

    /**
     * 发送优先级
     */
    private Integer smsPriority;
    /**
     * 状态
     */
    private Integer status;

    /**
     * 发送状态（1 未发送，10已发送）
     */
    private Integer sendStatus;
    /**
     * 发送信息Id
     */
    private String msgId;
    /**
     * sta所属店铺
     */
    private String owner;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SMS_LG", sequenceName = "S_T_SYS_SMS_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SMS_LG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "SEND_TIME")
    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    @Column(name = "MOBILE", length = 30)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "SMS_CONTENT", length = 1000)
    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    @Column(name = "SMS_PRIORITY")
    public Integer getSmsPriority() {
        return smsPriority;
    }

    public void setSmsPriority(Integer smsPriority) {
        this.smsPriority = smsPriority;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "STA_CODE", length = 30)
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "SEND_STATUS")
    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    @Column(name = "MSG_ID")
    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @Column(name = "OWNER", length = 100)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
