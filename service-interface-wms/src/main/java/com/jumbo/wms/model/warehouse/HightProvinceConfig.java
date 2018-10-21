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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;


@Entity
@Table(name = "T_WH_HIGHT_PROVINCE_CONFIG")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class HightProvinceConfig extends BaseModel {


    /**
     * 
     */
    private static final long serialVersionUID = -2313629972536138870L;

    /**
     * ID
     */
    private Long id;

    /**
     * 组织ID
     */
    private Long ouId;

    /**
     * 组织类型ID
     */
    private Long ouTypeId;

    /**
     * 优先发货城市
     */
    private String priorityName;

    /**
     * version
     */
    private Integer version;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_PRIORITY_CITY_CONFIG", sequenceName = "S_T_WH_PRIORITY_CITY_CONFIG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRIORITY_CITY_CONFIG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "OU_ID")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    @Column(name = "OU_TYPE_ID")
    public Long getOuTypeId() {
        return ouTypeId;
    }

    public void setOuTypeId(Long ouTypeId) {
        this.ouTypeId = ouTypeId;
    }


    @Column(name = "PRIORITY_NAME")
    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }


    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
