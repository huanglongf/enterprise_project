package com.jumbo.wms.model.command.authorization;

import com.jumbo.wms.model.authorization.UserRole;

public class UserRoleCmd extends UserRole {

    private static final long serialVersionUID = 4044133038247224634L;

    private Long userid;
    private Long roleid;
    private String roleName;
    private Long ouid;
    private String ouName;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getOuid() {
        return ouid;
    }

    public void setOuid(Long ouid) {
        this.ouid = ouid;
    }

    public String getOuName() {
        return ouName;
    }

    public void setOuName(String ouName) {
        this.ouName = ouName;
    }


}
