/**
 * DimensionImpl.java com.erry.model.it.impl
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
 * ClassName:DimensionImpl
 * 
 * @author shanshan.yu
 * @version
 * @Date 2011-9-6 02:39:38
 * 
 */
@Entity
@Table(name = "T_IT_DIMENSION_DATA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class DimensionData extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 4840702727833889490L;

    private Long id;
    private Integer dimPos; // dimension position :1-color;2-size
    private String dimgrpCode; // dimendion group code
    private String dimCode; // dimension code
    private String dimName;
    private Integer showOrder;
    private String updFlag;
    private Integer flag;
    private String batchNum;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_DIMENSION_DATA", sequenceName = "S_IT_DIMENSION_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DIMENSION_DATA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "DIMPOS")
    public Integer getDimPos() {
        return dimPos;
    }

    public void setDimPos(Integer dimPos) {
        this.dimPos = dimPos;
    }

    @Column(name = "DIMGRPKO", length = 10)
    public String getDimgrpCode() {
        return dimgrpCode;
    }

    public void setDimgrpCode(String dimgrpCode) {
        this.dimgrpCode = dimgrpCode;
    }

    @Column(name = "DIMKO", length = 5)
    public String getDimCode() {
        return dimCode;
    }

    public void setDimCode(String dimCode) {
        this.dimCode = dimCode;
    }

    @Column(name = "DIMNAME", length = 10)
    public String getDimName() {
        return dimName;
    }

    public void setDimName(String dimName) {
        this.dimName = dimName;
    }

    @Column(name = "SHOWORDER")
    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
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
