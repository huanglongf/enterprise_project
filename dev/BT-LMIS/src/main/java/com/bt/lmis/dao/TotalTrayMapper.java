package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;

/**
* @ClassName: TotalTrayMapper
* @Description: TODO(TotalTrayMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface TotalTrayMapper<T> extends BaseMapper<T> {

	public List<Map<String, Object>> findByCBID(@Param("cbid")String id);
	
}
