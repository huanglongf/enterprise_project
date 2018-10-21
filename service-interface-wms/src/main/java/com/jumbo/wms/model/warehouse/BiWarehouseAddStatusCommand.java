package com.jumbo.wms.model.warehouse;

import java.io.Serializable;

import com.jumbo.wms.model.authorization.OperationUnit;

public class BiWarehouseAddStatusCommand implements Serializable{
    
	private static final long serialVersionUID = 1802455331659243811L;

	private Long id;
    
    /**
     *类型
     *1、配货清单类 
     */
    private int type;
    
    /**
     * 状态
     * 21、待打印
     * 24、待拣货
     * 26、待分拣
     * 29、待核对
     */
    private Integer status;
    
    /**
     * 仓库信息外键
     */
    private OperationUnit operationUnit;
    
    /**
     * 顺序
     */
    private int sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public OperationUnit getOperationUnit() {
        return operationUnit;
    }

    public void setOperationUnit(OperationUnit operationUnit) {
        this.operationUnit = operationUnit;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
    
    
}
