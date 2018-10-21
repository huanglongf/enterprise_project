package com.jumbo.webservice.ems.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "XMLInfo")
public class EmsOrderUpdate {

    @XmlElement(name = "sysAccount", required = true)
    private String sysAccount = "";
    @XmlElement(name = "passWord", required = true)
    private String passWord = "";
    @XmlElement(name = "billNo", required = true)
    private String billNo = "";
    @XmlElement(name = "weight", required = true)
    private String weight = "";

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

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

}
