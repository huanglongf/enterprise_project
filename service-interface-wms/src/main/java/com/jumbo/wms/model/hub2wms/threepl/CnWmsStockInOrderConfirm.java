package com.jumbo.wms.model.hub2wms.threepl;

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
 * 菜鸟入库订单确认反馈
 */
@Entity
@Table(name = "T_WH_CN_STOCK_IN_CONFIRM")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CnWmsStockInOrderConfirm extends BaseModel {

    private static final long serialVersionUID = -9073458067326817086L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 对应作业单id
     */
    private Long staId;
    /**
     * 仓储中心入库订单编码
     */
    private String orderCode;
    /**
     * 单据类型： 302调拨入库单 501销退入库单 601采购入库单 904普通入库单306 B2B入库单 604 B2B干线退货入库 704 库存状态调整入库
     */
    private Integer orderType;
    /**
     * 外部业务编码，物流宝用来去重，需要每次确认都不一样，同一次确认重试需要一样
     */
    private String outBizCode;
    /**
     * 支持入库单多次确认(如：每上架一次就立刻回传) 0 最后一次确认或是一次性确认； 1 多次确认； 默认值为 0 例如输入2认为是0
     */
    private Integer confirmType;
    /**
     * 仓库入库单完成时间
     */
    private Date orderConfirmTime;
    /**
     * 错误次数
     */
    private Integer errorCount;
    /**
     * 错误信息
     */
    private String errorMsg;
    /**
     * 确认状态：0：未执行；1：已执行；-2：执行错误(接受到的反馈确认信息不成功或异常),-1:自身异常
     */
    private String status;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WCSS", sequenceName = "S_T_WH_CN_STOCK_IN_CONFIRM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WCSS")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "ORDER_CODE")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "ORDER_TYPE")
    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    @Column(name = "OUT_BIZ_CODE")
    public String getOutBizCode() {
        return outBizCode;
    }

    public void setOutBizCode(String outBizCode) {
        this.outBizCode = outBizCode;
    }

    @Column(name = "CONFIRM_TYPE")
    public Integer getConfirmType() {
        return confirmType;
    }

    public void setConfirmType(Integer confirmType) {
        this.confirmType = confirmType;
    }

    @Column(name = "ORDER_CONFIRM_TIME")
    public Date getOrderConfirmTime() {
        return orderConfirmTime;
    }

    public void setOrderConfirmTime(Date orderConfirmTime) {
        this.orderConfirmTime = orderConfirmTime;
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

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
