package com.jumbo.webservice.biaogan.command;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Response")
public class InvoicesResponseCommand implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4494183657864009135L;

    @XmlElement(required = true, name = "success")
    private Boolean isSuccess;

    @XmlElement(name = "code")
    private String code;
    @XmlElement(name = "desc")
    private String desc;

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
