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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.User;

/**
 * 包裹订单
 * 
 * @author lzb
 * 
 */
@Entity
@Table(name = "T_WH_TRANS_PACKAGE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class TransPackage extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -8495648342527768029L;

    /**
     * PK
     */
    private Long id;

    /**
     * 运单号
     */
    private String transNo;
    /**
     * 扫描耗材（仓库）
     */
    private long skuId;
    /**
     * 包裹重量
     */
    private Double packageWeight;
    /**
     * 包裹体积
     */
    private Double volume;
    /**
     * 快递订单ID（外键）
     */
    private TransOrder order;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 操作人
     */
    private User opUser;
    /**
     * 操作时间
     */
    private Date opTime;
    /**
     * 状态
     */
    private int status;

    /**
     * 物流商平台订单号
     * 
     * @return
     */
    private String extTransOrderId;


    @Column(name = "EXT_TRANS_ORDER_ID")
    public String getExtTransOrderId() {
        return extTransOrderId;
    }

    public void setExtTransOrderId(String extTransOrderId) {
        this.extTransOrderId = extTransOrderId;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WHL", sequenceName = "S_T_WH_TRANS_PACKAGE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WHL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TRANS_NO")
    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }


    @Column(name = "SKU_ID")
    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    @Column(name = "PACKAGE_WEIGHT")
    public Double getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(Double packageWeight) {
        this.packageWeight = packageWeight;
    }

    @Column(name = "VOLUME")
    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    @Index(name = "IDX_FK_ORDER_ID")
    public TransOrder getOrder() {
        return order;
    }

    public void setOrder(TransOrder order) {
        this.order = order;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



    @Column(name = "OP_TIME")
    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OP_USER_ID")
    @Index(name = "IDX_FK_OP_USER_ID")
    public User getOpUser() {
        return opUser;
    }

    public void setOpUser(User opUser) {
        this.opUser = opUser;
    }

    @Column(name = "STATUS")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
