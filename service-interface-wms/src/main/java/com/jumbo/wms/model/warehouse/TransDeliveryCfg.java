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
import javax.persistence.Version;

import org.hibernate.annotations.Index;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Transportator;

/**
 * 仓库物流维护
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_WH_TRANS_DELIVERY_CFG")
public class TransDeliveryCfg extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 52104219996659422L;

    /**
     * PK
     */
    private Long id;

    /**
     * version
     */
    private Integer version;

    /**
     * 相关仓库组织
     */
    private OperationUnit ou;

    /**
     * 物流
     */
    private Transportator transportator;

    /**
     * 库存数量
     */
    private Long quantity;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_TDCFG", sequenceName = "S_T_WH_TRANS_DELIVERY_CFG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TDCFG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_TD_CFG_OU_ID")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANS_ID")
    @Index(name = "IDX_TD_CFG_TRANS_ID")
    public Transportator getTransportator() {
        return transportator;
    }

    public void setTransportator(Transportator transportator) {
        this.transportator = transportator;
    }

    @Column(name = "QUANTITY")
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
