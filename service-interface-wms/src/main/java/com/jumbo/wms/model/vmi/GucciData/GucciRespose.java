package com.jumbo.wms.model.vmi.GucciData;

import java.io.Serializable;

/**
 * wms反馈
 * 
 * @author xiaolong.fei
 * 
 */
public class GucciRespose implements Serializable {

    private static final long serialVersionUID = 1087364563338231306L;
    /**
     * 是否成功
     */
    private Boolean success;
    /**
     * 错误码
     */
    private String errorCode;
    /**
     * 错误信息
     */
    private String errorMsg;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
