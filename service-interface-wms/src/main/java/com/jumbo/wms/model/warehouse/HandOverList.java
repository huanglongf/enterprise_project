package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
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
 * 出库单
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_WH_STA_HO_LIST")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class HandOverList extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6671080804492171304L;
    /**
     * PK
     */
    private Long id;
    /**
     * version
     */
    private int version;
    /**
     * 编码 lpcode+ "-" + yyMMddHHmmss构成
     */
    private String code;
    /**
     * 单据数量
     */
    private Integer billCount;
    /**
     * 包裹数量
     */
    private Integer packageCount;
    /**
     * 总重量
     */
    private BigDecimal totalWeight;
    /**
     * 发货方
     */
    private String sender;
    /**
     * 宝尊交接人
     */
    private String partyAOperator;
    /**
     * 宝尊联系方式
     */
    private String paytyAMobile;
    /**
     * 系统用户
     */
    private User operator;
    /**
     * 修改人
     */
    private User modifier;
    /**
     * 物流方交接人
     */
    private String partyBOperator;
    /**
     * 物流方联系方式
     */
    private String paytyBMobile;
    /**
     * 物流方证件号（车牌号）
     */
    private String paytyBPassPort;
    /**
     * 物流供应商
     */
    private String lpcode;
    /**
     * 最后一次修改时间
     */
    private Date lastModifyTime;
    /**
     * 交接清单创建时间
     */
    private Date createTime;
    /**
     * 交接时间
     */
    private Date handOverTime;

    /**
     * 状态
     */
    private HandOverListStatus status;
    /**
     * 组织(根据TYPE区分仓库还是运营中心)
     */
    private OperationUnit ou;

    /**
     * 作业单列表
     */
    //private List<StockTransApplication> stas;
    /**
     * 明细行
     */
    private List<HandOverListLine> lines;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_STA", sequenceName = "S_T_WH_STA_HO_LIST", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CODE", length = 20)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "BILL_COUNT")
    public Integer getBillCount() {
        return billCount;
    }

    public void setBillCount(Integer billCount) {
        this.billCount = billCount;
    }

    @Column(name = "PACKAGE_COUNT")
    public Integer getPackageCount() {
        return packageCount;
    }

    public void setPackageCount(Integer packageCount) {
        this.packageCount = packageCount;
    }

    @Column(name = "TOTAL_WEIGHT")
    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }

    @Column(name = "SENDER", length = 100)
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Column(name = "PARTY_A_OPERATOR", length = 50)
    public String getPartyAOperator() {
        return partyAOperator;
    }

    public void setPartyAOperator(String partyAOperator) {
        this.partyAOperator = partyAOperator;
    }

    @Column(name = "PARTY_A_MOBILE", length = 50)
    public String getPaytyAMobile() {
        return paytyAMobile;
    }

    public void setPaytyAMobile(String paytyAMobile) {
        this.paytyAMobile = paytyAMobile;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATOR_ID")
    @Index(name = "IDX_FK_OPERATOR_ID")
    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODIFER_ID")
    @Index(name = "IDX_FK_MODIFER_ID")
    public User getModifier() {
        return modifier;
    }

    public void setModifier(User modifier) {
        this.modifier = modifier;
    }

    @Column(name = "PARTY_B_OPERATOR", length = 50)
    public String getPartyBOperator() {
        return partyBOperator;
    }

    public void setPartyBOperator(String partyBOperator) {
        this.partyBOperator = partyBOperator;
    }

    @Column(name = "PARTY_B_MOBILE", length = 50)
    public String getPaytyBMobile() {
        return paytyBMobile;
    }

    public void setPaytyBMobile(String paytyBMobile) {
        this.paytyBMobile = paytyBMobile;
    }

    @Column(name = "PARTY_B_PASSPORT", length = 50)
    public String getPaytyBPassPort() {
        return paytyBPassPort;
    }

    public void setPaytyBPassPort(String paytyBPassPort) {
        this.paytyBPassPort = paytyBPassPort;
    }

    @Column(name = "LPCODE", length = 20)
    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "HAND_OVER_TIME")
    public Date getHandOverTime() {
        return handOverTime;
    }

    public void setHandOverTime(Date handOverTime) {
        this.handOverTime = handOverTime;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.HandOverListStatus")})
    public HandOverListStatus getStatus() {
        return status;
    }

    public void setStatus(HandOverListStatus status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_FK_OU_ID")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "hoList", orphanRemoval = true)
    @OrderBy("id")
    public List<HandOverListLine> getLines() {
        return lines;
    }

    public void setLines(List<HandOverListLine> lines) {
        this.lines = lines;
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Transient
    public int getIntStatus() {
        return status == null ? -1 : status.getValue();
    }
}
