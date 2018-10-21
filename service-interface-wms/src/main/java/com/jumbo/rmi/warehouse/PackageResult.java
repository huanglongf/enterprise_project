package com.jumbo.rmi.warehouse;

import java.io.Serializable;

/**
 * 退货申请 异常包裹接口
 * 
 * @author xiaolong.fei
 * 
 */
public class PackageResult implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -227721634166237787L;

    /**
     * wms异常包裹编码 唯一
     */
    private String wmsBillCode;

    /**
     * 反馈结果 1：已创建 2：拒收 3：无法确认
     */
    private Integer status;

    /**
     * 已创建必须反馈新的单据号给WMS
     */
    private String slipCode;

    /**
     * 备注
     */
    private String remark;

    public String getWmsBillCode() {
        return wmsBillCode;
    }

    public void setWmsBillCode(String wmsBillCode) {
        this.wmsBillCode = wmsBillCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
