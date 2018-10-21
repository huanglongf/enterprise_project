package com.jumbo.web.action.warehouse;

import java.util.List;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.warehouse.SkuSnManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.SkuSn;
import com.jumbo.wms.model.warehouse.SkuSnCommand;

public class SnListBycodeAction extends BaseJQGridProfileAction {
    /**
     * 
     */
    private static final long serialVersionUID = 2929313653856534568L;
    @Autowired
    private SkuSnManager skuSnManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    private List<SkuSn> skuSns;
    private SkuSn skuSn;
    private Sku sku;

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    private Sku skulist;
    private String snnumber;

    public String getSnnumber() {
        return snnumber;
    }

    public void setSnnumber(String snnumber) {
        this.snnumber = snnumber;
    }

    public SkuSn getSkuSn() {
        return skuSn;
    }

    public void setSkuSn(SkuSn skuSn) {
        this.skuSn = skuSn;
    }

    public Sku getSkulist() {
        return skulist;
    }

    public List<SkuSn> getSkuSns() {
        return skuSns;
    }

    public void setSkuSns(List<SkuSn> skuSns) {
        this.skuSns = skuSns;
    }

    public void setSkulist(Sku skulist) {
        this.skulist = skulist;
    }

    private String barCode;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String back() {
        return SUCCESS;
    }

    /**
     * 方法说明：进入 出入库SN号导入 页面
     * 
     * @author LuYingMing
     * @date 2016年9月8日 下午6:25:04
     * @return
     */
    public String initOutOfStorageSnPage() {
        return SUCCESS;
    }

    public String snsimportHand() throws JSONException {
        JSONObject jsonObject = wareHouseManager.snsimportHand(skuSns, userDetails.getCurrentOu().getId());
        request.put(JSON, jsonObject);
        return JSON;
    }

    public String getSkuBycode() throws JSONException {
        JSONObject result = new JSONObject();
        Long whouid = userDetails.getCurrentOu().getId();
        Sku s = wareHouseManager.checkSnBybarcode(barCode, whouid);
        if (s == null) {
            result.put("result", "product");
        } else {
            Boolean issnproduct = s.getIsSnSku();
            if (issnproduct == null) {
                result.put("result", "notSNproduct");
            } else if (issnproduct.equals(false)) {
                result.put("result", "notSNproduct");
            } else {
                result.put("skulist", JsonUtil.obj2json(s));
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    public String createSn() throws JSONException {
        this.setTableConfig();
        try {
            Long whouid = userDetails.getCurrentOu().getId();
            wareHouseManager.createSnImportByBarcode(snnumber, 1, barCode, whouid);
            List<SkuSnCommand> s = skuSnManager.getSkuSnBySku(snnumber, whouid);
            request.put(JSON, toJson(s));
        } catch (Exception e) {
            request.put(JSON, ERROR);
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("createSn Exception:", e);
            };
        }
        return JSON;
    }
}
