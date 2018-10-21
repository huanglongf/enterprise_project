package com.jumbo.web.action.automaticEquipment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.automaticEquipment.AutoBaseInfoManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.command.automaticEquipment.InboundRoleCommand;


/**
 * @author lihui
 *
 * @createDate 2016年1月20日 下午3:29:54
 */
public class InboundRoleAction extends BaseJQGridProfileAction {


    private static final long serialVersionUID = -7205089049304833423L;

    @Autowired
    private AutoBaseInfoManager autoBaseInfoManager;
	
    @Autowired
    private ExcelReadManager excelReadManager;

    private InboundRoleCommand inboundRoleCommand;
    
    private String idStr;

    private Long ouId;

    private Long popId;

    private File inboundRoleFile;


    public String initInboundRolePage() {

        return SUCCESS;
    }
    
    public String findInboundRoleByParams() {
        setTableConfig();
        Map<String, Object> params = new HashMap<String, Object>();
        if (inboundRoleCommand != null) {
            if (!StringUtil.isEmpty(inboundRoleCommand.getLocationCode())) {
                params.put("locationCode", inboundRoleCommand.getLocationCode());
            }
            if (!StringUtil.isEmpty(inboundRoleCommand.getPopUpAraeCode())) {
                params.put("targetLocation", inboundRoleCommand.getPopUpAraeCode());
            }
            if (inboundRoleCommand.getChannelId() != null) {
                params.put("channelId", inboundRoleCommand.getChannelId());
            }
            if (inboundRoleCommand.getSkuTypeId() != null) {
                params.put("skuTypeId", inboundRoleCommand.getSkuTypeId());
            }
            if (!StringUtil.isEmpty(inboundRoleCommand.getSkuCode())) {
                params.put("skuCode", inboundRoleCommand.getSkuCode());
            }
        }
        params.put("ouId", getCurrentOu().getId());

        Pagination<InboundRoleCommand> zoonList = autoBaseInfoManager.findInboundRoleByParams(tableConfig.getStart(), tableConfig.getPageSize(), params);
        request.put(JSON, toJson(zoonList));
        return JSON;
    }

    public String saveInboundRole() throws JSONException {
        JSONObject result = new JSONObject();


        if (inboundRoleCommand != null) {
            try {
                inboundRoleCommand.setOuId(getCurrentOu().getId());
                String flag = autoBaseInfoManager.saveInboundRole(inboundRoleCommand);
                if (flag == null || "".equals(flag)) {

                    result.put("flag", SUCCESS);
                } else {
                    result.put("flag", flag);
                }
            } catch (Exception e) {
                result.put("flag", ERROR);
                log.error(e.getMessage());
            }
        } else {
            result.put("flag", "信息不完整！");
        }


        request.put(JSON, result);
        return JSON;
    }

    public String updateInboundRoleByIds() throws JSONException {
        JSONObject result = new JSONObject();


        if (inboundRoleCommand != null) {
            try {
                inboundRoleCommand.setOuId(getCurrentOu().getId());
                String flag = autoBaseInfoManager.updateInboundRole(inboundRoleCommand);
                if (flag == null || "".equals(flag)) {

                    result.put("flag", SUCCESS);
                } else {
                    result.put("flag", flag);
                }
            } catch (Exception e) {
                result.put("flag", ERROR);
                log.error(e.getMessage());
            }
        } else {
            result.put("flag", "信息不完整！");
        }
        request.put(JSON, result);
        return JSON;

    }



    public String deleteInboundRoleByIds() throws JSONException {
        if (idStr != null && !"".equals(idStr)) {
            JSONObject result = new JSONObject();
            try {

                List<Long> idList = new ArrayList<Long>();
                String[] ids = idStr.split(",");
                for (int i = 0; i < ids.length; i++) {
                    idList.add(Long.parseLong(ids[i]));
                }


                autoBaseInfoManager.deleteInboundRoleByIds(idList);
                result.put("flag", SUCCESS);
                request.put(JSON, result);
            } catch (Exception e) {
                result.put("flag", ERROR);
                log.error(e.getMessage());
            }
        }
        return JSON;

    }

    /**
     * 根据自动化仓库获取店铺
     * 
     * @return
     */
    public String findChannelByAutoWh() {
        request.put(JSON, JsonUtil.collection2json(autoBaseInfoManager.findChannelByAutoWh(getCurrentOu().getId())));
        return JSON;
    }


    /**
     * 根据弹出口获取库位
     * 
     * @return
     */
    public String findLocationByZoon() {
        request.put(JSON, JsonUtil.collection2json(autoBaseInfoManager.findLocationByZoon(ouId, popId)));
        return JSON;
    }

    public String findSkuTypeJson() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", 0);
        request.put(JSON, JsonUtil.collection2json(autoBaseInfoManager.findSkuTypeByParams(-1, -1, params).getItems()));
        return JSON;
    }

    public String inboundRoleImport() {
        request.put("result", SUCCESS);
        if (inboundRoleFile == null) {
            log.error(" The upload file must not be null");
            request.put("msg", " The upload file must not be null");
        } else {
            try {
                ReadStatus rs = excelReadManager.importInboundRole(inboundRoleFile, getCurrentOu().getId());
                if (rs.getStatus() < 1) {
                    request.put("msg", SUCCESS);
                } else {
                    request.put("msg", rs.getMessage());
                }
            } catch (Exception e) {
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }


    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public AutoBaseInfoManager getAutoBaseInfoManager() {
        return autoBaseInfoManager;
    }

    public void setAutoBaseInfoManager(AutoBaseInfoManager autoBaseInfoManager) {
        this.autoBaseInfoManager = autoBaseInfoManager;
    }

    public InboundRoleCommand getInboundRoleCommand() {
        return inboundRoleCommand;
    }

    public void setInboundRoleCommand(InboundRoleCommand inboundRoleCommand) {
        this.inboundRoleCommand = inboundRoleCommand;
    }

    public File getInboundRoleFile() {
        return inboundRoleFile;
    }

    public void setInboundRoleFile(File inboundRoleFile) {
        this.inboundRoleFile = inboundRoleFile;
    }

    
   
}
