package com.jumbo.wms.model.pda;



/**
 * 
 * @author lizaibiao
 * 
 */
public class PdaLocationTypeCommand extends PdaLocationType {

    /**
     * 
     */
    private static final long serialVersionUID = 2863259775416970890L;

    private String userName;// 用户名

    private String locationCode;// 库位编码



    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
