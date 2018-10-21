package com.jumbo.wms.model.vmi.guess;

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
import com.jumbo.wms.model.DefaultStatus;

@Entity
@Table(name = "T_GUESS_ECOM_ADJ_DATA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class GuessEcomAdjData extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 9059420576534802017L;

    private Long id;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date updateDate;

    
    private String shortSku;
    
    private Long qty;
    
    private String reasonCode;
    
    
    private String adjCode;
    
    private String staCode;
    
    /**
     * 状态
     */
    private DefaultStatus status;
    
    private String  binCode;
    
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_GUESS_ECOM_ADJ_DATA", sequenceName = "S_T_GUESS_ECOM_ADJ_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GUESS_ECOM_ADJ_DATA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "UPDATE_DATE")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Column(name = "SHORT_SKU", length = 50)
    public String getShortSku() {
        return shortSku;
    }

    public void setShortSku(String shortSku) {
        this.shortSku = shortSku;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "REASON_CODE")
    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }
    @Column(name = "ADJ_CODE")
    public String getAdjCode() {
        return adjCode;
    }

    public void setAdjCode(String adjCode) {
        this.adjCode = adjCode;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }
    
    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }
    
    @Column(name = "BIN_CODE")
    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
    }
}
