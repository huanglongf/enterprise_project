package com.jumbo.wms.model.hub2wms.threepl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 菜鸟入库通知--装箱明细
 */
@Entity
@Table(name = "T_WH_CN_STOCK_IN_CASE_ITEM")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CnWmsStockInCaseItem extends BaseModel {

    private static final long serialVersionUID = 7542047787345658485L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 商品ID
     */
    private String itemId;
    /**
     * 菜鸟装箱信息
     */
    private CnWmsStockInCaseInfo stockInCaseInfo;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 商家对商品的编码
     */
    private String itemCode;
    /**
     * 条形码，多条码请用”;”分隔；仓库入库扫码使用
     */
    private String barCode;
    /**
     * 库存类型（1 可销售库存(正品) 101 残次 102 机损 103 箱损301 在途库存 201 冻结库存） 注意：采购入库单下发的库存类型是301
     */
    private Integer inventoryType;
    /**
     * 商品数量
     */
    private Integer itemQuantity;
    /**
     * 商品版本
     */
    private Integer itemVersion;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WCSI", sequenceName = "S_T_WH_CN_STOCK_IN_CASE_ITEM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WCSI")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ITEM_ID")
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCK_IN_CASE_INFO_ID")
    public CnWmsStockInCaseInfo getStockInCaseInfo() {
        return stockInCaseInfo;
    }

    public void setStockInCaseInfo(CnWmsStockInCaseInfo stockInCaseInfo) {
        this.stockInCaseInfo = stockInCaseInfo;
    }

    @Column(name = "ITEM_NAME")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Column(name = "ITEM_CODE")
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Column(name = "BAR_CODE")
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Column(name = "INVENTORY_TYPE")
    public Integer getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(Integer inventoryType) {
        this.inventoryType = inventoryType;
    }

    @Column(name = "ITEM_QUANTITY")
    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @Column(name = "ITEM_VERSION")
    public Integer getItemVersion() {
        return itemVersion;
    }

    public void setItemVersion(Integer itemVersion) {
        this.itemVersion = itemVersion;
    }
}
