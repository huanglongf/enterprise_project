package com.jumbo.pms.model;

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

import com.jumbo.wms.model.BaseModel;

/**
 * 包裹明细
 * 
 * @author ShengGe
 * 
 */
@Entity
@Table(name = "T_WH_PARCEL_LINE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class ParcelLine extends BaseModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 815476089066787705L;

	/**
     * PK
     */
    private Long id;

    /**
     * Version
     */
    private Date version;
    
    /**
     * SKU编码
     */
    private String skuCode;
    
    /**
     * 条形码
     */
    private String barCode;

    /**
     * 商品名称
     */
    private String skuName;
    
    /**
     * 购买数量
     */
    private Long quantity;
    
    /**
     * 包裹主档信息
     */
    private Parcel parcel;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PARCEL_LINE", sequenceName = "S_T_WH_PARCEL_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARCEL_LINE")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
    @Version
    @Column(name = "VERSION")
	public Date getVersion() {
		return version;
	}
	
	public void setVersion(Date version) {
		this.version = version;
	}

	@Column(name = "SKU_CODE")
	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	@Column(name = "BAR_CODE")
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	@Column(name = "SKU_NAME")
	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	@Column(name = "QUANTITY")
	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARCEL_ID")
    @Index(name = "IDX_PARCEL_ID")
	public Parcel getParcel() {
		return parcel;
	}

	public void setParcel(Parcel parcel) {
		this.parcel = parcel;
	}
}
