package com.jumbo.wms.model.warehouse;

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

@Entity
@Table(name = "t_wh_stv_imperfect_line_log")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class ImperfectStvLog extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -5403666423964394174L;
    private Long id;
    private String barCode;
    private String jmCode;
    private String keyProperties;
    private String name;
    private String supplierCode;
    private String statusName;
    private Integer quantity;
    private Integer completeQuantity;
    private Integer addQuantity;
    private String skuSn;
    private String imperfectName;
    private String imperfectLineName;
    private String memo;
    private Long sta;
    private Date createTime;
    private Date LogTime;
    private Long statusId;
    private String sn;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_stv_imperfect_line_log", sequenceName = "s_t_wh_stv_imperfect_line_log", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_stv_imperfect_line_log")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "bar_Code")
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Column(name = "jm_Code")
    public String getJmCode() {
        return jmCode;
    }

    public void setJmCode(String jmCode) {
        this.jmCode = jmCode;
    }

    @Column(name = "key_Properties")
    public String getKeyProperties() {
        return keyProperties;
    }

    public void setKeyProperties(String keyProperties) {
        this.keyProperties = keyProperties;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "supplier_Code")
    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    @Column(name = "status_Name")
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Column(name = "quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column(name = "completeQuantity")
    public Integer getCompleteQuantity() {
        return completeQuantity;
    }

    public void setCompleteQuantity(Integer completeQuantity) {
        this.completeQuantity = completeQuantity;
    }

    @Column(name = "addQuantity")
    public Integer getAddQuantity() {
        return addQuantity;
    }

    public void setAddQuantity(Integer addQuantity) {
        this.addQuantity = addQuantity;
    }

    @Column(name = "skuSn")
    public String getSkuSn() {
        return skuSn;
    }

    public void setSkuSn(String skuSn) {
        this.skuSn = skuSn;
    }

    @Column(name = "imperfect_Name")
    public String getImperfectName() {
        return imperfectName;
    }

    public void setImperfectName(String imperfectName) {
        this.imperfectName = imperfectName;
    }

    @Column(name = "imperfectLine_Name")
    public String getImperfectLineName() {
        return imperfectLineName;
    }

    public void setImperfectLineName(String imperfectLineName) {
        this.imperfectLineName = imperfectLineName;
    }

    @Column(name = "mome")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "sta_id")
    public Long getSta() {
        return sta;
    }

    public void setSta(Long sta) {
        this.sta = sta;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "log_time")
    public Date getLogTime() {
        return LogTime;
    }

    public void setLogTime(Date logTime) {
        LogTime = logTime;
    }

    @Column(name = "status_Id")
    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
    @Column(name="sn")
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }



}
