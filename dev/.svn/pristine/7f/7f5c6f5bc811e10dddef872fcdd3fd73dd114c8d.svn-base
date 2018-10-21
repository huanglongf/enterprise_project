package com.bt.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.bt.lmis.model.Employee;


/** 
* @ClassName: SessionUtils 
* @Description: TODO(Cookie 工具类) 
* @author Yuriy.Jiang
* @date 2016年5月27日 下午6:09:40 
*  
*/
public final class SessionUtils {

	protected static final Logger logger = Logger.getLogger(SessionUtils.class);

	private static final String SESSION_EMPLOYEE = "session_employee";//当前登录的用户

	/** 
	 * @Title: setAttr 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param request
	 * @param key
	 * @param value
	 * @param interval
	 * @return void 返回类型
	 * @author Ian.Huang
	 * @throws
	 * @date 2017年4月25日 上午11:41:53
	 */
	public static void setAttr(HttpServletRequest request, String key, Object value, int interval) {
		setAttr(request, key, value);
		request.getSession().setMaxInactiveInterval(interval);
		
	}
	
	/**
	  * 设置session的值
	  * @param request
	  * @param key
	  * @param value
	  */
	 public static void setAttr(HttpServletRequest request,String key,Object value){
		 request.getSession(true).setAttribute(key, value);
	 }
	 
	 /**
	  * 获取session的值
	  * @param request
	  * @param key
	  * @param value
	  */
	 public static Object getAttr(HttpServletRequest request,String key){
		 return request.getSession(true).getAttribute(key);
	 }
	 
	 /**
	  * 删除Session值
	  * @param request
	  * @param key
	  */
	 public static void removeAttr(HttpServletRequest request,String key){
		 request.getSession(true).removeAttribute(key);
	 }
	 
	 /**
	  * 设置用户信息 到session
	  * @param request
	  * @param user
	  */
	 public static void setUser(HttpServletRequest request,Employee employee){
		 request.getSession(true).setAttribute(SESSION_EMPLOYEE, employee);
	 }
	 
	 
	 /**
	  * 从session中获取用户信息
	  * @param request
	  * @return SysUser
	  */
	 public static Employee getEMP(HttpServletRequest request){
		return (Employee)request.getSession(true).getAttribute(SESSION_EMPLOYEE);
	 }
}
