package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;

public class StockInCheckItem implements Serializable {
    private static final long serialVersionUID = 8861083296696637289L;
    /**
     * 入库单明细id
     */
    private String orderItemId;
    /**
     * 该商品实际入库总量
     */
    private long quantity;

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "StockInCheckItem [orderItemId=" + orderItemId + ", quantity=" + quantity + "]";
    }
}
