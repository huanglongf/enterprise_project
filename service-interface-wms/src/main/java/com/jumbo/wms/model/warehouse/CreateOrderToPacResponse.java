package com.jumbo.wms.model.warehouse;

import java.util.Date;

import com.jumbo.wms.model.BaseModel;

/**
 * 过仓成功通知PAC反馈
 * 
 */
public class CreateOrderToPacResponse extends BaseModel {


    /**
     * 
     */
    private static final long serialVersionUID = 4638386774657422843L;

    /**
     * 相关单据号
     */
    private String slipCode;


    /**
     * 反馈信息
     */
    private String result;

    /**
     * 创建时间
     */
    private Date createTime;


    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



}
