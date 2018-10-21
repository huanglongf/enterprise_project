package com.jumbo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


public class FormatUtil {
    protected static final Logger log = LoggerFactory.getLogger(FormatUtil.class);

    public static final String DATE_FORMATE_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMATE_HHMMSS = "HHmmss";

    /**
     * 获取日期所有周的第一天
     * 
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.setFirstDayOfWeek(Calendar.MONDAY);
        ca.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return ca.getTime();
    }

    /**
     * 格式化字符串，在字符串左边或者右边补充固定字符使字符达到指定长度 如： str = "123456" chr = "0" strLength = 10 direction = 1 结果为
     * 0000123456
     * 
     * @param str 原始字符串
     * @param chr 补充字符
     * @param strLength 反馈字符长度
     * @param direction 方向；>=0 时左边补充，<0时右边补充
     * @return 调整后字符串
     */
    public static String addCharForString(String str, char chr, int strLength, int direction) {
        if(str == null){
            return null;
        }
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                if (direction >= 0) {
                    sb.append(chr).append(str);// 左补0
                } else {
                    sb.append(str).append(chr);// 右补0
                }
                str = sb.toString();
                strLen = str.length();
            }
        }

        return str;
    }

    public static Date stringToDate(String str, String format) throws ParseException {
        if ((null == str || "".equals(str)) || (null == format || "".equals(format))) {
            return null;
        }
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        return fmt.parse(str);
    }


    /**
     * String转换date类型
     * 
     * @param str 支持格式"yyyy-MM-dd HH:mm:ss" 与 "yyyy-MM-dd"
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String str) throws ParseException {
        Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}");
        Pattern noSlipt = Pattern.compile("[0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}");
        Matcher matcher = pattern.matcher(str);
        Matcher matcherNoSlipt = noSlipt.matcher(str);
        SimpleDateFormat fmt = null;
        if (matcher.matches()) {
            fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if (matcherNoSlipt.matches()) {
            fmt = new SimpleDateFormat("yyyyMMddHHmmss");
        } else {
            fmt = new SimpleDateFormat("yyyy-MM-dd");
        }
        return fmt.parse(str);
    }

    /**
     * 返回指定日期相应位移后的日期
     * 
     * @param date 参考日期
     * @param field 位移单位，见 {@link Calendar}
     * @param offset 位移数量，正数表示之后的时间，负数表示之前的时间
     * @return 位移后的日期
     */
    public static Date offsetDate(Date date, int field, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, offset);
        return calendar.getTime();
    }

    /**
     * 格式化输出日期
     * 
     * @param date 日期
     * @param fmt 格式类型
     * @return
     */
    public static String formatDate(Date date, String fmtStr) {
        if (date == null) return "";
        SimpleDateFormat fmt = new SimpleDateFormat(fmtStr);
        return fmt.format(date);
    }

    /**
     * ======================================== 接收一个String类型的参数，并将其转化为中文金额的大写方式 例如 123.45 -->
     * 壹佰贰拾叁元肆角伍分 ========================================
     */
    public static String uppercaseCurrenty(java.math.BigDecimal val) {
        try {
            return stringToUpperAmount(val.toString());
        } catch (NumberFormatException e) {
            // System.out.println("非法数据，请检查！");
            return "非法数据，请检查";
        }
    }

    public static String uppercaseCurrenty(float val) {
        return stringToUpperAmount(val + "");
    }

    private static String stringToUpperAmount(String s) {
        try {
            float f = Float.valueOf(s);
            if (f < 0) {
                // System.out.println("非法数据，请检查！");
                return "非法数据，请检查";
            } else {
                return cleanZero(splitNum(roundString(s)));
            }
        } catch (NumberFormatException e) {
            // System.out.println("非法数据，请检查！");
            return "非法数据，请检查";
        }
    }

    /**
     * 把用户输入的数以小数点为界分割开来，并调用 numFormat() 方法 进行相应的中文金额大写形式的转换 注：传入的这个数应该是经过 roundString()
     * 方法进行了四舍五入操作的
     * 
     * @param s String
     * @return 转换好的中文金额大写形式的字符串
     */
    private static String splitNum(String s) {
        // 如果传入的是空串则继续返回空串
        if ("".equals(s)) {
            return "";
        }
        // 以小数点为界分割这个字符串
        int index = s.indexOf(".");
        // 截取并转换这个数的整数部分
        String intOnly = s.substring(0, index);
        String part1 = numFormat(1, intOnly);
        // 截取并转换这个数的小数部分
        String smallOnly = s.substring(index + 1);
        String part2 = numFormat(2, smallOnly);
        // 把转换好了的整数部分和小数部分重新拼凑一个新的字符串
        String newS = part1 + part2;
        return newS;
    }

    /**
     * 对传入的数进行四舍五入操作
     * 
     * @param s String 从命令行输入的那个数
     * @return 四舍五入后的新值
     */
    private static String roundString(String s) {
        // 如果传入的是空串则继续返回空串
        if ("".equals(s)) {
            return "";
        }
        // 将这个数转换成 double 类型，并对其进行四舍五入操作
        double d = Double.parseDouble(s);
        // 此操作作用在小数点后两位上 1.233 123.8/100
        d = (d * 100 + 0.5) / 100;
        // 将 d 进行格式化
        s = new java.text.DecimalFormat("##0.000").format(d);
        // 以小数点为界分割这个字符串
        int index = s.indexOf(".");
        // 这个数的整数部分
        String intOnly = s.substring(0, index);
        // 规定数值的最大长度只能到万亿单位，否则返回 "0"
        if (intOnly.length() > 13) {
            // System.out.println("输入数据过大！（整数部分最多13位！）");
            return "";
        }
        // 这个数的小数部分
        String smallOnly = s.substring(index + 1);
        // 如果小数部分大于两位，只截取小数点后两位
        if (smallOnly.length() > 2) {
            String roundSmall = smallOnly.substring(0, 2);
            // 把整数部分和新截取的小数部分重新拼凑这个字符串
            s = intOnly + "." + roundSmall;
        }
        return s;
    }

    /**
     * 把传入的数转换为中文金额大写形式
     * 
     * @param flag int 标志位，1 表示转换整数部分，0 表示转换小数部分
     * @param s String 要转换的字符串
     * @return 转换好的带单位的中文金额大写形式
     */
    private static String numFormat(int flag, String s) {
        int sLength = s.length();
        // 货币大写形式
        String bigLetter[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        // 货币单位
        String unit[] = {"元", "拾", "佰", "仟", "万",
                // 拾万位到仟万位
                "拾", "佰", "仟",
                // 亿位到万亿位
                "亿", "拾", "佰", "仟", "万"};
        String small[] = {"分", "角"};
        // 用来存放转换后的新字符串
        String newS = "";
        // 逐位替换为中文大写形式
        for (int i = 0; i < sLength; i++) {
            if (flag == 1) {
                // 转换整数部分为中文大写形式（带单位）
                newS = newS + bigLetter[s.charAt(i) - 48] + unit[sLength - i - 1];
            } else if (flag == 2) {
                // 转换小数部分（带单位）
                newS = newS + bigLetter[s.charAt(i) - 48] + small[sLength - i - 1];
            }
        }
        return newS;
    }

    /**
     * 把已经转换好的中文金额大写形式加以改进，清理这个字 符串里面多余的零，让这个字符串变得更加可观 注：传入的这个数应该是经过 splitNum() 方法进行处理，这个字
     * 符串应该已经是用中文金额大写形式表示的
     * 
     * @param s String 已经转换好的字符串
     * @return 改进后的字符串
     */
    private static String cleanZero(String s) {
        // 如果传入的是空串则继续返回空串
        if ("".equals(s)) {
            return "";
        }
        // 如果用户开始输入了很多 0 去掉字符串前面多余的'零'，使其看上去更符合习惯
        while (s.charAt(0) == '零') {
            // 将字符串中的 "零" 和它对应的单位去掉
            s = s.substring(2);
            // 如果用户当初输入的时候只输入了 0，则只返回一个 "零"
            if (s.length() == 0) {
                return "零";
            }
        }
        // 字符串中存在多个'零'在一起的时候只读出一个'零'，并省略多余的单位
        String regex1[] = {"零仟", "零佰", "零拾"};
        String regex2[] = {"零亿", "零万", "零元"};
        String regex3[] = {"亿", "万", "元"};
        String regex4[] = {"零角", "零分"};
        // 第一轮转换把 "零仟", 零佰","零拾"等字符串替换成一个"零"
        for (int i = 0; i < 3; i++) {
            s = s.replaceAll(regex1[i], "零");
        }
        // 第二轮转换考虑 "零亿","零万","零元"等情况
        // "亿","万","元"这些单位有些情况是不能省的，需要保留下来
        for (int i = 0; i < 3; i++) {
            // 当第一轮转换过后有可能有很多个零叠在一起
            // 要把很多个重复的零变成一个零
            s = s.replaceAll("零零零", "零");
            s = s.replaceAll("零零", "零");
            s = s.replaceAll(regex2[i], regex3[i]);
        }
        // 第三轮转换把"零角","零分"字符串省略
        for (int i = 0; i < 2; i++) {
            s = s.replaceAll(regex4[i], "");
        }
        // 当"万"到"亿"之间全部是"零"的时候，忽略"亿万"单位，只保留一个"亿"
        s = s.replaceAll("亿万", "亿");
        return s;
    }

    /**
     * 屏蔽手机号码 （例：13800138138屏蔽后：1380013****）
     * 
     * @param mobile
     * @return
     */
    public static String shieldinMobile(String mobile) {
        if (mobile == null || mobile.trim().length() < 6) {
            return mobile;
        }
        return mobile.trim().substring(0, mobile.length() - 5) + "*****";
    }


    /**
     * 屏蔽手机号码 （例：021-12345678-254屏蔽后：021-123*****-254）
     * 
     * @param mobile
     * @return
     */
    public static String shieldinPhoneNumber(String phone) {
        if (phone == null || phone.trim().length() < 6) {
            return phone;
        }
        String[] s = phone.trim().split("-");
        int maxIndex = 0;
        int maxLength = s[0].length();
        for (int i = 1; i < s.length; i++) {
            if (s[i].length() >= maxLength) {
                maxIndex = i;
                maxLength = s[i].length();
            }
        }
        String temp = "";
        if (maxLength > 5) {
            temp = s[maxIndex].substring(0, maxLength - 5) + "*****";
        } else {
            for (int i = 0; i < maxLength; i++) {
                temp += "*";
            }
        }
        String result = "";
        String _ = "";
        for (int i = 0; i < s.length; i++) {
            result += _;
            if (i == maxIndex) {
                result += temp;
            } else {
                result += s[i];
            }
            if (i == 0) _ = "-";
        }
        return result;
    }

    public static Date getDate(String date) {
        Date s = null;
        try {
            if (StringUtils.hasText(date)) {
                s = stringToDate(date);
                boolean b = DateUtil.isYearDate(date);
                if (b) {
                    s = DateUtil.addDays(s, 1);
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return s;
    }

    public static void main(String[] s) {
        System.out.println(addCharForString("296", '0', 8, 1));
    }
}
