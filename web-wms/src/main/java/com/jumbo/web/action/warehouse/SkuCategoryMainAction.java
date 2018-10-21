package com.jumbo.web.action.warehouse;

import java.util.List;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.manager.warehouse.SkuCategoryManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.baseinfo.SkuCategories;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 商品分类维护控制
 * 
 * @author jinlong.ke
 * 
 */
public class SkuCategoryMainAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = 9207557800168596375L;
    @Autowired
    private SkuCategoryManager skuCategoryManager;
    private SkuCategories sc;
    private String barCode;
    @Autowired
    private WareHouseManager wareHouseManager;

    /**
     * 跳转到商品维护页面
     * 
     * @return
     */
    public String skuCategoryMaintenance() {
        return SUCCESS;
    }

    /**
     * 构造商品分类树状列表
     * 
     * @return
     * @throws Exception
     */
    public String skuCategoryTree() throws Exception {
        request.put(JSON, skuCategoryManager.findSkuCategory());
        return JSON;
    }

    /**
     * 保存新增的商品分类
     * 
     * @return
     * @throws JSONException 
     */
    public String saveNewSkuCategory() throws JSONException {
        JSONObject jb = new JSONObject();
        try {
            skuCategoryManager.saveNewSkuCategory(sc);
            jb.put("rs", true);
        } catch (Exception e) {
            log.error(e.getClass().getSimpleName());
            if(e.getClass().getSimpleName().equals("DataIntegrityViolationException")){
                jb.put("error", "1");
            }
            log.error("SkuCategoryMainAction.saveNewSkuCategroy.error");
        }
        request.put(JSON, jb);
        return JSON;
    }

    /**
     * 修改并保存分类节点信息
     * 
     * @return
     * @throws JSONException 
     */
    public String updateSkuCategory() throws JSONException {
        JSONObject jb = new JSONObject();
        try {
            skuCategoryManager.updateSkuCategory(sc);
            jb.put("rs", true);
        } catch (Exception e) {
            //log.error("",e);
            log.error(e.getClass().getSimpleName());
            if(e.getClass().getSimpleName().equals("ConstraintViolationException")){
                jb.put("error", "1");
            }
            log.error("SkuCategoryMainAction.updateSkuCategory.error");
        }
        request.put(JSON, jb);
        return JSON;
    }

    /**
     * 根据选中节点进行删除
     * 
     * @return
     */
    public String removeCategoryById() {
        JSONObject jb = new JSONObject();
        List<Sku> plist = wareHouseManager.getProductByCategoryId(sc.getId());
        try {
            if (plist.isEmpty() || plist.size() <= 0) {
                skuCategoryManager.removeCategoryById(sc.getId());
                jb.put("rs", "true");
            } else {
                jb.put("rs", "false");
            }
        } catch (Exception e) {
            log.error("",e);
            log.error("SkuCategoryMainAction.removeCategoryById.error");
        }
        request.put(JSON, jb);
        return JSON;
    }
    
    /**
     * 得到Sku的Type
     * 
     * @return
     * @throws Exception
     */
    public String getSkuTypeByBarCode() throws Exception {
        JSONObject json = new JSONObject();
        Integer type = skuCategoryManager.getSkuSpTypeByBarCode(barCode);
        json.put("value", type);
        request.put(JSON, json);
        return JSON;
    }
    
    public SkuCategories getSc() {
        return sc;
    }

    public void setSc(SkuCategories sc) {
        this.sc = sc;
    }
    
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
