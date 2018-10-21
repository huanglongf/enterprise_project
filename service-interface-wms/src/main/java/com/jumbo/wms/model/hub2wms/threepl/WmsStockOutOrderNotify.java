package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class WmsStockOutOrderNotify implements Serializable {

    private static final long serialVersionUID = 739066481480650455L;
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
     * 分批下发控制参数
     */
    private BatchSendCtrlParam batchSendCtrlParam;
    /**
     * 收货方信息
     */
    private ReceiverInfo receiverInfo;
    /**
     * 发件方信息
     */
    private SenderInfo senderInfo;
    /**
     * 出库单商品信息列表
     */
    private List<WmsStockOutOrderItem> orderItemList;
    /**
     * 拓展属性数据 详见 订单下发扩展字段
     */
    private Map<Object, Object> extendFields;
    /**
     * 备注
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

    public String getOutboundTypeDesc() {
        return outboundTypeDesc;
    }

    public void setOutboundTypeDesc(String outboundTypeDesc) {
        this.outboundTypeDesc = outboundTypeDesc;
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }

    public String getPickName() {
        return pickName;
    }

    public void setPickName(String pickName) {
        this.pickName = pickName;
    }

    public String getPickCall() {
        return pickCall;
    }

    public void setPickCall(String pickCall) {
        this.pickCall = pickCall;
    }

    public String getCarriersName() {
        return carriersName;
    }

    public void setCarriersName(String carriersName) {
        this.carriersName = carriersName;
    }

    public String getPickId() {
        return pickId;
    }

    public void setPickId(String pickId) {
        this.pickId = pickId;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public BatchSendCtrlParam getBatchSendCtrlParam() {
        return batchSendCtrlParam;
    }

    public void setBatchSendCtrlParam(BatchSendCtrlParam batchSendCtrlParam) {
        this.batchSendCtrlParam = batchSendCtrlParam;
    }

    public ReceiverInfo getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(ReceiverInfo receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public SenderInfo getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(SenderInfo senderInfo) {
        this.senderInfo = senderInfo;
    }

    public List<WmsStockOutOrderItem> getOrderItemList() {
        if (this.orderItemList == null) {
            this.orderItemList = new ArrayList<WmsStockOutOrderItem>();
        }
        return orderItemList;
    }

    public void setOrderItemList(List<WmsStockOutOrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public Map<Object, Object> getExtendFields() {
        return extendFields;
    }

    public void setExtendFields(Map<Object, Object> extendFields) {
        this.extendFields = extendFields;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
