/**
 * Copyright (c) 2013 Jumbomart All Rights Reserved.
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.jumbo.wms.model.BaseModel;

/**
 * 操作者出库包裹 仓库人员-出库未交接清单
 * 
 * @author fanht
 * 
 */
@Entity
@Table(name = "T_WH_OUTBOUND_PACK", uniqueConstraints = {@UniqueConstraint(columnNames = {"TRACKING_NO"})})
public class OutBoundPack extends BaseModel {
    private static final long serialVersionUID = 52104219999862981L;

    /**
     * PK
     */
    private Long id;

    /**
     * 物流平台编码
     */
    private String lpcode;

    /**
     * 物流单号
     */
    private String trackingNo;

    /**
     * 组织节点仓库主键
     */
    private Long WarehouseId;

    /**
     * 组织节点运营中心主键
     */
    private Long ouId;

    /**
     * 操作人主键
     */
    private Long creatorId;

    /**
     * 物流主键
     */
    private Long packageId;

    /**
     * 是否已交接(0:未交接 1:已交接)
     * 
     * @return
     */
    private Long isHo;


    private Boolean isPreSale;



    @Column(name = "IS_HO")
    public Long getIsHo() {
        return isHo;
    }

    public void setIsHo(Long isHo) {
        this.isHo = isHo;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_OUTBP", sequenceName = "S_T_WH_OUTBOUND_PACK", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_OUTBP")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "LPCODE", length = 40)
    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    @Column(name = "TRACKING_NO", length = 40)
    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    @Column(name = "PACKAGE_ID", length = 19)
    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    @Column(name = "WAREHOUSE_ID", length = 19)
    public Long getWarehouseId() {
        return WarehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        WarehouseId = warehouseId;
    }

    @Column(name = "OU_ID", length = 19)
    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    @Column(name = "CREATOR_ID", length = 19)
    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    @Column(name = "is_Pre_Sale")
    public Boolean getIsPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(Boolean isPreSale) {
        this.isPreSale = isPreSale;
    }


}
