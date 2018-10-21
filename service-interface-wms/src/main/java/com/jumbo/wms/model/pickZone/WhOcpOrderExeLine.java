package com.jumbo.wms.model.pickZone;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.WarehouseLocation;


/**
 * The persistent class for the T_WH_OCP_ORDER_EXE_LINE database table.
 * 
 */
@Entity
@Table(name="T_WH_OCP_ORDER_EXE_LINE")
public class WhOcpOrderExeLine extends BaseModel {
    
    /**
     * 
     */
    private static final long serialVersionUID = 5257238981239751232L;
    private Long id;
	private String barchCode;
	private Date expireDate;
	private Date inboundTime;
    private InventoryStatus inventoryStatus;
    private WarehouseLocation warehouseLocation;
    private WhOcpOrder whOcpOrder;
	private String owner;
	private Integer qty;
    private Sku sku;


	@Id
    @SequenceGenerator(name = "T_WH_OCP_ORDER_EXE_LINE_ID_GENERATOR", sequenceName = "S_T_WH_OCP_ORDER_EXE_LINE", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="T_WH_OCP_ORDER_EXE_LINE_ID_GENERATOR")
    public Long getId() {
		return this.id;
	}

    public void setId(Long id) {
		this.id = id;
	}


	@Column(name="BARCH_CODE")
	public String getBarchCode() {
		return this.barchCode;
	}

	public void setBarchCode(String barchCode) {
		this.barchCode = barchCode;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="EXPIRE_DATE")
	public Date getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="INBOUND_TIME")
	public Date getInboundTime() {
		return this.inboundTime;
	}

	public void setInboundTime(Date inboundTime) {
		this.inboundTime = inboundTime;
	}


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INV_STATUS_ID")
    public InventoryStatus getInventoryStatus() {
        return inventoryStatus;
	}


    public void setInventoryStatus(InventoryStatus inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
	}



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OCP_ID")
    public WhOcpOrder getWhOcpOrder() {
        return whOcpOrder;
    }



    public void setWhOcpOrder(WhOcpOrder whOcpOrder) {
        this.whOcpOrder = whOcpOrder;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    public WarehouseLocation getWarehouseLocation() {
        return warehouseLocation;
    }


    public void setWarehouseLocation(WarehouseLocation warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }



    public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}


	public Integer getQty() {
		return this.qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    public Sku getSku() {
        return sku;
    }


    public void setSku(Sku sku) {
        this.sku = sku;
    }


}