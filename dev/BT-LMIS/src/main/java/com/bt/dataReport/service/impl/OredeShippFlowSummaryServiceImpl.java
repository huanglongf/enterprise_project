
package com.bt.dataReport.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bt.dataReport.bean.OredeShippFlowSummaryBean;
import com.bt.dataReport.bean.OredeShippSummaryBean;
import com.bt.dataReport.dao.OredeShippFlowSummaryMapper;
import com.bt.dataReport.service.OredeShippFlowSummaryService;
import com.bt.dataReport.util.ExportExcelUtils;

/**
 *@author jinggong.li
 *@date 2018年7月3日  
 */
@Service
public class OredeShippFlowSummaryServiceImpl implements OredeShippFlowSummaryService {

	@Resource
	private OredeShippFlowSummaryMapper oredeShippFlowSummaryMapper;
	
	@Override
	public List<OredeShippFlowSummaryBean> selectOredeShippFlowSummaryBean() throws Exception {
        OredeShippFlowSummaryBean oredeShippFlowSummaryBean = new OredeShippFlowSummaryBean();
		
		return  (List<OredeShippFlowSummaryBean>) oredeShippFlowSummaryMapper.selectOredeShippFlowSummaryBean(oredeShippFlowSummaryBean);
	}

	
	@Override
	public String exportOredeShippFlowSummaryBean(String path, String warehouseName, String storeName, String transportName, String beginDateTime, String endDateTime) throws Exception {
		OredeShippFlowSummaryBean oredeShippFlowSummaryBean = new OredeShippFlowSummaryBean();
		oredeShippFlowSummaryMapper.exportOredeShippFlowSummaryBean(oredeShippFlowSummaryBean);
		String uuid = UUID.randomUUID().toString();
		List<Map<String,Object>> list = (List<Map<String, Object>>) oredeShippFlowSummaryMapper.exportOredeShippFlowSummaryBean(getBean(warehouseName,storeName,transportName,beginDateTime,endDateTime));
		String filePath = path + "/OredeShippFlowSummary";
	    String fileName = uuid;
	    String[] headers = new String[] {"仓库名称 ","始发地(省)","店铺代码  ","店铺名称","快递代码 ","快递名称 ","目的地(省)","目的地(市)","运单数 ","揽件数","签收数"};
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
	private OredeShippFlowSummaryBean getBean(String warehouseName, String storeName, String transportName,
			String beginDateTime, String endDateTime) {
		OredeShippFlowSummaryBean oredeShippFlowSummaryBean = new OredeShippFlowSummaryBean();
		oredeShippFlowSummaryBean.setWarehouseName(warehouseName);
		oredeShippFlowSummaryBean.setStoreName(storeName);
		oredeShippFlowSummaryBean.setTransportName(transportName);
		oredeShippFlowSummaryBean.setBeginDateTime(beginDateTime);
		oredeShippFlowSummaryBean.setEndDateTime(endDateTime);
		return oredeShippFlowSummaryBean;
	}
}
