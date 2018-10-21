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

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_WH_STA_PAYMENT")
public class StaPayment extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 3316515392902169982L;
    /*
     * PK
     */
    private Long id;
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
    /*
     * 关联单据
     */
    private StockTransApplication sta;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_STA_PAYMENT", sequenceName = "S_T_WH_STA_PAYMENT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_STA_PAYMENT")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Column(name = "MEMO", length = 100)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

}
