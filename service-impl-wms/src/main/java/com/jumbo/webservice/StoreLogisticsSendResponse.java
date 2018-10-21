package com.jumbo.webservice;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jumbo.wms.model.BaseModel;

/**
 * @author jinlong.ke
 * @date 2016年7月14日下午4:02:49
 * 
 */
public class StoreLogisticsSendResponse extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -3310186461356882945L;
    @JsonProperty(value = "IsSuccess")
    private String isSuccess;
    @JsonProperty(value = "PlatformMessage")
    private String platformMessage;
    @JsonProperty(value = "Lines")
    private List<ErrLines> lines;
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

    public String getPlatformMessage() {
        return platformMessage;
    }

    public void setPlatformMessage(String platformMessage) {
        this.platformMessage = platformMessage;
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

    public List<ErrLines> getLines() {
        return lines;
    }

    public void setLines(List<ErrLines> lines) {
        this.lines = lines;
    }

}
