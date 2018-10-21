package com.jumbo.wms.model.hub2wms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus;

/**
 * 订单取消结果
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "T_WH_MSG_ORDER_CANCEL")
public class MsgOrderCancel extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -8869479195059677522L;

    /**
     * PK
     */
    private Long id;
    /**
     * VERSION
     */
    private int version;

    /**
     * 作业单号
     */
    private String staCode = "";

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
     * 
     */
    private String systemKey;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_MSG_ORDER_CAN", sequenceName = "S_T_WH_MSG_ORDER_CANCEL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_MSG_ORDER_CAN")
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

    @Column(name = "IS_CANCELED")
    public Boolean getIsCanceled() {
        return isCanceled;
    }

    public void setIsCanceled(Boolean isCanceled) {
        this.isCanceled = isCanceled;
    }

    @Column(name = "MSG", length = 30)
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Column(name = "SOURCE", length = 30)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    @Column(name = "RESULT", length = 50)
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Column(name = "systemKey", length = 50)
    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }



}
