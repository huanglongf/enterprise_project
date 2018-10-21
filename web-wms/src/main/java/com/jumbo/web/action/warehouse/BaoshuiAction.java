package com.jumbo.web.action.warehouse;

import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.baoShui.CustomsDeclarationDto;
import com.jumbo.wms.model.warehouse.baoShui.CustomsDeclarationLineCommand;

/**
 * 保稅倉
 * 
 * @author lzb
 */
public class BaoshuiAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 8724866874045548214L;
    private StockTransApplicationCommand sta;
    @Autowired
    private WareHouseManager wareHouseManager;

    private Long customsDeclarationDtoLineId;

    private Long gQty;

    private CustomsDeclarationDto cdd;

    public String baoShuiOpOut() {
        return SUCCESS;
    }



    /**
     * 保税仓出库通知
     * 
     * @return
     */
    public String queryBaoShuiOutStaList() {
        setTableConfig();
        if (sta != null) {
            sta.setCreateTime(FormatUtil.getDate(sta.getCreateTime1()));
            sta.setEndCreateTime(FormatUtil.getDate(sta.getEndCreateTime1()));
        }
        // System.out.println(sta.getBaoShuiType());
        Pagination<CustomsDeclarationDto> list = wareHouseManager.queryBaoShuiOutStaList(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), sta, tableConfig.getSorts());
        // System.out.println(toJson(list));
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 报税仓出库明细
     * 
     * @return
     */
    public String queryBaoShuiOutStaLineList() {
        setTableConfig();
        List<CustomsDeclarationLineCommand> list = wareHouseManager.queryBaoShuiOutStaLineList(sta.getId(), sta.getSkuCode(), sta.getBarCode());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String updateBaoShuiOutStaLineById() throws JSONException {
        JSONObject ob = new JSONObject();
        try {
            wareHouseManager.updateBaoShuiOutStaLine(customsDeclarationDtoLineId, gQty);
            ob.put("msg", "success");
        } catch (Exception e) {
            ob.put("msg", "error");
        }
        request.put(JSON, ob);
        return JSON;
    }

    public String updateBaoShuiOutSta() throws JSONException {
        JSONObject ob = new JSONObject();
        try {
            wareHouseManager.updateBaoShuiOutSta(cdd);
            ob.put("msg", "success");
        } catch (Exception e) {
            ob.put("msg", "error");
        }
        request.put(JSON, ob);
        return JSON;
    }
    
    public String countWeight() throws JSONException{
        JSONObject ob = new JSONObject();
        try {
            wareHouseManager.updateWeight(userDetails.getCurrentOu().getId());
            ob.put("msg", "success");
        } catch (Exception e) {
            ob.put("msg", "error");
        }
        request.put(JSON, ob);
        return JSON;
    }
    public StockTransApplicationCommand getSta() {
        return sta;
    }

    public void setSta(StockTransApplicationCommand sta) {
        this.sta = sta;
    }



    public Long getCustomsDeclarationDtoLineId() {
        return customsDeclarationDtoLineId;
    }



    public void setCustomsDeclarationDtoLineId(Long customsDeclarationDtoLineId) {
        this.customsDeclarationDtoLineId = customsDeclarationDtoLineId;
    }



    public Long getgQty() {
        return gQty;
    }



    public void setgQty(Long gQty) {
        this.gQty = gQty;
    }



    public CustomsDeclarationDto getCdd() {
        return cdd;
    }



    public void setCdd(CustomsDeclarationDto cdd) {
        this.cdd = cdd;
    }



}
