package com.jumbo.mq;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 订单行信息
 * 
 * @author dzz
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mqsolinemsg")
public class MqSoLineMsg implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8678238017337518080L;

    /**
     * jmskucode(oms到尺码颜色) 从3.0.6版本停用
     */
    @Deprecated
    private String jmskuCode;

    /**
     * 条码(供应商到颜色尺码) 从3.0.6版本停用
     */
    @Deprecated
    private String barCode;

    /**
     * oms与bs SKU匹配唯一标识
     */
    private String extentionCode;

    /**
     * 数量
     */
    private Integer qty;

    /**
     * 单价(折前)
     */
    private BigDecimal unitPrice;

    /**
     * 市场价
     */
    private BigDecimal listPrice;

    /**
     * markdown价格 在品牌没有MarkDown活动时，此价格=null，当且仅当品牌制定有MarkDown活动时记录，
     * 商品的基价其实就是nvl（mdPrice，listPrice）这里不用min函数来说明其实是想为未来预留markUp的需求
     */
    private BigDecimal mdPrice;

    /**
     * 行总计 (扣减所有活动优惠且未扣减积分抵扣) sum(sl.totalActual)=so.totalActual totalActual + discountFee =
     * unitPrice × qty
     */
    private BigDecimal totalActual;

    /**
     * 行优惠总金额(不包含积分抵扣)
     */
    private BigDecimal discountFee;

    /**
     * 订单行使用总积分点 该字段先停用。暂且由oms基于现有数据计算所得（outerPointValue*100+innerPointValue*100),
     * 后续待所有商城切换到新版本且虚拟货币过k3逻辑相应调整完毕后再移除该字段
     */
    @Deprecated
    private BigDecimal totalPointUsed;

    /**
     * 订单行使用外部积分金额
     */
    private BigDecimal outerPointValue;

    /**
     * 订单行使用内部积分金额
     */
    private BigDecimal innerPointValue;

    /**
     * 其它虚拟货币金额（如预付卡）
     */
    private BigDecimal otherVc;

    /**
     * 平台订单行ID
     */
    private Long platformLineId;

    /**
     * 扩展字段1 json格式，若商城有某些特有的字段信息需要传送，以json格式通过该字段传送
     */
    private String extProp1;

    /**
     * 优惠券代码 Esprit官方商城使用,从3.0.6版本开始 移至订单促销信息中
     */
    @Deprecated
    private String couponCode;

    /**
     * 商品组合搭配说明
     * JSON格式:{skus:[{skuCode:"",sidaiCode:"",peishiCode:"",qty:"",wishCard:"",wishComment:""}]}
     * 从3.0.6版本开始 移至字段extProp1中
     */
    @Deprecated
    private String skuComboRemark;
    
    /**
     * 商品单价=外部平台传递单价
     */
    private BigDecimal platformUnitPrice;// //
    /**
     * 行总计 = platformUnitPrice*QTY
     */
    private BigDecimal platformTotalActual;// ///
    
    /**
     * 行总计 所有soLine的总合
     */
    private BigDecimal lineTotal;
    
    /**
     * 商品单价
     */
    private BigDecimal lineUnitPrice;
    
    /**
     * 扣减所有优惠且扣减积分抵扣金额后行总金额
     */
    private BigDecimal finalTotalActual;
    /**
     * 扣减所有优惠且扣减积分抵扣金额后行单价
     */
    private BigDecimal finalUnitPrice;
    
    /**
     * 行积分抵扣总金额 = SoLine.totalPointUsed/100
     */
    private BigDecimal totalPointUsedFee;
    
    /**
     * 订单行所享受的促销活动
     */
    @XmlElements({@XmlElement(name = "mqsopromotionmsg", type = MqSoPromotionMsg.class)})
    private List<MqSoPromotionMsg> promotions;

    public String getExtentionCode() {
        return extentionCode;
    }

    public void setExtentionCode(String extentionCode) {
        this.extentionCode = extentionCode;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getMdPrice() {
        return mdPrice;
    }

    public void setMdPrice(BigDecimal mdPrice) {
        this.mdPrice = mdPrice;
    }

    public BigDecimal getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(BigDecimal totalActual) {
        this.totalActual = totalActual;
    }

    public BigDecimal getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(BigDecimal discountFee) {
        this.discountFee = discountFee;
    }

    public BigDecimal getTotalPointUsed() {
        return totalPointUsed;
    }

    public void setTotalPointUsed(BigDecimal totalPointUsed) {
        this.totalPointUsed = totalPointUsed;
    }

    public BigDecimal getOuterPointValue() {
        return outerPointValue;
    }

    public void setOuterPointValue(BigDecimal outerPointValue) {
        this.outerPointValue = outerPointValue;
    }

    public BigDecimal getInnerPointValue() {
        return innerPointValue;
    }

    public void setInnerPointValue(BigDecimal innerPointValue) {
        this.innerPointValue = innerPointValue;
    }

    public BigDecimal getOtherVc() {
        return otherVc;
    }

    public void setOtherVc(BigDecimal otherVc) {
        this.otherVc = otherVc;
    }

    public Long getPlatformLineId() {
        return platformLineId;
    }

    public void setPlatformLineId(Long platformLineId) {
        this.platformLineId = platformLineId;
    }

    public String getExtProp1() {
        return extProp1;
    }

    public void setExtProp1(String extProp1) {
        this.extProp1 = extProp1;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getSkuComboRemark() {
        return skuComboRemark;
    }

    public void setSkuComboRemark(String skuComboRemark) {
        this.skuComboRemark = skuComboRemark;
    }

    public List<MqSoPromotionMsg> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<MqSoPromotionMsg> promotions) {
        this.promotions = promotions;
    }

    public String getJmskuCode() {
        return jmskuCode;
    }

    public void setJmskuCode(String jmskuCode) {
        this.jmskuCode = jmskuCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

	public BigDecimal getPlatformUnitPrice() {
		return platformUnitPrice;
	}

	public void setPlatformUnitPrice(BigDecimal platformUnitPrice) {
		this.platformUnitPrice = platformUnitPrice;
	}

	public BigDecimal getPlatformTotalActual() {
		return platformTotalActual;
	}

	public void setPlatformTotalActual(BigDecimal platformTotalActual) {
		this.platformTotalActual = platformTotalActual;
	}

	public BigDecimal getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(BigDecimal lineTotal) {
		this.lineTotal = lineTotal;
	}

	public BigDecimal getLineUnitPrice() {
		return lineUnitPrice;
	}

	public void setLineUnitPrice(BigDecimal lineUnitPrice) {
		this.lineUnitPrice = lineUnitPrice;
	}

	public BigDecimal getFinalTotalActual() {
		return finalTotalActual;
	}

	public void setFinalTotalActual(BigDecimal finalTotalActual) {
		this.finalTotalActual = finalTotalActual;
	}

	public BigDecimal getFinalUnitPrice() {
		return finalUnitPrice;
	}

	public void setFinalUnitPrice(BigDecimal finalUnitPrice) {
		this.finalUnitPrice = finalUnitPrice;
	}

	public BigDecimal getTotalPointUsedFee() {
		return totalPointUsedFee;
	}

	public void setTotalPointUsedFee(BigDecimal totalPointUsedFee) {
		this.totalPointUsedFee = totalPointUsedFee;
	}

}
