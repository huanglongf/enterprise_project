package com.jumbo.wms.model.command.pickZone;

import com.jumbo.wms.model.pickZone.WhPickZoon;


/**
 * The persistent class for the T_WH_PICK_ZOON database table.
 * 
 */

public class WhPickZoonCommand extends WhPickZoon {

    /**
     * 
     */
    private static final long serialVersionUID = -4473034587339448179L;

    private Long whZoonId;
    private String whZoonCode;
    private String whZoonName;

    public Long getWhZoonId() {
        return whZoonId;
    }

    public void setWhZoonId(Long whZoonId) {
        this.whZoonId = whZoonId;
    }

    public String getWhZoonCode() {
        return whZoonCode;
    }

    public void setWhZoonCode(String whZoonCode) {
        this.whZoonCode = whZoonCode;
    }

    public String getWhZoonName() {
        return whZoonName;
    }

    public void setWhZoonName(String whZoonName) {
        this.whZoonName = whZoonName;
    }


   
}
