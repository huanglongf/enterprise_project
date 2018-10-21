package com.jumbo.util.qrcode;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class ZXingUtil {

    public static final int DEFAULT_SIZE = 200;
    public static final String DEFAULT_FORMAT = "png";


    /**
     * 生成QR code，200*200，PNG格式
     * 
     * @param content
     * @return
     * @throws Exception
     */
    public static byte[] genQRCode(String content) throws Exception {
        return genQRCode(DEFAULT_SIZE, DEFAULT_FORMAT, content);
    }

    /**
     *
     * @param size
     * @param format
     * @param content
     * @return
     * @throws Exception
     */
    public static byte[] genQRCode(int size, String format, String content) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);// 生成矩阵

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, format, bout);
        return bout.toByteArray();
    }

    /**
     * 自定义边框
     * 
     * @param size
     * @param margin
     * @param format
     * @param content
     * @return
     */
    public static byte[] genQRCode(int size, int margin, String format, String content) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);// 生成矩阵
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        bitMatrix = updateBit(bitMatrix, margin); // 生成新的bitMatrix
        // 因为二维码生成时，白边无法控制，去掉原有的白边，再添加自定义白边后，二维码大小与size大小就存在差异了，为了让新
        // 生成的二维码大小还是size大小，根据size重新生成图片
        BufferedImage bi = MatrixToImageWriter.toBufferedImage(bitMatrix);
        bi = zoomInImage(bi, size, size);// 根据size放大、缩小生成的二维码
        ImageIO.write(bi, format, bout);
        return bout.toByteArray();
    }

    /**
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static String genQRCodeBase64(String content) throws Exception {
        return Base64Util.encode(genQRCode(content));
    }

    /**
     *
     * @param size
     * @param format
     * @param content
     * @return
     * @throws Exception
     */
    public static String genQRCodeBase64(int size, String format, String content) throws Exception {
        return Base64Util.encode(genQRCode(size, format, content));
    }


    /**
     * 因为二维码边框设置那里不起作用，不管设置多少，都会生成白边，所以根据网上的例子进行修改，自定义控制白边宽度， 该方法生成自定义白边框后的bitMatrix；
     * 
     * @param matrix
     * @param margin
     * @return
     */
    public static BitMatrix updateBit(BitMatrix matrix, int margin) {
        int tempM = margin * 2;
        int[] rec = matrix.getEnclosingRectangle(); // 获取二维码图案的属性
        int resWidth = rec[2] + tempM;
        int resHeight = rec[3] + tempM;
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight); // 按照自定义边框生成新的BitMatrix
        resMatrix.clear();
        for (int i = margin; i < resWidth - margin; i++) { // 循环，将二维码图案绘制到新的bitMatrix中
            for (int j = margin; j < resHeight - margin; j++) {
                if (matrix.get(i - margin + rec[0], j - margin + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }

    public static BufferedImage zoomInImage(BufferedImage originalImage, int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return newImage;

    }

    public static void main(String[] args) throws Exception {
        String xxx = genQRCodeBase64("ddddddddddddddddddddddddddddddddddddasdpoasiporwjerlk30-923842098423098423-908402394");
        System.out.println(xxx);
    }
}
