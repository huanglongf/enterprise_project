package com.jumbo.web.action.warehouse;

import java.util.Arrays;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.warehouse.QsSkuCommand;



/**
 * 
 * 
 */
public class QsSkuMaintainAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 8745086351040752728L;
    /**
     * 
     */
    /**
     * QS商品维护
     */
    @Autowired
    private WareHouseManager wareHouseManager;

    private QsSkuCommand qsSkuCommand;

    private String ids;

    /**
     * 跳转到QS商品维护
     * 
     * @return
     */
    public String qsSkuMaintain() {
        return SUCCESS;
    }

    /**
     * 分页查询
     */
    public String getQsSkuPage() {
        setTableConfig();
        Pagination<QsSkuCommand> list = wareHouseManager.getQsSkuPage(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), qsSkuCommand, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 批量删除QS商品
     * 
     * @return
     * @throws JSONException
     */
    public String delQsSkuBinding() throws JSONException {
        JSONObject result = new JSONObject();
        List<String> list = Arrays.asList(ids.split(","));
        String brand = null;
        try {
            brand = wareHouseManager.delQsSkuBinding(list, null, null);
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("delQsSkuBinding error:" + ids, e);
            };
            brand = "-1";// 系统异常
        }
        result.put("brand", brand);
        request.put(JSON, result);
        return JSON;
    }

    public QsSkuCommand getQsSkuCommand() {
        return qsSkuCommand;
    }

    public void setQsSkuCommand(QsSkuCommand qsSkuCommand) {
        this.qsSkuCommand = qsSkuCommand;
    }


    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

}
