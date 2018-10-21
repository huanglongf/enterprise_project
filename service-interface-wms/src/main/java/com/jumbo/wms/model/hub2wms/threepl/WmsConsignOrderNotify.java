package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class WmsConsignOrderNotify implements Serializable {

    private static final long serialVersionUID = -8801231380535214147L;
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
     * 仓储中心订单编码
     */
    private String orderCode;
    /**
     * 单据类型 ： 201 交易出库单502 换货出库单
     */
    private Integer orderType;
    /**
     * 店铺id
     */
    private String userId;
    /**
     * 店铺名称
     */
    private String userName;
    /**
     * 
     仓库订单需要履行服务标识： 1: cod –货到付款 2: limit-限时配送 3: presell-预售 4:invoiceinfo-需要发票 8:退换货 9:上门服务 10:
     * 不可改配送方式 12: 买家家承担运费 20: 使用干配服务 22：配送方式 32：标识期货预售的预发货单 34：生鲜－常温服务标 35：生鲜－冷链服务标 36：退货时同时换货
     * 37：负卖
     */
    private String orderFlag;
    /**
     * 订单来源（213 天猫，201 淘宝，214 京东，202 1688 阿里中文站 ，203 苏宁在线，204 亚马逊中国，205 当当，208 1号店，207 唯品会，209
     * 国美在线，210 拍拍，206 易贝ebay，211 聚美优品，212 乐蜂网，215 邮乐，216 凡客，217 优购，218 银泰，219 易讯，221 聚尚网，222
     * 蘑菇街，223 POS门店，301 其他 不在范围之内请忽略）
     */
    private Integer orderSource;
    /**
     * 订单创建时间(格式为 YYYY-MM-DD HH:mm:ss)
     */
    private Date orderCreateTime;
    /**
     * 订单支付时间(格式为 YYYY-MM-DD HH:mm:ss)
     */
    private Date orderPayTime;
    /**
     * 订单审核时间(格式为 YYYY-MM-DD HH:mm:ss)
     */
    private Date orderExaminationTime;
    /**
     * 前台交易创建时间(格式为 YYYY-MM-DD HH:mm:ss)
     */
    private Date orderShopCreateTime;
    /**
     * 订单金额（=总商品金额-订单优惠金额+快递费用），单位分
     */
    private Long orderAmount;
    /**
     * 订单优惠金额，单位分
     */
    private Long discountAmount;
    /**
     * 订单应收金额，消费者还需要付多少钱，单位分
     */
    private Long arAmount;
    /**
     * 订单已收金额，消费者已经支付多少钱，单位分
     */
    private Long gotAmount;
    /**
     * 快递费用，单位分
     */
    private Long postfee;
    /**
     * 服务费，单位分，例如COD
     */
    private Long serviceFee;
    /**
     * 配送编码
     */
    private String tmsServiceCode;
    /**
     * 配送公司名称
     */
    private String tmsServiceName;
    /**
     * 运单号，指定运单号发货业务
     */
    private String tmsOrderCode;
    /**
     * 原仓储作业单号
     * 
     * 适用如下场景： 换货出库单：原发货单号，注意：原发货单可能是其他仓库发出
     */
    private String prevOrderCode;
    /**
     * 配送要求
     */
    private DeliverRequirements deliverRequirements;
    /**
     * 收货方信息 一般是本地语言
     */
    private ReceiverInfo receiverInfo;
    /**
     * 通用收货方信息 一般是英语
     */
    private ReceiverInfo uniReceiverInfo;
    /**
     * 发货方信息
     */
    private SenderInfo senderInfo;
    /**
     * 发票信息列表
     */
    private List<InvoinceItem> invoiceInfoList;
    /**
     * 订单商品列表
     */
    private List<WmsConsignOrderItem> orderItemList;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
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

    public Date getOrderPayTime() {
        return orderPayTime;
    }

    public void setOrderPayTime(Date orderPayTime) {
        this.orderPayTime = orderPayTime;
    }

    public Date getOrderExaminationTime() {
        return orderExaminationTime;
    }

    public void setOrderExaminationTime(Date orderExaminationTime) {
        this.orderExaminationTime = orderExaminationTime;
    }

    public Date getOrderShopCreateTime() {
        return orderShopCreateTime;
    }

    public void setOrderShopCreateTime(Date orderShopCreateTime) {
        this.orderShopCreateTime = orderShopCreateTime;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Long getArAmount() {
        return arAmount;
    }

    public void setArAmount(Long arAmount) {
        this.arAmount = arAmount;
    }

    public Long getGotAmount() {
        return gotAmount;
    }

    public void setGotAmount(Long gotAmount) {
        this.gotAmount = gotAmount;
    }

    public Long getPostfee() {
        return postfee;
    }

    public void setPostfee(Long postfee) {
        this.postfee = postfee;
    }

    public Long getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Long serviceFee) {
        this.serviceFee = serviceFee;
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

    public DeliverRequirements getDeliverRequirements() {
        return deliverRequirements;
    }

    public void setDeliverRequirements(DeliverRequirements deliverRequirements) {
        this.deliverRequirements = deliverRequirements;
    }

    public ReceiverInfo getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(ReceiverInfo receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public ReceiverInfo getUniReceiverInfo() {
        return uniReceiverInfo;
    }

    public void setUniReceiverInfo(ReceiverInfo uniReceiverInfo) {
        this.uniReceiverInfo = uniReceiverInfo;
    }

    public SenderInfo getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(SenderInfo senderInfo) {
        this.senderInfo = senderInfo;
    }

    public List<InvoinceItem> getInvoiceInfoList() {
        return invoiceInfoList;
    }

    public void setInvoiceInfoList(List<InvoinceItem> invoiceInfoList) {
        this.invoiceInfoList = invoiceInfoList;
    }

    public List<WmsConsignOrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<WmsConsignOrderItem> orderItemList) {
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
