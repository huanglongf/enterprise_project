package com.jumbo.wms.model.vmi.gymboreeData;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 金宝贝仓库档案
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_GYMBOREE_WAREHOUSE")
public class GymboreeWarehouse implements Serializable {


    private static final long serialVersionUID = 7115074765921939459L;
    private String fchrWhCode;
    private String fchrWarehouseID;
    private String fchrMemo;
    private String fchrWhName;
    private String fchrAddress;
    private String fchrPhone;
    private String fchrLinkman;
    private String fchrStoreFlag;
    private String fchrWarehouseOrgID;
    private String fbitNoUsed;
    private String fbitWhPos;
    private String fchrAccountID;
    private String fchrOrgCode;
    private String fchrOrgName;
    private Date createTime;
    private Long id;

    @Column(name = "FCHR_WH_CODE", length = 100)
    public String getFchrWhCode() {
        return fchrWhCode;
    }

    public void setFchrWhCode(String fchrWhCode) {
        this.fchrWhCode = fchrWhCode;
    }

    @Column(name = "FCHR_WAREHOUSE_ID", length = 100)
    public String getFchrWarehouseID() {
        return fchrWarehouseID;
    }

    public void setFchrWarehouseID(String fchrWarehouseID) {
        this.fchrWarehouseID = fchrWarehouseID;
    }

    @Column(name = "FCHR_MEMO", length = 100)
    public String getFchrMemo() {
        return fchrMemo;
    }

    public void setFchrMemo(String fchrMemo) {
        this.fchrMemo = fchrMemo;
    }

    @Column(name = "FCHR_WH_NAME", length = 100)
    public String getFchrWhName() {
        return fchrWhName;
    }

    public void setFchrWhName(String fchrWhName) {
        this.fchrWhName = fchrWhName;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_GYMBOREE_WAREHOUSE", sequenceName = "S_T_GYMBOREE_WAREHOUSE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_GYMBOREE_WAREHOUSE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "FCHR_ADDRESS", length = 100)
    public String getFchrAddress() {
        return fchrAddress;
    }

    public void setFchrAddress(String fchrAddress) {
        this.fchrAddress = fchrAddress;
    }

    @Column(name = "FCHR_PHONE", length = 100)
    public String getFchrPhone() {
        return fchrPhone;
    }

    public void setFchrPhone(String fchrPhone) {
        this.fchrPhone = fchrPhone;
    }

    @Column(name = "FCHR_LINK_MAN", length = 100)
    public String getFchrLinkman() {
        return fchrLinkman;
    }

    public void setFchrLinkman(String fchrLinkman) {
        this.fchrLinkman = fchrLinkman;
    }

    @Column(name = "FCHR_STORE_FLAG", length = 100)
    public String getFchrStoreFlag() {
        return fchrStoreFlag;
    }

    public void setFchrStoreFlag(String fchrStoreFlag) {
        this.fchrStoreFlag = fchrStoreFlag;
    }

    @Column(name = "FCHR_WH_ORG_ID", length = 100)
    public String getFchrWarehouseOrgID() {
        return fchrWarehouseOrgID;
    }

    public void setFchrWarehouseOrgID(String fchrWarehouseOrgID) {
        this.fchrWarehouseOrgID = fchrWarehouseOrgID;
    }

    @Column(name = "FBIT_NO_USED", length = 100)
    public String getFbitNoUsed() {
        return fbitNoUsed;
    }

    public void setFbitNoUsed(String fbitNoUsed) {
        this.fbitNoUsed = fbitNoUsed;
    }

    @Column(name = "FBIT_WH_POS", length = 100)
    public String getFbitWhPos() {
        return fbitWhPos;
    }

    public void setFbitWhPos(String fbitWhPos) {
        this.fbitWhPos = fbitWhPos;
    }

    @Column(name = "FCHR_AC_ID", length = 100)
    public String getFchrAccountID() {
        return fchrAccountID;
    }

    public void setFchrAccountID(String fchrAccountID) {
        this.fchrAccountID = fchrAccountID;
    }

    @Column(name = "FCHR_ORG_CODE", length = 100)
    public String getFchrOrgCode() {
        return fchrOrgCode;
    }

    public void setFchrOrgCode(String fchrOrgCode) {
        this.fchrOrgCode = fchrOrgCode;
    }

    @Column(name = "FCHR_ORG_NAME", length = 100)
    public String getFchrOrgName() {
        return fchrOrgName;
    }

    public void setFchrOrgName(String fchrOrgName) {
        this.fchrOrgName = fchrOrgName;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
