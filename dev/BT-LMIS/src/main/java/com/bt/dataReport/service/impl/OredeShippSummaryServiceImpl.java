
package com.bt.dataReport.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.bt.dataReport.bean.OredeShippSummaryBean;
import com.bt.dataReport.dao.OredeShippSummaryMapper;
import com.bt.dataReport.service.OredeShippSummaryService;
import com.bt.dataReport.util.ExportExcelUtils;

/**
 *@author jinggong.li
 *@date 2018年7月3日  
 */
@Service
public class OredeShippSummaryServiceImpl implements OredeShippSummaryService {
    
	@Resource
	private OredeShippSummaryMapper oredeShippSummaryMapper;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OredeShippSummaryBean> selectOredeShippSummaryBean() throws Exception {
		OredeShippSummaryBean oredeShippSummaryBean = new OredeShippSummaryBean();
		return (List<OredeShippSummaryBean>) oredeShippSummaryMapper.selectOredeShippSummaryBean(oredeShippSummaryBean);
	}

	
	//导出文件
	@Override
	public String exportOredeShippSummaryBean(String path, String warehouseName, String storeName, String transportName, String beginDateTime, String endDateTime) throws Exception {
		String uuid = UUID.randomUUID().toString();
		List<Map<String,Object>> list = (List<Map<String, Object>>) oredeShippSummaryMapper.exportOredeShippSummaryBean(getBean(warehouseName,storeName,transportName,beginDateTime,endDateTime));
		String filePath = path + "/OredeShippSummary";
	    String fileName = uuid;
	    String[] headers = new String[] {"仓库代码 ","仓库名称 ","始发地 (省)","店铺代码 ","店铺名称 ","快递代码 ","快递名称","运单数  ","揽件运单数 ","签收运单数"};
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
	private OredeShippSummaryBean getBean(String warehouseName, String storeName, String transportName,
			String beginDateTime, String endDateTime) {
		OredeShippSummaryBean oredeShippSummaryBean = new OredeShippSummaryBean();
		oredeShippSummaryBean.setWarehouseName(warehouseName);
		oredeShippSummaryBean.setStoreName(storeName);
		oredeShippSummaryBean.setTransportName(transportName);
		oredeShippSummaryBean.setBeginDateTime(beginDateTime);
		oredeShippSummaryBean.setEndDateTime(endDateTime);
		return oredeShippSummaryBean;
	}

}
