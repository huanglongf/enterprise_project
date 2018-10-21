package com.jumbo.wms.model.warehouse;

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
 * 表格仓库退货申请明细表
 * 
 * @author xiaolong.fei
 * 
 */
@Entity
@Table(name = "t_wh_return_application_line")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ReturnApplicationLine extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5245763820930338669L;
	/**
	 * PK
	 */
	private Long id;
	/**
	 * 商品ID
	 */
	private Long skuId;
	/**
	 * 库存状态ID
	 */
	private Long invStatusId;
	/**
	 * 数量
	 */
	private Long qty;
	/**
	 * 申请ID
	 */
	private Long raId;

	/**
	 * 商品描述信息 。 用于存储手动添加的商品
	 */
	private String skuInfo;
	
	
	/**
	 * wms_order_type  wms工单类型
	 * @return
	 */
	
	private  String wmsOrdeType;
	
	@Column(name = "wms_order_type")
	public String getWmsOrdeType() {
        return wmsOrdeType;
    }

    public void setWmsOrdeType(String wmsOrdeType) {
        this.wmsOrdeType = wmsOrdeType;
    }

    @Id
	@Column(name = "ID")
	@SequenceGenerator(name = "SEQ_RETURN_APP_LINE", sequenceName = "S_T_WH_RETURN_APPLICATION_LINE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RETURN_APP_LINE")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "SKU_ID")
	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	@Column(name = "INV_STATUS_ID")
	public Long getInvStatusId() {
		return invStatusId;
	}

	public void setInvStatusId(Long invStatusId) {
		this.invStatusId = invStatusId;
	}

	@Column(name = "QTY")
	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	@Column(name = "RA_ID")
	public Long getRaId() {
		return raId;
	}

	public void setRaId(Long raId) {
		this.raId = raId;
	}
	
	@Column(name = "SKU_INFO")
    public String getSkuInfo() {
        return skuInfo;
    }

    public void setSkuInfo(String skuInfo) {
        this.skuInfo = skuInfo;
    }
	
}
