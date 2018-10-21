package com.bt.vims.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.Workbook;
import jxl.write.Label;

/**
 * @Title:CommonUtils
 * @Description: TODO
 * @author Ian.Huang
 * @date 2016年10月11日上午11:38:02
 */
public class CommonUtils {

	/**
	 * 
	 * @Description: TODO(使用序列化方法实现深度复制（相对靠谱的方法）)
	 * @param src
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @return: List<T>
	 * @author Ian.Huang
	 * @date 2016年10月31日下午12:29:24
	 */
	public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(src);
		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);
		@SuppressWarnings("unchecked")
		List<T> dest = (List<T>) in.readObject();
		return dest;
	}

	/**
	 * 
	 * @Description: TODO(获取当前服务MAC地址)
	 * @return
	 * @throws SocketException
	 * @throws UnknownHostException
	 * @return: Map<String,Object>
	 * @author Ian.Huang
	 * @date 2016年10月14日下午1:48:45
	 */
	public final static Map<String, Object> getLocalMac() throws SocketException, UnknownHostException {
		Map<String, Object> map = new HashMap<>();
		InetAddress iA = InetAddress.getLocalHost();
		System.out.println(iA);
		// 获取网卡，获取地址
		byte[] mac = NetworkInterface.getByInetAddress(iA).getHardwareAddress();
		// System.out.println("mac数组长度：" + mac.length);
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < mac.length; i++) {
			if (i != 0) {
				sb.append("-");

			}
			// 字节转换为整数
			int temp = mac[i] & 0xff;
			String str = Integer.toHexString(temp);
			// System.out.println("每8位:" + str);
			if (str.length() == 1) {
				sb.append("0" + str);

			} else {
				sb.append(str);

			}

		}
		// System.out.println("本机MAC地址:" + sb.toString().toUpperCase());
		map.put("service_name", iA.getHostName() + iA.getHostAddress());
		map.put("service_mac", sb.toString().toUpperCase());
		return map;

	}

	/**
	 * 
	 * @Description: TODO
	 * @param e
	 * @return
	 * @return: String
	 * @author Ian.Huang
	 * @date 2016年9月25日下午7:10:36
	 */
	public static String getExceptionStack(Exception e) {
		StackTraceElement[] stackTraceElements = e.getStackTrace();
		String result = e.toString() + "\n";
		for (int index = stackTraceElements.length - 1; index >= 0; --index) {
			result += "at [" + stackTraceElements[index].getClassName() + ",";
			result += stackTraceElements[index].getFileName() + ",";
			result += stackTraceElements[index].getMethodName() + ",";
			result += stackTraceElements[index].getLineNumber() + "]\n";
		}
		return result;
	}

	/**
	 * @Title: shiftFirstLetter @Description: TODO(切换首字母大小写) @param @param
	 * string @param @param type @param @return 设定文件 @return String 返回类型 @throws
	 */
	public static String shiftFirstLetter(String string, int type) {
		char[] cs = string.toCharArray();
		if (type == 0) {
			// 大写
			cs[0] -= 32;
		} else {
			// 小写
			cs[0] += 32;
		}
		return String.valueOf(cs);
	}

	/**
	 * 
	 * @Description: TODO(String[]转String[])
	 * @param str
	 * @return
	 * @return: Integer[]
	 * @author Ian.Huang
	 * @date 2016年8月9日下午1:47:48
	 */
	public static String[] toStringArray(String str) {
		return str.split(",");
	}

	/**
	 * 
	 * @Description: TODO(String转Integer[])
	 * @param str
	 * @return: Integer[]
	 * @author Ian.Huang
	 * @date 2016年8月9日下午1:47:48
	 */
	public static Integer[] strToIntegerArray(String str) {
		String[] arrayString = str.split(",");
		Integer[] arrayInteger = new Integer[arrayString.length];
		for (int i = 0; i < arrayString.length; i++) {
			arrayInteger[i] = Integer.parseInt(arrayString[i]);

		}
		return arrayInteger;

	}

	/**
	 * 
	 * @Description: TODO(年月日转年月)
	 * @param param
	 * @return
	 * @return: StringBuffer
	 * @author Ian.Huang
	 * @date 2016年8月1日上午11:35:02
	 */
	public static String convertToMonth(StringBuffer endDate) {
		return endDate.delete(endDate.lastIndexOf("-"), endDate.length())
				.replace(endDate.indexOf("-"), endDate.indexOf("-") + 2, "-").toString();
	}

	/**
	 * 
	 * @Description: TODO(数字是否在区间内)
	 * @param region
	 * @param num
	 * @return
	 * @return: boolean
	 * @author Ian.Huang
	 * @date 2016年7月19日上午10:14:52
	 */
	public static boolean inRegionOrNot(Map<String, Object> region, BigDecimal num) {
		Integer compare_1 = Integer.parseInt(region.get("compare_1").toString());
		BigDecimal num_1 = new BigDecimal(region.get("num_1").toString());
		Integer compare_2 = Integer.parseInt(region.get("compare_2").toString());
		BigDecimal num_2 = null;
		if (checkExistOrNot(region.get("num_2"))) {
			num_2 = new BigDecimal(region.get("num_2").toString());
		} else {
			num_2 = getMax(new BigDecimal[] { num_1, num }).add(new BigDecimal(1));
		}
		if ((num.compareTo(num_1) == 1 && num.compareTo(num_2) == -1) || (compare_1 == 1 && num.compareTo(num_1) == 0)
				|| (compare_2 == 3 && num.compareTo(num_2) == 0)) {
			return true;
		}
		return false;
	}

	/**
	 * @Title: checkRegion @Description: TODO(检验区间是否重叠) @param @param
	 * input @param @param existing @param @return 设定文件 @return JSONObject
	 * 返回类型 @throws
	 */
	public static JSONObject checkRegion(Map<String, Object> input, List<Map<String, Object>> existing) {
		JSONObject result = new JSONObject();
		Integer compare_1 = Integer.parseInt(input.get("compare_1").toString());
		BigDecimal num_1 = new BigDecimal(input.get("num_1").toString());
		Integer compare_2 = Integer.parseInt(input.get("compare_2").toString());
		BigDecimal num_2 = null;
		if (CommonUtils.checkExistOrNot(input.get("num_2"))) {
			num_2 = new BigDecimal(input.get("num_2").toString());
		}
		int flag = 0;
		BigDecimal num_1_existing = null;
		BigDecimal num_2_existing = null;
		Integer compare_1_existing = null;
		Integer compare_2_existing = null;
		Map<String, Object> param = new HashMap<String, Object>();
		for (int i = 0; i < existing.size(); i++) {
			param = existing.get(i);
			num_1_existing = new BigDecimal(param.get("num_1").toString());
			if (CommonUtils.checkExistOrNot(param.get("num_2"))) {
				num_2_existing = new BigDecimal(param.get("num_2").toString());
				if (!CommonUtils.checkExistOrNot(num_2)) {
					num_2 = CommonUtils.getMax(new BigDecimal[] { num_1, num_1_existing, num_2_existing })
							.add(new BigDecimal(1));
					flag = 1;
				}
			} else {
				if (!CommonUtils.checkExistOrNot(num_2)) {
					num_2 = num_2_existing = CommonUtils.getMax(new BigDecimal[] { num_1, num_1_existing })
							.add(new BigDecimal(1));
					flag = 1;
				} else {
					num_2_existing = CommonUtils.getMax(new BigDecimal[] { num_1, num_1_existing, num_2 })
							.add(new BigDecimal(1));
				}
			}
			compare_1_existing = Integer.parseInt(param.get("compare_1").toString());
			compare_2_existing = Integer.parseInt(param.get("compare_2").toString());
			if (num_2.compareTo(num_1_existing) == -1 || num_1.compareTo(num_2_existing) == 1) {
				// 通过-成功
				if (flag == 1) {
					num_2 = null;
				}
				continue;
			} else if (num_2.compareTo(num_1_existing) == 0) {
				if (compare_2 == 3 && 1 == compare_1_existing) {
					result.put("result_code", "FAILURE");
					result.put("result_content", "新增阶梯区间失败,失败原因:阶梯区间重叠！");
					return result;
				} else {
					if (flag == 1) {
						num_2 = null;
					}
					continue;
				}
			} else if (num_1.compareTo(num_2_existing) == 0) {
				if (compare_1 == 1 && 3 == compare_2_existing) {
					result.put("result_code", "FAILURE");
					result.put("result_content", "新增阶梯区间失败,失败原因:阶梯区间重叠！");
					return result;
				} else {
					if (flag == 1) {
						num_2 = null;
					}
					continue;
				}
			} else {
				// 不通过-报错
				result.put("result_code", "FAILURE");
				result.put("result_content", "新增阶梯区间失败,失败原因:阶梯区间重叠！");
				return result;
			}
		}
		return result;
	}

	/**
	 * 
	 * @Description: TODO(取最大数)
	 * @param nums
	 * @return
	 * @return: Integer
	 * @author Ian.Huang
	 * @date 2016年7月1日下午2:43:00
	 */
	public static BigDecimal getMax(BigDecimal[] nums) {
		BigDecimal max = nums[0];
		for (int i = 1; i < nums.length; i++) {
			if (max.compareTo(nums[i]) == -1) {
				max = nums[i];
			}
		}
		return max;
	}

	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @return: Map<String,Object>
	 * @author Ian.Huang
	 * @date 2016年12月20日上午11:14:21
	 */
	public static Map<String, Object> getParamMap(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Object key : request.getParameterMap().keySet()) {
			map.put((String) key, request.getParameter(key.toString()).trim());
			System.out.println(key + ":" + request.getParameter(key.toString()).trim());

		}
		// 获取当前操作用户
		map.put("currentUser", SessionUtils.getEMP(request).getId());
		return map;

	}

	/**
	 * 
	 * @Description: TODO(拆分周期字符串)
	 * @param param
	 * @return: Map<String,String>
	 * @author Ian.Huang
	 * @date 2016年6月25日下午9:09:45
	 */
	public static Map<String, String> spiltDateString(String param) {
		Map<String, String> map = new HashMap<String, String>();
		String date[] = param.split(" - ");
		map.put("startDate", date[0]);
		map.put("endDate", date[1]);
		return map;

	}

	/**
	 * 
	 * @Title: spiltDateStringByTilde @Description: TODO(时间周期拆分~) @param @param
	 * param @param @return 设定文件 @author likun @return Map<String,String>
	 * 返回类型 @throws
	 */
	public static Map<String, String> spiltDateStringByTilde(String param) {
		Map<String, String> map = new HashMap<String, String>();
		String date[] = param.split(" ~ ");
		map.put("startDate", date[0]);
		map.put("endDate", date[1]);
		return map;

	}

	/**
	 * 
	 * @Description: TODO(判断)
	 * @param obj
	 * @return
	 * @return: boolean
	 * @author Ian.Huang
	 * @date 2016年6月25日下午9:13:01
	 */
	public static boolean checkExistOrNot(Object obj) {
		// 空对象
		if (obj == null) {
			return false;
		}
		// 空字符串
		if (obj.toString().equals("")) {
			return false;
		}
		// 空数组
		if (obj.getClass().isArray()) {
			// 如果是数组
			Object[] objs = (Object[]) obj;
			if (objs.length == 0) {
				// 当数组长度为0，则认定为空
				return false;
			}
		}
		// 空集合
		if (obj instanceof List) {
			// 为List集合
			List<?> objList = (List<?>) obj;
			if (objList.size() == 0) {
				// 当List集合长度为0，则认定为空
				return false;
			}
		}
		return true;
	}

	/**
	 *
	 * @Title: isExist
	 * @Description: TODO(判断数值是否存在区间中)
	 * @param data(数值)
	 * @param section(区间)
	 * @return boolean
	 * @author likun
	 */
	public static boolean isExist(float data, String section) {
		Map<String, Object> map = getParam(section);
		String type = (String) map.get("type");
		if (type.equals("2")) {
			String mark1 = (String) map.get("mark1");
			String param1 = (String) map.get("param1");
			String mark2 = (String) map.get("mark2");
			String param2 = (String) map.get("param2");
			// (:3 ,[:30, ):4, ]:40
			boolean a = mark1.equals("30") && data >= Float.valueOf(param1) && mark2.equals("40")
					&& data <= Float.valueOf(param2) ? true : false;
			boolean b = mark1.equals("3") && data > Float.valueOf(param1) && mark2.equals("4")
					&& data < Float.valueOf(param2) ? true : false;
			boolean c = mark1.equals("30") && data >= Float.valueOf(param1) && mark2.equals("4")
					&& data < Float.valueOf(param2) ? true : false;
			boolean d = mark1.equals("3") && data > Integer.valueOf(param1) && mark2.equals("40")
					&& data <= Float.valueOf(param2) ? true : false;
			if (a || b || c || d) {
				return true;
			} else {
				return false;
			}
		} else {
			// mark: >:1,>=:10,<:2,<=:20
			String mark = (String) map.get("mark");
			String param = (String) map.get("param");
			boolean a = mark.equals("1") && data > Float.valueOf(param) ? true : false;
			boolean b = mark.equals("10") && data >= Float.valueOf(param) ? true : false;
			boolean c = mark.equals("2") && data < Float.valueOf(param) ? true : false;
			boolean d = mark.equals("20") && data <= Float.valueOf(param) ? true : false;
			if (a || b || c || d) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 
	 * @Title: isExist
	 * @Description: TODO(区间交集判断，参数为两个区间)
	 * @param @param
	 *            sec1
	 * @param @param
	 *            sec2
	 * @param @return
	 *            设定文件
	 * @author likun
	 * @return boolean 返回类型
	 * @throws (:3
	 *             ,[:30, ):4, ]:40 >:1, >=10, < 2, <=:20
	 */
	public static boolean isExist(String sec1, String sec2) {
		if (sec1.equals(sec2)) {
			throw new RuntimeException("区间已经存在,无法添加！");
		}
		Map<String, Object> se1 = getParam(sec1);
		Map<String, Object> se2 = getParam(sec2);
		String type = (String) se1.get("type");
		String type2 = (String) se2.get("type");
		if (type.equals("2")) {
			Integer mark1_1 = Integer.valueOf((String) se1.get("mark1"));
			Float param1_1 = Float.valueOf((String) se1.get("param1"));
			Integer mark1_2 = Integer.valueOf((String) se1.get("mark2"));
			Float param1_2 = Float.valueOf((String) se1.get("param2"));
			if (type2.equals("2")) {
				Integer mark2_1 = Integer.valueOf((String) se2.get("mark1"));
				Float param2_1 = Float.valueOf((String) se2.get("param1"));
				// Integer mark2_2= Integer.valueOf((String)se2.get("mark2"));
				Float param2_2 = Float.valueOf((String) se2.get("param2"));
				// 并集1-2 或2-1
				if ((param1_1 <= param2_1 && param1_2 >= param2_2) || (param2_1 <= param1_1 && param2_1 >= param1_2)) {
					throw new RuntimeException("区间" + sec1 + "和" + sec2 + "存在并集,无法添加！");
				}
				// 交集
				if (isExist(param1_1, sec2)) {
					if (param1_1 - param2_2 == 0) {
						if (isExist(param1_1, sec1)) {
							throw new RuntimeException("区间" + sec1 + "和" + sec2 + "存在交集,无法添加！");
						}
					} else {
						throw new RuntimeException("区间" + sec1 + "和" + sec2 + "存在交集,无法添加！");
					}
				}
				if (isExist(param1_2, sec2)) {
					if (param1_2 - param2_1 == 0) {
						if (mark1_2 == 30 && mark2_1 == 40) {
							throw new RuntimeException("区间" + sec1 + "和" + sec2 + "存在交集,无法添加！");
						}
					} else {
						throw new RuntimeException("区间" + sec1 + "和" + sec2 + "存在交集,无法添加！");
					}
				}
			} else {
				Integer mark2_1 = Integer.valueOf((String) se2.get("mark"));
				Float param2_1 = Float.valueOf((String) se2.get("param"));
				// 交集
				if (isExist(param1_1, sec2)) {
					if (param1_2 - param2_1 == 0) {
						if (mark2_1 == 10 || mark2_1 == 20) {
							throw new RuntimeException("区间" + sec1 + "和" + sec2 + "存在交集,无法添加！");
						}
					} else {
						throw new RuntimeException("区间" + sec1 + "和" + sec2 + "存在交集,无法添加！");
					}
				}
				// 区间(0,200)和>=200存在交集,无法添加！
				if (isExist(param1_2, sec2)) {
					if (param1_2 - param2_1 == 0) {
						if (mark1_1 == 10 || mark1_1 == 20) {
							throw new RuntimeException("区间" + sec1 + "和" + sec2 + "存在交集,无法添加！");
						}
					} else {
						throw new RuntimeException("区间" + sec1 + "和" + sec2 + "存在交集,无法添加！");
					}

				}
			}
			// (:3 ,[:30, ):4, ]:40
		} else {
			if (type2.equals("2")) {
				Float param2_1 = Float.valueOf((String) se2.get("param1"));
				Float param2_2 = Float.valueOf((String) se2.get("param2"));
				Integer mark2_1 = Integer.valueOf((String) se2.get("mark1"));
				Integer mark1_1 = Integer.valueOf((String) se1.get("mark"));
				Float param1_1 = Float.valueOf((String) se1.get("param"));
				if (isExist(param2_1, sec1)) {
					if (param2_1 - param1_1 == 0) {
						if (mark2_1 == 30 && mark1_1 == 10) {
							throw new RuntimeException("区间" + sec1 + "和" + sec2 + "存在交集,无法添加！");
						} else {

						}
					} else {
						throw new RuntimeException("区间" + sec1 + "和" + sec2 + "存在交集,无法添加！");
					}
				}
				if (isExist(param2_2, sec1)) {
					if (param2_1 - param1_1 == 0) {
						if (mark2_1 == 30 && mark1_1 == 10) {
							throw new RuntimeException("区间" + sec1 + "和" + sec2 + "存在交集,无法添加！");
						} else {

						}
					} else {
						throw new RuntimeException("区间" + sec1 + "和" + sec2 + "存在交集,无法添加！");
					}
				}
			} else {
				Float param2_1 = Float.valueOf((String) se2.get("param"));
				Float param1_1 = Float.valueOf((String) se1.get("param"));
				Float mark1_1 = Float.valueOf((String) se1.get("mark"));
				Integer mark2_1 = Integer.valueOf((String) se2.get("mark"));
				if (isExist(param2_1, sec1)) {
					if (param2_1 - param1_1 == 0) {
						if ((mark2_1 == 10 || mark2_1 == 20)) {
							throw new RuntimeException("区间" + sec1 + "和" + sec2 + "存在交集,无法添加！");
						}
					} else {
						throw new RuntimeException("区间" + sec1 + "和" + sec2 + "存在交集,无法添加！");
					}
				}
				if (isExist(param1_1, sec2)) {
					if (param2_1 - param1_1 == 0) {
						if ((mark1_1 == 10 || mark1_1 == 20)) {
							throw new RuntimeException("区间" + sec1 + "和" + sec2 + "存在交集,无法添加！");
						}
					} else {
						throw new RuntimeException("区间" + sec1 + "和" + sec2 + "存在交集,无法添加！");
					}
				}
			}
		}
		return false;

	}

	/**
	 * 
	 * @Title: getParam @Description: TODO(获取区间参数) @param @param
	 * secs @param @return 设定文件 @author likun @return Map 返回类型 @throws
	 */
	public static Map<String, Object> getParam(String secs) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] result = secs.split(",");
		Integer type = result.length == 1 ? 1 : 2;
		if (type == 1) {
			map.put("type", "1");
			if (secs.contains(">")) {
				if (secs.contains(">=")) {
					map.put("mark", "10");
					map.put("param", secs.split(">=")[1]);
					return map;
				} else {
					map.put("mark", "1");
					map.put("param", secs.split(">")[1]);
					return map;
				}
			}

			if (secs.contains("<")) {
				if (secs.contains("<=")) {
					map.put("mark", "20");
					map.put("param", secs.split("<=")[1]);
					return map;
				} else {
					map.put("mark", "2");
					map.put("param", secs.split("<")[1]);
					return map;
				}
			}
		}

		if (type == 2) {
			map.put("type", "2");
			String rs1 = result[0];
			String rs2 = result[1];
			if (rs1.contains("(")) {
				map.put("mark1", "3");
				map.put("param1", rs1.split("\\(")[1]);
			}
			if (rs1.contains("[")) {
				map.put("mark1", "30");
				map.put("param1", rs1.split("\\[")[1]);
			}

			if (rs2.contains(")")) {
				map.put("mark2", "4");
				map.put("param2", rs2.split("\\)")[0]);
			}
			if (rs2.contains("]")) {
				map.put("mark2", "40");
				map.put("param2", rs2.split("\\]")[0]);
			}

		}
		return map;
	}

	public static Map<String, Object> convertBean(Object bean)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class<?> type = bean.getClass();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);

				} else {
					returnMap.put(propertyName, "");

				}

			}

		}
		return returnMap;

	}

	@SuppressWarnings("unchecked")
	public static File excelDownLoadData(List<?> result, Map<String, String> cMap, String sheetName) {
		/**
		 * Cmap : 列名显示Map (key:查询结果集的字段，value:中文显示列名) sheetName : 生成sheet名称
		 */
		String fileName = "C:/temp/" + sheetName;
		System.out.println(fileName);
		WritableWorkbook book = null;
		WritableSheet sheet = null;
		// WritableSheet sheet2 = null;
		File file = null;
		try {
			file = new File(fileName);
			book = Workbook.createWorkbook(file);
			sheet = book.createSheet("sheet", 0);
			// sheet2 = book.createSheet("sheet2", 1);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		int r = 0;
		Collection<?> col = null;
		String tempStr = "";
		Label label;
		Map<String, Object> map = new HashMap<String, Object>();
		// 先放入标题行
		col = cMap.keySet();
		for (Object o : col) {
			try {
				tempStr = cMap.get(o.toString()).toString();
			} catch (Exception e) {
				tempStr = "";
			}
			label = new Label(r, 0, tempStr);
			try {
				sheet.addCell(label);
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
			r++;
		}
		r = 0;
		List<?> list = result;
		// 放入查询结果
		for (int i = 0; i < list.size(); i++) {
			map = (HashMap<String, Object>) list.get(i);
			for (Object o : col) {
				try {
					tempStr = map.get(o.toString()).toString();
				} catch (Exception e) {
					tempStr = "";
				}
				label = new Label(r, i + 1, tempStr);
				try {
					sheet.addCell(label);
				} catch (RowsExceededException e) {
					e.printStackTrace();
				} catch (WriteException e) {
					e.printStackTrace();
				}
				r++;
			}
			r = 0;
		}
		try {
			book.write();
			book.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return file;
	}

	public static String getDownloadFileName(String sheetName) {
		String downFileName = sheetName;
		try {
			downFileName = new String(downFileName.getBytes(), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return downFileName;
	}

	/**
	 * @Title: getAllMessage @Description: TODO(这里用一句话描述这个方法的作用) @param @param
	 * propertyName 调用方式： 1.配置文件放在resource源包下，不用加后缀
	 * PropertiesUtil.getAllMessage("config"); 2.放在包里面的
	 * PropertiesUtil.getAllMessage("com.bt.lmis.config"); @param @param
	 * key @param @return 设定文件 @return String 返回类型 @throws
	 */
	public static String getAllMessage(String propertyName, String key) {
		// 获得资源包
		ResourceBundle rb = ResourceBundle.getBundle(propertyName.trim());
		// 通过资源包拿到所有的key
		Enumeration<String> allKey = rb.getKeys();
		// 遍历key 得到 value
		while (allKey.hasMoreElements()) {
			String keys = allKey.nextElement();
			String value = (String) rb.getString(key);
			if (key.equals(keys)) {
				return value;

			}

		}
		return null;

	}

	@SuppressWarnings("unchecked")
	public static File excelDownLoadData2(List<?> lis, String fileNames) throws IOException, WriteException {
		/**
		 * Cmap : 列名显示Map (key:查询结果集的字段，value:中文显示列名) sheetName : 生成sheet名称
		 */

		String filePath = "";
		if (OSinfo.getOSname().equals(EPlatform.Mac_OS_X)) {
			filePath = CommonUtils.getAllMessage("config", "MAC_OX_excel_prefix");
		} else {
			filePath = CommonUtils.getAllMessage("config", "WINDOWS_excel_prefix");
		}
		String fileName = filePath + fileNames;
		WritableWorkbook book = null;
		File file = null;
		Map<String, Object> dMap = new HashMap<String, Object>();
		Map<String, String> cMap = new HashMap<String, String>();
		Map<String, Object> map = new HashMap<String, Object>();
		String sheetName = null;
		List<?> data = new ArrayList<>();
		file = new File(fileName);
		book = Workbook.createWorkbook(file);

		for (int i = 0; i < lis.size(); i++) {
			dMap = (Map<String, Object>) lis.get(i);

			cMap = (Map<String, String>) dMap.get("title");
			data = (List<?>) dMap.get("data");
			sheetName = (String) dMap.get("sheetName");
			WritableSheet sheet = book.createSheet(sheetName, i);
			int r = 0;
			Collection<?> col = null;
			String tempStr = "";
			Label label;
			// 先放入标题行
			col = cMap.keySet();
			for (Object o : col) {
				try {
					tempStr = cMap.get(o.toString()).toString();
				} catch (Exception e) {
					tempStr = "";
				}
				label = new Label(r, 0, tempStr);
				try {
					sheet.addCell(label);
				} catch (RowsExceededException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (WriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				r++;
			}
			r = 0;
			List<?> list = data;
			for (int k = 0; k < list.size(); k++) {
				map = (HashMap<String, Object>) list.get(k);
				for (Object o : col) {
					try {
						tempStr = map.get(o.toString()).toString();
					} catch (Exception e) {
						tempStr = "";
					}
					label = new Label(r, k + 1, tempStr);
					try {
						sheet.addCell(label);
					} catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					r++;
				}
				r = 0;
			}
		}
		book.write();
		book.close();
		return file;
	}

	/**
	 * 
	 * @Description: TODO
	 * @param is
	 * @return
	 * @return: byte[]
	 * @author Ian.Huang
	 * @date 2016年10月14日下午1:50:57
	 */
	public static byte[] getbyt(InputStream is) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] bs = new byte[1024];
		int len = -1;
		try {
			while ((len = is.read(bs)) != -1) {
				bos.write(bs, 0, len);
			}

			bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte b[] = bos.toByteArray();
		return b;
	}

	/**
	 * 浏览器地址数据转换成字符串的方法
	 * 
	 * @param str
	 * @return
	 */
	public static String getStringFromBrowser(String str, String encoding) {
		String newStr = "";
		StringBuffer stringBufferResult = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char chr = str.charAt(i);
			if (chr == '%') {
				StringBuffer stringTmp = new StringBuffer();
				stringTmp.append(str.charAt(i + 1)).append(str.charAt(i + 2));
				// 转换字符，16进制转换成整型
				stringBufferResult.append((char) (Integer.valueOf(stringTmp.toString(), 16).intValue()));
				i = i + 2;
				continue;
			}
			stringBufferResult.append(chr);
		}

		try {
			// 编码转换
			if ("GBK".equals(encoding)) {
				newStr = new String(stringBufferResult.toString().getBytes("GBK"), "UTF-8");
			} else if ("GB2312".equals(encoding)) {
				newStr = new String(stringBufferResult.toString().getBytes("GB2312"), "UTF-8");
			} else {
				newStr = new String(stringBufferResult.toString().getBytes("ISO-8859-1"), "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newStr;
	}

	/**
	 * 判断字符串的编码方式
	 * 
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str) {
		String encoding = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(), encoding))) {
				return encoding;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		encoding = "GBK";
		try {
			if (str.equals(new String(str.getBytes(), encoding))) {
				return encoding;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		encoding = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(), encoding))) {
				return encoding;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		encoding = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(), encoding))) {
				return encoding;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
