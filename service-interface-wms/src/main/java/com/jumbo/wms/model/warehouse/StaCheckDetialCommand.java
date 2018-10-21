package com.jumbo.wms.model.warehouse;

public class StaCheckDetialCommand extends StaCheckDetial{

    /**
     * 
     */
    private static final long serialVersionUID = 8408556218748063361L;

    private String barCode;


    private String strExpireDate;


    public String getStrExpireDate() {
        return strExpireDate;
    }

    public void setStrExpireDate(String strExpireDate) {
        this.strExpireDate = strExpireDate;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }


}
