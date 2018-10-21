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

import java.util.HashMap;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.StringUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.warehouse.QueueStaManagerExecute;
import com.jumbo.wms.model.warehouse.QueueStaCommand;

public class WhFailCountResetAction extends BaseJQGridProfileAction {

	private static final long serialVersionUID = -81718013741989526L;
	
	@Autowired
	private QueueStaManagerExecute queueStaManagerExecute;
	
	/**
	 * 待创建队列-订单信息
	 */
	private QueueStaCommand queueStaCommand;
	
	private String ids;
	
	
	public String initWhFailCountResetPage() {
        return SUCCESS;
    }
	
	/**
	 * 方法说明：分页查询(根据参数) queueStaCommand
	 * @author LuYingMing
	 * @date 2016年7月25日 下午2:53:45 
	 * @return
	 */
    public String findQueueStaParams() {
        setTableConfig();
        Map<String, Object> params = new HashMap<String, Object>();
        if (queueStaCommand != null) {
            if (!"".equals(queueStaCommand.getOrdercode())) {
                params.put("ordercode", queueStaCommand.getOrdercode());
            }
            if (!"".equals(queueStaCommand.getOwner())) {
                params.put("owner", queueStaCommand.getOwner());
            }
            if (null != queueStaCommand.getNumberUp()) {
            	params.put("numberUp", queueStaCommand.getNumberUp());
            }
            if (null != queueStaCommand.getAmountTo()) {
            	params.put("amountTo", queueStaCommand.getAmountTo());
            }
        }
        Pagination<QueueStaCommand> queueStaList = queueStaManagerExecute.findQueueStaByParams(tableConfig.getStart(), tableConfig.getPageSize(), params);
        request.put(JSON, toJson(queueStaList));
        return JSON;
    }
	
	
	
	
	
	/**
	 * 方法说明：重置为 ZERO
	 * @author LuYingMing
	 * @date 2016年7月25日 下午5:03:37 
	 * @return
	 * @throws JSONException
	 */
    public String resetToZero() throws JSONException {
    	JSONObject result = new JSONObject();
        	try {
				if (!StringUtil.isEmpty(ids)) {
					String[] idArray = ids.split(",");
					for (int i = 0; i < idArray.length; i++) {
						Long id = Long.parseLong(idArray[i]);
						queueStaManagerExecute.resetToZero(id);
					}
					result.put("msg", SUCCESS);
				}else {
					result.put("msg", ERROR);
				}
			} catch (Exception e) {
				log.error("", e);
				result.put("msg", ERROR);
			}
        request.put(JSON, result);
        return JSON;
    }
	
	
    /**
     * 方法说明：重置为99
     * @author LuYingMing
     * @date 2016年7月25日 下午5:44:44 
     * @return
     * @throws JSONException
     */
    public String resetTo99() throws JSONException {
    	JSONObject result = new JSONObject();
        	try {
				if (!StringUtil.isEmpty(ids)) {
					String[] idArray = ids.split(",");
					for (int i = 0; i < idArray.length; i++) {
						Long id = Long.parseLong(idArray[i]);
						queueStaManagerExecute.resetTo99(id);
					}
					result.put("msg", SUCCESS);
				}else {
					result.put("msg", ERROR);
				}
        }catch (Exception e){
            log.error("", e);
            result.put("msg", ERROR);
			}
        request.put(JSON, result);
        return JSON;
    }


	public QueueStaCommand getQueueStaCommand() {
		return queueStaCommand;
	}

	public void setQueueStaCommand(QueueStaCommand queueStaCommand) {
		this.queueStaCommand = queueStaCommand;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	

}
