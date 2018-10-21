package com.bt.dataReport.dao;

import java.util.List;

import com.bt.dataReport.bean.OredeShippFlowSummaryBean;
import com.bt.lmis.code.BaseMapper;

/**
 *@author jinggong.li
 *@date 2018年7月3日  
 */
public interface OredeShippFlowSummaryMapper extends BaseMapper {
	
	public List<?> selectOredeShippFlowSummaryBean(OredeShippFlowSummaryBean oredeShippFlowSummaryBean) throws Exception;
	
	public List<?> exportOredeShippFlowSummaryBean(OredeShippFlowSummaryBean oredeShippFlowSummaryBean) throws Exception;
	
}
