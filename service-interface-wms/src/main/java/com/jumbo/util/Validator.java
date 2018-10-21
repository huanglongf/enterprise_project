/*
 * 创建日期 2011-08-10 常用工具类构件库
 */
package com.jumbo.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;


/**
 * 数据校验常用工具类 @
 * 
 * @作者 weiy
 * @版本 1.0
 */
public class Validator implements Serializable {


    private static final long serialVersionUID = -1114517581552238591L;

    /**
     * 检验字符串是否是地址
     * 
     * @param address
     * @return
     */

    public static boolean isAddress(String address) {
        if (isNull(address)) {
            return false;
        }

        char[] c = address.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if ((!isChar(c[i])) && (!isDigit(c[i])) && (!Character.isWhitespace(c[i]))) {

                return false;
            }
        }

        return true;
    }

    /**
     * 校验是否是字符
     * 
     * @param c
     * @return
     */
    public static boolean isChar(char c) {
        return Character.isLetter(c);
    }

    /**
     * 检验字符串是否包括字符
     * 
     * @param s
     * @return
     */

    public static boolean isChar(String s) {
        if (isNull(s)) {
            return false;
        }

        char[] c = s.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (!isChar(c[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * 检查字符是否是数字
     * 
     * @param c
     * @return
     */
    public static boolean isDigit(char c) {
        int x = (int) c;

        if ((x >= 48) && (x <= 57)) {
            return true;
        }

        return false;
    }

    /**
     * 检查字符串是否是数字组成
     * 
     * @param s
     * @return
     */
    public static boolean isDigit(String s) {
        if (isNull(s)) {
            return false;
        }

        char[] c = s.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (!isDigit(c[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * 是否是Email地址
     * 
     * @param ea
     * @return
     */
    public static boolean isEmailAddress(String ea) {
        if (isNull(ea)) {
            return false;
        }

        int eaLength = ea.length();

        if (eaLength < 6) {

            // j@j.c

            return false;
        }

        ea = ea.toLowerCase();

        int at = ea.indexOf('@');

        // Unix based email addresses cannot be longer than 24 characters.
        // However, many Windows based email addresses can be longer than 24,
        // so we will set the maximum at 48.

        // int maxEmailLength = 24;
        int maxEmailLength = 48;

        if ((at > maxEmailLength) || (at == -1) || (at == 0) || ((at <= eaLength) && (at > eaLength - 5))) {

            // 123456789012345678901234@joe.com
            // joe.com
            // @joe.com
            // joe@joe
            // joe@jo
            // joe@j

            return false;
        }

        int dot = ea.lastIndexOf('.');

        if ((dot == -1) || (dot < at) || (dot > eaLength - 3)) {

            // joe@joecom
            // joe.@joecom
            // joe@joe.c

            return false;
        }

        if (ea.indexOf("..") != -1) {

            // joe@joe..com

            return false;
        }

        char[] name = ea.substring(0, at).toCharArray();

        for (int i = 0; i < name.length; i++) {
            if ((!isChar(name[i])) && (!isDigit(name[i])) && (name[i] != '.') && (name[i] != '-') && (name[i] != '_')) {

                return false;
            }
        }

        if ((name[0] == '.') || (name[name.length - 1] == '.') || (name[0] == '-') || (name[name.length - 1] == '-') || (name[0] == '_')) { // ||
                                                                                                                                            // (name[name.length
                                                                                                                                            // -
                                                                                                                                            // 1]
                                                                                                                                            // ==
                                                                                                                                            // '_'))
                                                                                                                                            // {

            // .joe.@joe.com
            // -joe-@joe.com
            // _joe_@joe.com

            return false;
        }

        char[] host = ea.substring(at + 1, ea.length()).toCharArray();

        for (int i = 0; i < host.length; i++) {
            if ((!isChar(host[i])) && (!isDigit(host[i])) && (host[i] != '.') && (host[i] != '-')) {

                return false;
            }
        }

        if ((host[0] == '.') || (host[host.length - 1] == '.') || (host[0] == '-') || (host[host.length - 1] == '-')) {

            // joe@.joe.com.
            // joe@-joe.com-

            return false;
        }

        // postmaster@joe.com

        if (ea.startsWith("postmaster@")) {
            return false;
        }

        // root@.com

        if (ea.startsWith("root@")) {
            return false;
        }

        return true;
    }

    /**
     * 检查是否是数字
     * 
     * @param number
     * @return
     */
    public static boolean isNumber(String number) {
        if (isNull(number)) {
            return false;
        }

        char[] c = number.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (!isDigit(c[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查字符串是否是空或空字符串
     * 
     * @param s
     * @return
     */
    public static boolean isNull(String s) {
        if (s == null) {
            return true;
        }

        s = s.trim();

        if ((s.equals(StringPool.NULL)) || (s.equals(StringPool.BLANK)) || s.endsWith(StringPool.SBCBLANK) || s.startsWith(StringPool.SBCBLANK)) {
            return true;
        }

        return false;
    }

    public static boolean isNotNull(String s) {
        return !isNull(s);
    }


    /**
     * 检验是否是电话号码
     * 
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        if (phoneNumber.indexOf("+") == 0) {
            phoneNumber = phoneNumber.substring(1, phoneNumber.length());
        }
        if (phoneNumber.indexOf("-") != -1) {
            phoneNumber = phoneNumber.replaceAll("-", "");
        }
        if (phoneNumber.indexOf("*") != -1) {
            phoneNumber = phoneNumber.replace('*', ' ');
            phoneNumber = phoneNumber.replaceAll(" ", "");
        }



        if (phoneNumber.indexOf("#") != -1) {
            phoneNumber = phoneNumber.replaceAll("#", "");
        }
        return isNumber(phoneNumber);
    }

    /**
     * 判断是否为金额
     * 
     * @param money
     * @return
     */
    public static boolean isMoney(String money) {
        if (null == money) {
            return false;
        }
        try {
            new Double(money);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串长度是否小于等于输入的长度
     * 
     * @param value
     * @param length
     * @return
     */
    public static boolean isShortThan(String value, int length) {
        if (isNull(value)) {
            return true;
        }
        byte[] bytes = value.getBytes();
        if (bytes.length > length) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPhoneNumber("+861111111111"));
    }

    /**
     * @description -判断String参数number是否是数字格式(前置零也是合法的); 正则表达式: [\+\-]?\d*.?\d*
     * @param String number -要判断的字符串;
     * @return boolean;
     * @time 20101221;
     * @author SAM;
     */
    public static boolean isDigNumber(String number) {
        // 为空判断;
        if (number == null || number.equals("")) {
            return false;
        }
        // 小数点次数;
        int pointNum = 1;
        // 克隆String的char[];
        char[] charArray = number.toCharArray();
        // 第一个字母是否为'-'|'+';
        if (!Character.isDigit(charArray[0]) && charArray[0] != '-' && charArray[0] != '+') {
            return false;
        }
        for (int i = 1; i < charArray.length; i++) {
            if (!Character.isDigit(charArray[i])) { // 非'0'-'9';
                if (charArray[i] != '.') { // 非'0'-'9'且非'.';
                    return false;
                } else if (pointNum != 1) { // 是'.'但非有1次;
                    return false;
                } else { // 是'.'且只有1次;
                    pointNum++;
                }
            }
        }
        return true;
    }

    /**
     * 判断元素是否不为空,调用<code>!isNullOrEmpty</code>方法
     * <p>
     * 目前可以判断一下元素
     * <ul>
     * <li>Collection,使用其isEmpty()</li>
     * <li>Map,使用其isEmpty()</li>
     * <li>Object[],判断length==0</li>
     * <li>String,使用.trim().length()效率高</li>
     * <li>Enumeration,使用hasMoreElements()</li>
     * <li>Iterator,使用hasNext()</li>
     * </ul>
     * 
     * @param value 可以是Collection,Map,Object[],String,Enumeration,Iterator类型
     * @return 不为空返回true
     * @since 1.0
     */
    public final static boolean isNotNullOrEmpty(Object value) {
        return !isNullOrEmpty(value);
    }

    /**
     * 判断元素是否为空
     * <p>
     * 目前可以判断一下元素
     * <ul>
     * <li>Collection,使用其isEmpty()</li>
     * <li>Map,使用其isEmpty()</li>
     * <li>Object[],判断length==0</li>
     * <li>String,使用.trim().length()效率高</li>
     * <li>Enumeration,使用hasMoreElements()</li>
     * <li>Iterator,使用hasNext()</li>
     * </ul>
     * 
     * @param value 可以是Collection,Map,Object[],String,Enumeration,Iterator类型
     * @return 空返回true
     * @since 1.0
     */
    @SuppressWarnings("rawtypes")
    public final static boolean isNullOrEmpty(Object value) {
        if (null == value) {
            return true;
        }
        /*****************************************************************************/
        boolean flag = false;
        // 字符串
        if (value instanceof String) {
            // 比较字符串长度, 效率高
            flag = value.toString().trim().length() <= 0;
        }
        // Object[]object数组
        else if (value instanceof Object[]) {
            flag = ((Object[]) value).length == 0;
        }
        // ***********************************************************
        // 集合
        else if (value instanceof Collection) {
            flag = ((Collection) value).isEmpty();
        }
        // map
        else if (value instanceof Map) {
            flag = ((Map) value).isEmpty();
        }
        // 枚举
        else if (value instanceof Enumeration) {
            flag = !((Enumeration) value).hasMoreElements();
        }
        // Iterator迭代器
        else if (value instanceof Iterator) {
            flag = !((Iterator) value).hasNext();
        }
        return flag;
    }

}
