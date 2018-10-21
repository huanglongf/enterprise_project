package com.bt.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;

import javax.imageio.stream.FileImageOutputStream;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.BarcodeEncoder;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;

public class OneBarcodeUtil {
	/**
	 * 生成一维码
	 * 
	 * @param value
	 *            值
	 * @return
	 */
	public static byte[] createBarcodeDefault(String value) {
		return createBarcode(Code128Encoder.class, value, false);
	}

	// 产生一维码图片
	public static byte[] createBarcode(Class<?> clazz, String value, boolean checkDigit) {
		try {
			JBarcode localJBarcode = new JBarcode(getInstance(clazz), WidthCodedPainter.getInstance(),BaseLineTextPainter.getInstance());
			/*
			 * 参考http://www.aichengxu.com/view/22024
			 * localJBarcode.setPainter(WideRatioCodedPainter.getInstance());
			 * localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());
			 */
			localJBarcode.setCheckDigit(checkDigit);
			localJBarcode.setShowCheckDigit(checkDigit);
			localJBarcode.setShowText(false);
			return getBytes(localJBarcode.createBarcode(value));
			/**
			 * jBarcode.setShowText(true);//显示图片下字符串内容
			 * jBarcode.setShowCheckDigit(true);//显示字符串内容中是否显示检查码内容
			 * jBarcode.setCheckDigit(false);//不生成检查码
			 * 检查码又叫校验码。一般是条码数据中的最后一位，它由条码的主体数据按一定规则计算而来，用扫描器扫描时，
			 * 会把读到的数据按同样的规则计算一个校验码再和读到的校验码进行比较，如果一致说明读到的数据是正确的，不一致则说明读取的数据有误。
			 */
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 获取单例的对象
	private static BarcodeEncoder getInstance(Class<?> clazz) throws Exception {
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		Constructor<?> privateConstructor = constructors[0];
		privateConstructor.setAccessible(true);
		return (BarcodeEncoder) privateConstructor.newInstance();

	}

	// 获取图片字节码数组
	private static byte[] getBytes(BufferedImage paramBufferedImage) throws IOException {
		return ImageUtil.encode(paramBufferedImage, "png", 96, 96);// jpeg
	}

	//byte数组到图片
	public static void byte2image(byte[] data,String path){
		if(data.length<3||path.equals("")) return;
		try{
			FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
			imageOutput.write(data, 0, data.length);
			imageOutput.close();
			System.out.println("Make Picture success,Please find image in " + path);
		} catch(Exception ex) {
			System.out.println("Exception: " + ex);
			ex.printStackTrace();
		}
	}
	//byte数组到16进制字符串
	public String byte2string(byte[] data){
		if(data==null||data.length<=1) return "0x";
    	if(data.length>200000) return "0x";
		StringBuffer sb = new StringBuffer();
		int buf[] = new int[data.length];
		//byte数组转化成十进制
		for(int k=0;k<data.length;k++){
			buf[k] = data[k]<0?(data[k]+256):(data[k]);
		}
		//十进制转化成十六进制
		for(int k=0;k<buf.length;k++){
			if(buf[k]<16) sb.append("0"+Integer.toHexString(buf[k]));
			else sb.append(Integer.toHexString(buf[k]));
		}	
		return "0x"+sb.toString().toUpperCase();
	}
	
	
	public static void main(String[] args) {
		byte[] b = createBarcodeDefault("test000000001");
		OneBarcodeUtil.byte2image(b, "/usr/local/log/test000000001.png");
	}
}
