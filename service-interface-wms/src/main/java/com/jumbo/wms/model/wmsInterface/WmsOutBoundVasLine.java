package com.jumbo.wms.model.wmsInterface;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * WMS通用出库增值服务
 * 
 */
public class WmsOutBoundVasLine implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5200567331134204299L;

    /**
     * 类型
     */
    private String type;

    /**
     * 卡号 Card number
     */
    private String cardCode;

    /**
     * 仓库增值服务类型 Value added service type
     */
    private String whVasType;

    /**
     * 快递增值服务类型 Express value added service type
     */
    private String expressVasType;

    /**
     * 打印模板 Print template
     */
    private String printTemplet;

    /**
     * 赠品编码/礼品包装 Gift bar code
     */
    private String skuBarCode;

    /**
     * 备注 Memo
     */
    private String memo;

    /**
     * 箱号 Carton number
     */
    private String cartonNo;

    /**
     * 数量 赠送数量 Quantity
     */
    private Long qty;

    /**
     * 金额 Amount
     */
    private BigDecimal amount;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getWhVasType() {
        return whVasType;
    }

    public void setWhVasType(String whVasType) {
        this.whVasType = whVasType;
    }

    public String getExpressVasType() {
        return expressVasType;
    }

    public void setExpressVasType(String expressVasType) {
        this.expressVasType = expressVasType;
    }

    public String getPrintTemplet() {
        return printTemplet;
    }

    public void setPrintTemplet(String printTemplet) {
        this.printTemplet = printTemplet;
    }

    public String getSkuBarCode() {
        return skuBarCode;
    }

    public void setSkuBarCode(String skuBarCode) {
        this.skuBarCode = skuBarCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCartonNo() {
        return cartonNo;
    }

    public void setCartonNo(String cartonNo) {
        this.cartonNo = cartonNo;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


}
