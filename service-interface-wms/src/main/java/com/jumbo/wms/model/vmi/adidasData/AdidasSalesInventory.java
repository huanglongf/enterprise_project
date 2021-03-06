package com.jumbo.wms.model.vmi.adidasData;

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
 * ad销售库存表记录
 * 
 * @author lizaibiao
 * 
 */

@Entity
@Table(name = "T_ADIDAS_SALES_INVENTORY")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class AdidasSalesInventory extends BaseModel {

    private static final long serialVersionUID = -4095850881943717110L;

    private Long id;// 主键

    private String inventoryProperty;// 库存属性


    private String skuBarCode;// SKU_BARCODE

    private Long quantity;// 库存数量

    private Date createTime;// 创建时间

    private Date finishTime;// 完成时间

    private Date inventoryTime;// 库存时间

    private Integer status;// 状态 0：不启用 1：启用

    private int version;// 版本

    private String whCode;// 仓库编码

    private String memo;// 备注

    // /+++++
    private String brand;// adidas gnc
    private String isValid;// 是否是效期商品
    private String batchCode;// 批次号

    @Column(name = "BRAND")
    public String getBrand() {
        return brand;
    }



    public void setBrand(String brand) {
        this.brand = brand;
    }


    @Column(name = "IS_VALID")
    public String getIsValid() {
        return isValid;
    }



    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }



    @Column(name = "INVENTORY_PROPERTY")
    public String getInventoryProperty() {
        return inventoryProperty;
    }



    public void setInventoryProperty(String inventoryProperty) {
        this.inventoryProperty = inventoryProperty;
    }



    @Column(name = "SKU_BARCODE")
    public String getSkuBarCode() {
        return skuBarCode;
    }



    public void setSkuBarCode(String skuBarCode) {
        this.skuBarCode = skuBarCode;
    }


    @Column(name = "QUANTITY")
    public Long getQuantity() {
        return quantity;
    }



    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }


    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }



    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @Column(name = "FINISH_TIME")
    public Date getFinishTime() {
        return finishTime;
    }



    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }


    @Column(name = "INVENTORY_TIME")
    public Date getInventoryTime() {
        return inventoryTime;
    }



    public void setInventoryTime(Date inventoryTime) {
        this.inventoryTime = inventoryTime;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }



    public void setStatus(Integer status) {
        this.status = status;
    }



    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }



    public void setVersion(int version) {
        this.version = version;
    }


    @Column(name = "WH_CODE")
    public String getWhCode() {
        return whCode;
    }



    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }


    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }



    public void setMemo(String memo) {
        this.memo = memo;
    }



    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WHL", sequenceName = "S_T_ADIDAS_TOTAL_INVENTORY", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WHL")
    public Long getId() {
        return id;
    }


    @Column(name = "BATCH_CODE")
    public String getBatchCode() {
        return batchCode;
    }



    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }


}
