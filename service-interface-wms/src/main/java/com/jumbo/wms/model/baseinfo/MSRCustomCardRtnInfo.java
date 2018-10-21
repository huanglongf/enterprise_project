package com.jumbo.wms.model.baseinfo;

import java.util.Date;

import com.jumbo.wms.model.BaseModel;

/**
 * 星巴克MSR接收卡厂反馈SN入库信息
 */
public class MSRCustomCardRtnInfo extends BaseModel {

    private static final long serialVersionUID = 1167771765165531655L;
    /**
     * 日期
     */
    private Date date;
    /**
     * 天猫订单号
     */
    private String orderCode;
    /**
     * 宝尊订单号
     */
    private String staCode;
    /**
     * 拣货单号
     */
    private String pickingListCode;
    /**
     * sku(excode1)
     */
    private String sku;
    /**
     * 数量
     */
    private Long qty;
    /**
     * 卡片SN号
     */
    private String sn;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getPickingListCode() {
        return pickingListCode;
    }

    public void setPickingListCode(String pickingListCode) {
        this.pickingListCode = pickingListCode;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public String toString() {
        return "MSRCustomCardRtnInfo [date=" + date + ", orderCode=" + orderCode + ", staCode=" + staCode + ", pickingListCode=" + pickingListCode + ", sku=" + sku + ", qty=" + qty + ", sn=" + sn + "]";
    }
}
