package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;
import java.util.List;

/**
 * Coach小票订单基本信息
 * 
 * @author jinlong.ke
 * 
 */
public class CoachTicketOrderInfo implements Serializable{
	private static final long serialVersionUID = -6448652535712168182L;
	private String code;// 订单编号
    private String createTime;// 订单创建时间
    private String memo;// 订单备注
    private List<CoachTicketOrderInfoLine> lines;// 订单明细列表
    private String shopName;// 订单所属店铺名称
    private String vipCode;// 会员卡编号
    private String realName;// 会员真实姓名
    private String salerName;// 销售姓名
    private String totalActual;// 订单实际总金额
    private String payActual;// 支付总金额
    private String size;// 总数量

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<CoachTicketOrderInfoLine> getLines() {
        return lines;
    }

    public void setLines(List<CoachTicketOrderInfoLine> lines) {
        this.lines = lines;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getVipCode() {
        return vipCode;
    }

    public void setVipCode(String vipCode) {
        this.vipCode = vipCode;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(String totalActual) {
        this.totalActual = totalActual;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPayActual() {
        return payActual;
    }

    public void setPayActual(String payActual) {
        this.payActual = payActual;
    }

    public String getSalerName() {
        return salerName;
    }

    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }
    
}
