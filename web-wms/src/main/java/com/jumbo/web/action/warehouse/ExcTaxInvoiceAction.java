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
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 退换货税控发票导出
 * 
 * @author dly
 * 
 */
public class ExcTaxInvoiceAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 5029420869862210452L;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private ExcelExportManager excelExportManager;

    private String createTime;
    private String endCreateTime;
    private String finishTime;
    private String endFinishTime;

    private StockTransApplicationCommand staCmd;

    public String excTaxInvoiceExportEntry() {
        return SUCCESS;
    }

    public String queryExcStaExport() {
        setTableConfig();
        if (staCmd == null) {
            staCmd = new StockTransApplicationCommand();
        }
        request.put(JSON, toJson(wareHouseManager.queryExcStaExport(tableConfig.getStart(), tableConfig.getPageSize(), FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime), FormatUtil.getDate(finishTime), FormatUtil.getDate(endFinishTime),
                staCmd, userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }

    public String queryExcStaDetail() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findStaLineCommandListByStaId(tableConfig.getStart(), tableConfig.getPageSize(), staCmd.getId(), tableConfig.getSorts())));
        return JSON;
    }

    public String queryExcSyaIsExport() throws JSONException {
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
    public void exportExcStaSoInvoice() throws Exception {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        String fileName = wareHouseManager.findExportFileNameBySta(staCmd.getId());
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        try {
            excelExportManager.exportSoInvoiceByPickingList(response.getOutputStream(), null,staCmd.getId());
        } catch (Exception e) {
            log.error("",e);
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

}
