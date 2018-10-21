package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WmsStockInOrderNotify implements Serializable {

    private static final long serialVersionUID = 7106467540291222125L;
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
     * 分批下发控制参数
     */
    private BatchSendCtrlParam batchSendCtrlParam;
    /**
     * 发件方信息
     */
    private SenderInfo senderInfo;
    /**
     * 入库单商品信息列表
     */
    private List<WmsStockInOrderItem> orderItemList;
    /**
     * 装箱列表
     */
    private List<WmsStockInCaseInfo> caseInfoList;
    /**
     * 拓展属性数据 详见 订单下发扩展字段
     */
    private String extendFields;
    /**
     * 备注:销退时留言备注填充到此字段
     */
    private String remark;

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getInboundTypeDesc() {
        return inboundTypeDesc;
    }

    public void setInboundTypeDesc(String inboundTypeDesc) {
        this.inboundTypeDesc = inboundTypeDesc;
    }

    public Integer getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(Integer orderFlag) {
        this.orderFlag = orderFlag;
    }

    public Integer getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(Integer orderSource) {
        this.orderSource = orderSource;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getTmsServiceCode() {
        return tmsServiceCode;
    }

    public void setTmsServiceCode(String tmsServiceCode) {
        this.tmsServiceCode = tmsServiceCode;
    }

    public String getTmsServiceName() {
        return tmsServiceName;
    }

    public void setTmsServiceName(String tmsServiceName) {
        this.tmsServiceName = tmsServiceName;
    }

    public String getTmsOrderCode() {
        return tmsOrderCode;
    }

    public void setTmsOrderCode(String tmsOrderCode) {
        this.tmsOrderCode = tmsOrderCode;
    }

    public String getPrevOrderCode() {
        return prevOrderCode;
    }

    public void setPrevOrderCode(String prevOrderCode) {
        this.prevOrderCode = prevOrderCode;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public BatchSendCtrlParam getBatchSendCtrlParam() {
        return batchSendCtrlParam;
    }

    public void setBatchSendCtrlParam(BatchSendCtrlParam batchSendCtrlParam) {
        this.batchSendCtrlParam = batchSendCtrlParam;
    }

    public SenderInfo getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(SenderInfo senderInfo) {
        this.senderInfo = senderInfo;
    }

    public List<WmsStockInOrderItem> getOrderItemList() {
        if (this.orderItemList == null) {
            this.orderItemList = new ArrayList<WmsStockInOrderItem>();
        }
        return orderItemList;
    }

    public void setOrderItemList(List<WmsStockInOrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public List<WmsStockInCaseInfo> getCaseInfoList() {
        if (this.caseInfoList == null) {
            this.caseInfoList = new ArrayList<WmsStockInCaseInfo>();
        }
        return caseInfoList;
    }

    public void setCaseInfoList(List<WmsStockInCaseInfo> caseInfoList) {
        this.caseInfoList = caseInfoList;
    }

    public String getExtendFields() {
        return extendFields;
    }

    public void setExtendFields(String extendFields) {
        this.extendFields = extendFields;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
