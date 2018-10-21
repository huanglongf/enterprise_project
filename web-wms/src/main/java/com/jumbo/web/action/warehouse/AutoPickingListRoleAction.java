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
 */
package com.jumbo.web.action.warehouse;

import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.warehouse.AutoPickingListRoleManager;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.command.TransportatorCommand;
import com.jumbo.wms.model.warehouse.AutoPickingListRole;
import com.jumbo.wms.model.warehouse.AutoPickingListRoleCommand;
import com.jumbo.wms.model.warehouse.AutoPickingListRoleDetailCommand;

/**
 * @author lichuan
 * 
 */
public class AutoPickingListRoleAction extends BaseJQGridProfileAction {
    /**
     * 
     */
    private static final long serialVersionUID = -3519358194762787531L;
    @Autowired
    private AutoPickingListRoleManager autoPickingListRoleManager;
    private Integer status;
    private AutoPickingListRole role;
    private List<AutoPickingListRoleDetailCommand> roleDetailList;
    private String lpCode;
    private String lpName;
    private Integer isCod;

    public String manager() {
        return SUCCESS;
    }

    public String findAutoPLRoleByPagination() {
        setTableConfig();
        try {
            Pagination<AutoPickingListRoleCommand> roleList = autoPickingListRoleManager.findAutoPLRoleByPagination(role, status, tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts());
            if (null != roleList)
                request.put(JSON, toJson(roleList));
            else {
                JSONObject result = new JSONObject();
                result.put("result", NONE);
                request.put(JSON, result);
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        return JSON;
    }

    public String findAutoPLRoleExistByCode() {
        try {
            String code = null;
            if (null != role) {
                code = role.getCode();
            }
            boolean result = autoPickingListRoleManager.findAutoPLRoleExistByCode(code);
            JSONObject ret = new JSONObject();
            ret.put("result", result);
            request.put(JSON, ret);
        } catch (JSONException e) {
            log.error("", e);
        }
        return JSON;
    }

    public String saveAutoPLRole() throws JSONException {
        JSONObject ret = new JSONObject();
        try {
            autoPickingListRoleManager.saveAutoPLRole(role, status);
            ret.put("msg", "success");
        } catch (Exception e) {
            ret.put("msg", "error");
            log.error("", e);
        }
        request.put(JSON, ret);
        return JSON;
    }

    public String findAutoPLRoleByCode() {
        String code = role.getCode();
        AutoPickingListRole role = autoPickingListRoleManager.findAutoPLRoleByCode(code);
        request.put(JSON, JsonUtil.obj2json(role));
        return JSON;
    }

    public String findAllPLRoleDetailByRoleId() {
        setTableConfig();
        Long roleId = -1L;
        if (null != role) {
            roleId = role.getId();
        }
        try {
            Pagination<AutoPickingListRoleDetailCommand> roleDetailList = autoPickingListRoleManager.findAllPLRoleDetailByRoleId(roleId, tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts());
            if (null != roleDetailList)
                request.put(JSON, toJson(roleDetailList));
            else {
                JSONObject result = new JSONObject();
                result.put("result", NONE);
                request.put(JSON, result);
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        return JSON;
    }

    public String saveAutoPLRoleDetail() throws JSONException {
        JSONObject ret = new JSONObject();
        try {
            autoPickingListRoleManager.saveAutoPLRoleDetail(role, status, roleDetailList);
            ret.put("msg", "success");
        } catch (Exception e) {
            ret.put("msg", "error");
            log.error("", e);
        }
        request.put(JSON, ret);
        return JSON;
    }

    /**
     * 获取混合创建物流商
     */
    public String findTransportatorList() {
        setTableConfig();
        List<Transportator> tList = autoPickingListRoleManager.findTransportatorList();
        request.put(JSON, toJson(tList));
        return JSON;
    }

    /**
     * 获取所有物流商除SFCOD JDCOD EMSCOD
     */
    public String findTransportatorListAll() {
        setTableConfig();
        List<Transportator> tList = autoPickingListRoleManager.findTransportatorListAll();
        request.put(JSON, toJson(tList));
        return JSON;
    }

    /**
     * 获取所有物流商
     */
    public String findTransportatorListByAll() {
        setTableConfig();
        List<TransportatorCommand> tList = autoPickingListRoleManager.findTransportatorListByAll(lpCode, lpName, status, isCod);
        request.put(JSON, toJson(tList));
        return JSON;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public AutoPickingListRole getRole() {
        return role;
    }

    public void setRole(AutoPickingListRole role) {
        this.role = role;
    }

    public List<AutoPickingListRoleDetailCommand> getRoleDetailList() {
        return roleDetailList;
    }

    public void setRoleDetailList(List<AutoPickingListRoleDetailCommand> roleDetailList) {
        this.roleDetailList = roleDetailList;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getLpName() {
        return lpName;
    }

    public void setLpName(String lpName) {
        this.lpName = lpName;
    }

    public Integer getIsCod() {
        return isCod;
    }

    public void setIsCod(Integer isCod) {
        this.isCod = isCod;
    }

}
