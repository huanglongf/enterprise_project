package com.jumbo.wms.model.warehouse;

public class StockTransVoucherCommand extends StockTransVoucher {


    private static final long serialVersionUID = 1L;

    private Integer statusInt; // 状态
    private Integer directionInt; // 作业方向
    private Integer creatorId; // 创建人Id
    private Integer operatorId; // 操作人Id
    private Integer staId; // 作业单Id
    private Integer transactionTypeId; // 作业类型
    private Integer whId; // 仓库组织Id
    private Integer snType;
    private Integer isSnSku;
    private String staCode;

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public Integer getSnType() {
        return snType;
    }

    public void setSnType(Integer snType) {
        this.snType = snType;
    }

    public Integer getIsSnSku() {
        return isSnSku;
    }

    public void setIsSnSku(Integer isSnSku) {
        this.isSnSku = isSnSku;
    }

    public Integer getStatusInt() {
        return statusInt;
    }

    public void setStatusInt(Integer statusInt) {
        this.statusInt = statusInt;
    }

    public Integer getDirectionInt() {
        return directionInt;
    }

    public void setDirectionInt(Integer directionInt) {
        this.directionInt = directionInt;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getStaId() {
        return staId;
    }

    public void setStaId(Integer staId) {
        this.staId = staId;
    }

    public Integer getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Integer transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public Integer getWhId() {
        return whId;
    }

    public void setWhId(Integer whId) {
        this.whId = whId;
    }



}
