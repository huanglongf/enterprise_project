package com.jumbo.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.exception.BusinessException;

/**
 * Created by Erry Date: 2006-9-22 Time: 11:51:09 To change this template use File | Settings | File
 * Templates.
 */
public class StringUtil implements Serializable {

    private static final long serialVersionUID = -1407572890440541195L;

    private static String[] CH = {"", "", "拾", "佰", "仟", "万", "", "", "", "亿", "", "", "", "兆"};

    private static String[] CHS_NUMBER = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    protected static final Logger log = LoggerFactory.getLogger(StringUtil.class);
    private static List<String> tmpArr = new ArrayList<String>();

    public static String formatString(String str, int length) {

        while (str.length() < length) {
            str = " " + str;
        }
        return str;
    }

    /**
     * 获得两个日期字符串相差的天数
     * 
     * @param time1
     * @param time2
     * @param pattern
     * @return
     */
    public static long getDateDiffByDay(String time1, String time2, String pattern) {
        long diff = 0;
        SimpleDateFormat ft = new SimpleDateFormat(pattern);
        try {
            Date date1 = ft.parse(time1);
            Date date2 = ft.parse(time2);
            diff = date1.getTime() - date2.getTime();
            diff = diff / 1000 / 60 / 60 / 24;
        } catch (ParseException e) {
            log.error("", e);
        }
        return diff;
    }

    public static String toStr(String html) {
        String s1, s2;
        s1 = html.replaceAll("<br>", "\n").replaceAll("&39;", "'").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&34;", "\"").replace("&92;", "\\");
        s2 = s1.replaceAll("<br>", "\n").replaceAll("&39;", "'").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&34;", "\"").replace("&92;", "\\");

        return s2;

    }

    public static String toHtml(String str) {
        if (str == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            switch (c) {
                case '\n':
                    sb.append("<br>");
                    break;
                case '\r':
                    break;
                case '\'':
                    sb.append("&39;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '"':
                    sb.append("&34;");
                    break;
                case '\\':
                    sb.append("&92;");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    public static Boolean isEmpty(String org) {
        Boolean flag = Boolean.FALSE;
        if (org == null || "".equals(org.trim())) {
            flag = Boolean.TRUE;
        }
        return flag;
    }


    public static String[] splitStr(String str, String splitStr) {
        String[] splitArray = null;
        String[] subScript = splitStr.split(",");
        if (splitStr.indexOf(",") > -1) {
            splitArray = new String[subScript.length];
            for (int i = 0; i < subScript.length; i++) {
                if (i == 0) {
                    splitArray[i] = str.substring(0, Integer.parseInt(subScript[i]) - 1).trim();
                } else {
                    splitArray[i] = str.substring(Integer.parseInt(subScript[i - 1]) - 1, Integer.parseInt(subScript[i])).trim();
                }
            }
        } else {
            splitArray = str.split(splitStr);
        }
        return splitArray;
    }

    public static String appendStr(String str, String appStr, int totalLength) {
        StringBuilder sbu = new StringBuilder(str);
        int length = totalLength - sbu.length();
        if (length < 1) {
            return sbu.toString();
        }
        for (int i = 0; i < length; i++) {
            sbu = sbu.append(appStr);
        }
        return sbu.toString();
    }

    public static String formatIntToChinaBigNumStr(int num) {
        if (num < 0) {
            num = 0;
        }
        String bigLetter[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String si = num + "";
        char cc[] = new char[5];
        if (si.length() < 5) {
            StringBuilder t = new StringBuilder(si);
            for (int n = 0; n < 5 - si.length(); n++) {
                t = new StringBuilder("0").append(t);
            }
            cc = t.toString().toCharArray();
        } else if (si.length() == 5) {
            cc = si.toCharArray();
        } else {
            return "";
        }
        StringBuilder tt = new StringBuilder();
        for (int j = 0; j < cc.length; j++) {
            tt.append(bigLetter[cc[j] - 48] + "   ");
        }
        return tt.toString();
    }

    public static String appendStr(Integer position, String str, String appStr, int totalLength) {
        StringBuilder sbu = new StringBuilder(str);
        int length = totalLength - sbu.length();
        if (length < 1) {
            return sbu.toString();
        }
        if (-1 == position) {
            for (int i = 0; i < length; i++) {
                sbu = sbu.insert(0, appStr);
            }
        } else {
            for (int i = 0; i < length; i++) {
                sbu = sbu.append(appStr);
            }
        }

        return sbu.toString();
    }

    public static String ReEmptyStr(String s) {
        if (StringUtil.isEmpty(s)) {
            return "";
        } else {
            return s;
        }
    }

    /**
     * 获取在 字符串 中 中文的数量
     * 
     * @param str
     * @return
     */
    public static int getChineseNumber(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int result = 0;
        for (char oneChar : str.toCharArray()) {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(oneChar);
            if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                    || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
                result++;
            }
        }
        return result;
    }

    /**
     * 正则表达式查询匹配字符串
     * 
     * @param str
     * @param pattern
     * @return
     */
    public static String findFirstStringWithRegex(String str, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        while (m.find()) {
            return m.group();
        }
        return null;
    }

    public static void main(String[] args) {
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
        // String dstr = "2018-01-02";
        // Date date = null;
        // try {
        // date = sdf.parse(dstr);
        // getLfCrd(date, 20, "20180123FW3670APSW");
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        String str = "唯一标识S111100000383数据已存储到EMS自助服务系统";
        System.out.println(findFirstStringWithRegex(str, "[A-Z][0-9]{12}"));
    }


    /**
     * 传入数字金额字符串，返回数字金额对应的中文大字与读法
     * 
     * @param money 金额字符串
     * @return 金额中文大写
     */
    public static String getCHSNumber(String money) {
        String chs = "";
        String tmp_int = money.substring(0, money.indexOf("."));
        String tmp_down = money.substring(money.indexOf(".") + 1, money.length());
        char[] tmp_int_char = tmp_int.toCharArray();
        String[] tmp_chs = new String[tmp_int_char.length];

        int tab = 0;
        for (int i = 0; i < tmp_int_char.length; i++) {

            tab = tmp_int_char.length - i - 1;

            if (tmp_int_char.length <= 5) {
                tmp_chs[tab] = CHS_NUMBER[(int) Float.parseFloat(tmp_int_char[i] + ".0")];

                if (!tmp_chs[tab].equals("零")) {

                    // tmp_int_char.length - i 为数字所在的位数
                    chs = chs + tmp_chs[tab] + CH[tmp_int_char.length - i];
                } else {// 当数字中有零时就在后加上零，如果超过１个以上的零也只加一个零
                    if (!chs.endsWith("零") && tab != 0) {
                        chs = chs + tmp_chs[tab];
                    } else if (chs.endsWith("零") && tab == 0) {
                        chs = chs.substring(0, chs.length() - 1);
                    }
                }
            }
            // 如果数字的位数大于５和小于９时
            if (tmp_int_char.length > 5 && tmp_int_char.length < 9) {
                tmp_chs[tab] = CHS_NUMBER[(int) Float.parseFloat(tmp_int_char[i] + ".0")];

                // 如：123,1234分成两部分
                // 第１部分123：万以上亿以下
                if (tab >= 4) {
                    // 当前数字不是大小零时
                    if (!tmp_chs[tab].equals("零")) {
                        chs = chs + tmp_chs[tab] + CH[tab - 3];

                        // 当第１部分算完时在加上"万"
                        if (tab == 4) {
                            chs = chs + "万";
                        }
                    } else {
                        // 当前数字为大小"零"时
                        // 判断前一次形成在字符串结尾有没有零
                        // 如果没有零就加上零
                        if (!chs.endsWith("零")) {
                            chs = chs + tmp_chs[tab];
                        }
                        // 当第１部分算完时
                        if (tab == 4) {
                            // 先判断字符串有没有零
                            // 如果有零时就把零去掉再加上"万"
                            if (chs.endsWith("零")) {
                                chs = chs.substring(0, chs.length() - 1);
                                chs = chs + "万";
                            } else {
                                // 如果没有零就直接加上"万"
                                chs = chs + "万";
                            }
                        }
                    }
                }
                // 如：123,1234分成两部分
                // 第１部分1234：万以下
                if (tab < 4) {
                    if (!tmp_chs[tab].equals("零")) {
                        // tmp_int_char.length - i 为数字所在的位数
                        chs = chs + tmp_chs[tab] + CH[tmp_int_char.length - i];
                    } else {// 当数字中有零时就在后加上零，如果超过１个以上的零也只加一个零
                        if (!chs.endsWith("零") && tab != 0) {
                            chs = chs + tmp_chs[tab];
                        }
                        if (chs.endsWith("零") && tab == 0) {
                            chs = chs.substring(0, chs.length() - 1);
                        }
                    }
                }
            }
            // 如果数字的位数大于５和小于９时
            if (tmp_int_char.length >= 9 && tmp_int_char.length <= 12) {
                tmp_chs[tab] = CHS_NUMBER[(int) Float.parseFloat(tmp_int_char[i] + ".0")];

                if (tab >= 8 && tab < 12) {
                    // 当前数字不是大小零时
                    if (!tmp_chs[tab].equals("零")) {
                        chs = chs + tmp_chs[tab] + CH[tab - 7];
                        // 当第１部分算完时在加上"万"
                        if (tab == 8) {
                            chs = chs + "亿";
                        }
                    } else {
                        // 当前数字为大小"零"时
                        // 判断前一次形成在字符串结尾有没有零
                        // 如果没有零就加上零
                        if (!chs.endsWith("零")) {
                            chs = chs + tmp_chs[tab];
                        }
                        // 当第１部分算完时
                        if (tab == 8) {
                            // 先判断字符串有没有零
                            // 如果有零时就把零去掉再加上"万"
                            if (chs.endsWith("零")) {
                                chs = chs.substring(0, chs.length() - 1);
                                chs = chs + "亿";
                            } else {
                                // 如果没有零就直接加上"万"
                                chs = chs + "亿";
                            }
                        }
                    }
                }
                // 如：123,1234分成两部分
                // 第１部分123：万以上亿以下
                if (tab >= 4 && tab < 8) {
                    // 当前数字不是大小零时
                    if (!tmp_chs[tab].equals("零")) {
                        chs = chs + tmp_chs[tab] + CH[tab - 3];

                        // 当第１部分算完时在加上"万"
                        if (tab == 4) {
                            chs = chs + "万";
                        }
                    } else {
                        // 当前数字为大小"零"时
                        // 判断前一次形成在字符串结尾有没有零
                        // 如果没有零就加上零
                        if (!chs.endsWith("零")) {
                            chs = chs + tmp_chs[tab];
                        }
                        // 当第１部分算完时
                        if (tab == 4) {
                            // 先判断字符串有没有零
                            // 如果有零时就把零去掉再加上"万"
                            if (chs.endsWith("零")) {
                                chs = chs.substring(0, chs.length() - 1);
                                if (!chs.endsWith("亿")) {
                                    chs = chs + "万";
                                }
                            } else {
                                // 如果没有零就直接加上"万"
                                if (!chs.endsWith("亿")) {
                                    chs = chs + "万";
                                }
                            }
                        }
                    }
                }
                // 如：123,1234分成两部分
                // 第１部分1234：万以下
                if (tab < 4) {
                    if (!tmp_chs[tab].equals("零")) {
                        // tmp_int_char.length - i 为数字所在的位数
                        chs = chs + tmp_chs[tab] + CH[tmp_int_char.length - i];
                    } else {// 当数字中有零时就在后加上零，如果超过１个以上的零也只加一个零
                        if (!chs.endsWith("零") && tab != 0) {
                            chs = chs + tmp_chs[tab];
                        }
                        if (chs.endsWith("零") && tab == 0) {
                            chs = chs.substring(0, chs.length() - 1);
                        }
                    }
                }
            }
            // 如果数字的位数大于12和小于16时
            if (tmp_int_char.length > 12 && tmp_int_char.length <= 16) {
                tmp_chs[tab] = CHS_NUMBER[(int) Float.parseFloat(tmp_int_char[i] + ".0")];
                if (tab >= 12 && tab < 16) {
                    // 当前数字不是大小零时
                    if (!tmp_chs[tab].equals("零")) {
                        chs = chs + tmp_chs[tab] + CH[tab - 11];
                        // 当第１部分算完时在加上"万"
                        if (tab == 12) {
                            chs = chs + "兆";
                        }
                    } else {
                        // 当前数字为大小"零"时
                        // 判断前一次形成在字符串结尾有没有零
                        // 如果没有零就加上零
                        if (!chs.endsWith("零")) {
                            chs = chs + tmp_chs[tab];
                        }
                        // 当第１部分算完
                        if (tab == 12) {
                            // 先判断字符串有没有零
                            // 如果有零时就把零去掉再加上"万"
                            if (chs.endsWith("零")) {
                                chs = chs.substring(0, chs.length() - 1);
                                chs = chs + "兆";
                            } else {
                                // 如果没有零就直接加上"万"
                                chs = chs + "兆";
                            }
                        }
                    }
                }
                if (tab >= 8 && tab < 12) {
                    // 当前数字不是大小零时
                    if (!tmp_chs[tab].equals("零")) {
                        chs = chs + tmp_chs[tab] + CH[tab - 7];
                        // 当第１部分算完时在加上"万"
                        if (tab == 8) {
                            chs = chs + "亿";
                        }
                    } else {
                        // 当前数字为大小"零"时
                        // 判断前一次形成在字符串结尾有没有零
                        // 如果没有零就加上零
                        if (!chs.endsWith("零")) {
                            chs = chs + tmp_chs[tab];
                        }
                        // 当第１部分算完时
                        if (tab == 8) {
                            // 先判断字符串有没有零
                            // 如果有零时就把零去掉再加上"万"
                            if (chs.endsWith("零")) {
                                chs = chs.substring(0, chs.length() - 1);
                                if (!chs.endsWith("兆")) {
                                    chs = chs + "亿";
                                }
                            } else {
                                // 如果没有零就直接加上"万"
                                if (!chs.endsWith("兆")) {
                                    chs = chs + "亿";
                                }
                            }
                        }
                    }
                }
                // 如：123,1234分成两部分
                // 第１部分123：万以上亿以下
                if (tab >= 4 && tab < 8) {
                    // 当前数字不是大小零时
                    if (!tmp_chs[tab].equals("零")) {
                        chs = chs + tmp_chs[tab] + CH[tab - 3];
                        // 当第１部分算完时在加上"万"
                        if (tab == 4) {
                            chs = chs + "万";
                        }
                    } else {
                        // 当前数字为大小"零"时
                        // 判断前一次形成在字符串结尾有没有零
                        // 如果没有零就加上零
                        if (!chs.endsWith("零")) {
                            chs = chs + tmp_chs[tab];
                        }
                        // 当第１部分算完时
                        if (tab == 4) {
                            // 先判断字符串有没有零
                            // 如果有零时就把零去掉再加上"万"
                            if (chs.endsWith("零")) {
                                chs = chs.substring(0, chs.length() - 1);
                                if (!chs.endsWith("亿")) {
                                    if (!chs.endsWith("兆")) {
                                        if (!chs.endsWith("兆")) {
                                            chs = chs + "万";
                                        }
                                    }
                                }
                            } else {
                                // 如果没有零就直接加上"万"
                                if (!chs.endsWith("亿")) {
                                    if (!chs.endsWith("兆")) {
                                        chs = chs + "万";
                                    }
                                }
                            }
                        }
                    }
                }
                // 如：123,1234分成两部分
                // 第１部分1234：万以下
                if (tab < 4) {
                    if (!tmp_chs[tab].equals("零")) {
                        // tmp_int_char.length - i 为数字所在的位数
                        chs = chs + tmp_chs[tab] + CH[tmp_int_char.length - i];
                    } else {// 当数字中有零时就在后加上零，如果超过１个以上的零也只加一个零
                        if (!chs.endsWith("零") && tab != 0) {
                            chs = chs + tmp_chs[tab];
                        }
                        if (chs.endsWith("零") && tab == 0) {
                            chs = chs.substring(0, chs.length() - 1);
                        }
                    }
                }
            }
            // 如果数字的位数大于16
            if (tmp_int_char.length > 16) {
                tmp_chs[tab] = CHS_NUMBER[(int) Float.parseFloat(tmp_int_char[i] + ".0")];
                if (tab >= 12) {
                    chs = chs + tmp_chs[tab];
                    // 当第１部分算完时在加上"万"
                    if (tab == 12) {
                        chs = chs + "兆";
                    }
                }
                if (tab >= 8 && tab < 12) {
                    // 当前数字不是大小零时
                    if (!tmp_chs[tab].equals("零")) {
                        chs = chs + tmp_chs[tab] + CH[tab - 7];
                        // 当第１部分算完时在加上"万"
                        if (tab == 8) {
                            chs = chs + "亿";
                        }
                    } else {
                        // 当前数字为大小"零"时
                        // 判断前一次形成在字符串结尾有没有零
                        // 如果没有零就加上零
                        if (!chs.endsWith("零")) {
                            chs = chs + tmp_chs[tab];
                        }
                        // 当第１部分算完时
                        if (tab == 8) {
                            // 先判断字符串有没有零
                            // 如果有零时就把零去掉再加上"万"
                            if (chs.endsWith("零")) {
                                chs = chs.substring(0, chs.length() - 1);
                                if (!chs.endsWith("兆")) {
                                    chs = chs + "亿";
                                }
                            } else {
                                // 如果没有零就直接加上"万"
                                if (!chs.endsWith("兆")) {
                                    chs = chs + "亿";
                                }
                            }
                        }
                    }
                }
                // 如：123,1234分成两部分
                // 第１部分123：万以上亿以下
                if (tab >= 4 && tab < 8) {
                    // 当前数字不是大小零时
                    if (!tmp_chs[tab].equals("零")) {
                        chs = chs + tmp_chs[tab] + CH[tab - 3];
                        // 当第１部分算完时在加上"万"
                        if (tab == 4) {
                            chs = chs + "万";
                        }
                    } else {
                        // 当前数字为大小"零"时
                        // 判断前一次形成在字符串结尾有没有零
                        // 如果没有零就加上零
                        if (!chs.endsWith("零")) {
                            chs = chs + tmp_chs[tab];
                        }
                        // 当第１部分算完时
                        if (tab == 4) {
                            // 先判断字符串有没有零
                            // 如果有零时就把零去掉再加上"万"
                            if (chs.endsWith("零")) {
                                chs = chs.substring(0, chs.length() - 1);
                                if (!chs.endsWith("兆")) {
                                    if (!chs.endsWith("亿")) {
                                        chs = chs + "万";
                                    }
                                }
                            } else {
                                // 如果没有零就直接加上"万"
                                if (!chs.endsWith("兆")) {
                                    if (!chs.endsWith("亿")) {
                                        chs = chs + "万";
                                    }
                                }
                            }
                        }
                    }
                }

                // 如：123,1234分成两部分
                // 第１部分1234：万以下
                if (tab < 4) {
                    if (!tmp_chs[tab].equals("零")) {
                        // tmp_int_char.length - i 为数字所在的位数
                        chs = chs + tmp_chs[tab] + CH[tmp_int_char.length - i];
                    } else {// 当数字中有零时就在后加上零，如果超过１个以上的零也只加一个零
                        if (!chs.endsWith("零") && tab != 0) {
                            chs = chs + tmp_chs[tab];
                        }
                        if (chs.endsWith("零") && tab == 0) {
                            chs = chs.substring(0, chs.length() - 1);
                        }
                    }
                }
            }
        }
        if (tmp_down != null) {
            char[] tmp = tmp_down.toCharArray();
            if (tmp.length == 1) {
                if (tmp[0] != '0') {
                    chs = chs + "元" + CHS_NUMBER[(int) Float.parseFloat(tmp[0] + ".0")] + "角整";
                } else {
                    chs = chs + "元整";
                }
            } else {
                if (tmp[1] != '0' && tmp[0] != '0') {
                    chs = chs + "元" + CHS_NUMBER[(int) Float.parseFloat(tmp[0] + ".0")] + "角" + CHS_NUMBER[(int) Float.parseFloat(tmp[1] + ".0")] + "分";
                } else if (tmp[1] != '0' && tmp[0] == '0') {
                    chs = chs + "元零" + CHS_NUMBER[(int) Float.parseFloat(tmp[1] + ".0")] + "分";
                }
            }
        } else {
            chs = chs + "元整";
        }
        return chs;
    }

    /**
     * 获取拼接了省市区的正确地址
     * 
     * @param address 作业单物流信息里面地址，可能包含省市区也可能没有
     * @param province 省
     * @param city 市
     * @param district 区
     * @return
     */
    public static String getRealAddress(String address, String province, String city, String district, boolean isSplitJoint) {
        String rtnAddress = address.trim();
        if (district != null) {
            rtnAddress = rtnAddress.replace(district + " ", "").trim();
        }
        if (city != null) {
            rtnAddress = rtnAddress.replace(city + " ", "").trim();
        }
        if (province != null) {
            rtnAddress = rtnAddress.replace(province + " ", "").trim();
        }

        if (province != null) {
            if (rtnAddress.startsWith(province)) {
                rtnAddress = rtnAddress.substring(province.length());
            }
        }
        if (city != null) {
            if (rtnAddress.startsWith(city)) {
                rtnAddress = rtnAddress.substring(city.length());
            }
        }
        if (district != null) {
            if (rtnAddress.startsWith(district)) {
                rtnAddress = rtnAddress.substring(district.length());
            }
        }
        if (isSplitJoint) {
            rtnAddress = (province == null ? "" : province) + " " + (city == null ? "" : city) + " " + (district == null ? "" : district) + " " + rtnAddress;
        }
        return rtnAddress;
    }

    /**
     * 换算LF CRD时间
     * 
     * @param planOutboundTime 计划发货时间
     * @param transportPra 运输时效
     * @param po nikePo
     * @return
     * @throws Exception
     */
    public static String getLfCrd(Date planOutboundTime, Integer transportPra, String po) {
        String crd = "";
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(planOutboundTime);
        calendar.add(Calendar.DATE, transportPra);
        // 截取po
        String p = po.substring(0, 8);
        if (!StringUtil.isEmpty(p) && !isNumeric(p)) {
            return "";
        }
        Date poDate = null;
        try {
            poDate = df.parse(p);
        } catch (ParseException e) {
            throw new BusinessException(e);
        }
        if (calendar.getTime().getTime() < poDate.getTime()) {
            // 当计划发货时间+运输时间<po前8位时间 crd=当计划发货时间+运输时间
            crd = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
            return p;
        }
        return "";
    }

    /**
     * 判断字符串是否为数字
     * 
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public static List<String> getCountAreas(String[] list) {
        try {
            List<String> arrList = new CopyOnWriteArrayList<String>();
            for (int i = 0; i < list.length; i++) {
                int k = i + 1;
                if (k > list.length || list.length <= 0) {
                    return new ArrayList<String>(arrList);
                }
                combine(0, k, list, arrList);
            }
            return new ArrayList<String>(arrList);
        } catch (Exception e) {
            tmpArr.clear();
        }
        return null;
    }

    public static void combine(int index, int k, String[] arr, List<String> arrList) {
        // List<String> returnList = new ArrayList<String>();
        if (k == 1) {
            for (int i = index; i < arr.length; i++) {
                tmpArr.add(arr[i]);
                // System.out.println(tmpArr.toString());
                String[] s = tmpArr.toString().substring(1, tmpArr.toString().length() - 1).split(",");
                String ss = "";
                for (int j = 0; j < s.length; j++) {
                    if (j + 1 == s.length) {
                        ss += s[j].trim();
                    } else {
                        ss += s[j].trim() + "|";
                    }
                }
                arrList.add(ss);
                tmpArr.remove((Object) arr[i]);
            }
        } else if (k > 1) {
            for (int i = index; i <= arr.length - k; i++) {
                tmpArr.add(arr[i]);
                combine(i + 1, k - 1, arr, arrList);
                tmpArr.remove((Object) arr[i]);
            }
        } else {
            return;
        }
    }

}
