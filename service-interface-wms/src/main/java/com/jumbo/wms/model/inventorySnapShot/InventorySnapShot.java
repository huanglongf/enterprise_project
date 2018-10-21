/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.wms.model.inventorySnapShot;

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
 * 库存快照表
 * 
 * @author bin.hu
 *
 */
@Entity
@Table(name = "T_WH_INVENTORY_SNAPSHOT")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class InventorySnapShot extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -1701956413998778762L;
    /**
     * PK
     */
    private Long id;

    /**
     * 批次号
     */
    private String batchCode;

    /**
     * 店铺编码
     */
    private String ownerCode;

    private String binCode;

    /**
     * 商品编码
     */
    private String skuCode;

    /**
     * 库存状态编码
     */
    private String invStatusCode;

    /**
     * 库存数量
     */
    private Integer qty;

    /**
     * 状态0-未同步 1-同步成功
     */
    private Integer status;

    /**
     * 事务时间
     */
    private Date transactionTime;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_INVENTORY_SNAPSHOT", sequenceName = "S_T_WH_INVENTORY_SNAPSHOT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_INVENTORY_SNAPSHOT")
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

    @Column(name = "OWNER_CODE")
    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    @Column(name = "BIN_CODE")
    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
    }

    @Column(name = "SKU_CODE")
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "INV_STATUS_CODE")
    public String getInvStatusCode() {
        return invStatusCode;
    }

    public void setInvStatusCode(String invStatusCode) {
        this.invStatusCode = invStatusCode;
    }

    @Column(name = "QTY")
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "TRANSACTION_TIME")
    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }


}
