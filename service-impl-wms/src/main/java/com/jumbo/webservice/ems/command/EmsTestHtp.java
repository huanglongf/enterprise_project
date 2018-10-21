package com.jumbo.webservice.ems.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "XMLInfo")
public class EmsTestHtp {

    /**
     * 大客户号，必填
     */
    @XmlElement(required = true, name = "sysAccount")
    private String sysAccount = "";
    /**
     * 大客户数据的唯一标识，如某电商公司的配货单号，必填
     */
    @XmlElement(required = true, name = "bigAccountDataId")
    private String bigAccountDataId = "";

    public String getSysAccount() {
        return sysAccount;
    }

    public void setSysAccount(String sysAccount) {
        this.sysAccount = sysAccount;
    }

    public String getBigAccountDataId() {
        return bigAccountDataId;
    }

    public void setBigAccountDataId(String bigAccountDataId) {
        this.bigAccountDataId = bigAccountDataId;
    }
}
