package com.jumbo.wms.model.vmi.ckData;

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
@Table(name = "T_CK_PRODUCT_DATA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class CKProductData extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8404838817893134334L;

    private Long id;

    private String dataType;

    private String skuCode;

    private String description;

    private String prodCode;

    private String shortDesc;

    private String colouCode;

    private String colDescZH;

    private String colourDesc;

    private String sizesCode;

    private String SIZESDESC;

    private Double originalSp;

    private String level1Desc;

    private String level2Desc;

    private String level3Desc;

    private String level4Desc;

    private Date createTime;

    private Integer status;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CK_PRODUCT_DATA", sequenceName = "S_T_CK_PRODUCT_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CK_PRODUCT_DATA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "DATA_TYPE", length = 10)
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Column(name = "SKU_CODE", length = 50)
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "PROD_CODE", length = 24)
    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    @Column(name = "SHORT_DESC", length = 40)
    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    @Column(name = "COLOUR_CODE", length = 20)
    public String getColouCode() {
        return colouCode;
    }

    public void setColouCode(String colouCode) {
        this.colouCode = colouCode;
    }

    @Column(name = "COL_DESC_ZH", length = 200)
    public String getColDescZH() {
        return colDescZH;
    }

    public void setColDescZH(String colDescZH) {
        this.colDescZH = colDescZH;
    }

    @Column(name = "COLOUR_DESC", length = 100)
    public String getColourDesc() {
        return colourDesc;
    }

    public void setColourDesc(String colourDesc) {
        this.colourDesc = colourDesc;
    }

    @Column(name = "SIZES_CODE", length = 20)
    public String getSizesCode() {
        return sizesCode;
    }

    public void setSizesCode(String sizesCode) {
        this.sizesCode = sizesCode;
    }

    @Column(name = "SIZES_DESC", length = 100)
    public String getSIZESDESC() {
        return SIZESDESC;
    }

    public void setSIZESDESC(String sIZESDESC) {
        SIZESDESC = sIZESDESC;
    }

    @Column(name = "ORIGINAL_SP")
    public Double getOriginalSp() {
        return originalSp;
    }

    public void setOriginalSp(Double originalSp) {
        this.originalSp = originalSp;
    }

    @Column(name = "LEVEL1_DESC", length = 50)
    public String getLevel1Desc() {
        return level1Desc;
    }

    public void setLevel1Desc(String level1Desc) {
        this.level1Desc = level1Desc;
    }

    @Column(name = "LEVEL2_DESC", length = 50)
    public String getLevel2Desc() {
        return level2Desc;
    }

    public void setLevel2Desc(String level2Desc) {
        this.level2Desc = level2Desc;
    }

    @Column(name = "LEVEL3_DESC", length = 50)
    public String getLevel3Desc() {
        return level3Desc;
    }

    public void setLevel3Desc(String level3Desc) {
        this.level3Desc = level3Desc;
    }

    @Column(name = "LEVEL4_DESC", length = 50)
    public String getLevel4Desc() {
        return level4Desc;
    }

    public void setLevel4Desc(String level4Desc) {
        this.level4Desc = level4Desc;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}
