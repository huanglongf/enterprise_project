/**
 * BrandImpl.java com.erry.model.it
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
 * ClassName:BrandImpl
 * 
 * @author shanshan.yu
 * @version
 * @Date 2011-9-6 02:37:52
 * 
 */
@Entity
@Table(name = "T_IT_BRAND_DATA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ITBrandData extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1831012252112754248L;

    private Long id;
    private String code;
    private String name;
    private BigDecimal maxdisc;
    private String updFlag;
    private Integer flag;
    private String batchNum;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_BRAND_DATA", sequenceName = "S_IT_BRAND_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BRAND_DATA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "BRANDCODE", length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "BRANDNAME", length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "MAXDISC")
    public BigDecimal getMaxdisc() {
        return maxdisc;
    }

    public void setMaxdisc(BigDecimal maxdisc) {
        this.maxdisc = maxdisc;
    }

    @Column(name = "UPDFLAG", length = 1)
    public String getUpdFlag() {
        return updFlag;
    }

    public void setUpdFlag(String updFlag) {
        this.updFlag = updFlag;
    }

    @Column(name = "FLAG")
    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Column(name = "BATCH_NUM", length = 20)
    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

}
