package com.jumbo.webservice.ems.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.jumbo.webservice.ems.EmsServiceClient;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "printData")
public class EmsOrderInfoPrintData {
    /**
     * 业务类型，1为标准快递，2为代收货款，3为收件人付费，4为经济快递
     */
    @XmlElement(required = true, name = "businessType")
    private String businessType = EmsServiceClient.BUSINESS_TYPE_NORMAL;
    /**
     * 时间类型，1为收寄时间 ；2为打印时间 ；可传空
     */
    @XmlElement(required = true, name = "dateType")
    private String dateType = "";
    /**
     * 收寄时间或打印时间，形式如“YYYY-MM-DD XX：XX”，精确到“时”，时间类型为空时，本值可为空也可进行传值。
     */
    @XmlElement(required = true, name = "procdate")
    private String procdate = "";
    /**
     * 寄件人姓名
     */
    @XmlElement(required = true, name = "scontactor")
    private String scontactor = "";
    /**
     * 寄件人联系方式1
     */
    @XmlElement(required = true, name = "scustMobile")
    private String scustMobile = "";
    /**
     * 寄件人联系方式2(选填)
     */
    @XmlElement(required = true, name = "scustTelplus")
    private String scustTelplus = "";
    /**
     * 寄件人邮编
     */
    @XmlElement(required = true, name = "scustPost")
    private String scustPost = "";
    /**
     * 寄件人地址
     */
    @XmlElement(required = true, name = "scustAddr")
    private String scustAddr = "";
    /**
     * 寄件人公司
     */
    @XmlElement(required = true, name = "scustComp")
    private String scustComp = "";
    /**
     * 收件人姓名
     */
    @XmlElement(required = true, name = "tcontactor")
    private String tcontactor = "";
    /**
     * 收件人联系方式1
     */
    @XmlElement(required = true, name = "tcustMobile")
    private String tcustMobile = "";
    /**
     * 收件人联系方式2(选填)
     */
    @XmlElement(required = true, name = "tcustTelplus")
    private String tcustTelplus = "";
    /**
     * 收件人邮编
     */
    @XmlElement(required = true, name = "tcustPost")
    private String tcustPost = "";
    /**
     * 收件人地址
     */
    @XmlElement(required = true, name = "tcustAddr")
    private String tcustAddr = "";
    /**
     * 收件人公司
     */
    @XmlElement(required = true, name = "tcustComp")
    private String tcustComp = "";
    /**
     * 到件省
     */
    @XmlElement(required = true, name = "tcustProvince")
    private String tcustProvince = "";
    /**
     * 到件市
     */
    @XmlElement(required = true, name = "tcustCity")
    private String tcustCity = "";
    /**
     * 到件县
     */
    @XmlElement(required = true, name = "tcustCounty")
    private String tcustCounty = "";
    /**
     * 寄件重量
     */
    @XmlElement(required = true, name = "weight")
    private String weight = "";
    /**
     * 物品长度
     */
    @XmlElement(required = true, name = "length")
    private String length = "";
    /**
     * 保价，每件最高投保金额以人民币5万元为限
     */
    @XmlElement(required = true, name = "insure")
    private String insure = "";
    /**
     * 小写金额，代收货款和收件人付费不保留小数点； 标准快递和经济快递保留两位小数点
     */
    @XmlElement(required = true, name = "fee")
    private String fee = "";
    /**
     * 大写金额（代收货款和收件人付费需要填写）
     */
    @XmlElement(required = true, name = "feeUppercase")
    private String feeUppercase = "";
    /**
     * 内件信息，根据货品的实际情况填写（对个别已与EMS和买家达成协议的，可只写货号，不写实际货物名称）
     */
    @XmlElement(required = true, name = "cargoDesc")
    private String cargoDesc = "宝尊商品";
    /**
     * 对揽投员的投递要求，填写客户的个性化投递要求
     */
    @XmlElement(required = true, name = "deliveryclaim")
    private String deliveryclaim = "";
    /**
     * 备注
     */
    @XmlElement(required = true, name = "remark")
    private String remark = "";
    /**
     * 大客户数据的唯一标识，如某电商公司的配货单号
     */
    @XmlElement(required = true, name = "bigAccountDataId")
    private String bigAccountDataId = "";
    /**
     * 大客户数据的客户订单号，主要是对于电商客户有用
     */
    @XmlElement(required = true, name = "customerDn")
    private String customerDn = "";
    /**
     * 付费类型,1-现金(支票)，2-记欠，3-托收，4-转帐，9-其他
     */
    @XmlElement(required = true, name = "payMode")
    private String payMode = "2";
    /**
     * 所负责任,2-保价，3-保险
     */
    @XmlElement(required = true, name = "insureType")
    private String insureType = "";
    /**
     * 预留字段1
     */
    @XmlElement(required = true, name = "blank1")
    private String blank1 = "";
    /**
     * 预留字段2
     */
    @XmlElement(required = true, name = "blank2")
    private String blank2 = "";
    /**
     * 预留字段3
     */
    @XmlElement(required = true, name = "blank3")
    private String blank3 = "";
    /**
     * 预留字段4
     */
    @XmlElement(required = true, name = "blank4")
    private String blank4 = "";
    /**
     * 预留字段5
     */
    @XmlElement(required = true, name = "blank5")
    private String blank5 = "";

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getProcdate() {
        return procdate;
    }

    public void setProcdate(String procdate) {
        this.procdate = procdate;
    }

    public String getScontactor() {
        return scontactor;
    }

    public void setScontactor(String scontactor) {
        this.scontactor = scontactor;
    }

    public String getScustMobile() {
        return scustMobile;
    }

    public void setScustMobile(String scustMobile) {
        this.scustMobile = scustMobile;
    }

    public String getScustTelplus() {
        return scustTelplus;
    }

    public void setScustTelplus(String scustTelplus) {
        this.scustTelplus = scustTelplus;
    }

    public String getScustPost() {
        return scustPost;
    }

    public void setScustPost(String scustPost) {
        this.scustPost = scustPost;
    }

    public String getScustAddr() {
        return scustAddr;
    }

    public void setScustAddr(String scustAddr) {
        this.scustAddr = scustAddr;
    }

    public String getScustComp() {
        return scustComp;
    }

    public void setScustComp(String scustComp) {
        this.scustComp = scustComp;
    }

    public String getTcontactor() {
        return tcontactor;
    }

    public void setTcontactor(String tcontactor) {
        this.tcontactor = tcontactor;
    }

    public String getTcustMobile() {
        return tcustMobile == null ? (tcustTelplus == null ? "" : tcustTelplus) : tcustMobile;
    }

    public void setTcustMobile(String tcustMobile) {
        this.tcustMobile = tcustMobile;
    }

    public String getTcustTelplus() {
        return tcustTelplus;
    }

    public void setTcustTelplus(String tcustTelplus) {
        this.tcustTelplus = tcustTelplus;
    }

    public String getTcustPost() {
        return tcustPost;
    }

    public void setTcustPost(String tcustPost) {
        this.tcustPost = tcustPost;
    }

    public String getTcustAddr() {
        return tcustAddr;
    }

    public void setTcustAddr(String tcustAddr) {
        this.tcustAddr = tcustAddr;
    }

    public String getTcustComp() {
        return tcustComp;
    }

    public void setTcustComp(String tcustComp) {
        this.tcustComp = tcustComp;
    }

    public String getTcustProvince() {
        return tcustProvince;
    }

    public void setTcustProvince(String tcustProvince) {
        this.tcustProvince = tcustProvince;
    }

    public String getTcustCity() {
        return tcustCity;
    }

    public void setTcustCity(String tcustCity) {
        this.tcustCity = tcustCity;
    }

    public String getTcustCounty() {
        return tcustCounty;
    }

    public void setTcustCounty(String tcustCounty) {
        this.tcustCounty = tcustCounty;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getInsure() {
        return insure;
    }

    public void setInsure(String insure) {
        this.insure = insure;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getFeeUppercase() {
        return feeUppercase;
    }

    public void setFeeUppercase(String feeUppercase) {
        this.feeUppercase = feeUppercase;
    }

    public String getCargoDesc() {
        return cargoDesc;
    }

    public void setCargoDesc(String cargoDesc) {
        this.cargoDesc = cargoDesc;
    }

    public String getDeliveryclaim() {
        return deliveryclaim;
    }

    public void setDeliveryclaim(String deliveryclaim) {
        this.deliveryclaim = deliveryclaim;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBigAccountDataId() {
        return bigAccountDataId;
    }

    public void setBigAccountDataId(String bigAccountDataId) {
        this.bigAccountDataId = bigAccountDataId;
    }

    public String getCustomerDn() {
        return customerDn;
    }

    public void setCustomerDn(String customerDn) {
        this.customerDn = customerDn;
    }

    public String getInsureType() {
        return insureType;
    }

    public void setInsureType(String insureType) {
        this.insureType = insureType;
    }

    public String getBlank1() {
        return blank1;
    }

    public void setBlank1(String blank1) {
        this.blank1 = blank1;
    }

    public String getBlank2() {
        return blank2;
    }

    public void setBlank2(String blank2) {
        this.blank2 = blank2;
    }

    public String getBlank3() {
        return blank3;
    }

    public void setBlank3(String blank3) {
        this.blank3 = blank3;
    }

    public String getBlank4() {
        return blank4;
    }

    public void setBlank4(String blank4) {
        this.blank4 = blank4;
    }

    public String getBlank5() {
        return blank5;
    }

    public void setBlank5(String blank5) {
        this.blank5 = blank5;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

}
