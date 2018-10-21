package com.jumbo.wms.model.baseinfo;

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

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_WH_SKU_RFID")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class SkuRfid extends BaseModel{

    /**
     * 
     */
    private static final long serialVersionUID = -5299964309930416093L;

    /**
     * PK
     */
    private Long id;

    /**
     * RFID编码
     */
    private String rfidCode;

    /**
     * 商品ID
     */
    private Long skuId;

    /**
     * 作业单ID
     */
    private Long staId;

    /**
     * 仓库ID
     */
    private Long ouId;

    /**
     * 最后修改时间
     */
    private Date lastModifyTime;

    /**
     * version
     */
    private int version;
    /**
     * 批次号
     */
    private String rfidBatch;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "S_T_WH_SKU_RFID", sequenceName = "S_T_WH_SKU_RFID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_T_WH_SKU_RFID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "RFID_CODE")
    public String getRfidCode() {
        return rfidCode;
    }

    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "OU_ID")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "RFID_BATCH")
    public String getRfidBatch() {
        return rfidBatch;
    }

    public void setRfidBatch(String rfidBatch) {
        this.rfidBatch = rfidBatch;
    }

}
