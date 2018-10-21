package com.jumbo.wms.model.pingan;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.jumbo.wms.model.BaseModel;


/**
 * 平安投保表
 * 
 */
@Entity
@Table(name = "T_WH_PINGAN_COVER")
public class WhPingAnCover extends BaseModel {


    /**
     * 
     */
    private static final long serialVersionUID = 8216402371519148891L;

    // 货物大类
    public static final String cargoBigTypeX = "12";
    public static final String cargoBigTypeY = "11";
    // 货物细类
    public static final String cargoDetailTypeX = "6603";
    public static final String cargoDetailTypeY = "5407";

    private Long id;

    /**
     * WMS单据编码
     */
    private String staCode;

    /**
     * 交易流水号 运单号
     */
    private String transSerialNo;

    /**
     * 商品清单明细 条码 货号 商品名称 数量
     */
    private String cargoInfo;

    /**
     * 货物价值
     */
    private BigDecimal merit;

    /**
     * 快递单号
     */
    private String conveyanceNo;

    /**
     * 物流商编码
     */
    private String expressCode;

    /**
     * 起运日期
     */
    private Date consignorDate;

    /**
     * 签单日期
     */
    private Date signingOddDate;

    /**
     * 货物大类
     */
    private String cargoBigType;

    /**
     * 货物细类
     */
    private String cargoDetailType;

    /**
     * 货物的描述
     */
    private String cargoRiskDepict;

    /**
     * 状态 1:未投保 2:投保成功 3:投保失败
     */
    private Integer status = 1;

    /**
     * 投保失败编码
     */
    private String coverErrorCode;

    /**
     * 投保失败次数
     */
    private Integer coverErrorNumber = 0;

    /**
     * 仓库ID
     */
    private Long ouId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 版本
     */
    private int version = 0;
    
    /**
     * 保单号
     */
   private  String coverNo;
   
   /**
    * 投保金额
    */
   private String coverMoney;


    @Id
    @SequenceGenerator(name = "SEQ_WH_PINGAN_COVER", sequenceName = "S_T_WH_PINGAN_COVER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_PINGAN_COVER")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_CODE")
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "TRANS_SERIAL_NO")
    public String getTransSerialNo() {
        return transSerialNo;
    }

    public void setTransSerialNo(String transSerialNo) {
        this.transSerialNo = transSerialNo;
    }

    @Column(name = "CARGO_INFO")
    public String getCargoInfo() {
        return cargoInfo;
    }

    public void setCargoInfo(String cargoInfo) {
        this.cargoInfo = cargoInfo;
    }

    @Column(name = "CONVEYANCE_NO")
    public String getConveyanceNo() {
        return conveyanceNo;
    }

    public void setConveyanceNo(String conveyanceNo) {
        this.conveyanceNo = conveyanceNo;
    }

    @Column(name = "CONSIGNOR_DATE")
    public Date getConsignorDate() {
        return consignorDate;
    }

    public void setConsignorDate(Date consignorDate) {
        this.consignorDate = consignorDate;
    }

    @Column(name = "SIGNING_ODD_DATE")
    public Date getSigningOddDate() {
        return signingOddDate;
    }

    public void setSigningOddDate(Date signingOddDate) {
        this.signingOddDate = signingOddDate;
    }

    @Column(name = "CARGO_BIG_TYPE")
    public String getCargoBigType() {
        return cargoBigType;
    }

    public void setCargoBigType(String cargoBigType) {
        this.cargoBigType = cargoBigType;
    }

    @Column(name = "CARGO_DETAIL_TYPE")
    public String getCargoDetailType() {
        return cargoDetailType;
    }

    public void setCargoDetailType(String cargoDetailType) {
        this.cargoDetailType = cargoDetailType;
    }

    @Column(name = "CARGO_RISK_DEPICT")
    public String getCargoRiskDepict() {
        return cargoRiskDepict;
    }

    public void setCargoRiskDepict(String cargoRiskDepict) {
        this.cargoRiskDepict = cargoRiskDepict;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "COVER_ERROR_CODE")
    public String getCoverErrorCode() {
        return coverErrorCode;
    }

    public void setCoverErrorCode(String coverErrorCode) {
        this.coverErrorCode = coverErrorCode;
    }

    @Column(name = "COVER_ERROR_NUMBER")
    public Integer getCoverErrorNumber() {
        return coverErrorNumber;
    }

    public void setCoverErrorNumber(Integer coverErrorNumber) {
        this.coverErrorNumber = coverErrorNumber;
    }

    @Column(name = "MAIN_WH_ID")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "MERIT")
    public BigDecimal getMerit() {
        return merit;
    }

    public void setMerit(BigDecimal merit) {
        this.merit = merit;
    }

    @Column(name = "EXPRESS_CODE")
    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    @Column(name = "COVER_NO")
    public String getCoverNo() {
        return coverNo;
    }

    public void setCoverNo(String coverNo) {
        this.coverNo = coverNo;
    }

    @Column(name = "COVER_MONEY")
    public String getCoverMoney() {
        return coverMoney;
    }

    public void setCoverMoney(String coverMoney) {
        this.coverMoney = coverMoney;
    }

}
