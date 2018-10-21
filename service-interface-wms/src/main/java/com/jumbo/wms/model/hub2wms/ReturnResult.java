package com.jumbo.wms.model.hub2wms;

import com.jumbo.wms.model.BaseModel;

public class ReturnResult extends BaseModel {
    private static final long serialVersionUID = 5638348890132380142L;
    /**
     * 接收状态： 0失败，1成功
     */
    private int status;
    /**
     * 备注信息
     */
    private String memo;
    /**
     * 错误信息
     */
    private String errorMsg;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
