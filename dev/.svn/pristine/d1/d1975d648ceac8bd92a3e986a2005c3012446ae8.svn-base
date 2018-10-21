package com.bt.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* @ClassName: SortUtil 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Yuriy.Jiang
* @date 2016年7月1日 上午10:36:03 
*  
*/
public class IntervalValidationUtil {
	
	/** 
	* @Title: bubbleSort 
	* @Description: TODO(冒泡排序) 
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> bubbleSort(List<Map<String, Object>> sortList){
		return null;
	}
	
	/** 
	* @Title: verification 
	* @Description: TODO(验证插入数据设否合法) 
	* @param @param str
	* @param @param compareData
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	public static boolean verification(String str,String str2){
		Map<String, Integer> section = getStartEndSection(str);
		int A1 = section.get("startNum");
		int A2 = section.get("endNum");
		Map<String, Integer> section2 = getStartEndSection(str2);
		int B1 = section2.get("startNum");
		int B2 = section2.get("endNum");
		
		BigDecimal begin = A1<B1 ? BigDecimal.valueOf(B1) :BigDecimal.valueOf(A1);
		BigDecimal end = A2>B2 ? BigDecimal.valueOf(B2) :BigDecimal.valueOf(A2);;
		BigDecimal len = end.subtract(begin);
		if(len.compareTo(BigDecimal.valueOf(0))>0){
			return false;
		}
		return true;
	}
	
	public static Map<String, Integer> strToMap(String section){
		String greaterhan=".*\\(.*"; 
		String lesshan=".*\\).*";
		String greaterThanOrEqualTo=".*\\[.*";
		String lessThanOrEqualTo=".*\\].*";
		String startNum = "";
		String endNum = "";
		Map<String, Integer> returnMap = new HashMap<String, Integer>();
		if(section.indexOf(">")>0){
			returnMap.put("type", 0);
			returnMap.put("startSymbol", 0);
			startNum=section.substring(section.indexOf(">")+1,startNum.length());
			returnMap.put("startNum", Integer.valueOf(startNum));
		}else if(section.indexOf("<")>0){
			returnMap.put("type", 0);
			returnMap.put("startSymbol", 1);
			startNum=section.substring(section.indexOf("<")+1,startNum.length());
			returnMap.put("startNum", Integer.valueOf(startNum));
		}else{
			if(section.matches(greaterhan)){
				returnMap.put("type", 1);
				returnMap.put("startSymbol", 0);
				startNum=section.substring(section.indexOf("(")+1,section.indexOf(","));
			}
			if(section.matches(greaterThanOrEqualTo)){
				returnMap.put("type", 1);
				returnMap.put("startSymbol", 1);
				startNum=section.substring(section.indexOf("[")+1,section.indexOf(","));
			}
			if(section.matches(lesshan)){
				returnMap.put("type", 1);
				returnMap.put("endSymbol", 0);
				endNum=section.substring(section.indexOf(",")+1,section.indexOf(")"));
			}
			if(section.matches(lessThanOrEqualTo)){
				returnMap.put("type", 1);
				returnMap.put("endSymbol", 1);
				endNum=section.substring(section.indexOf(",")+1,section.indexOf("]"));
			}
			returnMap.put("startNum", Integer.valueOf(startNum));
			returnMap.put("endNum", Integer.valueOf(endNum));
		}
		return returnMap;
	}
	

	public static Map<String, Object> strToMapS(String section){
		String greaterhan=".*\\(.*"; 
		String lesshan=".*\\).*";
		String greaterThanOrEqualTo=".*\\[.*";
		String lessThanOrEqualTo=".*\\].*";
		String startNum = "";
		String endNum = "";
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if(section.indexOf(">")>0){
			returnMap.put("type", 0);
			returnMap.put("startSymbol", 0);
			startNum=section.substring(section.indexOf(">")+1,startNum.length());
			returnMap.put("startNum", Integer.valueOf(startNum));
		}else if(section.indexOf("<")>0){
			returnMap.put("type", 0);
			returnMap.put("startSymbol", 1);
			startNum=section.substring(section.indexOf("<")+1,startNum.length());
			returnMap.put("startNum", Integer.valueOf(startNum));
		}else{
			if(section.matches(greaterhan)){
				returnMap.put("type", 1);
				returnMap.put("startSymbol", 0);
				startNum=section.substring(section.indexOf("(")+1,section.indexOf(","));
			}
			if(section.matches(greaterThanOrEqualTo)){
				returnMap.put("type", 1);
				returnMap.put("startSymbol", 1);
				startNum=section.substring(section.indexOf("[")+1,section.indexOf(","));
			}
			if(section.matches(lesshan)){
				returnMap.put("type", 1);
				returnMap.put("endSymbol", 0);
				endNum=section.substring(section.indexOf(",")+1,section.indexOf(")"));
			}
			if(section.matches(lessThanOrEqualTo)){
				returnMap.put("type", 1);
				returnMap.put("endSymbol", 1);
				endNum=section.substring(section.indexOf(",")+1,section.indexOf("]"));
			}
			returnMap.put("startNum", new BigDecimal(startNum));
			returnMap.put("endNum", new BigDecimal(endNum));
		}
		return returnMap;
	}
	
	/** 
	* @Title: getStartEndSection 
	* @Description: TODO(区间转换数值) 
	* @param @param section
	* @param @return    设定文件 
	* @return Map<String,Integer>    返回类型 
	* @throws 
	*/
	public static Map<String, Integer> getStartEndSection(String section){
		String greaterhan=".*\\(.*"; 
		String lesshan=".*\\).*";
		String greaterThanOrEqualTo=".*\\[.*";
		String lessThanOrEqualTo=".*\\].*";
		String startNum = "";
		String endNum = "";
		Map<String, Integer> returnMap = new HashMap<String, Integer>();
		if(section.matches(greaterhan)){
			returnMap.put("startSymbol", 0);
			startNum=section.substring(section.indexOf("(")+1,section.indexOf(","));
		}
		if(section.matches(greaterThanOrEqualTo)){
			returnMap.put("startSymbol", 1);
			startNum=section.substring(section.indexOf("{")+1,section.indexOf(","));
		}
		if(section.matches(lesshan)){
			returnMap.put("endSymbol", 0);
			endNum=section.substring(section.indexOf(",")+1,section.indexOf(")"));
		}
		if(section.matches(lessThanOrEqualTo)){
			returnMap.put("endSymbol", 1);
			endNum=section.substring(section.indexOf(",")+1,section.indexOf("}"));
		}
		returnMap.put("startNum", Integer.valueOf(startNum));
		returnMap.put("endNum", Integer.valueOf(endNum));
		return returnMap;
	}
	
	public static void main(String[] args) {
//		System.out.println(verification("(30,50)","(40,100)"));
//		System.out.println(verification("(30,50)","(50,100)"));
		System.out.println(strToMap("[5,6]"));
		System.out.println(strToMap("(5,6]"));
	}
	
	
	public static boolean isExist(String sec1,String sec2){
		if(sec1.equals(sec2)){
			throw new RuntimeException("区间已经存在,无法添加！");	
		}
		Map se1=getParam(sec1);
		Map se2=getParam(sec2);
		String type=(String) se1.get("type");
		String type2=(String) se2.get("type");
		if(type.equals("2")){
			Integer mark1_1=Integer.valueOf((String) se1.get("mark1"));
			Float param1_1=Float.valueOf((String) se1.get("param1"));
			Integer mark1_2=Integer.valueOf((String) se1.get("mark2"));
			Float param1_2=Float.valueOf((String) se1.get("param2"));		
			
			if(type2.equals("2")){
			Integer mark2_1=Integer.valueOf((String) se2.get("mark1"));
			Float param2_1=Float.valueOf((String) se2.get("param1"));
			Integer mark2_2=Integer.valueOf((String) se2.get("mark2"));
			Float param2_2=Float.valueOf((String) se2.get("param2"));	
	          //并集1-2  或2-1
				if((param1_1<=param2_1 && param1_2>=param2_2) || (param2_1<=param1_1&& param2_1>=param1_2)){
					throw new RuntimeException("区间"+sec1+"和"+sec2+"存在并集,无法添加！");
				}			
				  //交集
					if(isExist(param1_1, sec2)){
						if(param1_1-param2_2==0){
							if(isExist(param1_1,sec1)){
							throw new RuntimeException("区间"+sec1+"和"+sec2+"存在交集,无法添加！");
							}
						}else{
							throw new RuntimeException("区间"+sec1+"和"+sec2+"存在交集,无法添加！");
						}
					 }
				
					if(isExist(param1_2, sec2)){
	                     if(param1_2-param2_1==0){
                        	 if(mark1_2==30 &&  mark2_1==40){
                        		 throw new RuntimeException("区间"+sec1+"和"+sec2+"存在交集,无法添加！");
                        	 }
                         }else{
                        	 throw new RuntimeException("区间"+sec1+"和"+sec2+"存在交集,无法添加！");
                         }
					}				
			}else{
				Integer mark2_1=Integer.valueOf((String) se2.get("mark"));
				Float param2_1=Float.valueOf((String) se2.get("param"));	
				
				  //交集
					if(isExist(param1_1, sec2)){
						if(param1_2-param2_1==0){
							if(mark2_1==10 || mark2_1==20){
								throw new RuntimeException("区间"+sec1+"和"+sec2+"存在交集,无法添加！");
							}
						}else{
							throw new RuntimeException("区间"+sec1+"和"+sec2+"存在交集,无法添加！");
						}
					 }
				//区间(0,200)和>=200存在交集,无法添加！
					if(isExist(param1_2, sec2)){
						if(param1_2-param2_1==0){
							if(mark1_1==10 || mark1_1==20){
								throw new RuntimeException("区间"+sec1+"和"+sec2+"存在交集,无法添加！");
							}
						}else{
							throw new RuntimeException("区间"+sec1+"和"+sec2+"存在交集,无法添加！");
						}
						 
					}
			}
			
		   //(:3 ,[:30, ):4, ]:40
			}else{
				
				if(type2.equals("2")){
				Float param2_1=Float.valueOf((String) se2.get("param1"));
				Float param2_2=Float.valueOf((String) se2.get("param2"));
				Integer mark2_1=Integer.valueOf((String) se2.get("mark1"));
				
				Integer mark1_1=Integer.valueOf((String) se1.get("mark"));
				Float param1_1=Float.valueOf((String) se1.get("param"));	
					if(isExist(param2_1, sec1)){
						System.out.println(mark2_1);
						 if(param2_1-param1_1==0){
							 if(mark2_1==30 &&  mark1_1==10){
								 throw new RuntimeException("区间"+sec1+"和"+sec2+"存在交集,无法添加！");
							 }else{
								 
							 }
						 }else{
							 throw new RuntimeException("区间"+sec1+"和"+sec2+"存在交集,无法添加！");
						 }
						
					}
					if(isExist(param2_2, sec1)){
						 if(param2_1-param1_1==0){
							 if(mark2_1==30 &&  mark1_1==10){
								 throw new RuntimeException("区间"+sec1+"和"+sec2+"存在交集,无法添加！");
							 }else{
								 
							 }
						 }else{
							 throw new RuntimeException("区间"+sec1+"和"+sec2+"存在交集,无法添加！");
						 }
					}
				}else{
					Float param2_1=Float.valueOf((String) se2.get("param"));
					Float param1_1=Float.valueOf((String) se1.get("param"));
					Float mark1_1=Float.valueOf((String) se1.get("mark"));
					Integer mark2_1=Integer.valueOf((String) se2.get("mark"));
					if(isExist(param2_1, sec1)){
						if(param2_1-param1_1==0 ){
							 if((mark2_1==10||mark2_1==20)){
						        throw new RuntimeException("区间"+sec1+"和"+sec2+"存在交集,无法添加！");
							 }
						}else{
							throw new RuntimeException("区间"+sec1+"和"+sec2+"存在交集,无法添加！");
						}
					}
					if(isExist(param1_1, sec2)){
						if(param2_1-param1_1==0 ){
							 if((mark1_1==10||mark1_1==20)){
						        throw new RuntimeException("区间"+sec1+"和"+sec2+"存在交集,无法添加！");
							 }
						}else{
							throw new RuntimeException("区间"+sec1+"和"+sec2+"存在交集,无法添加！");
						}
					}
				}
		}
		
		return false;
	}
	
	public static boolean isExist(float data,String section){
		Map map=getParam(section);
		String type=(String) map.get("type");
		if(type.equals("2")){
			String mark1=(String) map.get("mark1");
			String param1=(String) map.get("param1");
			String mark2=(String) map.get("mark2");
			String param2=(String) map.get("param2");			
		  //(:3 ,[:30, ):4, ]:40
		   boolean a=mark1.equals("30") && data>=Float.valueOf(param1) && mark2.equals("40")&&data<=Float.valueOf(param2)?true:false;  
		  
		   boolean b=mark1.equals("3") && data>Float.valueOf(param1) && mark2.equals("4")&&data<Float.valueOf(param2)?true:false;
		   
		   boolean c=mark1.equals("30") && data>=Float.valueOf(param1) && mark2.equals("4")&&data<Float.valueOf(param2)?true:false;
		   
		   boolean d=mark1.equals("3") && data>Integer.valueOf(param1) && mark2.equals("40")&&data<=Float.valueOf(param2)?true:false;
		   
		   if(a || b || c || d){
			   return true;
		   }else{
			   return false;
		   }
		}
		else{
			//mark:   >:1,>=:10,<:2,<=:20
			String mark=(String) map.get("mark");
			String param=(String) map.get("param");
			boolean a=mark.equals("1") && data>Float.valueOf(param)?true:false;
			boolean b=mark.equals("10") && data>=Float.valueOf(param)?true:false;
			boolean c=mark.equals("2") && data<Float.valueOf(param)?true:false;
			boolean d=mark.equals("20") && data<=Float.valueOf(param)?true:false;
			if(a || b || c || d){
				   return true;
			 }else{
				   return false;
			}
		}
	}
	
	public static Map getParam(String secs){
		Map<String, String>map=new HashMap<String, String>();
		String[]result=secs.split(",");
		Integer type=result.length==1?1:2;
		if(type==1){
			map.put("type", "1");
			if(secs.contains(">")){
				if(secs.contains(">=")){
					map.put("mark","10");
					map.put("param", secs.split(">=")[1]);
					return map;
				}else{
					map.put("mark","1");
					map.put("param", secs.split(">")[1]);
					return map;
				}
			}
			
			if(secs.contains("<")){
				if(secs.contains("<=")){
					map.put("mark","20");
					map.put("param", secs.split("<=")[1]);
					return map;
				}else{
					map.put("mark","2");
					map.put("param", secs.split("<")[1]);
					return map;
				}
			}
		}
	
		if(type==2){
			map.put("type", "2");
			String rs1=result[0];
			String rs2=result[1];
			if(rs1.contains("(")){
				map.put("mark1","3");
				map.put("param1", rs1.split("\\(")[1]);
			}
			if(rs1.contains("[")){
				map.put("mark1","30");
				map.put("param1", rs1.split("\\[")[1]);
			}
			
			if(rs2.contains(")")){
				map.put("mark2","4");
				map.put("param2", rs2.split("\\)")[0]);
			}
			if(rs2.contains("]")){
				map.put("mark2","40");
				map.put("param2", rs2.split("\\]")[0]);
			}		
			
		}
		return map;
	}
	
	public static boolean isExist_easy(String sec1,String sec2){
		Map se1=getParam(sec1);
		Map se2=getParam(sec2);
		String type=(String) se1.get("type");
		String type2=(String) se2.get("type");
		Integer mark1_1=0;
		Integer mark1_2=0;
		Float param1_1=0f;
		Float param1_2=0f;
		
		Integer mark2_1=0;
		Integer mark2_2=0;
		Float param2_1=0f;
		Float param2_2=0f;
		//[]
		if(type.equals("2") && type2.equals("2")){
			mark1_1=Integer.valueOf((String) se1.get("mark1"));
			mark1_2=Integer.valueOf((String) se1.get("mark2"));
			param1_1=Float.valueOf((String) se1.get("param1"));
			param1_2=Float.valueOf((String) se1.get("param2"));	
		//[]
			mark2_1=Integer.valueOf((String) se2.get("mark1"));
			mark2_2=Integer.valueOf((String) se2.get("mark2"));
			param2_1=Float.valueOf((String) se2.get("param1"));
			param2_2=Float.valueOf((String) se2.get("param2"));	
		}
			
		
		//[],[]
		if(type.equals("2") && type2.equals("2")){
		   if(isExist(param2_1, sec1) && mark2_1==30){
                throw new RuntimeException(sec1+":"+sec2+"存在交集");
		   }
		}
		return false;
	}

	/**
	 * Title: judgeInterval
	 * Description: 
	 * @param interval				区间数学表达式	例 (0,1)  从小到大排列
	 * @param judgeNumerical		判断数值
	 * @return						-1.左趋向,0.无趋向1.右趋向
	 * @author jindong.lin
	 * @date 2017年9月6日
	 */
	public static int judgeInterval(String interval, BigDecimal judgeNumerical) {
		
		String[] sections = interval.split(",");
		int left = 0;
		int right = 0;

		//区间判断
		if (sections[0].indexOf("(") > -1) {
			//左开
			left = 0;
		} else if (sections[0].indexOf("[") > -1){
			//左闭
			left = -1;
		}
		
		if (sections[1].indexOf(")") > -1) {
			//右开
			right = 0;
		} else if (sections[1].indexOf("]") > -1) {
			//右闭
			right = -1;
		}
		
		BigDecimal minNumber = new BigDecimal(sections[0].substring(1, sections[0].length()));
		BigDecimal maxNumber = new BigDecimal(sections[1].substring(0, sections[1].length()-1));
		
		BigDecimal temp = null;
		int tempbase = 0;
		if (minNumber.compareTo(maxNumber) > 0) {
			//对调数值
			temp = minNumber;
			minNumber = maxNumber;
			maxNumber = temp;
			//对调开闭符号
			tempbase = left;
			left = right;
			right = left;
		} else if (minNumber.compareTo(maxNumber) == 0) {
			if (left == right && left == 0) {
				throw new RuntimeException("区间表达式错误," + interval);
			}
		}
		
		if (judgeNumerical.compareTo(minNumber) > left
			&&
			maxNumber.compareTo(judgeNumerical) > right) {
			return 0;
		} else if (judgeNumerical.compareTo(minNumber) <= left) {
			return -1;
		} else if (maxNumber.compareTo(judgeNumerical) <= right) {
			return 1;
		}
		return 0;
	}
}
