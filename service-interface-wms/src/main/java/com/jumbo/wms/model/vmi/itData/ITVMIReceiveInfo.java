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

package com.jumbo.wms.model.vmi.itData;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifTotalLine;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * VMI 确认信息
 * 
 * @author 于姗姗
 * 
 */
@Entity
@Table(name = "T_IT_VMI_RECEIVE_INFO")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ITVMIReceiveInfo extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1942646210332165319L;

    /**
     * PK
     */
    private Long id;

    /**
     * Location NO
     */
    private String fromLocation;

    /**
     * To Location
     */
    private String toLocation;

    /**
     * DN memo number
     */
    private String tranid;

    /**
     * sku code
     */
    private String UPC;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * Transaction Date yyyy-mm-dd
     */
    private Date txDate;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 指令类型
     */
    private VMIInstructionType vmiType;

    /**
     * 关联店铺
     */
    private String innerShopCode;

    /**
     * 品牌供应商
     */
    private String vender;

    /**
     * 货品状态
     */
    private String stockstatus;

    private StockTransApplication sta;

    private StaLine staLine;

    private InventoryCheck invCK;

    private InventoryCheckDifTotalLine invLine;



    /**
     * Station No
     */
    private String stdno;

    private VMIReceiveInfoStatus status;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_IT_DELIVERY", sequenceName = "S_VMI_IT_RECEIVING", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_IT_DELIVERY")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "from_Location", length = 20)
    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    @Column(name = "STATION_NO", length = 20)
    public String getStdno() {
        return stdno;
    }

    public void setStdno(String stdno) {
        this.stdno = stdno;
    }

    @Column(name = "TO_LOCATION", length = 20)
    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    @Column(name = "TRAN_ID", length = 20)
    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    @Column(name = "UPC", length = 20)
    public String getUPC() {
        return UPC;
    }

    public void setUPC(String UPC) {
        this.UPC = UPC;
    }

    @Column(name = "QUANTITY")
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Column(name = "TX_DATE")
    public Date getTxDate() {
        return txDate;
    }

    public void setTxDate(Date txDate) {
        this.txDate = txDate;
    }

    @Column(name = "FILE_NAME", length = 100)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.vmi.itData.VMIInstructionType")})
    public VMIInstructionType getVmiType() {
        return vmiType;
    }

    public void setVmiType(VMIInstructionType vmiType) {
        this.vmiType = vmiType;
    }

    @Column(name = "INNER_SHOP_CODE", length = 20)
    public String getInnerShopCode() {
        return innerShopCode;
    }

    public void setInnerShopCode(String innerShopCode) {
        this.innerShopCode = innerShopCode;
    }

    @Column(name = "VENDER", length = 20)
    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    @Index(name = "IDX_WH_VMI_IF_STA")
    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    @OneToOne
    public StaLine getStaLine() {
        return staLine;
    }

    public void setStaLine(StaLine staLine) {
        this.staLine = staLine;
    }

    @Column(name = "STOCK_STATUS", length = 10)
    public String getStockstatus() {
        return stockstatus;
    }

    public void setStockstatus(String stockstatus) {
        this.stockstatus = stockstatus;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.vmi.itData.VMIReceiveInfoStatus")})
    public VMIReceiveInfoStatus getStatus() {
        return status;
    }

    public void setStatus(VMIReceiveInfoStatus status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INV_CHECK_ID")
    @Index(name = "IDX_WH_VMI_INV_CHECK")
    public InventoryCheck getInvCK() {
        return invCK;
    }

    public void setInvCK(InventoryCheck invCK) {
        this.invCK = invCK;
    }

    @OneToOne
    public InventoryCheckDifTotalLine getInvLine() {
        return invLine;
    }

    public void setInvLine(InventoryCheckDifTotalLine invLine) {
        this.invLine = invLine;
    }



}
