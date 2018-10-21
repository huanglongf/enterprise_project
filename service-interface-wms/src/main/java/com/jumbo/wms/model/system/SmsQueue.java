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

@Entity
@Table(name = "T_SYS_SMS_QUEUE")
public class SmsQueue extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -345425419605084668L;
    /**
     * Id PK
     */
    private Long id;
    /**
     * 订单号/作业单号
     */
    private String staCode;
    /**
     * 生成时间
     */
    private Date createTime;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 信息内容
     */
    private String smsContent;
    /**
     * 信息Id
     */
    private String msgId;
    /**
     * 失败次数
     */
    private Integer errorCount;
    /**
     * sta所属店铺
     */
    private String owner;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SMS_QUEUE", sequenceName = "S_T_SYS_SMS_QUEUE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SMS_QUEUE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_CODE", length = 30)
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    @Column(name = "MSG_ID")
    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @Column(name = "ERROR_COUNT")
    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    @Column(name = "OWNER", length = 100)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
