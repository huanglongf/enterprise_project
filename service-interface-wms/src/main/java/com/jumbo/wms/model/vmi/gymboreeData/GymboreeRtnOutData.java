package com.jumbo.wms.model.vmi.gymboreeData;

import java.math.BigDecimal;
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
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;

/**
 * 店存出库(入库)反馈实体
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_GYM_RTN_RETURN")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class GymboreeRtnOutData extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 5471181451464614073L;
    public static final Integer OUTBOUND = 2;// 店存出库
    public static final Integer INBOUND = 1;// 店存入库
    public static final Integer INVIN = 3;// 其他入库
    public static final Integer INVOUT = 4;// 其他出库
    private Long id;
    private String fchrItemId;
    private Long flotQuantity;
    private String fchrSourceCode;
    private String fchrWarehouseID;
    private String fdtmDate;
    private String fchrEmployeeID;
    private String fchrMaker;
    private BigDecimal flotQuotePrice;
    private BigDecimal flotMoney;
    private Long staId;
    private DefaultStatus status;
    private Date createTime;
    private Date finishTime;
    private int version;
    private Integer type;
    private String fchrBarcode;
    private String fchrFree2;
    private String fchrInOutVouchID;
    private String fchrOutVouchDetailID;
    private String owner;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_GYM_RTN_RETURN", sequenceName = "S_T_GYM_RTN_RETURN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_GYM_RTN_RETURN")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "FCHR_ITEM_ID", length = 60)
    public String getFchrItemId() {
        return fchrItemId;
    }

    public void setFchrItemId(String fchrItemId) {
        this.fchrItemId = fchrItemId;
    }

    @Column(name = "FLOT_QUANTITY")
    public Long getFlotQuantity() {
        return flotQuantity;
    }

    public void setFlotQuantity(Long flotQuantity) {
        this.flotQuantity = flotQuantity;
    }

    @Column(name = "FCHR_SOURCE_CODE", length = 100)
    public String getFchrSourceCode() {
        return fchrSourceCode;
    }

    public void setFchrSourceCode(String fchrSourceCode) {
        this.fchrSourceCode = fchrSourceCode;
    }

    @Column(name = "FCHR_WAREHOUSE_ID", length = 60)
    public String getFchrWarehouseID() {
        return fchrWarehouseID;
    }

    public void setFchrWarehouseID(String fchrWarehouseID) {
        this.fchrWarehouseID = fchrWarehouseID;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "FINISH_TIME")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "FDTM_DATE", length = 100)
    public String getFdtmDate() {
        return fdtmDate;
    }

    public void setFdtmDate(String fdtmDate) {
        this.fdtmDate = fdtmDate;
    }

    @Column(name = "FCHR_EMPLOYEE_ID", length = 100)
    public String getFchrEmployeeID() {
        return fchrEmployeeID;
    }

    public void setFchrEmployeeID(String fchrEmployeeID) {
        this.fchrEmployeeID = fchrEmployeeID;
    }

    @Column(name = "FCHR_MARKER", length = 100)
    public String getFchrMaker() {
        return fchrMaker;
    }

    public void setFchrMaker(String fchrMaker) {
        this.fchrMaker = fchrMaker;
    }

    @Column(name = "FLOT_QUOTE_PRICE")
    public BigDecimal getFlotQuotePrice() {
        return flotQuotePrice;
    }

    public void setFlotQuotePrice(BigDecimal flotQuotePrice) {
        this.flotQuotePrice = flotQuotePrice;
    }

    @Column(name = "FLOT_MONEY")
    public BigDecimal getFlotMoney() {
        return flotMoney;
    }

    public void setFlotMoney(BigDecimal flotMoney) {
        this.flotMoney = flotMoney;
    }

    @Column(name = "FCHR_BARCODE", length = 60)
    public String getFchrBarcode() {
        return fchrBarcode;
    }

    public void setFchrBarcode(String fchrBarcode) {
        this.fchrBarcode = fchrBarcode;
    }

    @Column(name = "FCHR_FREE2", length = 60)
    public String getFchrFree2() {
        return fchrFree2;
    }

    public void setFchrFree2(String fchrFree2) {
        this.fchrFree2 = fchrFree2;
    }

    @Column(name = "FCHR_INOUT_VOUCH_ID", length = 60)
    public String getFchrInOutVouchID() {
        return fchrInOutVouchID;
    }

    public void setFchrInOutVouchID(String fchrInOutVouchID) {
        this.fchrInOutVouchID = fchrInOutVouchID;
    }

    @Column(name = "FD_ID", length = 100)
    public String getFchrOutVouchDetailID() {
        return fchrOutVouchDetailID;
    }

    public void setFchrOutVouchDetailID(String fchrOutVouchDetailID) {
        this.fchrOutVouchDetailID = fchrOutVouchDetailID;
    }

    @Column(name = "OWNER", length = 20)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
