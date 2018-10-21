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
 */
package com.jumbo.wms.model.warehouse;

/**
 * @author lichuan
 * 
 */
public enum InventoryLockType {
    SHOP_NOT_SALES(1), // 店铺暂不销售
    DEFECT_TO_CHECK(2), // 残次待核实
    INV_TO_CHECK(3), // 库存差异待核实
    OTHER(10); // 其他（此时，用户需填写备注说明）

    private int value;

    private InventoryLockType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static InventoryLockType valueOf(int value) {
        switch (value) {
            case 1:
                return SHOP_NOT_SALES;
            case 2:
                return DEFECT_TO_CHECK;
            case 3:
                return INV_TO_CHECK;
            case 10:
                return OTHER;
            default:
                throw new IllegalArgumentException();
        }
    }
}
