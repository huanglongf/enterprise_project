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

public enum TransactionControl {
    NO_CONTROL(0), // 不做控制
    LE_PLAN(1), // 执行量小于等于计划量
    GE_PLAN(2), // 执行量大于等于计划量
    EQ_PLAN(3); // 执行量必须等于计划量

    private int value;

    private TransactionControl(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    public static TransactionControl valueOf(int value) {
        switch (value) {
            case 0:
                return NO_CONTROL;
            case 1:
                return LE_PLAN;
            case 2:
                return GE_PLAN;
            case 3:
                return EQ_PLAN;
            default:
                throw new IllegalArgumentException();
        }
    }
}
