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

package com.jumbo.wms.model.baseinfo;

public enum BiChannelSpecialActionType {

    INVOICE_MODE(1), // 发票导出定制
    PRINT_PACKING_PAGE(20), // 装箱清单打印定制
    PRINT_EXPRESS_BILL(30), // 运单定制
    SPECIAL_PACKING(40),// 特殊处理
    REPLACE_GOODS(60);  //更换商品
    

    private int value;

    private BiChannelSpecialActionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static BiChannelSpecialActionType valueOf(int value) {
        switch (value) {
            case 1:
                return INVOICE_MODE;
            case 20:
                return PRINT_PACKING_PAGE;
            case 30:
                return PRINT_EXPRESS_BILL;
            case 40:
                return SPECIAL_PACKING;
            case 60:
            	return REPLACE_GOODS;
            default:
                throw new IllegalArgumentException();
        }
    }
}
