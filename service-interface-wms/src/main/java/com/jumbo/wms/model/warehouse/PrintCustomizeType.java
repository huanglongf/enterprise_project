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

public enum PrintCustomizeType {
    VMI_RETURN_PACKING_LIST(10), // VMI退仓装箱清单
    VMI_RETURN_BOX_LABEL(11), // 退仓箱明细
    VMI_RETURN_BOX_SURFACE_LABEL(12), // 退仓箱标签
    VMI_RETURN_POD(13), // POD打印
    OUTBOUND_PACKING_LIST(14)// 出库装箱清单
    ;
    private int value;

    private PrintCustomizeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PrintCustomizeType valueOf(int value) {
        switch (value) {
            case 10:
                return VMI_RETURN_PACKING_LIST;
            case 11:
                return VMI_RETURN_BOX_LABEL;
            case 12:
                return VMI_RETURN_BOX_SURFACE_LABEL;
            case 13:
                return VMI_RETURN_POD;
            case 14:
                return OUTBOUND_PACKING_LIST;
            default:
                throw new IllegalArgumentException();
        }
    }
}
