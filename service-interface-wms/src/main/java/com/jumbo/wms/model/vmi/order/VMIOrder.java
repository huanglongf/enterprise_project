package com.jumbo.wms.model.vmi.order;

import java.math.BigDecimal;
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

@Entity
@Table(name = "T_VMI_ORDER")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class VMIOrder extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 780289698577066303L;

    private Long id;

    private String platform;
    // 宝尊订单号
    private String code;
    // 店内编码
    private String storeCode;
    // 支付时间
    private Date payTime;
    // 平台订单号
    private String slipCode;
    // 收货人
    private String receiver;
    // 收件人手机号码
    private String mobile;
    // 收件人电话号码
    private String telephone;
    // 收件人省份
    private String province;
    // 收件人城市
    private String city;
    // 收件人区县
    private String district;
    // 收件人地址
    private String address;
    // 收件人邮编
    private String zipCode;
    // 支付方法
    private String paymentMethod;
    // 备注
    private String remark;
    // 配货方式
    private String deliveryType;
    // 物流费用
    private BigDecimal logisticFee;
    // 订单总金额
    private BigDecimal total;
    // 货物重量
    private BigDecimal goodsWeight;
    // 促销编码
    private String promotionCode;

    private Date createTime;

    private Integer status;

    private Date deliveryTime;

    /**
     * 消息批次ID
     */
    private Long msgBatchId;

    private Long shopId;

    /**
     * 所有行的总价
     */
    private BigDecimal merchandiseTotal;

    /**
     * 运输公司
     */
    private String routing;

    /**
     * 要求送货时间
     */
    private String releaseDate;

    /**
     * gdv 订单类型是'A'销售'D'取消
     */
    private String orderType;


    private String paymentType;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_VMI_ORDER", sequenceName = "S_T_VMI_ORDER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VMI_ORDER")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "MOBILE", length = 20)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "TELEPHONE", length = 20)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "PROVINCE", length = 20)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "CITY", length = 20)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "DISTRICT", length = 20)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "PLATFORM", length = 20)
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Column(name = "ORDER_CODE", length = 20)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "OWNER", length = 20)
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Column(name = "PAY_TIME")
    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    @Column(name = "slipCode", length = 20)
    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    @Column(name = "RECEIVER", length = 20)
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Column(name = "ADDRESS", length = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "ZIPCODE", length = 6)
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Column(name = "PAYMENT_METHOD", length = 6)
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Column(name = "REMARK", length = 100)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "DELIVERY_TYPE", length = 20)
    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    @Column(name = "logistic_Fee")
    public BigDecimal getLogisticFee() {
        return logisticFee;
    }

    public void setLogisticFee(BigDecimal logisticFee) {
        this.logisticFee = logisticFee;
    }

    @Column(name = "total")
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Column(name = "GOODS_WEIGHT")
    public BigDecimal getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(BigDecimal goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    @Column(name = "PROMOTION_CODE", length = 20)
    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "MSG_BATCH_ID")
    public Long getMsgBatchId() {
        return msgBatchId;
    }

    public void setMsgBatchId(Long msgBatchId) {
        this.msgBatchId = msgBatchId;
    }

    @Column(name = "SHOP_ID")
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Column(name = "DELIVERY_TIME")
    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Column(name = "MERCHANDISE_TOTAL")
    public BigDecimal getMerchandiseTotal() {
        return merchandiseTotal;
    }

    public void setMerchandiseTotal(BigDecimal merchandiseTotal) {
        this.merchandiseTotal = merchandiseTotal;
    }

    @Column(name = "ROUTING", length = 30)
    public String getRouting() {
        return routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
    }

    @Column(name = "RELEASE_DATE", length = 50)
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Column(name = "ORDER_TYPE", length = 20)
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Column(name = "PAYMENT_TYPE", length = 50)
    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }



}
