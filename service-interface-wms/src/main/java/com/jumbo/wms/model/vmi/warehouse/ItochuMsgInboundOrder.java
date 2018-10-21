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
@Table(name = "T_WH_MSG_ITOCHU_INBOUND_ORDER")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ItochuMsgInboundOrder extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4909658255884851571L;
    /**
     * pk
     */
    private Long id;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_ITOCHU_INBOUND_ORDER", sequenceName = "S_T_WH_ITOCHU_INBOUND_ORDER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_ITOCHU_INBOUND_ORDER")
    public Long getId() {
        return id;
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


    @Column(name = "D_T")
    public String getDT() {
        return DT;
    }

    public void setDT(String dT) {
        DT = dT;
    }


    @Column(name = "SKU", length = 25)
    public String getSku() {
        return sku;
    }

    @Column(name = "BILL_NO", length = 25)
    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    @Column(name = "BOX_NO", length = 25)
    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Column(name = "QUANTITY")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Column(name = "USERDEF1", length = 25)
    public String getUserDef1() {
        return userDef1;
    }

    public void setUserDef1(String userDef1) {
        this.userDef1 = userDef1;
    }

    @Column(name = "USERDEF2", length = 25)
    public String getUserDef2() {
        return userDef2;
    }

    public void setUserDef2(String userDef2) {
        this.userDef2 = userDef2;
    }

    @Column(name = "USERDEF3", length = 25)
    public String getUserDef3() {
        return userDef3;
    }

    public void setUserDef3(String userDef3) {
        this.userDef3 = userDef3;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    /**
     * version
     */
    private int version;
    /**
     * 业务单据编号
     */
    private String billNo;
    /**
     * 仓库到达日期
     */
    private String DT;
    /**
     * 箱号
     */
    private String boxNo;
    /**
     * sku
     */
    private String sku;
    /**
     * 数量
     */
    private int quantity;
    /**
     * 备用字段1
     */
    private String userDef1;
    /**
     * 备用字段2
     */
    private String userDef2;
    /**
     * 备用字段3
     */
    private String userDef3;
    /**
     * 状态
     */
    private DefaultStatus status;

    /**
     * staId
     */
    private Long staId;
    /**
     * staLineId
     */
    private Long staLineId;

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "STA_LINE_ID")
    public Long getStaLineId() {
        return staLineId;
    }

    public void setStaLineId(Long staLineId) {
        this.staLineId = staLineId;
    }

    /**
     * 商品库存状态
     */
    private String invStatus;


    @Column(name = "INV_STATUS")
    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }
}
