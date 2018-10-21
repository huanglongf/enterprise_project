package com.jumbo.web.action.warehouse;

import java.util.List;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.warehouse.ShopStoreInfoManager;
import com.jumbo.wms.model.warehouse.CreatePickingListSql;
import com.jumbo.wms.model.warehouse.ShopStoreInfo;

/**
 * O2O门店信息维护
 * 
 * @author jinlong.ke
 * 
 */
public class ShopStoreInfoAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = -2129548751154392139L;
    @Autowired
    private ShopStoreInfoManager shopStoreInfoManager;
    private String code;
    private ShopStoreInfo ssi;

    private String modeName;



    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    /**
     * 初始页面跳转
     * 
     * @return
     */
    public String redirect() {
        return SUCCESS;
    }

    /**
     * 查询所有O2O门店信息
     * 
     * @return
     */
    public String getAllShopStore() {
        setTableConfig();
        List<ShopStoreInfo> skuList = shopStoreInfoManager.getAllShopStore(code);
        request.put(JSON, toJson(skuList));
        return JSON;
    }


    public String getAllRuleName() {
        List<CreatePickingListSql> list = shopStoreInfoManager.getAllRuleName(userDetails.getCurrentOu().getId(), modeName);
        request.put(JSON, JsonUtil.collection2json(list));
        return JSON;
    }

    /**
     * 页面下拉菜单
     * 
     * @return
     * @throws Exception
     */
    public String getAllShopStoreForOption() throws Exception {
        request.put(JSON, JsonUtil.collection2json(shopStoreInfoManager.getAllShopStore(null)));
        return JSON;
    }

    /**
     * 删除特定O2O门店信息
     * 
     * @return
     * @throws JSONException
     */
    public String deleteShopStoreById() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            shopStoreInfoManager.deleteShopStoreById(ssi.getId());
            result.put("result", "success");
        } catch (Exception e) {
            result.put("result", "error");
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 修改特定O2O门店信息
     * 
     * @return
     * @throws JSONException
     */
    public String editShopStoreByCode() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            shopStoreInfoManager.editShopStoreByCode(ssi);
            result.put("result", "success");
        } catch (Exception e) {
            result.put("result", "error");
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 新增O2O门店信息
     * 
     * @return
     * @throws JSONException
     */
    public String addNewShopStore() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            shopStoreInfoManager.addNewShopStore(ssi);
            result.put("result", "success");
        } catch (BusinessException e) {
            result.put("result", "error");
            result.put("msg", "01");
        } catch (Exception e) {
            result.put("result", "error");
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ShopStoreInfo getSsi() {
        return ssi;
    }

    public void setSsi(ShopStoreInfo ssi) {
        this.ssi = ssi;
    }


}
