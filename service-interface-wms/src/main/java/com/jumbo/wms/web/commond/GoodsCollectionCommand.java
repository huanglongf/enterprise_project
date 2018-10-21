package com.jumbo.wms.web.commond;

import com.jumbo.wms.model.automaticEquipment.GoodsCollection;

public class GoodsCollectionCommand extends GoodsCollection {

    /**
     * 
     */
    private static final long serialVersionUID = -5998446386091182404L;


    private String statusName;

    private String userName;

    private String popUpAreaName;

    private String plCode;

    private String container;



    public String getPopUpAreaName() {
        return popUpAreaName;
    }

    public void setPopUpAreaName(String popUpAreaName) {
        this.popUpAreaName = popUpAreaName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPlCode() {
        return plCode;
    }

    public void setPlCode(String plCode) {
        this.plCode = plCode;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }


}
