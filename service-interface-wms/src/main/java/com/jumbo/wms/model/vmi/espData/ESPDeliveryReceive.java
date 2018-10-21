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
 */
package com.jumbo.wms.model.vmi.espData;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * @author lichuan
 * 
 */
@Entity
@Table(name = "T_ESPRIT_DELIVERY_RECEIVE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class ESPDeliveryReceive extends BaseModel {

    private static final long serialVersionUID = -7837839744173732887L;
    private Long id;
    private Date createTime;
    private Date version;
    private Date lastModifyTime;
    private Long staId;
    private String hFromNode;
    private String hToNode;
    private String hFromGln;
    private String hToGln;
    private String hBatchNo;
    private String hNoOfRecord;
    private String hGenerationDate;
    private String hGenerationTime;
    private String pCompany;
    private String pLocation;
    private String pPickType;
    private String pPickNo;
    private String pFromLocation;
    private String pOrderFlag;
    private String pReqDeliveryDate;
    private String pLatestReqDeliveryDate;
    private String pPromotionStartDate;
    private String pEdiStatus;
    private String pRouteCode;
    private String pRemark1;
    private String pRemark2;
    private String pRemark3;
    private String pRemark4;
    private String pFromLocGln;
    private String pToLocGln;
    private String pCheckDigit;
    private String itemSku;
    private Long itemQty;
    private String itemSize;
    private String itemColorDesc;
    private String itemColor;
    private String itemDivision;
    private String itemSeason;
    private String itemYear;
    private String itemStyleShortDesc;
    private String itemStyle;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_ESPRIT_DELIVERY_RECEIVE", sequenceName = "S_T_ESPRIT_DELIVERY_RECEIVE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESPRIT_DELIVERY_RECEIVE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Version
    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "H_FROM_NODE", length=20)
    public String gethFromNode() {
        return hFromNode;
    }

    public void sethFromNode(String hFromNode) {
        this.hFromNode = hFromNode;
    }

    @Column(name = "H_TO_NODE", length=20)
    public String gethToNode() {
        return hToNode;
    }

    public void sethToNode(String hToNode) {
        this.hToNode = hToNode;
    }

    @Column(name = "H_FROM_GLN", length=50)
    public String gethFromGln() {
        return hFromGln;
    }

    public void sethFromGln(String hFromGln) {
        this.hFromGln = hFromGln;
    }

    @Column(name = "H_TO_GLN", length=50)
    public String gethToGln() {
        return hToGln;
    }

    public void sethToGln(String hToGln) {
        this.hToGln = hToGln;
    }

    @Column(name = "H_BATCH_NO", length=20)
    public String gethBatchNo() {
        return hBatchNo;
    }

    public void sethBatchNo(String hBatchNo) {
        this.hBatchNo = hBatchNo;
    }

    @Column(name = "H_NO_OF_RECORD", length=20)
    public String gethNoOfRecord() {
        return hNoOfRecord;
    }

    public void sethNoOfRecord(String hNoOfRecord) {
        this.hNoOfRecord = hNoOfRecord;
    }

    @Column(name = "H_GENERATION_DATE", length=20)
    public String gethGenerationDate() {
        return hGenerationDate;
    }

    public void sethGenerationDate(String hGenerationDate) {
        this.hGenerationDate = hGenerationDate;
    }

    @Column(name = "H_GENERATION_TIME", length=20)
    public String gethGenerationTime() {
        return hGenerationTime;
    }

    public void sethGenerationTime(String hGenerationTime) {
        this.hGenerationTime = hGenerationTime;
    }

    @Column(name = "P_COMPANY", length=20)
    public String getpCompany() {
        return pCompany;
    }

    public void setpCompany(String pCompany) {
        this.pCompany = pCompany;
    }

    @Column(name = "P_LOCATION", length=20)
    public String getpLocation() {
        return pLocation;
    }

    public void setpLocation(String pLocation) {
        this.pLocation = pLocation;
    }

    @Column(name = "P_PICK_TYPE", length=20)
    public String getpPickType() {
        return pPickType;
    }

    public void setpPickType(String pPickType) {
        this.pPickType = pPickType;
    }

    @Column(name = "P_PICK_NO", length=50)
    public String getpPickNo() {
        return pPickNo;
    }

    public void setpPickNo(String pPickNo) {
        this.pPickNo = pPickNo;
    }

    @Column(name = "P_FROM_LOCATION", length=30)
    public String getpFromLocation() {
        return pFromLocation;
    }

    public void setpFromLocation(String pFromLocation) {
        this.pFromLocation = pFromLocation;
    }

    @Column(name = "P_ORDER_FLAG", length=20)
    public String getpOrderFlag() {
        return pOrderFlag;
    }

    public void setpOrderFlag(String pOrderFlag) {
        this.pOrderFlag = pOrderFlag;
    }

    @Column(name = "P_REQ_DELIVERY_DATE", length=20)
    public String getpReqDeliveryDate() {
        return pReqDeliveryDate;
    }

    public void setpReqDeliveryDate(String pReqDeliveryDate) {
        this.pReqDeliveryDate = pReqDeliveryDate;
    }

    @Column(name = "P_LATEST_REQ_DELIVERY_DATE", length=20)
    public String getpLatestReqDeliveryDate() {
        return pLatestReqDeliveryDate;
    }

    public void setpLatestReqDeliveryDate(String pLatestReqDeliveryDate) {
        this.pLatestReqDeliveryDate = pLatestReqDeliveryDate;
    }

    @Column(name = "P_PROMOTION_START_DATE", length=20)
    public String getpPromotionStartDate() {
        return pPromotionStartDate;
    }

    public void setpPromotionStartDate(String pPromotionStartDate) {
        this.pPromotionStartDate = pPromotionStartDate;
    }

    @Column(name = "P_EDI_STATUS", length=20)
    public String getpEdiStatus() {
        return pEdiStatus;
    }

    public void setpEdiStatus(String pEdiStatus) {
        this.pEdiStatus = pEdiStatus;
    }

    @Column(name = "P_ROUTE_CODE", length=20)
    public String getpRouteCode() {
        return pRouteCode;
    }

    public void setpRouteCode(String pRouteCode) {
        this.pRouteCode = pRouteCode;
    }

    @Column(name = "P_REMARK1", length=200)
    public String getpRemark1() {
        return pRemark1;
    }

    public void setpRemark1(String pRemark1) {
        this.pRemark1 = pRemark1;
    }

    @Column(name = "P_REMARK2", length=200)
    public String getpRemark2() {
        return pRemark2;
    }

    public void setpRemark2(String pRemark2) {
        this.pRemark2 = pRemark2;
    }

    @Column(name = "P_REMARK3", length=200)
    public String getpRemark3() {
        return pRemark3;
    }

    public void setpRemark3(String pRemark3) {
        this.pRemark3 = pRemark3;
    }

    @Column(name = "P_REMARK4", length=200)
    public String getpRemark4() {
        return pRemark4;
    }

    public void setpRemark4(String pRemark4) {
        this.pRemark4 = pRemark4;
    }

    @Column(name = "P_FROM_LOC_GLN", length=20)
    public String getpFromLocGln() {
        return pFromLocGln;
    }

    public void setpFromLocGln(String pFromLocGln) {
        this.pFromLocGln = pFromLocGln;
    }

    @Column(name = "P_TO_LOC_GLN", length=20)
    public String getpToLocGln() {
        return pToLocGln;
    }

    public void setpToLocGln(String pToLocGln) {
        this.pToLocGln = pToLocGln;
    }

    @Column(name = "P_CHECK_DIGIT", length=20)
    public String getpCheckDigit() {
        return pCheckDigit;
    }

    public void setpCheckDigit(String pCheckDigit) {
        this.pCheckDigit = pCheckDigit;
    }

    @Column(name = "ITEM_SKU", length=50)
    public String getItemSku() {
        return itemSku;
    }

    public void setItemSku(String itemSku) {
        this.itemSku = itemSku;
    }

    @Column(name = "ITEM_QTY")
    public Long getItemQty() {
        return itemQty;
    }

    public void setItemQty(Long itemQty) {
        this.itemQty = itemQty;
    }

    @Column(name = "ITEM_SIZE", length=20)
    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    @Column(name = "ITEM_COLOR_DESC", length=50)
    public String getItemColorDesc() {
        return itemColorDesc;
    }

    public void setItemColorDesc(String itemColorDesc) {
        this.itemColorDesc = itemColorDesc;
    }

    @Column(name = "ITEM_COLOR", length=50)
    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    @Column(name = "ITEM_DIVISION", length=50)
    public String getItemDivision() {
        return itemDivision;
    }

    public void setItemDivision(String itemDivision) {
        this.itemDivision = itemDivision;
    }

    @Column(name = "ITEM_SEASON", length=10)
    public String getItemSeason() {
        return itemSeason;
    }

    public void setItemSeason(String itemSeason) {
        this.itemSeason = itemSeason;
    }

    @Column(name = "ITEM_YEAR", length=10)
    public String getItemYear() {
        return itemYear;
    }

    public void setItemYear(String itemYear) {
        this.itemYear = itemYear;
    }

    @Column(name = "ITEM_STYLE_SHORT_DESC", length=200)
    public String getItemStyleShortDesc() {
        return itemStyleShortDesc;
    }

    public void setItemStyleShortDesc(String itemStyleShortDesc) {
        this.itemStyleShortDesc = itemStyleShortDesc;
    }

    @Column(name = "ITEM_STYLE", length=50)
    public String getItemStyle() {
        return itemStyle;
    }

    public void setItemStyle(String itemStyle) {
        this.itemStyle = itemStyle;
    }



}
