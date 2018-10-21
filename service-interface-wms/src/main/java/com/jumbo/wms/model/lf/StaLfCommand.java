package com.jumbo.wms.model.lf;

import java.math.BigDecimal;
import java.util.Date;

import com.baozun.utilities.sftp.DateUtil;


/**
 * 
 * @author lihui
 *
 */
public class StaLfCommand extends StaLf {
    private static final long serialVersionUID = 8619421871265875100L;

    private String containerId;

    private Integer packQty;
    
    private String slipcode;// LF出库单号


    private Long lfId;
    private String slipcode1;// NIke单据编码
    private String slipcode2;// LOAD KEY
    private String plantime;// 计划发货时间
    private String yunshutime;// 运输时效
    private String skuCode;// sku
    private String skuUpc;// upc
    private String skuVas;// vas
    private String lineNum;// 行号
    private Long skuQty;// 数量

    private Date planDateTime;
    
    private String whCode;


    public String getSlipcode1() {
        return slipcode1;
    }

    public void setSlipcode1(String slipcode1) {
        this.slipcode1 = slipcode1;
    }

    public String getSlipcode2() {
        return slipcode2;
    }

    public void setSlipcode2(String slipcode2) {
        this.slipcode2 = slipcode2;
    }

    private String cartonCode;

    private String orderDefine;

    private String staCode;

    private String packList;

    private String printDate;

    private Date printDateTime;

    private String loadingTime;

    private Date loadingDateTime;

    private String loadingPlace;

    private String whContact;

    private String tel;

    private String fax;

    private String contact;

    private String tel2;

    private String podStaCode;

    private Integer fw;

    private Integer app;

    private Integer eq;

    private Integer totalPackages;

    private BigDecimal totalWeight;

    private BigDecimal cargoValue;

    private BigDecimal size;

    private String unloadingTime;



    public String getContainerId() {
        return containerId;
    }

    public String getSlipcode() {
        return slipcode;
    }

    public void setSlipcode(String slipcode) {
        this.slipcode = slipcode;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public Integer getPackQty() {
        return packQty;
    }

    public void setPackQty(Integer packQty) {
        this.packQty = packQty;
    }

    public String getCartonCode() {
        return cartonCode;
    }

    public void setCartonCode(String cartonCode) {
        this.cartonCode = cartonCode;
    }

    public String getOrderDefine() {
        return orderDefine;
    }

    public void setOrderDefine(String orderDefine) {
        this.orderDefine = orderDefine;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getPackList() {
        return packList;
    }

    public void setPackList(String packList) {
        this.packList = packList;
    }

    public String getLoadingPlace() {
        return loadingPlace;
    }

    public void setLoadingPlace(String loadingPlace) {
        this.loadingPlace = loadingPlace;
    }

    public String getWhContact() {
        return whContact;
    }

    public void setWhContact(String whContact) {
        this.whContact = whContact;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getPodStaCode() {
        return podStaCode;
    }

    public void setPodStaCode(String podStaCode) {
        this.podStaCode = podStaCode;
    }

    public Integer getFw() {
        return fw;
    }

    public void setFw(Integer fw) {
        this.fw = fw;
    }

    public Integer getApp() {
        return app;
    }

    public void setApp(Integer app) {
        this.app = app;
    }

    public Integer getEq() {
        return eq;
    }

    public void setEq(Integer eq) {
        this.eq = eq;
    }

    public Integer getTotalPackages() {
        return totalPackages;
    }

    public void setTotalPackages(Integer totalPackages) {
        this.totalPackages = totalPackages;
    }

    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }

    public BigDecimal getCargoValue() {
        return cargoValue;
    }

    public void setCargoValue(BigDecimal cargoValue) {
        this.cargoValue = cargoValue;
    }

    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }


    public String getPlantime() {
        return plantime;
    }

    public void setPlantime(String plantime) {
        this.plantime = plantime;
    }

    public String getYunshutime() {
        return yunshutime;
    }

    public void setYunshutime(String yunshutime) {
        this.yunshutime = yunshutime;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuUpc() {
        return skuUpc;
    }

    public void setSkuUpc(String skuUpc) {
        this.skuUpc = skuUpc;
    }

    public String getSkuVas() {
        return skuVas;
    }

    public void setSkuVas(String skuVas) {
        this.skuVas = skuVas;
    }

    public Long getSkuQty() {
        return skuQty;
    }

    public void setSkuQty(Long skuQty) {
        this.skuQty = skuQty;
    }

    public String getLineNum() {
        return lineNum;
    }

    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    public Date getPrintDateTime() {
        return printDateTime;
    }

    public void setPrintDateTime(Date printDateTime) {
        this.printDateTime = printDateTime;
        this.printDate = DateUtil.formatDate(printDateTime, DateUtil.PATTERN_NORMAL);
    }

    public String getLoadingTime() {
        return loadingTime;
    }

    public void setLoadingTime(String loadingTime) {
        this.loadingTime = loadingTime;
    }

    public Date getLoadingDateTime() {
        return loadingDateTime;
    }

    public void setLoadingDateTime(Date loadingDateTime) {
        this.loadingDateTime = loadingDateTime;
        this.loadingTime = DateUtil.formatDate(loadingDateTime, DateUtil.PATTERN_NORMAL);
    }

    public String getUnloadingTime() {
        return unloadingTime;
    }

    public void setUnloadingTime(String unloadingTime) {
        this.unloadingTime = unloadingTime;
    }

    public Date getPlanDateTime() {
        return planDateTime;
    }

    public void setPlanDateTime(Date planDateTime) {
        this.planDateTime = planDateTime;
    }

    public Long getLfId() {
        return lfId;
    }

    public void setLfId(Long lfId) {
        this.lfId = lfId;
    }

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}


}
