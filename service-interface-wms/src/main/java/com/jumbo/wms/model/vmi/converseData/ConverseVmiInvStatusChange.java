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
@Table(name = "T_CONVERSE_INVSTATUS_CHANGE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ConverseVmiInvStatusChange extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6818124467202152637L;

    private Long id;

    private VMIReceiveInfoStatus status;

    private String storeCode;

    private String skuBarCode;

    private String colorCode;

    private String sizeCode;

    private Long origStatusId;

    private Long destStatusId;

    private Long quantity;

    private Date transDate;

    private String eanCode;

    private Date version;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CONVERSE_INVSTATUS_CHANGE", sequenceName = "S_T_CONVERSE_INVSTATUS_CHANGE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONVERSE_INVSTATUS_CHANGE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.vmi.itData.VMIReceiveInfoStatus")})
    public VMIReceiveInfoStatus getStatus() {
        return status;
    }

    public void setStatus(VMIReceiveInfoStatus status) {
        this.status = status;
    }

    @Column(name = "STORE_CODE", length = 10)
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Column(name = "PRODUCT_CODE", length = 50)
    public String getSkuBarCode() {
        return skuBarCode;
    }

    public void setSkuBarCode(String skuBarCode) {
        this.skuBarCode = skuBarCode;
    }

    @Column(name = "COLOR_CODE", length = 10)
    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    @Column(name = "SIZE_CODE", length = 10)
    public String getSizeCode() {
        return sizeCode;
    }

    public void setSizeCode(String sizeCode) {
        this.sizeCode = sizeCode;
    }

    @Column(name = "PORIGBINID")
    public Long getOrigStatusId() {
        return origStatusId;
    }

    public void setOrigStatusId(Long origStatusId) {
        this.origStatusId = origStatusId;
    }

    @Column(name = "PDESTBINID")
    public Long getDestStatusId() {
        return destStatusId;
    }

    public void setDestStatusId(Long destStatusId) {
        this.destStatusId = destStatusId;
    }

    @Column(name = "QTY")
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Column(name = "TRANSDATE")
    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    @Column(name = "EAN_CODE", length = 50)
    public String getEanCode() {
        return eanCode;
    }

    public void setEanCode(String eanCode) {
        this.eanCode = eanCode;
    }

    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

}
