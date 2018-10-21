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
 * 物流业务类型
 * 
 * @author lingyun.dai
 * 
 */
public enum TransDeliveryType {
    ORDINARY(0), // 普通
    AVIATION(1), // 汽运
    LAND(5); // 陆运

    private int value;

    private TransDeliveryType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TransDeliveryType valueOf(int value) {
        switch (value) {
            case 0:
                return ORDINARY;
            case 1:
                return AVIATION;
            case 5:
                return LAND;
            default:
                return ORDINARY;
        }
    }
}
