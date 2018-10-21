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
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * 作业类型
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_WH_TRANSACTION_TYPE")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class TransactionType extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1067247275540049046L;

    /**
     * PK
     */
    private Long id;

    /**
     * 作业类型编码
     */
    private String code;

    /**
     * 作业类型名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 作业方向
     */
    private TransactionDirection direction;

    /**
     * 计划与控制
     */
    private TransactionControl control;

    /**
     * 是否系统预定义
     */
    private Boolean isSystem = false;

    /**
     * 是否参与成本计算
     */
    private Boolean isInCost = false;

    /**
     * 是否有效
     */
    private Boolean isAvailable = true;
    private int version;

    /**
     * 相关运营中心组织
     */
    private OperationUnit ou;

    /**
     * 是否同步淘宝
     */
    private Boolean isSynchTaobao = false;
    /**
     * 是否发送MQ消息
     */
    private Boolean isMq = false;
    /**
     * 最后修改时间
     */
    private Date lastModifyTime;

    /**
     * 是否可以在 【库区库位作业类型设定】此界面显示配置功能
     */
    private Boolean customeDef = false;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_TRST", sequenceName = "S_T_WH_TRANSACTION_TYPE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRST")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CODE", length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "NAME", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "DESCRIPTION", length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "DIRECTION", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransactionDirection")})
    public TransactionDirection getDirection() {
        return direction;
    }

    public void setDirection(TransactionDirection direction) {
        this.direction = direction;
    }


    @Column(name = "custome_def", length = 255)
    public Boolean getCustomeDef() {
        return customeDef;
    }

    public void setCustomeDef(Boolean customeDef) {
        this.customeDef = customeDef;
    }

    @Column(name = "CONTROL", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.warehouse.TransactionControl")})
    public TransactionControl getControl() {
        return control;
    }

    public void setControl(TransactionControl control) {
        this.control = control;
    }

    @Column(name = "IS_SYSTEM")
    public Boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    @Column(name = "LAST_MODIFY_TIME")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Column(name = "IS_INCOST")
    public Boolean getIsInCost() {
        return isInCost;
    }

    public void setIsInCost(Boolean isInCost) {
        this.isInCost = isInCost;
    }

    @Column(name = "IS_AVAILABLE")
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OU_ID")
    @Index(name = "IDX_TRST_OU")
    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    @Transient
    public int getIntControl() {
        return control.getValue();
    }

    public void setIntControl(int control) {
        setControl(TransactionControl.valueOf(control));
    }

    public void setIntDirection(int direction) {
        setDirection(TransactionDirection.valueOf(direction));
    }

    @Transient
    public int getIntDirection() {
        return direction.getValue();
    }

    @Column(name = "IS_SYNCH_TAOBAO")
    public Boolean getIsSynchTaobao() {
        return isSynchTaobao;
    }

    public void setIsSynchTaobao(Boolean isSynchTaobao) {
        this.isSynchTaobao = isSynchTaobao;
    }

    @Column(name = "IS_MQ")
    public Boolean getIsMq() {
        return isMq;
    }

    public void setIsMq(Boolean isMq) {
        this.isMq = isMq;
    }

    public static String returnTypeInbound(StockTransApplicationType type) {
        switch (type) {
            case INBOUND_PURCHASE:// 采购入库
                return Constants.TRANSACTION_TYPE_CODE_PURCHASE_INBOUND;
            case INBOUND_SETTLEMENT:// 结算经销入库
                return Constants.TRANSACTION_TYPE_SETTLEMENT_INBOUND;
            case INBOUND_OTHERS:// 其他入库
                return Constants.TRANSACTION_TYPE_OTHERS_INBOUND;
            case INBOUND_CONSIGNMENT:// 代销入库
                return Constants.TRANSACTION_TYPE_CONSIGNMENT_INBOUND;
            case INBOUND_GIFT:// 赠品入库
                return Constants.TRANSACTION_TYPE_GIFT_INBOUND;
            case INBOUND_MOBILE:// 移库入库
                return Constants.TRANSACTION_TYPE_MOBILE_INBOUND;
            case SKU_RETURN_INBOUND:// 货返入库
                return Constants.TRANSACTION_TYPE_SKU_RETURN_INBOUND;
            case TRANSIT_INNER:// 库内移动-入库
                return Constants.TRANSACTION_TYPE_TRANSIT_INNER_IN;
            case TRANSIT_CROSS:// 库间移动-入库
                return Constants.TRANSACTION_TYPE_TRANSIT_CROSS_IN;
            case INBOUND_RETURN_REQUEST:// 退换货申请-退货入库
                return Constants.TRANSACTION_TYPE_INBOUND_RETURN_REQUEST;
            case INVENTORY_STATUS_CHANGE:// 库存状态修改-入库
                return Constants.TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_IN;
            case INBOUND_INVENTORY_INITIALIZE:// 库存初始化
                return Constants.TRANSACTION_TYPE_INBOUND_INITIALIZE;
            case GI_PUT_SHELVES:// GI 上架 -入库
                return Constants.TRANSACTION_TYPE_GI_PUT_SHELVES_INBOUND;
            case VMI_INBOUND_CONSIGNMENT:// VMI移库入库
                return Constants.TRANSACTION_TYPE_VMI_INBOUND_CONSIGNMENT;
            case VMI_ADJUSTMENT_INBOUND:// VMI库存调整入库
                return Constants.TRANSACTION_TYPE_VMI_ADJUSTMENT_INBOUND_CONSIGNMENT;
            case VMI_OWNER_TRANSFER:// VMI转店-入库
                return Constants.VMI_TRANSFER_IN;
            case SAME_COMPANY_TRANSFER:// VMI调拨(同公司)-入库
                return Constants.VMI_FLITTING_IN;
            case DIFF_COMPANY_TRANSFER:// 不同公司调拨-入库
                return Constants.DIFF_COMPANY_TRANSFER_IN;
            case SAMPLE_INBOUND:// 样品领用入库
                return Constants.SAMPLE_INBOUND;
            case SKU_EXCHANGE_INBOUND: // 商品置换入库
                return Constants.SKU_EXCHANGE_INBOUND;
            case REAPAIR_INBOUND:// 送修入库
                return Constants.REAPAIR_INBOUND;
            case SERIAL_NUMBER_INBOUND:// 串号拆分入库
                return Constants.SERIAL_NUMBER_INBOUND;
            case SERIAL_NUMBER_GROUP_INBOUND:// 串号组合入库
                return Constants.SERIAL_NUMBER_GROUP_INBOUND;
            case INVENTORY_ADJUSTMENT_UPDATE:// 库存调整入
                return Constants.INVENTORY_ADJUSTMENT_UPDATE_INBOUND;
            case INBOUND_RETURN_PURCHASE:// 采购退货调整入库
                return Constants.TRANSACTION_RETURN_PURCHASE_INBOUND;
            default:
                throw new BusinessException("No support for " + type.name());
        }
    }

    public static String returnTypeOutbound(StockTransApplicationType type) {
        switch (type) {
            case OUTBOUND_SALES:// 销售出库
            case OUT_SALES_ORDER_OUTBOUND_SALES:// 外部订单销售出库
                return Constants.TRANSACTION_TYPE_CODE_SALES_OUTBOUND;
            case OUTBOUND_OTHERS:// 其他出库
                return Constants.TRANSACTION_TYPE_OTHERS_OUTBOUND;
            case TRANSIT_INNER:// 库内移动-出库
                return Constants.TRANSACTION_TYPE_TRANSIT_CROSS_OUT;
            case TRANSIT_CROSS:// 库间移动-出库
                return Constants.TRANSACTION_TYPE_TRANSIT_CROSS_OUT;
            case OUTBOUND_RETURN_REQUEST:// 退换货申请-换货出库
                return Constants.TRANSACTION_TYPE_OUTBOUND_RETURN_REQUEST;
            case INVENTORY_STATUS_CHANGE:// 库存状态修改-出库
                return Constants.TRANSACTION_TYPE_INVENTORY_STATUS_CHANGE_OUT;
            case GI_PUT_SHELVES: // GI出库上架
                return Constants.TRANSACTION_TYPE_GI_PUT_SHELVES_OUTBOUND;
            case OUTBOUND_PURCHASE:// 采购出库（采购退货出库）
                return Constants.PURCHASE_OUTBOUND;
            case OUTBOUND_SETTLEMENT:// 结算经销出库
                return Constants.SETTLEMENT_OUTBOUND;
            case OUTBOUND_PACKAGING_MATERIALS:// 包材领用出库
                return Constants.PACKAGING_MATERIALS_OUTBOUND;
            case OUTBOUND_CONSIGNMENT:// 代销 出库
                return Constants.CONSIGNMENT_OUTBOUND;
            case VMI_ADJUSTMENT_OUTBOUND: // VMI库存调整出库
                return Constants.TRANSACTION_TYPE_VMI_ADJUSTMENT_OUTBOUND_CONSIGNMENT;
            case VMI_OWNER_TRANSFER:// VMI转店-出库
                return Constants.VMI_TRANSFER_OUT;
            case SAME_COMPANY_TRANSFER:// VMI调拨（同公司)-出库
                return Constants.VMI_FLITTING_OUT;
            case DIFF_COMPANY_TRANSFER:// VMI挑拨（同公司)-出库
                return Constants.DIFF_COMPANY_TRANSFER_OUT;
            case VMI_RETURN:// VMI退仓
                return Constants.VMI_RETURN_OUT;
            case WELFARE_USE:// 福利领用
                return Constants.WELFARE_USE;
            case FIXED_ASSETS_USE:// 固定资产领用
                return Constants.FIXED_ASSETS_USE;
            case SCARP_OUTBOUND:// 报废出库
                return Constants.SCARP_OUTBOUND;
            case SALES_PROMOTION_USE:// 促销领用
                return Constants.SALES_PROMOTION_USE;
            case LOW_VALUE_CONSUMABLE_USE:// 低值易耗品
                return Constants.LOW_VALUE_CONSUMABLE_USE;
            case SAMPLE_OUTBOUND:// 样品领用出库
                return Constants.SAMPLE_OUTBOUND;
            case SKU_EXCHANGE_OUTBOUND:// 商品置换出库
                return Constants.SKU_EXCHANGE_OUTBOUND;
            case REAPAIR_OUTBOUND:// 送修出库
                return Constants.REAPAIR_OUTBOUND;
            case SERIAL_NUMBER_OUTBOUND:// 串号拆分出库
                return Constants.SERIAL_NUMBER_OUTBOUND;
            case SERIAL_NUMBER_GROUP_OUTBOUND:// 串号组合出库
                return Constants.SERIAL_NUMBER_GROUP_OUTBOUND;
            case INVENTORY_ADJUSTMENT_UPDATE:// 库存调整出
                return Constants.INVENTORY_ADJUSTMENT_UPDATE_OUTBOUND;
            default:
                throw new BusinessException("No support for " + type.name());
        }
    }
}
