package com.jumbo.web.action.baseinfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.model.LitreSingle;

/**
 * Nike当日达业务控制逻辑
 * 
 */
public class NikeTodaySendConfigAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = 2876687458958553040L;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private ExcelExportManager excelExportManager;
    
    private LitreSingle ls;
    
    private File fileNIKE;
    private File file;
    private File fileType;
    private File fileWhy;

    public void exportNikeTodaySendConfig() throws IOException {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        String fileName = userDetails.getCurrentOu().getName() + "Nike当日达区域配置";
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        ServletOutputStream os = response.getOutputStream();
        try {
            excelExportManager.exportNikeTodaySendConfigByOuId(userDetails.getCurrentOu().getId(), os);
            os.flush();
            os.close();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public String importNikeTodaySendConfig() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("flag", "nike");
        request.put("result", SUCCESS);
        if (fileNIKE == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = baseinfoManager.importNikeTodaySendConfig(fileNIKE, userDetails.getCurrentOu().getId());
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
    
    /**
     * 保存nike当日达的规则
     * @throws JSONException 
     */
    public String saveNikeConfig() throws JSONException{
        JSONObject result = new JSONObject();
        try {
            baseinfoManager.saveNikeConfig(ls, userDetails.getCurrentOu().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("msg","该规则已存在!");
        }
        request.put(JSON, result);
        return JSON;
    }

    public LitreSingle getLs() {
        return ls;
    }

    public void setLs(LitreSingle ls) {
        this.ls = ls;
    }
    
    public File getFileNIKE() {
        return fileNIKE;
    }

    public void setFileNIKE(File fileNIKE) {
        this.fileNIKE = fileNIKE;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFileType() {
        return fileType;
    }

    public void setFileType(File fileType) {
        this.fileType = fileType;
    }

    public File getFileWhy() {
        return fileWhy;
    }

    public void setFileWhy(File fileWhy) {
        this.fileWhy = fileWhy;
    }

}
