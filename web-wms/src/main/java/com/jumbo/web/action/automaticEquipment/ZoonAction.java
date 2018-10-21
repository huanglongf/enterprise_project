package com.jumbo.web.action.automaticEquipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.automaticEquipment.AutoBaseInfoManager;
import com.jumbo.wms.model.command.automaticEquipment.ZoonCommand;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;


/**
 * @author lihui
 *
 * @createDate 2016年1月20日 下午3:29:54
 */
public class ZoonAction extends BaseJQGridProfileAction {


    private static final long serialVersionUID = 4135139310496846146L;
    @Autowired
    private AutoBaseInfoManager autoBaseInfoManager;
	
    private ZoonCommand zoonCommand;
    
    private String idStr;

    private Boolean status;

    private Long ouId;

    private Boolean valid = false;

    public String initZoonPage() {

        return SUCCESS;
    }
    
    public String findZoonByParams() {
        setTableConfig();
        Map<String, Object> params = new HashMap<String, Object>();
        if (zoonCommand != null) {
            if (!"".equals(zoonCommand.getCode())) {
                params.put("code", zoonCommand.getCode());
            }
            if (!"".equals(zoonCommand.getName())) {
                params.put("name", zoonCommand.getName());
            }
        }

        params.put("ouId", getCurrentOu().getId());
        Pagination<ZoonCommand> zoonList = autoBaseInfoManager.findZoonByParams(tableConfig.getStart(), tableConfig.getPageSize(), params);
        request.put(JSON, toJson(zoonList));
        return JSON;
    }


    public String findZoonByParams2() {
        setTableConfig();
        List<ZoonCommand> zoonList = autoBaseInfoManager.findZoonByParams2(getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(zoonList));
        return JSON;
    }
    
    public String findAllDistrictByOuId(){
        setTableConfig();
        List<WarehouseDistrict> zoonList = autoBaseInfoManager.findAllDistrictByOuId(getCurrentOu().getId());
        request.put(JSON, toJson(zoonList));
        return JSON;
    }

    public String saveZoon() throws JSONException {
        JSONObject result = new JSONObject();


        if (zoonCommand != null) {
            try {
                zoonCommand.setOuId(getCurrentOu().getId());
                String flag = autoBaseInfoManager.saveZoon(zoonCommand);
                if (flag == null) {

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

    public String updateZoonByIds() throws JSONException {
        if (idStr != null && !"".equals(idStr)) {
            JSONObject result = new JSONObject();
            try {

                Map<String, Object> params = new HashMap<String, Object>();
                List<Long> idList = new ArrayList<Long>();

                String[] ids = idStr.split(",");
                for (int i = 0; i < ids.length; i++) {
                    idList.add(Long.parseLong(ids[i]));
                }

                params.put("status", status);
                autoBaseInfoManager.updateZoonByIds(params, idList);
                result.put("flag", SUCCESS);
                request.put(JSON, result);
            } catch (Exception e) {
                result.put("flag", ERROR);
                log.error(e.getMessage());
            }
        }
        return JSON;

    }

    public String findZoonByOuId() {
        Map<String, Object> params = new HashMap<String, Object>();
        if (ouId != null) {
                params.put("ouId", zoonCommand.getOuId());
        } else {
            params.put("ouId", getCurrentOu().getId());
        }
        if (valid) {
            params.put("valid", "valid");
        }

        Pagination<ZoonCommand> zoonList = autoBaseInfoManager.findZoonByParams(-1, -1, params);

        request.put(JSON, JsonUtil.collection2json(zoonList.getItems()));
        return JSON;
    }

    

    /**
     * 获取自动化仓库
     * 
     * @return
     */
    public String findAutoWh() {
        request.put(JSON, JsonUtil.collection2json(autoBaseInfoManager.findAutoWh()));
        return JSON;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ZoonCommand getZoonCommand() {
        return zoonCommand;
    }

    public void setZoonCommand(ZoonCommand zoonCommand) {
        this.zoonCommand = zoonCommand;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
   
}
