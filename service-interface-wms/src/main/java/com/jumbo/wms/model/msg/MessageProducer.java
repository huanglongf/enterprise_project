package com.jumbo.wms.model.msg;

/**
 * 
 * 生产者关系表
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
@Table(name = "T_MESSAGE_PRODUCER")
public class MessageProducer extends BaseModel {

    private static final long serialVersionUID = 6647421561596329362L;
    /**
     * id(全局id)
     */
    private Long id;
    /**
     * 消息id(如订单号或自定义id)
     */
    private String msgId;
    /**
     * 消息类型(如存放接口路径等)
     */
    private String msgType;
    /**
     * 消息队列(topic)
     */
    private String topic;
    /**
     * 二级标签
     */
    private String tags;
    /**
     * 状态(0:创建 1:消费)
     */
    private Integer status;
    /**
     * 发送时间
     */
    private Date sendTime;
    /**
     * 反馈时间
     */
    private Date feedbackTime;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_MESSAGE_PRODUCER", sequenceName = "S_T_MESSAGE_PRODUCER", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_T_MESSAGE_PRODUCER", strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "MSG_ID")
    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @Column(name = "MSG_TYPE")
    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    @Column(name = "TOPIC")
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Column(name = "TAGS")
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "SEND_TIME")
    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Column(name = "FEEDBACK_TIME")
    public Date getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(Date feedbackTime) {
        this.feedbackTime = feedbackTime;
    }
}
