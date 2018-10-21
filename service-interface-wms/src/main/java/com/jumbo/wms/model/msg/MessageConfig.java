package com.jumbo.wms.model.msg;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * 
 * RocketMQ消息切换开关配置表
 */
@Entity
@Table(name = "T_MESSAGE_CONFIG")
public class MessageConfig extends BaseModel {

    private static final long serialVersionUID = 3184779845378226159L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 消息类型(如消息接口名称)
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
     * 描述
     */
    private String description;
    /**
     * 
     * 是否开启 0：关闭 |1： 开启
     */
    private Integer isOpen;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_MESSAGE_CONFIG", sequenceName = "S_T_MESSAGE_CONFIG", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_T_MESSAGE_CONFIG", strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "IS_OPEN")
    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

}
