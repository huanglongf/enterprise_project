package com.jumbo.webservice.sf.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.jumbo.wms.model.BaseModel;

/**
 * 顺风下单实体
 * 
 * @author sjk
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "tporder")
public class SfOrder extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 6666851157476649456L;
    /**
     * 订单号
     */
    @XmlElement(required = true, name = "orderid")
    private String orderId;
    /**
     * 物流公司代码
     */
    @XmlElement(name = "logistic_provider")
    private String logisticProvider = "sf";
    /**
     * 订单服务类别
     */
    @XmlElement(required = true, name = "apply_type")
    private String applyType = "010";
    /**
     * 寄件方客户卡号
     */
    @XmlElement(required = true, name = "j_custid")
    private String jCustid;
    /**
     * 寄件方客户编码
     */
    @XmlElement(name = "j_custtag")
    private String jCusttag;
    /**
     * 寄件方公司名称
     */
    @XmlElement(required = true, name = "j_company")
    private String jCompany;
    /**
     * 寄件方联系人
     */
    @XmlElement(required = true, name = "j_contact")
    private String jContact;
    /**
     * 寄件方邮政编码
     */
    @XmlElement(name = "j_postcode")
    private String jPostcode;
    /**
     * 寄件方座机
     */
    @XmlElement(required = true, name = "j_tel")
    private String jTel;
    /**
     * 寄件方手机
     */
    @XmlElement(name = "j_mobile")
    private String jMobile;
    /**
     * 寄件方所在省份
     */
    @XmlElement(required = true, name = "j_province")
    private String jProvince;
    /**
     * 寄件方所属城市名称
     */
    @XmlElement(required = true, name = "j_city")
    private String jCity;
    /**
     * 寄件方所在区/县
     */
    @XmlElement(required = true, name = "j_county")
    private String jCounty;
    /**
     * 寄件方详细地址
     */
    @XmlElement(required = true, name = "j_address")
    private String jAddress;
    /**
     * 到件方公司名称
     */
    @XmlElement(name = "d_company")
    private String dCompany;
    /**
     * 到件方联系人
     */
    @XmlElement(required = true, name = "d_contact")
    private String dContact;
    /**
     * 到件方座机
     */
    @XmlElement(name = "d_tel")
    private String dTel;
    /**
     * 到件方手机
     */
    @XmlElement(name = "d_mobile")
    private String dMobile;
    /**
     * 到件方所在省份
     */
    @XmlElement(required = true, name = "d_province")
    private String dProvince;
    /**
     * 到件方所属城市名称
     */
    @XmlElement(required = true, name = "d_city")
    private String dCity;
    /**
     * 到件方所在区/县
     */
    @XmlElement(required = true, name = "d_county")
    private String dCounty;
    /**
     * 到件方地址
     */
    @XmlElement(required = true, name = "d_address")
    private String dAddress;
    /**
     * 货物名称
     */
    @XmlElement(name = "cargo")
    private String cargo;
    /**
     * 包裹数量
     */
    @XmlElement(name = "parcel_quantity")
    private Integer parcelQuantity = 1;
    /**
     * 是否代收货款：0-COD业务，1-普通业务
     */
    @XmlElement(name = "is_cod")
    private Integer isCod = 1;
    /**
     * 代收货款金额
     */
    @XmlElement(name = "cod_amount")
    private String codAmount;
    /**
     * 付款方式：1:寄方付 2:收方付 3:第三方付
     */
    @XmlElement(name = "pay_method")
    private Integer payMethod = 1;
    /**
     * 付款客户月结卡号，顺丰分配给月结客户的10位长度卡号
     */
    @XmlElement(name = "pay_custid")
    private String payCustid;
    /**
     * 校验字段，用于校验客户系统请求是否合法
     */
    @XmlElement(required = true, name = "checkword")
    private String checkword;
    /**
     * 寄件方城市编码
     */
    @XmlElement(required = true, name = "j_cityid")
    private String jCityid;
    /**
     * 仓储对应网点编码
     */
    @XmlElement(required = true, name = "filter3")
    private String filter3 = "BZ021001";
    /**
     * 到件方城市编码
     */
    @XmlElement(required = true, name = "d_cityid")
    private String dCityid;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLogisticProvider() {
        return logisticProvider;
    }

    public void setLogisticProvider(String logisticProvider) {
        this.logisticProvider = logisticProvider;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getjCustid() {
        return jCustid;
    }

    public void setjCustid(String jCustid) {
        this.jCustid = jCustid;
    }

    public String getjCusttag() {
        return jCusttag;
    }

    public void setjCusttag(String jCusttag) {
        this.jCusttag = jCusttag;
    }

    public String getjCompany() {
        return jCompany;
    }

    public void setjCompany(String jCompany) {
        this.jCompany = jCompany;
    }

    public String getjContact() {
        return jContact;
    }

    public void setjContact(String jContact) {
        this.jContact = jContact;
    }

    public String getjPostcode() {
        return jPostcode;
    }

    public void setjPostcode(String jPostcode) {
        this.jPostcode = jPostcode;
    }

    public String getjTel() {
        return jTel;
    }

    public void setjTel(String jTel) {
        this.jTel = jTel;
    }

    public String getjMobile() {
        return jMobile;
    }

    public void setjMobile(String jMobile) {
        this.jMobile = jMobile;
    }

    public String getjProvince() {
        return jProvince;
    }

    public void setjProvince(String jProvince) {
        this.jProvince = jProvince;
    }

    public String getjCity() {
        return jCity;
    }

    public void setjCity(String jCity) {
        this.jCity = jCity;
    }

    public String getjCounty() {
        return jCounty;
    }

    public void setjCounty(String jCounty) {
        this.jCounty = jCounty;
    }

    public String getjAddress() {
        return jAddress;
    }

    public void setjAddress(String jAddress) {
        this.jAddress = jAddress;
    }

    public String getdCompany() {
        return dCompany;
    }

    public void setdCompany(String dCompany) {
        this.dCompany = dCompany;
    }

    public String getdContact() {
        return dContact;
    }

    public void setdContact(String dContact) {
        this.dContact = dContact;
    }

    public String getdTel() {
        return dTel;
    }

    public void setdTel(String dTel) {
        this.dTel = dTel;
    }

    public String getdMobile() {
        return dMobile;
    }

    public void setdMobile(String dMobile) {
        this.dMobile = dMobile;
    }

    public String getdProvince() {
        return dProvince;
    }

    public void setdProvince(String dProvince) {
        this.dProvince = dProvince;
    }

    public String getdCity() {
        return dCity;
    }

    public void setdCity(String dCity) {
        if ("县".equals(dCity)) {
            this.dCity = "";
        } else {
            this.dCity = dCity;
        }
    }

    public String getdCounty() {
        return dCounty;
    }

    public void setdCounty(String dCounty) {
        this.dCounty = dCounty;
    }

    public String getdAddress() {
        return dAddress;
    }

    public void setdAddress(String dAddress) {
        this.dAddress = dAddress;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Integer getParcelQuantity() {
        return parcelQuantity;
    }

    public void setParcelQuantity(Integer parcelQuantity) {
        this.parcelQuantity = parcelQuantity;
    }

    public Integer getIsCod() {
        return isCod;
    }

    public void setIsCod(Integer isCod) {
        this.isCod = isCod;
    }

    public String getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(String codAmount) {
        this.codAmount = codAmount;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getPayCustid() {
        return payCustid;
    }

    public void setPayCustid(String payCustid) {
        this.payCustid = payCustid;
    }

    public String getCheckword() {
        return checkword;
    }

    public void setCheckword(String checkword) {
        this.checkword = checkword;
    }

    public String getjCityid() {
        return jCityid;
    }

    public void setjCityid(String jCityid) {
        this.jCityid = jCityid;
    }

    public String getdCityid() {
        return dCityid;
    }

    public void setdCityid(String dCityid) {
        this.dCityid = dCityid;
    }

    public String getFilter3() {
        return filter3;
    }

    public void setFilter3(String filter3) {
        this.filter3 = filter3;
    }
}
