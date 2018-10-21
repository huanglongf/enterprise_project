package com.jumbo.util.cxc;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 接口/类说明：由CXC物流商 提供
 * 
 * @ClassName: CxcMd5
 * @author LuYingMing
 * @date 2016年6月13日 下午1:28:47
 */
public class CxcMd5 {

    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    protected static final Logger logger = LoggerFactory.getLogger(CxcMd5.class);

    /**
     * 转换字节数组为16进制字串
     * 
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    /**
     * 转换byte到16进制
     * 
     * @param b 要转换的byte
     * @return 16进制格式
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * MD5编码
     * 
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(resultString.getBytes("UTF-8"));
            resultString = byteArrayToHexString(md.digest());
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("MD5Encode", e);
            }
        }
        return resultString;
    }

    /**
     * 
     * @Title: digest
     * @Description:
     * @param Datekey
     * @return String 返回类型
     * @throws
     */
    public static String base64MD5LowerCase(String Datekey) {

        Datekey = CxcMd5.MD5Encode(Datekey).toLowerCase();
        return CxcBase64.encode(Datekey);
    }

    /**
     * 
     * @Title: getSign
     * @Description:
     * @param map
     * @param mapkey
     * @return String 返回类型
     * @throws
     */
    public static String getSign(Map<String, Object> map, String mapkey) {
        ArrayList<String> list = new ArrayList<String>();

        Set<String> objSet = map.keySet();
        for (Object key : objSet) {
            if (key == null) {
                continue;
            }
            if (!("sign").equals(key.toString())) {
                list.add(key.toString() + "=" + map.get(key) + "&");
            }
        }

        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += mapkey;

        result = CxcMd5.MD5Encode(result).toLowerCase();

        return result;
    }

    /**
     * 
     * @Title: isTenpaySign
     * @Description:
     * @param map
     * @param mapkey
     * @param sign
     * @return boolean 返回类型
     * @throws
     */
    public static boolean isTenpaySign(Map<String, Object> map, String mapkey, String sign) {

        String s = getSign(map, mapkey);
        String tenpaySign = base64MD5LowerCase(s);
        return tenpaySign.equals(sign);
    }
}
