package com.jumbo.wms.model.vmi.converseData;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.vmi.itData.VMIReceiveInfoStatus;


@Entity
@Table(name = "T_CONVERSE_VMI_RECEIVE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ConverseVmiReceive extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2366169002275621117L;

    private Long id;

    private String transferPrefix;

    private String cartonNumber;

    private Date receiveDate;

    private String fromLocation;

    private String toLocation;

    private String cs2000ItemCode;

    private String colorCode;

    private String sizeCode;

    private String inseamCode;

    private String itemEanUpcCode;

    private Long quantity;

    private Double lineSequenceNO;

    private Double totalLineSequenceNO;

    private String transferNO;

    private String sapCarton;

    private VMIReceiveInfoStatus status;

    private Integer type;

    private String bin;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CONVERSE_VMI_RECEIVE", sequenceName = "S_T_CONVERSE_VMI_RECEIVE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONVERSE_VMI_RECEIVE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TRANSFER_PREFIX", length = 8)
    public String getTransferPrefix() {
        return transferPrefix;
    }

    public void setTransferPrefix(String transferPrefix) {
        this.transferPrefix = transferPrefix;
    }

    @Column(name = "CARTON_NUMBER", length = 26)
    public String getCartonNumber() {
        return cartonNumber;
    }

    public void setCartonNumber(String cartonNumber) {
        this.cartonNumber = cartonNumber;
    }

    @Column(name = "RECEIVE_DATE")
    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    @Column(name = "FROM_LOCATION", length = 10)
    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    @Column(name = "TO_LOCATION", length = 10)
    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    @Column(name = "CS2000_ITEM_CODE", length = 26)
    public String getCs2000ItemCode() {
        return cs2000ItemCode;
    }

    public void setCs2000ItemCode(String cs2000ItemCode) {
        this.cs2000ItemCode = cs2000ItemCode;
    }

    @Column(name = "COLOR_CODE", length = 4)
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

    @Column(name = "INSEAM_CODE", length = 10)
    public String getInseamCode() {
        return inseamCode;
    }

    public void setInseamCode(String inseamCode) {
        this.inseamCode = inseamCode;
    }

    @Column(name = "ITEM_EAN_UPC_CODE", length = 26)
    public String getItemEanUpcCode() {
        return itemEanUpcCode;
    }

    public void setItemEanUpcCode(String itemEanUpcCode) {
        this.itemEanUpcCode = itemEanUpcCode;
    }

    @Column(name = "QUANTITY")
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Column(name = "LINE_SEQUENCE_NO")
    public Double getLineSequenceNO() {
        return lineSequenceNO;
    }

    public void setLineSequenceNO(Double lineSequenceNO) {
        this.lineSequenceNO = lineSequenceNO;
    }

    @Column(name = "TOTAL_LINE_SEQUENCE_NO")
    public Double getTotalLineSequenceNO() {
        return totalLineSequenceNO;
    }

    public void setTotalLineSequenceNO(Double totalLineSequenceNO) {
        this.totalLineSequenceNO = totalLineSequenceNO;
    }

    @Column(name = "SAP_CARTON", length = 10)
    public String getSapCarton() {
        return sapCarton;
    }

    public void setSapCarton(String sapCarton) {
        this.sapCarton = sapCarton;
    }

    @Column(name = "TRANSFER_NO", length = 10)
    public String getTransferNO() {
        return transferNO;
    }

    public void setTransferNO(String transferNO) {
        this.transferNO = transferNO;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.vmi.itData.VMIReceiveInfoStatus")})
    public VMIReceiveInfoStatus getStatus() {
        return status;
    }

    public void setStatus(VMIReceiveInfoStatus status) {
        this.status = status;
    }

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "BIN")
    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

}
