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

public enum StaSpecialExecuteType {
    COACH_PRINT(1), // COACH小票打印
    BURBERRY_OUT_PRINT(3), // Burberry 寄货单 打印
    BURBERRY_RETURN_PRINT(5), // Burberry 退货表格 打印
    NIKE_GIFT_CARD_PRINT(8), // NIKE HK 礼品卡 打印A
    NIKE_GIFT_CARD_B_PRINT(9), // NIKE HK 礼品卡 打印B
    NIKE_GIFT_CARD_C_PRINT(10), // NIKE HK 礼品卡 打印C
    NIKE_ACTIVITY_PIC(15), // NIKE HK 活动文案图片 打印
    IDS_SH_UA(40), // UA
    MK_PACKAGING_COMMON(45), // 普通包装
    MK_PACKAGING_GIFT(46), // 礼品包装
    MK_PACKAGING_STAFF(47), // 员工包装
    GUCCI_GIFT_CARD_PRINT(50); // GUCCI 礼品卡 打印

    private int value;

    private StaSpecialExecuteType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static StaSpecialExecuteType valueOf(int value) {
        switch (value) {
            case 1:
                return COACH_PRINT;
            case 3:
                return BURBERRY_OUT_PRINT;
            case 5:
                return BURBERRY_RETURN_PRINT;
            case 8:
                return NIKE_GIFT_CARD_PRINT;
            case 9:
                return NIKE_GIFT_CARD_B_PRINT;
            case 10:
                return NIKE_GIFT_CARD_C_PRINT;
            case 15:
                return NIKE_ACTIVITY_PIC;
            case 40:
                return IDS_SH_UA;
            case 45:
                return MK_PACKAGING_COMMON;
            case 46:
                return MK_PACKAGING_GIFT;
            case 47:
                return MK_PACKAGING_STAFF;
            case 50:
                return GUCCI_GIFT_CARD_PRINT;
            default:
                throw new IllegalArgumentException();
        }
    }
}
