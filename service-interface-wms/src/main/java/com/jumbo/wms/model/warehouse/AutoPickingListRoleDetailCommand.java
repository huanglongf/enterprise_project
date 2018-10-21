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
package com.jumbo.wms.model.warehouse;

/**
 * @author lichuan
 * 
 */
public class AutoPickingListRoleDetailCommand extends AutoPickingListRoleDetail {
    /**
     * 
     */
    private static final long serialVersionUID = -2218000025339679428L;
    /**
     * 配货类型
     */
    private Integer type;
    /**
     * 配货类型名称
     */
    private String pickingTypeName;
    /**
     * 商品分类id
     */
    private Long skuCategoryId;
    /**
     * 商品分类名称
     */
    private String skuCategoryName;
    /**
     * 商品大小id
     */
    private Long skuSizeId;
    /**
     * 商品大小名称
     */
    private String skuSizeName;
    /**
     * 规则Id
     */
    private Long roleId;
    /**
     * 是否包含SN字符串
     */
    private String isSnString;

    /**
     * 物流商
     */
    private String maTList;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPickingTypeName() {
        return pickingTypeName;
    }

    public void setPickingTypeName(String pickingTypeName) {
        this.pickingTypeName = pickingTypeName;
    }

    public Long getSkuCategoryId() {
        return skuCategoryId;
    }

    public void setSkuCategoryId(Long skuCategoryId) {
        this.skuCategoryId = skuCategoryId;
    }

    public String getSkuCategoryName() {
        return skuCategoryName;
    }

    public void setSkuCategoryName(String skuCategoryName) {
        this.skuCategoryName = skuCategoryName;
    }

    public Long getSkuSizeId() {
        return skuSizeId;
    }

    public void setSkuSizeId(Long skuSizeId) {
        this.skuSizeId = skuSizeId;
    }

    public String getSkuSizeName() {
        return skuSizeName;
    }

    public void setSkuSizeName(String skuSizeName) {
        this.skuSizeName = skuSizeName;
    }

    public String getIsSnString() {
        return isSnString;
    }

    public void setIsSnString(String isSnString) {
        this.isSnString = isSnString;
    }

    public String getMaTList() {
        return maTList;
    }

    public void setMaTList(String maTList) {
        this.maTList = maTList;
    }
}
