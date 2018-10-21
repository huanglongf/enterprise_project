package com.jumbo.wms.model.hub2wms.threepl;

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

/**
 * 菜鸟大宝SN示例
 * 
 * @author hui.li
 * 
 */
@Entity
@Table(name = "T_WH_CN_SN_SAMPLE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CnSnSample extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 3223377658657353600L;

    private Long id;// 主键

    /**
     * sn示例顺序
     */
    private String snSeq;
    /**
     * sn示例描述
     */
    private String sampleDesc;

    /**
     * 商品信息
     */
    private CnSkuInfo cnSkuInfo;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CN_SN_SAMPLE", sequenceName = "S_T_WH_CN_SN_SAMPLE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CN_SN_SAMPLE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SN_SEQ")
    public String getSnSeq() {
        return snSeq;
    }

    public void setSnSeq(String snSeq) {
        this.snSeq = snSeq;
    }

    @Column(name = "SAMPLE_DESC")
    public String getSampleDesc() {
        return sampleDesc;
    }

    public void setSampleDesc(String sampleDesc) {
        this.sampleDesc = sampleDesc;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CN_SKU_INFO")
    public CnSkuInfo getCnSkuInfo() {
        return cnSkuInfo;
    }

    public void setCnSkuInfo(CnSkuInfo cnSkuInfo) {
        this.cnSkuInfo = cnSkuInfo;
    }



}
