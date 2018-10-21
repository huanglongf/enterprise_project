package com.jumbo.wms.model.hub2wms;

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

import org.hibernate.annotations.Index;

import com.jumbo.wms.model.BaseModel;

/**
 * 物流信息
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "t_wh_trans_info_OMS")
public class WmsTransInfoOms extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -4259904071968730655L;
    /**
     * ID
     */
    private Long id;
    /**
     * 物流商
     */
    private String transCode;
    /**
     * 运单号
     */
    private String transNo;
    /**
     * 时效
     */
    private int transTimeType;
    /**
     * 出入库状态
     */
    private WmsOrderStatusOms orderStatusOms;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_TRANS_INFO_OMS", sequenceName = "S_t_wh_trans_info_OMS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_TRANS_INFO_OMS")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "transCode")
    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    @Column(name = "transNo")
    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    @Column(name = "transTimeType")
    public int getTransTimeType() {
        return transTimeType;
    }

    public void setTransTimeType(int transTimeType) {
        this.transTimeType = transTimeType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderstatus")
    @Index(name = "IDX_ORDER_STATUS")
    public WmsOrderStatusOms getOrderStatusOms() {
        return orderStatusOms;
    }

    public void setOrderStatusOms(WmsOrderStatusOms orderStatusOms) {
        this.orderStatusOms = orderStatusOms;
    }


}
