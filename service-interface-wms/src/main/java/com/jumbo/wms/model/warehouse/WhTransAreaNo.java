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
 * 物流省份编码
 * 
 * @author xiaolong.fei
 * 
 */
@Entity
@Table(name = "T_BI_TRANS_AREA_NUMBER")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class WhTransAreaNo extends BaseModel {


    /**
     * 
     */
    private static final long serialVersionUID = -7439995155968366511L;
    /**
     * PK
     */
    private Long id;
    /**
     * 物流商
     */
    private String lpcode;
    /**
     * 省份
     */
    private String province;
    /**
     * 地区编号
     */
    private String areaNumber;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建用户
     */
    private Long createId;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_TRANS_PROVIDE_NO", sequenceName = "S_T_BI_TRANS_AREA_NUMBER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANS_PROVIDE_NO")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "LPCODE")
    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    @Column(name = "PROVINCE")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "AREA_NUMBER")
    public String getAreaNumber() {
        return areaNumber;
    }

    public void setAreaNumber(String areaNumber) {
        this.areaNumber = areaNumber;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "CREATE_ID")
    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

}
