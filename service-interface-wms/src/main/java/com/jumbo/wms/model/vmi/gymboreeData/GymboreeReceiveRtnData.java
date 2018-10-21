package com.jumbo.wms.model.vmi.gymboreeData;

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
 * 金宝贝店存出库接收实体
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_GYM_RTN_RECEIVE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class GymboreeReceiveRtnData extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 3001036056606253256L;
    private Long id;
    private String fchrWarehouseID;
    private String item;
    private String erpVouchCode;
    private String moveTo;
    private String userDef4;
    private Long totalQty;
    private String inoutTime;
    private String skuCode;
    private Long skuId;
    private Long staId;
    private Long invStatusId;
    private DefaultStatus status;
    private Date createTime;
    private Date finishTime;
    private int version;
    private String owner;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_GYM_RTN_RECEIVE", sequenceName = "S_T_GYM_RTN_RECEIVE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_GYM_RTN_RECEIVE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "FCHR_WAREHOUSE_ID", length = 30)
    public String getFchrWarehouseID() {
        return fchrWarehouseID;
    }

    public void setFchrWarehouseID(String fchrWarehouseID) {
        this.fchrWarehouseID = fchrWarehouseID;
    }

    @Column(name = "ITEM", length = 30)
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Column(name = "ERP_VOUCH_CODE", length = 60)
    public String getErpVouchCode() {
        return erpVouchCode;
    }

    public void setErpVouchCode(String erpVouchCode) {
        this.erpVouchCode = erpVouchCode;
    }

    @Column(name = "MOVE_TO", length = 60)
    public String getMoveTo() {
        return moveTo;
    }

    public void setMoveTo(String moveTo) {
        this.moveTo = moveTo;
    }

    @Column(name = "USER_DEF4", length = 30)
    public String getUserDef4() {
        return userDef4;
    }

    public void setUserDef4(String userDef4) {
        this.userDef4 = userDef4;
    }

    @Column(name = "TOTAL_QTY")
    public Long getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Long totalQty) {
        this.totalQty = totalQty;
    }

    @Column(name = "INOUT_TIME")
    public String getInoutTime() {
        return inoutTime;
    }

    public void setInoutTime(String inoutTime) {
        this.inoutTime = inoutTime;
    }

    @Column(name = "SKU_CODE", length = 150)
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "INV_STATUS_ID")
    public Long getInvStatusId() {
        return invStatusId;
    }

    public void setInvStatusId(Long invStatusId) {
        this.invStatusId = invStatusId;
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

    @Column(name = "OWNER", length = 20)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
