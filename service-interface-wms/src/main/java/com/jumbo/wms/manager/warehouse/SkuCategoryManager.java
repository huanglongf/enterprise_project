package com.jumbo.wms.manager.warehouse;

import loxia.support.json.JSONArray;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.baseinfo.SkuCategories;

/**
 * 商品分类维护接口逻辑
 * 
 * @author jinlong.ke
 * 
 */
public interface SkuCategoryManager extends BaseManager {
    public static final String SHOW = "配货时显示";
    public static final String HIDDEN = "配货时不显示";

    /**
     * 查找商品分类树状结构列表
     * 
     * @return
     */
    JSONArray findSkuCategory();

    /**
     * 保存新增的商品分类
     * 
     * @param sc
     */
    void saveNewSkuCategory(SkuCategories sc);

    /**
     * 更新分类节点信息
     * 
     * @param sc
     */
    void updateSkuCategory(SkuCategories sc);

    /**
     * 删除选中的分类节点，判断该节点下是否有商品，如果没有则删除，父节点则连带子节点一起删除
     * 
     * @param id
     */
    void removeCategoryById(Long id);
    
    /**
     * 根据barCode得到Sku的Type
     * 
     * @param barCode
     * @return
     */
    Integer getSkuSpTypeByBarCode(String barCode);
}
