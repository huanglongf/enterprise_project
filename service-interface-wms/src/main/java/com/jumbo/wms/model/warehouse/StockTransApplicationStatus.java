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

public enum StockTransApplicationStatus {
    CREATED(1), // 已创建
    OCCUPIED(2), // 库存占用（配货中）
    CHECKED(3), // 已核对
    INTRANSIT(4), // 已转出
    PARTLY_RETURNED(5), // 部分转入
    PACKING(8), // 装箱中
    FINISHED(10), // 已完成
    CANCEL_UNDO(15), // 取消未处理
    CANCELED(17), // 取消已处理
    FAILED(20), // 配货失败
    FROZEN(25) // 冻结
    ;
    private int value;

    private StockTransApplicationStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static StockTransApplicationStatus valueOf(int value) {
        switch (value) {
            case 1:
                return CREATED;
            case 2:
                return OCCUPIED;
            case 3:
                return CHECKED;
            case 4:
                return INTRANSIT;
            case 5:
                return PARTLY_RETURNED;
            case 8:
                return PACKING;
            case 10:
                return FINISHED;
            case 15:
                return CANCEL_UNDO;
            case 17:
                return CANCELED;
            case 20:
                return FAILED;
            case 25:
                return FROZEN;
            default:
                throw new IllegalArgumentException();
        }
    }
}
