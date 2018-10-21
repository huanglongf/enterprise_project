package com.jumbo.wms.model.boc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * boc source Store 关联信息
 * 
 * @author wudan
 * 
 */
@Entity
@Table(name = "T_VMI_BOC_STORE_REFERENCE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class BocStoreReference extends BaseModel {


    private static final long serialVersionUID = 1071979053389672000L;

    private Long id;

    private int version;

    /**
     * 店铺
     */
    private String storeCode;

    /**
     * 来源
     */
    private String source;
    
    /**
     * 品牌Id
     */
    private Long bandId;

    /**
     * 销售模式
     */
    private Integer salesModel;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_BOC_STORE_REFERENCE", sequenceName = "S_T_VMI_BOC_STORE_REFERENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOC_STORE_REFERENCE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "STORE_CODE", length = 100)
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Column(name = "SOURCE", length = 100)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "SALES_MODEL", length = 100)
    public Integer getSalesModel() {
        return salesModel;
    }

    public void setSalesModel(Integer salesModel) {
        this.salesModel = salesModel;
    }

    @Column(name = "BANDID")
    public Long getBandId() {
        return bandId;
    }

    public void setBandId(Long bandId) {
        this.bandId = bandId;
    }

}
