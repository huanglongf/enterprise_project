package com.jumbo.wms.model.vmi.warehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;

//
@Entity
@Table(name = "T_WH_ITOCHU_CHECK_INVENTORY")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ItochuCheckInventory extends BaseModel {


    /**
	 * 
	 */
    private static final long serialVersionUID = 5907942099494609224L;
    private Long id;
    private int version;
    private String billNo;
    private String DT;
    private String sku;
    private String sysType;
    private String quanlity;
    private String userDef1;
    private String userDef2;
    private String userDef3;
    private DefaultStatus status;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_ITOCHU_CHECK_INVENTORY", sequenceName = "S_T_WH_ITOCHU_CHECK_INVENTORY", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_ITOCHU_CHECK_INVENTORY")
    public Long getId() {
        return id;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
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

    @Column(name = "BILL_NO", length = 100)
    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    @Column(name = "D_T", length = 100)
    public String getDT() {
        return DT;
    }

    public void setDT(String dT) {
        DT = dT;
    }

    @Column(name = "SKU", length = 100)
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Column(name = "SYS_TYPE", length = 100)
    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    @Column(name = "QUANLITY", length = 100)
    public String getQuanlity() {
        return quanlity;
    }

    public void setQuanlity(String quanlity) {
        this.quanlity = quanlity;
    }

    @Column(name = "USER_DEF1", length = 100)
    public String getUserDef1() {
        return userDef1;
    }

    public void setUserDef1(String userDef1) {
        this.userDef1 = userDef1;
    }

    @Column(name = "USER_DEF2", length = 100)
    public String getUserDef2() {
        return userDef2;
    }

    public void setUserDef2(String userDef2) {
        this.userDef2 = userDef2;
    }

    @Column(name = "USER_DEF3", length = 100)
    public String getUserDef3() {
        return userDef3;
    }

    public void setUserDef3(String userDef3) {
        this.userDef3 = userDef3;
    }



}
