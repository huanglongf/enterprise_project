package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.BiChannel;

/**
 * 库存盘点
 * 
 * @author sjk
 * 
 */

@Entity
@Table(name = "T_WH_INV_CHECK")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class InventoryCheck extends BaseModel {

    private static final long serialVersionUID = 6095015813860971532L;
    /**
     * Pk
     */
    private Long id;
    /**
     * 编码
     */
    private String code;
    /**
     * version
     */
    private int version;
    /**
     * 财务确认人
     */
    private String confirmUser;
    /**
     * 备注
     */
    private String remork;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private User creator;
    /**
     * 取消人
     */
    private User cancelUser;
    /**
     * 状态
     */
    private InventoryCheckStatus status;
    /**
     * 盘点表类型
     */
    private InventoryCheckType type = InventoryCheckType.NORMAL;
    /**
     * 仓库
     */
    private OperationUnit ou;
    // /**
    // * 店铺
    // */
    // private OperationUnit shop;

    private BiChannel shop;

    /**
     * 相关单据号
     */
    private String slipCode;

    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 发票号
     */
    private String invoiceNumber;
    /**
     * 税收系数(ESPRITS使用)
     */
    private Double dutyPercentage;
    /**
     * 其他系数(ESPRITS使用)
     */
    private Double miscFeePercentage;
    /**
     * 操作人
     */
    private User operatorUserId;
    /**
     * 操作人操作时间
     */
    private Date operatorTime;
    /**
     * 操作经理
     */
    private User managerUserId;
    /**
     * 操作经理操作时间
     */
    private Date managerTime;

    /**
     * 记录LevisADJ 入口 新接口2 ，老接口（文件）1
     */
    private Integer invType;
    /**
     * 原因编码
     */
    private String reasonCode;
    /**
     * 错误次数
     */
    private Integer errorCount;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PKLT", sequenceName = "S_T_WH_INV_CHECK", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PKLT")
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

    @Column(name = "CODE", length = 30)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "CONFIRM_USER", length = 50)
    public String getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(String confirmUser) {
        this.confirmUser = confirmUser;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATOR_ID")
    @Index(name = "IDX_INV_CK_CREATOR_ID")
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.InventoryCheckStatus")})
    public InventoryCheckStatus getStatus() {
        return status;
    }

    public void setStatus(InventoryCheckStatus status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_INV_CK_OU_ID")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    @Column(name = "REMORK", length = 250)
    public String getRemork() {
        return remork;
    }

    public void setRemork(String remork) {
        this.remork = remork;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CANCEL_USER_ID")
    @Index(name = "IDX_INV_CK_CANCEL_USER_ID")
    public User getCancelUser() {
        return cancelUser;
    }

    public void setCancelUser(User cancelUser) {
        this.cancelUser = cancelUser;
    }

    public String codePrefix() {
        return "I";
    }

    @Transient
    public Integer getIntStatus() {
        return status == null ? -1 : status.getValue();
    }

    public void setIntStatus(Integer intStatus) {
        if (intStatus != null) {
            setStatus(InventoryCheckStatus.valueOf(intStatus));
        }
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.InventoryCheckType")})
    public InventoryCheckType getType() {
        return type;
    }

    public void setType(InventoryCheckType type) {
        this.type = type;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOP_ID")
    @Index(name = "IDX_INV_CK_SHOP_ID")
    public BiChannel getShop() {
        return shop;
    }

    public void setShop(BiChannel shop) {
        this.shop = shop;
    }

    @Column(name = "SLIP_CODE", length = 100)
    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    @Column(name = "FINISH_TIME")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Column(name = "INVOICE_NUMBER", length = 100)
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATOR_USER_ID")
    @Index(name = "IDX_INV_CK_OPERATOR_ID")
    public User getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(User operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    @Column(name = "OPERATOR_TIME")
    public Date getOperatorTime() {
        return operatorTime;
    }


    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_USER_ID")
    @Index(name = "IDX_INV_CK_MANAGER_ID")
    public User getManagerUserId() {
        return managerUserId;
    }

    public void setManagerUserId(User managerUserId) {
        this.managerUserId = managerUserId;
    }


    @Column(name = "MANAGER_TIME")
    public Date getManagerTime() {
        return managerTime;
    }


    public void setManagerTime(Date managerTime) {
        this.managerTime = managerTime;
    }

    @Column(name = "INV_TYPE")
    public Integer getInvType() {
        return invType;
    }

    public void setInvType(Integer invType) {
        this.invType = invType;
    }

    @Column(name = "REASON_CODE", length = 50)
    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    @Column(name = "error_Count")
    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }



}
