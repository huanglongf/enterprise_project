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
@Table(name = "T_WH_UA_INVENTORY")
public class WhUaInventory extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 5997204753042802747L;

    /**
     * id
     */
    private Long id;
    
    /**
     * sku
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
     * 创建时间
     */
    private Date createTime;

    @Id
    @SequenceGenerator(name = "T_WH_UA_INVENTORY_ID_GENERATOR", sequenceName = "S_T_WH_UA_INVENTORY", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_WH_UA_INVENTORY_ID_GENERATOR")
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

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
