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
 * 外包仓商品中间表
 * 
 * @author xiaolong.fei
 * 
 */
public class WarehouseMsgSkuCommand extends WarehouseMsgSku {


    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 是否保质期
     */
    private String storeMode;

    /**
     * 商品ID
     */
    private Long skuId;
    /**
     * 外包仓商品对接
     */
    private String extCode1;
    /**
     * 外包仓商品对接
     */
    private String extCode2;
    private int validDate;

    /**
     * 商品分类
     */
    private String categories;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getStoreMode() {
        return storeMode;
    }

    public void setStoreMode(String storeMode) {
        this.storeMode = storeMode;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public int getValidDate() {
        return validDate;
    }

    public void setValidDate(int validDate) {
        this.validDate = validDate;
    }

    public String getExtCode1() {
        return extCode1;
    }

    public void setExtCode1(String extCode1) {
        this.extCode1 = extCode1;
    }

    public String getExtCode2() {
        return extCode2;
    }

    public void setExtCode2(String extCode2) {
        this.extCode2 = extCode2;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

}
