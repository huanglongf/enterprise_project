package com.jumbo.wms.model.warehouse;

import com.jumbo.wms.model.BaseModel;

/**
 * 便利店自提信息导出实体
 * 
 * @author jinlong.ke
 * 
 */
public class ConvenienceStoreOrderInfo extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -3567372521188162436L;
    private String erpOrderCode;
    private String address;
    private String trackingNo;
    private String receiver;
    private String telephone;
    private String outboundTime;
    private String weight;
    private String memo;
    private String lpCode;

    public String getErpOrderCode() {
        return erpOrderCode;
    }

    public void setErpOrderCode(String erpOrderCode) {
        this.erpOrderCode = erpOrderCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(String outboundTime) {
        this.outboundTime = outboundTime;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

}
