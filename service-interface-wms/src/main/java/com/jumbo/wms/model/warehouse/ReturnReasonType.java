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
 * 
 * @author xiaolong.fei
 *退货原因类型
 */
public enum ReturnReasonType {
    SEVEN_DAY(1), // 7天无理由退/换货
    THE_QUALITY_OF_GOOD(2), // 商品质量问题
    IN_KIND_MATCH_AND_ORDER_GOODS(3),// 实物与订购商品不符
    NOT_COD_ORDER(4),//非COD订单客户拒收
    COD_ORDER(5),//COD订单客户拒收
    THRITY_DAY(6),//三十天无理由退货
    COD_NORMAL(7),//COD正常退货
    OTHER(99);//其他
    private int value;
    private ReturnReasonType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ReturnReasonType valueOf(int value) {
        switch (value) {
            case 1:
                return SEVEN_DAY;
            case 2:
                return THE_QUALITY_OF_GOOD;
            case 3:
                return IN_KIND_MATCH_AND_ORDER_GOODS;
            case 4:
                return NOT_COD_ORDER;
            case 5:
                return COD_ORDER;
            case 6:
                return THRITY_DAY;
            case 7:
                return COD_NORMAL;
            case 99:
                return OTHER;
            default:
                return null;
        }
    }
}
