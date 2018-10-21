package com.jumbo.wms.model.vmi.warehouse;

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

/**
 * 外包仓订单取消信息表
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_WH_MSG_OUTBOUND_ORDER_CANCEL")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgOutboundOrderCancel extends BaseModel {

    private static final long serialVersionUID = 5657109001375119148L;
    /**
     * PK
     */
    private Long id;
    /**
     * VERSION
     */
    private int version;
    /**
     * 批次号
     */
    private Long batchId;
    /**
     * 作业单号
     */
    private String staCode = "";
    /**
     * 作业单ID(不使用，待删除)
     */
    private Long staId;
    /**
     * 状态
     */
    private MsgOutboundOrderCancelStatus status;
    /**
     * 是否已经取消
     */
    private Boolean isCanceled;
    /**
     * 反馈消息
     */
    private String msg;
    /**
     * 来源
     */
    private String source = "";
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    private String result;
    /**
     * 执行次数
     */
    private Long errorCount;
    /**
     * 是否已经邮件通知
     */
    private Integer isMail;

    /**
     * 外包仓数据唯一标识
     */
    private Long uuid;
    /**
     * 推送标识
     */
    private Integer isKey;
    /**
     * 系统编码
     */
    private String systemKey;

    /**
     * 是否发送结果表
     */
    // private Integer isSend;


    private String silpCode1;

    private String warehouseCode;

    /**
     * 运输公司
     */
    private String transCode;

    /**
     * 运输单号
     */
    private String transNo;



    @Column(name = "silp_code1")
    public String getSilpCode1() {
        return silpCode1;
    }

    public void setSilpCode1(String silpCode1) {
        this.silpCode1 = silpCode1;
    }

    @Column(name = "Warehouse_Code")
    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    @Column(name = "trans_Code")
    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    @Column(name = "trans_No")
    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    @Column(name = "SOURCE", length = 30)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
    @SequenceGenerator(name = "SEQ_T_WH_MSG_OUTBOUND_ORDER_CAN", sequenceName = "S_T_WH_MSG_OUTBOUND_ORDER_CAN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_MSG_OUTBOUND_ORDER_CAN")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_CODE", length = 50)
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus")})
    public MsgOutboundOrderCancelStatus getStatus() {
        return status;
    }

    public void setStatus(MsgOutboundOrderCancelStatus status) {
        this.status = status;
    }

    @Column(name = "STA_ID", length = 19)
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "MSG", length = 30)
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Column(name = "BATCH_ID")
    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
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

    @Column(name = "IS_CANCELED")
    public Boolean getIsCanceled() {
        return isCanceled;
    }

    public void setIsCanceled(Boolean isCanceled) {
        this.isCanceled = isCanceled;
    }

    @Column(name = "RESULT", length = 50)
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Column(name = "error_count")
    public Long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }

    @Column(name = "is_mail")
    public Integer getIsMail() {
        return isMail;
    }

    public void setIsMail(Integer isMail) {
        this.isMail = isMail;
    }

    @Column(name = "UUID")
    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    @Column(name = "iskey")
    public Integer getIsKey() {
        return isKey;
    }

    public void setIsKey(Integer isKey) {
        this.isKey = isKey;
    }

    @Column(name = "systemkey")
    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }


    // public Integer getIsSend() {
    // return isSend;
    // }
    //
    // public void setIsSend(Integer isSend) {
    // this.isSend = isSend;
    // }


}
