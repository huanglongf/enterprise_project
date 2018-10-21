package com.jumbo.wms.model.automaticEquipment;

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

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.WarehouseLocation;


/**
 * 自动化设备上架规则
 * 
 * @author lihui
 *
 * @createDate 2016年2月17日 上午11:09:51
 */
@Entity
@Table(name = "T_WH_INBOUND_ROLE")
public class InboundRole extends BaseModel {

    private static final long serialVersionUID = 4044818049594420995L;

    private Long id;
    /**
     * 店铺
     */
    private String owner;
    /**
     * 组织
     */
    private OperationUnit operationUnit;
    /**
     * 商品
     */
    private Sku sku;
    /**
     * 商品上架类型
     */
    private SkuType skuType;
    /**
     * 优先级
     */
    private Integer lv;
    /**
     * 库位
     */
    private WarehouseLocation location;
    /**
     * 弹出口
     */
    private PopUpArea targetLocation;

    @Id
    @SequenceGenerator(name = "S_T_WH_INBOUND_ROLE_GENERATOR", sequenceName = "s_t_wh_inbound_role", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_T_WH_INBOUND_ROLE_GENERATOR")
    @Column(name = "ID", unique = true, nullable = false, scale = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ou_id")
    public OperationUnit getOperationUnit() {
        return operationUnit;
    }

    public void setOperationUnit(OperationUnit operationUnit) {
        this.operationUnit = operationUnit;
    }

    @Column(name = "owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_type")
    public SkuType getSkuType() {
        return skuType;
    }

    public void setSkuType(SkuType skuType) {
        this.skuType = skuType;
    }

    @Column(name = "lv", length = 5)
    public Integer getLv() {
        return lv;
    }

    public void setLv(Integer lv) {
        this.lv = lv;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_location_id")
    public PopUpArea getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(PopUpArea targetLocation) {
        this.targetLocation = targetLocation;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
	public WarehouseLocation getLocation() {
		return location;
	}

	public void setLocation(WarehouseLocation location) {
		this.location = location;
	}





}
