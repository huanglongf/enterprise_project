package com.jumbo.wms.model.msg;

/**
 * 
 * 消费者关系表DTO
 */
import java.util.Date;

import com.jumbo.wms.model.BaseModel;

public class MessageConsumerDto extends BaseModel {

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
     * 状态(0:未处理 1:已处理)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }



}
