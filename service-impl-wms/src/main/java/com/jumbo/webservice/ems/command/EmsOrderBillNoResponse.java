package com.jumbo.webservice.ems.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class EmsOrderBillNoResponse {
    public static final String RESULT_SUCCESS = "1";
    public static final String RESULT_FAILED = "0";
    /**
     * //是否执行成功，1是成功，0是失败
     */
    @XmlElement(required = true, name = "result")
    private String result = "";

    @XmlElement(required = true, name = "errorDesc")
    private String errorDesc = "";

    @XmlElement(required = true, name = "assignIds")
    private EmsOrderBillNoResponseAssignIds assignIds;

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

    public EmsOrderBillNoResponseAssignIds getAssignIds() {
        return assignIds;
    }

    public void setAssignIds(EmsOrderBillNoResponseAssignIds assignIds) {
        this.assignIds = assignIds;
    }

    public Boolean getIsSuccess() {
        if (RESULT_SUCCESS.equals(result)) {
            return true;
        } else {
            return false;
        }
    }
}
