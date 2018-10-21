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

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.model.warehouse.InventoryCheckCommand;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 
 * @author jumbo
 * 
 */
public class SkuGroupQueryAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3602716997391011946L;

    private static Logger log = LoggerFactory.getLogger(SkuGroupQueryAction.class);
    private Long id;
    private InventoryCheckCommand icheck;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerExecute wareHouseManagerExecute;

    public String skuGroupQueryEntry() {
        return SUCCESS;
    }

    public String getSkuGroupICheckList() {
        log.debug("======================getSkuGroupICheckList =================");
        setTableConfig();
        if (icheck != null) {
            icheck.setStartDate(FormatUtil.getDate(icheck.getStartDate1()));
            icheck.setEndDate(FormatUtil.getDate(icheck.getEndDate1()));
            icheck.setStartFinishTime(FormatUtil.getDate(icheck.getStartFinishTime1()));
            icheck.setEndFinishTime(FormatUtil.getDate(icheck.getEndFinishTime1()));
        }
        Pagination<InventoryCheckCommand> skuGroupStaList = wareHouseManager.findSkuGroupInvCheckList(tableConfig.getStart(), tableConfig.getPageSize(), icheck, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(skuGroupStaList));
        return JSON;
    }

    // tab-1
    public String findSkuCollectInfo() {
        log.debug("======================tab1 id {}  =================", id);
        setTableConfig();
        Pagination<StvLineCommand> skuCollectInfos = wareHouseManager.findSkuCollectInfo(tableConfig.getStart(), tableConfig.getPageSize(), id, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(skuCollectInfos));
        return JSON;
    }

    // tab-2
    public String findSkuAdjustDetailInfo() {
        log.debug("======================tab2 id {}  =================", id);
        setTableConfig();
        Pagination<StvLineCommand> skuAdjustDetailInfos = wareHouseManager.findSkuAdjustDetailInfo(tableConfig.getStart(), tableConfig.getPageSize(), id, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(skuAdjustDetailInfos));
        return JSON;
    }

    /**
     * 执行 操作
     * 
     * @return
     * @throws JSONException
     */
    public String skuGroupExecution() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManagerExecute.skuGroupExecution(this.userDetails.getUser().getId(), id);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e) {
            log.error("",e);
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.SYSTEM_ERROR + " : " + e.getMessage()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 取消
     * 
     * @return
     * @throws JSONException
     */
    public String skuGroupCancel() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManagerExecute.skuGroupCancel(id,this.userDetails.getUser());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e) {
            log.error("",e);
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.SYSTEM_ERROR + " : " + e.getMessage()));
        }
        request.put(JSON, result);
        return JSON;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InventoryCheckCommand getIcheck() {
        return icheck;
    }

    public void setIcheck(InventoryCheckCommand icheck) {
        this.icheck = icheck;
    }

}
