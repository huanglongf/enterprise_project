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
import javax.persistence.Transient;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 集货库位日志
 * 
 * @author hui.li
 * 
 */
@Entity
@Table(name = "T_WH_GOODS_COLLECTION_LOG")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class GoodsCollectionLog extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -8474943925838544820L;

    /**
     * PK
     */
    private Long id;

    /**
     * 集货库位编码
     */
    private String collectionCode;
    /**
     * 批次号
     */
    private String pickingCode;
    /**
     * 操作人
     */
    private Long opUser;
    /**
     * 操作时间
     */
    private Date opTime;

    /**
     * 周转箱
     */
    private String containerCode;

    /**
     * 状态
     */
    private String status;

    private String loginName;



    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SGCL", sequenceName = "S_GOODS_COLLECTION_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SGCL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "COLLECTION_CODE")
    public String getCollectionCode() {
        return collectionCode;
    }

    public void setCollectionCode(String collectionCode) {
        this.collectionCode = collectionCode;
    }

    @Column(name = "PICKING_CODE")
    public String getPickingCode() {
        return pickingCode;
    }

    public void setPickingCode(String pickingCode) {
        this.pickingCode = pickingCode;
    }

    @Column(name = "OP_USER")
    public Long getOpUser() {
        return opUser;
    }

    public void setOpUser(Long opUser) {
        this.opUser = opUser;
    }

    @Column(name = "OP_TIME")
    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    @Column(name = "CONTAINER_CODE")
    public String getContainerCode() {
        return containerCode;
    }

    public void setContainerCode(String containerCode) {
        this.containerCode = containerCode;
    }

    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Transient
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


}
