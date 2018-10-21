package com.bt.lmis.code;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.alibaba.fastjson.JSONObject;
import com.bt.common.controller.param.Parameter;
import com.bt.lmis.model.Employee;
import com.bt.utils.BaseConst;
import com.bt.utils.SessionUtils;
/**
* @ClassName: BaseController
* @Description: TODO(控制器基类)
* @author Yuriy.Jiang
* @date 2016年5月22日 上午9:21:33
*
*/ 
@Controller
public class BaseController {

	
	/**
	 * @Title: generateParameter
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @return: Parameter
	 * @author: Ian.Huang
	 * @date: 2017年7月28日 下午3:25:38
	 */
	protected Parameter generateParameter(HttpServletRequest request) {
		return new Parameter(SessionUtils.getEMP(request),request2Map(request));
	}
	
	private Map<String, Object> request2Map(HttpServletRequest request) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(Object key:request.getParameterMap().keySet()) {
			if(key.toString().contains("[]")) {
				map.put((String)key, request.getParameterValues(key.toString()));
				
			} else {
				map.put((String)key, request.getParameter(key.toString()).trim());
				
			}

		}
		return map;

	}
	/**
	  * 获取IP地址
	  * @param request
	  * @return
	  */
	 public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	 
	/** 
	* @Title: StringtoInt 
	* @Description: TODO(String[]转Integer[]) 
	* @param @param str
	* @param @return    设定文件 
	* @return Integer[]    返回类型 
	* @throws 
	*/
	public Integer[] StringtoInt(String str) {  
		Integer ret[] = new Integer[str.length()];   
		StringTokenizer toKenizer = new StringTokenizer(str, ",");   
	    int i = 0;  
	    while (toKenizer.hasMoreElements()) { 
	    	ret[i++] = Integer.valueOf(toKenizer.nextToken()); 
	    }   
    	return ret;  
	 }
	
	public boolean isEmpty(String args){
		if(args==null || args==""){
			return true;
		}
		if(args!=null && args.equals("")){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	* @Title: getParamMap 
	* @Description: 获取前台页面传入的参数，from表单和pagination参数等等
	* @param @param request
	* @param @return
	* @return HashMap
	* @throws
	 */
	public Map<String,Object> getParamMap(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String, String[]> maps = request.getParameterMap();
		Employee employee = SessionUtils.getEMP(request);
		for (Object key : maps.keySet()) {
			map.put((String)key, request.getParameter(key.toString()).trim());
			System.out.println(key+":"+request.getParameter(key.toString()).trim());
		}
		String startRow=(String)(isEmpty(request.getParameter("startRow"))?"0":request.getParameter("startRow"));
		String pageSize=(String)(isEmpty(request.getParameter("pageSize"))?BaseConst.pageSize+"":request.getParameter("pageSize"));
		String pageIndex=(String)(isEmpty(request.getParameter("pageIndex"))?"1":request.getParameter("pageIndex"));
		map.put("startRow", Integer.parseInt(startRow));
		map.put("pageSize", Integer.parseInt(pageSize));
		map.put("pageIndex", Integer.parseInt(pageIndex));
		map.put("operate_user", employee.getId());
		return map;
		
	}
	
	public Map<String,String>spiltDateString(String param){
		Map<String, String> map=new HashMap<String,String>();
		if(isEmpty(param)){
			return map;
		}
        String date[]=param.split(" - ");
        if(date.length<2){
        	return null;
        }
		map.put("startDate",date[0]);
		map.put("endDate",date[1]);
		return map;
	}
	
	/**
	 * 
	* @Title: spiltDateString 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param param
	* @param @return    设定文件 
	* @author likun   
	* @return Map<String,String>    返回类型 
	* @throws
	 */
	public Map<String,String>spiltDateString_jt(String param){
		Map<String, String> map=new HashMap<String,String>();
		if(isEmpty(param)){
			return map;
		}
        String date[]=param.split("/");
        if(date.length<2){
        	return null;
        }
		map.put("startDate",date[0]);
		map.put("endDate",date[1]);
		return map;
	}
	
	/** 
	* @Title: returnFunction 
	* @Description: TODO(根据flag判断返回参数) 
	* @param @param msg
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public String returnFunction(Integer msg){
		//返回信息 400系统异常 201入参异常 360成功
		JSONObject json = new JSONObject();
		if(msg==400){
			json.put("status", "n");
			json.put("info", "系统异常,请联系管理员!");
		}else if(msg==401){
			json.put("status", "n");
			json.put("info", "没有数据!");
		}else if(msg==201){
			json.put("status", "n");
			json.put("info", "输入参数错误，请确认输入参数是否合法!");
		}else if(msg==360){
			json.put("status", "y");
			json.put("info", "操作成功!");
		}
		return json.toString();
	}
		
	/** 
	* @Title: getSection 
	* @Description: TODO(获取区间参数) 
	* @param @param mark1	条件1
	* @param @param mark2	条件2
	* @param @param mark3	条件3
	* @param @param mark4	参数1
	* @param @param mark5	参数2
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public String getSection(String mark1,String mark2,String mark3,String mark4,String mark5){
		String result = null;
		if((null==mark4 || isNumber(mark4)) && (null==mark5 || isNumber(mark5))){
			BigDecimal a1 = new BigDecimal(mark4);
			BigDecimal a2 = new BigDecimal(mark5);
			if (a2.compareTo(a1)>=0) {
				if(null==mark4){
					return result=mark3+mark5;
				}else if (null==mark5) {
					return result=mark1+mark4;
				}
				if(mark1.equals(">=") || mark1.equals("<=")){
					result="["+mark4+",";
				}else{
					result="("+mark4+",";
				}
				if(mark3.equals(">=") || mark3.equals("<=")){
					result+=mark5+"]";
				}else{
					result+=mark5+")";
				}
			}
		}
		return result;
	}
	/**
	 * 判断字符串是否是整数
	 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		}catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是浮点数
	 */
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			if (value.contains(".")){
				return true;
			}
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是数字
	 */
	public static boolean isNumber(String value) {
		return isInteger(value) || isDouble(value);
	}

	public static Map<Integer, String> readExcels(String url){
		Map<Integer, String> map = new HashMap<Integer, String>();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
	    try {  
			File excelFile = new File(url);							//创建文件对象
			FileInputStream is = new FileInputStream(excelFile);	//文件流
			Workbook workbook = WorkbookFactory.create(is); 		//这种方式 Excel 2003/2007/2010 都是可以处理的
			int sheetCount = workbook.getNumberOfSheets(); 			//Sheet的数量
			//遍历每个Sheet
			for (int s = 0; s < sheetCount; s++) {
				Sheet sheet = workbook.getSheetAt(s);  
	            int rowCount = sheet.getPhysicalNumberOfRows(); //获取总行数  
	            //遍历每一行  
	            for (int r = 1; r < rowCount; r++) { 
	                Row row = sheet.getRow(r);  
	                int cellCount = row.getPhysicalNumberOfCells(); //获取总列数  
	                //遍历每一列
	                String cellValues = "";
	                for (int c = 0; c < cellCount; c++) {  
	                    Cell cell = row.getCell(c);  
	                    int cellType = cell.getCellType();  
	                    String cellValue = null;  
	                    switch(cellType) {  
	                        case Cell.CELL_TYPE_STRING: //文本  
	                            cellValue = cell.getStringCellValue();  
	                            break;  
	                        case Cell.CELL_TYPE_NUMERIC: //数字、日期  
	                            if(DateUtil.isCellDateFormatted(cell)) {  
	                                cellValue = fmt.format(cell.getDateCellValue()); //日期型  
	                            }  
	                            else {  
	                                cellValue = String.valueOf(cell.getNumericCellValue()); //数字  
	                            }  
	                            break;  
	                        case Cell.CELL_TYPE_BOOLEAN: //布尔型  
	                            cellValue = String.valueOf(cell.getBooleanCellValue());  
	                            break;  
	                        case Cell.CELL_TYPE_BLANK: //空白  
	                            cellValue = cell.getStringCellValue();  
	                            break;  
	                        case Cell.CELL_TYPE_ERROR: //错误  
	                            cellValue = "错误";  
	                            break;  
	                        case Cell.CELL_TYPE_FORMULA: //公式  
	                            cellValue = "错误";  
	                            break;  
	                        default:  
	                            cellValue = "错误";  
	                    }
	                    cellValues += cellValue + "    ";
	                }  
                    map.put(r-1, cellValues);
	            }
			}
	    }catch (Exception e) {  
	        e.printStackTrace();  
	    }
	    return map;
	}
	
	@InitBinder    
    public void initBinder(WebDataBinder binder) {    

        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"), true)); 

    }
}
