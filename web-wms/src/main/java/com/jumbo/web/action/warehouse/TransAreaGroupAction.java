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

import javax.servlet.ServletOutputStream;

import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.security.UserDetails;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.TransAreaGroupManager;
import com.jumbo.wms.model.command.TransportatorCommand;
import com.jumbo.wms.model.trans.TransAreaGroupCommand;

/**
 * 配送范围
 * 
 * @author fxl
 * 
 */
public class TransAreaGroupAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = -4130724244310328034L;
    private File file;
    private String areaCode;
    private String areaName;
    private Long areaId;// 配送范围Id
    private Long status;
    private String tranId; // 物流服务ID
    private String serviceName; // 服务名称
    private String serviceType; // 服务类型
    private String timeType; // 时效类型
    private String statuss; // 状态
    private String maId; // 物流ID
    protected UserDetails userDetails;
    private TransportatorCommand ma;
    @Autowired
    private TransAreaGroupManager transAreaGroupManager;
    @Autowired
    private ExcelExportManager excelExportManager;



    /**
     * 查询配送范围
     * 
     * @return
     */
    public String findTransArea() {
        setTableConfig();
        Pagination<TransAreaGroupCommand> group = transAreaGroupManager.findTransArea(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), userDetails.getCurrentOu().getId());
        request.put(JSON, toJson(group));
        return JSON;
    }

    /**
     * 编辑查询配送范围
     * 
     * @return
     */
    public String updateFindTransArea() {
        setTableConfig();
        Pagination<TransAreaGroupCommand> group = transAreaGroupManager.updateFindTransArea(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), userDetails.getCurrentOu().getId());
        request.put(JSON, toJson(group));
        return JSON;
    }


    /**
     * 修改配送范围
     * 
     * @return
     */
    public String updateAreaGroup() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            transAreaGroupManager.updateTransArea(areaId, areaName, status);
            result.put("msg", "success");
        } catch (Exception e) {
            result.put("msg", "error");
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * EXL导入
     * 
     * @return
     */
    public String transAreaImport() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = transAreaGroupManager.transAreaImport(file, areaCode, areaName, areaId, status, userDetails.getCurrentOu().getId());
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
     * EXL导入 加保存
     * 
     * @return
     */
    public String transServiceImport() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        try {
            // 快递维护 配送范围导入
            ReadStatus rs = transAreaGroupManager.transAreaImport2(file, maId, tranId, serviceName, serviceType, timeType, statuss, userDetails.getUser());
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
        return SUCCESS;
    }



    /**
     * 新增/修改 快递
     * 
     * @return
     * @throws JSONException
     */
    public String saveMaTransport() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            Long maId = transAreaGroupManager.saveMaTransport(ma, userDetails.getUser());
            result.put("result", SUCCESS);
            result.put("maId", maId);
        } catch (BusinessException e) {
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 配送范围导出
     * 
     * @return
     */
    public void expTransAreaDetail() throws Exception {
        areaName = new String(areaName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + areaName + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.expTransAreaDetail(outs, areaId);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        }
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getMaId() {
        return maId;
    }

    public void setMaId(String maId) {
        this.maId = maId;
    }

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getStatuss() {
        return statuss;
    }

    public void setStatuss(String statuss) {
        this.statuss = statuss;
    }

    public TransportatorCommand getMa() {
        return ma;
    }

    public void setMa(TransportatorCommand ma) {
        this.ma = ma;
    }
}
