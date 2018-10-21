/**
 * DimensionImpl.java com.erry.model.it.impl
 * 
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

package com.jumbo.wms.model.vmi.nikeData;

import com.jumbo.wms.model.BaseModel;




public class NikeVmiStockInHub extends BaseModel {
    private static final long serialVersionUID = 2969159735590828086L;

    private Long Id;
    private Long quantity;
    private Long staId;
    private Long staLineId;
    private String referenceNo;
    private String itemEanUpcCode;
    private String toLocation;
    private String fromLocation;
    private String transferPrefix;
    private String receiveDate;
    private String cs2000ItemCode;
    private String colorCode;
    private String sizeCode;
    private String inseamCode;
    private String lineSequenceNo;
    private String totalLineSequenceNo;
    private String sapCarton;
    private String sapDNNo;
    private Integer status;
    private String brand;// (1:hub 0或空 ：原来逻辑)

    private String qualifier;

    private String lotnumber;


    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getLotnumber() {
        return lotnumber;
    }

    public void setLotNumber(String lotnumber) {
        this.lotnumber = lotnumber;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public Long getStaLineId() {
        return staLineId;
    }

    public void setStaLineId(Long staLineId) {
        this.staLineId = staLineId;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getItemEanUpcCode() {
        return itemEanUpcCode;
    }

    public void setItemEanUpcCode(String itemEanUpcCode) {
        this.itemEanUpcCode = itemEanUpcCode;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getTransferPrefix() {
        return transferPrefix;
    }

    public void setTransferPrefix(String transferPrefix) {
        this.transferPrefix = transferPrefix;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getCs2000ItemCode() {
        return cs2000ItemCode;
    }

    public void setCs2000ItemCode(String cs2000ItemCode) {
        this.cs2000ItemCode = cs2000ItemCode;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getSizeCode() {
        return sizeCode;
    }

    public void setSizeCode(String sizeCode) {
        this.sizeCode = sizeCode;
    }

    public String getInseamCode() {
        return inseamCode;
    }

    public void setInseamCode(String inseamCode) {
        this.inseamCode = inseamCode;
    }

    public String getLineSequenceNo() {
        return lineSequenceNo;
    }

    public void setLineSequenceNo(String lineSequenceNo) {
        this.lineSequenceNo = lineSequenceNo;
    }

    public String getTotalLineSequenceNo() {
        return totalLineSequenceNo;
    }

    public void setTotalLineSequenceNo(String totalLineSequenceNo) {
        this.totalLineSequenceNo = totalLineSequenceNo;
    }

    public String getSapCarton() {
        return sapCarton;
    }

    public void setSapCarton(String sapCarton) {
        this.sapCarton = sapCarton;
    }

    public String getSapDNNo() {
        return sapDNNo;
    }

    public void setSapDNNo(String sapDNNo) {
        this.sapDNNo = sapDNNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

}
