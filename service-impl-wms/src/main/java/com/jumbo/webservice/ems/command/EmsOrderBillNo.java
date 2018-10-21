package com.jumbo.webservice.ems.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.jumbo.webservice.ems.EmsServiceClient;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "XMLInfo")
public class EmsOrderBillNo {
    /**
     * 大客户号
     */
    @XmlElement(required = true, name = "sysAccount")
    private String sysAccount = "";
    /**
     * 大客户密码
     */
    @XmlElement(required = true, name = "passWord")
    private String passWord = "";
    /**
     * 业务类型，1为标准快递，4为经济快递
     */
    @XmlElement(required = true, name = "businessType")
    private String businessType = EmsServiceClient.BUSINESS_TYPE_NORMAL;

    /**
     * 需要详情单数量，最多输入100个
     */
    @XmlElement(required = true, name = "billNoAmount")
    private String billNoAmount = "";

    /**
     * 对接授权码 ,接口调用方的身份凭据，由接口提供方提供
     */
    @XmlElement(required = false, name = "appKey")
    private String appKey = "";

    public String getSysAccount() {
        return sysAccount;
    }

    public void setSysAccount(String sysAccount) {
        this.sysAccount = sysAccount;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBillNoAmount() {
        return billNoAmount;
    }

    public void setBillNoAmount(String billNoAmount) {
        this.billNoAmount = billNoAmount;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

}
