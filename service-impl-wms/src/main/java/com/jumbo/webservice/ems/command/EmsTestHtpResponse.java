package com.jumbo.webservice.ems.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class EmsTestHtpResponse {
    public static final String RESULT_SUCCESS = "1";
    public static final String RESULT_FAILED = "0";
    /**
     * //是否执行成功，1是成功，0是失败
     */
    @XmlElement(name = "result")
    private String result = "";

    @XmlElement(name = "errorDesc")
    private String errorDesc = "";

    @XmlElement(name = "sysAccount")
    private String sysAccount = "";

    @XmlElement(name = "bigAccountDataId")
    private String bigAccountDataId = "";

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

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

    public Boolean getIsSuccess() {
        if (RESULT_SUCCESS.equals(result)) {
            return true;
        } else {
            return false;
        }
    }
}
