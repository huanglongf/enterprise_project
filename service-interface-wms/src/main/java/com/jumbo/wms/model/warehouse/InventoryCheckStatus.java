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

public enum InventoryCheckStatus {
    CANCELED(0), // 取消
    CREATED(1), // 新建
    UNEXECUTE(5), // 差异未处理
    CHECKWHINVENTORY(6), // 盘点库存已确认
    CHECKWHMANAGER(7), // 仓库经理已确认
    CONFIRMOMS(8), // OMS已确认
    FINISHED(10); // 完成


    private int value;

    private InventoryCheckStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static InventoryCheckStatus valueOf(int value) {
        switch (value) {
            case 0:
                return CANCELED;
            case 1:
                return CREATED;
            case 5:
                return UNEXECUTE;
            case 6:
                return CHECKWHINVENTORY;
            case 7:
                return CHECKWHMANAGER;
            case 8:
                return CONFIRMOMS;
            case 10:
                return FINISHED;
            default:
                throw new IllegalArgumentException();
        }
    }
}
