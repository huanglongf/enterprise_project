package com.jumbo.web.action.baseinfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.SfNextMorningConfigManager;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * SF次晨达业务控制逻辑
 * 
 * @author jinlong.ke
 * @date 2016年4月7日下午2:39:55
 */
public class SfNextMorningConfigAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = -3167229754572866030L;
    @Autowired
    private ExcelExportManager excelExportManager;

    @Autowired
    private SfNextMorningConfigManager sfNextMorningConfigManager;

    private File fileSFC;
    private File fileSFT;
    private File file;
    private File fileType;
    private File fileWhy;
    private Long ouId;
    private Long cId;

    public File getFileSFT() {
        return fileSFT;
    }

    public void setFileSFT(File fileSFT) {
        this.fileSFT = fileSFT;
    }


    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }


    public void exportSfNextMorningConfig() throws IOException {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        String fileName = userDetails.getCurrentOu().getName() + "SF次晨达区域配置";
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        ServletOutputStream os = response.getOutputStream();
        try {
            excelExportManager.exportSfNextMorningConfigByOuId(userDetails.getCurrentOu().getId(), os);
            os.flush();
            os.close();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 直连sf时效导出
     * 
     * @return
     */
    public void exportSfConfig() throws IOException {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        OperationUnit op = sfNextMorningConfigManager.getById(ouId);
        String fileName = op.getName() + "SF时效区域配置";
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        ServletOutputStream os = response.getOutputStream();
        try {
            excelExportManager.exportSfConfigByOuId(ouId, cId, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("exportSfConfig Exception:", e);
            };
            // log.error("", e);
        }
    }

    /**
     * 直连sf时效导入
     * 
     * @return
     */
    public String importSfConfig() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (fileSFT == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = sfNextMorningConfigManager.importSfConfig(fileSFT, ouId, userDetails.getUser().getId(), cId);
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
                if ("sf001".equals(e.getMessage())) {
                    sb.append("省市时效必填");
                } else if ("sf002".equals(e.getMessage())) {
                    sb.append("时效填写不合法");
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



    public String importSfNextMorningConfig() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (fileSFC == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = sfNextMorningConfigManager.importSfNextMorningConfig(fileSFC, userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
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

    public File getFileSFC() {
        return fileSFC;
    }

    public void setFileSFC(File fileSFC) {
        this.fileSFC = fileSFC;
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
