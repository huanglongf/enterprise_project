package com.jumbo.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class UUIDUtil {

	public static String getUUID() {
		String str = UUID.randomUUID().toString();
		str.replace("-", "");
		return str;
	}

	public static String getRandomNumber() {
		return "" + (int) (Math.random() * 10);
	}

	public static String getRandomKey(int count) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String date = sdf.format(new Date());
		StringBuffer sb = new StringBuffer();
		sb.append(date);
		for (int i = 0; i < count; i++) {
			sb.append(getRandomNumber());
		}
		return sb.toString();
	}
	
	public static String getRandomNum(){
		String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";  
		StringBuffer sb = new StringBuffer();
		Random ran = new Random();
		for(int i=0;i<30;i++){
			sb.append(str.charAt(ran.nextInt(62)));
		}
		return sb.toString();
	}

    public static void main(String[] args) {
        System.out.println(UUIDUtil.getUUID());
    }
}
