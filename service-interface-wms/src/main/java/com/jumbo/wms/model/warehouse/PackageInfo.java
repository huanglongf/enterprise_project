package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;
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
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Sku;

/**
 * 快递包裹
 * 
 * @author sjk
 * 
 */

@Entity
@Table(name = "T_WH_PACKAGE_INFO", uniqueConstraints = {@UniqueConstraint(columnNames = {"TRACKING_NO"})})
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class PackageInfo extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 885583595408215155L;
    /**
     * Pk
     */
    private Long id;
    /**
     * version
     */
    private int version;

    /**
     * 物流单号
     */
    private String trackingNo;

    /**
     * 快递费
     */
    private BigDecimal transferFee;

    /**
     * 物流成本
     */
    private BigDecimal transferCose;

    /**
     * 状态
     */
    private PackageInfoStatus status = PackageInfoStatus.CREATED;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 物流供应商编码
     */
    private String lpCode;
    /**
     * 是否已经核对
     */
    private Boolean isChecked;
    /**
     * 物流信息
     */
    private StaDeliveryInfo staDeliveryInfo;
    /**
     * 创建时间
     */
    private Date createTime = new Date();

    /**
     * 修改时间
     */
    private Date lastModifyTime;
    /**
     * 是否允许交接
     */
    private Boolean isHandover;

    /**
     * 阿里云栈包裹号
     */
    private String aliPackageNo;

    /**
     * 交接清单明细实体
     * 
     * @return
     */
    private HandOverListLine handOverLine;

    /**
     * 商品耗材
     * 
     * @return
     */
    private com.jumbo.wms.model.baseinfo.Sku sku;


    /**
     * 组织ID（仓库）
     */
    private OperationUnit opUnit;

    /**
     * 快递包裹
     * 
     * @return
     */
    private TransPackage packAge;


    /**
     * 物流单号
     */
    private String oldTrackingNo;


    private String oldLpcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TANS_PG_ID")
    @Index(name = "T_WH_PACKAGE_INFO_FK_TRANS_PACKAGE_ID")
    public TransPackage getPackAge() {
        return packAge;
    }

    public void setPackAge(TransPackage packAge) {
        this.packAge = packAge;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HO_LIST_LINE_ID")
    @Index(name = "T_WH_PACKAGE_INFO_FK_HO_LIST_LINE_ID")
    public HandOverListLine getHandOverLine() {
        return handOverLine;
    }

    public void setHandOverLine(HandOverListLine handOverLine) {
        this.handOverLine = handOverLine;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "T_WH_PACKAGE_INFO_FK_SKU_ID")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "T_WH_PACKAGE_INFO_FK_OU_ID")
    public OperationUnit getOpUnit() {
        return opUnit;
    }

    public void setOpUnit(OperationUnit opUnit) {
        this.opUnit = opUnit;
    }


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PKLT", sequenceName = "S_T_WH_PACKAGE_INFO", allocationSize = 1)
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

    @Column(name = "TRACKING_NO", length = 100)
    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    @Column(name = "TRANSFER_FEE")
    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public void setTransferFee(BigDecimal transferFee) {
        this.transferFee = transferFee;
    }

    @Column(name = "TRANSFER_COST")
    public BigDecimal getTransferCose() {
        return transferCose;
    }

    public void setTransferCose(BigDecimal transferCose) {
        this.transferCose = transferCose;
    }

    @Column(name = "WEIGHT")
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Column(name = "LPCODE", length = 20)
    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    @Column(name = "IS_CHECKED")
    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_DELIVERY_INFO_ID")
    @Index(name = "IDX_FK_STA_DELIVERY_INFO_ID")
    public StaDeliveryInfo getStaDeliveryInfo() {
        return staDeliveryInfo;
    }

    public void setStaDeliveryInfo(StaDeliveryInfo staDeliveryInfo) {
        this.staDeliveryInfo = staDeliveryInfo;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.PackageInfoStatus")})
    public PackageInfoStatus getStatus() {
        return status;
    }

    public void setStatus(PackageInfoStatus status) {
        this.status = status;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Column(name = "is_Handover")
    public Boolean getIsHandover() {
        return isHandover;
    }

    public void setIsHandover(Boolean isHandover) {
        this.isHandover = isHandover;
    }

    @Column(name = "ALI_PACKAGE_NO")
    public String getAliPackageNo() {
        return aliPackageNo;
    }

    public void setAliPackageNo(String aliPackageNo) {
        this.aliPackageNo = aliPackageNo;
    }

    @Column(name = "OLD_TRACKING_NO")
    public String getOldTrackingNo() {
        return oldTrackingNo;
    }

    public void setOldTrackingNo(String oldTrackingNo) {
        this.oldTrackingNo = oldTrackingNo;
    }

    @Column(name = "OLD_LPCODE")
    public String getOldLpcode() {
        return oldLpcode;
    }

    public void setOldLpcode(String oldLpcode) {
        this.oldLpcode = oldLpcode;
    }


}
