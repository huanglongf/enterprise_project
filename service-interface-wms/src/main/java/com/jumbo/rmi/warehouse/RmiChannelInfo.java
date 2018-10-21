package com.jumbo.rmi.warehouse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * RMI渠道信息
 * 
 * @author jumbo
 * 
 */
public class RmiChannelInfo implements Serializable {

    private static final long serialVersionUID = 4760121280864845915L;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 关联仓库
     */
    private List<RmiWarehouseInfo> whList = new ArrayList<RmiWarehouseInfo>();

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

    public List<RmiWarehouseInfo> getWhList() {
        return whList;
    }

    public void setWhList(List<RmiWarehouseInfo> whList) {
        this.whList = whList;
    }


}
