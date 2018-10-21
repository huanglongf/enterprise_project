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

public enum StockTransVoucherType {
    INBOUND_ORDER(1), // 收货
    INBOUND_SHELVES(11), // 上架
    INBOUND_EXPENSE(13), // 费用化 (不记库存)
    OUTBOUND_GIOUT(66);// GI仓库出库
    private int value;

    private StockTransVoucherType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static StockTransVoucherType valueOf(int value) {
        switch (value) {
            case 1:
                return INBOUND_ORDER;
            case 11:
                return INBOUND_SHELVES;
            case 13:
                return INBOUND_EXPENSE;
            case 66:
                return OUTBOUND_GIOUT;
            default:
                throw new IllegalArgumentException();
        }
    }
}
