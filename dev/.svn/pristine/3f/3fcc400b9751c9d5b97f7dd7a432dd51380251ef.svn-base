package com.bt.base.session;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


/** 
* @ClassName: SessionUtils 
* @Description: TODO(Cookie 工具类) 
* @author Yuriy.Jiang
* @date 2016年5月27日 下午6:09:40 
*  
*/
public final class SessionUtil {

	protected static final Logger logger = Logger.getLogger(SessionUtil.class);
	
	/**
	 * @Title: setAttr
	 * @Description: TODO(向session中存值，可控制过期时间)
	 * @param request
	 * @param key
	 * @param value
	 * @param interval
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午3:34:28
	 */
	public static void setAttr(HttpServletRequest request, String key, Object value, int interval) {
		setAttr(request, key, value);
		request.getSession().setMaxInactiveInterval(interval);
		
	}
	
	/**
	 * @Title: setAttr
	 * @Description: TODO(向session中存值)
	 * @param request
	 * @param key
	 * @param value
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午3:35:35
	 */
	public static void setAttr(HttpServletRequest request, String key, Object value){
		request.getSession(true).setAttribute(key, value);
		
	}
	
	/**
	 * @Title: getAttr
	 * @Description: TODO(获取session的值)
	 * @param request
	 * @param key
	 * @return: Object
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午3:36:52
	 */
	public static Object getAttr(HttpServletRequest request, String key) {
		return request.getSession(true).getAttribute(key);
		
	}
	
	/**
	 * @Title: removeAttr
	 * @Description: TODO(删除Session中对应key的值)
	 * @param request
	 * @param key
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午3:37:45
	 */
	public static void removeAttr(HttpServletRequest request, String key){
		request.getSession(true).removeAttribute(key);
		
	}
	 
}
