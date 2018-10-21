package com.jumbo.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author weiy add by 2011-08-09
 */

public class DateUtil implements Serializable {

    static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    private static final long serialVersionUID = 5089714462467845197L;

    private static Pattern p = Pattern.compile("[0-9]{2}:[0-9]{2}:[0-9]{2}");

    /**
     * 日期时间是否在给定时分秒比较(不计算日期,只计算时分秒)
     * 
     * @param date
     * @param time format(hh:mm:ss)
     * @return the value 0 if the time represented by the argument is equal to the time represented
     *         by this Calendar; a value less than 0 if the time of this Calendar is before the time
     *         represented by the argument; and a value greater than 0 if the time of this Calendar
     *         is after the time represented by the argument.
     */
    public static int compareToTime(Date date, String time) {
        Calendar baseDate = Calendar.getInstance();
        baseDate.setTime(date);
        Calendar cmpDate = Calendar.getInstance();
        cmpDate.setTime(getDateWithTime(date, time));
        return baseDate.compareTo(cmpDate);
    }

    public static Date getDateWithTime(Date date, String time) {

        Matcher m = p.matcher(time);
        if (!m.matches()) {
            throw new IllegalArgumentException();
        }
        String[] tmp = time.split(":");
        Calendar cmpDate = Calendar.getInstance();
        cmpDate.setTime(date);
        cmpDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tmp[0], 10));
        cmpDate.set(Calendar.MINUTE, Integer.parseInt(tmp[1], 10));
        cmpDate.set(Calendar.SECOND, Integer.parseInt(tmp[2], 10));
        return cmpDate.getTime();
    }

    /**
     * get the current time
     */
    public static Date now() {
        return Calendar.getInstance().getTime();
    }

    public static Date today() {
        return roundDate(now());
    }

    /**
     * Add specified number of days to a date.
     */
    public static Date addDays(Date date, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, num);
        return c.getTime();
    }

    /**
     * 月份加减
     * 
     * @param date
     * @param month
     * @return
     */
    public final static Date monthAscORDesc(Date date, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }

    /**
     * Set hour, minute, second, millisecond of _c to 0.
     */
    public static Calendar roundCalendar(Calendar _c) {
        _c.set(Calendar.HOUR_OF_DAY, 0);
        _c.set(Calendar.MINUTE, 0);
        _c.set(Calendar.SECOND, 0);
        _c.set(Calendar.MILLISECOND, 0);
        return _c;
    }

    /**
     * Set hour, minute, second, millisecond of _c to 0.
     */
    public static Date roundDate(Date _d) {
        Calendar c = Calendar.getInstance();
        c.setTime(_d);
        return roundCalendar(c).getTime();
    }

    /**
     * convert to the sql date
     */
    public static java.sql.Date toSQLDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * convert to the utility date
     */
    public static Date toUtilDate(java.sql.Date date) {
        return new Date(date.getTime());
    }

    /**
     * Get the interval days of the two date.If return result is less than 0, date _one is before
     * _two; if the return result is greater than 0, date _one is after _two.
     */
    public static int getInterval(Date _one, Date _two) {
        Calendar one = Calendar.getInstance();
        one.setTime(_one);
        Calendar two = Calendar.getInstance();
        two.setTime(_two);
        // System.out.println("One date is " + _one.toString() + ", and two date is " +
        // _two.toString());

        int yearOne = one.get(Calendar.YEAR);
        int yearTwo = two.get(Calendar.YEAR);
        int dayOne = one.get(Calendar.DAY_OF_YEAR);
        int dayTwo = two.get(Calendar.DAY_OF_YEAR);

        // System.out.println("One date is the number " + dayOne + " of " + yearOne);
        // System.out.println("Two date is the number " + dayTwo + " of " + yearTwo);

        if (yearOne == yearTwo) {
            return dayOne - dayTwo;
        } else if (yearOne < yearTwo) {
            int yearDays = 0;
            while (yearOne < yearTwo) {
                if (isLeapyear(yearOne)) {
                    yearDays += 366;
                } else {
                    yearDays += 365;
                }

                yearOne += 1;
            }
            return dayOne - yearDays - dayTwo;
        } else {
            int yearDays = 0;
            while (yearTwo < yearOne) {
                if (isLeapyear(yearTwo)) {
                    yearDays += 366;
                } else {
                    yearDays += 365;
                }

                yearTwo += 1;
            }
            return dayOne - dayTwo + yearDays;
        }
    }

    public static int getMonthInterval(Date _one, Date _two) {
        Calendar one = Calendar.getInstance();
        one.setTime(_one);
        Calendar two = Calendar.getInstance();
        two.setTime(_two);
        int yearInt = two.get(Calendar.YEAR) - one.get(Calendar.YEAR);
        int monthInt = two.get(Calendar.MONTH) - one.get(Calendar.MONTH);
        int dayInt = two.get(Calendar.DAY_OF_MONTH) - one.get(Calendar.DAY_OF_MONTH);
        return yearInt * 12 + monthInt + (dayInt > 0 ? 1 : 0);
    }

    public static boolean isLeapyear(Date _date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(_date);
        int year = calendar.get(Calendar.YEAR);
        return isLeapyear(year);
    }

    public static boolean isLeapyear(int _year) {
        if (_year % 4 == 0) {
            if (_year % 100 == 0) {
                if (_year % 400 == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * 制定日期YYYY-MM-DD
     * 
     * @param date
     * @return
     */
    public static boolean isYearDate(String date) {
        String EL =
                "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        Pattern p = Pattern.compile(EL);
        Matcher m = p.matcher(date);
        boolean b = m.matches();
        return b;
    }

    public static int Hour(Date time) {
        SimpleDateFormat st = new SimpleDateFormat("yyyyMMddHH");
        return Integer.parseInt(st.format(time));
    }

    public static boolean DateCompare(Date compareDate, Date dateBegin, Date dateEnd) {
        if (null == compareDate || null == dateBegin || null == dateEnd) {
            return false;
        }
        if (Hour(compareDate) <= Hour(dateEnd) && Hour(compareDate) >= Hour(dateBegin)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * date 转 string
     */
    public static String returnStringByDate(Date day) {
        Date today1 = new Date();
        today1.setTime(day.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        return formatter.format(today1);
    }

    /**
     * 传递当天时间年月日
     * 
     * @return 上周六-本周五 共七天 写入execl 6-上周六 7-上周天 1-本周一 2-本周二 3-本周三 4-本周四 5-本周五
     * @throws ParseException
     */
    @SuppressWarnings("deprecation")
    public static Date returnDateAfterSundayToNextFriDay(int i) {
        Date mydate = new Date();
        Calendar calendar = Calendar.getInstance();

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        // 周日
        if (mydate.getDay() == 0) {
            calendar.add(Calendar.DATE, i - dayOfWeek - 14);
            return calendar.getTime();
        } else {
            calendar.add(Calendar.DATE, i - dayOfWeek - 7);
            return calendar.getTime();
        }
    }

    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                // System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                // System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            // exception.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error("compare_date Exception！", exception);
            }
        }
        return 0;
    }

    public static String getSysDateFormat(String format) {
        // 设置日期格式
        SimpleDateFormat df = new SimpleDateFormat(format);
        // new Date()为获取当前系统时间
        return df.format(new Date());
    }
}
