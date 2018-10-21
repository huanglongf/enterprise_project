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
import com.jumbo.wms.model.warehouse.PhysicalWarehouse;
import com.jumbo.wms.model.warehouse.PickingList;

/**
 * 集货库位配置
 * 
 * @author hui.li
 * 
 */
@Entity
@Table(name = "t_wh_goods_collection")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class GoodsCollection extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -6121401052975700631L;

    /**
     * PK
     */
    private Long id;

    /**
     * 集货库位编码
     */
    private String collectionCode;
    /**
     * 弹出口编码
     */
    private String popUpCode;
    /**
     * 顺序
     */
    private Integer sort;
    /**
     * 修改人
     */
    private User modifier;
    /**
     * 更新时间
     */
    private Date modifyDate;

    /**
     * 配货批次
     */
    private PickingList pickinglist;

    /**
     * 状态（1：待使用，2：部分集货，3：集货完成）
     */
    private Integer status;

    private PhysicalWarehouse physicalId;

    /**
     * 周转箱集入时间
     */
    private Date startTime;

    /**
     * 通道
     */
    private String passWay;

    /**
     * 拣货模式
     */
    private String pickModel;


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

    @Column(name = "COLLECTION_CODE")
    public String getCollectionCode() {
        return collectionCode;
    }

    public void setCollectionCode(String collectionCode) {
        this.collectionCode = collectionCode;
    }

    @Column(name = "POP_UP_CODE")
    public String getPopUpCode() {
        return popUpCode;
    }

    public void setPopUpCode(String popUpCode) {
        this.popUpCode = popUpCode;
    }

    @Column(name = "SORT")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODIFIER")
    public User getModifier() {
        return modifier;
    }

    public void setModifier(User modifier) {
        this.modifier = modifier;
    }

    @Column(name = "MODIFY_DATE")
    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PICKINGLIST_ID")
    public PickingList getPickinglist() {
        return pickinglist;
    }

    public void setPickinglist(PickingList pickinglist) {
        this.pickinglist = pickinglist;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PHYSICAL_ID")
    public PhysicalWarehouse getPhysicalId() {
        return physicalId;
    }

    public void setPhysicalId(PhysicalWarehouse physicalId) {
        this.physicalId = physicalId;
    }

    @Column(name = "START_TIME")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column(name = "PASS_WAY")
    public String getPassWay() {
        return passWay;
    }

    public void setPassWay(String passWay) {
        this.passWay = passWay;
    }

    @Column(name = "PICK_MODEL")
    public String getPickModel() {
        return pickModel;
    }

    public void setPickModel(String pickModel) {
        this.pickModel = pickModel;
    }


}
