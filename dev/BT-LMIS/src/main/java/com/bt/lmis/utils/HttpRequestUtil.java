package com.bt.lmis.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;




public class HttpRequestUtil {
    public  static ConcurrentMap<String,String> sendPost(ConcurrentMap<String,String> post_param){  
        String result = null;  
        CloseableHttpResponse response = null;		
        ConcurrentMap<String,String> res=new ConcurrentHashMap<String,String>();
        try{  
        	CloseableHttpClient httpClient = HttpClients.createDefault();
        	HttpPost post=new HttpPost(post_param.get("url"));
        	RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(300000).setConnectTimeout(300000).setSocketTimeout(300000).build();
        	HttpEntity pentity = new StringEntity(post_param.get("body_param"),"utf-8");
            post.setEntity(pentity);
            post.setConfig(config);
            response = httpClient.execute(post);
            if(response != null && response.getStatusLine().getStatusCode()==200){  
              HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                  HttpEntity entity = response.getEntity();
                  result = EntityUtils.toString(entity);
                  res.put("linkFlag", "1");
                  res.put("linkInfo",result);
                  EntityUtils.consume(entity);
               }  
            }else{
                res.put("linkFlag", "0");
                res.put("linkInfo","接口连接失败,错误码:"+response.getStatusLine().getStatusCode()+"|地址:"+post_param.get("url"));
            }  
        }catch(Exception ex){  
            res.put("linkFlag", "0");
            res.put("linkInfo",ex.getMessage());
            ex.printStackTrace();  
        }  
//        System.out.println("获取时间:开始"+star +"---"+"结束:"+DateUtil.getData()+"共用时:"+DateUtil.time(star,DateUtil.getData()));
        return res;  
    }  
    
	public static Map<String,String> sendPost2(String url, List<NameValuePair> nvps){
    	String result=null;
    	Map<String,String> res=new HashMap<String,String>();
    	try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url);
            
            post.setEntity(new UrlEncodedFormEntity(nvps));
            
//            HttpEntity entity = new StringEntity(body, "utf-8");
//            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
              String resEntityStr = EntityUtils.toString(response.getEntity());
              result=new String(resEntityStr.getBytes("iso-8859-1"), "utf-8");
              res.put("linkFlag", "1");
              res.put("linkInfo",result);
            }else{
              res.put("linkFlag", "0");
              res.put("linkInfo","接口连接失败,错误码:"+response.getStatusLine().getStatusCode()+"|地址:"+url);
            }		
		} catch (Exception e) {
            res.put("linkFlag", "0");
            res.put("linkInfo",e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
		}
    	System.out.println(res);
            return res;
    }
	
	
	
	
}