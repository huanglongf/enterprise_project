package com.jumbo.wms.model.pda;

import java.util.Date;

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
 * 
 * @author lizaibiao 作业单操作明细日志
 * 
 */
@Entity
@Table(name = "t_wh_sta_op_log")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class StaOpLog extends BaseModel {

    private static final long serialVersionUID = -21588589753094995L;
    /**
     * PK
     */
    private Long id;
    /**
     * 操作人
     */
    private Long userId;
    /**
     * 操作时间
     */
    private Date createTime;
    /**
     * 作业单ID
     */
    private Long staId;
    /**
     * 容器编码
     */
    private String cartonCode;

    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 库位编码
     */
    private String locationCode;
    /*
     * 类型
     */
    private Integer type;
    /**
     * SN
     */
    private String sn;
    /**
     * 残次类型
     */
    private String dmgType;
    /**
     * 残次原因
     */
    private String dmgReason;
    /**
     * 过期时间
     */
    private Date expDate;

    /**
     * 库存状态
     */
    private Long invStatusId;
    /**
     * 数量
     */
    private Long qty;
    /**
     * 残次条码
     */
    private String dmgCode;
    /**
     * 仓库组织ID
     */
    private Long whOuId;

    private Long carId;// 箱id

    @Column(name = "CAR_ID")
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WHL", sequenceName = "S_T_WH_STA_OP_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WHL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "USER_ID")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "CARTON_CODE")
    public String getCartonCode() {
        return cartonCode;
    }

    public void setCartonCode(String cartonCode) {
        this.cartonCode = cartonCode;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "LOCATION_CODE")
    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "SN")
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Column(name = "DMG_TYPE")
    public String getDmgType() {
        return dmgType;
    }

    public void setDmgType(String dmgType) {
        this.dmgType = dmgType;
    }

    @Column(name = "DMG_REASON")
    public String getDmgReason() {
        return dmgReason;
    }

    public void setDmgReason(String dmgReason) {
        this.dmgReason = dmgReason;
    }

    @Column(name = "EXP_DATE")
    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
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

    @Column(name = "DMG_CODE")
    public String getDmgCode() {
        return dmgCode;
    }

    public void setDmgCode(String dmgCode) {
        this.dmgCode = dmgCode;
    }

    @Column(name = "WH_OU_ID")
    public Long getWhOuId() {
        return whOuId;
    }

    public void setWhOuId(Long whOuId) {
        this.whOuId = whOuId;
    }

}
