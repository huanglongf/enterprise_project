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

package com.jumbo.wms.model.compensation;

/**
 * 索赔单状态
 * 
 * @author lihui
 * 
 */
public enum CompensateStatus {
    CREATE(1), // 已创建
    DISPOSE(2), // 已处理
    CHECK(3), // 已审核
    SUCCEED(5), // 索赔成功
    FAIL(10), // 索赔失败
    DISCARD(17); // 作废
    private int value;

    private CompensateStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static CompensateStatus valueOf(int value) {
        switch (value) {
            case 1:
                return CREATE;
            case 2:
                return DISPOSE;
            case 3:
                return CHECK;
            case 5:
                return SUCCEED;
            case 10:
                return FAIL;
            case 17:
                return DISCARD;
            default:
                throw new IllegalArgumentException();
        }
    }
}
