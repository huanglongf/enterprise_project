package com.jumbo.wms.model.vmi.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;

//
@Entity
@Table(name = "T_WH_MSG_SKU_SYNC")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgSKUSync extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 4910532659553946060L;
    private Long id;
    private String skuCode;
    /**
     * 状态
     */
    private DefaultStatus status;

    /**
     * 来源
     */
    private String source;

    /**
     * 创建时间
     */
    private Date updateTime = new Date();
    /**
     * 批次号
     */
    private Long batchId;

    private String barCode;
    private String barCode2;
    private String skuName;
    private int version;
    private String brandName;
    private DefaultStatus type;
    private Integer intType;
    private Boolean isSn;

    /**
     * 外包仓仓库编码
     */
    private String soruceWh;

    /**
     * 外包仓商品编码
     */
    private String skuThreeplCode;

    private String sfFlag;

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "BAR_CODE", length = 100)
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Column(name = "BAR_CODE2", length = 100)
    public String getBarCode2() {
        return barCode2;
    }

    public void setBarCode2(String barCode2) {
        this.barCode2 = barCode2;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SKUSYNC", sequenceName = "S_T_WH_MSG_SKU_SYNC", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SKUSYNC")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SKU_CODE", length = 150)
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "SKU_NAME", length = 300)
    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "SOURCE", length = 30)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "BATCH_ID")
    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    @Column(name = "BRAND_NAME")
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getType() {
        return type;
    }

    public void setType(DefaultStatus type) {
        this.type = type;
    }

    @Transient
    public Integer getIntType() {
        return intType;
    }

    public void setIntType(Integer intType) {
        this.intType = intType;
    }

    @Column(name = "IS_SN")
    public Boolean getIsSn() {
        return isSn;
    }

    public void setIsSn(Boolean isSn) {
        this.isSn = isSn;
    }

    @Column(name = "SOURCE_WH")
    public String getSoruceWh() {
        return soruceWh;
    }

    public void setSoruceWh(String soruceWh) {
        this.soruceWh = soruceWh;
    }

    @Column(name = "SKU_THREEPL_CODE")
    public String getSkuThreeplCode() {
        return skuThreeplCode;
    }

    public void setSkuThreeplCode(String skuThreeplCode) {
        this.skuThreeplCode = skuThreeplCode;
    }

    @Column(name = "SF_FLAG")
    public String getSfFlag() {
        return sfFlag;
    }

    public void setSfFlag(String sfFlag) {
        this.sfFlag = sfFlag;
    }


}
