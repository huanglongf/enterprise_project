package com.jumbo.wms.model.vmi.af;

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
@Table(name = "AF_INVENTORY_COMPARE_REPORT")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class AFInventoryCompareReport extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 6520599594298142827L;

    private Long id;
    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * UPC
     */
    private String UPC;
    
    /**
     * 宝尊库存量
     */
    private Long bzQty;

    /**
     * 利丰库存量
     */
    private Long lfQty;
    
    /**
     * 差异量
     */
    private Long differenceQty;
    
    /**
     * 状态
     */
    private DefaultStatus status;
    
    /**
     * 库存状态
     */
    private String invStatus;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_INV_COM_REPORT", sequenceName = "S_AF_INV_COM_REPORT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INV_COM_REPORT")
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

    @Column(name = "UPC", length = 50)
    public String getUPC() {
        return UPC;
    }

    public void setUPC(String uPC) {
        this.UPC = uPC;
    }

    @Column(name = "BAOZUN_QTY")
    public Long getBzQty() {
        return bzQty;
    }

    public void setBzQty(Long bzQty) {
        this.bzQty = bzQty;
    }

    @Column(name = "LIFENG_QTY")
    public Long getLfQty() {
        return lfQty;
    }

    public void setLfQty(Long lfQty) {
        this.lfQty = lfQty;
    }

    @Column(name = "DIFFERENCE_QTY")
    public Long getDifferenceQty() {
        return differenceQty;
    }

    public void setDifferenceQty(Long differenceQty) {
        this.differenceQty = differenceQty;
    }
    
    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }
    
    @Column(name = "INVENTORY_STATUS", length = 20)
    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
    	this.invStatus = invStatus;
    }

}
