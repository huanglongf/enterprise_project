package com.jumbo.webservice.ems.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.jumbo.webservice.ems.EmsServiceClient;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "XMLInfo")
public class EmsOrderPostInfo {
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
     * 打印类型
     */
    @XmlElement(required = true, name = "printKind")
    private String printKind = EmsServiceClient.PRINT_KIND_RM;
    /**
     * 订单数据
     */
    @XmlElement(required = true, name = "printDatas")
    private EmsOrderDatas printDatas;

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

    public String getPrintKind() {
        return printKind;
    }

    public void setPrintKind(String printKind) {
        this.printKind = printKind;
    }

    public EmsOrderDatas getPrintDatas() {
        return printDatas;
    }

    public void setPrintDatas(EmsOrderDatas printDatas) {
        this.printDatas = printDatas;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

}
