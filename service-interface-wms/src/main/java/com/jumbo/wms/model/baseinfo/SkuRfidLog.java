package com.jumbo.wms.model.baseinfo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.warehouse.TransactionDirection;

@Entity
@Table(name = "T_WH_SKU_RFID_LOG")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class SkuRfidLog extends BaseModel{

    /**
     * 
     */
    private static final long serialVersionUID = 2536313379624839613L;

    /**
     * PK
     */
    private Long id;

    /**
     * RFID编码
     */
    private String rfidCode;

    /**
     * 事务方向
     */
    private TransactionDirection direction;

    /**
     * 商品ID
     */
    private Long skuId;

    /**
     * 作业单ID
     */
    private Long staId;

    /**
     * 仓库ID
     */
    private Long ouId;

    /**
     * 事务时间
     */
    private Date transactionTime;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "S_T_WH_SKU_RFID_LOG", sequenceName = "S_T_WH_SKU_RFID_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_T_WH_SKU_RFID_LOG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "RFID_CODE")
    public String getRfidCode() {
        return rfidCode;
    }

    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }

    @Column(name = "DIRECTION", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransactionDirection")})
    public TransactionDirection getDirection() {
        return direction;
    }

    public void setDirection(TransactionDirection direction) {
        this.direction = direction;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "OU_ID")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    @Column(name = "TRANSACTION_TIME")
    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }



}
