/**
 * 
 */
package com.bt.vims.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 *@author jinggong.li
 *@date 2018年6月22日  
 */
public class HttpConfigUtil {
	   
	public static void main(String[] args) {
		  String result =   getURLContent("http://10.8.36.37:8088/easyTask/test/getList?im="+12335025);
		  List<Map<String,Object>> list = (List<Map<String, Object>>) JSON.parse(result);
	}
	/**
	 * 
	 * @param urlStr 地址
	 * @return  返回数据
	 */
    public static String getURLContent(String urlStr) {  
        URL url = null;  
        HttpURLConnection httpConn = null;
        BufferedReader in = null;  
        StringBuffer sb = new StringBuffer();  
        try {  
            url = new URL(urlStr);  
            in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));  
            String str = null;  
            while ((str = in.readLine()) != null) {  
                sb.append(str);  
            }  
            return sb.toString();
        } catch (Exception ex) {  
  
        } finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
            }  
        }  
        return null;  
    }    
}
