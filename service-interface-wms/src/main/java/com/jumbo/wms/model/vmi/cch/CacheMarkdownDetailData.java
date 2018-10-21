package com.jumbo.wms.model.vmi.cch;

import java.math.BigDecimal;
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
@Table(name = "T_CACHE_MARKDOWN_DATA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class CacheMarkdownDetailData extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1564894814066009272L;

    private Long id;

    private String salesOperationCode;

    private String salesOperationName;

    private Long skuCode;

    private BigDecimal newPrice;

    private Date startingDate;

    private Date endDate;

    private Date createTime;

    private String status;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CACHE_MARKDOWN_DATA", sequenceName = "S_T_CACHE_MARKDOWN_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CACHE_MARKDOWN_DATA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SALES_OPERATION_CODE", length = 20)
    public String getSalesOperationCode() {
        return salesOperationCode;
    }

    public void setSalesOperationCode(String salesOperationCode) {
        this.salesOperationCode = salesOperationCode;
    }

    @Column(name = "SALES_OPERATION_NAME", length = 30)
    public String getSalesOperationName() {
        return salesOperationName;
    }

    public void setSalesOperationName(String salesOperationName) {
        this.salesOperationName = salesOperationName;
    }

    @Column(name = "SKU_CODE", length = 30)
    public Long getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(Long skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "NEW_PRICE")
    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(BigDecimal newPrice) {
        this.newPrice = newPrice;
    }

    @Column(name = "STARTING_DATE")
    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    @Column(name = "END_DATE")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }


}
