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
 */
package com.jumbo.web.action.warehouse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.BatchReceivingManagerProxy;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransVoucher;

/**
 * @author lichuan
 * 
 */
public class ImportBatchReceivingAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private BatchReceivingManagerProxy bachBatchReceivingManagerProxy;

    private StockTransApplication sta;
    private StockTransVoucher stv;
    private File staFile;

    /**
     * 
     * @return
     */
    public String importTemplateBatReceiving() {
        return SUCCESS;
    }

    /**
     * 模板导入
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String importForBatchReceiving() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (staFile == null) {
            msg = (staFile == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                Map<String, Object> readStatus = new HashMap<String, Object>();
                Map<String, Object> beans = new HashMap<String, Object>();
                readStatus = bachBatchReceivingManagerProxy.readAllDataFromExcel(staFile, beans);
                beans = (Map<String, Object>) readStatus.get("beans");
                ReadStatus rs = (ReadStatus) readStatus.get("readStatus");
                if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                    ReadStatus rs2 = bachBatchReceivingManagerProxy.batchReceiving(beans, userDetails.getUser());
                    if (rs2.getStatus() < ReadStatus.STATUS_SUCCESS) {
                        request.put("msg", "");
                        List<String> list = new ArrayList<String>();
                        for (Exception ex : rs2.getExceptions()) {
                            if (ex instanceof BusinessException) {
                                BusinessException be = (BusinessException) ex;
                                list.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()) + Constants.HTML_LINE_BREAK);
                            }
                        }
                        request.put("msg", list);
                    } else {
                        request.put("msg", "收货成功");
                    }
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
                return SUCCESS;
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


    /**
     * 保质期商品模板导入
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String importShelfLifeForBatchReceiving() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (staFile == null) {
            msg = (staFile == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                Map<String, Object> readStatus = new HashMap<String, Object>();
                Map<String, Object> beans = new HashMap<String, Object>();
                readStatus = bachBatchReceivingManagerProxy.readAllShelfLifeDataFromExcel(staFile, beans);
                beans = (Map<String, Object>) readStatus.get("beans");
                ReadStatus rs = (ReadStatus) readStatus.get("readStatus");
                if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                    ReadStatus rs2 = bachBatchReceivingManagerProxy.batchShelfLifeReceiving(beans, userDetails.getUser());
                    if (rs2.getStatus() < ReadStatus.STATUS_SUCCESS) {
                        request.put("msg", "");
                        List<String> list = new ArrayList<String>();
                        for (Exception ex : rs2.getExceptions()) {
                            if (ex instanceof BusinessException) {
                                BusinessException be = (BusinessException) ex;
                                list.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()) + Constants.HTML_LINE_BREAK);
                            }
                        }
                        request.put("msg", list);
                    } else {
                        request.put("msg", "收货成功");
                    }
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
                return SUCCESS;
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


    /**
     * @return the excelReadManager
     */
    public ExcelReadManager getExcelReadManager() {
        return excelReadManager;
    }

    /**
     * @param excelReadManager the excelReadManager to set
     */
    public void setExcelReadManager(ExcelReadManager excelReadManager) {
        this.excelReadManager = excelReadManager;
    }

    /**
     * @return the sta
     */
    public StockTransApplication getSta() {
        return sta;
    }

    /**
     * @param sta the sta to set
     */
    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    /**
     * @return the stv
     */
    public StockTransVoucher getStv() {
        return stv;
    }

    /**
     * @param stv the stv to set
     */
    public void setStv(StockTransVoucher stv) {
        this.stv = stv;
    }

    /**
     * @return the staFile
     */
    public File getStaFile() {
        return staFile;
    }

    /**
     * @param staFile the staFile to set
     */
    public void setStaFile(File staFile) {
        this.staFile = staFile;
    }

}
