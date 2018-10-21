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

import com.jumbo.wms.model.baseinfo.Sku;

/**
 * 基于仓库维护属性判断以哪个商品编码对接
 * 
 * @author DLY
 * 
 */
public enum WarehouseSourceSkuType {
    BAR_CODE(1), // 根据商品条码对接(sku.bar_code)
    CODE(2), // 根据商品编码对接(sku.code)
    EXT_CODE1(3), // 根据对接码1对接(sku.ext_code1)
    EXT_CODE2(4);// 根据对接码2对接(sku.ext_code2)

    private int value;

    private WarehouseSourceSkuType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static WarehouseSourceSkuType valueOf(int value) {
        switch (value) {
            case 1:
                return BAR_CODE;
            case 2:
                return CODE;
            case 3:
                return EXT_CODE1;
            case 4:
                return EXT_CODE2;
            default:
                return null;
        }
    }

    public static String getSkuByWhSourceSkuType(Sku sku, WarehouseSourceSkuType type) {
        if (type == null){ return null;}
        switch (type) {
            case BAR_CODE:
                return sku.getBarCode();
            case CODE:
                return sku.getCode();
            case EXT_CODE1:
                return sku.getExtensionCode1();
            case EXT_CODE2:
                return sku.getExtensionCode2();
            default:
                return null;
        }
    }
}
