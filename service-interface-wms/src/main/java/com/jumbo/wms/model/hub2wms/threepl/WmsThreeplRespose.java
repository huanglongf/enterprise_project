package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;

/**
 * wms反馈
 * 
 * @author xiaolong.fei
 * 
 */
public class WmsThreeplRespose implements Serializable {

    private static final long serialVersionUID = -885838740076454319L;
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
