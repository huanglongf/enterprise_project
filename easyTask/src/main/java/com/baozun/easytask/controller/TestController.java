package com.baozun.easytask.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baozun.easytask.service.SkxService;
import com.baozun.easytask.util.BigExcelExport;
import com.baozun.easytask.util.DateUtil;
import com.baozun.easytask.util.ExportExcelUtils;
import com.baozun.easytask.util.MailUtils;
import com.baozun.easytask.util.SendExcelMailUtil;

@Controller
@RequestMapping("/test")
public class TestController {
	private final static String  path = "/home/tomcat_lmis_pe/order/";//文件产生的地址
	private final static String email = "jinggong.li@baozun.com";//收件人邮箱
	private final static String cc = "jinggong.li@baozun.com";//抄送人邮箱
	
	@Resource(name = "skxServiceImpl")
	private SkxService skxServiceImpl;
	
	@RequestMapping("/getList")
	@ResponseBody
	public String getList(@RequestParam String im) {
		List<Map<String,Object>> list = null ;//skxServiceImpl.job11();///获取到数据源数据
		System.out.println("im="+im);
		return JSON.toJSONString(list);
	}
	
	
	
	@RequestMapping("/test.do")
    @ResponseBody
	public Boolean wmsSearch(HttpServletRequest request,HttpServletResponse res) {
		boolean flage = false;
		String fileUrl = null;
		List<String> filepath = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	    String filehead = sdf.format(new Date());
		List<List<List<Object>>> list = getExeculList();//获取到数据源数据
        System.out.println(list);
		try {
			fileUrl = ExportExcelUtils.exportExcel(list,path+"618-订单发货配送报表(斯凯奇)");
			filepath.add(fileUrl);
			flage = SendExcelMailUtil.sendMail(email,cc,"618-订单发货配送报表(斯凯奇)"+"--"+filehead,"测试邮件", filepath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flage;	
	}
    
	/**
	 * 获取到List数据源
	 * @return
	 */
	private List<List<List<Object>>> getExeculList(){
		List<List<List<Object>>> list = new LinkedList<List<List<Object>>>();
		List<List<Object>> data1 = mapChangList(skxServiceImpl.job7());
		List<List<Object>> data2 = mapChangList2(skxServiceImpl.job8());
		List<List<Object>> data3 = mapChangList3(skxServiceImpl.job9());
        list.add(data1);
        list.add(data2);
        list.add(data3);
		return list;	
	}

	
	//转换map1中的数据
	private List<List<Object>> mapChangList(List<Map<String, Object>> data){
		List<List<Object>> data1 = new ArrayList<List<Object>>(); 
		for(Map map : data) {
			List rowData = new ArrayList();  
            rowData.add(map.get("start_province") != null ? map.get("start_province"): "");  
            rowData.add(map.get("warehouse_name") != null ? map.get("warehouse_name"): map.get("warehouse_code"));
            rowData.add(map.get("store_name") != null ? map.get("store_name"): map.get("store_code"));
            rowData.add(map.get("transport_code") != null ? map.get("transport_code"): "");
            rowData.add(map.get("order_count") != null ? map.get("order_count"): "");
            rowData.add(map.get("embrance_count") != null ? map.get("embrance_count"): "");
            rowData.add(map.get("receive_count") != null ? map.get("receive_count"): "");
            data1.add(rowData);
		}
		return data1;
	}
	
	//转换map2中的数据
	private  List<List<Object>> mapChangList2(List<Map<String, Object>> data){
		List<List<Object>> data2 = new ArrayList<List<Object>>(); 
		for(Map map : data) {
			List rowData = new ArrayList();  
            rowData.add(map.get("start_province") != null ? map.get("start_province"): "");  
            rowData.add(map.get("warehouse_name") != null ? map.get("warehouse_name"): map.get("warehouse_code"));
            rowData.add(map.get("store_name") != null ? map.get("store_name"): map.get("store_code"));
            rowData.add(map.get("transport_code") != null ? map.get("transport_code"): "");
            String provinces = (String) (map.get("province") != null ? map.get("province"): "");
            if(null != provinces) {
                 String province = provinces.substring(0,2);
                 if(province.equals("内蒙")) {
                	 province = province + "古";
                	 rowData.add(province);
                 }else {
                	 rowData.add(province); 
                 }     
            }else {
            	rowData.add(provinces);
            }
            String city = (String) (map.get("city") != null ? map.get("city"): "");
            if(null != city) {
            	switch(city) {
            	case "北京":
                     city = city + "市";
                    break;
            	case "上海":
            		city = city + "市";
                    break;
            	case "重庆":
            		city = city + "市";
                    break;
            	case "天津":
            		city = city + "市";
                    break;
            	}
            	rowData.add(city);
            }else {
            	rowData.add(city);
            }
            rowData.add(map.get("order_count") != null ? map.get("order_count"): "");
            rowData.add(map.get("embrance_count") != null ? map.get("embrance_count"): "");
            rowData.add(map.get("receive_count") != null ? map.get("receive_count"): "");
            data2.add(rowData);
		}
		return data2;
	}
	
	//转换map3中的数据
	private  synchronized List<List<Object>> mapChangList3(List<Map<String, Object>> data){
		List<List<Object>> data3 = new ArrayList<List<Object>>(); 
		for(Map map : data) {
			List rowData = new ArrayList();  
            rowData.add(map.get("transport_time") != null ? map.get("transport_time"): "");  
            rowData.add(map.get("transport_hour") != null ? map.get("transport_hour"): "");
            rowData.add(map.get("start_province") != null ? map.get("start_province"): "");
            rowData.add(map.get("warehouse_name") != null ? map.get("warehouse_name"): map.get("warehouse_code"));
            rowData.add(map.get("store_name") != null ? map.get("store_name"): map.get("store_code"));
            rowData.add(map.get("transport_code") != null ? map.get("transport_code"): "");
            rowData.add(map.get("order_count") != null ? map.get("order_count"): "");
            rowData.add(map.get("embrance_count") != null ? map.get("embrance_count"): "");
            data3.add(rowData);
		}
		return data3;
	}
	
	/*public static void main(String[] args) { 
		    String uuid = UUID.randomUUID().toString();
		    String path = "D:\\";
		    String fileUrl = null;
			List<List<Object>> data1 = new ArrayList<List<Object>>();  
			for (int i = 1; i < 1000; i++) {  
			    List rowData = new ArrayList();  
			    rowData.add(String.valueOf(i));  
			    rowData.add("宝尊集团0");
			    rowData.add("宝尊集团0");
			    rowData.add("宝尊集团0");
			    rowData.add("宝尊集团0");
			    data1.add(rowData);  
			}
		List<List<Object>> data2 = new ArrayList<List<Object>>(); 
		for (int i = 1; i < 1000; i++) {  
		    List rowData = new ArrayList();  
		    rowData.add(String.valueOf(i));  
		    rowData.add("宝尊集团1");
		    rowData.add("宝尊集团1");
		    rowData.add("宝尊集团1");
		    rowData.add("宝尊集团1");
		    rowData.add("宝尊集团1");
		    rowData.add("宝尊集团1");
		    data2.add(rowData);  
		}
		List<List<Object>> data3 = new ArrayList<List<Object>>();
		for (int i = 1; i < 1000; i++) {  
		    List rowData = new ArrayList();  
		    rowData.add(String.valueOf(i));  
		    rowData.add("宝尊集团2"); 
		    rowData.add("宝尊集团2");
		    rowData.add(null != null ? "2":"");
		    rowData.add("宝尊集团2");
		    rowData.add("宝尊集团2");
		    rowData.add("宝尊集团2");
		    rowData.add("宝尊集团2");
		    data3.add(rowData);  
		}
		
		List<List<List<Object>>> list = new LinkedList<List<List<Object>>>();
		list.add(data1);
		list.add(data2);
		list.add(data3);
		System.out.println(list.size());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String filehead = sdf.format(new Date());
		try {
			fileUrl = exportExcel(list,path+filehead+"订单发货配送报表");
			} catch (Exception e) {
				e.printStackTrace();
			}
		    System.out.println(fileUrl);
} */
 }
