package com.jumbo.wms.model.vmi.warehouse;

import java.math.BigDecimal;


public class MsgOutboundOrderLineCommand extends MsgOutboundOrderLine {


    private static final long serialVersionUID = 5887468679359145133L;


    private String skuCode;
    private String skuName;

    private BigDecimal price;
    private String barCode;
    private Long msgId;
    private String skuExtCode2;

    @Override
    public String getSkuName() {
        return skuName;
    }
    @Override
    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getSkuExtCode2() {
        return skuExtCode2;
    }

    public void setSkuExtCode2(String skuExtCode2) {
        this.skuExtCode2 = skuExtCode2;
    }

}
