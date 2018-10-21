package com.jumbo.wms.model.lf;

public class ZdhPiciCommand extends ZdhPici {

    /**
     * 
     */
    private static final long serialVersionUID = 9152052440341985406L;

    private String userName;

    private String statusName;



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

}
