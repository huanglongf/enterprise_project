/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.web.action.authorization;

import java.util.ArrayList;
import java.util.List;

import loxia.support.json.JSONArray;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import com.jumbo.Constants;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseProfileAction;
import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.PhysicalWarehouse;

/**
 * 
 * @author wanghua
 * 
 */
@SuppressWarnings("deprecation")
public class OperationUnitAction extends BaseProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 545620897952892021L;
    @Autowired
    private AuthorizationManager authorizationManager;

    @Autowired
    private WareHouseManager wareHouseManager;

    @Autowired
    private DropDownListManager dropDownListManager;
    /**
     * 可用组织类型下拉列表
     */
    private List<OperationUnitType> opeUnitType;
    /**
     * 状态下拉列表
     */
    private List<ChooseOption> statusOptionList;
    /**
     * 被修改的operationUnit/新增的operationUnit
     */
    private OperationUnit operationUnit;
    private Boolean editAble;

    @Autowired
    private WareHouseManagerQuery whQuery;

    /**
     * Jsp页面跳转
     */
    public String execute() throws Exception {
        opeUnitType = dropDownListManager.findOperationUnitTypeList(null);
        statusOptionList = dropDownListManager.findStatusChooseOptionList();
        return SUCCESS;
    }

	public String view() throws Exception {
        editAble = userDetails.getAuthorities().contains(new GrantedAuthorityImpl(Constants.PRIVILEGE_ACL_ROOT_OU_MAINTAIN));
        return execute();
    }

    public String manage() throws Exception {
        return execute();
    }

    /**
     * 构造Json组织树
     * 
     * @return
     * @throws Exception
     */
    public String operationUnitTree() throws Exception {
        JSONArray result = null;
        try {
            result = rebuild(authorizationManager.findOperationUnitListSql());
        } catch (Exception e) {
            log.error("", e);
        }

        request.put(JSON, result);
        return JSON;
    }

    /**
     * List转为JSONArray
     * 
     * @param list
     * @return
     * @throws Exception
     */
    private JSONArray rebuild(List<OperationUnit> list) throws Exception {
        List<JSONObject> listJson = new ArrayList<JSONObject>();
        for (OperationUnit each : list) {
            listJson.add(ouToJson(each));
        }
        return new JSONArray(listJson);
    }

    /**
     * 把OperationUnit转为前台需要的JSON格式
     * 
     * @param obj
     * @return
     * @throws Exception
     */
    private JSONObject ouToJson(OperationUnit obj) throws Exception {
        JSONObject json = new JSONObject();
        json.put("id", obj.getId());
        json.put("text", obj.getName() + "(" + obj.getOuType().getName() + ")-" + obj.getCode());
        JSONObject attributes = new JSONObject();
        attributes.put("name", obj.getName());
        attributes.put("type", obj.getOuType().getDisplayName());
        attributes.put("code", obj.getCode());
        attributes.put("available", obj.getIsAvailable());
        attributes.put("comment", obj.getComment());
        json.put("attributes", attributes);
        if (obj.getChildrenUnits() != null && obj.getChildrenUnits().size() > 0) {
            json.put("children", rebuild(obj.getChildrenUnits()));
        }
        return json;
    }


    /**
     * 修改OperationUnit
     * 
     * @return
     * @throws Exception
     */
    public String updateOperationUnit() throws Exception {
        JSONObject json = new JSONObject();
        authorizationManager.updateOperationUnit(operationUnit);
        json.put("id", operationUnit.getId());
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 通过节点查找该组织类型的子组织类型
     * 
     * @return
     * @throws Exception
     */
    public String getOUTByNode() throws Exception {
        Long OUID = operationUnit.getId();
        operationUnit = authorizationManager.getOUByPrimaryKey(OUID);
        Long outId = operationUnit.getOuType().getId();
        opeUnitType = dropDownListManager.findChildOUPList(outId);
        request.put(JSON, new JSONArray(opeUnitType));
        return JSON;
    }

    /**
     * 根据OUID获取当前仓库
     * 
     * @return
     * @throws Exception
     */
    public String getWHByOuId() throws Exception {
        JSONObject json = new JSONObject();
        Warehouse warehouse = wareHouseManager.getByOuId(userDetails.getCurrentOu().getId());
        json.put("warehouse", JsonUtil.obj2json(warehouse));
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 新增operationUnit
     * 
     * @return
     * @throws Exception
     */
    public String addOperationUnit() throws Exception {
        JSONObject json = new JSONObject();
        OperationUnit ou = authorizationManager.createOperationUnit(operationUnit);
        json.put("id", ou.getId());
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 获取所有仓库
     * 
     * @return
     */
    public String findAllWhByType() {
        JSONObject result = new JSONObject();
        JSONArray ja = new JSONArray();
        try {
            List<OperationUnit> wareList = whQuery.fandOperationUnitsByType(OperationUnitType.OUTYPE_WAREHOUSE);
            for (int i = 0; i < wareList.size(); i++) {
                OperationUnit ou = wareList.get(i);
                JSONObject jo = new JSONObject();
                jo.put("name", ou.getName());
                jo.put("id", ou.getId());
                ja.put(jo);
            }
            result.put("warelist", ja);
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 下拉选择获取仓库
     * 
     * @return
     * @throws Exception
     */
    public String findAllWhByChoose() throws Exception {
        JSONObject json = new JSONObject();
        StringBuilder sb = new StringBuilder();
        List<PhysicalWarehouse> pwlist = authorizationManager.findAllPhyWarehouse();
        if (pwlist != null && !pwlist.isEmpty()) {
            for (PhysicalWarehouse tc : pwlist) {
                sb.append((sb.length() > 0 ? ";" : "") + tc.getId() + ":" + tc.getName());
            }
        }
        json.put("value", sb.toString());
        request.put(JSON, json);
        return JSON;
    }


    public String getDivision() {
        request.put(JSON, JsonUtil.collection2json(authorizationManager.findDivisionList(userDetails.getCurrentOu().getId())));
        return JSON;
    }

    public List<OperationUnitType> getOpeUnitType() {
        return opeUnitType;
    }

    public void setOpeUnitType(List<OperationUnitType> opeUnitType) {
        this.opeUnitType = opeUnitType;
    }

    public OperationUnit getOperationUnit() {
        return operationUnit;
    }

    public void setOperationUnit(OperationUnit operationUnit) {
        this.operationUnit = operationUnit;
    }

    public Boolean getEditAble() {
        return editAble;
    }

    public void setEditAble(Boolean editAble) {
        this.editAble = editAble;
    }

    public List<ChooseOption> getStatusOptionList() {
        return statusOptionList;
    }

    public void setStatusOptionList(List<ChooseOption> statusOptionList) {
        this.statusOptionList = statusOptionList;
    }

}
