/**
 * PluImpl.java com.erry.model.it.impl
 * 
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.wms.model.vmi.itData;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * ClassName:PluImpl
 * 
 * @author shanshan.yu
 * @version
 * @Date 2011-9-6 02:40:50
 * 
 */
@Entity
@Table(name = "T_IT_PLU_DATA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class PluData extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -2518144886081762193L;
    private Long id;
    private Long PLUNo; // plu no .PK
    private String PLUKO; // Plu code
    private String name;
    private String suppCode; // supplier code
    private String brand;
    private String season;
    private String dimGrpCode1;
    private String dimGrpCode2;
    private String dimGrpCode3;
    private String attrCode1;
    private String attrCode2;
    private String attrCode3;
    private String attrCode4;
    private String attrCode5;
    private String attrCode6;
    private String attrCode7;
    private String attrCode8;
    private String attrCode9;
    private String attrCode10;
    private String suppRef;// supplier reference
    private BigDecimal maxDisc; // maximum discount
    private Integer isGift; // 0-no;1-yes
    private String type;
    private Integer status;
    private Integer running;
    private Integer webCollection;
    private String updFlag;
    private String attrCode11;
    private String attrCode12;
    private String attrCode13;
    private String attrCode14;
    private String attrCode15;
    private Integer carryFwdr;
    private String cfSeason;
    private Integer linkType;
    private Integer flag;
    private String batchNum;
    private String prodName;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_IT_PLU_DATA", sequenceName = "S_IT_PLU_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_IT_PLU_DATA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "PLUKO", length = 20)
    public String getPLUKO() {
        return PLUKO;
    }

    public void setPLUKO(String PLUKO) {
        this.PLUKO = PLUKO;
    }

    @Column(name = "SUPPKO", length = 30)
    public String getSuppCode() {
        return suppCode;
    }

    public void setSuppCode(String suppCode) {
        this.suppCode = suppCode;
    }

    @Column(name = "BRAND_ID", length = 50)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "SEASON_ID", length = 50)
    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    @Column(name = "DIM1GRPKO", length = 50)
    public String getDimGrpCode1() {
        return dimGrpCode1;
    }

    public void setDimGrpCode1(String dimGrpCode1) {
        this.dimGrpCode1 = dimGrpCode1;
    }

    @Column(name = "DIM2GRPKO", length = 50)
    public String getDimGrpCode2() {
        return dimGrpCode2;
    }

    public void setDimGrpCode2(String dimGrpCode2) {
        this.dimGrpCode2 = dimGrpCode2;
    }

    @Column(name = "DIM3GRPKO", length = 50)
    public String getDimGrpCode3() {
        return dimGrpCode3;
    }

    public void setDimGrpCode3(String dimGrpCode3) {
        this.dimGrpCode3 = dimGrpCode3;
    }

    @Column(name = "ATTR1KO", length = 10)
    public String getAttrCode1() {
        return attrCode1;
    }

    public void setAttrCode1(String attrCode1) {
        this.attrCode1 = attrCode1;
    }

    @Column(name = "ATTR2KO", length = 10)
    public String getAttrCode2() {
        return attrCode2;
    }

    public void setAttrCode2(String attrCode2) {
        this.attrCode2 = attrCode2;
    }

    @Column(name = "ATTR3KO", length = 10)
    public String getAttrCode3() {
        return attrCode3;
    }

    public void setAttrCode3(String attrCode3) {
        this.attrCode3 = attrCode3;
    }

    @Column(name = "ATTR4KO", length = 10)
    public String getAttrCode4() {
        return attrCode4;
    }

    public void setAttrCode4(String attrCode4) {
        this.attrCode4 = attrCode4;
    }

    @Column(name = "ATTR5KO", length = 10)
    public String getAttrCode5() {
        return attrCode5;
    }

    public void setAttrCode5(String attrCode5) {
        this.attrCode5 = attrCode5;
    }

    @Column(name = "ATTR6KO", length = 10)
    public String getAttrCode6() {
        return attrCode6;
    }

    public void setAttrCode6(String attrCode6) {
        this.attrCode6 = attrCode6;
    }

    @Column(name = "ATTR7KO", length = 10)
    public String getAttrCode7() {
        return attrCode7;
    }

    public void setAttrCode7(String attrCode7) {
        this.attrCode7 = attrCode7;
    }

    @Column(name = "ATTR8KO", length = 10)
    public String getAttrCode8() {
        return attrCode8;
    }

    public void setAttrCode8(String attrCode8) {
        this.attrCode8 = attrCode8;
    }

    @Column(name = "ATTR9KO", length = 10)
    public String getAttrCode9() {
        return attrCode9;
    }

    public void setAttrCode9(String attrCode9) {
        this.attrCode9 = attrCode9;
    }

    @Column(name = "ATTR10KO", length = 10)
    public String getAttrCode10() {
        return attrCode10;
    }

    public void setAttrCode10(String attrCode10) {
        this.attrCode10 = attrCode10;
    }

    @Column(name = "SUPPREF", length = 20)
    public String getSuppRef() {
        return suppRef;
    }

    public void setSuppRef(String suppRef) {
        this.suppRef = suppRef;
    }

    @Column(name = "MAXDISC")
    public BigDecimal getMaxDisc() {
        return maxDisc;
    }

    public void setMaxDisc(BigDecimal maxDisc) {
        this.maxDisc = maxDisc;
    }

    @Column(name = "ISGIFT")
    public Integer getIsGift() {
        return isGift;
    }

    public void setIsGift(Integer isGift) {
        this.isGift = isGift;
    }

    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "RUNNING")
    public Integer getRunning() {
        return running;
    }

    public void setRunning(Integer running) {
        this.running = running;
    }

    @Column(name = "WEBCOLLECTION")
    public Integer getWebCollection() {
        return webCollection;
    }

    public void setWebCollection(Integer webCollection) {
        this.webCollection = webCollection;
    }

    @Column(name = "UPDFLAG", length = 1)
    public String getUpdFlag() {
        return updFlag;
    }

    public void setUpdFlag(String updFlag) {
        this.updFlag = updFlag;
    }

    @Column(name = "ATTR11KO", length = 10)
    public String getAttrCode11() {
        return attrCode11;
    }

    public void setAttrCode11(String attrCode11) {
        this.attrCode11 = attrCode11;
    }

    @Column(name = "ATTR12KO", length = 10)
    public String getAttrCode12() {
        return attrCode12;
    }

    public void setAttrCode12(String attrCode12) {
        this.attrCode12 = attrCode12;
    }

    @Column(name = "ATTR13KO", length = 10)
    public String getAttrCode13() {
        return attrCode13;
    }

    public void setAttrCode13(String attrCode13) {
        this.attrCode13 = attrCode13;
    }

    @Column(name = "ATTR14KO", length = 10)
    public String getAttrCode14() {
        return attrCode14;
    }

    public void setAttrCode14(String attrCode14) {
        this.attrCode14 = attrCode14;
    }

    @Column(name = "ATTR15KO", length = 10)
    public String getAttrCode15() {
        return attrCode15;
    }

    public void setAttrCode15(String attrCode15) {
        this.attrCode15 = attrCode15;
    }

    @Column(name = "CARRYFWDR")
    public Integer getCarryFwdr() {
        return carryFwdr;
    }

    public void setCarryFwdr(Integer carryFwdr) {
        this.carryFwdr = carryFwdr;
    }

    @Column(name = "CFSEASON", length = 4)
    public String getCfSeason() {
        return cfSeason;
    }

    public void setCfSeason(String cfSeason) {
        this.cfSeason = cfSeason;
    }

    @Column(name = "LINKTYPE")
    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    @Column(name = "FLAG")
    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Column(name = "PROD_NAME", length = 110)
    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    @Column(name = "NAME", length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "PLUNO")
    public Long getPLUNo() {
        return PLUNo;
    }

    public void setPLUNo(Long pLUNo) {
        PLUNo = pLUNo;
    }

    @Column(name = "BATCH_NUM", length = 20)
    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

}
