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

package com.jumbo.wms.model.baseinfo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * 公司，对应组织类型是Company的组织实体，记录有公司的附加信息
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_MSG_OMS_SKU_CREATE_LOG")
public class MsgOmsSkuLog extends BaseModel {

    private static final long serialVersionUID = -871011787564931281L;

    private Long id;

    private String vmiCode;

    private String extCode2;

    private Date createTime;
    
    private Long isMail;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CP", sequenceName = "S_T_MSG_OMS_SKU_CREATE_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CP")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "VMI_CODE", length = 50)
    public String getVmiCode() {
        return vmiCode;
    }

    public void setVmiCode(String vmiCode) {
        this.vmiCode = vmiCode;
    }

    @Column(name = "EXT_CODE2", length = 50)
    public String getExtCode2() {
        return extCode2;
    }

    public void setExtCode2(String extCode2) {
        this.extCode2 = extCode2;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    @Column(name = "IS_MAIL")
    public Long getIsMail() {
        return isMail;
    }

    public void setIsMail(Long isMail) {
        this.isMail = isMail;
    }
}
