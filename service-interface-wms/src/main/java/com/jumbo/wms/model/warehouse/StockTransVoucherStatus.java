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

public enum StockTransVoucherStatus {
    CANCELED(0), // 取消
    CREATED(1), // 已创建
    CONFIRMED(3), // (收货)已确认
    CHECK(5), // (审核)已核对
    FINISHED(10); // 已完成

    private int value;

    private StockTransVoucherStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static StockTransVoucherStatus valueOf(int value) {
        switch (value) {
            case 0:
                return CANCELED;
            case 1:
                return CREATED;
            case 3:
                return CONFIRMED;
            case 5:
                return CHECK;
            case 10:
                return FINISHED;
            default:
                throw new IllegalArgumentException();
        }
    }
}
