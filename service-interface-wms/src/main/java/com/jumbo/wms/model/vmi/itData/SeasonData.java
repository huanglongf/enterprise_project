/**
 * SeasonImpl.java com.erry.model.it.impl
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

/**
 * ClassName:SeasonImpl
 * 
 * @author shanshan.yu
 * @version
 * @Date 2011-9-6 02:40:08
 * 
 */

@Entity
@Table(name = "T_IT_SEASON_DATA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class SeasonData extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -9201987476127039511L;
    private Long id;
    private String code;
    private String shortCode;
    private String updFlag;
    private Date expDate;
    private Integer flag;
    private String batchNum;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SEASON_DATA", sequenceName = "S_IT_SEASON_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SEASON_DATA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SEASONCODE", length = 8)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "SHORTCODE", length = 1)
    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    @Column(name = "UPDFLAG", length = 1)
    public String getUpdFlag() {
        return updFlag;
    }

    public void setUpdFlag(String updFlag) {
        this.updFlag = updFlag;
    }

    @Column(name = "EXPDATE")
    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
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
