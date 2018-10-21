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

/**
 * @author author
 */
public enum StockTransApplicationType {
    /**
     * STA类型
     */

    // 订单号
    ORDER_CODE(1),
    // 退换货申请单号
    INBOUND_RETURN_REQUEST_CODE(2),
    INBOUND_PURCHASE(11), // 采购入库
    INBOUND_SETTLEMENT(12), // 结算经销入库
    INBOUND_OTHERS(13), // 其他入库
    INBOUND_CONSIGNMENT(14), // 代销入库
    INBOUND_GIFT(15), // 赠品入库
    INBOUND_MOBILE(16), // 移库入库
    SKU_RETURN_INBOUND(17), // 货返入库
    OUTBOUND_SALES(21), // 销售出库
    OUTBOUND_OTHERS(22), // 其他出库
    OUT_SALES_ORDER_OUTBOUND_SALES(25), // 外部订单销售出库
    TRANSIT_INNER(31), // 库内移动
    TRANSIT_CROSS(32), // 库间移动

    INBOUND_RETURN_REQUEST(41), // 退换货申请-退货入库
    OUTBOUND_RETURN_REQUEST(42), // 退换货申请-换货出库
    INVENTORY_STATUS_CHANGE(45), // 库存状态修改
    INBOUND_INVENTORY_INITIALIZE(50), // 库存初始化

    GI_PUT_SHELVES(55), // GI 上架


    OUTBOUND_PURCHASE(61), // 采购出库（采购退货出库）
    OUTBOUND_SETTLEMENT(62), // 结算经销出库
    OUTBOUND_PACKAGING_MATERIALS(63), // 包材领用出库
    OUTBOUND_CONSIGNMENT(64), // 代销 出库
    INBOUND_RETURN_PURCHASE(65), // 采购退货调整入库

    VMI_INBOUND_CONSIGNMENT(81), // VMI移库入库
    VMI_ADJUSTMENT_INBOUND(85), // VMI库存调整入库
    VMI_ADJUSTMENT_OUTBOUND(86), // VMI库存调整出库
    VMI_OWNER_TRANSFER(88), // 转店
    SAME_COMPANY_TRANSFER(90), // 同公司调拨
    DIFF_COMPANY_TRANSFER(91), // 不同公司调拨
    VMI_RETURN(101), // VMI退大仓
    VMI_TRANSFER_RETURN(102), // VMI转店退仓

    INVENTORY_LOCK(110), // 库存锁定

    WELFARE_USE(201), // 福利领用
    FIXED_ASSETS_USE(202), // 固定资产领用
    SCARP_OUTBOUND(204), // 报废出库
    SALES_PROMOTION_USE(205), // 促销领用
    LOW_VALUE_CONSUMABLE_USE(206), // 低值易耗品
    SAMPLE_OUTBOUND(210), // 样品领用出库
    SAMPLE_INBOUND(211), // 样品领用入库
    SKU_EXCHANGE_OUTBOUND(212), // 商品置换出库
    SKU_EXCHANGE_INBOUND(213), // 商品置换入库
    REAPAIR_OUTBOUND(214), // 送修出库
    REAPAIR_INBOUND(215), // 送修入库
    SERIAL_NUMBER_OUTBOUND(216), // 串号拆分出库
    SERIAL_NUMBER_INBOUND(217), // 串号拆分入库
    SERIAL_NUMBER_GROUP_OUTBOUND(218), // 串号组合出库
    SERIAL_NUMBER_GROUP_INBOUND(219), // 串号组合入库
    WMS_OTHER_IOBOUND(222), // wms其他类型出入库指令（无前置单据）
    INVENTORY_ADJUSTMENT_UPDATE(251);// 库存出入调整（针对盘点调整）



    private int value;

    private StockTransApplicationType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static StockTransApplicationType valueOf(int value) {
        switch (value) {
            case 1:
                return ORDER_CODE;
            case 2:
                return INBOUND_RETURN_REQUEST_CODE;
            case 11:
                return INBOUND_PURCHASE;
            case 12:
                return INBOUND_SETTLEMENT;
            case 13:
                return INBOUND_OTHERS;
            case 14:
                return INBOUND_CONSIGNMENT;
            case 15:
                return INBOUND_GIFT;
            case 16:
                return INBOUND_MOBILE;
            case 17:
                return SKU_RETURN_INBOUND;
            case 21:
                return OUTBOUND_SALES;
            case 22:
                return OUTBOUND_OTHERS;
            case 25:
                return OUT_SALES_ORDER_OUTBOUND_SALES;
            case 31:
                return TRANSIT_INNER;
            case 32:
                return TRANSIT_CROSS;
            case 41:
                return INBOUND_RETURN_REQUEST;
            case 42:
                return OUTBOUND_RETURN_REQUEST;
            case 45:
                return INVENTORY_STATUS_CHANGE;
            case 50:
                return INBOUND_INVENTORY_INITIALIZE;
            case 55:
                return GI_PUT_SHELVES;
            case 61:
                return OUTBOUND_PURCHASE;
            case 62:
                return OUTBOUND_SETTLEMENT;
            case 63:
                return OUTBOUND_PACKAGING_MATERIALS;
            case 64:
                return OUTBOUND_CONSIGNMENT;
            case 65:
                return INBOUND_RETURN_PURCHASE;
            case 81:
                return VMI_INBOUND_CONSIGNMENT;
            case 85:
                return VMI_ADJUSTMENT_INBOUND;
            case 86:
                return VMI_ADJUSTMENT_OUTBOUND;
            case 88:
                return VMI_OWNER_TRANSFER;
            case 90:
                return SAME_COMPANY_TRANSFER;
            case 91:
                return DIFF_COMPANY_TRANSFER;
            case 101:
                return VMI_RETURN;
            case 102:
                return VMI_TRANSFER_RETURN;
            case 110:
                return INVENTORY_LOCK;
            case 201:
                return WELFARE_USE;
            case 202:
                return FIXED_ASSETS_USE;
            case 204:
                return SCARP_OUTBOUND;
            case 205:
                return SALES_PROMOTION_USE;
            case 206:
                return LOW_VALUE_CONSUMABLE_USE;
            case 210:
                return SAMPLE_OUTBOUND;
            case 211:
                return SAMPLE_INBOUND;
            case 212:
                return SKU_EXCHANGE_OUTBOUND;
            case 213:
                return SKU_EXCHANGE_INBOUND;
            case 214:
                return REAPAIR_OUTBOUND;
            case 215:
                return REAPAIR_INBOUND;
            case 216:
                return SERIAL_NUMBER_OUTBOUND;
            case 217:
                return SERIAL_NUMBER_INBOUND;
            case 218:
                return SERIAL_NUMBER_GROUP_OUTBOUND;
            case 219:
                return SERIAL_NUMBER_GROUP_INBOUND;
            case 222:
                return WMS_OTHER_IOBOUND;
            case 251:
                return INVENTORY_ADJUSTMENT_UPDATE;
            default:
                throw new IllegalArgumentException();
        }
    }
}
