package com.jumbo.wms.model.msg;

/**
 * 
 * 消费者关系表
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
@Table(name = "T_MESSAGE_CONSUMER")
public class MessageConsumer extends BaseModel {

    private static final long serialVersionUID = -6320803234864189421L;
    /**
     * 全局id
     */
    private Long id;
    /**
     * 消息ID(如订单号或自定义主键)
     */
    private String msgId;
    /**
     * 消息类型(如消息接口名称)
     */
    private String msgType;
    /**
     * 消息队列
     */
    private String topic;
    /**
     * 二级标签
     */
    private String tags;
    /**
     * 状态(0:未处理 1:已处理 2：處理失敗)
     */
    private Integer status;
    /**
     * 接收时间
     */
    private Date receiveTime;
    /**
     * 处理时间
     */
    private Date dealTime;
    
    /**
     * 補償次數
     */
    private Integer compensateCount;
    
    /**
     * 1:本系統校驗  2：TK校驗
     */
    private String brand;

    @Column(name = "brand")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_MESSAGE_CONSUMER", sequenceName = "S_T_MESSAGE_CONSUMER", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_T_MESSAGE_CONSUMER", strategy = GenerationType.SEQUENCE)
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

    @Column(name = "RECEIVE_TIME")
    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    @Column(name = "DEAL_TIME")
    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }
    
    @Column(name = "compensate_count")
    public Integer getCompensateCount() {
        return compensateCount;
    }

    public void setCompensateCount(Integer compensateCount) {
        this.compensateCount = compensateCount;
    }


}
