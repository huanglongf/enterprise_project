package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.List;

public class WmsItem implements Serializable {

    private static final long serialVersionUID = -8064567353819038295L;
    /**
     * 货主ID
     */
    private Long providerTpId;
    /**
     * 菜鸟商品ID列表 最大50个
     */
    private List<Long> itemIds;

    public Long getProviderTpId() {
        return providerTpId;
    }

    public void setProviderTpId(Long providerTpId) {
        this.providerTpId = providerTpId;
    }

    public List<Long> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Long> itemIds) {
        this.itemIds = itemIds;
    }
}
