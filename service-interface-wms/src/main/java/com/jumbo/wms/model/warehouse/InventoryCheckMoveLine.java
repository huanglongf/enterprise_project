package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
import java.util.Date;

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
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.Sku;

/**
 * 盘点差异 调整记录
 * 
 * @author sjk
 * 
 */

@Entity
@Table(name = "T_WH_INV_CHECK_MOVE_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class InventoryCheckMoveLine extends BaseModel {

    private static final long serialVersionUID = -8155750340990043182L;
    /**
     * Pk
     */
    private Long id;
    /**
     * version
     */
    private Date version;
    /**
     * 相关Sku
     */
    private Sku sku;
    /**
     * 差异量
     */
    private Long quantity;
    /**
     * 相关库位
     */
    private WarehouseLocation location;
    /**
     * 相关库存状态
     */
    private InventoryStatus status;
    /**
     * 店铺 innser shop code
     */
    private String owner;
    /**
     * 库存盘点批
     */
    private InventoryCheck inventoryCheck;
    /**
     * 生产日期
     */
    private Date productionDate;
    /**
     * 商品成本
     */
    private BigDecimal skuCost;
    /**
     * 存放模式
     */
    private InboundStoreMode storeMode;
    /**
     * 过期时间
     */
    private Date expireDate;

    /**
     * 相关调整单
     */
    private StockTransApplication sta;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PKLT", sequenceName = "S_T_WH_INV_CHECK_DIF_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PKLT")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_INV_CK_DIF_LINE_SKU")
    public Sku getSku() {
        return sku;
    }

    public Date getVersion() {
        return version;
    }

    @Version
    @Column(name = "VERSION")
    public void setVersion(Date version) {
        this.version = version;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @Column(name = "QUANTITY")
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    @Index(name = "IDX_INV_CK_DIF_LINE_WHL")
    public WarehouseLocation getLocation() {
        return location;
    }

    public void setLocation(WarehouseLocation location) {
        this.location = location;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_ID")
    @Index(name = "IDX_INV_CK_DIF_LINE_INVS")
    public InventoryStatus getStatus() {
        return status;
    }

    public void setStatus(InventoryStatus status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INV_CHECK_ID")
    @Index(name = "IDX_INV_CK_DIF_ID")
    public InventoryCheck getInventoryCheck() {
        return inventoryCheck;
    }

    public void setInventoryCheck(InventoryCheck inventoryCheck) {
        this.inventoryCheck = inventoryCheck;
    }

    @Column(name = "OWNER", length = 100)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    @Index(name = "IDX_INV_CK_STA_ID")
    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    @Column(name = "PRODUCTION_DATE")
    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    @Column(name = "EXPIRE_DATE")
    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    @Column(name = "SKU_COST", precision = 22, scale = 8)
    public BigDecimal getSkuCost() {
        return skuCost;
    }

    public void setSkuCost(BigDecimal skuCost) {
        this.skuCost = skuCost;
    }

    @Column(name = "STORE_MODE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.InboundStoreMode")})
    public InboundStoreMode getStoreMode() {
        return storeMode;
    }

    public void setStoreMode(InboundStoreMode storeMode) {
        this.storeMode = storeMode;
    }
}
