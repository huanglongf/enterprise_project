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

public enum DefaultStatus {
    CANCELED(0), // 取消(Msg***外包仓消息实体不支持取消只有失败)/失败
    CREATED(1), // 新建
    SENT(2), // 已发送的
    REXEERROR(3),//补充执行错误，SF外包仓SN，包裹补充执行
    EXECUTING(5), // 执行中 / 部分完成
    EXECUT_CREATE(7), // 外包仓已创单 / 部分完成
    EXECUT_PACKING(9), // 外包仓已经装箱 / 部分完成
    FINISHED(10), // 完成(所有外包仓通知队列如取消设置为完成)
    INEXECUTION(20), // 不执行
    ERROR(-1);// 错误


    private int value;

    private DefaultStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    public static DefaultStatus valueOf(int value) {
        switch (value) {
            case -1:
                return ERROR;
            case 0:
                return CANCELED;
            case 1:
                return CREATED;
            case 2:
                return SENT;
            case 3:
            	return REXEERROR;
            case 5:
                return EXECUTING;
            case 7:
                return EXECUT_CREATE;
            case 9:
                return EXECUT_PACKING;
            case 10:
                return FINISHED;
            case 20:
                return INEXECUTION;
            default:
                throw new IllegalArgumentException();
        }
    }
}
