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

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.User;

/**
 * 快递服务支持区域
 * 
 * @author xiaolong.fei
 * 
 */
@Entity
@Table(name = "T_BI_TRANSPORT_SERVICE_AREA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class TransportServiceArea extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 5806277552821570958L;

    /**
     * PK
     */
    private Long id;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改人
     */
    private User lastModifyId;

    /**
     * 物流服务ID
     */
    private TransportService transServiceId;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_TRANSPORT_SERVICE_AREA", sequenceName = "S_TRANSPORT_SERVICE_AREA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANSPORT_SERVICE_AREA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "PROVINCE")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "DISTRICT")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LAST_MODIFY_ID")
    public User getLastModifyId() {
        return lastModifyId;
    }

    public void setLastModifyId(User lastModifyId) {
        this.lastModifyId = lastModifyId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANS_SERVICE_ID")
    public TransportService getTransServiceId() {
        return transServiceId;
    }

    public void setTransServiceId(TransportService transServiceId) {
        this.transServiceId = transServiceId;
    }

}
