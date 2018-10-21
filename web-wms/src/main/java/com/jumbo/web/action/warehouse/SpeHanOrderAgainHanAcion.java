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

import org.springframework.beans.factory.annotation.Autowired;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;

import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 特殊处理订单重新处理
 * 
 * @author fanht
 * 
 */
public class SpeHanOrderAgainHanAcion extends BaseJQGridProfileAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    @Autowired
    private WareHouseManager manager;
    private StockTransApplicationCommand sta;
	private String createTime;
    private String endCreateTime;
    
    public StockTransApplicationCommand getSta() {
		return sta;
	}

	public void setSta(StockTransApplicationCommand sta) {
		this.sta = sta;
	}
   public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public String orderAgainHanQuery(){
		   return SUCCESS;
	 }
   
   /**
    * 获取历史作业单列表
    * 
    * @return
    */
   public String getStaOrderList() throws JSONException {
       setTableConfig();
       Pagination<StockTransApplicationCommand> list =
               manager.findEspStaList(tableConfig.getStart(), tableConfig.getPageSize(), FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime),userDetails.getCurrentOu().getId(),
                       sta, tableConfig.getSorts());
       request.put(JSON, toJson(list));
       return JSON;
   }
   
}
