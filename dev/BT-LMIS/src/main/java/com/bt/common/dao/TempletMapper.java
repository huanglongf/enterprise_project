package com.bt.common.dao;

import java.util.List;
import java.util.Map;

import com.bt.common.controller.param.Parameter;
import com.bt.common.model.TableColumnConfig;

/** 
 * @ClassName: TempletMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年8月16日 下午7:16:10 
 * 
 * @param <T>
 */
public interface TempletMapper<T> {
	
	/**
	 * @Title: listTableColumnConfig
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月17日 下午3:55:49
	 */
	List<Map<String, Object>> listTableColumnConfig(Parameter parameter);
	
	/**
	 * @Title: listTableColumn
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月17日 下午3:59:15
	 */
	List<Map<String, Object>> listTableColumn(Parameter parameter);
	
	/**
	 * @Title: delTableColumnConfig
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年8月18日 上午10:14:03
	 */
	int delTableColumnConfig(Parameter parameter);
	
	/**
	 * @Title: saveTableColumnConfig
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param tableColumnConfig
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年8月18日 下午1:23:49
	 */
	int saveTableColumnConfig(List<TableColumnConfig> tableColumnConfig);
	
	/**
	 * @Title: searchTempletTest
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月18日 下午2:32:36
	 */
	List<Map<String, Object>> searchTempletTest(Parameter parameter);
	
	/**
	 * @Title: countTempletTest
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年8月18日 下午2:44:04
	 */
	int countTempletTest(Parameter parameter);
	
}
