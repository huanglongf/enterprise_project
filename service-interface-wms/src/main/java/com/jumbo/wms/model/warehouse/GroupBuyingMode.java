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
 * 团购模型类型
 * 
 * @author fanht
 * 
 */
public enum GroupBuyingMode {
    SINGEL_ORDER(1), // 单件订单
    SINGEL_GOODS(2);// 单品订单

    private int value;

    private GroupBuyingMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static GroupBuyingMode valueOf(int value) {
        switch (value) {
            case 1:
                return SINGEL_ORDER;
            case 2:
                return SINGEL_GOODS;
            default:
                throw new IllegalArgumentException();
        }
    }
}
