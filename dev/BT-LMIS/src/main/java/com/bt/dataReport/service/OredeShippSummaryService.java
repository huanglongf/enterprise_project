
package com.bt.dataReport.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bt.dataReport.bean.OredeShippSummaryBean;

/**
 *@author jinggong.li
 *@date 2018年7月3日  
 */
public interface OredeShippSummaryService {
	
	public List<OredeShippSummaryBean> selectOredeShippSummaryBean() throws Exception;
	
	public String exportOredeShippSummaryBean(String path, String warehouseName, String storeName, String transportName, String beginDateTime, String endDateTime) throws Exception;
}
