package com.jumbo.wms.model.baseinfo;

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

@Entity
@Table(name = "T_WH_SKU_DECLARATION")
public class SkuDeclaration extends BaseModel{

    /**
     * 
     */
    private static final long serialVersionUID = 4070545229043741261L;
    /**
     * PK
     */
    private Long id;

    /**
     * 报关商品唯一标识
     */
    private String code;
    /**
     * 商品编码
     */
    private String skuCode;
    /**
     * UPC
     */
    private String upc;
    /**
     * HSCODE
     */
    private String hsCode;
    /**
     * 商品名称
     */
    private String skuName;
    /**
     * 商品描述
     */
    private String skuDescribe;
    /**
     * 款式
     */
    private String style;
    /**
     * 颜色
     */
    private String color;
    /**
     * 尺码
     */
    private String skuSize;
    /**
     * 是否打折 0:否 1:是
     */
    private int isDiscount;
    /**
     * 净重
     */
    private BigDecimal netWt;
    /**
     * 单价
     */
    private BigDecimal declPrice;
    /**
     * 品牌
     */
    private String owner;
    /**
     * 申报单位
     */
    private String gUnit;
    /**
     * 备注
     */
    private String memo;
    /**
     * 是否需要推送 0:否 1:是
     */
    private int isPush;
    /**
     * 状态 1:未同步 2:同步成功 3:同步失败
     */
    private int status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 推送时间
     */
    private Date pushTime;
    /**
     * 版本
     */
    private int version;
    /**
     * 款色码
     */
    private String ksm;

    private Integer errorCount;

    private String errorMsg;

    private Integer declarationStatus;

    private Integer ciqStatus;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "S_T_WH_SKU_DECLARATION", sequenceName = "S_T_WH_SKU_DECLARATION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_T_WH_SKU_DECLARATION")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "SKU_CODE")
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "UPC")
    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    @Column(name = "HS_CODE")
    public String getHsCode() {
        return hsCode;
    }

    public void setHsCode(String hsCode) {
        this.hsCode = hsCode;
    }

    @Column(name = "SKU_NAME")
    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    @Column(name = "SKU_DESCRIBE")
    public String getSkuDescribe() {
        return skuDescribe;
    }

    public void setSkuDescribe(String skuDescribe) {
        this.skuDescribe = skuDescribe;
    }

    @Column(name = "STYLE")
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Column(name = "COLOR")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "SKU_SIZE")
    public String getSkuSize() {
        return skuSize;
    }

    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }

    @Column(name = "IS_DISCOUNT")
    public int getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(int isDiscount) {
        this.isDiscount = isDiscount;
    }

    @Column(name = "NET_WT")
    public BigDecimal getNetWt() {
        return netWt;
    }

    public void setNetWt(BigDecimal netWt) {
        this.netWt = netWt;
    }

    @Column(name = "DECL_PRICE")
    public BigDecimal getDeclPrice() {
        return declPrice;
    }

    public void setDeclPrice(BigDecimal declPrice) {
        this.declPrice = declPrice;
    }

    @Column(name = "OWNER")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "G_UNIT")
    public String getgUnit() {
        return gUnit;
    }

    public void setgUnit(String gUnit) {
        this.gUnit = gUnit;
    }

    @Column(name = "MEMO")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "IS_PUSH")
    public int getIsPush() {
        return isPush;
    }

    public void setIsPush(int isPush) {
        this.isPush = isPush;
    }

    @Column(name = "STATUS")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public String getKsm() {
        return ksm;
    }

    public void setKsm(String ksm) {
        this.ksm = ksm;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "PUSH_TIME")
    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "ERROR_MSG")
    public String getErrorMsg() {
        return errorMsg;
    }



    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Column(name = "ERROR_COUNT")
    public Integer getErrorCount() {
        return errorCount;
    }



    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    @Column(name = "DECLARATION_STATUS")
    public Integer getDeclarationStatus() {
        return declarationStatus;
    }

    public void setDeclarationStatus(Integer declarationStatus) {
        this.declarationStatus = declarationStatus;
    }

    @Column(name = "CIQ_STATUS")
    public Integer getCiqStatus() {
        return ciqStatus;
    }

    public void setCiqStatus(Integer ciqStatus) {
        this.ciqStatus = ciqStatus;
    }


}
