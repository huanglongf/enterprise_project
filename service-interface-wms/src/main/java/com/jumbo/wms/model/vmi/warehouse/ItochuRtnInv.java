package com.jumbo.wms.model.vmi.warehouse;


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
@Table(name = "T_WH_ITOCHU_RTN_INV")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ItochuRtnInv extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6192842209453066474L;

    private Long id;
    private String warehouse;
    private String company;
    private String sku;
    private Long qty;
    private String invStatus;
    private Integer VERSION;
    private String invup;
    private Date createTime;

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "INVUP", length = 50)
    public String getInvup() {
        return invup;
    }

    public void setInvup(String invup) {
        this.invup = invup;
    }

    @Column(name = "VERSION")
    public Integer getVERSION() {
        return VERSION;
    }

    public void setVERSION(Integer vERSION) {
        VERSION = vERSION;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_ITOCHU_RTN_INV", sequenceName = "S_T_WH_ITOCHU_RTN_INV", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_ITOCHU_RTN_INV")
    public Long getId() {
        return id;
    }

    @Column(name = "WAREHOUSE", length = 100)
    public String getWarehouse() {
        return warehouse;
    }


    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    @Column(name = "COMPANY", length = 100)
    public String getCompany() {
        return company;
    }


    public void setCompany(String company) {
        this.company = company;
    }

    @Column(name = "SKU", length = 20)
    public String getSku() {
        return sku;
    }


    public void setSku(String sku) {
        this.sku = sku;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }


    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "INV_STATUS", length = 20)
    public String getInvStatus() {
        return invStatus;
    }


    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }


    public void setId(Long id) {
        this.id = id;
    }



}
