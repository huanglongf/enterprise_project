package com.lmis.common.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @author daikaihua
 * @date 2018年1月19日
 * @todo TODO base64加密解密工具类
 */
public class Base64Utils {

	/**
	 * @Title: decoderBase64
	 * @Description: TODO通过UTF-8格式来解码base64加密后的数据
	 * @param str
	 * @return: String
	 * @author: daikaihua
	 * @date: 2018年1月19日
	 */
	public static String decoderBase64(String str){
		byte[] strBt = Base64.getDecoder().decode(str);
		String rstStr = null;
		try {
			rstStr = new String(strBt, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return rstStr;
	}
	
	/**
	 * @Title: encoderBase64
	 * @Description: TODO通过UTF-8格式，使用base64来加密数据
	 * @param str
	 * @return: String
	 * @author: daikaihua
	 * @date: 2018年1月19日
	 */
	public static String encoderBase64(String str){
		String rstStr = null;
		try {
			rstStr = Base64.getEncoder().encodeToString(str.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return rstStr;
	}
	
}