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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.util.JsonUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.vmi.Default.TransferOwnerSource;
import com.jumbo.wms.model.vmi.Default.TransferOwnerSourceCommand;
import com.jumbo.wms.model.vmi.Default.TransferOwnerTargetCommand;

import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import net.sf.json.JSONArray;


public class SkuInvAllocationAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = 3388905243315716290L;

    /**
     * 文件导入
     */
    private File file;
    
    @Autowired
    private WareHouseManager wareHouseManager;

    private String sourceOwner;
    
    private String targetOwner;
    
    private String sourceOwnerName;

    private String targetOwnerName;

    private String priorityOwner;
    
    private Long id;
    
    private String idStr;

    private String skuCode;

    private String targetRatioStr;

    /**
     * 页面跳转
     * 
     * @return
     */
    public String pageRedirect() {
        return SUCCESS;
    }
    
    public String findTransferOwnerSource() {
        setTableConfig();
       
        Pagination<TransferOwnerSourceCommand> zoonList = wareHouseManager.findTransferOwnerSource(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId());
        request.put(JSON, toJson(zoonList));
        return JSON;
    }
    
    public String findTransferOwnerTarget() {
        setTableConfig();
        Map<String, Object> m = new HashMap<String, Object>();
        if (!StringUtil.isEmpty(sourceOwner)) {
            m.put("sourceOwner", sourceOwner);
        }
        if (!StringUtil.isEmpty(skuCode)) {
            m.put("skuCode", skuCode);
        }
        if (!StringUtil.isEmpty(targetOwner)) {
            m.put("targetOwner", targetOwner);
        }
        if (!StringUtil.isEmpty(sourceOwnerName)) {
            m.put("sourceOwnerName", sourceOwnerName);
        }
        if (!StringUtil.isEmpty(targetOwnerName)) {
            m.put("targetOwnerName", targetOwnerName);
        }
        m.put("ouId", userDetails.getCurrentOu().getId());
        Pagination<TransferOwnerTargetCommand> zoonList = wareHouseManager.findTransferOwnerTarget(tableConfig.getStart(), tableConfig.getPageSize(), m);
        request.put(JSON, toJson(zoonList));
        return JSON;
    }

    public String findTransferOwnerTargetByUpdate() {
        Map<String, Object> m = new HashMap<String, Object>();
        if (!StringUtil.isEmpty(sourceOwner)) {
            m.put("sourceOwner", sourceOwner);
        }
        if (!StringUtil.isEmpty(skuCode)) {
            m.put("skuCode", skuCode);
        }
        m.put("ouId", userDetails.getCurrentOu().getId());
        Pagination<TransferOwnerTargetCommand> zoonList = wareHouseManager.findTransferOwnerTarget(-1, -1, m);

        request.put(JSON, JsonUtil.collection2json(zoonList.getItems()));
        return JSON;
    }

    public String deleteTransferOwnerSource() throws JSONException {
        JSONObject result = new JSONObject();
       
         wareHouseManager.deleteTransferOwnerSource(id);
         result.put("flag", SUCCESS);
        request.put(JSON, result);
        return JSON;
    }
    
    public String deleteTransferOwnerTarget() throws JSONException {
        JSONObject result = new JSONObject();
        List<Long> idList = new ArrayList<Long>();

        String[] ids = idStr.split(",");
        for (int i = 0; i < ids.length; i++) {
            idList.add(Long.parseLong(ids[i]));
        }
        wareHouseManager.deleteTransferOwnerTarget(idList);
        result.put("flag", SUCCESS);
        request.put(JSON, result);
        return JSON;
    }

    public String updateTransferOwnerTarget() throws JSONException {
        JSONObject result = new JSONObject();
        List<TransferOwnerTargetCommand> totList = new ArrayList<TransferOwnerTargetCommand>();
        if (!StringUtil.isEmpty(targetRatioStr)) {
            JSONArray json = JSONArray.fromObject(targetRatioStr);
            if (json.size() > 0) {
                for (int i = 0; i < json.size(); i++) {
                    net.sf.json.JSONObject job = json.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成
                                                                        // json 对象
                    TransferOwnerTargetCommand tot = new TransferOwnerTargetCommand();
                    tot.setSkuCode(skuCode);
                    tot.setSourceOwner(sourceOwner);
                    tot.setTargetOwner(job.getString("targetOwner"));
                    tot.setTargetRatio((Integer) job.get("targetRatio"));
                    tot.setOuId(userDetails.getCurrentOu().getId());
                    totList.add(tot);
                }
                wareHouseManager.updateTransferOwnerTarget(totList);
                result.put("flag", SUCCESS);
            } else {
                result.put("flag", ERROR);
            }
        } else {
            result.put("flag", ERROR);
        }

        request.put(JSON, result);
        return JSON;
    }

    public String saveTransferOwnerSource() throws JSONException {
        JSONObject result = new JSONObject();


        if (!StringUtil.isEmpty(priorityOwner)&&!StringUtil.isEmpty(targetOwner)&&!StringUtil.isEmpty(sourceOwner)) {
            try {
                TransferOwnerSource tos=new TransferOwnerSource();
                tos.setId(id);
                tos.setOwnerSource(sourceOwner);
                tos.setPriorityOwner(priorityOwner);
                tos.setTargetOwner(targetOwner);
                tos.setOuId(userDetails.getCurrentOu().getId());
                String flag = wareHouseManager.insertTransferOwnerSource(tos);
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
    
    // 导入数据
    public String importSkuTargetOwner() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = wareHouseManager.importSkuTargetOwner(file, userDetails.getCurrentOu().getId());
                if (rs != null) {
                    if (rs.getExceptions().size() > 0) request.put("msg", rs.getExceptions().get(0).getMessage());
                }
                if (rs == null) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                } else if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
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
                request.put("msg", ERROR);
                log.error("", e);
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                log.error(e.getMessage());
                request.put("msg", sb.toString());
            } catch (Exception e) {
                request.put("msg", ERROR);
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }
    
    public String findAllSourceOwner() {
        request.put(JSON, JsonUtil.collection2json(wareHouseManager.findAllSourceOwner(userDetails.getCurrentOu().getId())));
        return JSON;
    }
    
    public String findAllTargetOwner() {
        request.put(JSON, JsonUtil.collection2json(wareHouseManager.findAllTargetOwner(sourceOwner, userDetails.getCurrentOu().getId())));
        return JSON;
    }
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getSourceOwner() {
        return sourceOwner;
    }

    public void setSourceOwner(String sourceOwner) {
        this.sourceOwner = sourceOwner;
    }

    public String getTargetOwner() {
        return targetOwner;
    }

    public void setTargetOwner(String targetOwner) {
        this.targetOwner = targetOwner;
    }

    public String getPriorityOwner() {
        return priorityOwner;
    }

    public void setPriorityOwner(String priorityOwner) {
        this.priorityOwner = priorityOwner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getTargetRatioStr() {
        return targetRatioStr;
    }

    public void setTargetRatioStr(String targetRatioStr) {
        this.targetRatioStr = targetRatioStr;
    }

    public String getSourceOwnerName() {
        return sourceOwnerName;
    }

    public void setSourceOwnerName(String sourceOwnerName) {
        this.sourceOwnerName = sourceOwnerName;
    }

    public String getTargetOwnerName() {
        return targetOwnerName;
    }

    public void setTargetOwnerName(String targetOwnerName) {
        this.targetOwnerName = targetOwnerName;
    }

    
    
}
