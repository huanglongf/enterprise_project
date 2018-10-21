package com.jumbo.rmi.warehouse;

import com.jumbo.wms.model.BaseModel;

/**
 * 特定地址发货仓及优先级
 * 
 * @author jinlong.ke
 * 
 */
public class WarehousePriority extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -7610461800641243503L;
    /*
     * 优先级
     */
    private int priority;
    /*
     * 仓库编码
     */
    private String whCode;
    /*
     * 仓库组织ID
     */
    private Long ouId;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

}
