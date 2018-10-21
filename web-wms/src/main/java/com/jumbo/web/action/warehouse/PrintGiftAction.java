package com.jumbo.web.action.warehouse;

import org.springframework.beans.factory.annotation.Autowired;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;

import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class PrintGiftAction extends BaseJQGridProfileAction {
	 public StockTransApplication getStap() {
	        return stap;
	    }

	    public void setStap(StockTransApplication stap) {
	        this.stap = stap;
	    }
	/**
	 * NIKE礼品卡打印
	 */
	private static final long serialVersionUID = 7438446789004799195L;
	@Autowired
	private WareHouseManager manager;
	private String createTime;
	private String endCreateTime;
	private String finishTime;
	private String endFinishTime;
	private StockTransApplicationCommand sta;
	private StockTransApplication stap;
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

	    public String getFinishTime() {
	        return finishTime;
	    }

	    public void setFinishTime(String finishTime) {
	        this.finishTime = finishTime;
	    }

	    public String getEndFinishTime() {
	        return endFinishTime;
	    }

	    public void setEndFinishTime(String endFinishTime) {
	        this.endFinishTime = endFinishTime;
	    }

	    public StockTransApplicationCommand getSta() {
	        return sta;
	    }

	    public void setSta(StockTransApplicationCommand sta) {
	        this.sta = sta;
	    }

	public String orderQueryEntry(){
		return SUCCESS;
	}
	 /**
     * 获取历史作业单列表
     * 
     * @return
     */
    public String getPrintGift() throws JSONException {
        setTableConfig();
        Pagination<StockTransApplicationCommand> list =
                manager.findPrintGift(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime), FormatUtil.getDate(finishTime), FormatUtil
                        .getDate(endFinishTime), sta, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }
    /**
     * 获取作业单明细
     * 
     * @return
     */
    public String getPrintGiftLine() throws JSONException {
        setTableConfig();
        request.put(JSON, toJson(manager.findPrintGiftLine(tableConfig.getStart(), tableConfig.getPageSize(), sta.getId(), tableConfig.getSorts())));
        return JSON;
    }
	

}
