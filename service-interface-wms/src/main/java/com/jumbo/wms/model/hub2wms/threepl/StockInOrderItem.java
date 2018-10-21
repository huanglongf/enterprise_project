package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.List;


public class StockInOrderItem implements Serializable {

    private static final long serialVersionUID = -8806302183140824279L;
    /**
     * 入库单明细ID
     */
    private String orderItemId;
    /**
     * sku重量 单位克,采购入库单、普通入库单必传
     */
    private Long weight;
    /**
     * 体积 单位立方厘米 采购入库单、普通入库单必传
     */
    private Integer volume;
    /**
     * 长 单位 mm 采购入库单、普通入库单必传
     */
    private Integer length;
    /**
     * 宽 单位 mm 采购入库单、普通入库单必传
     */
    private Integer width;
    /**
     * 高 单位 mm 采购入库单、普通入库单必传
     */
    private Integer height;
    /**
     * 商品信息
     */
    private List<StockInItemInventory> items;

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<StockInItemInventory> getItems() {
        return items;
    }

    public void setItems(List<StockInItemInventory> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "StockInOrderItem [orderItemId=" + orderItemId + ", weight=" + weight + ", volume=" + volume + ", length=" + length + ", width=" + width + ", height=" + height + ", items=" + items + "]";
    }
}
