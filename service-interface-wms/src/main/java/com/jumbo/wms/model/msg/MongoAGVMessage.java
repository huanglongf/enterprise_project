package com.jumbo.wms.model.msg;

import com.jumbo.wms.model.BaseModel;

/**
 * 
 * MQ消息mongodb保存实体
 *
 */
public class MongoAGVMessage extends BaseModel {

    private static final long serialVersionUID = 2113449711825985443L;

    /**
     * 作业单code
     */
    private String staCode;
    /**
     * 放置其他业务参数唯一键
     */
    private String otherUniqueKey;
    /**
     * 备注
     */
    private String memo;

    /******************* MessageCommond ********************/
    /**
     * 消息ID
     */
    private String msgId;

    /**
     * 消息类型:如接口名称com.baozun.pay.service.test.PayService
     */
    private String msgType;

    /**
     * 消息内容：消费者真正接收消息数据
     */
    private String msgBody;

    /**
     * 生产者发送时间
     */
    private String sendTime;

    /**
     * 消费者反馈时间
     */
    private String feedBackTime;

    /**
     * 消息描述
     */
    private Object msgDesc;

    /******************* MessageCommond ********************/

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getOtherUniqueKey() {
        return otherUniqueKey;
    }

    public void setOtherUniqueKey(String otherUniqueKey) {
        this.otherUniqueKey = otherUniqueKey;
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

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getFeedBackTime() {
        return feedBackTime;
    }

    public void setFeedBackTime(String feedBackTime) {
        this.feedBackTime = feedBackTime;
    }

    public Object getMsgDesc() {
        return msgDesc;
    }

    public void setMsgDesc(Object msgDesc) {
        this.msgDesc = msgDesc;
    }

}
