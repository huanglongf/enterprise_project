package com.jumbo.wms.model.command.automaticEquipment;

import com.jumbo.wms.model.automaticEquipment.ShippingPoint;

/**
 * 
 * @author xiaolong.fei
 * 
 */
public class ShippingPointCommand extends ShippingPoint {
    private static final long serialVersionUID = -1907560327541348478L;

    /**
     * 创建人
     */
    private String createName;
    /**
     * 最后修改人
     */
    private String lastName;
    /**
     * 规则ID
     */
    private Long roleId;

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}
