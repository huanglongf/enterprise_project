package com.jumbo.wms.model.hub2wms;

import com.jumbo.wms.model.BaseModel;

/**
 * 包裹取消结果
 * 
 * @author cheng.su
 * 
 */
public class WmsShippingResult extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4671244111667496697L;
    /**
     * 单据号，Wms单据唯一标识
     */
    private String shippingCode;
    /**
     * 状态编码
     */
    private String statusCode;
    /**
     * 备注
     */
    private String memo;

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }



}
