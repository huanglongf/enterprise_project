package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
import java.util.Date;

public class HandOverListLineCommand extends HandOverListLine {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6016282832460458194L;

    /**
     * 相关单据号
     */
    private String refSlipCode;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 店铺
     */
    private String owner;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 扫描出库时间
     */
    private Date outboundTime;
    /**
     * 物流供应商名称
     */
    private String expName;

    /**
     * 状态
     */
    private Integer lineIntStatus;
    /**
     * 每单的商品数量
     */
    private Integer quantity;

    /**
     * 城市
     */
    private String city;

    /**
     * 收件人邮件
     */
    private String zipcode;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 物品金额
     */
    private BigDecimal totalActual;
    // /**
    // * 重量
    // */
    // private BigDecimal weight;
    /**
     * 条形码
     */
    private String barcode;
    private Date createTime;
    private String lpCode;
    private String code;
    private String transTimeType;

    private Long hoListId;

    private Long staId;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getRefSlipCode() {
        return refSlipCode;
    }

    public void setRefSlipCode(String refSlipCode) {
        this.refSlipCode = refSlipCode;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public Integer getLineIntStatus() {
        return lineIntStatus;
    }

    public void setLineIntStatus(Integer lineIntStatus) {
        this.lineIntStatus = lineIntStatus;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(BigDecimal totalActual) {
        this.totalActual = totalActual;
    }

    // public BigDecimal getWeight() {
    // return weight;
    // }
    //
    // public void setWeight(BigDecimal weight) {
    // this.weight = weight;
    // }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getTransTimeType() {
        return transTimeType;
    }

    public void setTransTimeType(String transTimeType) {
        this.transTimeType = transTimeType;
    }

    public Long getHoListId() {
        return hoListId;
    }

    public void setHoListId(Long hoListId) {
        this.hoListId = hoListId;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

}
