package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

/**
 * 菜鸟盘点中间表
 * 
 */
@Entity
@Table(name = "T_WH_CN_INVENTORY_COUNT")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CnWmsInventoryCount implements Serializable {

    private static final long serialVersionUID = -2752911219055999263L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 库存盘点表id
     */
    private Long ckId;
    /**
     * 仓库编码
     */
    private String storeCode;
    /**
     * 订单类型：701 盘点出库（盘亏） 702 盘点入库（盘盈）
     */
    private Integer orderType;
    /**
     * 货主ID
     */
    private String ownerUserId;
    /**
     * 外部业务编码，物流宝用来去重，需要每次确认都不一样，同一次确认重试需要一样
     */
    private String outBizCode;
    /**
     * 差异单orderCode 无差异单业务不填 差异单：主要是为了先锁定库存
     */
    private String imbalanceOrderCode;
    /**
     * 库存差异调整原因类型: //WMS_GOODS_OVER_FLOW CHECK 仓内多货 // WMS_GOODS_LOCK CHECK 仓内少货//
     * WMS_GOODS_OWNER_TRANSFER ADJUST 货权转移// WMS_GOODS_DEFECT ADJUST 临保转残 // WMS_GOODS_DAMAGED
     * ADJUST 库内破损 // WMS_GOODS_BATCH_ADJUST ADJUST 批次调整 // OTHER ADJUST 其它 主要是为了区分调整和盘点//
     */
    private String adjustReasonType;
    /**
     * 调整主单号
     */
    private String adjustBizKey;
    /**
     * 备注
     */
    private String remark;
    /**
     * 上传状态 0：未上传,1：上传成功 ，-2：上传后反馈异常,-1：自身异常
     */
    private String status;
    /**
     * 错误次数
     */
    private Integer errorCount;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 盘点数据上传后菜鸟返回的OrderCode
     */
    private String rtOrderCode;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WCSI", sequenceName = "S_T_WH_CN_INVENTORY_COUNT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WCSI")
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

    @Column(name = "ORDER_TYPE")
    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    @Column(name = "OWNER_USER_ID")
    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    @Column(name = "OUT_BIZ_CODE")
    public String getOutBizCode() {
        return outBizCode;
    }

    public void setOutBizCode(String outBizCode) {
        this.outBizCode = outBizCode;
    }

    @Column(name = "IMBALANCE_ORDER_CODE")
    public String getImbalanceOrderCode() {
        return imbalanceOrderCode;
    }

    public void setImbalanceOrderCode(String imbalanceOrderCode) {
        this.imbalanceOrderCode = imbalanceOrderCode;
    }

    @Column(name = "ADJUST_REASON_TYPE")
    public String getAdjustReasonType() {
        return adjustReasonType;
    }

    public void setAdjustReasonType(String adjustReasonType) {
        this.adjustReasonType = adjustReasonType;
    }

    @Column(name = "ADJUST_BIZ_KEY")
    public String getAdjustBizKey() {
        return adjustBizKey;
    }

    public void setAdjustBizKey(String adjustBizKey) {
        this.adjustBizKey = adjustBizKey;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "CK_ID")
    public Long getCkId() {
        return ckId;
    }

    public void setCkId(Long ckId) {
        this.ckId = ckId;
    }

    @Column(name = "ERROR_COUNT")
    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    @Column(name = "ERROR_MSG")
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Column(name = "RT_ORDER_CODE")
    public String getRtOrderCode() {
        return rtOrderCode;
    }

    public void setRtOrderCode(String rtOrderCode) {
        this.rtOrderCode = rtOrderCode;
    }
}
