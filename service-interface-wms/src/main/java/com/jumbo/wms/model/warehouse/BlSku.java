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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * ad品牌SKU
 * 
 * @author lz b
 * 
 */
@Entity
@Table(name = "T_BL_SKU")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class BlSku extends BaseModel {

    private static final long serialVersionUID = -1281356369589741996L;

    private Long id;// 主键
    private String pCode;// 商品num
    private String skuCdBarcode;// 商品cd或barcode
    private String technicalSize;//
    private String sizeCd;//
    private String barcode;//
    private Integer status;// 0:已创建 1：未创建
    private Date createTime;// 创建时间



    @Column(name = "P_CODE")
    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WHL", sequenceName = "S_T_BL_SKU", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WHL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SKU_CD_BARCODE")
    public String getSkuCdBarcode() {
        return skuCdBarcode;
    }

    public void setSkuCdBarcode(String skuCdBarcode) {
        this.skuCdBarcode = skuCdBarcode;
    }

    @Column(name = "TECHNICAL_SIZE")
    public String getTechnicalSize() {
        return technicalSize;
    }

    public void setTechnicalSize(String technicalSize) {
        this.technicalSize = technicalSize;
    }

    @Column(name = "SIZE_CD")
    public String getSizeCd() {
        return sizeCd;
    }

    public void setSizeCd(String sizeCd) {
        this.sizeCd = sizeCd;
    }

    @Column(name = "BARCODE")
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

}
