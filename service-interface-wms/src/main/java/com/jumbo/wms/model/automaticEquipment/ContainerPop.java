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

package com.jumbo.wms.model.automaticEquipment;

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
 * 集货弹出扣
 * 
 * @author xiaolong.fei
 * 
 */
@Entity
@Table(name = "t_wh_container_pop_ref")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class ContainerPop extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8567885886530504453L;
    /**
     * PK
     */
    private Long id;

    /**
     * 货箱号
     */
    private String containerCode;
    /**
     * 弹出扣编码
     */
    private String popCode;
    /**
     * wcs编码
     */
    private String wcsCode;
    /**
     * 仓库ID
     */
    private Long ouId;
    /**
     * 创建时间
     */
    private Date createTime;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_container_pop_ref", sequenceName = "S_container_pop_ref ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_container_pop_ref")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "container_code")
    public String getContainerCode() {
        return containerCode;
    }

    public void setContainerCode(String containerCode) {
        this.containerCode = containerCode;
    }

    @Column(name = "pop_code")
    public String getPopCode() {
        return popCode;
    }

    public void setPopCode(String popCode) {
        this.popCode = popCode;
    }

    @Column(name = "wsc_code")
    public String getWcsCode() {
        return wcsCode;
    }

    public void setWcsCode(String wcsCode) {
        this.wcsCode = wcsCode;
    }

    @Column(name = "ou_id")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
