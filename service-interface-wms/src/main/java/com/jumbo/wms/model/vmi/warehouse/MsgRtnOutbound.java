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

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;

@Entity
@Table(name = "T_WH_MSG_RTN_OUTBOUND")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgRtnOutbound extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 9106816894038248908L;
    /**
     * PK
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date createTime = new Date();
    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * Sta 编码
     */
    private String staCode;
    /**
     * 运单号
     */
    private String trackingNo;
    /**
     * 出库时间
     */
    private Date outboundTime;
    /**
     * 快递简称
     */
    private String lpCode;
    /**
     * 物流商名称
     */
    private String trackName;
    /**
     * 来源
     */
    private String source;
    /**
     * 来源仓库
     */
    private String sourceWh;
    /**
     * 重量
     */
    private BigDecimal weight;
    /**
     * 状态
     */
    private DefaultStatus status;

    private String type;

    /**
     * 仓库支付的运费
     */
    public BigDecimal transFeeCost;
    /**
     * 仓库说明
     */
    public String remark;
    /**
     * 备用字段1
     */
    private String field1;
    /**
     * 淘宝订单号
     */
    private String tid;
    /**
     * 物流宝单据号
     */
    private String wlbCode;
    /**
     * 相关单据号 一般是指 oms 单号
     */
    private String slipCode;

    private int version;

    /**
     * 包材规格
     */
    private String ctnType;
    /**
     * SF出库单号
     */
    private String sfCode;
    /**
     * 扩展字段，json格式
     */
    private String extMemo;

    /**
     * 是否发送邮件
     */
    private Long isMail;

    /**
     * 错误次数
     */
    private Long errorCount;

    /**
     * 是否是需要SN号作业单
     */
    private Integer isSn;

    /**
     * 是否已经发送通知邮件(菜鸟仓sn发货查询) 0:否 1:是 2:数据被成功处理
     */
    private Integer isSend;


    /**
     * is_mq
     * @return
     */
    private String isMq;// 0和空 非mq 1：mq


    /**
     * mq count 预警次数
     * 
     * @return
     */
    private Long mqErrorCount;
    /**
     * mq_status
     * 
     * @return
     */
    private String mqStatus; // 1:未推送 10:已推送

    
    private Date nextTime;


    @Column(name = "next_time")
    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

    @Column(name = "IS_SEND")
    public Integer getIsSend() {
        return isSend;
    }



    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    @Column(name = "IS_SN")
    public Integer getIsSn() {
        return isSn;
    }

    public void setIsSn(Integer isSn) {
        this.isSn = isSn;
    }

    @Column(name = "TRANS_FEE_COST", precision = 10, scale = 4)
    public BigDecimal getTransFeeCost() {
        return transFeeCost;
    }

    public void setTransFeeCost(BigDecimal transFeeCost) {
        this.transFeeCost = transFeeCost;
    }

    @Column(name = "REMARK", length = 100)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "FIELD1", length = 100)
    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_MSGO", sequenceName = "S_T_WH_MSG_RTN_OUTBOUND", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MSGO")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_CODE", length = 100)
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "TRACKING_NO", length = 100)
    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    @Column(name = "OUTBOUND_TIME")
    public Date getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }

    @Column(name = "LP_CODE", length = 100)
    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    @Column(name = "TRACK_NAME")
    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    @Column(name = "SOURCE", length = 30)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "SOURCE_WH", length = 30)
    public String getSourceWh() {
        return sourceWh;
    }

    public void setSourceWh(String sourceWh) {
        this.sourceWh = sourceWh;
    }

    @Column(name = "WEIGHT")
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "TYPE", length = 30)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "TID", length = 30)
    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    @Column(name = "WLB_CODE", length = 30)
    public String getWlbCode() {
        return wlbCode;
    }

    public void setWlbCode(String wlbCode) {
        this.wlbCode = wlbCode;
    }

    @Column(name = "SLIP_CODE", length = 30)
    @Index(name = "IDX_MSG_RTN_OUTBOUND_SLIP_CODE")
    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    @Column(name = "CTN_TYPE", length = 50)
    public String getCtnType() {
        return ctnType;
    }

    public void setCtnType(String ctnType) {
        this.ctnType = ctnType;
    }

    @Column(name = "SF_CODE", length = 100)
    public String getSfCode() {
        return sfCode;
    }

    public void setSfCode(String sfCode) {
        this.sfCode = sfCode;
    }

    @Column(name = "EXT_MEMO")
    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
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

    @Column(name = "IS_MQ")
    public String getIsMq() {
        return isMq;
    }

    public void setIsMq(String isMq) {
        this.isMq = isMq;
    }

    @Column(name = "MQ_ERROR_COUNT")
    public Long getMqErrorCount() {
        return mqErrorCount;
    }

    public void setMqErrorCount(Long mqErrorCount) {
        this.mqErrorCount = mqErrorCount;
    }


    @Column(name = "MQ_STATUS")
    public String getMqStatus() {
        return mqStatus;
    }

    public void setMqStatus(String mqStatus) {
        this.mqStatus = mqStatus;
    }
}
