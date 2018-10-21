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
package com.jumbo.wms.model.task;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_K3_COMMON_CONFIG")
public class CommonConfig extends BaseModel {


    /**
	 * 
	 */
    private static final long serialVersionUID = -866960024231058945L;

    Long id;

    /**
     * 参数名称
     */
    String paraName;
    /**
     * 参数值
     */
    String paraValue;
    /**
     * 参数组名称
     */
    String paraGroup;

    String memo;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WH_K3_CONFIG", sequenceName = "S_T_K3_COMMON_CONFIG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WH_K3_CONFIG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "PNAME", length = 20)
    public String getParaName() {
        return paraName;
    }

    public void setParaName(String paraName) {
        this.paraName = paraName;
    }

    @Column(name = "pValue", length = 50)
    public String getParaValue() {
        return paraValue;
    }

    public void setParaValue(String paraValue) {
        this.paraValue = paraValue;
    }

    @Column(name = "PGROUP", length = 255)
    public String getParaGroup() {
        return paraGroup;
    }

    public void setParaGroup(String paraGroup) {
        this.paraGroup = paraGroup;
    }

    @Column(name = "MEMO", length = 500)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
