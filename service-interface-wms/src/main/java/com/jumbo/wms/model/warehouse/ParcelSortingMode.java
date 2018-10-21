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
 * 拣货模式
 * 
 * @author benjamin
 * 
 */
public enum ParcelSortingMode {
    ANY(0), // 不限
    SINGLE(1), // 按单逐一分拣 (单张订单单间)
    SECONDARY(2), // 合并二次分拣
    MIXED(3); // 混合树状分拣

    private int value;

    private ParcelSortingMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ParcelSortingMode valueOf(int value) {
        switch (value) {
            case 0:
                return ANY;
            case 1:
                return SINGLE;
            case 2:
                return SECONDARY;
            case 3:
                return MIXED;
            default:
                throw new IllegalArgumentException();
        }
    }
}
