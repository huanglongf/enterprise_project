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
 * 自动化创建批次表明细（迁移）
 */
@Entity
@Table(name = "t_wh_zdh_pici_line")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ZdhPiciLine extends BaseModel {

    private static final long serialVersionUID = -2410127583679328914L;
    /**
     * PK
     */
    private Long id;

    /**
     * 店铺id
     * @return
     */
    private Long channelId;

    /**
     * 仓库id
     * @return
     */
    private Long whId;

    /**
     * 批次头ID
     */
    private Long piCiId;



    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "SEQ_WHL", sequenceName = "s_t_wh_zdh_pici", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WHL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "channel_id")
    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    @Column(name = "wh_id")
    public Long getWhId() {
        return whId;
    }

    public void setWhId(Long whId) {
        this.whId = whId;
    }

    @Column(name = "pici_id")
    public Long getPiCiId() {
        return piCiId;
    }

    public void setPiCiId(Long piCiId) {
        this.piCiId = piCiId;
    }


}
