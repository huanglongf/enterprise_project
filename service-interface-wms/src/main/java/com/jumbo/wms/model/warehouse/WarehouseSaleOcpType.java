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
 * @author DLY
 * 
 */
public enum WarehouseSaleOcpType {
    INVENTORY_FIFO(1), // 库存先进先出
    WAREHOUSE_FIFO(2); // 库区降序先进先出

    private int value;

    private WarehouseSaleOcpType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static WarehouseSaleOcpType valueOf(int value) {
        switch (value) {
            case 1:
                return INVENTORY_FIFO;
            case 2:
                return WAREHOUSE_FIFO;
            default:
                throw new IllegalArgumentException();
        }
    }
}
