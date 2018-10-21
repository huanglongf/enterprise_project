package com.jumbo.wms.model.vmi.warehouse;

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
import com.jumbo.wms.model.warehouse.StockTransApplicationType;

//
@Entity
@Table(name = "T_WH_MSG_INBOUND_ORDER")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgInboundOrder extends BaseModel {

    private static final long serialVersionUID = 6881955603524063203L;

    private Long id;
    private int version;

    /**
     * 批次号
     */
    private Long batchId;

    /**
     * 来源
     */
    private String source;

    /**
     * 来源仓库
     */
    private String sourceWh;

    /**
     * 作业单号
     */
    private String staCode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 入库类型
     */
    private StockTransApplicationType type;

    /**
     * 预计到货日期
     */
    private Date planArriveTime;

    /**
     * 金额
     */
    private BigDecimal totalActual;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
     */
    private DefaultStatus status;

    /**
     * 前置单据编码
     */
    private String refSlipCode;

    private String owner;

    /**
     * 物流供应商编码
     */
    private String lpCode;
    /**
     * 快递单号
     */
    private String trackingNo;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 手机
     */
    private String mobile;

    /**
     * 物流宝单号
     */
    private String wlbCode;
    /**
     * 批次号
     */
    private String batchCode;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 是否发送邮件
     */
    private Long isMail;

    /**
     * 错误次数
     */
    private Long errorCount;

    /**
     * 扩展字段，json格式
     */
    private String extMemo;

    /**
     * 外包仓数据唯一标识
     */
    private Long uuid;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_MSG_INBOUND_ORDER", sequenceName = "S_T_WH_MSG_INBOUND_ORDER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_MSG_INBOUND_ORDER")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SOURCE", length = 30)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "SOURCEWH", length = 30)
    public String getSourceWh() {
        return sourceWh;
    }

    public void setSourceWh(String sourceWh) {
        this.sourceWh = sourceWh;
    }

    @Column(name = "STA_CODE", length = 50)
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.StockTransApplicationType")})
    public StockTransApplicationType getType() {
        return type;
    }

    public void setType(StockTransApplicationType type) {
        this.type = type;
    }

    public void setPlanArriveTime(Date planArriveTime) {
        this.planArriveTime = planArriveTime;
    }

    @Column(name = "PLAN_ARRIVE_TIME")
    public Date getPlanArriveTime() {
        return planArriveTime;
    }

    @Column(name = "TOTAL_ACTUAL")
    public BigDecimal getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(BigDecimal totalActual) {
        this.totalActual = totalActual;
    }

    @Column(name = "REMARK", length = 1000)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "SLIP_CODE", length = 100)
    public String getRefSlipCode() {
        return refSlipCode;
    }

    public void setRefSlipCode(String refSlipCode) {
        this.refSlipCode = refSlipCode;
    }

    @Column(name = "OWNER", length = 100)
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "TELEPHONE", length = 50)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "MOBILE", length = 50)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "LPCODE", length = 20)
    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    @Column(name = "TRACKING_NO", length = 100)
    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    @Column(name = "RECEIVER", length = 50)
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Column(name = "BATCH_ID")
    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "wlb_code", length = 30)
    public String getWlbCode() {
        return wlbCode;
    }

    public void setWlbCode(String wlbCode) {
        this.wlbCode = wlbCode;
    }

    @Column(name = "BATCH_CODE", length = 50)
    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    @Column(name = "SHOP_ID")
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Column(name = "IS_MAIL")
    public Long getIsMail() {
        return isMail;
    }

    public void setIsMail(Long isMail) {
        this.isMail = isMail;
    }

    @Column(name = "ERROR_COUNT")
    public Long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }

    @Column(name = "EXT_MEMO")
    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    @Column(name = "UUID")
    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }
}
