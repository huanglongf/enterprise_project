
package com.bt.dataReport.service;

import java.util.List;

import com.bt.dataReport.bean.OredeShippFlowSummaryBean;

/**
 *@author jinggong.li
 *@date 2018年7月3日  
 */
public interface OredeShippFlowSummaryService {

	public List<OredeShippFlowSummaryBean> selectOredeShippFlowSummaryBean() throws Exception;
	
	public String exportOredeShippFlowSummaryBean(String path, String warehouseName, String storeName, String transportName, String beginDateTime, String endDateTime) throws Exception;
}
