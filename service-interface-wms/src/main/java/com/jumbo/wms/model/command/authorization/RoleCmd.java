package com.jumbo.wms.model.command.authorization;

import com.jumbo.wms.model.authorization.Role;

public class RoleCmd extends Role {

    private static final long serialVersionUID = 7254584678884536969L;

    private String ouTypeDisplayName;
    private Long ouTypeid;

    public String getOuTypeDisplayName() {
        return ouTypeDisplayName;
    }

    public void setOuTypeDisplayName(String ouTypeDisplayName) {
        this.ouTypeDisplayName = ouTypeDisplayName;
    }

    public Long getOuTypeid() {
        return ouTypeid;
    }

    public void setOuTypeid(Long ouTypeid) {
        this.ouTypeid = ouTypeid;
    }

}
