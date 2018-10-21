package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 入库单反馈明细行
 * 
 */
public class WmsInboundOrderResponseLine implements Serializable {


    /**
     * 
     */
    private static final long serialVersionUID = 5684386878420077906L;
    /**
     * 商品对接唯一标识
     */
    private String skuCode;
    /**
     * 入库数量
     */
    private Long qty;
    /**
     * 库存状态
     */
    private String invStatus;
    /**
     * 扩展字段，json格式
     */
    private String extMemo;
    /**
     * 过期时间
     */
    private String expDate;
    /**
     * SN列表
     */
    private WmsInboundOrderResponseSnLine[] snLines;

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public WmsInboundOrderResponseSnLine[] getSnLines() {
        return snLines;
    }

    public void setSnLines(WmsInboundOrderResponseSnLine[] snLines) {
        this.snLines = snLines;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

}
