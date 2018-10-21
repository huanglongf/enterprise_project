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
package com.jumbo.web.action.warehouse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.command.EspritStoreCommand;

public class EspritStoreAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1971577527768941019L;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private ExcelReadManager excelReadManager;

    private EspritStoreCommand espritCommand;

    private String idStr;

    private File file;


    private String lpCode;// 物流商

    private String owner;// 店铺code

    private Long staId;// staId


    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public String initEspritStorePage() {
        return SUCCESS;
    }


    public String vmiReturnCreateEsprit() {
        return SUCCESS;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    /*
     * 分页查询 esprit 门店信息
     */
    public String findEspritStoreByParams() {
        setTableConfig();
        Pagination<EspritStoreCommand> list = wareHouseManager.findEspritStoreByParams(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), espritCommand, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }


    // 根据staId来获取 sta物流信息
    public String getStaDel() throws JSONException {
        String msg = null;
        JSONObject result = new JSONObject();
        try {
            msg = wareHouseManager.getStaDel(staId);
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("getStaDel error:" + staId, e);
            };
            msg = "3";// 失败
        }
        result.put("msg", msg);
        request.put(JSON, result);
        return JSON;
    }

    // 保存esprit 门店信息
    public String saveEspritStore() throws JSONException {
        String msg = "success";
        String msg2 = "";
        JSONObject result = new JSONObject();
        espritCommand.setModifyUser((userDetails.getUser()));
        try {
            msg2 = wareHouseManager.saveEsprit(espritCommand);
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("saveEspritStore error:", e);
            };
            msg = "error";
        }
        result.put("msg", msg);
        result.put("msg2", msg2);
        request.put(JSON, result);
        return JSON;
    }

    // 删除esprit 门店信息
    public String delEspritStore() throws JSONException {
        List<Long> idList = new ArrayList<Long>();
        if (idStr != null && !"".equals(idStr)) {
            String[] ids = idStr.split(",");
            for (int i = 0; i < ids.length; i++) {
                idList.add(Long.parseLong(ids[i]));
            }
        }
        String msg = "success";
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.delEsprit(idList);
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("delEspritStore error:", e);
            };
            msg = "error";
        }
        result.put("msg", msg);
        request.put(JSON, result);
        return JSON;
    }


    // 创建 - 导入 esprit转店退仓
    public String vmiReturnEspritImport() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                Long cmpOuid = userDetails.getCurrentOu().getParentUnit().getParentUnit().getId();
                ReadStatus rs = excelReadManager.creStaForVMIReturnEs(owner, lpCode, file, null, null, userDetails.getCurrentOu().getId(), cmpOuid, userDetails.getUser().getId(), null, true, true);
                if (rs != null) if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
                    request.put("msg", "");
                    List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                    request.put("msg", list);
                } else if (rs.getStatus() < ReadStatus.STATUS_SUCCESS) {
                    request.put("msg", "");
                    List<String> list = new ArrayList<String>();
                    for (Exception ex : rs.getExceptions()) {
                        if (ex instanceof BusinessException) {
                            BusinessException be = (BusinessException) ex;
                            list.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()) + Constants.HTML_LINE_BREAK);
                        }
                    }
                    request.put("msg", list);
                }
            } catch (BusinessException e) {
                request.put("msg", "error");
                log.error("", e);
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                sb.append(e.getMessage());
                log.error(e.getMessage());
                request.put("msg", sb.toString());
            } catch (Exception e) {
                request.put("msg", "error");
                // e.printStackTrace();
                if (log.isErrorEnabled()) {
                    log.error("vmiReturnEspritImport error:", e);
                };
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }


    public EspritStoreCommand getEspritCommand() {
        return espritCommand;
    }

    public void setEspritCommand(EspritStoreCommand espritCommand) {
        this.espritCommand = espritCommand;
    }

    public WareHouseManager getWareHouseManager() {
        return wareHouseManager;
    }

    public void setWareHouseManager(WareHouseManager wareHouseManager) {
        this.wareHouseManager = wareHouseManager;
    }
}
