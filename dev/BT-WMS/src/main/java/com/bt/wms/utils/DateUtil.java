package com.bt.wms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/** 
* @ClassName: DateUtil 
* @Description: TODO(日期工具类) 
* @author Yuriy.Jiang
* @date 2017年1月11日 上午10:22:27 
*  
*/
public class DateUtil {

	public static String formatS(Date date){
		SimpleDateFormat da=new SimpleDateFormat("yyyyMMdd");
		return da.format(date);
	 }
	
	public static void main(String[] args) {
		System.out.println(DateUtil.formatS(new Date()));
	}
}
