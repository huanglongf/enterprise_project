package com.jumbo.wms.model.vmi.reebokData;

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
 * re 全量库 存记录表
 * 
 * @author lizaibiao
 * 
 */

@Entity
@Table(name = "T_REEBOK_TOTAL_INVENTORY")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ReebokTotalInventory extends BaseModel {

    private static final long serialVersionUID = -4095850881943717110L;

    private Long id;// 主键

    private String inventoryProperty;// 库存属性

    private String skuCode;// sku_Code

    private String skuBarCode;// SKU_BARCODE

    private Long quantity;// 库存数量

    private Date createTime;// 创建时间

    private Date finishTime;// 完成时间

    private Integer status;// 状态 0：不启用 1：启用

    private int version;// 版本

    private String whCode;// 仓库编码

    private String memo;// 备注

    @Column(name = "INVENTORY_PROPERTY")
    public String getInventoryProperty() {
        return inventoryProperty;
    }



    public void setInventoryProperty(String inventoryProperty) {
        this.inventoryProperty = inventoryProperty;
    }


    @Column(name = "SKU_CODE")
    public String getSkuCode() {
        return skuCode;
    }



    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
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
    @SequenceGenerator(name = "SEQ_WHL", sequenceName = "S_T_REEBOK_TOTAL_INVENTORY", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WHL")
    public Long getId() {
        return id;
    }


}
