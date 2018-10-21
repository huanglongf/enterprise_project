package com.jumbo.wms.model.msg;

/**
 * 
 * 生产发送失败中间表
 */
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
@Table(name = "T_MESSAGE_PRODUCER_ERROR")
public class MessageProducerError extends BaseModel {
    private static final long serialVersionUID = 6699774978357228718L;
    
    private Long id;

    private String msgId;

    private String msgType;

    private String msgBody;


    private String topic;
    
    
    private String tags;

    private Date createTime;


    private Date nextTime;

    private Integer status;// 1:未发送 10：发送成功

    private Integer num;// 错误次数

    private Integer type;

    private String staCode;


    private String slipCode;

    private String slipCode1;

    private String slipCode2;

    private Long stvId;


    @Column(name = "STV_ID")
    public Long getStvId() {
        return stvId;
    }

    public void setStvId(Long stvId) {
        this.stvId = stvId;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WHL", sequenceName = "S_T_MESSAGE_PRODUCER_ERROR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WHL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "msg_id")
    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @Column(name = "msg_type")
    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    @Column(name = "topic")
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Column(name = "tags")
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "next_time")
    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "num")
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "sta_code")
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "slip_code")
    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    @Column(name = "slip_code1")
    public String getSlipCode1() {
        return slipCode1;
    }

    public void setSlipCode1(String slipCode1) {
        this.slipCode1 = slipCode1;
    }

    @Column(name = "slip_code2")
    public String getSlipCode2() {
        return slipCode2;
    }

    public void setSlipCode2(String slipCode2) {
        this.slipCode2 = slipCode2;
    }

    @Column(name = "msg_body")
    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }


}
