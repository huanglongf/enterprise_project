package com.jumbo.wms.model.pda;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;

/**
 * PDA 上传单据头
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_WH_PDA_ORDER")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class PdaOrder extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -1450145323958513474L;
    /**
     * PK
     */
    private Long id;
    /**
     * version
     */
    private int version;
    /**
     * 单据号
     */
    private String orderCode;
    /**
     * 仓库组织编码
     */
    private String ouId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 状态
     */
    private DefaultStatus status;
    /**
     * 单据类型
     */
    private PdaOrderType type;
    /**
     * 完成时间
     */
    private Date finishTime;
    /**
     * 快递单号（执行销售出库用到）
     */
    private String transNo;
    /**
     * 备注信息（执行之后信息记录）
     */
    private String memo;
    /**
     * 判断是Pda主动还是被动
     */
    private Boolean isPDA;
    /**
     * 前置单据号
     */
    private String slipCode;
    /**
     * 物流商编码
     */
    private String lpCode;
    /**
     * 操作人
     */
    private String userName;
    

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PDA_ORDER", sequenceName = "S_T_WH_PDA_ORDER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PDA_ORDER")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ORDER_CODE", length = 60)
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "OU_ID")
    public String getOuId() {
        return ouId;
    }

    public void setOuId(String ouId) {
        this.ouId = ouId;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.pda.PdaOrderType")})
    public PdaOrderType getType() {
        return type;
    }

    public void setType(PdaOrderType type) {
        this.type = type;
    }

    @Column(name = "FINISH_TIME")
    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "TRAN_NO", length = 20)
    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    @Column(name = "MEMO", length = 200)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "IS_PDA")
    public Boolean getIsPDA() {
        return isPDA;
    }

    public void setIsPDA(Boolean isPDA) {
        this.isPDA = isPDA;
    }

    @Transient
    public int getIntStatus() {
        return status == null ? -2 : status.getValue();
    }

    public void setIntStatus(int intStatus) {
        setStatus(DefaultStatus.valueOf(intStatus));
    }

    public void setIntType(int intType) {
        setType(PdaOrderType.valueOf(intType));
    }

    @Transient
    public int getIntType() {
        return type == null ? -1 : type.getValue();
    }

    @Column(name = "SLIP_CODE", length = 60)
    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    @Column(name = "LPCODE", length = 60)
    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    @Column(name = "USER_NAME", length = 50)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
