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

public enum TransTimeType {
    ORDINARY(1), // 普通
    TIMELY(3), // 及时达
    SAME_DAY(5), // 当日
    THE_NEXT_DAY(6), // 次日
    THE_NEXT_MORNING(7), // 次晨
    YUN_NEXT_DAY(8), // 云仓专配次日
    YUN_GE_DAY(9);// 云仓专配隔日
    private int value;

    private TransTimeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TransTimeType valueOf(int value) {
        switch (value) {
            case 1:
                return ORDINARY;
            case 3:
                return TIMELY;
            case 5:
                return SAME_DAY;
            case 6:
                return THE_NEXT_DAY;
            case 7:
                return THE_NEXT_MORNING;
            case 8:
                return YUN_NEXT_DAY;
            case 9:
                return YUN_GE_DAY;
            default:
                return null;
        }
    }
}
