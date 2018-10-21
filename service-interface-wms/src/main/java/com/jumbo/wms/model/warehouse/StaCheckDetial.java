package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * 
 * @author yang.wang
 * @date 2018年7月10日 下午2:21:15
 *
 */
@Entity
@Table(name = "T_WH_STA_CHECK_DETIAL")
public class StaCheckDetial extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -8735049578731756682L;

    private Long id;

    private Long skuId;

    private String sn;

    /**
     * 过期时间
     */
    private Date expDate;

    /**
     * RFID数据
     */
    private String rfid;

    /**
     * logTime
     */
    private Date logDate;

    /**
     * 数量
     */
    private Long qty;


    private Long staId;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(allocationSize = 1, name = "seq_t_wh_sta_check_detial", sequenceName = "seq_t_wh_sta_check_detial")
    @GeneratedValue(generator = "seq_t_wh_sta_check_detial", strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }


    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }


    @Column(name = "SN")
    public String getSn() {
        return sn;
    }


    public void setSn(String sn) {
        this.sn = sn;
    }


    @Column(name = "EXP_DATE")
    public Date getExpDate() {
        return expDate;
    }


    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }


    @Column(name = "RFID")
    public String getRfid() {
        return rfid;
    }


    public void setRfid(String rfid) {
        this.rfid = rfid;
    }


    @Column(name = "LOG_DATE")
    public Date getLogDate() {
        return logDate;
    }


    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }


    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }


    public void setQty(Long qty) {
        this.qty = qty;
    }


    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }


    public void setStaId(Long staId) {
        this.staId = staId;
    }

}
