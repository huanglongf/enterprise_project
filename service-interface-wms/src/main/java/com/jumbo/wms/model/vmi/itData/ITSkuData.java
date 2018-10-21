/**
 * SkuImpl.java com.erry.model.it.impl
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
 * ClassName:SkuImpl
 * 
 * @author shanshan.yu
 * @version
 * @Date 2011-9-6 02:41:39
 * 
 */
@Entity
@Table(name = "T_IT_SKU_DATA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ITSkuData extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 7732512116673818698L;
    private Long id;
    private Long SKNO;
    private String skuKo;
    private Long pluNo;
    private String updFlag;
    private Integer flag;
    private String batchNum;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_IT_SKU_DATA", sequenceName = "S_IT_SKU_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_IT_SKU_DATA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SKUKO", length = 20)
    public String getSkuKo() {
        return skuKo;
    }

    public void setSkuKo(String skuKo) {
        this.skuKo = skuKo;
    }

    @Column(name = "PLUNO")
    public Long getPluNo() {
        return pluNo;
    }

    public void setPluNo(Long pluKo) {
        this.pluNo = pluKo;
    }

    @Column(name = "UPDFLAG")
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

    @Column(name = "SKUNO")
    public Long getSKNO() {
        return SKNO;
    }

    public void setSKNO(Long sKNO) {
        SKNO = sKNO;
    }
}
