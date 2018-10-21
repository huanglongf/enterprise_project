/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.jumbo.wms.model.jasperReport;

import java.io.Serializable;

public class OutBoundSendInfoLine implements Serializable, Comparable<OutBoundSendInfoLine> {
    /**
	 * 
	 */
    private static final long serialVersionUID = -7770644605754480904L;

    private Integer ordinal;
    // 单据出库商品信息(取sta_line 商品JMSKUCODE、条码、数量)
    private String jmskuCode;
    private String barCode;
    private String supplyCode;
    private String skuName;
    private Long quantity;
    private String inventoryStatus;
    private String cartonCode;// 箱号编码
    private String skuCode;// sku编码
    private String orderKey;// 利丰系统单号或者BZ系统出库单号-
    private String pplNo;// 出库单-NIKE单据编码
    private String vas;// VAS
    private Long totalQty;// 箱总商品件数

    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSupplyCode() {
        return supplyCode;
    }

    public void setSupplyCode(String supplyCode) {
        this.supplyCode = supplyCode;
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public String getCartonCode() {
        return cartonCode;
    }

    public void setCartonCode(String cartonCode) {
        this.cartonCode = cartonCode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getPplNo() {
        return pplNo;
    }

    public void setPplNo(String pplNo) {
        this.pplNo = pplNo;
    }


    public String getVas() {
        return vas;
    }

    public void setVas(String vas) {
        this.vas = vas;
    }

    public Long getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Long totalQty) {
        this.totalQty = totalQty;
    }

    @Override
    public int compareTo(OutBoundSendInfoLine o) {
        if (o.getSupplyCode() != null) {
            return this.getSupplyCode().compareTo(o.getSupplyCode());
        } else {
            return this.getCartonCode().compareTo(o.getCartonCode());
        }
    }

}
