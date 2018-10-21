package com.jumbo.wms.model.archive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;
/**
 * 物流信息
 * 
 * @author cheng.su
 * 
 */
@Entity
@Table(name = "t_wh_trans_info_OMS_ARCHIVE")
public class WmsTransInfoOmsArchive extends BaseModel {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 8913784307352153628L;
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
    private Long orderStatusOms;

    @Id
    @Column(name = "ID")
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

    @Column(name = "orderstatus")
    public Long getOrderStatusOms() {
        return orderStatusOms;
    }

    public void setOrderStatusOms(Long orderStatusOms) {
        this.orderStatusOms = orderStatusOms;
    }


}
