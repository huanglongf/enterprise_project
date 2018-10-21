package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.List;

public class StockOutOrderItem implements Serializable {


    private static final long serialVersionUID = 7248008526865462043L;

    /**
     * 出库单明细ID
     */
    private String orderItemId;
    /**
     * 商品属性列表
     */
    private List<StockOutItemInventory> items;

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public List<StockOutItemInventory> getItems() {
        return items;
    }

    public void setItems(List<StockOutItemInventory> items) {
        this.items = items;
    }

}
