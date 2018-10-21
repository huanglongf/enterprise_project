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
import java.util.List;

public class PickingListObj implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -160100822570996416L;

    private String warehouseName;
    private String dphCode;
    private Integer planBillCount;
    private Integer planSkuQty;
    private String operator;

    // private List<PickingListLineObj> lines;
    private List<PickingListSubLineObj> lines;
    private String location;
    private String barCode;
    private String jmCode;
    private String jmskucode;
    private String skuName;
    private String keyProperty;
    private Integer quantity;
    private String lpcode;
    private String specialPackaging;
    private String isBigBox;
    private String barCode1;
    private String barCode2;
    private String strExpireDate;
    private Integer isCod;
    /**
     * 拣货区域code
     */
    private String pickZoneCode;

    private String whZone;

    private String batchIndex; // 自动化出库1级和2级批次

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getDphCode() {
        return dphCode;
    }

    public void setDphCode(String dphCode) {
        this.dphCode = dphCode;
    }

    public Integer getPlanBillCount() {
        return planBillCount;
    }

    public void setPlanBillCount(Integer planBillCount) {
        this.planBillCount = planBillCount;
    }

    public Integer getPlanSkuQty() {
        return planSkuQty;
    }

    public void setPlanSkuQty(Integer planSkuQty) {
        this.planSkuQty = planSkuQty;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public List<PickingListSubLineObj> getLines() {
        return lines;
    }

    public void setLines(List<PickingListSubLineObj> lines) {
        this.lines = lines;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getJmCode() {
        return jmCode;
    }

    public void setJmCode(String jmCode) {
        this.jmCode = jmCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getKeyProperty() {
        return keyProperty;
    }

    public void setKeyProperty(String keyProperty) {
        this.keyProperty = keyProperty;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getJmskucode() {
        return jmskucode;
    }

    public void setJmskucode(String jmskucode) {
        this.jmskucode = jmskucode;
    }

    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    public String getSpecialPackaging() {
        return specialPackaging;
    }

    public void setSpecialPackaging(String specialPackaging) {
        this.specialPackaging = specialPackaging;
    }

    public String getIsBigBox() {
        return isBigBox;
    }

    public void setIsBigBox(String isBigBox) {
        this.isBigBox = isBigBox;
    }

    public String getBarCode1() {
        return barCode1;
    }

    public void setBarCode1(String barCode1) {
        this.barCode1 = barCode1;
    }

    public String getBarCode2() {
        return barCode2;
    }

    public void setBarCode2(String barCode2) {
        this.barCode2 = barCode2;
    }

    public String getStrExpireDate() {
        return strExpireDate;
    }

    public void setStrExpireDate(String strExpireDate) {
        this.strExpireDate = strExpireDate;
    }

    public Integer getIsCod() {
        return isCod;
    }

    public void setIsCod(Integer isCod) {
        this.isCod = isCod;
    }

    public String getPickZoneCode() {
        return pickZoneCode;
    }

    public void setPickZoneCode(String pickZoneCode) {
        this.pickZoneCode = pickZoneCode;
    }

    public String getWhZone() {
        return whZone;
    }

    public void setWhZone(String whZone) {
        this.whZone = whZone;
    }

    public String getBatchIndex() {
        return batchIndex;
    }

    public void setBatchIndex(String batchIndex) {
        this.batchIndex = batchIndex;
    }

}
