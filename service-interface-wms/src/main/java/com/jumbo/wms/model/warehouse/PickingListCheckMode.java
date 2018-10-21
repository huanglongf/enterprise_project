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

public enum PickingListCheckMode {
    DEFAULE(1), // 默认(普通多件订单核对模式)-按单核对
    PICKING_CHECK(5), // 配货此批核对(单件订单使用)-批次核对
    PICKING_SECKILL(8), // 秒杀订单核对-导入核对
    PICKING_GROUP(10), // 团购订单核对模式-按单核对
    PICKING_PACKAGE(2), // 套装组合商品
    PICKING_SPECIAL(3), // 特殊包装
    PCIKING_O2OQS(6);// O2OQS

    private int value;

    private PickingListCheckMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PickingListCheckMode valueOf(int value) {
        switch (value) {
            case 1:
                return DEFAULE;
            case 5:
                return PICKING_CHECK;
            case 8:
                return PICKING_SECKILL;
            case 10:
                return PICKING_GROUP;
            case 2:
                return PICKING_PACKAGE;
            case 3:
                return PICKING_SPECIAL;
            case 6:
                return PCIKING_O2OQS;
            default:
                throw new IllegalArgumentException();
        }
    }
}
