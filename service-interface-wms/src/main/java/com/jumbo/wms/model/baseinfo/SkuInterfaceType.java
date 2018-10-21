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

public enum SkuInterfaceType {

    NUL(1), // 1/空
    STB_ZHX(3), // 星巴克智和星接口
    STB_HP(5), // 星巴克HP接口
    NIKE(6), // nike接口
    STB_BEN(7);// 星巴克 本


    private int value;

    private SkuInterfaceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SkuInterfaceType valueOf(int value) {
        switch (value) {
            case 3:
                return STB_ZHX;
            case 5:
                return STB_HP;
            case 6:
                return NIKE;
            case 7:
                return STB_BEN;
            default:
                return NUL;
        }
    }
}
