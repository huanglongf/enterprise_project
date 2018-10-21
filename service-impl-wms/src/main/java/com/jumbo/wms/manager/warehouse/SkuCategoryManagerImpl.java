package com.jumbo.wms.manager.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.support.json.JSONArray;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.SkuCategoriesDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.commandMapper.SkuCategoriesRowMapper;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuCategories;

/**
 * 商品分类维护实现逻辑
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
@Service("skuCategoryManager")
public class SkuCategoryManagerImpl extends BaseManagerImpl implements SkuCategoryManager {

    /**
     * 
     */
    private static final long serialVersionUID = 2867367155843680280L;
    @Autowired
    private SkuCategoriesDao skuCategoriesDao;
    @Autowired
    private SkuDao skuDao;

    @Transactional(readOnly = true)
    public JSONArray findSkuCategory() {
        JSONArray result = null;
        try {
            result = rebuild(build(skuCategoriesDao.findSkuCategoryList(new SkuCategoriesRowMapper())));
        } catch (Exception e) {
            log.error("", e);
            log.error("----------SkuCategoryManagerImpl.findSkuCategory({})", e.getCause());
        }
        return result;
    }

    private JSONArray rebuild(List<SkuCategories> build) throws Exception {
        List<JSONObject> listJson = new ArrayList<JSONObject>();
        // 查询第2级目录
        List<Long> tree2 = skuCategoriesDao.findSkuCategoriesSonTree2(new SingleColumnRowMapper<Long>(Long.class));
        // 查询第3级目录
        List<Long> tree3 = skuCategoriesDao.findSkuCategoriesSonTree(new SingleColumnRowMapper<Long>(Long.class));
        for (SkuCategories each : build) {
            listJson.add(ouToJson(each, tree2, tree3));
        }
        return new JSONArray(listJson);
    }

    private JSONObject ouToJson(SkuCategories each, List<Long> tree2, List<Long> tree3) throws Exception {
        JSONObject json = new JSONObject();
        json.put("id", each.getId());
        json.put("text", each.getSkuCategoriesName());
        if (each.getIsPickingCategories()) {
            json.put("text", each.getSkuCategoriesName() + "【" + SHOW + "】");
        }
        JSONObject attributes = new JSONObject();
        attributes.put("name", each.getSkuCategoriesName());
        attributes.put("able", each.getIsPickingCategories());
        attributes.put("cqty", each.getSedPickingskuQty());
        attributes.put("skuLimit", each.getSkuMaxLimit());
        for (Long id : tree2) {
            if (each.getId().equals(id)) {
                attributes.put("tree2", "1");
                break;
            } else {
                attributes.put("tree2", "0");
            }
        }
        for (Long id : tree3) {
            if (each.getId().equals(id)) {
                attributes.put("tree3", "1");
                break;
            } else {
                attributes.put("tree3", "0");
            }
        }
        json.put("attributes", attributes);
        if (each.getChildrenSkuCategoriesList() != null && each.getChildrenSkuCategoriesList().size() > 0) {
            json.put("children", rebuild(each.getChildrenSkuCategoriesList()));
        }
        return json;
    }

    private List<SkuCategories> build(List<SkuCategories> findSkuCategoryList) {
        List<SkuCategories> root = new ArrayList<SkuCategories>();
        if (findSkuCategoryList == null || findSkuCategoryList.isEmpty()) {
            return findSkuCategoryList;
        }
        Map<Long, SkuCategories> map = new HashMap<Long, SkuCategories>();
        for (SkuCategories sc : findSkuCategoryList) {
            if (sc.getSkuCategories() == null) {
                root.add(sc);
            } else {
                getChildrenForSC(map.get(sc.getSkuCategories().getId())).add(sc);
            }
            map.put(sc.getId(), sc);
        }
        return root;
    }

    private List<SkuCategories> getChildrenForSC(SkuCategories skuCategories) {
        List<SkuCategories> list = skuCategories.getChildrenSkuCategoriesList();
        if (list == null) {
            list = new ArrayList<SkuCategories>();
        }
        return list;
    }

    @Override
    public void saveNewSkuCategory(SkuCategories sc) {
        skuCategoriesDao.save(sc);
    }

    @Override
    public void updateSkuCategory(SkuCategories sc) {
        skuCategoriesDao.updateSkuCategory(sc.getId(), sc.getSkuCategoriesName(), sc.getSedPickingskuQty(), sc.getIsPickingCategories(), sc.getSkuMaxLimit());
        if (sc.getIsPickingCategories()) {
            skuCategoriesDao.updateSkuCategoriesById(sc.getId());
        }
    }

    @Override
    public void removeCategoryById(Long id) {
        skuCategoriesDao.removeCategoryById(id);
    }

    @Override
    public Integer getSkuSpTypeByBarCode(String barCode) {
        Sku sku = skuDao.getSkuByBarcode(barCode);
        if (sku == null || sku.getSpType() == null) {
            return null;
        }
        return sku.getSpType().getValue();
    }

}
