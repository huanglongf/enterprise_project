package com.jumbo.wms.model.automaticEquipment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.warehouse.TransactionDirection;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_CONTAINER_LOG")
public class WhContainerLog extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 6873278428248371711L;
    /*
     * PK 主键
     */
    private Long id;
    /*
     * 周转箱编码
     */
    private String code;
    /*
     * 周转箱目前状态 1:可用 5:占用
     */
    private DefaultStatus status;
    /*
     * 日志时间
     */
    private Date createTime;
    /*
     * 相关单据编码
     */
    private String orderCode;
    /*
     * 出入类型
     */
    private TransactionDirection type;
    /*
     * 配货批
     */
    private Long pId;
    /*
     * 二级批次
     */
    private Long p2Id;
    /*
     * 操作人
     */
    private Long userId;
    /*
     * 作业单ID，入库使用
     */
    private Long staId;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_CONTAINER_LOG", sequenceName = "S_T_WH_CONTAINER_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_CONTAINER_LOG")
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

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "ORDER_CODE", length = 50)
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransactionDirection")})
    public TransactionDirection getType() {
        return type;
    }

    public void setType(TransactionDirection type) {
        this.type = type;
    }

    @Column(name = "P_ID")
    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    @Column(name = "P2_ID")
    public Long getP2Id() {
        return p2Id;
    }

    public void setP2Id(Long p2Id) {
        this.p2Id = p2Id;
    }

    @Column(name = "USER_ID")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }
}
