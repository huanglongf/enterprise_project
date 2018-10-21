package com.jumbo.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

import org.jfree.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.jumbo.util.qrcode.ZXingUtil;



/**
 * 生成二维码工具类
 * 
 * @author jinggang.chen
 *
 */
public class QrCodeUtil {

    /**
     * 
     * @param content 二维码内容
     * @param qrcodeWidth 宽度
     * @param qrcodeHeight 高度
     * @param path 存放路径
     * @param qrcodeFormat 格式(*.png,*.jpg,*.gif)
     * @return
     */
    public static String createQrCode(String content, int qrcodeWidth, int qrcodeHeight, String path, String qrcodeFormat) {
        String fileName = "";
        try {
            HashMap<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrcodeWidth, qrcodeHeight, hints);
            BufferedImage image = new BufferedImage(qrcodeWidth, qrcodeHeight, BufferedImage.TYPE_INT_RGB);
            // 目录不存在，创建目录
            File filePath = new File(path);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            Random random = new Random();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
            String date = format.format(new Date());
            fileName = date + "" + Math.abs(random.nextInt());
            File QrcodeFile = new File(path + fileName + "." + qrcodeFormat);
            ImageIO.write(image, qrcodeFormat, QrcodeFile);
            MatrixToImageWriter.writeToFile(bitMatrix, qrcodeFormat, QrcodeFile);
        } catch (Exception e) {
            Log.error("生成二维码失败", e);
        }
        return fileName + "." + qrcodeFormat;
    }

    /**
     * 去除二维码白边
     * 
     * @param content 二维码内容
     * @param qrcodeWidth 宽度
     * @param qrcodeHeight 高度
     * @param path 存放路径
     * @param qrcodeFormat 格式(*.png,*.jpg,*.gif)
     * @return
     */
    public static String createQrCodeDelWhiteBound(String content, int qrcodeWidth, int qrcodeHeight, String path, String qrcodeFormat) {
        String fileName = "";
        try {
            HashMap<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrcodeWidth, qrcodeHeight, hints);
            bitMatrix = ZXingUtil.updateBit(bitMatrix, 1);
            BufferedImage image = new BufferedImage(qrcodeWidth, qrcodeHeight, BufferedImage.TYPE_INT_RGB);
            // 目录不存在，创建目录
            File filePath = new File(path);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            Random random = new Random();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
            String date = format.format(new Date());
            fileName = date + "" + Math.abs(random.nextInt());
            File QrcodeFile = new File(path + fileName + "." + qrcodeFormat);
            ImageIO.write(image, qrcodeFormat, QrcodeFile);
            MatrixToImageWriter.writeToFile(bitMatrix, qrcodeFormat, QrcodeFile);
        } catch (Exception e) {
            Log.error("生成二维码失败", e);
        }
        return fileName + "." + qrcodeFormat;
    }


}
