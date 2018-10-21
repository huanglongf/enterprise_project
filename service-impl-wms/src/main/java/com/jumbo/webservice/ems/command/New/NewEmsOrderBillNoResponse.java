package com.jumbo.webservice.ems.command.New;

import java.util.List;

public class NewEmsOrderBillNoResponse {
    // private NewEmsOrderBillNoItem response;
    private String success;
    private String errorMsg;
    private String errorCode;
    private List<String> mailnums;
    private String requestno;
    private int count;


    public String getRequestno() {
        return requestno;
    }

    public void setRequestno(String requestno) {
        this.requestno = requestno;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getMailnums() {
        return mailnums;
    }

    public void setMailnums(List<String> mailnums) {
        this.mailnums = mailnums;
    }

    // public NewEmsOrderBillNoItem getResponse() {
    // return response;
    // }
    //
    // public void setResponse(NewEmsOrderBillNoItem response) {
    // this.response = response;
    // }

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
