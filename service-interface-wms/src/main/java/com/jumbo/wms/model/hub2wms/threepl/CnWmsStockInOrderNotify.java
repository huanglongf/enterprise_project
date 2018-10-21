package com.jumbo.wms.model.hub2wms.threepl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 菜鸟入库通知单中间表(菜鸟下发)
 * 
 */
@Entity
@Table(name = "T_WH_CN_STOCK_IN_NOTIFY")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CnWmsStockInOrderNotify extends BaseModel {

    private static final long serialVersionUID = 2378424624510544519L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 相关作业单id
     */
    private Long staId;
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
     * 仓储中心入库订单编码
     */
    private String orderCode;
    /**
     * 单据类型： 302调拨入库单 501销退入库单 601采购入库单 904普通入库单306 B2B入库单 604 B2B干线退货入库 704 库存状态调整入库
     */
    private Integer orderType;
    /**
     * 自定义文本透传至WMS 加工归还 委外归还 借出归还 内部归还
     */
    private String inboundTypeDesc;
    /**
     * 仓库订单需要履行服务标识 适用如下场景： 退货入库: 8:退换货 9:上门服务 13: 退货收取发票 31：退货入库 36：退货时同时换货 其他单据忽略
     */
    private Integer orderFlag;

    /**
     * 适用如下场景:退货入库: 订单来源（213 天猫，201 淘宝，214 京东，202 1688 阿里中文站 ，203 苏宁在线，204 亚马逊中国，205 当当，208 1号店，207
     * 唯品会，209 国美在线，210 拍拍，206 易贝ebay，211 聚美优品，212 乐蜂网，215 邮乐，216 凡客，217 优购，218 银泰，219 易讯，221
     * 聚尚网，222 蘑菇街，223 POS门店，301 其他 不在范围之内请忽略） 其他单据忽略
     */
    private Integer orderSource;

    /**
     * 订单创建时间(格式为 YYYY-MM-DD HH:mm:ss)
     */
    private Date orderCreateTime;

    /**
     * 供应商编码，往来单位编码 适用如下场景： 采购入库单
     */
    private String supplierCode;

    /**
     * 供应商名称，往来单位名称 适用如下场景：采购入库单
     */
    private String supplierName;

    /**
     * 配送公司编码 适用如下场景：销退买家拒签：原发货单的配送公司编码； 销退上门取件，消费者退货，商家下单：新生成的运单号所属配送公司的编码
     */
    private String tmsServiceCode;

    /**
     * 配送公司名称 适用如下场景： 销退买家拒签：原发货单的配送公司名称 销退上门取件，消费者退货，商家下单：新生成的运单号所属配送公司的名称
     */
    private String tmsServiceName;

    /**
     * 运单号
     * 
     * 适用如下场景： 销退买家拒签：原发货单的运单号 销退上门取件，消费者退货，商家下单：新生成的运单号
     */
    private String tmsOrderCode;

    /**
     * 原仓储作业单号 适用如下场景： 销退入库单：原发货单号，注意：原发货单可能是其他仓库发出
     * 
     */
    private String prevOrderCode;

    /**
     * 退货原因：销退场景下，如可能请提供退货的原因，多个退货原因用；号分开
     */
    private String returnReason;

    /**
     * (分批下发控制参数)下发总共ITEM数
     */
    private Integer totalOrderItemCount;

    /**
     * (分批下发控制参数)是否分批下发: 0 最后一次下发 1 多次发送
     */
    private Integer distributeType;

    /**
     * 发件方信息
     */
    private CnSenderInfo cnSenderInfo;

    /**
     * 拓展属性数据 详见 订单下发扩展字段
     */
    private String extendFields;

    /**
     * 备注:销退时留言备注填充到此字段
     */
    private String remark;

    /**
     * 入库单状态 8:新建中， 0: 新建 ，1:执行中， 5:完成， -1: 错误，-2:取消
     */
    private Integer status;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WCSI", sequenceName = "S_T_WH_CN_STOCK_IN_NOTIFY", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WCSI")
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


    @Column(name = "OWNER_USER_ID")
    public String getOwnerUserId() {
        return ownerUserId;
    }


    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    @Column(name = "OWNER_USER_NAME")
    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    @Column(name = "STORE_CODE")
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
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

    @Column(name = "INBOUND_TYPE_DESC")
    public String getInboundTypeDesc() {
        return inboundTypeDesc;
    }

    public void setInboundTypeDesc(String inboundTypeDesc) {
        this.inboundTypeDesc = inboundTypeDesc;
    }

    @Column(name = "ORDER_FLAG")
    public Integer getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(Integer orderFlag) {
        this.orderFlag = orderFlag;
    }

    @Column(name = "ORDER_SOURCE")
    public Integer getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(Integer orderSource) {
        this.orderSource = orderSource;
    }

    @Column(name = "ORDER_CREATE_TIME")
    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    @Column(name = "SUPPLIER_CODE")
    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    @Column(name = "SUPPLIER_NAME")
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Column(name = "TMS_SERVICE_CODE")
    public String getTmsServiceCode() {
        return tmsServiceCode;
    }

    public void setTmsServiceCode(String tmsServiceCode) {
        this.tmsServiceCode = tmsServiceCode;
    }

    @Column(name = "TMS_SERVICE_NAME")
    public String getTmsServiceName() {
        return tmsServiceName;
    }

    public void setTmsServiceName(String tmsServiceName) {
        this.tmsServiceName = tmsServiceName;
    }

    @Column(name = "TMS_ORDER_CODE")
    public String getTmsOrderCode() {
        return tmsOrderCode;
    }

    public void setTmsOrderCode(String tmsOrderCode) {
        this.tmsOrderCode = tmsOrderCode;
    }

    @Column(name = "PREV_ORDER_CODE")
    public String getPrevOrderCode() {
        return prevOrderCode;
    }

    public void setPrevOrderCode(String prevOrderCode) {
        this.prevOrderCode = prevOrderCode;
    }

    @Column(name = "RETURN_REASON")
    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    @Column(name = "TOTAL_ORDER_ITEM_COUNT")
    public Integer getTotalOrderItemCount() {
        return totalOrderItemCount;
    }

    public void setTotalOrderItemCount(Integer totalOrderItemCount) {
        this.totalOrderItemCount = totalOrderItemCount;
    }

    @Column(name = "DISTRIBUTE_TYPE")
    public Integer getDistributeType() {
        return distributeType;
    }

    public void setDistributeType(Integer distributeType) {
        this.distributeType = distributeType;
    }

    @OneToOne
    @PrimaryKeyJoinColumn
    public CnSenderInfo getCnSenderInfo() {
        return cnSenderInfo;
    }

    public void setCnSenderInfo(CnSenderInfo CnSenderInfo) {
        this.cnSenderInfo = CnSenderInfo;
    }

    @Column(name = "EXTEND_FIELDS")
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

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
