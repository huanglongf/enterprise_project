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
@Table(name = "T_NIKE_STOCK_RECEIVE_DATA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class NikeStockReceiveData extends BaseModel {
	
	public static final String OPEN = "IN_PROCESS";
	public static final String CLOSED = "CLOSED";
    /**
     * 入库反馈类型 1
     */
    public static final Integer INBOUND_RECEIVE_TYPE = 1;
    /**
     * 转店入库反馈类型 2
     */
    public static final Integer TRANSFER_INBOUND_RECEIVE_TYPE = 2;
    /**
     * 新建状态 1
     */
    public static final Integer CREATE_STATUS = 1;
    /**
     * 完成状态 10
     */
    public static final Integer FINISH_STATUS = 10;
    /**
     * 准备写文件状态
     */
    public static final Integer TO_WRITE_STATUS = 2;

    private static final long serialVersionUID = -920755407584316110L;
    private Long id;
    private String transferPrefix;
    private String referenceNo;
    private Date receiveDate;
    private String fromLocation;
    private String toLocation;
    private String cs2000ItemCode;
    private String colorCode;
    private String sizeCode;
    private String inseamCode;
    private String itemEanUpcCode;
    private Long quantity;
    private String lineSequenceNo;
    private String totalLineSequenceNo;
    private String sapCarton;
    private String sapCNNo;
    // 1 是新建 10 是完成
    private Integer status;
    private Date createDate;
    /**
     * 收货入库类型 1 转店入库类型 2
     */
    public Integer type;
    
    /**
     * 箱状态
     */
    private String cartonStatus;
    /**
     * PO单状态
     */
    private String poStatus;


    @Column(name = "type", length = 8)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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
    @SequenceGenerator(name = "SEQ_NikeStockReceive", sequenceName = "S_T_NIKE_STOCK_RECEIVE_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NikeStockReceive")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Column(name = "RECEIVE_DATE")
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

    @Column(name = "ITEM_EAN_UPC_CODE", length = 35)
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

    @Column(name = "SAP_CARTON", length = 20)
    public String getSapCarton() {
        return sapCarton;
    }

    public void setSapCarton(String sapCarton) {
        this.sapCarton = sapCarton;
    }

    @Column(name = "SAP_C_N_NO", length = 20)
    public String getSapCNNo() {
        return sapCNNo;
    }

    public void setSapCNNo(String sapCNNo) {
        this.sapCNNo = sapCNNo;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "CARTON_STATUS", length = 20)
	public String getCartonStatus() {
		return cartonStatus;
	}

	public void setCartonStatus(String cartonStatus) {
		this.cartonStatus = cartonStatus;
	}

    @Column(name = "PO_STATUS", length = 20)
	public String getPoStatus() {
		return poStatus;
	}

	public void setPoStatus(String poStatus) {
		this.poStatus = poStatus;
	}

}
