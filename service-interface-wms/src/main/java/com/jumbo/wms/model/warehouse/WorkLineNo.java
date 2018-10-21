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

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * 仓库流水线编号
 * 
 */
@Entity
@Table(name = "T_WH_WORK_LINE_NO")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class WorkLineNo extends BaseModel implements Cloneable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8692035228092885800L;

    /**
     * PK
     */
    private Long id;

    /**
     * 编号
     */
    private String code;

    /**
     * 作业流水线号
     */
    private String lineNo;

    /**
     * 仓库
     */
    private OperationUnit wh;



    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WORK_LINE", sequenceName = "S_T_WH_WORK_LINE_NO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WORK_LINE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CODE", length = 100)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "LINE_No", length = 100)
    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WH_ID")
    @Index(name = "IDX_STA_MAIN_WH")
    public OperationUnit getWh() {
        return wh;
    }

    public void setWh(OperationUnit wh) {
        this.wh = wh;
    }

}
