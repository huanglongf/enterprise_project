package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.page.QueryParameter;

/**
* @ClassName: InvitationUseanmountDataMapper
* @Description: TODO(InvitationUseanmountDataMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface InvitationUseanmountDataMapper<T> extends BaseMapper<T> {
	
	/** 
	* @Title: findAll 
	* @Description: TODO(查询所有耗材使用数据) 
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	List<Map<String, Object>> findAll();
	
	/** 
	* @Title: findAll 
	* @Description: TODO(查询所有耗材使用数据关联基础信息表) 
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	List<Map<String, Object>> findAllData(QueryParameter queryParam);
	
	
	Integer findDataCount(QueryParameter queryParam);
}
