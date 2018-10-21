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
package com.jumbo.web.action.report;

import java.io.UnsupportedEncodingException;
import javax.servlet.ServletOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import com.jumbo.Constants;
import com.jumbo.wms.manager.report.ReportManager;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class ReportLevisCurrentInventoryExportAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = -4171054154406227226L;

    private InventoryCommand cmd;

    @Autowired
    private ReportManager reportManager;

    public String entry() {
        return SUCCESS;
    }

    /**
     * 报表导出
     * 
     * @throws UnsupportedEncodingException
     * 
     * @throws Exception
     */
    public void reportExportCurrentInventory() throws UnsupportedEncodingException {
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = new String(getMessage("excel.tplt_report_export_for_levis_current_inv", new Object[] {}).getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            reportManager.exportShopCurrentInventory(outs, userDetails.getCurrentOu().getId(), cmd);
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        } catch (Exception e) {
            log.error("",e);
        }
    }

    public InventoryCommand getCmd() {
        return cmd;
    }

    public void setCmd(InventoryCommand cmd) {
        this.cmd = cmd;
    }

}
