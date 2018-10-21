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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Index;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * 顺丰单据取消
 * 
 * @author sjk
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "tporderUpdate")
@Entity
@Table(name = "T_SF_CANCEL_ORDER_QUEUE")
public class SfOrderCancelQueue extends BaseModel {
    private static final long serialVersionUID = -313337721675508574L;
    /**
     * PK
     */
    private Long id;
    /**
     * 次数
     */
    private Long count;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 订单号
     */
    @XmlElement(required = true, name = "orderid")
    private String orderId;

    @XmlElement(required = true, name = "custid")
    private String custid;

    @XmlElement(required = true, name = "sendtype")
    private String sendtype = "3";

    @XmlElement(required = true, name = "checkword")
    private String checkword;

    private String staCode;

    private OperationUnit cmpOu;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SFCQQ", sequenceName = "S_T_SF_CANCEL_ORDER_QUEUE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SFCQQ")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ORDER_ID", length = 50)
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    @Column(name = "SEND_TYPE", length = 5)
    public String getSendtype() {
        return sendtype;
    }

    public void setSendtype(String sendtype) {
        this.sendtype = sendtype;
    }

    public String getCheckword() {
        return checkword;
    }

    public void setCheckword(String checkword) {
        this.checkword = checkword;
    }

    @Column(name = "COUNT")
    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "STA_CODE", length = 50)
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CMP_OU_ID")
    @Index(name = "IDX_SF_CQ_CMP_OU_ID")
    public OperationUnit getCmpOu() {
        return cmpOu;
    }

    public void setCmpOu(OperationUnit cmpOu) {
        this.cmpOu = cmpOu;
    }
}
