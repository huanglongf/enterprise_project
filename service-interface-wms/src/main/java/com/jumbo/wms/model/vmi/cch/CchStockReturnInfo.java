package com.jumbo.wms.model.vmi.cch;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_CCH_STOCK_RETURN")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class CchStockReturnInfo extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4872944688770316888L;

    private Long id;

    private String type;

    private Long returnNumber;

    private Date returnDate;

    private Integer warehouseCode;

    private String remarqus;

    private Long carrierShipNumber;

    private String via;

    private int carrierNumber;

    private String parcelStatus;

    private String statusDate;

    private Date createTime;

    private Integer status;

    private Long msgBatchId;
    
    private Long staId;

    private Long cartonId;

    private String vmiCode;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CCH_STOCK_RETURN", sequenceName = "S_T_CCH_STOCK_RETURN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CCH_STOCK_RETURN")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TYPE", length = 20)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "RETURN_NUMBER")
    public Long getReturnNumber() {
        return returnNumber;
    }

    public void setReturnNumber(Long returnNumber) {
        this.returnNumber = returnNumber;
    }

    @Column(name = "RETURN_DATE")
    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "CARTON_ID")
    public Long getCartonId() {
        return cartonId;
    }

    public void setCartonId(Long cartonId) {
        this.cartonId = cartonId;
    }

    @Column(name = "WAREHOUSE_CODE")
    public Integer getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(Integer warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    @Column(name = "REMARQUS", length = 20)
    public String getRemarqus() {
        return remarqus;
    }

    public void setRemarqus(String remarqus) {
        this.remarqus = remarqus;
    }

    @Column(name = "CARRIER_SHIP_NUMBER")
    public Long getCarrierShipNumber() {
        return carrierShipNumber;
    }

    public void setCarrierShipNumber(Long carrierShipNumber) {
        this.carrierShipNumber = carrierShipNumber;
    }

    @Column(name = "VIA", length = 20)
    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    @Column(name = "CARRIER_NUMBER")
    public int getCarrierNumber() {
        return carrierNumber;
    }

    public void setCarrierNumber(int carrierNumber) {
        this.carrierNumber = carrierNumber;
    }

    @Column(name = "PARCEL_STATUS", length = 20)
    public String getParcelStatus() {
        return parcelStatus;
    }

    public void setParcelStatus(String parcelStatus) {
        this.parcelStatus = parcelStatus;
    }

    @Column(name = "STATUS_DATE", length = 20)
    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "msg_Batch_Id")
    public Long getMsgBatchId() {
        return msgBatchId;
    }

    public void setMsgBatchId(Long msgBatchId) {
        this.msgBatchId = msgBatchId;
    }

    @Column(name = "VMI_CODE")
    public String getVmiCode() {
        return vmiCode;
    }

    public void setVmiCode(String vmiCode) {
        this.vmiCode = vmiCode;
    }
}
