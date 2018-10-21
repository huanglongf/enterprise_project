package com.jumbo.webservice.ems.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.jumbo.webservice.ems.EmsServiceClient;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "XMLInfo")
public class EmsOrderInfo {

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
     * 打印类型，1为五联单打印，2为热敏打印，必填
     */
    @XmlElement(required = true, name = "printKind")
    private String printKind = EmsServiceClient.PRINT_KIND_RM;

    @XmlElement(required = true, name = "printDatas")
    private EmsOrderInfoPrintDatas printDatas = new EmsOrderInfoPrintDatas();

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

    public EmsOrderInfoPrintDatas getPrintDatas() {
        return printDatas;
    }

    public void setPrintDatas(EmsOrderInfoPrintDatas printDatas) {
        this.printDatas = printDatas;
    }
}
