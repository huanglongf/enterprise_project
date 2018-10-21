package com.jumbo.wms.model.mongodb;

import java.util.Date;
import java.util.List;

import com.jumbo.wms.model.BaseModel;

public class AsnShelves extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 4217689356133193000L;

    private Long id;//
    private Long cartonId;// 箱id
    private String cartonCode;// 箱号
    private String orderCode;// 单据号
    private String slipCode;// 相关单据号
    private Date createTime;// 创建时间
    private Long ouId;// 仓库id
    private Long staId;// 作业单Id
    private Long userId;// 操作人id
    private Long invStatusId;// 库存状态
    private Long invStatus;// 0:残次 1：良品


    public Long getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(Long invStatus) {
        this.invStatus = invStatus;
    }

    public Long getCartonId() {
        return cartonId;
    }

    public void setCartonId(Long cartonId) {
        this.cartonId = cartonId;
    }

    public Long getInvStatusId() {
        return invStatusId;
    }

    public void setInvStatusId(Long invStatusId) {
        this.invStatusId = invStatusId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    private List<ShelvesCartonLine> cartonLines;

    public String getCartonCode() {
        return cartonCode;
    }

    public void setCartonCode(String cartonCode) {
        this.cartonCode = cartonCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<ShelvesCartonLine> getCartonLines() {
        return cartonLines;
    }

    public void setCartonLines(List<ShelvesCartonLine> cartonLines) {
        this.cartonLines = cartonLines;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
