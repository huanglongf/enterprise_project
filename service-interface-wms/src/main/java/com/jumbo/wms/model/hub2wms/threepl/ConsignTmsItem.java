package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;

public class ConsignTmsItem implements Serializable {

    private static final long serialVersionUID = -9019464974931602280L;

    /**
     * 淘宝指定的包材型号
     */
    private String itemId;
    /**
     * 包材的数量
     */
    private Integer itemQuantity;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

}
