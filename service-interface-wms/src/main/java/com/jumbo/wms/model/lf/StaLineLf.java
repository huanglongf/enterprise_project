/**
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

package com.jumbo.wms.model.lf;

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
 * 利丰单据附加信息明细表
 * 
 * @author bin.hu
 * 
 */
@Entity
@Table(name = "T_WH_STA_LINE_LF")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class StaLineLf extends BaseModel {


    /**
     * 
     */
    private static final long serialVersionUID = -748673352371446604L;

    /**
     * PK
     */
    private Long id;

    /**
     * 作业单ID
     */
    private Long staId;

    /**
     * 作业单明细ID
     */
    private Long staLineId;

    /**
     * 商品ID
     */
    private Long skuId;

    /**
     * VAS
     */
    private String vas;

    /**
     * 仓库ID
     */
    private Long ouId;



    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_S_T_WH_STA_LINE_LF", sequenceName = "S_T_WH_STA_LINE_LF", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_S_T_WH_STA_LINE_LF")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STA_ID")
    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    @Column(name = "STA_LINE_ID")
    public Long getStaLineId() {
        return staLineId;
    }

    public void setStaLineId(Long staLineId) {
        this.staLineId = staLineId;
    }

    @Column(name = "SKU_ID")
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "VAS")
    public String getVas() {
        return vas;
    }

    public void setVas(String vas) {
        this.vas = vas;
    }

    @Column(name = "OU_ID")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }


}
