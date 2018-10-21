package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author jiangang.chen 退仓装箱明细
 * 
 */
public class ReturnWarehousePackingLine implements Serializable {

    private static final long serialVersionUID = -6398876665543664299L;

    private String caseNumber;// 箱号

    private String skuCode;// 商品编码

    private Long qty;// 数量

    private BigDecimal weight;// 重量

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

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

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

}
