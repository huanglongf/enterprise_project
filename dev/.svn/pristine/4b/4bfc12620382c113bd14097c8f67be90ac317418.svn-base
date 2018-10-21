package com.bt.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.bt.lmis.base.JSON_TYPE;

/**
 * @Title:JSONUtils
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年11月30日下午5:40:43
 */
public class JSONUtils {
	
	/**
	 * 
	 * @Description: TODO(获取JSON类型-判断规则-判断第一个字母是否为{或[ 如果都不是则不是一个JSON格式的文本)
	 * @param str
	 * @return: JSON_TYPE  
	 * @author Ian.Huang 
	 * @date 2016年11月30日下午5:44:16
	 */
	public static JSON_TYPE getJSONType(String str){
		if(!CommonUtils.checkExistOrNot(str)){
			return JSON_TYPE.JSON_TYPE_ERROR;
		}
		char firstChar= str.substring(0, 1).toCharArray()[0];
		if(firstChar == '{'){
			return JSON_TYPE.JSON_TYPE_OBJECT;
		} else if(firstChar == '['){
			return JSON_TYPE.JSON_TYPE_ARRAY;
		} else {
			return JSON_TYPE.JSON_TYPE_ERROR;
		}
	}
	
    /** 
    * @Title: mapToJson 
    * @Description: TODO(map转换json.) 
    * @param @param map 集合
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws 
    */
    public static String mapToJson(Map<String, String> map) {
        Set<String> keys = map.keySet();
        String key = "";
        String value = "";
        StringBuffer jsonBuffer = new StringBuffer();
        jsonBuffer.append("{");
        for (Iterator<String> it = keys.iterator(); it.hasNext();) {
            key = (String) it.next();
            value = map.get(key);
            jsonBuffer.append(key + ":" +"\""+ value+"\"");
            if (it.hasNext()) {
                jsonBuffer.append(",");
            }
        }
        jsonBuffer.append("}");
        return jsonBuffer.toString();
    }
	
}
