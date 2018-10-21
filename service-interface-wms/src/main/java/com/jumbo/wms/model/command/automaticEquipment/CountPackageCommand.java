package com.jumbo.wms.model.command.automaticEquipment;

import com.jumbo.wms.model.BaseModel;


/**
 * @author hui.li
 *
 *         统计创建交接的包裹数量
 */
public class CountPackageCommand extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -3919603875216943257L;

    private Integer quantity;

    private String lpcode;

    private String lpName;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    public String getLpName() {
        return lpName;
    }

    public void setLpName(String lpName) {
        this.lpName = lpName;
    }


}
