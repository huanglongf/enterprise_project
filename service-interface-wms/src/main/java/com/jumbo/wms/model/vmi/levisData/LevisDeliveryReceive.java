package com.jumbo.wms.model.vmi.levisData;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import javax.persistence.Column;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_LEVIS_DELIVERY_RECEIVE")
public class LevisDeliveryReceive extends BaseModel {

    private static final long serialVersionUID = 2983628352611806657L;

    private Long id;
    private String storeCode1;
    private String storeCode2;
    private String productCode;
    private String inseamCode;
    private String sizeCode;
    private String quantity;
    private String orderCode;
    private String poCode;
    private Long staId;
    private String type;
    private String seqNo;
    private Long invCkId;
    private Long skuId;
    private Date createTime;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_LVS_DO", sequenceName = "S_T_LEVIS_DELIVERY_RECEIVE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LVS_DO")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "INV_CK_ID")
    public Long getInvCkId() {
        return invCkId;
    }

    public void setInvCkId(Long invCkId) {
        this.invCkId = invCkId;
    }

    @Column(name = "SEQ_NO")
    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "PO_CODE")
    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    @Column(name = "ORDER_CODE")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "QUANTITY")
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Column(name = "SIZE_CODE")
    public String getSizeCode() {
        return sizeCode;
    }

    public void setSizeCode(String sizeCode) {
        this.sizeCode = sizeCode;
    }

    @Column(name = "INSEAM_CODE")
    public String getInseamCode() {
        return inseamCode;
    }

    public void setInseamCode(String inseamCode) {
        this.inseamCode = inseamCode;
    }

    @Column(name = "PRODUCT_CODE")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Column(name = "STORE_CODE1")
    public String getStoreCode1() {
        return storeCode1;
    }

    public void setStoreCode1(String storeCode1) {
        this.storeCode1 = storeCode1;
    }

    @Column(name = "STORE_CODE2")
    public String getStoreCode2() {
        return storeCode2;
    }

    public void setStoreCode2(String storeCode2) {
        this.storeCode2 = storeCode2;
    }



}
