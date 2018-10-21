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

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.automaticEquipment.AutoBaseInfoManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.automaticEquipment.SkuType;


/**
 * @author lihui
 *
 * @createDate 2016年1月20日 下午3:29:54
 */
public class SkuTypeAction extends BaseJQGridProfileAction {


    private static final long serialVersionUID = 4135139310496846146L;
    @Autowired
    private AutoBaseInfoManager autoBaseInfoManager;
    @Autowired
    private ExcelReadManager excelReadManager;

    private SkuType skuType;

    private String idStr;

    private Boolean status;

    private File skuTypeImportFile;

    private File skuSpTypeImportFile;

    private File channelSkuSpTypeImportFile;



    public File getChannelSkuSpTypeImportFile() {
        return channelSkuSpTypeImportFile;
    }

    public void setChannelSkuSpTypeImportFile(File channelSkuSpTypeImportFile) {
        this.channelSkuSpTypeImportFile = channelSkuSpTypeImportFile;
    }

    public File getSkuSpTypeImportFile() {
        return skuSpTypeImportFile;
    }

    public void setSkuSpTypeImportFile(File skuSpTypeImportFile) {
        this.skuSpTypeImportFile = skuSpTypeImportFile;
    }

    public String initSkuTypeByPage() {

        return SUCCESS;
    }

    public String findSkuTypeByParams() {
        setTableConfig();
        Map<String, Object> params = new HashMap<String, Object>();
        if (skuType != null) {
            if (skuType.getName() != null && !"".equals(skuType.getName())) {
                params.put("name", skuType.getName());
            }
        }

        Pagination<SkuType> skuTypeList = autoBaseInfoManager.findSkuTypeByParams(tableConfig.getStart(), tableConfig.getPageSize(), params);
        request.put(JSON, toJson(skuTypeList));
        return JSON;
    }

    public String saveSkuType() throws JSONException {
        JSONObject result = new JSONObject();


        if (skuType != null) {
            try {
                String msg = autoBaseInfoManager.saveSkuType(skuType);
                if ("".equals(msg)) {
                    result.put("flag", SUCCESS);
                } else {
                    result.put("flag", msg);
                }
            } catch (Exception e) {
                result.put("flag", ERROR);
                log.error(e.getMessage());
            }
        } else {
            result.put("flag", ERROR);
        }


        request.put(JSON, result);
        return JSON;
    }

    public String updateSkuType() throws JSONException {
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
                autoBaseInfoManager.updateSkuType(params, idList);
                result.put("flag", SUCCESS);
                request.put(JSON, result);
            } catch (Exception e) {
                result.put("flag", ERROR);
                log.error(e.getMessage());
            }
        }
        return JSON;

    }

    public String skuTypeImport() {
        request.put("result", SUCCESS);
        if (skuTypeImportFile == null) {
            log.error(" The upload file must not be null");
            request.put("msg", " The upload file must not be null");
        } else {
            try {
                ReadStatus rs = excelReadManager.importSkuType(skuTypeImportFile);
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

    public String channelSkuSpTypeImport() {
        request.put("result", SUCCESS);
        if (channelSkuSpTypeImportFile == null) {
            log.error(" The upload file must not be null channelSkuSpTypeImportFile");
            request.put("msg", " The upload file must not be null");
        } else {
            try {
                ReadStatus rs = excelReadManager.channelSkuSpTypeImport(channelSkuSpTypeImportFile);
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

    public String skuSpTypeImport() {
        request.put("result", SUCCESS);
        if (skuSpTypeImportFile == null) {
            log.error(" The upload file must not be null skuSpTypeImport");
            request.put("msg", " The upload file must not be null");
        } else {
            try {
                ReadStatus rs = excelReadManager.importSkuSpType(skuSpTypeImportFile);
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

    // 跳转到特殊 商品作废页
    public String particularcommdycancle() {

        return SUCCESS;
    }

    public SkuType getSkuType() {
        return skuType;
    }

    public void setSkuType(SkuType skuType) {
        this.skuType = skuType;
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

    public File getSkuTypeImportFile() {
        return skuTypeImportFile;
    }

    public void setSkuTypeImportFile(File skuTypeImportFile) {
        this.skuTypeImportFile = skuTypeImportFile;
    }



}
