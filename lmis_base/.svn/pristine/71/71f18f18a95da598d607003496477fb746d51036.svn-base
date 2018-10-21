package com.lmis.common.getData.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.getData.model.ConstantData;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.setup.constantSql.model.SetupConstantSql;

/**
 * @author daikaihua
 * @date 2017年12月7日
 * @todo TODO
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface GetConstantDataServiceInterface<T extends ConstantData> {

	//公共配置详细设计:3.6.1	获取下拉列表参数
	LmisResult<SetupConstantSql> getConstantDate(String jsonStr);
	
	//公共配置详细设计:3.6.2	同步页面常量数据
	LmisResult<SetupConstantSql> redisForConstantSql();
	
	//公共配置详细设计:3.6.3	带条件获取数据
	LmisResult<ConstantData> getElementValue(ConstantData constantData);

}
