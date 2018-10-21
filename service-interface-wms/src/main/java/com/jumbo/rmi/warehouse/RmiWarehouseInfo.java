package com.jumbo.rmi.warehouse;

import java.io.Serializable;

/**
 * 仓库信息
 * 
 * @author jumbo
 * 
 */
public class RmiWarehouseInfo implements Serializable {

    private static final long serialVersionUID = 7614888999837472327L;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
