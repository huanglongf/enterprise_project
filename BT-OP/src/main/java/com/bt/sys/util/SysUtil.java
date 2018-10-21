package com.bt.sys.util;

import javax.servlet.http.HttpServletRequest;

import com.bt.base.BaseConstant;
import com.bt.base.session.SessionUtil;
import com.bt.common.util.CommonUtil;
import com.bt.sys.model.Account;
import com.bt.sys.model.BusinessPower;

/** 
 * @ClassName: SysUtil
 * @Description: TODO(系统工具)
 * @author Ian.Huang
 * @date 2017年4月27日 下午5:23:32 
 * 
 */
public class SysUtil {

	/**
	 * @Title: getAccountInSession
	 * @Description: TODO(获取会话中的账号信息)
	 * @param request
	 * @return: Account
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午5:25:55
	 */
	public static Account getAccountInSession(HttpServletRequest request) {
		return (Account) SessionUtil.getAttr(request, BaseConstant.LOGIN_ACCOUNT);
		
	}

	public static BusinessPower getPowerSession(HttpServletRequest request) {
		return (BusinessPower) SessionUtil.getAttr(request, BaseConstant.LOGIN_POWER);
		
	}
	
	/**
	 * @Title: setAccountInSession
	 * @Description: TODO(将账号信息存入会话)
	 * @param request
	 * @param account
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午5:27:32
	 */
	public static void setAccountInSession(HttpServletRequest request, Account account) {
		SessionUtil.setAttr(request, BaseConstant.LOGIN_ACCOUNT, account, Integer.parseInt(CommonUtil.getConfig(BaseConstant.SESSION_INTERVAL)));
		
	}
	
	/**
	 * @Title: removeAccountInSession
	 * @Description: TODO(删除会话中的账号信息)
	 * @param request
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午9:28:24
	 */
	public static void removeAccountInSession(HttpServletRequest request) {
		SessionUtil.removeAttr(request, BaseConstant.LOGIN_ACCOUNT);
		
	}
	
}