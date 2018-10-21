package com.bt.lmis.balance.util;

import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.bt.lmis.balance.controller.param.Parameter;
import com.bt.lmis.balance.model.DomainModel;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;

/** 
 * @ClassName: CommonUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月10日 下午3:29:14 
 * 
 */
public class CommonUtil {
	
	/**
	 * @Title: formatExceptionStack
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param ex
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年5月10日 下午3:38:00
	 */
	public static String formatExceptionStack(Throwable ex) {
		StackTraceElement[] stackTraceElements=ex.getStackTrace();
		String exception=ex.toString()+"\n";
		for (int index=stackTraceElements.length - 1; index>=0; --index) {
			exception+="at ["+stackTraceElements[index].getClassName()+",";
			exception+=stackTraceElements[index].getFileName()+",";
			exception+=stackTraceElements[index].getMethodName()+",";
			exception+=stackTraceElements[index].getLineNumber()+"]\n";
		}
		return exception;
		
	}
	
	/**
	 * @Title: isInDomain
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param dm
	 * @param num
	 * @return: boolean
	 * @author: Ian.Huang
	 * @date: 2017年5月10日 下午9:08:24
	 */
	public static boolean isInDomain(DomainModel dm, BigDecimal num){
		if(!CommonUtils.checkExistOrNot(dm.getNum2())){
			dm.setNum2(CommonUtils.getMax(new BigDecimal[]{dm.getNum1(), num}).add(new BigDecimal(1)));
			
		}
		if((num.compareTo(dm.getNum1()) == 1 && num.compareTo(dm.getNum2()) == -1)
				|| (dm.getCompare1() == 1 && num.compareTo(dm.getNum1()) == 0)
				|| (dm.getCompare2() == 3 && num.compareTo(dm.getNum2()) == 0)
				) {
			return true;
			
		} 
		return false;
		
	}
	
	/**
	 * @Title: generateParameter
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @return: Parameter
	 * @author: Ian.Huang
	 * @date: 2017年5月23日 下午2:23:00
	 */
	public static Parameter generateParameter(HttpServletRequest request) {
		return new Parameter(SessionUtils.getEMP(request), CommonUtils.request2Map(request));
		
	}
	
	
	public static String getAllMessage(String resourceName, String key) {
		// 获得资源
		ResourceBundle rb = ResourceBundle.getBundle(resourceName.trim());
		// 获取资源所有的key
		Enumeration<String> allKey = rb.getKeys();
		// 遍历key得到value
		while (allKey.hasMoreElements()) {
			if(key.equals(allKey.nextElement())) {
				return (String) rb.getString(key);
				
			}
			
		}
		return null;
		
	}
	
}