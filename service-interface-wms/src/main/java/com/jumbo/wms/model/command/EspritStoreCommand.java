package com.jumbo.wms.model.command;

import com.jumbo.wms.model.warehouse.EspritStore;

public class EspritStoreCommand extends  EspritStore{

    /**
     * 
     */
    private static final long serialVersionUID = 2367523272871895978L;

    private String userName;

    private String type;// 1：新增 2：修改

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
