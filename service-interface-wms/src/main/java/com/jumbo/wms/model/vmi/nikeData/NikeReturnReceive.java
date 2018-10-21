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
@Table(name = "T_WH_RECEIVE_CONFIRMATION")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class NikeReturnReceive extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -5474264370125726416L;
    /**
     * 转店退仓 类型 -2
     */
    public static final Integer RETURN_TRANSFER_OUT_TYPE = 2;
    /**
     * 退大仓 类型 -3
     * 
     */
    public static final Integer RETURN_VMI_RETURN_TYPE = 3;
    /**
     * 新建状态 1
     */
    public static final Integer CREATE_STATUS = 1;
    /**
     * 完成状态 5
     */
    public static final Integer FINISH_STATUS = 5;
    /**
     * 写文件状态
     */
    public static final Integer TO_WRITE_STATUS = 1;

    public Long id;
    public String fromResource;
    public String transferKey;
    public String transferPrefix;
    public String referenceNo;
    public String receiveDate;
    public String fromLocation;
    public String toLocation;
    public String upcCode;
    public Long quantity;
    public Integer status; // 1新建 ，5 完成
    public Date createTime;
    public Integer type; // 2-转店退仓 ，3-退大仓
    public String reasonCode;

    public String cs2000ItemCode;
    public String colorCode;
    public String sizeCode;
    public String inseamCode;

    public String itemEanUpcCode;

    public String lineSequenceNo;
    public String totalLineSequenceNo;

    public String wmsCarton;
    public String wmsDNNo;

    /**
     * version
     */
    private int version;
    private String fileName;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_NIKE_RETURN_RECEIVE", sequenceName = "S_T_WH_RECEIVE_CONFIRMATION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NIKE_RETURN_RECEIVE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "TYPE", length = 4)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "STATUS", length = 4)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "FROM_RESOURCE", length = 50)
    public String getFromResource() {
        return fromResource;
    }

    public void setFromResource(String fromResource) {
        this.fromResource = fromResource;
    }

    @Column(name = "TRANSFER_KEY", length = 80)
    public String getTransferKey() {
        return transferKey;
    }

    public void setTransferKey(String transferKey) {
        this.transferKey = transferKey;
    }

    @Column(name = "TRANSFER_PREFIX", length = 20)
    public String getTransferPrefix() {
        return transferPrefix;
    }

    public void setTransferPrefix(String transferPrefix) {
        this.transferPrefix = transferPrefix;
    }

    @Column(name = "REFERENCE_NO", length = 20)
    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    @Column(name = "RECEIVE_DATE", length = 20)
    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    @Column(name = "FROM_LOCATION", length = 50)
    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    @Column(name = "TO_LOCATION", length = 20)
    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    @Column(name = "UPC_CODE", length = 50)
    public String getUpcCode() {
        return upcCode;
    }

    public void setUpcCode(String upcCode) {
        this.upcCode = upcCode;
    }

    @Column(name = "QUANTITY", length = 19)
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Column(name = "CREATE_TIME", length = 6)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "REASON_CODE", length = 50)
    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    @Column(name = "CS2000_ITEM_CODE", length = 20)
    public String getCs2000ItemCode() {
        return cs2000ItemCode;
    }

    public void setCs2000ItemCode(String cs2000ItemCode) {
        this.cs2000ItemCode = cs2000ItemCode;
    }

    @Column(name = "COLOR_CODE", length = 20)
    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    @Column(name = "SIZE_CODE", length = 20)
    public String getSizeCode() {
        return sizeCode;
    }

    public void setSizeCode(String sizeCode) {
        this.sizeCode = sizeCode;
    }

    @Column(name = "INSEAM_CODE", length = 20)
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

    @Column(name = "LINE_SEQUENCE_NO", length = 20)
    public String getLineSequenceNo() {
        return lineSequenceNo;
    }

    public void setLineSequenceNo(String lineSequenceNo) {
        this.lineSequenceNo = lineSequenceNo;
    }

    @Column(name = "TOTAL_LINE_SEQUENCE_NO", length = 20)
    public String getTotalLineSequenceNo() {
        return totalLineSequenceNo;
    }

    public void setTotalLineSequenceNo(String totalLineSequenceNo) {
        this.totalLineSequenceNo = totalLineSequenceNo;
    }

    @Column(name = "WMS_CARTON", length = 10)
    public String getWmsCarton() {
        return wmsCarton;
    }

    public void setWmsCarton(String wMSCarton) {
        this.wmsCarton = wMSCarton;
    }

    @Column(name = "WMS_DN_NO", length = 10)
    public String getWmsDNNo() {
        return wmsDNNo;
    }

    public void setWmsDNNo(String wMSDNNo) {
        this.wmsDNNo = wMSDNNo;
    }

    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
