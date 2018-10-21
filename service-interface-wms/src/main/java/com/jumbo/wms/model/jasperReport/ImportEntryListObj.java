package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ImportEntryListObj implements Serializable {

    private static final long serialVersionUID = -8136162733434409312L;

    private Long staid;

    /**
     * 作业单号
     */
    private String staCode;

    /**
     * 收件人
     */
    private String receiver;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 邮政编码
     */
    private String zipcode;

    private List<ImportEntryListLinesObj> lines;

    private String printTime;

    private Integer detailSize;

    private Integer index;

    private String logoUrl;

    private String trackingNo;

    private String slipCode2;

    private String supplineCode;

    private BigDecimal totalActual;

    private BigDecimal totalPrice;

    private Integer num;

    private BigDecimal weight;

    private String unitName;

    private String packageCount;


    public String getPackageCount() {
        return packageCount;
    }

    public void setPackageCount(String packageCount) {
        this.packageCount = packageCount;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(BigDecimal totalActual) {
        this.totalActual = totalActual;
    }

    public String getSupplineCode() {
        return supplineCode;
    }

    public void setSupplineCode(String supplineCode) {
        this.supplineCode = supplineCode;
    }

    public String getSlipCode2() {
        return slipCode2;
    }

    public void setSlipCode2(String slipCode2) {
        this.slipCode2 = slipCode2;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Long getStaid() {
        return staid;
    }

    public void setStaid(Long staid) {
        this.staid = staid;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public List<ImportEntryListLinesObj> getLines() {
        return lines;
    }

    public void setLines(List<ImportEntryListLinesObj> lines) {
        this.lines = lines;
    }

    public String getPrintTime() {
        return printTime;
    }

    public void setPrintTime(String printTime) {
        this.printTime = printTime;
    }

    public Integer getDetailSize() {
        return detailSize;
    }

    public void setDetailSize(Integer detailSize) {
        this.detailSize = detailSize;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }


}
