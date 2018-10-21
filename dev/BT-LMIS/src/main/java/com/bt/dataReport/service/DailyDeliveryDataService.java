
package com.bt.dataReport.service;

import java.util.List;

import com.bt.dataReport.bean.DailyDeliveryDataBean;

/**
 *@author jinggong.li
 *@date 2018年7月3日  
 */
public interface DailyDeliveryDataService {
     
	public List<DailyDeliveryDataBean> selectDailyDeliveryDataBean() throws Exception;
	
	public String exporttDailyDeliveryDataBean(String path, String warehouseName, String storeName, String transportName, String beginDateTime, String endDateTime)throws Exception;
	
}
