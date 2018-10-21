package com.bt.baidumap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;


/** 
* @ClassName: AddressQuery 
* @Description: TODO(地址查询) 
* @author Yuriy.Jiang
* @date 2017年7月30日 下午3:48:04 
*  
*/
public class AddressQuery {

	public final static String url = "http://api.map.baidu.com/geocoder/v2/";
	public final static String output = "json";
	public final static String ak = "XvEgZyfNq3uNPBPp5QbsWW1M6x7hfaac";
	public final static String callback = "showLocation";
	
	/** 
	* @Title: sendGet 
	* @Description: TODO(get请求)
	* @param @param url
	* @param @param param
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	/** 
	* @Title: getLatitudeAndLongitude 
	* @Description: TODO(1.详细地址转换经纬度) 
	* @param @param address
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	public final static Map<String, Object> getLatitudeAndLongitude(String address){
		Map<String, Object> map = null;
		try {
			if(null!=address && !address.equals("")){
				String get_1 =AddressQuery.sendGet(url,"address="+address.replace(" ", "")+"&output="+output+"&ak="+ak+"&callback=");
				JSONObject jsStr = JSONObject.parseObject(getUTF8XMLString(get_1));
				if(null!=jsStr.get("result") && jsStr.get("status").toString().equals("0")){
					JSONObject result = JSONObject.parseObject(jsStr.get("result").toString());
					JSONObject location = JSONObject.parseObject(result.get("location").toString());
					String lat = location.get("lat").toString();
					String lng = location.get("lng").toString();
					map = new HashMap<>();
					map.put("code", "200");
					map.put("msg", "成功!");
					map.put("lat", lat);
					map.put("lng", lng);
				}else{
		        	map = new HashMap<>();
					map.put("code", "300");
					map.put("msg", "API调用异常!");
		        }
			}else{
				map = new HashMap<>();
				map.put("code", "404");
				map.put("msg", "地址为空!");
			}
		} catch (Exception e) {
			map = new HashMap<>();
			map.put("code", "500");
			map.put("msg", "系统异常!");
		}
		return map;
	}
	
	/** 
	* @Title: getAddress 
	* @Description: TODO(2.经纬度转换详细地址) 
	* @param @param latitude 纬度
	* @param @param longitude 经度
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	public static Map<String, Object> getAddress(String latitude,String longitude){
		Map<String, Object> map = null;
		try {
			if(null!=latitude && !latitude.equals("") && null!=longitude && !longitude.equals("")){
		        String get_2=AddressQuery.sendGet(url,"callback=&location="+latitude+","+longitude+"&output=json&pois=1&ak="+ak);
		        
		        JSONObject jsStr = JSONObject.parseObject(getUTF8XMLString(get_2));
		        if(null!=jsStr.get("result") && jsStr.get("status").toString().equals("0")){
					JSONObject result = JSONObject.parseObject(jsStr.get("result").toString());
					JSONObject addressComponent = JSONObject.parseObject(result.get("addressComponent").toString());
					String province = addressComponent.get("province").toString();
					String city = addressComponent.get("city").toString();
					String district = addressComponent.get("district").toString();
					map = new HashMap<>();
					map.put("code", "200");
					map.put("msg", "成功!");
					map.put("province", province);
					map.put("city", city);
					map.put("district", district);
		        }else{
		        	map = new HashMap<>();
					map.put("code", "300");
					map.put("msg", "API调用异常!");
		        }
			}else{
				map = new HashMap<>();
				map.put("code", "404");
				map.put("msg", "地址为空!");
			}
		} catch (Exception e) {
			map = new HashMap<>();
			map.put("code", "500");
			map.put("msg", "系统异常!");
		}
		return map;
	}
	
	public static void main(String[] args) {
		Map<String, Object> map = getLatitudeAndLongitude("云南 大理州 大理市 下关镇北区机电市场正大门A1-5-6号思地管业");
		Map<String, Object> map2 = getAddress(map.get("lat").toString(), map.get("lng").toString());
		System.out.println(map2.get("code")+"省:"+map2.get("province").toString()+" 市:"+map2.get("city").toString()+" 区:"+map2.get("district").toString());
	}
	
	public static String getUTF8XMLString(String xml) {  
	    try {  
	    	return new String(xml.getBytes("utf-8"),"utf-8");
	    } catch (UnsupportedEncodingException e) {  
		    e.printStackTrace();  
	    }  
	    return null;  
	}  
	
}
