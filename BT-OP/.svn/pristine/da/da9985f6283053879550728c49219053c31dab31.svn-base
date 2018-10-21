package com.bt.orderPlatform.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.orderPlatform.controller.form.AreaQueryParam;
import com.bt.orderPlatform.model.Area;


/**
* @ClassName: AreaMapper
* @Description: TODO(AreaMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface AreaMapper<T> {
	
	public List<Map<String, Object>> findChildren(Map param);

	public Object selectById(int id);

	public void updateTree(AreaQueryParam parma);

	public void insert(T area);

	public void batchDelete(Integer[] iID);

	public List<String> selectByIdPid(int id);

	public void deleteById(Integer id);

	public List findArea(Area area);

	public List findRecordsByP_code(Area area);

	public List<Area> getArea(@Param("area_code")String area_code);

	public String queryByAreaCode(String area_code);
	

	
}
