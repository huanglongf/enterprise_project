package com.jumbo.wms.model.command;

import com.jumbo.wms.model.warehouse.StaSpecialExecute;

/**
 * 作业单特殊处理信息
 * 
 * @author dly
 * 
 */
public class StaSpecialExecutedCommand extends StaSpecialExecute {


    private static final long serialVersionUID = 442523025234408370L;

    private Integer intType;

    private Long staId;

    public Integer getIntType() {
        return intType;
    }

    public void setIntType(Integer intType) {
        this.intType = intType;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }
}
