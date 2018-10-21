package com.jumbo.wms.model.vmi.cch;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;

@Entity
@Table(name = "T_CACHE_sin_unnecessary")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class CacheSinUnnecessary implements Serializable {

    private static final long serialVersionUID = -4951331751527915624L;

    private Long id;

    private String parcelCode;

    private String storeCode;

    private String skuCode;

    private Long quantityShipped;

    private Date createTime;

    private String status;

    private StockTransApplication sta;

    private StaLine staLine;

    private Date version;

    private String vmiCode = "CCH";

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CACHE_SIN_UNNECESSARY", sequenceName = "S_T_CACHE_SIN_UNNECESSARY", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CACHE_SIN_UNNECESSARY")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "PARCEL_CODE", length = 20)
    public String getParcelCode() {
        return parcelCode;
    }

    public void setParcelCode(String parcelCode) {
        this.parcelCode = parcelCode;
    }

    @Column(name = "SKU_CODE", length = 20)
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "QUANTITY_SHIPPED")
    public Long getQuantityShipped() {
        return quantityShipped;
    }

    public void setQuantityShipped(Long quantityShipped) {
        this.quantityShipped = quantityShipped;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "STATUS", length = 2)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "STORE_CODE", length = 10)
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    @Index(name = "IDX_WH_VMI_CCH_STA")
    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    @OneToOne
    public StaLine getStaLine() {
        return staLine;
    }

    public void setStaLine(StaLine staLine) {
        this.staLine = staLine;
    }

    @Column(name = "version")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Column(name = "VMI_CODE")
    public String getVmiCode() {
        return vmiCode;
    }

    public void setVmiCode(String vmiCode) {
        this.vmiCode = vmiCode;
    }

}
