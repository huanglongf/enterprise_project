package com.jumbo.wms.model.warehouse;

public class WhReplenishCommand extends WhReplenish {

    /**
     * 
     */
    private static final long serialVersionUID = -8664078767079548955L;
    /**
     * 整型状态
     */
    private Integer intStatus;
    /**
     * 整型类型
     */
    private Integer intType;

    public Integer getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(Integer intStatus) {
        this.intStatus = intStatus;
    }

    public Integer getIntType() {
        return intType;
    }

    public void setIntType(Integer intType) {
        this.intType = intType;
    }

    public String getStringStatus() {
        return this.getIntStatus() == 1 ? "新建" : this.getIntStatus() == 10 ? "已完成" : this.getIntStatus() == 3 ? "进行中" : "取消";
    }

    public String getStringType() {
        return this.getIntType() == 1 ? "库内补货" : "配货失败补货";
    }
}
