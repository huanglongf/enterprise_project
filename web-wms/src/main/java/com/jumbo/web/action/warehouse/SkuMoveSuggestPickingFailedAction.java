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

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.wms.model.warehouse.SkuReplenishmentCommand;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 
 * @author jumbo
 * 
 */
public class SkuMoveSuggestPickingFailedAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1056196022008461731L;

    private static Logger log = LoggerFactory.getLogger(SkuMoveSuggestPickingFailedAction.class);
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
    private SkuReplenishmentCommand comm;
    @Autowired
    private ExcelExportManager excelExportManager;

    public String skuMoveSuggsetPickingFailedEntry() {
        return SUCCESS;
    }


    /**
     * 导出配货失败汇总
     * 
     * @throws Exception
     */
    public void skuMovesuggestPickingFailedExport() throws Exception {
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=SKU_PICKING_FAILED_REPLENISHMENT_" + sdf.format(new Date()) + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            // int type = 2; // 配货失败补货
            // wareHouseManager.exportSkuReplenishmentInfo(type,
            // userDetails.getCurrentOu().getId(),comm,outs);
            // 配货失败补货 fanht
            excelExportManager.exportSkuReplenishmentInfo(userDetails.getCurrentOu().getId(), comm, outs);
        } catch (Exception e) {
            log.debug("Export SKU Replenishment Summary Error!");
            log.error("", e);
        } finally {
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        }
    }

    

    /**
     * 导出补货建议
     * 
     * @throws Exception
     */
    public void skuReplenishmentSuggestExport() throws Exception {
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=补货建议_" + sdf.format(new Date()) + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        String staCode=null;
        try {
            outs = response.getOutputStream();
            if(comm!=null){
                staCode=comm.getStaCode();
            }
            excelExportManager.exportSkuReplenishmentSuggest(userDetails.getCurrentOu().getId(), staCode, outs);
        } catch (Exception e) {
            log.debug("Export SKU Replenishment Summary Error!");
            log.error("", e);
        } finally {
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        }
    }
    public SkuReplenishmentCommand getComm() {
        return comm;
    }

    public void setComm(SkuReplenishmentCommand comm) {
        this.comm = comm;
    }
}
