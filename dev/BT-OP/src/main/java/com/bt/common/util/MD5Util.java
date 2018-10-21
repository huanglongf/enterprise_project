package com.bt.common.util;
import java.security.MessageDigest;

import sun.misc.BASE64Encoder;

/** 
 * @ClassName: MD5Util
 * @Description: TODO(MD5加密工具类)
 * @author Ian.Huang
 * @date 2017年4月27日 下午10:00:29 
 * 
 */
public class MD5Util {
    public final static String md5(String s) {
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
                
            }
            return new String(str);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
            
        }
        
    }
    
    
    public static byte[] md5Encrypt(String encryptStr) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(encryptStr.getBytes("utf8"));
            return md5.digest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
}