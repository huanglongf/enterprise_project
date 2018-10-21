package com.jumbo.pms.model.parcel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * 包裹明细
 * 
 * @author ShengGe
 * 
 */
@Entity
@Table(name = "T_WH_PARCEL_INFO_LINE")
public class ParcelInfoLine extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * PK
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * SKU编码(后端商品对接唯一编码)
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
     * 数量
     */
    private Long quantity;
    
    /**
     * 包裹主档信息
     */
    private Long parcelId;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PARCEL_INFO_LINE", sequenceName = "S_T_WH_PARCEL_INFO_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARCEL_INFO_LINE")
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

    @Column(name = "PARCEL_ID")
    public Long getParcelId() {
        return parcelId;
    }

    public void setParcelId(Long parcelId) {
        this.parcelId = parcelId;
    }
	
}
