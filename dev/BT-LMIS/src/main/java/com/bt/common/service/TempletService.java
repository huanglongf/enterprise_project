package com.bt.common.service;

import java.util.List;
import java.util.Map;

import com.bt.common.controller.param.Parameter;
import com.bt.common.controller.result.Result;
import com.bt.lmis.page.QueryResult;

/** 
 * @ClassName: TempletService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年8月17日 上午11:03:57 
 * 
 * @param <T>
 */
public interface TempletService<T> {
	
	/**
	 * @Title: listTableColumnConfig
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月17日 下午3:47:48
	 */
	List<Map<String, Object>> listTableColumnConfig(Parameter parameter);
	
	/**
	 * @Title: listTableColumn
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月17日 下午3:47:31
	 */
	List<Map<String, Object>> listTableColumn(Parameter parameter);
	
	/**
	 * @Title: saveTableColumnConfig
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: Result
	 * @author: Ian.Huang
	 * @date: 2017年8月18日 上午9:59:25
	 */
	Result saveTableColumnConfig(Parameter parameter);
	
	/**
	 * @Title: searchTempletTest
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: QueryResult<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月18日 下午2:41:27
	 */
	QueryResult<Map<String, Object>> searchTempletTest(Parameter parameter);
	
	/**
	 * @Title: loadingTableColumnConfig
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年8月19日 下午6:39:55
	 */
	List<Map<String, Object>> loadingTableColumnConfig(Parameter parameter);
	
}
