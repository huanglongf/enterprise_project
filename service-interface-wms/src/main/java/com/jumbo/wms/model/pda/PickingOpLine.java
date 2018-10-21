package com.jumbo.wms.model.pda;

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

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.WarehouseLocation;


/**
 * @author hui.li
 *
 */
@Entity
@Table(name = "t_wh_picking_op_line")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class PickingOpLine extends BaseModel {
   
    /**
     * 
     */
    private static final long serialVersionUID = 550726578346867525L;

    /**
     * PK
     */
    private Long id;
   
    /**
     * 容器编码
     */
    private String containerCode;

    /**
     * 商品条码
     */
    private String skuBarcode;

    /**
     * 商品
     */
    private Sku sku;

    /**
     * 库位编码
     */
    private String locationCode;

    /**
     * 库位
     */
    private WarehouseLocation location;

    /**
     * 拣货数量
     */
    private Long qty;

    /**
     * 生产日期
     */
    private Date proDate;

    /**
     * 过期日期
     */
    private Date expDate;

    /**
     * 操作人
     */
    private User user;
    
    private PickingLine pickingLine;
    
    /**
     * 退仓拣货批次号
     */
    private String rtwDiekingCode;
    
    /**
     * 库存状态
     */
    private String skuInvStatus;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_S_T_WH_PICKING_OP_LINE", sequenceName = "S_T_WH_PICKING_OP_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_S_T_WH_PICKING_OP_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "C_CODE")
    public String getContainerCode() {
        return containerCode;
    }

    public void setContainerCode(String containerCode) {
        this.containerCode = containerCode;
    }

    @Column(name = "SKU_BARCODE")
    public String getSkuBarcode() {
        return skuBarcode;
    }

    public void setSkuBarcode(String skuBarcode) {
        this.skuBarcode = skuBarcode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @Column(name = "LOCATION_CODE")
    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID")
    public WarehouseLocation getLocation() {
        return location;
    }

    public void setLocation(WarehouseLocation location) {
        this.location = location;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "PRO_DATE")
    public Date getProDate() {
        return proDate;
    }

    public void setProDate(Date proDate) {
        this.proDate = proDate;
    }

    @Column(name = "EXP_DATE")
    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PICKING_LINE_ID")
    public PickingLine getPickingLine() {
        return pickingLine;
    }

    public void setPickingLine(PickingLine pickingLine) {
        this.pickingLine = pickingLine;
    }
    
    @Column(name="RTW_DIEKING_CODE")
    public String getRtwDiekingCode() {
        return rtwDiekingCode;
    }

    public void setRtwDiekingCode(String rtwDiekingCode) {
        this.rtwDiekingCode = rtwDiekingCode;
    }

    @Column(name="SKU_INV_STATUS")
    public String getSkuInvStatus() {
        return skuInvStatus;
    }

    public void setSkuInvStatus(String skuInvStatus) {
        this.skuInvStatus = skuInvStatus;
    }

}
