package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.List;

public class ConsignOrderItem implements Serializable {

    private static final long serialVersionUID = 384717076904809847L;
    /**
     * 订单明细ID
     */
    private String orderItemId;
    /**
     * 商品库存列表
     */
    private List<ConsignItemInventory> items;

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public List<ConsignItemInventory> getItems() {
        return items;
    }

    public void setItems(List<ConsignItemInventory> items) {
        this.items = items;
    }


}
