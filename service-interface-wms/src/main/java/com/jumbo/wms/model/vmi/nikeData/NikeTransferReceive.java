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

@Entity
@Table(name = "T_NIKE_TRANSFER_RECEIVE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class NikeTransferReceive extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -920755407584316110L;
    public Long id;
    public String transferPrefix;
    public String referenceNo;
    public Date receiveDate;
    public String fromLocation;
    public String toLocation;
    public String cs2000ItemCode;
    public String colorCode;
    public String sizeCode;
    public String inseamCode;
    public String itemEanUpcCode;
    public Long quantity;
    public String lineSequenceNo;
    public String totalLineSequenceNo;
    public String sapCarton;
    public String sapCNNo;
    public Integer status;
    public Date createDate;

    @Column(name = "status", length = 10)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "CREATE_DATE", length = 8)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * version
     */
    private int version;

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_NikeTransferReceive", sequenceName = "S_T_NIKE_TRANSFER_RECEIVE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NikeTransferReceive")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TRANSFER_PREFIX", length = 50)
    public String getTransferPrefix() {
        return transferPrefix;
    }

    public void setTransferPrefix(String transferPrefix) {
        this.transferPrefix = transferPrefix;
    }

    @Column(name = "REFERENCE_NO", length = 10)
    @SequenceGenerator(name = "SEQ_NikeTransferReceive_refno", sequenceName = "SEQ_NikeTransferReceive_refno", allocationSize = 1, initialValue = 1000000001)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NikeTransferReceive_refno")
    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    @Column(name = "RECEIVE_DATE", length = 8)
    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    @Column(name = "FROM_LOCATION", length = 50)
    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    @Column(name = "TO_LOCATION", length = 50)
    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
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

    @Column(name = "ITEM_EAN_UPC_CODE", length = 20)
    public String getItemEanUpcCode() {
        return itemEanUpcCode;
    }

    public void setItemEanUpcCode(String itemEanUpcCode) {
        this.itemEanUpcCode = itemEanUpcCode;
    }

    @Column(name = "QUANTITY", length = 7)
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
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

    @Column(name = "SAP_C_N_NO", length = 10)
    public String getSapCNNo() {
        return sapCNNo;
    }

    public void setSapCNNo(String sapCNNo) {
        this.sapCNNo = sapCNNo;
    }

    public void setVersion(int version) {
        this.version = version;
    }


}
