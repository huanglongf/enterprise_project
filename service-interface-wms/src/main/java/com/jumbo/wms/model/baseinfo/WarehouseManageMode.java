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

public enum WarehouseManageMode {
    /**
     * 自营
     */
    NORMAL(1), THREEPL(2), VMI(3);

    private int value;

    private WarehouseManageMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static WarehouseManageMode valueOf(int value) {
        switch (value) {
            case 1:
                return NORMAL;
            case 2:
                return THREEPL;
            case 3:
                return VMI;
            default:
                throw new IllegalArgumentException();
        }
    }
}
