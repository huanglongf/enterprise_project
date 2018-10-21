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

package com.jumbo.wms.model.trans;


import java.util.Date;

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

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.BiChannel;

/**
 * 物流推荐规则
 * 
 * @author lingyun.dai
 * 
 */
@Entity
@Table(name = "T_BI_TRANS_ROLE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class TransRole extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 8132270086199373053L;

    /**
     * 默认快递序号
     */
    public final static int defaultTransSort = 9999;

    /**
     * PK
     */
    private Long id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态
     */
    private TransAreaGroupStatus status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 渠道
     */
    private BiChannel channel;

    /**
     * 物流服务
     */
    private TransportService transService;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_TRANS_ROLE", sequenceName = "S_T_BI_TRANS_ROLE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANS_ROLE")
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

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.trans.TransAreaGroupStatus")})
    public TransAreaGroupStatus getStatus() {
        return status;
    }

    public void setStatus(TransAreaGroupStatus status) {
        this.status = status;
    }

    @Column(name = "SORT")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHANNEL_ID")
    public BiChannel getChannel() {
        return channel;
    }


    public void setChannel(BiChannel channel) {
        this.channel = channel;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TS_ID")
    public TransportService getTransService() {
        return transService;
    }

    public void setTransService(TransportService transService) {
        this.transService = transService;
    }
}
