package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;

public class ConsignPackageMaterial implements Serializable {

    private static final long serialVersionUID = -7589124428710398727L;

    /**
     * 淘宝指定的包材型号
     */
    private String materialType;
    /**
     * 包材的数量
     */
    private Integer materialQuantity;

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public Integer getMaterialQuantity() {
        return materialQuantity;
    }

    public void setMaterialQuantity(Integer materialQuantity) {
        this.materialQuantity = materialQuantity;
    }

}
