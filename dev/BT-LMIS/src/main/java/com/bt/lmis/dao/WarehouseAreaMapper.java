package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;

/**
* @ClassName: WarehouseAreaMapper
* @Description: TODO(WarehouseAreaMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface WarehouseAreaMapper<T> extends BaseMapper<T> {
	
	/** 
	* @Title: findByWhid 
	* @Description: TODO(根据仓库ID查询仓库区域) 
	* @param @param id
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> findByWhid(@Param("id")int id);
	
}
