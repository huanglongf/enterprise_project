package com.jumbo.wms.web.commond;

import java.util.ArrayList;
import java.util.List;

import com.jumbo.wms.model.BaseModel;

public class OrderCheckLineCommand extends BaseModel implements Comparable<OrderCheckLineCommand> {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8560095306338844498L;

    // 行唯一ID
    private Long uniqueId;
    // 计划量
    private Long qty;
    // 执行量
    private Long cQty;
    // 商品
    private SkuCommand sku;
    // 是否是特殊处理
    private Integer isGift;
    // SN号列表，核对时用
    private List<String> sns = new ArrayList<String>();

    public Long getQty() {
        return qty;
    }

    public Integer getIsGift() {
        return isGift;
    }

    public void setIsGift(Integer isGift) {
        this.isGift = isGift;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Long getcQty() {
        return cQty;
    }

    public void setcQty(Long cQty) {
        this.cQty = cQty;
    }

    public SkuCommand getSku() {
        return sku;
    }

    public void setSku(SkuCommand sku) {
        this.sku = sku;
    }

    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public int compareTo(OrderCheckLineCommand o) {
        return this.getUniqueId().compareTo(o.getUniqueId());
    }

    public List<String> getSns() {
        return sns;
    }

    public void setSns(List<String> sns) {
        this.sns = sns;
    }
}
