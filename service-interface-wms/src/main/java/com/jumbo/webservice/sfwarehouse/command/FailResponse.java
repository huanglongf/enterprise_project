package com.jumbo.webservice.sfwarehouse.command;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author jinlong.ke
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "responseFail")
public class FailResponse implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1259914976067107920L;
    @XmlElement
    private Long reasoncode;
    @XmlElement
    private String remark;

    public Long getReasoncode() {
        return reasoncode;
    }

    public void setReasoncode(Long reasoncode) {
        this.reasoncode = reasoncode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
