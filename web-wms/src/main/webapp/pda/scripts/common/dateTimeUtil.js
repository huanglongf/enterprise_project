/**
 * 日期时间通用函数(常用日期处理函数) 
 * 
 * @author shenjian
 * @date 2016-1-13
 * @version 1.0
 * @updateTime 2016-1-13
 */

/**
 * 获取系统时间(年月日时分秒)
 * @return {*}
 */
var getDateTime = function (date) {
    var d = null;
    if(date){
        d = date;
    }else{
        d = new Date();
    }
    var df = new DateFormat("yyyy-MM-dd HH:mm:ss");
    return df.format(d);
};

/**
 * 获取系统日期(年月日)
 * @return {*}
 */
var getDate = function () {
    var d = new Date();
    var df = new DateFormat("yyyy-MM-dd");
    return df.format(d);
};

/**
 * 获取日期(年月日)
 * @param iYear 年标识
 * @param iMonth 月标识
 * @param iDay 日标识
 * @return {*}
 */
var getDateByDate = function (iYear, iMonth, iDay) {
    var d = new Date();
    var df = new DateFormat("yyyy-MM-dd");
    var c = calDate(d, iYear, iMonth, iDay);
    return df.format(c);
};

/**
 * 获取系统时间(时分秒)
 * @return {*}
 */
var getTime = function () {
    var d = new Date();
    var df = new DateFormat("HH:mm:ss");
    return df.format(d);
};

/**
 * 获取系统年份
 * @return {Number}
 */
var getFullYear = function () {
    var d = new Date();
    return d.getFullYear();
};

/**
 * 获取年份
 * @param iYear 年标识
 * @return {Number}
 */
var getFullYearByYear = function (iYear) {
    var d = new Date();
    var c = calDate(d, iYear, 0, 0);
    return c.getFullYear();
};

/**
 * 获取年份
 * @param iMonth 月标识
 * @return {Number}
 */
var getFullYearByMonth = function (iMonth) {
    var d = new Date();
    var c = calDate(d, 0, iMonth, 0);
    return c.getFullYear();
};

/**
 * 获取年份
 * @param iDay 日标识
 * @return {Number}
 */
var getFullYearByDay = function (iDay) {
    var d = new Date();
    var c = calDate(d, 0, 0, iDay);
    return c.getFullYear();
};

/**
 * 获取当前月份
 * @return {number}
 */
var getMonth = function () {
    var d = new Date();
    return d.getMonth() + 1;
};

/**
 * 获取月份
 * @param iMonth 月标识
 * @return {number}
 */
var getMonthByMonth = function (iMonth) {
    var d = new Date();
    var c = calDate(d, 0, iMonth, 0);
    return c.getMonth() + 1;
};

/**
 * 获取月份
 * @param iDay 日标识
 * @return {number}
 */
var getMonthByDay = function (iDay) {
    var d = new Date();
    var c = calDate(d, 0, 0, iDay);
    return c.getMonth() + 1;
};

/**
 * 获取当前日
 * @return {Number}
 */
var getDay = function () {
    var d = new Date();
    return d.getDate();
};

/**
 * 获取日
 * @param iDay 日标识
 * @return {Number}
 */
var getDayByDay = function (iDay) {
    var d = new Date();
    var c = calDate(d, 0, 0, iDay);
    return c.getDate();
};

/**
 * 获取开始时间【XXXX-XX-XX 00:00:00】
 */
var getBeginTime = function() {
    return getDateByDate(0, 0, 0) + " 00:00:00";
}

/**
 * 获取结束时间【XXXX-XX-XX 23:59:59】
 */
var getEndTime = function() {
	return getDateByDate(0, 0, 0) + " 23:59:59";
}

/**
 * 获取上个15分钟时间【XXXX-XX-XX XX:XX:XX】
 */
var getLast15Time = function() {
	// 获取当前日期
	var d = new Date();
    var df = new DateFormat("yyyy-MM-dd");
    // 计算上个15分钟刻度
    var hour = d.getHours(); // 时
    var minute = d.getMinutes();
    var min = Math.floor(minute / 15) * 15;
    return df.format(d) + ' ' + zeroizeDate(hour) + ':' + zeroizeDate(min) + ':00';
}

/**
 * 根据年、月、日的差值计算新的日期
 * @param date
 * @param year
 * @param month
 * @param day
 * @return {*}
 */
var calDate = function (date, year, month, day) {
    if (date && month <= 23 && month >= -23 && day <= 31 && day >= -31) {
        var d = new Date(date.getTime());
        var dYear = d.getFullYear();
        var dMonth = d.getMonth();
        var dDay = d.getDate();
        var rYear = dYear += year;
        var rMonth = dMonth += month;
        var rDay = dDay += day;
        d.setDate(rDay);
        d.setMonth(rMonth, dDay);
        d.setFullYear(rYear, rMonth, dDay);
        return d;
    }
    return null;
};

/**
 * 跟据时、分、秒的差值计算新的日期
 * @param date
 * @param hour
 * @param min
 * @param sec
 * @return {*}
 */
var calDateIn24 = function calDateIn24(date, hour, min, sec) {
    if (date && hour <= 23 && hour >= -23 && min <= 59 && min >= -59
        && sec <= 59 && sec >= -59) {
        var d = new Date(date.getTime());
        var dHours = d.getHours();
        var dMinutes = d.getMinutes();
        var dSeconds = d.getSeconds();
        dHours += hour;
        dMinutes += min;
        dSeconds += sec;
        d.setSeconds(dSeconds);
        d.setMinutes(dMinutes, dSeconds);
        d.setHours(dHours, dMinutes, dSeconds);
        return d;
    }
    return null;
};

/**
 * 判断年份是否为闰年
 * @param year
 * @return {boolean}
 */
var isLeapYear = function (year) {
    var bool = false;
    if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
        bool = true;
    }
    return bool;
};

/**
 * 获取所在月份的最大天数
 * @param year
 * @param month
 * @return {number}
 */
var getMonthMaxDay = function (year, month) {
    var day = 30;
    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
        day = 31;
    } else if (month == 4 || month == 6 || month == 9 || month == 11) {
        day = 30;
    } else if (month == 2) {
        day = isLeapYear(year) ? 29 : 28;
    }
    return day;
};

/**
 * 根据时间字符串获取Date对象
 * @param strDate 格式“yyyy-MM-dd”
 * @return {Date}
 */
var getDateByStrDate = function (strDate) {
    return new Date(Date.parse(strDate.replace(/-/g, "/")));
};

/**
 * 根据时间字符串获取秒数
 * @param strDate 格式“yyyy-MM-dd”
 * @return {Number}
 */
var getSecondByStrDate = function (strDate) {
    return new Date(Date.parse(strDate.replace(/-/g, "/"))).getTime();
};

/**
 * 非连续日期比较（离散）
 * @param a
 * @param b
 */
var compareScatterDate = function (a, b) {
    var beginDate = getSecondByStrDate(a);
    var endDate = getSecondByStrDate(b);
    if (beginDate > endDate) {
        return false;
    } else {
        return true;
    }
};

/**
 * 连续日期比较（区间）
 * @param a
 * @param b
 * @return {boolean}
 */
var compareContinuousDate = function (a, b) {
    var beginDate = getSecondByStrDate(a);
    var endDate = getSecondByStrDate(b);
    if (beginDate >= endDate) {
        return false;
    } else {
        return true;
    }
};

/**
 * 日期差值
 * @param a
 * @param b
 */
var differDate = function(a, b) {
    var beginDate = getSecondByStrDate(a);
    var endDate = getSecondByStrDate(b);
    return Math.abs((endDate - beginDate) / 86400000);
};

/**
 * 获取年份的数据集
 */
var getYearAC = function (now) {
    var result = [];
    var year = now.getFullYear();
    for (var i = year; i >= year - 19; i--) {
        var data = {};
        data["value"] = i + "";
        data["name"] = i + "年";
        result.push(data);
    }
    return result;
};

/**
 * 获取月份的数据集
 */
var getMonthAC = function () {
    var result = [];
    for (var i = 1; i <= 12; i++) {
        var data = {};
        data["value"] = zeroizeDate(i) + "";
        data["name"] = zeroizeDate(i) + "月";
        result.push(data);
    }
    return result;
};

/**
 * 时间格式补零
 *
 * @param dateVar
 */
var zeroizeDate = function (key) {
	return key < 10 ? "0" + key : key + "";
};

/**
 * 计算上一个15刻度
 * 
 * @param minutes 分钟
 */
var last15Scale = function(minutes) {
	return Math.floor(minutes / 15) * 15;
}

/**
 * 生成24点时间刻度
 */
var getTimeScale24 = function() {
	var data = [];
	var hour = '';
	for (var i = 0; i < 24; i++) {
		hour = zeroizeDate(i);
		data.push(hour + ':00');
	}
	return data;
}

/**
 * 生成96点时间刻度
 */
var getTimeScale96 = function() {
	var data = [];
	var hour = '';
	var minute = '';
	for (var i = 0; i < 24; i++) {
		hour = zeroizeDate(i);
		for (var j = 0; j < 4; j++) {
			minute = (j * 15) == 0 ? '00' : (j * 15) + '';
			data.push(hour + ':' + minute);
		}
	}
//	data.splice(0, 1) // 先移除首位的00:00
//	data.push('24:00'); // 再新增末尾的24:00
	return data;
}

/**
 * 生成日刻度（按月）
 * @param 年份
 * @param 月份
 */
var getDayScale = function(year, month) {
	var data = [];
	var maxDay = getMonthMaxDay(year, month);
	for (var i = 0; i < maxDay; i++) {
		data.push(zeroizeDate(i + 1));
	}
	return data;
}

/**
 * 生成月刻度（按年）
 */
var getMonthScale = function() {
	var data = [];
	for (var i = 0; i < 12; i++) {
		data.push(zeroizeDate(i + 1));
	}
	return data;
}