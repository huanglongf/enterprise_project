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

public enum PickingListStatus {
    CANCEL(0), // 取消
    WAITING(1), // 等待配货
    PACKING(2), // 配货中
    PARTLY_RETURNED(8), // 部分完成
    FINISHED(10), // 全部完成
    FAILED_SEND(19), // 无法送达
    FAILED(20) // 配货失败
    ;

    private int value;

    private PickingListStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PickingListStatus valueOf(int value) {
        switch (value) {
            case 0:
                return CANCEL;
            case 1:
                return WAITING;
            case 2:
                return PACKING;
            case 8:
                return PARTLY_RETURNED;
            case 10:
                return FINISHED;
            case 19:
                return FAILED_SEND;
            case 20:
                return FAILED;
            default:
                throw new IllegalArgumentException();
        }
    }
}
