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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.security.UserDetails;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.TransRoleAccordManager;
import com.jumbo.wms.model.trans.TransRoleAccordCommand;

/**
 * 渠道快递规则状态变更
 * 
 * @author sj
 * 
 */
public class TransRoleAccordAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = -4130724244310328034L;
    private File file;
    private Long id;
    private String changeTime;
    private Long priority;
    private String lastOptionTime;
    private Long roleIsAvailable;
    protected UserDetails userDetails;
    private TransRoleAccordCommand trac;
    @Autowired
    private TransRoleAccordManager transRoleAccordManager;


    /**
     * 获取渠道快递规则状态变更列表
     * 
     * @return
     */
    public String findTransRoleAccord() {
        setTableConfig();
        Pagination<TransRoleAccordCommand> transRoleAccordList = transRoleAccordManager.findTransRoleAccord(tableConfig.getStart(), tableConfig.getPageSize(), trac, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(transRoleAccordList));
        return JSON;
    }

    /**
     * 保存修改的渠道快递规则状态
     * 
     * @return
     */
    public String saveTransRoleAccord() {
        JSONObject result = new JSONObject();
        lastOptionTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        try {
            result.put("success", ERROR);
            transRoleAccordManager.updateTransRoleAccord(id, FormatUtil.getDate(changeTime), roleIsAvailable, priority, FormatUtil.getDate(lastOptionTime), userDetails.getUser().getId());
            result.put("success", SUCCESS);
        } catch (JSONException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("saveTransRoleAccord Exception:", e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 文件导入
     * 
     * @return
     */
    public String importTransRoleAccord() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = transRoleAccordManager.transRoleAccordImport(file, userDetails.getUser().getId());
                if (rs != null) {
                    if (rs.getExceptions().size() > 0) request.put("msg", rs.getExceptions().get(0).getMessage());
                }
                if (rs == null) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                } else if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
                    request.put("result", ERROR);
                    request.put("msg", "");
                    List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                    request.put("msg", list);
                } else if (rs.getStatus() < ReadStatus.STATUS_SUCCESS) {
                    request.put("result", ERROR);
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
                request.put("result", ERROR);
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
                request.put("result", ERROR);
                request.put("msg", ERROR);
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }


    public TransRoleAccordCommand getTrac() {
        return trac;
    }

    public void setTrac(TransRoleAccordCommand trac) {
        this.trac = trac;
    }


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public String getLastOptionTime() {
        return lastOptionTime;
    }

    public void setLastOptionTime(String lastOptionTime) {
        this.lastOptionTime = lastOptionTime;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public Long getRoleIsAvailable() {
        return roleIsAvailable;
    }

    public void setRoleIsAvailable(Long roleIsAvailable) {
        this.roleIsAvailable = roleIsAvailable;
    }
}
