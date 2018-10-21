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

import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 
 * @author sjk
 * 
 */
public class InventoryInitializeAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = -4130724244310328034L;

    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private ExcelReadManager excelReadManager;
    private File file;

    public String inventoryInitializeEntry() {
        return SUCCESS;
    }

    public String importInitializeInventory() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        } else {
            try {
                Long cmpOuid = userDetails.getCurrentOu().getParentUnit().getParentUnit().getId();
                StockTransApplication stacode = new StockTransApplication();
                stacode.setType(StockTransApplicationType.INBOUND_INVENTORY_INITIALIZE);
                ReadStatus rs = excelReadManager.createInitializeInventoryImport(stacode, file, userDetails.getCurrentOu().getId(), cmpOuid, userDetails.getUser().getId());
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
                request.put("result", ERROR);
                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
                errorMsg.add(msg);
            } catch (Exception e) {
                request.put("result", ERROR);
                log.error("",e);
                if (e instanceof DataIntegrityViolationException) {
                    String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + ErrorCode.SN_INSERT_UQ_ERROR);
                    errorMsg.add(msg);
                }
            }
            request.put("message", JsonUtil.collection2json(errorMsg));
        }
        return SUCCESS;
    }

    public String findAllInventory() {
        setTableConfig();
        Pagination<InventoryCommand> page = wareHouseManagerQuery.findAllInventory(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(page));
        return JSON;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
