package com.jumbo.web.action.warehouse;

import java.util.List;

import loxia.support.json.JSONArray;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.PhysicalWarehouse;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 
 * @author jinlong.ke
 * 
 */
public class WareHouseToVirtualAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -924939742024655074L;
    @Autowired
    private AuthorizationManager authorizationManager;
    private OperationUnit operUnit;
    private PhysicalWarehouse phWarehouse;
    private List<Long> list;

    /**
     * 初始化跳转页面，跳转到对应的jsp页面
     * 
     * @return
     */
    public String wareHouseToVirtual() {
        return SUCCESS;
    }

    /**
     * 得到所有公司基本信息
     * 
     * @return
     */
    public String findAllCompany() {
        JSONObject json = new JSONObject();
        try {
            json.put("comlist", authorizationManager.selectAllCompany());
        } catch (Exception e) {
            log.debug("WareHouseToVirtualAction.findAllCompany.exception");
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 根据公司得到运营中心
     * 
     * @return
     */
    public String findCenterByCompany() {
        JSONObject json = new JSONObject();
        try {
            Long comid = operUnit.getId();
            json.put("cenlist", authorizationManager.selectCenterByCompanyId(comid));
        } catch (Exception e) {
            log.error("WareHouseToVirtualAction.findCenterByCompany.exception");
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 根据运营中心查看仓库
     * 
     * @return
     */
    public String findWarehouseByCenter() {
        JSONObject json = new JSONObject();
        JSONArray ja = new JSONArray();
        try {
            Long cenid = operUnit.getId();
            List<OperationUnit> wareList = authorizationManager.selectWarehouseByCenId(cenid);
            for (int i = 0; i < wareList.size(); i++) {
                OperationUnit ou = wareList.get(i);
                JSONObject jo = new JSONObject();
                jo.put("name", ou.getName());
                jo.put("id", ou.getId());
                ja.put(jo);
            }
            json.put("warelist", ja);
        } catch (Exception e) {
            log.error("WareHouseToVirtualAction.findWarehouseByCenter.exception");
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 创建新物理仓
     * 
     * @return
     * @throws Exception
     */
    public String newPhWareHouse() {
        JSONObject json = new JSONObject();
        try {
            authorizationManager.savePhysicalWareHouse(phWarehouse);
            json.put("rs", true);
        } catch (Exception e) {
            log.error("WareHouseToVirtualAction.newPhWareHouse.exception");
            try {
                json.put("rs", false);
            } catch (JSONException e1) {
                log.error("WareHouseToVirtualAction.newPhWareHouse.exception");
            }
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 查询现有物理仓
     * 
     * @return
     */
    public String selectAllPhyWarehouse() {
        JSONObject json = new JSONObject();
        try {
            json.put("pwarelist", authorizationManager.selectAllPhyWarehouse());
        } catch (Exception e) {
            log.error("WareHouseToVirtualAction.selectAllPhyWarehouse.exception");
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 查询虚拟仓是否已关联
     * 
     * @return
     */
    public String findOuidByPhyware() {
        JSONObject json = new JSONObject();
        // List<Long> l1 =list;
        // Long phid = phWarehouse.getId();
        try {
            Boolean b = authorizationManager.ifExistVirtual(list);
            log.error("--------------------------------------" + b);
            if (authorizationManager.ifExistVirtual(list)) {
                json.put("rs", false);
                request.put(JSON, json);
                return JSON;
            }
            json.put("rs", true);
        } catch (JSONException e) {
            log.error("WareHouseToVirtualAction.findOuidByPhyware.JSONException");
        } catch (Exception e) {
            log.error("",e);
            log.error("WareHouseToVirtualAction.findOuidByPhyware.exception");
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 根据仓库查询所有的虚拟仓
     * 
     * @return
     */
    public String selectAllWarehouseByPhyId() {
        JSONObject json = new JSONObject();
        Long phyid = phWarehouse.getId();
        try {
            json.put("phywarelist", authorizationManager.selectAllWarehouseByPhyId(phyid));
        } catch (JSONException e) {
            log.error("",e);
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 保存物理仓对应虚拟仓关系
     * 
     * @return
     */
    public String saveRelationShip() {
        JSONObject json = new JSONObject();
        Long phid = phWarehouse.getId();
        boolean b = authorizationManager.saveRelationShip(phid, list);
        try {
            if (b) {
                json.put("rs", true);
            } else {
                json.put("rs", false);
            }
        } catch (JSONException e) {
            log.error("WareHouseToVirtualAction.saveRelationShip.exception");
        }
        request.put(JSON, json);
        return JSON;
    }

    public OperationUnit getOperUnit() {
        return operUnit;
    }

    public void setOperUnit(OperationUnit operUnit) {
        this.operUnit = operUnit;
    }

    public PhysicalWarehouse getPhWarehouse() {
        return phWarehouse;
    }

    public void setPhWarehouse(PhysicalWarehouse phWarehouse) {
        this.phWarehouse = phWarehouse;
    }

    public List<Long> getList() {
        return list;
    }

    public void setList(List<Long> list) {
        this.list = list;
    }
}
