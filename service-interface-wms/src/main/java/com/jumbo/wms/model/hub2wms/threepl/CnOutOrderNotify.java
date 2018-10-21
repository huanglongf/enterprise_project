package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

/**
 * 菜鸟大宝出库通知
 * 
 * @author hui.li
 *
 */
@Entity
@Table(name = "T_WH_CN_OUT_ORDER_NOTIFY")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CnOutOrderNotify implements Serializable {

    private static final long serialVersionUID = 7203690719956581283L;

    private Long id;
    /**
     * 货主ID
     */
    private String ownerUserId;
    /**
     * 货主名称
     */
    private String ownerUserName;
    /**
     * 仓储编码
     */
    private String storeCode;
    /**
     * 仓储中心出库订单编码
     */
    private String orderCode;
    /**
     * 操作类型：301 调拨出库单 901 普通出库单 (如货主拉走一部分货) 305 B2B出库单 703 库存状态调整出库 701 (大家电)盘点出库
     */
    private Integer orderType;
    /**
     * 自定义文本： 加工领料 委外领料 借出领用 内部领用
     */
    private String outboundTypeDesc;
    /**
     * 仓库订单需要履行服务标识
     */
    private String orderFlag;
    /**
     * 订单创建时间(格式为 YYYY-MM-DD HH:mm:ss)
     */
    private Date orderCreateTime;
    /**
     * 要求出库时间: 格式为YYYY/MM/DD HH:MM:SS
     */
    private Date sendTime;
    /**
     * 出库方式（自提，非自提）
     */
    private String transportMode;
    /**
     * 取件人姓名
     */
    private String pickName;
    /**
     * 取件人电话
     */
    private String pickCall;
    /**
     * 承运商名称
     */
    private String carriersName;
    /**
     * 取件人身份证
     */
    private String pickId;
    /**
     * 车牌号
     */
    private String carNo;


    /**
     * 下发总共ITEM数
     */
    private Integer totalOrderItemCount;
    /**
     * 是否分批下发: 0 最后一次下发 1 多次发送
     */
    private Integer distributeType;


    /**
     * 拓展属性数据 详见 订单下发扩展字段
     */
    private String extendFields;
    /**
     * 备注
     */
    private String remark;

    /**
     * 是否执行
     */
    private Integer status;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CN_OUT_ORDER_NOTIFY", sequenceName = "S_T_WH_CN_OUT_ORDER_NOTIFY", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CN_OUT_ORDER_NOTIFY")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "OWNERUSERID")
    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    @Column(name = "OWNERUSERNAME")
    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    @Column(name = "STORECODE")
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Column(name = "ORDERCODE")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "ORDERTYPE")
    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    @Column(name = "OUTBOUNDTYPEDESC")
    public String getOutboundTypeDesc() {
        return outboundTypeDesc;
    }

    public void setOutboundTypeDesc(String outboundTypeDesc) {
        this.outboundTypeDesc = outboundTypeDesc;
    }

    @Column(name = "ORDERFLAG")
    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag;
    }

    @Column(name = "ORDERCREATETIME")
    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    @Column(name = "SENDTIME")
    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Column(name = "TRANSPORTMODE")
    public String getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }

    @Column(name = "PICKNAME")
    public String getPickName() {
        return pickName;
    }

    public void setPickName(String pickName) {
        this.pickName = pickName;
    }

    @Column(name = "PICKCALL")
    public String getPickCall() {
        return pickCall;
    }

    public void setPickCall(String pickCall) {
        this.pickCall = pickCall;
    }

    @Column(name = "CARRIERSNAME")
    public String getCarriersName() {
        return carriersName;
    }

    public void setCarriersName(String carriersName) {
        this.carriersName = carriersName;
    }

    @Column(name = "PICKID")
    public String getPickId() {
        return pickId;
    }

    public void setPickId(String pickId) {
        this.pickId = pickId;
    }

    @Column(name = "CARNO")
    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }


    @Column(name = "TOTALORDERITEMCOUNT")
    public Integer getTotalOrderItemCount() {
        return totalOrderItemCount;
    }

    public void setTotalOrderItemCount(Integer totalOrderItemCount) {
        this.totalOrderItemCount = totalOrderItemCount;
    }

    @Column(name = "DISTRIBUTETYPE")
    public Integer getDistributeType() {
        return distributeType;
    }

    public void setDistributeType(Integer distributeType) {
        this.distributeType = distributeType;
    }

    @Column(name = "EXTENDFIELDS")
    public String getExtendFields() {
        return extendFields;
    }

    public void setExtendFields(String extendFields) {
        this.extendFields = extendFields;
    }

    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
