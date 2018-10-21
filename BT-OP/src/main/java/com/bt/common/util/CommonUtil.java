package com.bt.common.util;

import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;


import com.alibaba.druid.util.StringUtils;
import com.bt.base.BaseConstant;

import sun.misc.BASE64Encoder;

/** 
 * @ClassName: CommonUtil
 * @Description: TODO(通用工具类)
 * @author Ian.Huang
 * @date 2017年4月27日 下午5:28:16 
 * 
 */
public class CommonUtil {  
    
    private final static String[] AREA_FIELD  = {"自治州","自治省","自治区","自治县",
                                                "地区",
                                                "镇","县","旗","区","市","省"};
    
	/**
	 * @Title: isNull
	 * @Description: TODO(对象为空返回true不为空返回false)
	 * @param obj
	 * @return: boolean
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午5:32:05
	 */
	public static boolean isNull(Object obj) {
    	// 空对象
    	if(obj == null){
    		return true;
    		
    	}
    	// 空字符串
    	if(obj.toString().equals("")){
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
    		//	为List集合
    		List<?> objList =  (List<?>) obj;
    		if(objList.size() == 0){
    			// 当List集合长度为0，则认定为空
    			return true;
    			
    		}
    		
    	}
    	return false;
    	
    }
	
	/**
	 * @Title: getResource
	 * @Description: TODO(获取资源中给定key对应的值
	 * 调用方式： 
	 * 1.配置文件放在resource源包下，不用加后缀"config" 
 	 * 2.放在包里面的"com.bt.lmis.config"
	 * )
	 * @param resourceName
	 * @param key
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午5:39:28
	 */
	private static String getResource(String resourceName, String key) {
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
 	
 	/**
 	 * @Title: getConfig
 	 * @Description: TODO(获取配置文件中给定key对应的值)
 	 * @param key
 	 * @return: String
 	 * @author: Ian.Huang
 	 * @date: 2017年4月27日 下午5:43:37
 	 */
 	public static String getConfig(String key) {
 		return getResource(BaseConstant.CONFIG_NAME, key);
 		
 	}
 	
	/**
	 * 
	 * @Description: TODO(base64加密)
	 * @param b
	 * @return
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年9月9日下午1:09:14
	 */
	public static String encodeBase64(byte[] b) {
        BASE64Encoder base64Encode = new BASE64Encoder();
        String str = base64Encode.encode(b);
        return str;
    }
	
	/**  
	 * 生成32位uuid编码  
	 * @return string  
	 */
    public static String getUUID(){    
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");    
        return uuid;    
    } 
	
	 /**
     * 
     * @Description: TODO(判断)
     * @param obj
     * @return: boolean  
     * @author Ian.Huang 
     * @date 2016年6月25日下午9:13:01
     */
    public static boolean checkExistOrNot(Object obj) {
    	// 空对象
    	if(obj == null){
    		return false;
    		
    	}
    	// 空字符串
    	if(obj.toString().equals("")){
    		return false;
    		
    	}
    	// 空数组
    	if(obj.getClass().isArray()){
    		// 如果是数组
    		Object[] objs = (Object[]) obj;
    		if(objs.length == 0){
    			// 当数组长度为0，则认定为空
    			return false;
    			
    		}
    		
    	}
    	// 空集合
    	if(obj instanceof List){
    		//	为List集合
    		List<?> objList =  (List<?>) obj;
    		if(objList.size() == 0){
    			// 当List集合长度为0，则认定为空
    			return false;
    			
    		}
    		
    	}
    	return true;
    	
    }
    
    
    /**
	 * 
	 * @Description: TODO
	 * @param e
	 * @return
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年9月25日下午7:10:36
	 */
	public static String getExceptionStack(Exception e) {
		StackTraceElement[] stackTraceElements = e.getStackTrace();
		String result = e.toString() + "\n";
		for (int index = stackTraceElements.length - 1; index >= 0; --index) {
			result += "at [" + stackTraceElements[index].getClassName() + ",";
			result += stackTraceElements[index].getFileName() + ",";
			result += stackTraceElements[index].getMethodName() + ",";
			result += stackTraceElements[index].getLineNumber() + "]\n";
		}
		return result;
	}
	
	/**
	 * 
	 * <b>方法名：</b>：handleViewArea<br>
	 * <b>功能说明：</b>：从后剪除省市地区行政字段 ex：北京市--北京、朝阳区--朝阳<br>
	 * @author <font color='blue'>chenkun</font> 
	 * @date  2018年3月20日 上午9:33:16
	 * @param area
	 * @return 
	 */
	public static String handleViewArea(String area){
	   
        if(area==null||area.trim().length()==0){
            return null;
        }
        
        area = area.replaceAll("\\u00A0","").trim();
        
	    for (String field : AREA_FIELD){
            if(area.endsWith(field)){
                return area.substring( 0, area.length() - field.length());
            }
        }
        return area;
    }
}
