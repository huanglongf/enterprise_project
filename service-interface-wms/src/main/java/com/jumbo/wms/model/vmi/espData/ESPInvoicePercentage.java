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
import com.jumbo.util.StringUtils;


@Entity
@Table(name = "t_esprit_invoice_percentage")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ESPInvoicePercentage extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8866439039762208971L;

    private Long id;

    private String po;

    private String invoiceNum;

    private Double dutyPercentage;

    private Double miscFeePercentage;
    
    private Double commPercentage;

    private Date version;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_ESPRIT_INVOICE_PERCENTAGE", sequenceName = "S_T_ESPRIT_INVOICE_PERCENTAGE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESPRIT_INVOICE_PERCENTAGE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "INVOICE_NUMBER", length = 100)
    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        if (StringUtils.hasText(invoiceNum)) {
            invoiceNum = invoiceNum.toUpperCase();
        }
        this.invoiceNum = invoiceNum;
    }

    @Column(name = "DUTY_PERCENTAGE")
    public Double getDutyPercentage() {
        return dutyPercentage;
    }

    public void setDutyPercentage(Double dutyPercentage) {
        this.dutyPercentage = dutyPercentage;
    }

    @Column(name = "MISC_FEE_PERCENTAGE")
    public Double getMiscFeePercentage() {
        return miscFeePercentage;
    }

    public void setMiscFeePercentage(Double miscFeePercentage) {
        this.miscFeePercentage = miscFeePercentage;
    }
    
    @Column(name = "COMM_PERCENTAGE")
    public Double getCommPercentage() {
        return commPercentage;
    }

    public void setCommPercentage(Double commPercentage) {
        this.commPercentage = commPercentage;
    }

    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Column(name = "PO", length = 100)
    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }



}
