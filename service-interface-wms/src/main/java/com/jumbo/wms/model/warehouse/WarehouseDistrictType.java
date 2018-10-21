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
 * 库区类型
 * 
 */
public enum WarehouseDistrictType {
    PICKING(1), // 拣货
    RECEIVING(5), // 收货暂存区
    STOCK(10); // 存货

    private int value;

    private WarehouseDistrictType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static WarehouseDistrictType valueOf(int value) {
        switch (value) {
            case 1:
                return PICKING;
            case 5:
                return RECEIVING;
            case 10:
                return STOCK;
            default:
                throw new IllegalArgumentException();
        }
    }
}
