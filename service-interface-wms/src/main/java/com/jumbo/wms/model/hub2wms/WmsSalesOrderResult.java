package com.jumbo.wms.model.hub2wms;

import com.jumbo.wms.model.BaseModel;

/**
 * HUB2WMS过仓 通用反馈
 * 
 * @author jinlong.ke
 * 
 */
public class WmsSalesOrderResult extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -7515229604509808422L;
    /*
     * 订单来源
     */
    private String orderSource;
    /*
     * 订单号（唯一对接标识）
     */
    private String orderCode;
    /*
     * 备注
     */
    private String memo;
    /*
     * 状态 
     */
    private int status;

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
