package com.jumbo.webservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jumbo.wms.model.BaseModel;

/**
 * @author jinlong.ke
 * @date 2016年7月14日下午5:17:32
 * 
 */
public class ErrLines extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 3862215131165944349L;
    @JsonProperty(value = "LineNo")
    private String lineNo;
    @JsonProperty(value = "Code")
    private String code;
    @JsonProperty(value = "Status")
    private String status;

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
