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
 * 时间类型
 * 
 */
public enum TimeTypeMode {
    YEAR(1), // 年
    MONTH(2), // 月
    DAY(3);// 日

    private int value;

    private TimeTypeMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static TimeTypeMode valueOf(int value) {
        switch (value) {
            case 1:
                return YEAR;
            case 2:
                return MONTH;
            case 3:
                return DAY;
            default:
                throw new IllegalArgumentException();
        }
    }
}
