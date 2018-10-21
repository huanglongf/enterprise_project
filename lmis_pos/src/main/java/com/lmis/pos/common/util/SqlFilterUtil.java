package com.lmis.pos.common.util;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class SqlFilterUtil {

//	private String inj_str = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
	private final static String inj_str = "exec |insert |delete |update |chr |master |truncate |char |declare |;|#|--";
	private final static String inj_strict_str = "exec |insert |delete |update |chr |master |truncate |char |declare |;|#|--|'";
	public final static String MYSQL_DATE_FORMAT = "%Y-%m-%d %H:%i:%s";
	public final static String JAVA_POI_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**正则表达式**/  
    private static String reg = "(?:')|(?:--)|(?:#)|(/\\*(?:.|[\\n\\r])*?\\*/)|"  
                                + "(\\b(select|update|union|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";  
  
//  表示 限定单词边界  比如  select 不通过   1select则是可以的  
    private static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);  

	/**
	 * Title: sqlFilter(宽松模式)
	 * Description: false 不存在禁用关键字,true存在禁用关键字
	 * @param execSql  可执行sql
	 * @param isStrict 是否严格，true严格模式，fasle宽松模式(默认)
	 * @return
	 * @author lsh8044
	 * @date 2018年4月18日
	 */
	public static Boolean sqlFilter(String execSql,boolean... isStrict){
		if(StringUtils.isBlank(execSql)) {
			return true;
		}
		String[] inj_stra = null;
		
		if(isStrict != null && isStrict.length>0 && isStrict[0]) {
			inj_stra = inj_strict_str.split("\\|");
		} else {
			inj_stra = inj_str.split("\\|");
		}
		
		for (int i=0 ; i < inj_stra.length ; i++ ){
			if (execSql.indexOf(inj_stra[i])>=0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Title: sqlFilterValid
	 * Description: sql关键字过滤过滤 false不存在非法字符，true存在非法字符
	 * @param str
	 * @return
	 * @author lsh8044
	 * @date 2018年5月15日
	 */
	public static boolean sqlFilterValid(String str) {  
        return sqlPattern.matcher(str).find();  
    } 
	
	public static void main(String[] args) {
		System.out.println(sqlFilterValid("id#"));
	}
}
