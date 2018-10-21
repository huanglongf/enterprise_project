package com.bt.vims.utils;

import java.io.FileOutputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;

public class Base64Util {
	public static byte[] GenerateImage(String imgStr,String url) { 
		// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null){
			// 
			System.out.println("图像数据为空");
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			String imgFilePath = url;// 新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return b;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
