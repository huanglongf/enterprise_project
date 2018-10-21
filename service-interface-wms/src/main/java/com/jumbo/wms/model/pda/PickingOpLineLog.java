package com.jumbo.wms.model.pda;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;


/**
 * @author hui.li
 *
 */
@Entity
@Table(name = "t_wh_picking_op_line_log")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class PickingOpLineLog extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -732437194117268207L;

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
    private Long skuId;

    /**
     * 库位编码
     */
    private String locationCode;

    /**
     * 库位
     */
    private Long locationId;

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
    private Long userId;
    
    private Long pickingLineId;

    @Id
    @Column(name = "ID")
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



    @Column(name = "LOCATION_CODE")
    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
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

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "LOCATION_ID")
    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    @Column(name = "USER_ID")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "PICKING_LINE_ID")
    public Long getPickingLineId() {
        return pickingLineId;
    }

    public void setPickingLineId(Long pickingLineId) {
        this.pickingLineId = pickingLineId;
    }

}
