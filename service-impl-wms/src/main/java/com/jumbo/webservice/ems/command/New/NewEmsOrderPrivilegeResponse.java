package com.jumbo.webservice.ems.command.New;


public class NewEmsOrderPrivilegeResponse {
    private String success;
    private String errorMsg;
    private String errorCode;
    private String customerCode;// 大客户号
    private String authorization;// 授权码
    private String expireTime;// 授权码(过期时间)
    private String flashToken;// 刷新码
    private String flashTokenExpireTime;// 刷新吗（过期时间）

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

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getFlashToken() {
        return flashToken;
    }

    public void setFlashToken(String flashToken) {
        this.flashToken = flashToken;
    }

    public String getFlashTokenExpireTime() {
        return flashTokenExpireTime;
    }

    public void setFlashTokenExpireTime(String flashTokenExpireTime) {
        this.flashTokenExpireTime = flashTokenExpireTime;
    }




}
