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
 * 箱号状态
 * 
 * @author sjk
 * 
 */
public enum CartonStatus {
    CREATED(1), // 新建
    DOING(5), // 执行中
    FINISH(10); // 完成

    private int value;

    private CartonStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static CartonStatus valueOf(int value) {
        switch (value) {
            case 1:
                return CREATED;
            case 5:
                return DOING;
            case 10:
                return FINISH;
            default:
                throw new IllegalArgumentException();
        }
    }
}
