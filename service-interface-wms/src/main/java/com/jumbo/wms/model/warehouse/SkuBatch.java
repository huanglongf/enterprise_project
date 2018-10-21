package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.jumbo.wms.model.BaseModel;

/**
 * sku 库存商品批次
 */
@Entity
@Table(name = "T_WH_INV_SKU_BATCH")
public class SkuBatch extends BaseModel {


    private static final long serialVersionUID = -5451853740776803108L;

    /**
     * PK
     */
    private Long id;

    /**
     * 操作时间
     */
    private Date createTime;

    /**
     * 库存批次
     */
    private String invBatchCode;

    /**
     * sku id
     */
    private Long skuid;

    /**
     * 商品批次
     */
    private String skuBatchNo;

    /**
     * 版本
     */
    private int version;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_INV_SKU_BATCH", sequenceName = "S_T_WH_INV_SKU_BATCH", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_INV_SKU_BATCH")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "INV_BATCH_CODE")
    public String getInvBatchCode() {
        return invBatchCode;
    }

    public void setInvBatchCode(String invBatchCode) {
        this.invBatchCode = invBatchCode;
    }

    @Column(name = "SKU_ID")
    public Long getSkuid() {
        return skuid;
    }

    public void setSkuid(Long skuid) {
        this.skuid = skuid;
    }

    @Column(name = "SKU_BATCH_NO")
    public String getSkuBatchNo() {
        return skuBatchNo;
    }

    public void setSkuBatchNo(String skuBatchNo) {
        this.skuBatchNo = skuBatchNo;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


}
