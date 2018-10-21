package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.List;

public class WmsInventoryCount implements Serializable {

    private static final long serialVersionUID = 2913028041948199293L;
    /**
     * 仓库编码
     */
    private String storeCode;
    /**
     * 订单类型：701 盘点出库（盘亏） 702 盘点入库（盘盈）
     */
    private Integer orderType;
    /**
     * 货主ID
     */
    private String ownerUserId;
    /**
     * 外部业务编码，物流宝用来去重，需要每次确认都不一样，同一次确认重试需要一样
     */
    private String outBizCode;
    /**
     * 差异单orderCode 无差异单业务不填 差异单：主要是为了先锁定库存
     */
    private String imbalanceOrderCode;
    /**
     * 库存差异调整原因类型: WMS_GOODS_OVER_FLOW CHECK 仓内多货 WMS_GOODS_LOCK CHECK 仓内少货 WMS_GOODS_OWNER_TRANSFER
     * ADJUST 货权转移 WMS_GOODS_DEFECT ADJUST 临保转残 WMS_GOODS_DAMAGED ADJUST 库内破损 WMS_GOODS_BATCH_ADJUST
     * ADJUST 批次调整
     * 
     * OTHER ADJUST 其它 主要是为了区分调整和盘点
     */
    private String adjustReasonType;
    /**
     * 调整主单号
     */
    private String adjustBizKey;
    /**
     * 盘点单商品信息列表
     */
    private List<InventoryCountReturnOrderItem> itemList;
    /**
     * 备注
     */
    private String remark;

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getOutBizCode() {
        return outBizCode;
    }

    public void setOutBizCode(String outBizCode) {
        this.outBizCode = outBizCode;
    }

    public String getImbalanceOrderCode() {
        return imbalanceOrderCode;
    }

    public void setImbalanceOrderCode(String imbalanceOrderCode) {
        this.imbalanceOrderCode = imbalanceOrderCode;
    }

    public String getAdjustReasonType() {
        return adjustReasonType;
    }

    public void setAdjustReasonType(String adjustReasonType) {
        this.adjustReasonType = adjustReasonType;
    }

    public String getAdjustBizKey() {
        return adjustBizKey;
    }

    public void setAdjustBizKey(String adjustBizKey) {
        this.adjustBizKey = adjustBizKey;
    }

    public List<InventoryCountReturnOrderItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<InventoryCountReturnOrderItem> itemList) {
        this.itemList = itemList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
