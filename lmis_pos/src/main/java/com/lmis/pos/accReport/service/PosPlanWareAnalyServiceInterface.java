package com.lmis.pos.accReport.service;

import org.springframework.stereotype.Service;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.accReport.model.PosPlanWareAnalyAreaScale;

/**
 * 
 *@author jinggong.li
 *@date 2018年6月26日  
 * @param <T>
 */
@Service
public interface PosPlanWareAnalyServiceInterface<T extends PosPlanWareAnalyAreaScale> {
	
	//查询数据
    LmisResult<?> selectPosPlanWareAnalyAreaScale(DynamicSqlParam<PosPlanWareAnalyAreaScale> dynamicSqlParam,
			                 LmisPageObject pageObject, String beginDateTime, String endDateTime,String bu,String whsName) throws Exception;
    
    LmisResult<?> selectWhsallocate(DynamicSqlParam<PosPlanWareAnalyAreaScale> dynamicSqlParam)throws Exception;
    
    //导出PO计划入库分析数据
    public LmisResult<?> exportPosPlanWareAnalyAreaScale(String templeteId,String suffixName, String fileName, DynamicSqlParam<PosPlanWareAnalyAreaScale> dynamicSqlParam, 
    		                                             String beginDateTime, String endDateTime, String bu, String whsName)throws Exception;


}
