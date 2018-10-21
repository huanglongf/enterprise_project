package com.jumbo.wms.model.vmi.GucciData;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * Gucci每日同步可销售库存
 */
@Entity
@Table(name = "T_GUCCI_SALES_INVENTORY")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class GucciSalesInventory extends BaseModel {

    private static final long serialVersionUID = 6123458406391358826L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 商品upc
     */
    private String skuNumber;

    /**
     * 可销售数量
     */
    private Long invQty;

    /**
     * 库存时间
     */
    private Date invDate;

    /**
     * 库区库位
     */
    private String location;

    /**
     * JDA仓库编码
     */
    private String JDAWarehouseCode;

    /**
     * 品牌编码
     */
    private String brandCode;

    /**
     * 创建时间
     */
    private Date createTime;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_GUCCI_SALES_INVENTORY", sequenceName = "S_T_GUCCI_SALES_INVENTORY", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GUCCI_SALES_INVENTORY")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SKU_NUMBER")
    public String getSkuNumber() {
        return skuNumber;
    }

    public void setSkuNumber(String skuNumber) {
        this.skuNumber = skuNumber;
    }

    @Column(name = "INV_QTY")
    public Long getInvQty() {
        return invQty;
    }

    public void setInvQty(Long invQty) {
        this.invQty = invQty;
    }

    @Column(name = "INV_DATE")
    public Date getInvDate() {
        return invDate;
    }

    public void setInvDate(Date invDate) {
        this.invDate = invDate;
    }

    @Column(name = "LOCATION")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "JDA_WAREHOUSE_CODE")
    public String getJDAWarehouseCode() {
        return JDAWarehouseCode;
    }

    public void setJDAWarehouseCode(String jDAWarehouseCode) {
        JDAWarehouseCode = jDAWarehouseCode;
    }

    @Column(name = "BRAND_CODE")
    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
