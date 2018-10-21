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
 */
package com.jumbo.wms.model.vmi.Default;

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

/**
 * @author lichuan
 * 
 */
@Entity
@Table(name = "T_VMI_INVENTORY")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class VmiInventoryDefault extends BaseModel {

    private static final long serialVersionUID = 2583055772173199461L;
    public static final String vmiinv = "vmiinv";

    /**
     * id
     */
    private Long id;

    /**
     * 品牌店铺编码t_bi_channel.vmi_code
     */
    private String storeCode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 品牌来源，渠道vmi_source
     */
    private String vmiSource;

    /**
     * 商品唯一编码t_bi_inv_sku.ext_code2
     */
    private String upc;

    /**
     * 数量t_wh_sta_line.quantity
     */
    private Long qty;

    /**
     * 占用量
     */
    private Long blockQty;

    /**
     * 不可销售库存量
     */
    private Long onHoldQty;

    /**
     * 库存状态
     */
    private String invStatus;

    /**
     * 扩展字段信息
     */
    private String extMemo;

    /**
     * 生成批次
     */
    private String batchNo;

    /**
     * 状态
     */
    private VmiGeneralStatus status;

    /**
     * 异常次数
     */
    private int errorCount;

    /**
     * 版本
     */
    private int version;

    /**
     * 品牌通用接口标识是否品牌定制
     */
    private Boolean isVmiExt;


    private String whCode;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_VMI_INVENTORY", sequenceName = "S_T_VMI_INVENTORY", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VMI_INVENTORY")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STORE_CODE")
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "FINISH_TIME")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Column(name = "VMI_SOURCE")
    public String getVmiSource() {
        return vmiSource;
    }

    public void setVmiSource(String vmiSource) {
        this.vmiSource = vmiSource;
    }

    @Column(name = "UPC")
    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "BLOCK_QTY")
    public Long getBlockQty() {
        return blockQty;
    }

    public void setBlockQty(Long blockQty) {
        this.blockQty = blockQty;
    }

    @Column(name = "INV_STATUS")
    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    @Column(name = "EXT_MEMO")
    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    @Column(name = "BATCH_NO")
    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.vmi.Default.VmiGeneralStatus")})
    public VmiGeneralStatus getStatus() {
        return status;
    }

    public void setStatus(VmiGeneralStatus status) {
        this.status = status;
    }

    @Column(name = "ERROR_COUNT")
    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "IS_VMI_EXT")
    public Boolean getIsVmiExt() {
        return isVmiExt;
    }

    public void setIsVmiExt(Boolean isVmiExt) {
        this.isVmiExt = isVmiExt;
    }

    @Column(name = "WH_CODE")
    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    @Column(name = "ON_HOLD_QTY")
    public Long getOnHoldQty() {
        return onHoldQty;
    }

    public void setOnHoldQty(Long onHoldQty) {
        this.onHoldQty = onHoldQty;
    }



}
