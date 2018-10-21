package com.bt.common.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bt.common.controller.param.Parameter;
import com.bt.utils.SessionUtils;

/** 
 * @ClassName: BaseController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年7月28日 下午3:12:58 
 * 
 */
public class BaseController {

	/**
	 * @Title: request2Map
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @return: Map<String,Object>
	 * @author: Ian.Huang
	 * @date: 2017年7月28日 下午3:23:46
	 */
	private Map<String, Object> request2Map(HttpServletRequest request) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(Object key:request.getParameterMap().keySet()) {
			if(key.toString().contains("[]")) {
				map.put((String)key, request.getParameterValues(key.toString()));
			} else {
				map.put((String)key, request.getParameter(key.toString()).trim());
			}
		}
		return map;
	}
	
	/**
	 * @Title: generateParameter
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @return: Parameter
	 * @author: Ian.Huang
	 * @date: 2017年7月28日 下午3:25:38
	 */
	protected Parameter generateParameter(HttpServletRequest request) {
		return new Parameter(SessionUtils.getEMP(request), request2Map(request));
	}
	
	/**
	 * @Title: generateParameter
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @param request
	 * @return: Parameter
	 * @author: Ian.Huang
	 * @date: 2017年8月21日 下午1:54:17
	 */
	protected Parameter generateParameter(Parameter parameter, HttpServletRequest request) {
		parameter.setCurrentAccount(SessionUtils.getEMP(request));
		parameter.setParam(request2Map(request));
		return parameter;
	}
}
