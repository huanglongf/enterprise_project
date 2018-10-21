package com.jumbo.wms.model.warehouse;

import java.io.Serializable;

/**
 * 组织仓库信息
 * 
 * @author weiy
 * 
 */
public class WarehouseDataCommand implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2423661357488679961L;

    private String name;

    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
