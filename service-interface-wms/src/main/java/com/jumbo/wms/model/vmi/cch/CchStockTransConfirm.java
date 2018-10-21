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
@Table(name = "T_CCH_STOCKIN_CONFIRM")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class CchStockTransConfirm extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7988187563925580382L;

    private Long id;

    private String type;

    private String receptionNumber;

    private String shipmentNumber;

    private String slipNumber;

    private String supplierCode;

    private Date receptionDate;

    private String invoicingDate;

    private String comingFromhead;

    private String receptionHour;

    private String receivedBy;

    private int zere_field;

    private String blank_1;

    private String blank_2;

    private Date createDate;

    private Integer status;

    private Long msgBatchId;

    private String vmiCode;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CCH_STOCKIN_CONFIRM", sequenceName = "S_T_CCH_STOCKIN_CONFIRM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CCH_STOCKIN_CONFIRM")
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

    @Column(name = "RECEPTION_NUMBER", length = 20)
    public String getReceptionNumber() {
        return receptionNumber;
    }

    public void setReceptionNumber(String receptionNumber) {
        this.receptionNumber = receptionNumber;
    }

    @Column(name = "SHIPMENT_NUMBER", length = 20)
    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    @Column(name = "SLIP_NUMBER", length = 20)
    public String getSlipNumber() {
        return slipNumber;
    }

    public void setSlipNumber(String slipNumber) {
        this.slipNumber = slipNumber;
    }

    @Column(name = "SUPPLIER_CODE")
    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    @Column(name = "RECEPTION_DATE")
    public Date getReceptionDate() {
        return receptionDate;
    }

    public void setReceptionDate(Date receptionDate) {
        this.receptionDate = receptionDate;
    }

    @Column(name = "INVOICING_DATE", length = 20)
    public String getInvoicingDate() {
        return invoicingDate;
    }

    public void setInvoicingDate(String invoicingDate) {
        this.invoicingDate = invoicingDate;
    }

    @Column(name = "COMING_FROMHEAD", length = 20)
    public String getComingFromhead() {
        return comingFromhead;
    }

    public void setComingFromhead(String comingFromhead) {
        this.comingFromhead = comingFromhead;
    }

    @Column(name = "RECEPTION_HOUR", length = 10)
    public String getReceptionHour() {
        return receptionHour;
    }

    public void setReceptionHour(String receptionHour) {
        this.receptionHour = receptionHour;
    }

    @Column(name = "RECEIVED_BY", length = 20)
    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    @Column(name = "ZERE_FIELD")
    public int getZere_field() {
        return zere_field;
    }

    public void setZere_field(int zereField) {
        zere_field = zereField;
    }

    @Column(name = "BLANK_1", length = 20)
    public String getBlank_1() {
        return blank_1;
    }

    public void setBlank_1(String blank_1) {
        this.blank_1 = blank_1;
    }

    @Column(name = "BLANK_2", length = 20)
    public String getBlank_2() {
        return blank_2;
    }

    public void setBlank_2(String blank_2) {
        this.blank_2 = blank_2;
    }

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
