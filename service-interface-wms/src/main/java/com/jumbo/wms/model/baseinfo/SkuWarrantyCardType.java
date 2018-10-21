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

package com.jumbo.wms.model.baseinfo;

/**
 * 商品保修卡类型
 * 
 * @author sjk
 * 
 */
public enum SkuWarrantyCardType {

    NO_NEED(1), // 无需保修卡
    ALL_CHECK(2), // 需要保修卡
    NO_CHECK(3);// 不强制校验保修卡
    private int value;

    private SkuWarrantyCardType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static SkuWarrantyCardType valueOf(int value) {
        switch (value) {
            case 1:
                return NO_NEED;
            case 2:
                return ALL_CHECK;
            case 3:
                return NO_CHECK;
            default:
                return NO_CHECK;
        }
    }
}
