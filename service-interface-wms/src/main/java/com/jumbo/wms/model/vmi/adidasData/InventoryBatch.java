package com.jumbo.wms.model.vmi.adidasData;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_INVENTORY_BATCH")
public class InventoryBatch extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -7036935886099580844L;

    private Long id;// 主键

    private String batchCode;// 批次号

    private Long lineCount;// 行总数

    private Long totalInventory;// 库存总数

    private Date createTime;// 创建时间

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "S_T_INVENTORY_BATCH", sequenceName = "S_T_INVENTORY_BATCH", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_T_INVENTORY_BATCH")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "BATCH_CODE")
    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    @Column(name = "LINE_COUNT")
    public Long getLineCount() {
        return lineCount;
    }

    public void setLineCount(Long lineCount) {
        this.lineCount = lineCount;
    }

    @Column(name = "TOTAL_INVENTORY")
    public Long getTotalInventory() {
        return totalInventory;
    }

    public void setTotalInventory(Long totalInventory) {
        this.totalInventory = totalInventory;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}
