package com.jumbo.wms.model.warehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * SF电子面单 快件产品类别配置表
 * 
 * @author jinlong.ke
 * 
 */
@Entity
@Table(name = "T_BI_SF_EXPRESSTYPE")
public class SfExpressTypeConfig extends BaseModel {


    /**
     * 
     */
    private static final long serialVersionUID = -7787029281871731435L;
    /**
     * PK
     */
    private Integer id;
    /**
     * 宝尊自定义时效类型
     */
    private Integer transTimeType;
    /**
     * 宝尊自定义业务类型
     */
    private Integer transDeliveryType;
    /**
     * 宝尊定义物流商编码
     */
    private String lpCode;
    /**
     * SF接口设定的快件产品类型<br/>
     * 1:顺丰次日 2：顺丰隔日 5：顺丰次晨 6：顺丰即日 37：云仓次日 38：云仓隔日
     */
    private Integer expressType;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SF_EXPRESS_TYPE", sequenceName = "S_T_BI_SF_EXPRESSTYPE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SF_EXPRESS_TYPE")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "TRANS_TIME_TYPE")
    public Integer getTransTimeType() {
        return transTimeType;
    }

    public void setTransTimeType(Integer transTimeType) {
        this.transTimeType = transTimeType;
    }

    @Column(name = "TRANS_DELIVERY_TYPE")
    public Integer getTransDeliveryType() {
        return transDeliveryType;
    }

    public void setTransDeliveryType(Integer transDeliveryType) {
        this.transDeliveryType = transDeliveryType;
    }

    @Column(name = "LPCODE")
    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    @Column(name = "EXPRESS_TYPE")
    public Integer getExpressType() {
        return expressType;
    }

    public void setExpressType(Integer expressType) {
        this.expressType = expressType;
    }

}
