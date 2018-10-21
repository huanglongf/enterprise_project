package com.bt.lmis.balance.service;

import java.util.List;
import java.util.Map;

import com.bt.lmis.balance.controller.form.EstimateQueryParam;
import com.bt.lmis.balance.controller.param.Parameter;
import com.bt.lmis.page.QueryResult;

public interface EstimateManagementService {
	
	/**
	 * @Title: query
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @return: QueryResult<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年5月23日 下午4:41:55
	 */
	QueryResult<Map<String,Object>> query(EstimateQueryParam param);
	
	/**
	 * @Title: shiftContractByType
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @throws Exception
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年5月24日 下午1:34:26
	 */
	List<Map<String, Object>> shiftContractByType(Parameter parameter) throws Exception;
	
	/**
	 * @Title: add
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @throws Exception
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年5月24日 下午2:18:19
	 */
	String add(Parameter parameter) throws Exception;
	
	/**
	 * @Title: del
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @throws Exception
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月30日 下午2:01:20
	 */
	void del(Parameter parameter) throws Exception;
	
	/**
	 * @Title: cancel
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @throws Exception
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月30日 下午3:50:01
	 */
	void cancel(Parameter parameter) throws Exception;
	
	/**
	 * @Title: restart
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @throws Exception
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月30日 下午6:08:44
	 */
	void restart(Parameter parameter) throws Exception;
	
}
