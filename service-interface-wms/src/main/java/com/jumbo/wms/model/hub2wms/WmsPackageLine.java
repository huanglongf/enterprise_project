package com.jumbo.wms.model.hub2wms;

import com.jumbo.wms.model.BaseModel;

/**
 * 异常包裹明细
 * 
 * @author xiaolong.fei
 * 
 */
public class WmsPackageLine extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * SKU对接编码
     */
    private String skuCode;
    /**
     * 数量
     */
    private Long qty;
    /**
     * 退回商品库存状态对接码
     */
    private String invStatus;
    /**
     * 备注
     */
    private String remark;
    ///////////////////////ad////////////////////////////////////
    private String wmsOrderType;//仓库工单类型
    //////////////////////////////////////////////////

    public String getWmsOrderType() {
        return wmsOrderType;
    }

    public void setWmsOrderType(String wmsOrderType) {
        this.wmsOrderType = wmsOrderType;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
