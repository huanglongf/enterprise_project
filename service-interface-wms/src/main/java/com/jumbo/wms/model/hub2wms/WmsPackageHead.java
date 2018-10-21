package com.jumbo.wms.model.hub2wms;

import java.util.Date;
import java.util.List;

import com.jumbo.wms.model.BaseModel;

/**
 * 异常包裹头信息
 * 
 * @author xiaolong.fei
 * 
 */
public class WmsPackageHead extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -8663088940471014801L;
    /**
     * wms异常包裹编码 唯一
     */
    private String wmsBillCode;
    /**
     * 包裹类型 1：退回包裹无指令.2 有指令
     */
    private String packageType;
    /**
     * 相关店铺
     */
    private String shopOwner;
    /**
     * 相关单据号1
     */
    private String slipCode1;
    /**
     * 相关单据号2
     */
    private String slipCode2;
    /**
     * 相关单据号3
     */
    private String slipCode3;
    /**
     * 退回快递编码
     */
    private String transCode;
    /**
     * 退回快递单号
     */
    private String trackingNo;
    /**
     * 退货人
     */
    private String rtnPersion;
    /**
     * 退货人联系手机
     */
    private String mobile;
    /**
     * 退货人联系电话
     * 
     */
    private String telephone;
    /**
     * 仓库操作员 （异常包裹登记人）
     */
    private String wmsOperator;
    /**
     * 异常包裹登记时间
     */
    private Date checkedTime;
    /**
     * 退回仓库编码
     */
    private String warehouseCode;
    /**
     * 备注
     */
    private String remark;
    /**
     * 附件名称
     * 
     */
    private String fileName;
    
    ////////////////////////////////ad/////
    
    private String receiveDate;//收货日期 
    
    
    
    
    //////////////////////////////////
    
    
    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * 异常包裹指令明细行信息
     */
    private List<WmsPackageLine> lines;

    public String getWmsBillCode() {
        return wmsBillCode;
    }

    public void setWmsBillCode(String wmsBillCode) {
        this.wmsBillCode = wmsBillCode;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getShopOwner() {
        return shopOwner;
    }

    public void setShopOwner(String shopOwner) {
        this.shopOwner = shopOwner;
    }

    public String getSlipCode1() {
        return slipCode1;
    }

    public void setSlipCode1(String slipCode1) {
        this.slipCode1 = slipCode1;
    }

    public String getSlipCode2() {
        return slipCode2;
    }

    public void setSlipCode2(String slipCode2) {
        this.slipCode2 = slipCode2;
    }

    public String getSlipCode3() {
        return slipCode3;
    }

    public void setSlipCode3(String slipCode3) {
        this.slipCode3 = slipCode3;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getRtnPersion() {
        return rtnPersion;
    }

    public void setRtnPersion(String rtnPersion) {
        this.rtnPersion = rtnPersion;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWmsOperator() {
        return wmsOperator;
    }

    public void setWmsOperator(String wmsOperator) {
        this.wmsOperator = wmsOperator;
    }

    public Date getCheckedTime() {
        return checkedTime;
    }

    public void setCheckedTime(Date checkedTime) {
        this.checkedTime = checkedTime;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<WmsPackageLine> getLines() {
        return lines;
    }

    public void setLines(List<WmsPackageLine> lines) {
        this.lines = lines;
    }

}
