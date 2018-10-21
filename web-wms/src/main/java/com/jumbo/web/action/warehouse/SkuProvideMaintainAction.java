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

import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;

/***
 * 店铺信息维护
 * 
 * @author jumbo
 * 
 */
public class SkuProvideMaintainAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2281494069743297216L;

    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private ExcelExportManager excelExportManager;

    /**
     * 导入文件
     */
    private File file;

    /**
     * @return success
     */
    public String skuProvideMaintainEntry() {
        return SUCCESS;
    }


    /**
     * 导出拣货区 所有商品 KJL
     * 
     * @throws Exception
     */
    public void skuProvideInfoPickingDistrictExport1() throws Exception {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        String fileName = this.getMessage("sku.provide.for.picking.district.export");
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.skuProvideInfoPickingDistrictExport1(outs, this.userDetails.getCurrentOu().getId());
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("skuProvideInfoPickingDistrictExport1 Exception:", e);
            };
        } finally {
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        }
    }


    /**
     * 仓库未完成补货信息商品导出 KJL
     * 
     * @throws Exception
     */

    public void skuProvideInfoUnMaintainExport1() throws Exception {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        String fileName = this.getMessage("sku.provide.for.unmaintain.export");
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.skuProvideInfoUnMaintainExport1(outs, this.userDetails.getCurrentOu().getId());
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("skuProvideInfoUnMaintainExport1 Exception:", e);
            };
        } finally {
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        }
    }


    /**
     * 仓库未完成入库作业单 未维护补货信息商品导出 KJL
     * 
     * @throws Exception
     */

    public void unfinishedStaUnMaintainProductExport1() throws Exception {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        String fileName = this.getMessage("unfinished.sta.unmaintain.sku.export");
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.unfinishedStaUnMaintainProductExport1(outs, this.userDetails.getCurrentOu().getId());
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("unfinishedStaUnMaintainProductExport1 Exception:", e);
            };
        } finally {
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        }
    }


    // 创建 - 导入
    public String importSkuProvideMaxMaintain() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = excelReadManager.importSkuProvideMaxMaintain(file, userDetails.getCurrentOu().getId());
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
                log.error(e.getMessage());
                request.put("msg", sb.toString());
            } catch (Exception e) {
                request.put("msg", "error");
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    public File getFile() {
        return file;
    }


    public void setFile(File file) {
        this.file = file;
    }
}
