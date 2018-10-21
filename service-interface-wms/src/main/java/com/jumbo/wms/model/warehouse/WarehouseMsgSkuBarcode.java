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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;


/**
 * 外包仓商品中间表
 * 
 * @author xiaolong.fei
 * 
 */
@Entity
@Table(name = "T_WH_THREEPL_SKU_BARCODE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class WarehouseMsgSkuBarcode extends BaseModel {


    /**
     * 
     */
    private static final long serialVersionUID = -5640277818139651312L;

    /**
     * 数据唯一标识
     */
    private Long id;

    /**
     * 商品条码
     */
    private String barcode;

    /**
     * 商品中间表ID
     */
    private WarehouseMsgSku msgSku;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_MSG_SKU_BARCODE", sequenceName = "S_T_WH_MSG_SKU_BARCODE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_MSG_SKU_BARCODE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "BAR_CODE")
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @ManyToOne
    @JoinColumn(name = "MSG_SKU_ID")
    public WarehouseMsgSku getMsgSku() {
        return msgSku;
    }

    public void setMsgSku(WarehouseMsgSku msgSku) {
        this.msgSku = msgSku;
    }


}
