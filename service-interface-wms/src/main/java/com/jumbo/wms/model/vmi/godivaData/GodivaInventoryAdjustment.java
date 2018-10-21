package com.jumbo.wms.model.vmi.godivaData;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;

/**
 * gdv库存调整
 * 
 * @author wudan
 * 
 */
@Entity
@Table(name = "T_WH_GDV_INVENTORY_ADJUSTMENT")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class GodivaInventoryAdjustment extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5207265142432671915L;

    private Long Id;

    private int version;

    private String inventoryCheckCode;

    /**
     * Adjustment 键字
     */
    private String adjustmentKey;

    private Date reportDate;

    private Date confirmDate;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date updateDate;

    private DefaultStatus status;


    private String remarks;

    private Long shopId;

    /**
     * 调整方向 in 入 out 出
     */
    private String type;


    private String source;



    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_GDV_INVENTORY_ADJUSTMENT", sequenceName = "S_WH_GDV_INVENTORY_ADJUSTMENT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_GDV_INVENTORY_ADJUSTMENT")
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "ADJUSTMENT_KEY", length = 30)
    public String getAdjustmentKey() {
        return adjustmentKey;
    }

    public void setAdjustmentKey(String adjustmentKey) {
        this.adjustmentKey = adjustmentKey;
    }

    @Column(name = "REPORT_DATE")
    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    @Column(name = "CONFIRM_DATE")
    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    @Column(name = "CREATE_BY", length = 30)
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "REMARKS", length = 100)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "UPDATE_DATE")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Column(name = "SHOPID")
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Column(name = "TYPE", length = 20)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "INVENTORYCHECK_CODE", length = 30)
    public String getInventoryCheckCode() {
        return inventoryCheckCode;
    }

    public void setInventoryCheckCode(String inventoryCheckCode) {
        this.inventoryCheckCode = inventoryCheckCode;
    }

    @Column(name = "SOURCE", length = 30)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


}
