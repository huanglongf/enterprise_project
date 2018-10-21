/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.wms.model.warehouse;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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

/**
 * 仓库作业明细单
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_WH_STV")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class StockTransVoucher extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6638254880804176324L;

    public StockTransVoucher() {
        super();
    }

    public StockTransVoucher(Long id) {
        super();
        this.id = id;
    }

    /**
     * PK
     */
    private Long id;

    /**
     * 业务批号
     */
    private Long businessSeqNo;

    /**
     * 作业明细单编码
     */
    private String code;

    /**
     * 货主
     */
    private String owner;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 是否PDA收货
     */
    private Boolean isPda;

    /**
     * 状态
     */
    private StockTransVoucherStatus status;

    /**
     * 类型
     */
    private StockTransVoucherType type;

    /**
     * 存放模式
     */
    private InboundStoreMode mode;
    /**
     * 备注
     */
    private String memo;

    /**
     * version
     */
    private int version;

    /**
     * 相关申请单
     */
    private StockTransApplication sta;

    /**
     * 创建人
     */
    private User creator;

    /**
     * 操作人
     */
    private User operator;

    /**
     * 收货人
     */
    private User receiptor;

    /**
     * 核对人
     */
    private User affirmor;

    /**
     * 事务方向
     */
    private TransactionDirection direction;

    /**
     * 事务类型
     */
    private TransactionType transactionType;

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
     * 相关仓库
     */
    private OperationUnit warehouse;

    /**
     * 相关库位
     */
    private String giLocationCode;

    /**
     * 商品数量
     */
    private Long skuQty;

    /**
     * 作业明细单行
     */
    private List<StvLine> stvLines;

    /**
     * 最后修改时间
     */
    private Date lastModifyTime;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_STV", sequenceName = "S_T_WH_STV", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STV")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "BUSINESS_SEQ_NO")
    public Long getBusinessSeqNo() {
        return businessSeqNo;
    }

    public void setBusinessSeqNo(Long businessSeqNo) {
        this.businessSeqNo = businessSeqNo;
    }

    @Column(name = "CODE", length = 100)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "OWNER", length = 100)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.StockTransVoucherStatus")})
    public StockTransVoucherStatus getStatus() {
        return status;
    }

    public void setStatus(StockTransVoucherStatus status) {
        this.status = status;
    }

    @Column(name = "MEMO", length = 255)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    @Index(name = "IDX_STV_STA")
    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATOR_ID")
    @Index(name = "IDX_STV_CREATOR")
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATOR_ID")
    @Index(name = "IDX_STV_OPERATOR")
    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    @Column(name = "DIRECTION", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransactionDirection")})
    public TransactionDirection getDirection() {
        return direction;
    }

    public void setDirection(TransactionDirection direction) {
        this.direction = direction;
    }

    @Transient
    public int getIntDirection() {
        return direction == null ? -1 : direction.getValue();
    }

    public void setIntDirection(int intDirection) {
        setDirection(TransactionDirection.valueOf(intDirection));
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSTYPE_ID")
    @Index(name = "IDX_STV_TRANTYPE")
    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WH_ID")
    @Index(name = "IDX_STV_WH")
    public OperationUnit getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(OperationUnit warehouse) {
        this.warehouse = warehouse;
    }

    @Column(name = "STORE_MODE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.InboundStoreMode")})
    public InboundStoreMode getMode() {
        return mode;
    }

    public void setMode(InboundStoreMode mode) {
        this.mode = mode;
    }

    public void setIntMode(int intMode) {
        setMode(InboundStoreMode.valueOf(intMode));
    }

    @Transient
    public int getIntMode() {
        return mode == null ? -1 : mode.getValue();
    }

    @Column(name = "IS_PDA")
    public Boolean getIsPda() {
        return isPda;
    }

    public void setIsPda(Boolean isPda) {
        this.isPda = isPda;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stv", orphanRemoval = true)
    @OrderBy("id")
    public List<StvLine> getStvLines() {
        return stvLines;
    }

    public void setStvLines(List<StvLine> stvLines) {
        this.stvLines = stvLines;
    }

    private List<StvLineCommand> stvlineCommandList;

    @Transient
    public List<StvLineCommand> getStvlineCommandList() {
        return stvlineCommandList;
    }

    public void setStvlineCommandList(List<StvLineCommand> stvlineCommandList) {
        this.stvlineCommandList = stvlineCommandList;
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

    @Column(name = "GI_LOCATION_CODE")
    public String getGiLocationCode() {
        return giLocationCode;
    }

    public void setGiLocationCode(String giLocationCode) {
        this.giLocationCode = giLocationCode;
    }

    @Column(name = "SKU_QTY")
    public Long getSkuQty() {
        return skuQty;
    }

    public void setSkuQty(Long skuQty) {
        this.skuQty = skuQty;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.StockTransVoucherType")})
    public StockTransVoucherType getType() {
        return type;
    }

    public void setType(StockTransVoucherType type) {
        this.type = type;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIPTOR_ID")
    @Index(name = "IDX_STV_RECEIPTOR_ID")
    public User getReceiptor() {
        return receiptor;
    }

    public void setReceiptor(User receiptor) {
        this.receiptor = receiptor;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AFFIRMOR_ID")
    @Index(name = "IDX_STV_AFFIRMOR_ID")
    public User getAffirmor() {
        return affirmor;
    }

    public void setAffirmor(User affirmor) {
        this.affirmor = affirmor;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}
