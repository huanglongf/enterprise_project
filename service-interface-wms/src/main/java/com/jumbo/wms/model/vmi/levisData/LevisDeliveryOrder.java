package com.jumbo.wms.model.vmi.levisData;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "t_levis_delivery_order")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class LevisDeliveryOrder extends BaseModel {

    private static final long serialVersionUID = -6990198311430321729L;

    private Long id;
    private Date version;
    private String productCode;
    private String inseamCode;
    private String sizeCode;
    private String quantity;
    private String orderCode;
    /**
     * sta slip_code
     */
    private String poCode;
    /**
     * shop vim code
     */
    private String storeCode1;
    private String storeCode2;
    private Long staId;
    private Long staLineId;
    private Long shopId;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_LVS_DO", sequenceName = "s_t_levis_delivery_order", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LVS_DO")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Column(name = "PRODUCT_CODE", length = 50)
    public String getProductCode() {
        return productCode.trim();
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode.trim();
    }

    @Column(name = "INSEAM_CODE", length = 50)
    public String getInseamCode() {
        return inseamCode;
    }

    public void setInseamCode(String inseamCode) {
        this.inseamCode = inseamCode;
    }

    @Column(name = "SIZE_CODE", length = 20)
    public String getSizeCode() {
        return sizeCode;
    }

    public void setSizeCode(String sizeCode) {
        this.sizeCode = sizeCode;
    }

    @Column(name = "QUANTITY", length = 10)
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Column(name = "ORDER_CODE", length = 50)
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "PO_CODE", length = 50)
    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "STA_LINE_ID")
    public Long getStaLineId() {
        return staLineId;
    }

    public void setStaLineId(Long staLineId) {
        this.staLineId = staLineId;
    }

    @Column(name = "STORE_CODE1", length = 50)
    public String getStoreCode1() {
        return storeCode1;
    }

    public void setStoreCode1(String storeCode1) {
        this.storeCode1 = storeCode1;
    }

    @Column(name = "STORE_CODE2", length = 50)
    public String getStoreCode2() {
        return storeCode2;
    }

    public void setStoreCode2(String storeCode2) {
        this.storeCode2 = storeCode2;
    }

    @Column(name = "SHOP_ID")
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

}
