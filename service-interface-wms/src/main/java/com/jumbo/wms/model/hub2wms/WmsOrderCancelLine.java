package com.jumbo.wms.model.hub2wms;

import com.jumbo.wms.model.BaseModel;

public class WmsOrderCancelLine extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -8256381826724660609L;

    private String lineNo;

    private Integer status;

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
