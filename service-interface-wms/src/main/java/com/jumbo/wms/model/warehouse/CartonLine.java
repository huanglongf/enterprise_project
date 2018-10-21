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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Index;
import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.Sku;

/**
 * 出库装箱明细
 * 
 * 
 */
@Entity
@Table(name = "T_WH_CARTON_LINE")
public class CartonLine extends BaseModel {

    private static final long serialVersionUID = -6637521404435835635L;
    /**
     * PK
     */
    private Long id;
    /**
     * 数量
     */
    private Long qty;
    /**
     * 商品
     */
    private Sku sku;
    /**
     * 箱头
     */
    private Carton carton;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CARTON_L", sequenceName = "S_T_WH_CARTON_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CARTON_L")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "QTY")
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SKU_ID")
    @Index(name = "IDX_FK_CARTON_L_SKUID")
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CARTON_ID")
    @Index(name = "IDX_FK_CARTON_L_CTID")
    public Carton getCarton() {
        return carton;
    }

    public void setCarton(Carton carton) {
        this.carton = carton;
    }

}
