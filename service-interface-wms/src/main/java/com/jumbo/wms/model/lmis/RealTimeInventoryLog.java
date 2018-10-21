package com.jumbo.wms.model.lmis;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.jumbo.wms.model.BaseModel;

/**
 * lmis仓储：实时库存归档日志
 * 
 */
@Entity
@Table(name = "T_WH_REALTIME_INV_LOG")
public class RealTimeInventoryLog extends BaseModel {

    private static final long serialVersionUID = -8272788451844854718L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 仓库id
     */
    private String ouId;

    /**
     * 店铺编码
     */
    private String invOwner;

    /**
     * 库位id
     */
    private String locationId;

    /**
     * 商品id
     */
    private String skuId;

    /**
     * 数量
     */
    private Long qty;

    /**
     * 库存数据生成时间
     */
    private Date invTime;

    /**
     * 归档时间
     */
    private Date createTime;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_REALTIME_INV_LOG", sequenceName = "S_T_WH_REALTIME_INV_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_REALTIME_INV_LOG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "OU_ID")
    public String getOuId() {
        return ouId;
    }

    public void setOuId(String ouId) {
        this.ouId = ouId;
    }

    @Column(name = "INV_OWNER")
    public String getInvOwner() {
        return invOwner;
    }

    public void setInvOwner(String invOwner) {
        this.invOwner = invOwner;
    }

    @Column(name = "LOCATION_ID")
    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    @Column(name = "SKU_ID")
    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "INV_TIME")
    @Index(name = "IDX_INV_LOG_INV_TIME")
    public Date getInvTime() {
        return invTime;
    }

    public void setInvTime(Date invTime) {
        this.invTime = invTime;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
