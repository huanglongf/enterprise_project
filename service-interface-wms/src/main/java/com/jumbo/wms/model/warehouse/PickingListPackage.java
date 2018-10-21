package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;

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

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.baseinfo.Sku;

/**
 * O2O装箱实体
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_PICKING_PACKAGE")
public class PickingListPackage extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 6201859383857149810L;
    /**
     * PK ID
     */
    private Long id;
    /**
     * 状态 1：新建；10：完成；3:已核对
     */
    private DefaultStatus status;
    /**
     * 物流单号
     */
    private String trackingNo;
    /**
     * 联系人
     */
    private String receiver;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 所属O2OQS配货清单
     */
    private PickingList pickingListId;
    /**
     * 所用耗材
     */
    private Sku skuId;
    /**
     * 称重/计费重量
     */
    private BigDecimal weight;
    
    /**
     * 城市
     */
    private String city;
    
    /**
     * 省份
     */
    private String province;
    /**
     * 地区
     */
    private String district;
    
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_PICKING_PACKAGE", sequenceName = "S_T_WH_PICKING_PACKAGE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_PICKING_PACKAGE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "TRACKING_NO", length = 50)
    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    @Column(name = "RECEIVER", length = 20)
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Column(name = "TELEPHONE", length = 20)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "ADDRESS", length = 500)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PICKING_LIST_ID")
    @Index(name = "IDX_PP_PICK_ID")
    public PickingList getPickingListId() {
        return pickingListId;
    }

    public void setPickingListId(PickingList pickingListId) {
        this.pickingListId = pickingListId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_PP_SKU_ID")
    public Sku getSkuId() {
        return skuId;
    }

    public void setSkuId(Sku skuId) {
        this.skuId = skuId;
    }

    @Column(name = "WEIGHT")
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Column(name = "CITY", length = 50)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "PROVINCE", length = 50)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "DISTRICT", length = 50)
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
    
    
}
