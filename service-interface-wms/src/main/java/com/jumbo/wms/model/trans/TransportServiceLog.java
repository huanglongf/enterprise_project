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
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.warehouse.TransTimeType;
import com.jumbo.wms.model.warehouse.TransType;

/**
 * 物流服务类型日志
 * 
 * @author lingyun.dai
 * 
 */
@Entity
@Table(name = "T_BI_TRANSPORT_SERVICE_LOG")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class TransportServiceLog extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 5806277552821570958L;

    /**
     * PK
     */
    private Long id;

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
    private TransportServiceStatus status;
    /**
     * 类型
     */
    private TransType type;
    /**
     * 时效类型
     */
    private TransTimeType timeType;

    /**
     * 物流服务
     */
    private Transportator trans;

    /**
     * 最后修改人
     */
    private User lastModifyId;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_TRANSPORT_SERVICE_LOG", sequenceName = "S_TRANSPORT_SERVICE_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANSPORT_SERVICE_LOG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.trans.TransportServiceStatus")})
    public TransportServiceStatus getStatus() {
        return status;
    }

    public void setStatus(TransportServiceStatus status) {
        this.status = status;
    }

    @Column(name = "TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransType")})
    public TransType getType() {
        return type;
    }

    public void setType(TransType type) {
        this.type = type;
    }

    @Column(name = "TIME_TYPE", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransTimeType")})
    public TransTimeType getTimeType() {
        return timeType;
    }

    public void setTimeType(TransTimeType timeType) {
        this.timeType = timeType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANS_ID")
    public Transportator getTrans() {
        return trans;
    }

    public void setTrans(Transportator trans) {
        this.trans = trans;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LAST_MODIFY_ID")
    public User getLastModifyId() {
        return lastModifyId;
    }

    public void setLastModifyId(User lastModifyId) {
        this.lastModifyId = lastModifyId;
    }


}
