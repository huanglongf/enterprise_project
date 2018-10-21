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
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.PickingListPrintManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelValidateManager;
import com.jumbo.wms.model.warehouse.AllocateCargoOrderCommand;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListStatus;

/**
 * 
 * @author dly
 * 
 */
public class BatchInfoAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7046618913102206899L;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private ExcelValidateManager excelValidateManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private PickingListPrintManager pickingListPrintManager;
    @Autowired
    private ExcelExportManager excelExportManager;

    private AllocateCargoOrderCommand comd = new AllocateCargoOrderCommand();

    private File file;

    public String batchInfo() {
        return SUCCESS;
    }

    /**
     * 批次查寻
     * 
     * @return
     */
    public String batchQuery() {
        setTableConfig();
        if (comd != null) {
            if (comd.getStatus() == null) {
                List<Integer> statusList = new ArrayList<Integer>();
                statusList.add(PickingListStatus.PACKING.getValue());
                statusList.add(PickingListStatus.PARTLY_RETURNED.getValue());
                comd.setStatusList(statusList);
            }
            comd.setFormCrtime(FormatUtil.getDate(comd.getFormCrtime1()));
            comd.setToCrtime(FormatUtil.getDate(comd.getToCrtime1()));
        }
        request.put(JSON, toJson(wareHouseManager.queryPickingList(tableConfig.getStart(), tableConfig.getPageSize(), comd, userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }


    /**
     * pickingList导出
     * 
     * @throws Exception
     */
    public void exportPickingList() throws Exception {
        PickingList pl = pickingListPrintManager.getPickingListById(comd.getId());
        response.setHeader("Content-Disposition", "attachment;filename=" + pl.getCode() + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.exportPickingList(outs, pl);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        }
    }

    /**
     * 导入
     * 
     * @throws Exception
     */
    public String importRefreshPickingList() throws Exception {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            request.put("result", ERROR);
            return ERROR;
        } else {
            try {
                ReadStatus rs = excelReadManager.importRefreshPickingList(file, comd.getId(), userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
                request.put("result", ERROR);
                if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                    request.put("result", SUCCESS);
                } else if (rs.getStatus() > 0) {
                    List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                    for (String s : list) {
                        errorMsg.add(s);
                    }
                } else if (rs.getStatus() < 0) {
                    for (Exception ex : rs.getExceptions()) {
                        if (ex instanceof BusinessException) {
                            BusinessException bex = (BusinessException) ex;
                            String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                            errorMsg.add(msg);
                        }
                    }
                }
            } catch (BusinessException e) {
                log.error("", e);
                request.put("result", ERROR);
                if (e.getErrorCode() > 0) {
                    errorMsg.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                String ss = null;
                BusinessException current = e;
                while (current.getLinkedException() != null) {
                    current = current.getLinkedException();
                    ss = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs());
                    errorMsg.add(ss);
                }
            } catch (Exception e) {
                log.error("", e);
                if (e instanceof BusinessException) {
                    BusinessException be = (BusinessException) e;
                    request.put("result", ERROR);
                    String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs());
                    errorMsg.add(msg);
                } else {
                    request.put("result", ERROR);
                    errorMsg.add(e.getMessage());
                }
            }
            request.put("message", JsonUtil.collection2json(errorMsg));
        }
        return SUCCESS;
    }

    /**
     * 仓库导入批量出库
     * 
     * @throws Exception
     */
    public String importRefreshPickingListSN() throws Exception {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            request.put("result", ERROR);
            return ERROR;
        } else {
            try {
                ReadStatus rs = excelValidateManager.importRefreshPickingListSN(file, userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
                request.put("result", ERROR);
                if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                    request.put("result", SUCCESS);
                } else if (rs.getStatus() > 0) {
                    List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                    for (String s : list) {
                        errorMsg.add(s);
                    }
                } else if (rs.getStatus() < 0) {
                    for (Exception ex : rs.getExceptions()) {
                        if (ex instanceof BusinessException) {
                            BusinessException bex = (BusinessException) ex;
                            String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                            errorMsg.add(msg);
                        }
                    }
                }
            } catch (BusinessException e) {
                log.error("", e);
                request.put("result", ERROR);
                if (e.getErrorCode() > 0) {
                    errorMsg.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                String ss = null;
                BusinessException current = e;
                while (current.getLinkedException() != null) {
                    current = current.getLinkedException();
                    ss = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs());
                    errorMsg.add(ss);
                }
            } catch (Exception e) {
                log.error("", e);
                if (e instanceof BusinessException) {
                    BusinessException be = (BusinessException) e;
                    request.put("result", ERROR);
                    String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs());
                    errorMsg.add(msg);
                } else {
                    request.put("result", ERROR);
                    errorMsg.add(e.getMessage());
                }
            }
            request.put("message", JsonUtil.collection2json(errorMsg));
        }
        return SUCCESS;
    }

    public AllocateCargoOrderCommand getComd() {
        return comd;
    }

    public void setComd(AllocateCargoOrderCommand comd) {
        this.comd = comd;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
