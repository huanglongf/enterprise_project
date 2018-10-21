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

package com.jumbo.wms.model.vmi.espData;

public enum EspritOrderPOType {
    DIRECTSEND(1), // 直送
    NOTDIRECTSEND(3), // 非直送
    IMPORT(5), // 进口
    SPECIAL(7), // 特许证产品
    DALIAN_MENTION(9), // 大连自提
    ;
    private int value;

    private EspritOrderPOType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static EspritOrderPOType valueOf(int value) {
        switch (value) {
            case 1:
                return DIRECTSEND;
            case 3:
                return NOTDIRECTSEND;
            case 5:
                return IMPORT;
            case 7:
                return SPECIAL;
            case 9:
                return DALIAN_MENTION;
            default:
                throw new IllegalArgumentException();
        }
    }
}
