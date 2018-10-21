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

package com.jumbo.wms.model.pda;

public enum PdaOrderType {
    Inbound(1), // 入库-收货
    INBOUND_SHELEVES(3), // 入库-上架
    Outbound(11), // 出库
    InnerMove(21), // 库内移动
    INITIATIVEMOVEOUT(23),//主动移库出库
    INVENTORYCHECK(31), // 盘点
    RETURN(41), // 退仓单
    HANDOVER(51);// 交接

    private int value;

    private PdaOrderType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PdaOrderType valueOf(int value) {
        switch (value) {
            case 1:
                return Inbound;
            case 3:
                return INBOUND_SHELEVES;
            case 11:
                return Outbound;
            case 21:
                return InnerMove;
            case 23:
                return INITIATIVEMOVEOUT;
            case 31:
                return INVENTORYCHECK;
            case 41:
                return RETURN;
            case 51:
                return HANDOVER;
            default:
                throw new IllegalArgumentException();
        }
    }
}
