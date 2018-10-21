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
 * 
 * @author oypj
 * @version
 * @date 2010-02-24
 * 
 */

@Entity
@Table(name = "T_NIKE_VMI_STOCKIN")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class NikeVmiStockInCommand extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -5144739979466411730L;
    /**
     * 新建状态 1
     */
    public static final Integer CREATE_STATUS = 1;
    /**
     * 新建到完成的过渡状态
     */
    public static final Integer TODO_STATUS = 5;
    /**
     * 完成状态 10
     */
    public static final Integer FINISH_STATUS = 10;

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

    /**
     * version
     */
    private int version;



    private String qualifier;

    private String lotnumber;

    private Date createTime;

    /**
     * 是否创建报关信息0=false 1=true
     */
    private Boolean isCustomsDeclaration = false;

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_NIKESTOCK_IN", sequenceName = "S_T_NIKE_VMI_STOCKIN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NIKESTOCK_IN")
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }


    @Column(name = "qualifier")
    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    @Column(name = "lotnumber")
    public String getLotnumber() {
        return lotnumber;
    }

    public void setLotnumber(String lotnumber) {
        this.lotnumber = lotnumber;
    }


    @Column(name = "FROM_LOCATION", length = 50)
    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    @Column(name = "TRANSFER_PREFIX", length = 8)
    public String getTransferPrefix() {
        return transferPrefix;
    }

    public void setTransferPrefix(String transferPrefix) {
        this.transferPrefix = transferPrefix;
    }

    @Column(name = "RECEIVE_DATE", length = 8)
    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    @Column(name = "CS2000_ITEM_CODE", length = 14)
    public String getCs2000ItemCode() {
        return cs2000ItemCode;
    }

    public void setCs2000ItemCode(String cs2000ItemCode) {
        this.cs2000ItemCode = cs2000ItemCode;
    }

    @Column(name = "COLOR_CODE", length = 5)
    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    @Column(name = "SIZE_CODE", length = 4)
    public String getSizeCode() {
        return sizeCode;
    }

    public void setSizeCode(String sizeCode) {
        this.sizeCode = sizeCode;
    }

    @Column(name = "INSEAM_CODE", length = 4)
    public String getInseamCode() {
        return inseamCode;
    }

    public void setInseamCode(String inseamCode) {
        this.inseamCode = inseamCode;
    }

    @Column(name = "LINE_SEQUENCE_NO", length = 5)
    public String getLineSequenceNo() {
        return lineSequenceNo;
    }

    public void setLineSequenceNo(String lineSequenceNo) {
        this.lineSequenceNo = lineSequenceNo;
    }

    @Column(name = "TOTAL_LINE_SEQUENCE_NO", length = 5)
    public String getTotalLineSequenceNo() {
        return totalLineSequenceNo;
    }

    public void setTotalLineSequenceNo(String totalLineSequenceNo) {
        this.totalLineSequenceNo = totalLineSequenceNo;
    }

    @Column(name = "SAP_CARTON", length = 10)
    public String getSapCarton() {
        return sapCarton;
    }

    public void setSapCarton(String sapCarton) {
        this.sapCarton = sapCarton;
    }

    @Column(name = "SAP_D_N_NO", length = 10)
    public String getSapDNNo() {
        return sapDNNo;
    }

    public void setSapDNNo(String sapDNNo) {
        this.sapDNNo = sapDNNo;
    }

    @Column(name = "STATUS", length = 1)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "TO_LOCATION", length = 50)
    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    @Column(name = "STA_ID", length = 10)
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "STALINE_ID", length = 10)
    public Long getStaLineId() {
        return staLineId;
    }


    public void setStaLineId(Long staLineId) {
        this.staLineId = staLineId;
    }

    @Column(name = "REFERENCE_NO", length = 10)
    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    @Column(name = "ITEM_EAN_UPC_CODE", length = 20)
    public String getItemEanUpcCode() {
        return itemEanUpcCode;
    }

    public void setItemEanUpcCode(String itemEanUpcCode) {
        this.itemEanUpcCode = itemEanUpcCode;
    }


    @Column(name = "QUANTITY", length = 8)
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    @Column(name = "brand")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "IS_CUSTOMS_DECLARATION")
    public Boolean getIsCustomsDeclaration() {
        return isCustomsDeclaration;
    }

    public void setIsCustomsDeclaration(Boolean isCustomsDeclaration) {
        this.isCustomsDeclaration = isCustomsDeclaration;
    }


}
