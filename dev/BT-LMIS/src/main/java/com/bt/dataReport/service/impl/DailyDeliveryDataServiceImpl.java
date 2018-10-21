
package com.bt.dataReport.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bt.dataReport.bean.DailyDeliveryDataBean;
import com.bt.dataReport.bean.OredeShippFlowSummaryBean;
import com.bt.dataReport.dao.DailyDeliveryDataMapper;
import com.bt.dataReport.service.DailyDeliveryDataService;
import com.bt.dataReport.util.ExportExcelUtils;
/**
 *@author jinggong.li
 *@date 2018年7月3日  
 */
@Service
public class DailyDeliveryDataServiceImpl implements DailyDeliveryDataService {
     
	@Resource
	private  DailyDeliveryDataMapper dailyDeliveryDataMapper;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DailyDeliveryDataBean> selectDailyDeliveryDataBean() throws Exception {
		DailyDeliveryDataBean dailyDeliveryDataBean = new DailyDeliveryDataBean();
		
		return (List<DailyDeliveryDataBean>) dailyDeliveryDataMapper.selectDailyDeliveryDataBean(dailyDeliveryDataBean);
	}

	@Override
	public String exporttDailyDeliveryDataBean(String path, String warehouseName, String storeName, String transportName, String beginDateTime, String endDateTime) throws Exception {
		String uuid = UUID.randomUUID().toString();
		List<Map<String,Object>> list = (List<Map<String, Object>>) dailyDeliveryDataMapper.exportDailyDeliveryDataBean(getBean(warehouseName,storeName,transportName,beginDateTime,endDateTime));
		String filePath = path + "/DailyDeliveryData";
	    String fileName = uuid;
	    String[] headers = new String[] {"日期 ","小时","仓库名称","始发地(省)","店铺代码 ","店铺名称 ","快递代码","快递名称","运单数"};
	    return ExportExcelUtils.produceExcel(filePath, fileName, headers, list).getPath();
	}
   
	/**
	 * @param warehouseName
	 * @param storeName
	 * @param beginDateTime
	 * @param beginDateTime2
	 * @param endDateTime
	 * @return
	 */
	private DailyDeliveryDataBean getBean(String warehouseName, String storeName, String transportName,
			String beginDateTime, String endDateTime) {
		DailyDeliveryDataBean dailyDeliveryDataBean = new DailyDeliveryDataBean();
		dailyDeliveryDataBean.setWarehouseName(warehouseName);
		dailyDeliveryDataBean.setStoreName(storeName);
		dailyDeliveryDataBean.setTransportName(transportName);
		dailyDeliveryDataBean.setBeginDateTime(beginDateTime);
		dailyDeliveryDataBean.setEndDateTime(endDateTime);
		return dailyDeliveryDataBean;
	}
}
