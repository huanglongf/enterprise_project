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

package com.jumbo.wms.model;

public enum DataStatus {
    CREATING(8), // 新建中
    CREATED(0), // 新建
    EXECUTING(1), // 执行中
    FINISHED(5), // 完成
    ERROR(-1);// 错误

    private int value;

    private DataStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    public static DataStatus valueOf(int value) {
        switch (value) {
            case 0:
                return CREATED;
            case 1:
                return EXECUTING;
            case 5:
                return FINISHED;
            case -1:
                return ERROR;
            case 8:
                return CREATING;
            default:
                throw new IllegalArgumentException();
        }
    }
}
