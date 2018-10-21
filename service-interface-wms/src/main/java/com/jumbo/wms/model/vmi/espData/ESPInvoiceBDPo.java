package com.jumbo.wms.model.vmi.espData;


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
@Table(name = "T_WH_ESP_INVOICE_BD_PO")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ESPInvoiceBDPo extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6085314417834072073L;


    private Long id;

    private Date createTime;

    private String po;

    private String invoiceNumber;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_ESP", sequenceName = "S_T_WH_ESP_INVOICE_BD_PO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESP")
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

    @Column(name = "po", length = 100)
    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    @Column(name = "invoice_number", length = 100)
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
}
