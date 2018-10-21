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

package com.jumbo.wms.model.vmi.itData;

/**
 * VMI指令类型类型
 * 
 */
public enum VMIInstructionType {
    VMI_INBOUND(1), // VMI移库入库
    VMI_ADJUSTMENT(2);// VMI库存调整

    private int value;

    private VMIInstructionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static VMIInstructionType valueOf(int value) {
        switch (value) {
            case 1:
                return VMI_INBOUND;
            case 2:
                return VMI_ADJUSTMENT;
            default:
                throw new IllegalArgumentException();
        }
    }
}
