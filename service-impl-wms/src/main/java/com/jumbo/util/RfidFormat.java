package com.jumbo.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * rfid处理
 * 
 * @author bin.hu
 *
 */
public class RfidFormat {


    /**
     * 标头
     */
    private static final String TITLE = "01000000";
    /**
     * 滤值
     */
    private static final String LZ = "001";
    /**
     * 分区
     */
    private static final String SUBAREA = "101";

    /**
     * 左脚组件号
     */
    private static final String LEFT = "0000001";

    /**
     * 右脚组件号
     */
    private static final String RIGHT = "0000010";

    /**
     * 组件总数
     */
    private static final String ZJ = "0000010";



    /**
     * 生成rfid 第一版只生成鞋子 左右脚
     * 
     * @param barCode 商品条码
     * @param sequenceNumber 序列
     * @return 左脚rfid⊥右脚rfid
     */
    public static String createRfid(String barCode, String sequenceNumber) {
        String rfid = "";
        // 厂商识别码
        String idCode = getIdCode(barCode);
        // 商品代码
        String skuCode = getSkuCode(barCode);
        // 序列号
        String sequence = getSequenceNumber(sequenceNumber);
        // 左边鞋RFID
        String leftRfid = TITLE + LZ + SUBAREA + idCode + skuCode + LEFT + ZJ + sequence;
        // 右边鞋RFID
        String rightRfid = TITLE + LZ + SUBAREA + idCode + skuCode + RIGHT + ZJ + sequence;
        // System.out.println(leftRfid);
        // System.out.println(autoGenericCode(leftRfid, 112, "R").length());
        rfid = getHexValue(getStrList(autoGenericCode(leftRfid, 112, "R"), 4, 28)) + "⊥" + getHexValue(getStrList(autoGenericCode(rightRfid, 112, "R"), 4, 28));
        // System.out.println(rfid);
        return rfid;
    }

    /**
     * 校验RFID是否属于SKUCODE
     * 
     * @param rfid
     * @param skuCode
     * @return
     */
    public static boolean checkRfid(String rfid, String skuCode) {
        // 格式成二进制
        String binary = getBinaryValue(rfid);
        // 厂商识别码
        String idCode = binaryToAlgorism(binary.substring(14, 38));
        // 商品代码
        String sCode = binaryToAlgorism(binary.substring(38, 58));
        // 比对商品条码前12位是否相同
        String rfidCode = idCode + sCode;
        if (!rfidCode.equals(skuCode.substring(0, 11))) {
            return false;
        }
        return true;
    }


    /**
     * 验证RFID序列号是否相同
     * 
     * @return
     */
    public static boolean checkRfidSequence(String left, String right) {
        // 左脚序列号
        String lCode = getBinaryValue(left);
        // 右脚序列号
        String rCode = getBinaryValue(right);
        String l = binaryToAlgorism(lCode.substring(73, lCode.length() - 2));
        String r = binaryToAlgorism(rCode.substring(73, rCode.length() - 2));
        if (!l.equals(r)) {
            return false;
        }
        return true;
    }

    /**
     * 格式化十六进制
     * 
     * @param value
     * @return
     */
    private static String getHexValue(List<String> value) {
        String returnValue = "";
        for (String v : value) {
            String b = binaryToHex(v);
            returnValue += b;
        }
        return returnValue;
    }

    /**
     * 格式化二进制
     * 
     * @param value
     * @return
     */
    private static String getBinaryValue(String code) {
        String returnValue = "";
        for (int i = 0; i < code.length(); i++) {
            returnValue += hexToBinary(code.charAt(i));
        }
        return returnValue;
    }


    /**
     * 把原始字符串分割成指定长度的字符串列表
     * 
     * @param inputString 原始字符串
     * @param length 指定长度
     * @param size 指定列表大小
     * @return
     */
    public static List<String> getStrList(String inputString, int length, int size) {
        List<String> list = new ArrayList<String>();
        for (int index = 0; index < size; index++) {
            String childStr = substring(inputString, index * length, (index + 1) * length);
            list.add(childStr);
        }
        return list;
    }

    /**
     * 分割字符串，如果开始位置大于字符串长度，返回空
     * 
     * @param str 原始字符串
     * @param f 开始位置
     * @param t 结束位置
     * @return
     */
    public static String substring(String str, int f, int t) {
        if (f > str.length()) return null;
        if (t > str.length()) {
            return str.substring(f, str.length());
        } else {
            return str.substring(f, t);
        }
    }


    /**
     * 获取厂商识别码 转成二进制
     * 
     * @param barCode
     * @return
     */
    private static String getIdCode(String barCode) {
        // 厂商识别码
        String idCode = "0" + barCode.substring(0, 6);
        return autoGenericCode(algorismToBinary(idCode), 24, "L");
    }

    /**
     * 获取商品代码 转成二进制
     * 
     * @param barCode
     * @return
     */
    private static String getSkuCode(String barCode) {
        // 商品代码
        String skuCode = "0" + barCode.substring(6, 11);
        return autoGenericCode(algorismToBinary(skuCode), 20, "L");
    }

    /**
     * 获取序列号 转成二进制
     * 
     * @param sequenceNumber
     * @return
     */
    private static String getSequenceNumber(String sequenceNumber) {
        // 商品代码
        return autoGenericCode(algorismToBinary(sequenceNumber), 38, "L");
    }

    /**
     * 不够位数的在前面补0，保留num的长度位数字
     * 
     * @param code
     * @return
     */
    private static String autoGenericCode(String code, int num, String lf) {
        int strLen = code.length();
        StringBuffer sb = null;
        while (strLen < num) {
            sb = new StringBuffer();
            if (lf.equals("L")) {
                sb.append("0").append(code);// 左补0
            } else {
                sb.append(code).append("0");// 右补0
            }
            code = sb.toString();
            strLen = code.length();
        }
        return code;
    }

    /**
     * 十进制转二进制
     * 
     * @param code
     * @return
     */
    private static String algorismToBinary(String code) {
        BigInteger bi = new BigInteger(String.valueOf(code)); // 转换成BigInteger类型
        return bi.toString(2); // 参数2指定的是转化成X进制，默认10进制
    }

    /**
     * 二进制字符串转十进制
     * 
     * @param binary 二进制字符串
     * @return 十进制数值
     */
    public static String binaryToAlgorism(String binary) {
        BigInteger bi = new BigInteger(binary, 2); // 转换为BigInteger类型
        return bi.toString(); // 转换成十进制
    }

    /**
     * 转换十六进制
     * 
     * @param hex
     * @return
     */
    private static String binaryToHex(String hex) {
        String v = "";
        switch (hex) {
            case "1010":
                v = "A";
                break;
            case "1011":
                v = "B";
                break;
            case "1100":
                v = "C";
                break;
            case "1101":
                v = "D";
                break;
            case "1110":
                v = "E";
                break;
            case "1111":
                v = "F";
                break;
            default:
                v = Integer.valueOf(hex, 2) + "";
                break;
        }
        return v;
    }

    /**
     * 转换二进制
     * 
     * @param charAt
     * @return
     */
    private static String hexToBinary(char charAt) {
        switch (charAt) {
            case '0':
                return "0000";
            case '1':
                return "0001";
            case '2':
                return "0010";
            case '3':
                return "0011";
            case '4':
                return "0100";
            case '5':
                return "0101";
            case '6':
                return "0110";
            case '7':
                return "0111";
            case '8':
                return "1000";
            case '9':
                return "1001";
            case 'A':
                return "1010";
            case 'B':
                return "1011";
            case 'C':
                return "1100";
            case 'D':
                return "1101";
            case 'E':
                return "1110";
            case 'F':
                return "1111";
            default:
                return null;
        }
    }



    public static void main(String[] args) {
        // System.out.println(checkRfid("40343626DC3D9640825D21DBA004", "887224630654"));
        // System.out.println(binaryToAlgorism("1011101001000011101101110100000000001"));
        System.out.println(checkRfidSequence("40343626DC3D9640825D21DBA004", "40343626DC3D9641025D21DBA004"));
        // System.out.println(checkRfid("40343626DC3D9641025D21DBA004", "887223630654"));
        System.out.println(createRfid("887223630654", "100000000001"));
    }

}
