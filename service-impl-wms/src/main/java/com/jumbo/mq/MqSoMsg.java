package com.jumbo.mq;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * 订单信息
 * 
 * @author dzz
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mqsomsg")
public class MqSoMsg implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2203004731415944032L;

    /**
     * 商城订单号
     */
    private String code;

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 付款时间(从3.0.6版本开始 移至订单支付方式信息中)
     */
    @Deprecated
    private Date paymentTime;

    /**
     * 付款方式(从3.0.6版本开始 移至订单支付方式信息中)
     */
    @Deprecated
    private String paymentType;

    /**
     * 是否需要发票
     */
    private Boolean isNeededInvoice;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 发票内容
     */
    private String invoiceContent;

    /**
     * 实际运费
     */
    private BigDecimal acutalTransFee;

    /**
     * 商品总金额 该金额为整单最终实际货款. (不包含运费且未扣减虚拟货币[实际支付金额])
     */
    private BigDecimal totalActual;

    /**
     * 订单使用总积分点 (从3.0.6版本开始停用该字段)
     */
    @Deprecated
    private BigDecimal totalPointUsed;

    /**
     * 外部积分点
     */
    private BigDecimal totalOuterPoint;

    /**
     * 内部积分点
     */
    private BigDecimal totalInnerPoint;

    /**
     * 订单使用虚拟货币总金额
     */
    private BigDecimal totalVc;

    /**
     * 单据备注
     */
    private String remark;

    /**
     * 卖家备注
     */
    private String sellerMemo;

    /**
     * 会员帐号 (从3.0.6版本开始 移至发货信息中)
     */
    @Deprecated
    private String account;

    /**
     * 会员邮箱 (从3.0.6版本开始 移至发货信息中)
     */
    @Deprecated
    private String memberEmail;

    /**
     * 优惠券代码 (从3.0.6版本开始 移至订单促销信息中)
     */
    @Deprecated
    private String couponCode;

    /**
     * 优惠券类型 (从3.0.6版本开始 移至订单促销信息中)
     */
    @Deprecated
    private String couponType; // 1:现金券 2:折扣券

    /**
     * 优惠券抵扣金额 (从3.0.6版本开始 移至订单促销信息中)
     */
    @Deprecated
    private BigDecimal couponDiscountFee;

    /**
     * 是否需要包装
     */
    private Boolean isNeededPacking;

    /**
     * 配送备注 格式：{psDate:"",period:""} (Godiva使用 格式：{psDate:"",period:""},从3.0.6版本开始 移至字段expProp1中)
     */
    @Deprecated
    private String deliveryRemark;

    /**
     * 是否会员订单 (Esprit官方商城使用,从3.0.6版本开始 移至字段expProp1中)
     */
    @Deprecated
    private Boolean isMemberOrder;

    /**
     * Esprit官方商城使用,从3.0.6版本开始 移至字段expProp1中
     */
    @Deprecated
    private BigDecimal gcAmt;

    /**
     * Esprit官方商城使用,从3.0.6版本开始 移至字段expProp1中
     */
    @Deprecated
    private BigDecimal sgcAmt;

    /**
     * 积分抵扣金额 esprit报表需要，商城端将该金额作为了促销已从订单总金额中扣除 4.0版本后积分不属于促销而是一种支付方式
     */
    @Deprecated
    private BigDecimal bpcAmt;

    /**
     * 会员卡号 esprit报表需要，4.0版本后移至平台会员信息vipCode字段上
     */
    @Deprecated
    private String cardNo;

    /**
     * oms店铺ID (目前联通官方商城订单上必须带上对应oms店铺ID信息，后续所有商城订单均需要带上该信息)
     */
    private Long omsShopId;

    /**
     * 数据来源
     */
    private String source;

    /**
     * json格式，若商城有某些单独的字段信息需要传送，以json格式通过该字段传送
     */
    private String extProp1;

    /**
     * 物流供应商编码(官方商城目前不支持物流选择，该字段暂时冗余) 从3.0.6版本开始 移至发货信息中
     */
    @Deprecated
    private String lpCode;

    /**
     * 发货信息 从3.0.6版本开始 移至字段deliveryInfoList
     */
    @Deprecated
    private MqDeliveryInfoMsg deliveryInfo;

    /**
     * 发货信息集 (一个订单多发货信息，使用该字段的商城则以该集合第一条为该订单发货信息，若未使用该字段的商城则保持使用deliveryInfo字段不变)
     */
    @XmlElements({@XmlElement(name = "mqdeliveryinfomsg", type = MqDeliveryInfoMsg.class)})
    private List<MqDeliveryInfoMsg> deliveryInfoList;

    /**
     * 订单明细
     */
    @XmlElements({@XmlElement(name = "mqsolinemsg", type = MqSoLineMsg.class)})
    private List<MqSoLineMsg> lines;

    /**
     * 平台会员信息
     */
    private MqPlatformMemberMsg platformMemInfo;

    /**
     * 平台订单类型
     */
    private String platformOrderType;
    
    /**
     * 外部平台订单号
     */
    private String outerOrderCode;
    
    /**
     * 外部平台原始订单号
     */
    private String outerOrderCodeSource;
    
    /**
     * 订单来源-对应oms订单类型（orderType）
     */
    private int orderType;
    
    /**
     * 买家订单备注
     */
    private String buyerMemo;
    
    /**
     * 店铺id
     */
    private Long shopId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Boolean getIsNeededInvoice() {
        return isNeededInvoice;
    }

    public void setIsNeededInvoice(Boolean isNeededInvoice) {
        this.isNeededInvoice = isNeededInvoice;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public BigDecimal getAcutalTransFee() {
        return acutalTransFee;
    }

    public void setAcutalTransFee(BigDecimal acutalTransFee) {
        this.acutalTransFee = acutalTransFee;
    }

    public BigDecimal getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(BigDecimal totalActual) {
        this.totalActual = totalActual;
    }

    public BigDecimal getTotalPointUsed() {
        return totalPointUsed;
    }

    public void setTotalPointUsed(BigDecimal totalPointUsed) {
        this.totalPointUsed = totalPointUsed;
    }

    public BigDecimal getTotalOuterPoint() {
        return totalOuterPoint;
    }

    public void setTotalOuterPoint(BigDecimal totalOuterPoint) {
        this.totalOuterPoint = totalOuterPoint;
    }

    public BigDecimal getTotalInnerPoint() {
        return totalInnerPoint;
    }

    public void setTotalInnerPoint(BigDecimal totalInnerPoint) {
        this.totalInnerPoint = totalInnerPoint;
    }

    public BigDecimal getTotalVc() {
        return totalVc;
    }

    public void setTotalVc(BigDecimal totalVc) {
        this.totalVc = totalVc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSellerMemo() {
        return sellerMemo;
    }

    public void setSellerMemo(String sellerMemo) {
        this.sellerMemo = sellerMemo;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public BigDecimal getCouponDiscountFee() {
        return couponDiscountFee;
    }

    public void setCouponDiscountFee(BigDecimal couponDiscountFee) {
        this.couponDiscountFee = couponDiscountFee;
    }

    public Boolean getIsNeededPacking() {
        return isNeededPacking;
    }

    public void setIsNeededPacking(Boolean isNeededPacking) {
        this.isNeededPacking = isNeededPacking;
    }

    public String getDeliveryRemark() {
        return deliveryRemark;
    }

    public void setDeliveryRemark(String deliveryRemark) {
        this.deliveryRemark = deliveryRemark;
    }

    public Boolean getIsMemberOrder() {
        return isMemberOrder;
    }

    public void setIsMemberOrder(Boolean isMemberOrder) {
        this.isMemberOrder = isMemberOrder;
    }

    public BigDecimal getGcAmt() {
        return gcAmt;
    }

    public void setGcAmt(BigDecimal gcAmt) {
        this.gcAmt = gcAmt;
    }

    public BigDecimal getSgcAmt() {
        return sgcAmt;
    }

    public void setSgcAmt(BigDecimal sgcAmt) {
        this.sgcAmt = sgcAmt;
    }

    public BigDecimal getBpcAmt() {
        return bpcAmt;
    }

    public void setBpcAmt(BigDecimal bpcAmt) {
        this.bpcAmt = bpcAmt;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Long getOmsShopId() {
        return omsShopId;
    }

    public void setOmsShopId(Long omsShopId) {
        this.omsShopId = omsShopId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getExtProp1() {
        return extProp1;
    }

    public void setExtProp1(String extProp1) {
        this.extProp1 = extProp1;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public MqDeliveryInfoMsg getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(MqDeliveryInfoMsg deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public List<MqDeliveryInfoMsg> getDeliveryInfoList() {
        return deliveryInfoList;
    }

    public void setDeliveryInfoList(List<MqDeliveryInfoMsg> deliveryInfoList) {
        this.deliveryInfoList = deliveryInfoList;
    }

    public List<MqSoLineMsg> getLines() {
        return lines;
    }

    public void setLines(List<MqSoLineMsg> lines) {
        this.lines = lines;
    }

    public MqPlatformMemberMsg getPlatformMemInfo() {
        return platformMemInfo;
    }

    public void setPlatformMemInfo(MqPlatformMemberMsg platformMemInfo) {
        this.platformMemInfo = platformMemInfo;
    }

    public String getPlatformOrderType() {
        return platformOrderType;
    }

    public void setPlatformOrderType(String platformOrderType) {
        this.platformOrderType = platformOrderType;
    }

	public String getOuterOrderCode() {
		return outerOrderCode;
	}

	public void setOuterOrderCode(String outerOrderCode) {
		this.outerOrderCode = outerOrderCode;
	}

	public String getOuterOrderCodeSource() {
		return outerOrderCodeSource;
	}

	public void setOuterOrderCodeSource(String outerOrderCodeSource) {
		this.outerOrderCodeSource = outerOrderCodeSource;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public String getBuyerMemo() {
		return buyerMemo;
	}

	public void setBuyerMemo(String buyerMemo) {
		this.buyerMemo = buyerMemo;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

}
