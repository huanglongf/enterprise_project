package com.jumbo.wms.model.vmi.af;

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


@Entity
@Table(name = "AF_LFINVENTORY_INFO")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class AFLFInventoryInfo extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 6520599594298142827L;

    private Long id;
    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * Sku
     */
    private String sku;
    
    /**
     * 店铺key
     */
    private String storerKey;
    
    
    /**
     * 总库存量
     */
    private Long totalQty;

    /**
     * 可用库存量
     */
    private Long avaiableQty;
    
    /**
     * 状态
     */
    private Long status;
    
    /**
     * 对比状态
     */
    private String compareStatus;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_LFINV_INFO", sequenceName = "S_AF_LFINV_INFO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LFINV_INFO")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "SKU", length = 50)
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
    
    @Column(name = "STORER_KEY", length = 20)
    public String getStorerKey() {
        return storerKey;
    }

    public void setStorerKey(String storerKey) {
        this.storerKey = storerKey;
    }

    @Column(name = "TOTAL_QTY")
    public Long getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Long totalQty) {
        this.totalQty = totalQty;
    }

    @Column(name = "AVAIABLE_QTY")
    public Long getAvaiableQty() {
        return avaiableQty;
    }

    public void setAvaiableQty(Long avaiableQty) {
        this.avaiableQty = avaiableQty;
    }

    
    @Column(name = "STATUS")
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
    
    @Column(name = "COMPARE_STATUS", length = 20)
    public String getCompareStatus() {
        return compareStatus;
    }

    public void setCompareStatus(String compareStatus) {
    	this.compareStatus = compareStatus;
    }

}
