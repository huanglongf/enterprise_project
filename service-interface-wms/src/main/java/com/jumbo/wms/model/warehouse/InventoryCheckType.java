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
 * 库存盘点类型
 * 
 */
public enum InventoryCheckType {
    NORMAL(1), // 普通盘点表
    VMI(2), // VMI盘点表
    SKU_SPLIT(3), // SKU 拆分
    SKU_MERGER(4), // SKU 合并
    DAILY(5);//日常盘点
    private int value;

    private InventoryCheckType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static InventoryCheckType valueOf(int value) {
        switch (value) {
            case 1:
                return NORMAL;
            case 2:
                return VMI;
            case 3:
                return SKU_SPLIT;
            case 4:
                return SKU_MERGER;
            case 5:
                return DAILY;
            default:
                throw new IllegalArgumentException();
        }
    }
}
