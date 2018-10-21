package com.bt.dataReport.dao;

import java.util.List;

import com.bt.dataReport.bean.DailyDeliveryDataBean;
import com.bt.dataReport.bean.OredeShippSummaryBean;
import com.bt.lmis.code.BaseMapper;

/**
 *@author jinggong.li
 *@date 2018年7月3日  
 */
public interface DailyDeliveryDataMapper extends BaseMapper {
	public List<?> selectDailyDeliveryDataBean(DailyDeliveryDataBean dailyDeliveryDataBean) throws Exception;
	
	public List<?> exportDailyDeliveryDataBean(DailyDeliveryDataBean dailyDeliveryDataBean) throws Exception;

}
