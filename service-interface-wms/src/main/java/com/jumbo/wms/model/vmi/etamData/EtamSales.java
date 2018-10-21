package com.jumbo.wms.model.vmi.etamData;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_ETAM_SALES")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class EtamSales extends BaseModel {

    private static final long serialVersionUID = 1886748693308476590L;

    public final static Integer STATUS_NEW = 0;// 未发送
    public final static Integer STATUS_SENT = 1;// 发送成功
    public final static Integer STATUS_DOING = 5;// 正在同步发送
    public final static Integer STATUS_ERROR = 101;// 异常情况，失败
    private Long id;
    private Long soId;
    private String orderNo;
    private String skuNo;
    private String sSize;
    private String color;
    private Long qty;
    private Long shopId;
    private String sysType;
    private String vipId;
    private String oriOrderNo;
    private String batchId;
    private BigDecimal totalActual;
    private BigDecimal netRetail;
    private Integer status;
    private String msg;

    private Date storeCreateTime;
    private Date version;

    /**
     * 创建时间
     * 
     * @return
     */
    private Date createTime;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_ETAM_SALES", sequenceName = "S_T_ETAM_SALES", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_ETAM_SALES")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SO_ID")
    public Long getSoId() {
        return soId;
    }

    public void setSoId(Long soId) {
        this.soId = soId;
    }

    @Column(name = "ORDER_NO", length = 100)
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Column(name = "SKU_CODE", length = 100)
    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    @Column(name = "SSIZE", length = 20)
    public String getsSize() {
        return sSize;
    }

    public void setsSize(String sSize) {
        this.sSize = sSize;
    }

    @Column(name = "COLOR", length = 20)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @Column(name = "SHOP_ID")
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Column(name = "SYS_TYPE", length = 2)
    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    @Column(name = "VIP_ID", length = 20)
    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }

    @Column(name = "ORI_ORDER_NO", length = 100)
    public String getOriOrderNo() {
        return oriOrderNo;
    }

    public void setOriOrderNo(String oriOrderNo) {
        this.oriOrderNo = oriOrderNo;
    }

    @Column(name = "BATCH_ID", length = 25)
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @Column(name = "TOTAL_ACTUAL")
    public BigDecimal getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(BigDecimal totalActual) {
        this.totalActual = totalActual;
    }

    @Column(name = "NET_RETAIL")
    public BigDecimal getNetRetail() {
        return netRetail;
    }

    public void setNetRetail(BigDecimal netRetail) {
        this.netRetail = netRetail;
    }

    @Column(name = "STATUS", length = 3)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "MSG", length = 100)
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Column(name = "STORE_CREATE_TIME")
    public Date getStoreCreateTime() {
        return storeCreateTime;
    }

    public void setStoreCreateTime(Date storeCreateTime) {
        this.storeCreateTime = storeCreateTime;
    }

    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
