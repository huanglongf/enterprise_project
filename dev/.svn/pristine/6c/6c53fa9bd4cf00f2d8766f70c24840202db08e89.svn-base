package com.bt.lmis.balance.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.balance.controller.param.Parameter;
import com.bt.lmis.balance.model.RecoverProcess;

/** 
 * @ClassName: RecoverSettlementDataService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年7月11日 下午8:58:26 
 * 
 */
public interface RecoverSettlementDataService<T> {
	
	/**
	 * @Title: ensureRecoverProcess
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @throws Exception
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年7月13日 下午8:02:42
	 */
	List<Map<String, Object>> ensureRecoverProcess() throws Exception;
	
	/**
	 * @Title: addRecoverTask
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @throws Exception
	 * @return: JSONObject
	 * @author: Ian.Huang
	 * @date: 2017年7月14日 下午12:58:36
	 */
	JSONObject addRecoverTask(Parameter parameter) throws Exception;
	
	/**
	 * @Title: startRecoverProcess
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param recoverProcess
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年7月14日 下午2:17:29
	 */
	void startRecoverProcess(RecoverProcess recoverProcess);
	
	/**
	 * @Title: recoverSettlementData
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param recoverProcess
	 * @throws Exception
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年7月14日 下午2:45:52
	 */
	String recoverSettlementData(RecoverProcess recoverProcess) throws Exception;
	
}
