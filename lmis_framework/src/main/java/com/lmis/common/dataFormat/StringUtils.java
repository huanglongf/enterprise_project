package com.lmis.common.dataFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * @ClassName: StringUtils
 * @Description: TODO(字符串工具类)
 * @author Ian.Huang
 * @date 2017年9月28日 下午7:27:31 
 * 
 */
public class StringUtils {
	
	/**
	 * @Title: isBlank
	 * @Description: TODO(
	 * 可能为空格或者""及null的字符序列
	 * 
	 * StringUtils.isBlank(null) = true
	 * StringUtils.isBlank(&quot;&quot;) = true
	 * StringUtils.isBlank(&quot; &quot;) = true
	 * StringUtils.isBlank(&quot;bob&quot;) = false
	 * StringUtils.isBlank(&quot;  bob  &quot;) = false
	 * )
	 * @param cs
	 * @return: boolean
	 * @author: Ian.Huang
	 * @date: 2017年9月27日 下午8:20:17
	 */
	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(cs.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @Title: isBlank
	 * @Description: TODO(验证字符串数组是否为空)
	 * @param css
	 * @return: boolean
	 * @author: Ian.Huang
	 * @date: 2017年9月27日 下午8:22:15
	 */
	public static boolean isBlank(final CharSequence ... css) {
		if (css == null)
			return true;
		
		for (CharSequence cs : css) {
			if(isNotBlank(cs)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @Title: isNotBlank
	 * @Description: TODO(验证可能为""或者null的字符串)
	 * @param cs 可能为空的字符串
	 * @return: boolean
	 * @author: Ian.Huang
	 * @date: 2017年9月27日 下午8:22:49
	 */
	public static boolean isNotBlank(final CharSequence cs) {
		return !isBlank(cs);
	}
	
	/**
	 * @Title: join
	 * @Description: TODO(把数组内的对象通过分隔符连接为字符串)
	 * @param array 对象数组
	 * @param separator 分隔符
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年9月27日 下午8:23:50
	 */
	public static String join(final Object[] array, final String separator) {
		return join(Arrays.asList(array), separator);
	}
	
	/**
	 * @Title: join
	 * @Description: TODO(连接迭代器内对象为字符串)
	 * @param it 实现迭代接口的集合
	 * @param separator 分隔符
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年9月27日 下午8:24:14
	 */
	public static String join(final Iterable<?> it, final String separator) {
		Iterator<?> iterator = null;
		if ((iterator = it.iterator()) == null || !iterator.hasNext()) {
			return null;
		}
		Object first = iterator.next();
		if (!iterator.hasNext()) {
			return String.valueOf(first);
		}

		StringBuilder buf = new StringBuilder().append(first);
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			if (obj != null) {
				buf.append(separator).append(obj);
			}
		}
		return buf.toString();
	}
	
	/**
	 * @Title: substring
	 * @Description: TODO(截取指定长度字符串，超长部分截取后加"...")
	 * @param str 源字符串
	 * @param length 截取长度
	 * @param dot 是否为未显示内容添加"..."
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年9月27日 下午8:24:39
	 */
	public static String substring(String str, final int length, final boolean dot) {
		return substring(str, 0, length, dot);
	}
	
	/**
	 * @Title: substring
	 * @Description: TODO(截取指定长度字符串，超长部分截取后加"...")
	 * @param str 源字符串
	 * @param beginIndex 开始下标(包含本身)
	 * @param endIndex 结束下标(不包含本身)
	 * @param dot 是否为未显示内容添加"..."
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年9月27日 下午8:25:19
	 */
	public static String substring(String str, int beginIndex, int endIndex, final boolean dot) {
		if (str != null) {
			if (str.length() >= (endIndex + 1)) {
				str = str.substring(beginIndex, endIndex);
				return dot ? str + "..." : str;
			}
		}
		return str;
	}
	
	/**
	 * @Title: split
	 * @Description: TODO(以分隔标记分隔字符串为字符串数组)
	 * @param str 源字符串
	 * @param separator 分隔符,字符串或正则表达式对象，它标识了分隔字符串时使用的是一个还是多个字符。
	 * @return: String[]
	 * @author: Ian.Huang
	 * @date: 2017年9月27日 下午8:25:51
	 */
	public static String[] split(final String str, final String separator) {
		return split(str, separator, false);
	}
	
	/**
	 * @Title: split
	 * @Description: TODO(以分隔标记分隔字符串,为字符串数组)
	 * @param str 源字符串
	 * @param separator 分隔符
	 * @param bool 是否把分隔符添加到字符串尾部返回
	 * @return: String[]
	 * @author: Ian.Huang
	 * @date: 2017年9月27日 下午8:26:21
	 */
	public static String[] split(String str, String separator, boolean bool) {
		String[] strs = null;
		if (str != null) {
			strs = str.split(separator);
			if (bool) {
				for (int i = 0, length = strs.length; i < length; i++) {
					strs[i] += separator;
				}
			}
		}
		return strs;
	}
	
	/**
	 * @Title: split
	 * @Description: TODO(
	 * 将字符串以相同步长分隔生成List返回
	 * 
	 * StringUtil.split("我的世界因为有你才会美",3);   --> "[我的世, 界因为, 有你才, 会美]"
	 * StringUtil.split("我的世界因为有你才会美",2);   --> "[我的, 世界, 因为, 有你, 才会, 美]"
	 * )
	 * @param str 源字符串
	 * @param step 拆分步长
	 * @return: List<String>
	 * @author: Ian.Huang
	 * @date: 2017年9月27日 下午8:27:34
	 */
	public static List<String> split(final String str, int step) {
		if (str != null && step > 0) {
			List<String> list;
			if (step > str.length()) {
				list = new ArrayList<String>(1);
				list.add(str);
			}
			else {
				list = new ArrayList<String>();

				int i = 0;
				do {
					list.add(str.substring(i, i += step));
				}
				while ((i + step) < str.length());

				if (i < str.length()) {
					list.add(str.substring(i, str.length()));
				}
			}
			return list;
		}
		return null;
	}
	
	/**
	 * @Title: underline2Hump
	 * @Description: TODO(下划线转驼峰)
	 * @param str
	 * @param littleCamelCase 是否小驼峰
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年12月26日 下午6:03:58
	 */
	public static String underline2Hump(String str, boolean littleCamelCase) {
        if(str == null || "".equals(str)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()) {
            String word = matcher.group();
            sb.append(littleCamelCase && matcher.start() == 0 ?
            		Character.toLowerCase(word.charAt(0)) :
            			Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if(index > 0){
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }
	
}
