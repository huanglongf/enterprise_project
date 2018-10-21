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
 * 自动化创建批次表头（迁移）
 */
@Entity
@Table(name = "t_wh_zdh_pici")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ZdhPici extends BaseModel {

    private static final long serialVersionUID = -7892199709591065998L;

    /**
     * PK
     */
    private Long id;

    /**
     * code
     * 
     * @return
     */
    private String code;

    /**
     * 创建时间
     * 
     * @return
     */
    private Date createTime;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 状态(1:正常 0：不正常)
     * 
     * @return
     */
    private Integer status;

    /**
     * 迁移状态(1:下载库存 2:库存备份 3:未完成单据备份 4: 清理库存 5:下载未完成单据 6:关闭未完成单据 7:恢复 )
     * 
     */
    private Integer moveStatus;

    /**
     * 恢复状态(1:库存数据 2：单据状态)
     * 
     * @return
     */
    private String restoreStatus;


    @Column(name = "restore_status")
    public String getRestoreStatus() {
        return restoreStatus;
    }

    public void setRestoreStatus(String restoreStatus) {
        this.restoreStatus = restoreStatus;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WHL", sequenceName = "s_t_wh_zdh_pici", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WHL")
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

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "move_status")
    public Integer getMoveStatus() {
        return moveStatus;
    }

    public void setMoveStatus(Integer moveStatus) {
        this.moveStatus = moveStatus;
    }

}
