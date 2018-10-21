package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.AddservicefeeData;

/**
* @ClassName: AddservicefeeDataMapper
* @Description: TODO(AddservicefeeDataMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface AddservicefeeDataMapper<T> extends BaseMapper<T> {
	public List<AddservicefeeData> findByAll(AddservicefeeData afd);
	public List<AddservicefeeData> findBySection(AddservicefeeData afd);
	public List<Map<String, String>> findGroupByServiceName(AddservicefeeData afd);
	public void update_settleFlag(@Param("id")String id);
	public void  update_settleFlagToZero(AddservicefeeData afd);
	
}
