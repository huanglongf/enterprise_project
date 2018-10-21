package com.jumbo.webservice.kerry;

public class KerryOrderResponse {
    
    private Boolean isSuccess;  // 是否成功
    private String message;     // 消息
    private KerryOrderCreateResponse response;
    
    public Boolean getIsSuccess() {
        return isSuccess;
    }
    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public KerryOrderCreateResponse getResponse() {
        return response;
    }
    public void setResponse(KerryOrderCreateResponse response) {
        this.response = response;
    }
}
