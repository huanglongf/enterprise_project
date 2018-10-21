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

package com.jumbo.wms.model.trans;

import java.util.List;



/**
 * 物流推荐规则明细 -- 商品明细
 * 
 * @author xiaolong.fei
 * 
 */

public class TransRoleDetialCommand extends TransRoleDetial {
    /**
     * 
     */
    private static final long serialVersionUID = -1261893908452049363L;

    // 物流商品引用信息
    private String code;// 商品编码
    private String barCode;// 条形码
    private String name;// 名称
    private String extensionCode1;// 对接码
    private String brandName;// 品牌

    // 物流商品分类引用信息
    private Long cateId;// 分类Id
    private String skuCategoriesName;// 分类名称

    // 物流商品标签引用信息
    private Long tagId;// 标签ID
    private String tagStatus;// 分类状态
    private String tagType;// 分类类型

    private List<TransAreaGroupDetial> areaDetials;// 配送范围明细
    private List<Long> skuIds;// 关联商品
    private List<SkuTagCommand> skuTagRefSku; // 关联商品标签中的商品
    private List<Long> categories; // 关联商品分类

    private List<String> removeKeywords;// 排除字段集合

    private List<Long> whouList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtensionCode1() {
        return extensionCode1;
    }

    public void setExtensionCode1(String extensionCode1) {
        this.extensionCode1 = extensionCode1;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public String getSkuCategoriesName() {
        return skuCategoriesName;
    }

    public void setSkuCategoriesName(String skuCategoriesName) {
        this.skuCategoriesName = skuCategoriesName;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTagStatus() {
        return tagStatus;
    }

    public void setTagStatus(String tagStatus) {
        this.tagStatus = tagStatus;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public List<Long> getSkuIds() {
        return skuIds;
    }

    public void setSkuIds(List<Long> skuIds) {
        this.skuIds = skuIds;
    }

    public List<SkuTagCommand> getSkuTagRefSku() {
        return skuTagRefSku;
    }

    public void setSkuTagRefSku(List<SkuTagCommand> skuTagRefSku) {
        this.skuTagRefSku = skuTagRefSku;
    }

    public List<Long> getCategories() {
        return categories;
    }

    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }

    public List<TransAreaGroupDetial> getAreaDetials() {
        return areaDetials;
    }

    public void setAreaDetials(List<TransAreaGroupDetial> areaDetials) {
        this.areaDetials = areaDetials;
    }

    public List<String> getRemoveKeywords() {
        return removeKeywords;
    }

    public void setRemoveKeywords(List<String> removeKeywords) {
        this.removeKeywords = removeKeywords;
    }

    public List<Long> getWhouList() {
        return whouList;
    }

    public void setWhouList(List<Long> whouList) {
        this.whouList = whouList;
    }

}
