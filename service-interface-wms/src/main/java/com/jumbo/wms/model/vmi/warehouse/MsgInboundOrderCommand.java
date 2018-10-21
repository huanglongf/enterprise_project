package com.jumbo.wms.model.vmi.warehouse;

import java.util.Date;

public class MsgInboundOrderCommand extends MsgInboundOrder {


    /**
	 * 
	 */
    private static final long serialVersionUID = -193482470530258656L;

    private String slipCode;

    private Date startDate;

    private Date endDate;

    private int intStatus;

    private String skuBarcode;

    private String tbCode;

    private Long qty = 0L;

    private String pic;

    private String phone;

    private String address;
    /*
     * 添加String类型时间 用于控制时间精确到时分秒 辅助
     */
    private String startDate1;
    private String endDate1;

    private String outerOrderCode;
    
    private String trackingNo;
    
    private String receiver;
    
    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(int intStatus) {
        this.intStatus = intStatus;
    }

    public String getSkuBarcode() {
        return skuBarcode;
    }

    public void setSkuBarcode(String skuBarcode) {
        this.skuBarcode = skuBarcode;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getTbCode() {
        return tbCode;
    }

    public void setTbCode(String tbCode) {
        this.tbCode = tbCode;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStartDate1() {
        return startDate1;
    }

    public void setStartDate1(String startDate1) {
        this.startDate1 = startDate1;
    }

    public String getEndDate1() {
        return endDate1;
    }

    public void setEndDate1(String endDate1) {
        this.endDate1 = endDate1;
    }

    public String getOuterOrderCode() {
        return outerOrderCode;
    }

    public void setOuterOrderCode(String outerOrderCode) {
        this.outerOrderCode = outerOrderCode;
    }

    @Override
    public String getTrackingNo() {
        return trackingNo;
    }

    @Override
    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    @Override
    public String getReceiver() {
        return receiver;
    }

    @Override
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }


}
