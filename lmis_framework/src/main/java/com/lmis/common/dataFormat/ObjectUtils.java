package com.lmis.common.dataFormat;

import java.util.List;

/** 
 * @ClassName: CommonUtils
 * @Description: TODO(通用工具类)
 * @author Ian.Huang
 * @date 2017年9月28日 下午6:32:16 
 * 
 */
public class ObjectUtils {

	/**
	 * @Title: isNull
	 * @Description: TODO(判断对象是否为空)
	 * @param obj
	 * @return: boolean
	 * @author: Ian.Huang
	 * @date: 2017年9月26日 上午11:19:24
	 */
	public static boolean isNull(Object obj) {
    	// 空对象
    	if(obj == null){
    		return true;
    	}
    	// 空字符串
    	if("".equals(obj.toString())){
    		return true;
    	}
    	// 空数组
    	if(obj.getClass().isArray()){
    		// 如果是数组
    		Object[] objs = (Object[]) obj;
    		if(objs.length == 0){
    			// 当数组长度为0，则认定为空
    			return true;
    		}
    	}
    	// 空集合
    	if(obj instanceof List){
    		// 为List集合
    		List<?> objList =  (List<?>) obj;
    		if(objList.size() == 0){
    			// 当List集合长度为0，则认定为空
    			return true;
    		}
    	}
    	return false;
    }
	
}
