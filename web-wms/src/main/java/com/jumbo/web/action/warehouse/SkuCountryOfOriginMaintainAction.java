package com.jumbo.web.action.warehouse;

import java.util.Arrays;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.command.SkuCountryOfOriginCommand;



/**
 * 
 * 
 */
public class SkuCountryOfOriginMaintainAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 8745086351040752728L;
    /**
     * 
     */
    /**
     * 商品产地维护
     */
    @Autowired
    private WareHouseManager wareHouseManager;

    private SkuCountryOfOriginCommand skuCommand;

    private String ids;

    /**
     * 跳转到商品产地维护
     * 
     * @return
     */
    public String skuCountryOfOriginmain() {
        return SUCCESS;
    }

    /**
     * 分页查询
     */
    public String getSkuCountryOfOriginPage() {
        setTableConfig();
        Pagination<SkuCountryOfOriginCommand> list = wareHouseManager.getSkuCountryOfOriginPage(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), skuCommand, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 批量删除产地商品
     * 
     * @return
     * @throws JSONException
     */
    public String delSkuCountryOfOrigin() throws JSONException {
        JSONObject result = new JSONObject();
        List<String> list = Arrays.asList(ids.split(","));
        String brand = null;
        try {
            brand = wareHouseManager.delSkuCountryOfOrigin(list, null, null);
        } catch (Exception e) {
            log.error("批量删除产地商品异常！", e);
            brand = "-1";// 系统异常
        }
        result.put("brand", brand);
        request.put(JSON, result);
        return JSON;
    }


    public SkuCountryOfOriginCommand getSkuCommand() {
        return skuCommand;
    }

    public void setSkuCommand(SkuCountryOfOriginCommand skuCommand) {
        this.skuCommand = skuCommand;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

}
