package com.jumbo.wms.model.warehouse;

import java.math.BigDecimal;

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

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
@Entity
@Table(name = "t_wh_q_sta_payment")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class QueueStaPayment extends BaseModel {


    private static final long serialVersionUID = 2892086858729001977L;
    /**
     * PK
     */
    private Long id;
    /*
     * 作业单ID
     */
	private QueueSta qStaId;

    /*
     * 金额
     */
    private BigDecimal amt;
    /*
     * 类型
     */
    private PaymentType type;
    /*
     * 支付说明
     */
    private String memo;



    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WHL", sequenceName = "S_t_wh_q_sta_payment", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WHL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Q_STA_ID")
    public QueueSta getqStaId() {
        return qStaId;
    }

    public void setqStaId(QueueSta qStaId) {
        this.qStaId = qStaId;
    }

    @Column(name = "AMT")
    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.PaymentType")})
    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

	

}
