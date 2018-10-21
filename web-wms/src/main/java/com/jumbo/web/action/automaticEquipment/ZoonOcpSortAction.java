package com.jumbo.web.action.automaticEquipment;

import java.util.ArrayList;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.pickZone.NewInventoryOccupyManager;
import com.jumbo.wms.model.automaticEquipment.Zoon;
import com.jumbo.wms.model.automaticEquipment.ZoonOcpSort;
import com.jumbo.wms.model.warehouse.StockTransApplicationPickingType;

/**
 * 订单占用区域维护
 * 
 * @author jinggang.chen
 * 
 */
public class ZoonOcpSortAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = -8212418448095046275L;
    @Autowired
    private NewInventoryOccupyManager occupyManager;

    private ZoonOcpSort zoonOcpSort;

    private String zoonCode;// 仓库区域编码

    private Long id;// 主键id

    public String ocpinvManager() {
        return SUCCESS;
    }

    public String findzoonOcpSortList() {
        setTableConfig();
        Pagination<ZoonOcpSort> list = occupyManager.findZoonOcpSortListByZoonCodeAndOuid(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), userDetails.getCurrentOu().getId(), zoonCode);
        List<ZoonOcpSort> list1 = new ArrayList<ZoonOcpSort>();
        for (ZoonOcpSort zoonOcpSort : list.getItems()) {
            String salemadel = "";
            if (zoonOcpSort.getSaleModle().equals("1")) {
                salemadel = "单件";
            } else if (zoonOcpSort.getSaleModle().equals("10")) {
                salemadel = "多件";
            } else if (zoonOcpSort.getSaleModle().equals("20")) {
                salemadel = "团购";
            } else if (zoonOcpSort.getSaleModle().equals("30")) {
                salemadel = "秒杀";
            } else if (zoonOcpSort.getSaleModle().equals("2")) {
                salemadel = "套装组合";
            } else if (zoonOcpSort.getSaleModle().equals("3")) {
                salemadel = "O2OQS";
            } else {
                salemadel = "预售";
            }
            zoonOcpSort.setSaleModle(salemadel);
            list1.add(zoonOcpSort);
        }
        list.setItems(list1);
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 获取仓库区域下拉列表
     * 
     * @return
     */
    public String getZoonList() {
        List<Zoon> zoonList = occupyManager.getZoonList(userDetails.getCurrentOu().getId());
        request.put(JSON, JsonUtil.collection2json(zoonList));
        return JSON;
    }

    public String saveOrUpdateZoonOcpSort() throws JSONException {
        JSONObject ob = new JSONObject();
        String result = null;
        try {
            result = occupyManager.updateZoonOcpSort(zoonOcpSort, userDetails.getCurrentOu().getId());
            if ("".equals(result)) {
                ob.put("msg", "success");
            } else {
                ob.put("msg", result);
            }
        } catch (Exception e) {
            ob.put("msg", "优先级已存在");
        }
        request.put(JSON, ob);
        return JSON;
    }


    public String deleteZoonOcpSortById() {
        JSONObject ob = new JSONObject();
        try {
            occupyManager.deleteZoonOcpSort(id);
            ob.put("msg", "success");
        } catch (Exception e) {
            log.error("deleteZoonOcpSortById is error :" + e);
        }
        request.put(JSON, ob);
        return JSON;
    }

    public ZoonOcpSort getZoonOcpSort() {
        return zoonOcpSort;
    }

    public void setZoonOcpSort(ZoonOcpSort zoonOcpSort) {
        this.zoonOcpSort = zoonOcpSort;
    }

    public String getZoonCode() {
        return zoonCode;
    }

    public void setZoonCode(String zoonCode) {
        this.zoonCode = zoonCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
