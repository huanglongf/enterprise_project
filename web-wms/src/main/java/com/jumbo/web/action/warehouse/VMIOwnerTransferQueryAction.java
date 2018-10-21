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


import java.util.Date;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class VMIOwnerTransferQueryAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3174070359613322211L;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private BaseinfoManager baseinfoManager;

    private String createDate;
    private String endCreateDate;
    private String startDate;
    private String offStartDate;
    private StockTransApplicationCommand staCommand;
    private Integer status;
    private Long staID;

    /**
     * 获取VMI出入库的sta
     * 
     * @return
     */
    public String vmiTransferStaQuery() {
        Long companyid = baseinfoManager.findCompanyByWarehouse(this.userDetails.getCurrentOu().getId()).getId();
        setTableConfig();
        if (status != null && status != 0) {
            if (staCommand == null) {
                staCommand = new StockTransApplicationCommand();
            }
            staCommand.setStatus(StockTransApplicationStatus.valueOf(status));
            staCommand.setLastModifyTime(new Date());
        }
        request.put(JSON, toJson(wareHouseManager.findVMITransferStaPage(tableConfig.getStart(),tableConfig.getPageSize(),userDetails.getCurrentOu().getId(), companyid, FormatUtil.getDate(createDate), FormatUtil.getDate(endCreateDate), FormatUtil.getDate(startDate), FormatUtil.getDate(offStartDate),
                staCommand, tableConfig.getSorts())));
        return JSON;
    }

    /***
     * details
     * 
     * @return
     */
    public String findVmiTransDetails() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findVmiTransDetails(staID)));
        return JSON;
    }

    /**
     * 出库执行
     * 
     * @return
     * @throws Exception
     */

    public String executeVmiTransfer() throws Exception {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.executeVmiTransferOutBound(staID, userDetails.getUser().getId(), userDetails.getCurrentOu().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            log.debug("",e);
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 出入库取消
     * 
     * @return
     * @throws JSONException
     */

    public String cancelVmiTransfer() throws Exception {
        JSONObject result = new JSONObject();
        try {
        	wareHouseManagerCancel.cancelVmiTransferOutBound(staID, userDetails.getUser().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }


    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(String endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getOffStartDate() {
        return offStartDate;
    }

    public void setOffStartDate(String offStartDate) {
        this.offStartDate = offStartDate;
    }

    public StockTransApplicationCommand getStaCommand() {
        return staCommand;
    }

    public void setStaCommand(StockTransApplicationCommand staCommand) {
        this.staCommand = staCommand;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getStaID() {
        return staID;
    }

    public void setStaID(Long staID) {
        this.staID = staID;
    }
}
