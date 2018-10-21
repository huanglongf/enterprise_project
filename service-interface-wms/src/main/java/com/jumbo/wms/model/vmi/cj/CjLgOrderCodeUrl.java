package com.jumbo.wms.model.vmi.cj;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_WH_CJ_LGORDERCODE_URL")
public class CjLgOrderCodeUrl extends BaseModel {

    private static final long serialVersionUID = 6661124497750764113L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * staId
     */
    private Long staId;

    /**
     * 交易订单id
     */
    private String tradeOrderId;

    /**
     * 物流资源ID
     */
    private String resourceId;

    /**
     * 仓库编码
     */
    private String storeCode;

    /**
     * 第1段物流承运商
     */
    private String firstLogistics;

    /**
     * 第1段物流运单号
     */
    private String firstWaybillNo;

    /**
     * [菜鸟集货]获取菜鸟的物流订单编号
     */
    private String lgorderCode;

    /**
     * [菜鸟集货]菜鸟返回的电子面单链接地址
     */
    private String waybillurl;

    /**
     * 错误次数
     */
    private Integer errorCount;

    /**
     * 错误原因
     */
    private String errorMsg;

    /**
     * lgorderCode获取状态：1获取成功，0未获取，-1获取失败，-2系统异常
     */
    private Integer status1;
    /**
     * 快递面单url获取状态： 1获取成功，0未获取，-1获取失败，-2系统异常
     */
    private Integer status2;


    @Id
    @SequenceGenerator(name = "T_WH_CJ_LGORDERCODE_URL_NAME", sequenceName = "S_T_WH_CJ_LGORDERCODE_URL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "T_WH_CJ_LGORDERCODE_URL_NAME")
    @Column(name = "ID", unique = true, nullable = false, scale = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_ID")
    @Index(name = "INDEX_CJ_STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "TRADE_ORDER_ID")
    public String getTradeOrderId() {
        return tradeOrderId;
    }

    public void setTradeOrderId(String tradeOrderId) {
        this.tradeOrderId = tradeOrderId;
    }

    @Column(name = "RESOURCE_ID")
    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @Column(name = "STORE_CODE")
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Column(name = "FIRST_LOGISTICS")
    public String getFirstLogistics() {
        return firstLogistics;
    }

    public void setFirstLogistics(String firstLogistics) {
        this.firstLogistics = firstLogistics;
    }

    @Column(name = "FIRST_WAYBILL_NO")
    public String getFirstWaybillNo() {
        return firstWaybillNo;
    }

    public void setFirstWaybillNo(String firstWaybillNo) {
        this.firstWaybillNo = firstWaybillNo;
    }

    @Column(name = "LGORDER_CODE")
    public String getLgorderCode() {
        return lgorderCode;
    }

    public void setLgorderCode(String lgorderCode) {
        this.lgorderCode = lgorderCode;
    }

    @Column(name = "WAYBILLURL")
    public String getWaybillurl() {
        return waybillurl;
    }

    public void setWaybillurl(String waybillurl) {
        this.waybillurl = waybillurl;
    }

    @Column(name = "ERROR_COUNT")
    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    @Column(name = "ERROR_MSG")
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Column(name = "STATUS1")
    public Integer getStatus1() {
        return status1;
    }

    public void setStatus1(Integer status1) {
        this.status1 = status1;
    }

    @Column(name = "STATUS2")
    public Integer getStatus2() {
        return status2;
    }

    public void setStatus2(Integer status2) {
        this.status2 = status2;
    }

}
