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

package com.jumbo.wms.model;

public enum SlipType {
	NOTMAL(99), // 无前置单据
    PURCHASE_ORDER(1), // 采购单
    SALES_ORDER(2), // 销售单
    RETURN_REQUEST(3), // 退换货申请
    OUT_RETURN_REQUEST(4), // 外部退换货申请
    WMS_SHELF_LIFE(5), // 保质期商品
    REVERSE_NP_ADJUSTMENT_INBOUND(300)// 反向NP调整入库
    ;
    private int value;

    private SlipType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    public static SlipType valueOf(int value) {
        switch (value) {
            case 1:
                return PURCHASE_ORDER;
            case 2:
                return SALES_ORDER;
            case 3:
                return RETURN_REQUEST;
            case 4:
                return OUT_RETURN_REQUEST;
            case 5:
                return WMS_SHELF_LIFE;
            case 300:
                return REVERSE_NP_ADJUSTMENT_INBOUND;
            default:
                return NOTMAL;
        }
    }
}
