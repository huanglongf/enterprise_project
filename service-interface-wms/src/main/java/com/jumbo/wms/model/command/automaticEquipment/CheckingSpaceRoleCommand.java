package com.jumbo.wms.model.command.automaticEquipment;

import com.jumbo.wms.model.automaticEquipment.CheckingSpaceRole;

/**
 * 
 * @author xiaolong.fei
 * 
 */
public class CheckingSpaceRoleCommand extends CheckingSpaceRole {

    /**
     * 
     */
    private static final long serialVersionUID = -8909589944846844462L;
    /**
     * 是否QS
     */
    private String isQsStr;
    /**
     * 是否特殊处理
     */
    private String isSpecStr;
    /**
     * 店铺名称
     */
    private String ownerName;

    /**
     * OTO门店
     */
    private String toLocationName;

    /**
     * 时效类型 String
     * 
     * @return
     */
    private String transTimeTypeStr;

    private String isPreSaleStr;

    public String getTransTimeTypeStr() {
        return transTimeTypeStr;
    }

    public void setTransTimeTypeStr(String transTimeTypeStr) {
        this.transTimeTypeStr = transTimeTypeStr;
    }

    public String getIsQsStr() {
        return isQsStr;
    }

    public void setIsQsStr(String isQsStr) {
        this.isQsStr = isQsStr;
    }

    public String getIsSpecStr() {
        return isSpecStr;
    }

    public void setIsSpecStr(String isSpecStr) {
        this.isSpecStr = isSpecStr;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getToLocationName() {
        return toLocationName;
    }

    public void setToLocationName(String toLocationName) {
        this.toLocationName = toLocationName;
    }

    public String getIsPreSaleStr() {
        return isPreSaleStr;
    }

    public void setIsPreSaleStr(String isPreSaleStr) {
        this.isPreSaleStr = isPreSaleStr;
    }


}
