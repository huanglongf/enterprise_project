package com.jumbo.wms.model.hub2wms;

import java.util.List;

import com.jumbo.wms.model.BaseModel;

/**
 * 订单取消结果
 * 
 * @author cheng.su
 * 
 */
public class WmsOrderCancelResult extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6933485975518506306L;
    /**
     * 订单号(唯一对接标识)
     */
    private String orderCode;

    /**
     * 店铺
     */
    private String owner;
    /**
     * 状态(保留字段)
     */
    private int status;
    /**
     * 订单类型
     */
    private int orderType;
    /**
     * 包裹结果
     */
    private List<WmsShippingResult> shippings;
    /**
     * 备注
     */
    private String memo;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public List<WmsShippingResult> getShippings() {
        return shippings;
    }

    public void setShippings(List<WmsShippingResult> shippings) {
        this.shippings = shippings;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


}
