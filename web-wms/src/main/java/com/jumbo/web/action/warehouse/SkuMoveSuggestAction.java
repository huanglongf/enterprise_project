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

import loxia.support.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.wms.manager.warehouse.WareHouseLocationManager;
import com.jumbo.wms.model.warehouse.SkuReplenishmentCommand;
import com.jumbo.wms.model.warehouse.WarehouseDistrictType;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 
 * @author jumbo
 * 
 */
public class SkuMoveSuggestAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7455214293147676885L;

    private static Logger log = LoggerFactory.getLogger(SkuMoveSuggestAction.class);
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
    private SkuReplenishmentCommand comm;
    @Autowired
    private WareHouseLocationManager wareHouseLocationManager;
    @Autowired
    private ExcelExportManager excelExportManager;

    public String skuMoveSuggset() {
        return SUCCESS;
    }


    /**
     * 导出补货汇总
     * 
     * @throws Exception
     */
    public void exportReplenishmentInfo() throws Exception {
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=SKU_REPLENISHMENT_" + sdf.format(new Date()) + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            int type = 1; // 正常补货
            excelExportManager.exportSkuReplenishmentInfo(type, userDetails.getCurrentOu().getId(), comm, outs);
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
     * 当前仓库的所有拣货区列表
     * 
     * @return
     */
    public String districtPickingList() {
        request.put(JSON, new JSONArray(wareHouseLocationManager.findDistrictListByType(userDetails.getCurrentOu().getId(), WarehouseDistrictType.PICKING)));
        return JSON;
    }

    public SkuReplenishmentCommand getComm() {
        return comm;
    }

    public void setComm(SkuReplenishmentCommand comm) {
        this.comm = comm;
    }
}
