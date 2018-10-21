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

package com.jumbo.wms.model.odo;

import java.util.Date;

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

/**
 * 库间移动
 * 
 * @author hui.li
 * 
 */
@Entity
@Table(name = "T_WH_ODO")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class Odo extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 4182021074285604085L;

    /**
     * PK
     */
    private Long id;

    /**
     * 单号
     */
    private String code;

    /**
     * 店铺
     */
    private String owner;

    /**
     * 状态（1，新建；2，待出库反馈；3，出库完成；4待入库反馈；5，差异待确认；6
     * 差异待创单;7差异待反馈;8差异未完成;10，完成;20,出库单创建失败；21，入库单创建失败；22，差异单创建失败;17,已取消）
     */
    private Integer status;

    /**
     * 库存状态
     */
    private Long invStatusId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long creatorId;

    private int version;
    
    /**
     * 出库仓库ID
     */
    private Long ouId;
    
    /**
     * 入库仓库ID
     */
    private Long targetOuId;

    /**
     * 差异入库指定仓
     */
    private Long diffOuid;


    private Long errorCount;



    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_S_T_WH_ODO", sequenceName = "S_T_WH_ODO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_S_T_WH_ODO")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "OWNER")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "INV_STATUS_ID")
    public Long getInvStatusId() {
        return invStatusId;
    }

    public void setInvStatusId(Long invStatusId) {
        this.invStatusId = invStatusId;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "CREATOR_ID")
    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "OU_ID")
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    @Column(name = "TARGET_OU_ID")
    public Long getTargetOuId() {
        return targetOuId;
    }

    public void setTargetOuId(Long targetOuId) {
        this.targetOuId = targetOuId;
    }

    @Column(name = "DIFF_OUID")
    public Long getDiffOuid() {
        return diffOuid;
    }

    public void setDiffOuid(Long diffOuid) {
        this.diffOuid = diffOuid;
    }

    @Column(name = "ERROR_COUNT")
    public Long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Long errorCount) {
        this.errorCount = errorCount;
    }

}
