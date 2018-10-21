package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;

/**
* @ClassName: AllVolumeCalculationMapper
* @Description: TODO(AllVolumeCalculationMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface AllVolumeCalculationMapper<T> extends BaseMapper<T> {

	public List<Map<String, Object>> findByCBID(@Param("cbid")String id);
}
