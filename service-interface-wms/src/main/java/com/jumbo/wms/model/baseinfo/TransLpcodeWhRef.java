package com.jumbo.wms.model.baseinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

@Entity
@Table(name="T_MA_TRANS_WH_REF")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class TransLpcodeWhRef {
    /**
     * PK id
     */
    private Long id;
    /**
     * WMS系统LPcode,物流商Exp_code
     */
    private String lpCode;
    /**
     * 对接的外包仓
     */
    private String source;
    /**
     * 物流服务商
     */
    private String carrier;
    /**
     * 承运商服务类型
     */
    private String carrierService;
    /**
     * 版本
     */
    private int version;
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_MA_TRANS_WH_REF", sequenceName = "S_T_MA_TRANS_WH_REF", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_MA_TRANS_WH_REF")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Column(name="LP_CODE")
    public String getLpCode() {
        return lpCode;
    }
    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }
    @Column(name="SOURCE")
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    @Column(name="CARRIER")
    public String getCarrier() {
        return carrier;
    }
    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }
    @Column(name="CARRIER_SERVICE")
    public String getCarrierService() {
        return carrierService;
    }
    public void setCarrierService(String carrierService) {
        this.carrierService = carrierService;
    }
    @Version
    @Column(name="VERSION")
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }    
}
