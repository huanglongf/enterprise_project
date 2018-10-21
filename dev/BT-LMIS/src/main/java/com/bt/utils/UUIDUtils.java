package com.bt.utils;

import java.util.UUID;

/**
 * @Title:UUIDUtils
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2017年1月11日下午2:46:08
 */
public class UUIDUtils {

	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",  
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",  
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",  
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",  
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
            "W", "X", "Y", "Z" };
	
	/**
	 * 
	 * @Description: TODO(获取长度为8的UUID)
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年1月11日下午2:48:38
	 */
	public static String getUUID8L() {
		StringBuffer shortBuffer = new StringBuffer();  
	    String uuid = UUID.randomUUID().toString().replace("-", "");  
	    for (int i = 0; i < 8; i++) {  
	        shortBuffer.append(chars[Integer.parseInt(uuid.substring(i * 4, i * 4 + 4), 16) % 0x3E]);
	        
	    }  
	    return shortBuffer.toString();
		
	}
	
	public static void main(String[] args) {
		System.out.println(getUUID8L());
		
	}
	
}
