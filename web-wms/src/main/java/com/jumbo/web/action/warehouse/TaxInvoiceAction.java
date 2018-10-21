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





import javax.servlet.ServletOutputStream;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 税控发票导出
 * 
 * @author dly
 * 
 */
public class TaxInvoiceAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 5029420869862210452L;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private ExcelExportManager excelExportManager;
    private String createTime;
    private String endCreateTime;
    private String finishTime;
    private String endFinishTime;
    private String fromDate;
    private String endDate;
    
    private StockTransApplicationCommand staCmd;

    public String taxInvoiceExportEntry() {
        return SUCCESS;
    }

    public String queryStaExport() {
        setTableConfig();
        if (staCmd == null) {
            staCmd = new StockTransApplicationCommand();
        }
        staCmd.setIntType(StockTransApplicationType.OUTBOUND_SALES.getValue());
        request.put(JSON, toJson(wareHouseManager.queryStaExport(tableConfig.getStart(), tableConfig.getPageSize(), FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime), FormatUtil.getDate(finishTime), FormatUtil.getDate(endFinishTime),
                staCmd, userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }

    public String queryStaDetail() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findStaLineCommandListByStaId(tableConfig.getStart(), tableConfig.getPageSize(), staCmd.getId(), tableConfig.getSorts())));
        return JSON;
    }

    public String querySyaIsExport() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            boolean bool = wareHouseManager.staIsExport(staCmd.getId());
            if (bool) obj.put("isNotExport", SUCCESS);
            obj.put("result", SUCCESS);
        } catch (BusinessException e) {
            obj.put("result", ERROR);
        }
        request.put(JSON, obj);
        return JSON;
    }

    /**
     * 导出Sta税控发票
     * 
     * @throws Exception
     */
    public void exportStaSoInvoice() throws Exception {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        String fileName = wareHouseManager.findExportFileNameBySta(staCmd.getId());
        fileName = fileName.replace(" ", "");
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        try {
            excelExportManager.exportSoInvoiceByPickingList(response.getOutputStream(), null, staCmd.getId());
        } catch (Exception e) {
            log.error("", e);
        }
        ServletOutputStream outs = response.getOutputStream();
        outs.flush();
        outs.close();
    }

    /**
     * 销售|换货出库发票导出
     * @throws Exception
     */
    public void exportsoinvoiceforsalesreturnorder() throws Exception {
        if (fromDate == null) {
            throw new BusinessException("查询开始时间不能为空");
        }
        if (endDate == null) {
            throw new BusinessException("查询结束时间不能为空");
        }
        response.setContentType("application/octet-stream;charset=ISO8859-1");
       /* Date _fromDate = FormatUtil.stringToDate(fromDate, "yyyy-MM-dd HH:mm:ss");
        Date _endDate = FormatUtil.stringToDate(endDate, "yyyy-MM-dd HH:mm:ss");*/
        String _fileName = fromDate + "-" + endDate;
        _fileName = _fileName.replace(" ", "-");
        // fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        log.info("==============fileName : {}=================", _fileName);
        response.setHeader("Content-Disposition", "attachment;filename=" + _fileName + Constants.EXCEL_XLS);
        try {
        	excelExportManager.exportsoinvoiceforsalesreturnorder(response.getOutputStream(), this.getCurrentOu().getId(), fromDate, endDate);
        } catch (Exception e) {
            log.error("", e);
        }
        ServletOutputStream outs = response.getOutputStream();
        outs.flush();
        outs.close();
    }
    
    /**
     * Burberry 发票导出
     * 
     * @return
     */
    public String burberryInvoiceExport() {
        return SUCCESS;
    }


    public void exportBurberryInvoice() throws Exception {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        String fileName;
        if (staCmd.getId() != null) {
            fileName = wareHouseManager.findExportFileNameBySta(staCmd.getId()) + "_BURBERRY";
        } else {
            fileName = staCmd.getPickingCode() + "_BURBERRY";
        }
        fileName = fileName.replace(" ", "");
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        try {
            excelExportManager.exportSoInvoiceByPickingList(response.getOutputStream(), staCmd.getPickingList() == null ? null : staCmd.getPickingList().getId(), staCmd.getId());
        } catch (Exception e) {
            log.error("", e);
        }
        ServletOutputStream outs = response.getOutputStream();
        outs.flush();
        outs.close();
    }


    public StockTransApplicationCommand getStaCmd() {
        return staCmd;
    }

    public void setStaCmd(StockTransApplicationCommand staCmd) {
        this.staCmd = staCmd;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getEndFinishTime() {
        return endFinishTime;
    }

    public void setEndFinishTime(String endFinishTime) {
        this.endFinishTime = endFinishTime;
    }

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	

}
