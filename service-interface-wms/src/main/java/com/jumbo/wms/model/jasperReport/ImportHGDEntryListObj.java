package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ImportHGDEntryListObj implements Serializable {

    private static final long serialVersionUID = -2694695279625613578L;
    /**
     * 作业单id
     */
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
     * 地址
     */
    private String address;
    /**
     * 商品总数量
     */
    private BigDecimal totalQty;
    /**
     * 总毛重
     */
    private BigDecimal totalWeight;
    /**
     * 销售总金额
     */
    private BigDecimal totalActual;
    /**
     * 年
     */
    private String year;
    /**
     * 月
     */
    private String month;
    /**
     * 日
     */
    private String day;
    /**
     * 明细行
     */
    private List<ImportHGDEntryListLinesObj> lines;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(BigDecimal totalQty) {
        this.totalQty = totalQty;
    }

    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }

    public BigDecimal getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(BigDecimal totalActual) {
        this.totalActual = totalActual;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<ImportHGDEntryListLinesObj> getLines() {
        return lines;
    }

    public void setLines(List<ImportHGDEntryListLinesObj> lines) {
        this.lines = lines;
    }
}
