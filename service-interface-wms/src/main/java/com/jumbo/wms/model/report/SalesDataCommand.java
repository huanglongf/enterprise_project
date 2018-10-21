package com.jumbo.wms.model.report;

import java.math.BigDecimal;

import com.jumbo.wms.model.BaseModel;

public class SalesDataCommand extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3170983920435807227L;

    private Long id;
    /**
     * 店铺名称
     */
    private String store;

    /**
     * 交易日期
     */
    private String transDate;

    /**
     * 交易订单数量
     */
    private Integer transNum;

    /**
     * 交易金额
     */
    private BigDecimal netAmount;

    /**
     * 交易商品件数
     */
    private Integer netQty;

    /**
     * 客单价
     */
    private BigDecimal avt;

    /**
     * 客单件
     */
    private BigDecimal upt;

    /**
     * 退货金额
     */
    private BigDecimal rtnAmt;

    /**
     * 退货件数
     */
    private Long rtnQty;

    /**
     * 实际金额
     */
    private BigDecimal acAmt;

    /**
     * 实际件数
     */
    private Long acQty;

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public Integer getTransNum() {
        return transNum;
    }

    public void setTransNum(Integer transNum) {
        this.transNum = transNum;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public Integer getNetQty() {
        return netQty;
    }

    public void setNetQty(Integer netQty) {
        this.netQty = netQty;
    }

    public BigDecimal getAvt() {
        return avt;
    }

    public void setAvt(BigDecimal avt) {
        this.avt = avt;
    }

    public BigDecimal getUpt() {
        return upt;
    }

    public void setUpt(BigDecimal upt) {
        this.upt = upt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRtnAmt() {
        return rtnAmt;
    }

    public void setRtnAmt(BigDecimal rtnAmt) {
        this.rtnAmt = rtnAmt;
    }

    public Long getRtnQty() {
        return rtnQty;
    }

    public void setRtnQty(Long rtnQty) {
        this.rtnQty = rtnQty;
    }

    public BigDecimal getAcAmt() {
        return acAmt;
    }

    public void setAcAmt(BigDecimal acAmt) {
        this.acAmt = acAmt;
    }

    public Long getAcQty() {
        return acQty;
    }

    public void setAcQty(Long acQty) {
        this.acQty = acQty;
    }

}
