package com.jumbo.wms.model.warehouse;


/**
 * SF云仓可配地址维护command
 * 
 * @author lizaibiao
 * @date 2016年10月24日下午8:54:36
 * 
 */

public class SfCloudWarehouseConfigCommand extends SfCloudWarehouseConfig {

    private static final long serialVersionUID = -8998662942050082414L;

    private String opName;// 仓库名称

    private String userName;// 修改人

    private String timeTypeString;// 时效类型
    
    public String getTimeTypeString() {
        return timeTypeString;
    }

    public void setTimeTypeString(String timeTypeString) {
        this.timeTypeString = timeTypeString;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }




}
