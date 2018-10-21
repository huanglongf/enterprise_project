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

public enum GiftType {
    NIKE_GIFT(10), // 赠送礼品卡(需打印)
    COACH_CARD(20), // Coach保修卡
    GIFT_PACKAGE(30), // 商品特殊包装
    GIFT_PRINT(50), // 商品特殊印制
    HAAGENDAZS_TICKET(60), // 哈根达斯券处理
    CK_GIFT_BOX(70), // 礼盒包装
    MK_GIFT_MANUAL(80), // 礼品手册
    HUB4_VAS_GIFT(90); // 适配器接口增值服务


    private int value;

    private GiftType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GiftType valueOf(int value) {
        switch (value) {
            case 10:
                return NIKE_GIFT;
            case 20:
                return COACH_CARD;
            case 30:
                return GIFT_PACKAGE;
            case 50:
                return GIFT_PRINT;
            case 60:
                return HAAGENDAZS_TICKET;
            case 70:
                return CK_GIFT_BOX;
            case 80:
                return MK_GIFT_MANUAL;
            case 90:
                return HUB4_VAS_GIFT;
            default:
                throw new IllegalArgumentException();
        }
    }
}
