package com.jumbo.webservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jumbo.wms.model.BaseModel;

/**
 * @author jinlong.ke
 * @date 2016年7月14日下午4:02:49
 * 
 */
public class StoreExchangeConfirmResponse extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -3310186461356882945L;
    @JsonProperty(value = "IsSuccess")
    private String isSuccess;
    @JsonProperty(value = "ErrCode")
    private String errCode;
    @JsonProperty(value = "ErrMsg")
    private String errMsg;

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }



    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }


}
