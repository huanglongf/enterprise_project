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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.StringUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.task.TaskOmsOutManager;
import com.jumbo.wms.model.warehouse.WmsIntransitNoticeOmsCommand;

/**
 * 接口/类说明：出库反馈PACS查询及错误次数重置
 * 
 * @ClassName: OutBoundFeedBackPacsAction
 * @author LuYingMing
 * @date 2016年8月15日 上午10:21:22
 */
public class OutBoundFeedBackPacsAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 182857835039089928L;


    @Autowired
    private TaskOmsOutManager taskOmsOutManager;


    /**
     * 出库反馈PACS
     */
    private WmsIntransitNoticeOmsCommand wmsIntransitNoticeOmsCommand;
	
	private String ids;
	
	
    public String initOperatePacsPage(){
        return SUCCESS;
    }
	
	    /**
     * 方法说明：分页查询(根据参数) WmsIntransitNoticeOmsCommand
     * 
     * @author LuYingMing
     * @date 2016年8月15日 下午12:28:16
     * @return
     */
    public String findPacsParams(){
        setTableConfig();
        Map<String, Object> params = new HashMap<String, Object>();
        if (wmsIntransitNoticeOmsCommand != null){
            if (!"".equals(wmsIntransitNoticeOmsCommand.getStaCode())){
                params.put("staCode", wmsIntransitNoticeOmsCommand.getStaCode());
            }
            if (!"".equals(wmsIntransitNoticeOmsCommand.getOwner())) {
                params.put("owner", wmsIntransitNoticeOmsCommand.getOwner());
            }
            if (null != wmsIntransitNoticeOmsCommand.getNumberUp()) {
            	params.put("numberUp", wmsIntransitNoticeOmsCommand.getNumberUp());
            }
            if (null != wmsIntransitNoticeOmsCommand.getAmountTo()) {
            	params.put("amountTo", wmsIntransitNoticeOmsCommand.getAmountTo());
            }
        }
        Pagination<WmsIntransitNoticeOmsCommand> omsList = taskOmsOutManager.findOutPacsByParams(tableConfig.getStart(), tableConfig.getPageSize(), params);
        request.put(JSON, toJson(omsList));
        return JSON;
    }
	
	
	
	
	
    /**
     * 方法说明：重置为 ZERO
     * 
     * @author LuYingMing
     * @date 2016年8月15日 下午5:33:59
     * @return
     * @throws JSONException
     */
    public String resetToZero() throws JSONException{
        JSONObject result = new JSONObject();
        try{
            if (!StringUtil.isEmpty(ids)){
                List<Long> idList = new ArrayList<Long>();
                String[] idArray = ids.split(",");
                for (int i = 0; i < idArray.length; i++){
                    Long id = Long.parseLong(idArray[i]);
                    idList.add(id);
                }
                taskOmsOutManager.resetZero(idList);
                result.put("msg", SUCCESS);
            }else{
                result.put("msg", ERROR);
            }
        }catch (Exception e){
            log.error("", e);
            result.put("msg", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 方法说明：重置为99
     * 
     * @author LuYingMing
     * @date 2016年7月25日 下午5:44:44
     * @return
     * @throws JSONException
     */
    public String resetHundred() throws JSONException{
        JSONObject result = new JSONObject();
        try{
            if (!StringUtil.isEmpty(ids)){
                List<Long> idList = new ArrayList<Long>();
                String[] idArray = ids.split(",");
                for (int i = 0; i < idArray.length; i++){
                    Long id = Long.parseLong(idArray[i]);
                    idList.add(id);
                }
                taskOmsOutManager.resetHundred(idList);
                result.put("msg", SUCCESS);
            }else{
                result.put("msg", ERROR);
            }
        }catch (Exception e){
            log.error("", e);
            result.put("msg", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

    public WmsIntransitNoticeOmsCommand getWmsIntransitNoticeOmsCommand(){
        return wmsIntransitNoticeOmsCommand;
    }

    public void setWmsIntransitNoticeOmsCommand(WmsIntransitNoticeOmsCommand wmsIntransitNoticeOmsCommand){
        this.wmsIntransitNoticeOmsCommand = wmsIntransitNoticeOmsCommand;
    }

}
