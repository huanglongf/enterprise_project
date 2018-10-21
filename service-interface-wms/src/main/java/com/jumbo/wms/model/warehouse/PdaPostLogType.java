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
 * 配货模式
 * 
 * @author sjk
 * 
 */
public enum PdaPostLogType {
    PO(1), // 采购入库
    INVENTORY_CHECK(2) // 盘点
    ;

    private int value;

    private PdaPostLogType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static PdaPostLogType valueOf(int value) {
        switch (value) {
            case 1:
                return PO;
            case 2:
                return INVENTORY_CHECK;
            default:
                throw new IllegalArgumentException();
        }
    }
}
