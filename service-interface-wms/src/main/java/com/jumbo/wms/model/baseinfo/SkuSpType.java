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
 */
package com.jumbo.wms.model.baseinfo;

/**
 * @author lichuan
 * 
 */
public enum SkuSpType {
    COMMON(0), // 普通商品
    CONSUMPTIVE_MATERIAL(1), // 耗材
    STARBUCKS_SVC(2), // 星巴克SVC实体卡（星礼卡）,使用资和信接口,ZHX
    STARBUCKS_SVC_T(3), // 星巴克SVC实体券（卡券）,使用资和信接口,ZHX
    STARBUCKS_SVC_VIRTUAL(4), // 星巴克SVC虚拟卡券
    STARBUCKS_MSR(5), // 星巴克MSR实体卡（星享卡）,使用惠普接口,HP
    STARBUCKS_MSR_VIRTUAL(6), // 星巴克MSR虚拟卡券
    STARBUCKS_MSR_VIRTUAL_NEW(7), // 星巴克MSR虚拟卡券(一种特殊定制的星享卡)使用惠普接口,HP
    NIKE(10), STARBUCKS_BEN(11); // 星巴克按本，使用资和信接口

    private Integer value;

    private SkuSpType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public static SkuSpType valueOf(Integer value) {
        if (null == value) {value = 0;}
        switch (value) {
            case 0:
                return COMMON;
            case 1:
                return CONSUMPTIVE_MATERIAL;
            case 2:
                return STARBUCKS_SVC;
            case 3:
                return STARBUCKS_SVC_T;
            case 4:
                return STARBUCKS_SVC_VIRTUAL;
            case 5:
                return STARBUCKS_MSR;
            case 6:
                return STARBUCKS_MSR_VIRTUAL;
            case 7:
                return STARBUCKS_MSR_VIRTUAL_NEW;
            case 10:
                return NIKE;
            case 11:
                return STARBUCKS_BEN;
            default:
                return COMMON;
        }
    }


}
