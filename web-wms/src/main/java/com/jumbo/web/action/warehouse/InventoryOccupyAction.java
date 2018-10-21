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

import java.util.List;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.InventoryOccupyManager;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StvLine;

/**
 * @author lichuan
 * 
 */
public class InventoryOccupyAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = -3603698638034668528L;
    @Autowired
    private InventoryOccupyManager inventoryOccupyManager;
    private String memo;
    private List<StvLine> stvLinelist;
    private Integer lockType;
    private StockTransApplicationCommand staCmd;

    public String invLockAndUnlockEntry() {
        return SUCCESS;
    }

    // 库存锁定
    public String inventoryLock() throws JSONException {
        JSONObject result = new JSONObject();
        String errorMsg = "";
        try {
            StockTransApplication sta = inventoryOccupyManager.inventoryLock(null, lockType, memo, stvLinelist, userDetails.getUser().getId(), userDetails.getCurrentOu().getId());
            result.put("result", SUCCESS);
            result.put("sta", JsonUtil.obj2json(sta));
        } catch (BusinessException e) {
            result.put("result", ERROR);
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs());
            }
            result.put("message", errorMsg);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String findInventoryLockSta() {
        setTableConfig();
        if (staCmd != null) {
            staCmd.setCreateTime(FormatUtil.getDate(staCmd.getCreateTime1()));
            staCmd.setFinishTime(FormatUtil.getDate(staCmd.getFinishTime1()));
        }
        request.put(JSON, toJson(inventoryOccupyManager.findInventoryLockStaByPage(tableConfig.getStart(), tableConfig.getPageSize(), staCmd, lockType, userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }

    public String findOccupiedStaLineByStaId() {
        setTableConfig();
        List<StaLineCommand> list = inventoryOccupyManager.findOccupiedStaLineByStaId(staCmd.getId());
        request.put(JSON, toJson(list));
        return JSON;
    }

    // 库存解锁
    public String inventoryUnlock() throws JSONException {
        JSONObject result = new JSONObject();
        String errorMsg = "";
        try {
            inventoryOccupyManager.inventoryUnlock(staCmd, userDetails.getUser().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs());
            }
            result.put("message", errorMsg);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<StvLine> getStvLinelist() {
        return stvLinelist;
    }

    public void setStvLinelist(List<StvLine> stvLinelist) {
        this.stvLinelist = stvLinelist;
    }

    public Integer getLockType() {
        return lockType;
    }

    public void setLockType(Integer lockType) {
        this.lockType = lockType;
    }

    public StockTransApplicationCommand getStaCmd() {
        return staCmd;
    }

    public void setStaCmd(StockTransApplicationCommand staCmd) {
        this.staCmd = staCmd;
    }



}
