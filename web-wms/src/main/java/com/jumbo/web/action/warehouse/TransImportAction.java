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
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.expressDelivery.TransManager;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.warehouse.WhTransAreaNoCommand;
import com.jumbo.wms.model.warehouse.WhTransProvideNoCommand;

/**
 * 
 * @author sjk
 * 
 */
public class TransImportAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = -4130724244310328034L;

    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private TransManager transManager;
    private File file;
    private String lpcode; // 物流商
    private Long updateMode;// 1:部分更新，2：全部更新
    private WhTransProvideNoCommand whTransProvideNoCommand;
    private WhTransAreaNoCommand whTransAreaNoCommand;
    private List<Long> ids; // 删除ID集合
    private String jcustid; // SF月结账号
    private Boolean checkboxSf; // 是否SF子母单

    public String getJcustid() {
        return jcustid;
    }

    public void setJcustid(String jcustid) {
        this.jcustid = jcustid;
    }

    public Boolean getCheckboxSf() {
        return checkboxSf;
    }

    public void setCheckboxSf(Boolean checkboxSf) {
        this.checkboxSf = checkboxSf;
    }

    /***
     * 返回所有物流商
     * 
     * @return
     * @throws JSONException
     */
    public String getAllTrans() {
        List<Transportator> list = baseinfoManager.findAllTransportator();
        request.put(JSON, JsonUtil.collection2json(list));
        return JSON;
    }

    public String findProvideNo() {
        return SUCCESS;
    }

    public String findLpCode() {
        return SUCCESS;
    }

    public String findProvide() {
        setTableConfig();
        Pagination<WhTransProvideNoCommand> pro = transManager.findProvide(tableConfig.getStart(), tableConfig.getPageSize(), whTransProvideNoCommand, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(pro));
        return JSON;
    }

    /**
     * 查询物流省份编码
     * 
     * @return
     */
    public String findProvince() {
        setTableConfig();
        Pagination<WhTransAreaNoCommand> area = transManager.findProvince(tableConfig.getStart(), tableConfig.getPageSize(), whTransAreaNoCommand, tableConfig.getSorts());
        request.put(JSON, toJson(area));
        return JSON;
    }

    /**
     * 删除省份编码
     * 
     * @return
     * @throws JSONException
     */
    public String deleteProvince() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            // wareHouseManager.deletePadcodeOperate(ids);
            transManager.deleteProvince(ids);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 电子面单导入
     * 
     * @return
     */
    public String transProvideImport() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                String lpcode = request.get("lpcode").toString();
                ReadStatus rs = transManager.transProvideImport(file, lpcode, jcustid, checkboxSf);
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

    /**
     * 省份编码导入
     * 
     * @return
     */
    public String transProvinceImport() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = transManager.transProvinceImport(file, lpcode, updateMode, userDetails.getUser().getId());
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


    /**
     * 用户仓库绑定导入
     * 
     * @return
     */
    public String userWarehouseRefImport() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = transManager.userWarehouseRefImport(file);
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

    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    public Long getUpdateMode() {
        return updateMode;
    }

    public void setUpdateMode(Long updateMode) {
        this.updateMode = updateMode;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public WhTransProvideNoCommand getWhTransProvideNoCommand() {
        return whTransProvideNoCommand;
    }

    public void setWhTransProvideNoCommand(WhTransProvideNoCommand whTransProvideNoCommand) {
        this.whTransProvideNoCommand = whTransProvideNoCommand;
    }

    public WhTransAreaNoCommand getWhTransAreaNoCommand() {
        return whTransAreaNoCommand;
    }

    public void setWhTransAreaNoCommand(WhTransAreaNoCommand whTransAreaNoCommand) {
        this.whTransAreaNoCommand = whTransAreaNoCommand;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

}
