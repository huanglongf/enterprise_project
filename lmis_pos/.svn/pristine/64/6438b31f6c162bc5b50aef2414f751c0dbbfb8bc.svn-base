package com.lmis.pos.common.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Will.Wang
 * @date 2018年5月29日
 */
public class StringUtil{

    /**
     * @param obj
     */
    public static boolean isNotEmpty(Object obj){
        // 空对象
        if (obj == null){
            return false;
        }
        // 空字符串
        if (obj.toString().equals("")){
            return false;
        }
        // 空数组
        if (obj.getClass().isArray()){
            // 如果是数组
            Object[] objs = (Object[]) obj;
            if (objs.length == 0){
                // 当数组长度为0，则认定为空
                return false;
            }
        }
        // 空集合
        if (obj instanceof List){
            //  为List集合
            List<?> objList = (List<?>) obj;
            if (objList.size() == 0){
                // 当List集合长度为0，则认定为空
                return false;
            }
        }
        return true;
    }
    
    /**
     * 判断是否含有特殊字符
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
    
}
