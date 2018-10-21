package com.jumbo.webservice.sfNew.model;

import java.util.Date;

import com.jumbo.wms.model.BaseModel;

/**
 * 顺风订单发货确认
 * 
 */
public class MongoSfOrderConfirm extends BaseModel {

    private static final long serialVersionUID = -6336259841819724356L;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 运单号
     */
    private String mailno;
    /**
     * 订单操作标识 :1 -订单确认 2-取消
     */
    private Integer dealtype;

    private String content;// 报文

    private Date createTime;
    
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMailno() {
        return mailno;
    }

    public void setMailno(String mailno) {
        this.mailno = mailno;
    }

    public Integer getDealtype() {
        return dealtype;
    }

    public void setDealtype(Integer dealtype) {
        this.dealtype = dealtype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    

}
