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

package com.jumbo.wms.model.warehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;

/**
 * 仓库作业申请单行
 * 
 * @author lingyun.dai
 * 
 */
@Entity
@Table(name = "T_WH_STA_SPECIAL_EXECUTE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class StaSpecialExecute extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2247215822090901191L;

    /**
     * PK
     */
    private Long id;

    /**
     * 相关仓库作业申请单
     */
    private StockTransApplication sta;
    /**
     * 类型
     */
    private StaSpecialExecuteType type;

    /**
     * 备注
     */
    private String memo;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_STA_SPECIAL_EXECUTE", sequenceName = "S_T_WH_STA_SPECIAL_EXECUTE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STA_SPECIAL_EXECUTE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    @Index(name = "IDX_STA_SPECIAL_EXECUTE_STA")
    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.StaSpecialExecuteType")})
    public StaSpecialExecuteType getType() {
        return type;
    }

    public void setType(StaSpecialExecuteType type) {
        this.type = type;
    }

    @Column(name = "MEMO", length = 100)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
