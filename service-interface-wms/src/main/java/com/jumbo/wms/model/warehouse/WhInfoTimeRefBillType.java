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
 * 订单触发时间 单据类型
 * 
 * @author xiaolong.fei
 * 
 */
public enum WhInfoTimeRefBillType {
    STA(1), // 作业单
    STA_PICKING(2), // 配货清单
    INVOICE_ORDER(3);// 发票清单

    private int value;

    private WhInfoTimeRefBillType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static WhInfoTimeRefBillType valueOf(int value) {
        switch (value) {
            case 1:
                return STA;
            case 2:
                return STA_PICKING;
            case 3: 
                return INVOICE_ORDER;
            default:
                throw new IllegalArgumentException();
        }
    }
}
