package com.lmis.pos.whsOutPlan.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.framework.baseModel.PersistentObject;
import com.lmis.pos.whsOutPlan.model.PosWhsOutPlanAreaScale;
import com.lmis.pos.whsOutPlan.model.PosWhsOutPlanLog;

/**
 * 
 *@author jinggong.li
 *@date 2018年6月22日  
 * @param <T>
 */
@Service
public interface PosWhsOutAreaScaleServiceInterface<T extends PersistentObject> {
	
	//查询数据
	LmisResult<?> selectPosWhsOutAreaScale(DynamicSqlParam<PosWhsOutPlanAreaScale> dynamicSqlParam, LmisPageObject pageObject,String size,String skuGgg) throws Exception;
	
	
	//导入数据
	LmisResult<?> savePosWhsOutAreaScaleInfo(List<Map<String, Object>> list, String name, String fileName) throws Exception;
}
