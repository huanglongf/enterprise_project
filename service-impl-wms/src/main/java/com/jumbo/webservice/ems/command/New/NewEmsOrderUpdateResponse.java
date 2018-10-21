package com.jumbo.webservice.ems.command.New;

import java.util.ArrayList;
import java.util.List;



public class NewEmsOrderUpdateResponse {
    private List<ResponseWaybill> responseWaybills = new ArrayList<ResponseWaybill>();
    private String success;
    private String errorMsg;
    private String errorCode;

    public List<ResponseWaybill> getResponseWaybills() {
        return responseWaybills;
    }

    public void setResponseWaybills(List<ResponseWaybill> responseWaybills) {
        this.responseWaybills = responseWaybills;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
