package com.jumbo.wms.model.mq;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_MQ_MSG_BATCH")
public class MqMsgBatch extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -5583133665846385619L;

    public static String MQ_MSG_TYPE_INV_ADD = "inv_add";
    public static String MQ_MSG_TYPE_INV_FULL = "inv_full";
    public static String MQ_MSG_TYPE_PRICE = "price";
    public static String MQ_MSG_TYPE_ORDER = "order";
    public static String MQ_MSG_TYPE_ASNRECEIVE = "asnReceive";
    public static String MQ_MSG_TYPE_GOODS_ISSUE = "goodsIssue";
    public static String MQ_MSG_TYPE_GOODS_RECEIPT = "goodsReceipt";
    public static String MQ_MSG_TYPE_GOODS_RECEIPT_RETURN = "goodsReceiptReturn";
    public static String MQ_MSG_TYPE_GOODS_MOVEMENT = "goodsMovement";
    public static String MQ_MSG_TYPE_GOODS_STOCK_COMPARISON = "goodsStockComparison";

    /**
     * PK
     */
    private Long id;

    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 确认时间
     */
    private Date confirmTime;

    /**
     * 状态
     */
    private MqMsgBatchStatus status;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_MQBATCH", sequenceName = "S_T_MQ_MSG_BATCH", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MQBATCH")
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

    @Column(name = "shop_id")
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "SEND_TIME")
    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Column(name = "CONFIRM_TIME")
    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.mq.MqMsgBatchStatus")})
    public MqMsgBatchStatus getStatus() {
        return status;
    }

    public void setStatus(MqMsgBatchStatus status) {
        this.status = status;
    }

}
