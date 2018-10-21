package com.lmis.common.getData.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.common.getData.model.ConstantData;

/**
 * @author daikaihua
 * @date 2017年11月28日
 * @todo TODO
 */
@Mapper
@Repository
public interface GetConstantDataMapper<T extends ConstantData>{

	ConstantData getConstantData(String sqlCode);
	
	List<ConstantData> getConstantSqlData(String sqlString);
	
}
