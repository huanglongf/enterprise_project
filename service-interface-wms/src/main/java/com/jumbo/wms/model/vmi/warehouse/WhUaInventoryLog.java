package com.jumbo.wms.model.vmi.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_WH_UA_INVENTORY_LOG")
public class WhUaInventoryLog extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 7644995010601692284L;

    /**
     * id
     */
    private Long id;
    
    /**
     * sku:， ext code 2
     */
    private String sku;
    
    /**
     * 店铺名字 
     */
    private String storerKey;
    
    /**
     * 总数
     */
    private String totalQty;
    
    /**
     * 可用数量
     */
    private String avaiableQty;
    
    /**
     * 状态
     */
    private String shorts;
    
    /**
     * ua inventory id
     */
    private Long uaInventoryId;
    
    /**
     * 条形码
     */
    private String barCode;
    
    /**
     * 库存数量
     */
    private String quantity;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    private String qty;
    
    

    @Id
    @SequenceGenerator(name = "T_WH_UA_INVENTORY_LOG_ID_GENERATOR", sequenceName = "S_T_WH_UA_INVENTORY_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_WH_UA_INVENTORY_LOG_ID_GENERATOR")
    @Column(name = "ID", unique = true, nullable = false, scale = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SKU")
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Column(name = "STORER_KEY")
    public String getStorerKey() {
        return storerKey;
    }

    public void setStorerKey(String storerKey) {
        this.storerKey = storerKey;
    }

    @Column(name = "TOTAL_QTY")
    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    @Column(name = "AVAIABLE_QTY")
    public String getAvaiableQty() {
        return avaiableQty;
    }

    public void setAvaiableQty(String avaiableQty) {
        this.avaiableQty = avaiableQty;
    }

    @Column(name = "SHORTS")
    public String getShorts() {
        return shorts;
    }

    public void setShorts(String shorts) {
        this.shorts = shorts;
    }

    @Column(name = "UA_INVENTORY_ID")
    public Long getUaInventoryId() {
        return uaInventoryId;
    }

    public void setUaInventoryId(Long uaInventoryId) {
        this.uaInventoryId = uaInventoryId;
    }

    @Column(name = "BAR_CODE")
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Column(name = "QUANTITY")
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "QTY")
	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}
    
    
    
}
