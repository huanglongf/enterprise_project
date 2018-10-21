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

import java.text.ParseException;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StvLine;

/**
 * @author lichuan
 * 
 */
public class InventoryAdjustAction extends BaseJQGridProfileAction {


    private static final long serialVersionUID = 8316377150334271702L;


    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;


    private String memo;
    private List<StvLine> stvLinelist;
    private Integer lockType;
    private StockTransApplicationCommand staCmd;

    private InventoryCommand inventoryCommand;

    // private List<String> postData;
    //
    // public List<String> getPostData() {
    // return postData;
    // }
    //
    // public void setPostData(List<String> postData) {
    // this.postData = postData;
    // }

    public InventoryCommand getInventoryCommand() {
        return inventoryCommand;
    }

    public void setInventoryCommand(InventoryCommand inventoryCommand) {
        this.inventoryCommand = inventoryCommand;
    }



    public String validityOperate() {
        return SUCCESS;
    }

    /**
     * 
     * 方法说明：效期调整(条件)查询
     * 
     * @author LuYingMing
     * @return
     */
    public String validityAdjustQuery() {
        // Long whOuId = userDetails.getCurrentOu().getId();
        setTableConfig();
        Pagination<InventoryCommand> list = wareHouseManager.findValidityAdjustByPage(tableConfig.getStart(), tableConfig.getPageSize(), inventoryCommand, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 
     * 方法说明：修改效期
     * 
     * @author LuYingMing
     * @return
     * @throws JSONException
     * @throws ParseException
     */
    public String validityAdjustModify() throws JSONException {
        String flag = null;
        JSONObject result = new JSONObject();
        Long whOuId = userDetails.getCurrentOu().getId();
        try {
            flag = wareHouseManagerExe.validityAdjustModify(whOuId, inventoryCommand);
            if ("SUCCESS".equals(flag)) {
                result.put("result", "success");
            } else if ("NONE".equals(flag)) {
                result.put("result", "none");
            } else {
                result.put("result", "error");
            }
        } catch (BusinessException e) {
            result.put("result", "exception");
            result.put("msg", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e) {
            log.error("", e);
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
