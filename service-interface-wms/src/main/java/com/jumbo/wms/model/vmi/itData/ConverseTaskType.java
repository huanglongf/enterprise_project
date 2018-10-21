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

package com.jumbo.wms.model.vmi.itData;

/**
 * VMI指令类型类型
 * 
 */
public enum ConverseTaskType {
    ASN(1), // 基本信息
    MASTER(2), // 基础信息
    PRICECHANGE(3), // 价格调整
    EVERGREEN(4), // 货运单据
    PRODUCTINFO(5), // ProductInfo中文
    LISTPRICECHANGE(6) // 修改吊牌价
    ;

    private int value;

    private ConverseTaskType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ConverseTaskType valueOf(int value) {
        switch (value) {
            case 1:
                return ASN;
            case 2:
                return MASTER;
            case 3:
                return PRICECHANGE;
            case 4:
                return EVERGREEN;
            case 5:
                return PRODUCTINFO;
            case 6:
                return LISTPRICECHANGE;
            default:
                throw new IllegalArgumentException();
        }
    }
}
