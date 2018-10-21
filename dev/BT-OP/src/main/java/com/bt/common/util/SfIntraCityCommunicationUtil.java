package com.bt.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.codec.binary.Base64;

import com.bt.orderPlatform.model.sfIntraCity.IntraCityCreateorder;
import com.bt.orderPlatform.model.sfIntraCity.element.OrderDetail;
import com.bt.orderPlatform.model.sfIntraCity.element.ProductDetail;
import com.bt.orderPlatform.model.sfIntraCity.element.Receive;
import com.bt.orderPlatform.model.sfIntraCity.element.Shop;
import com.fasterxml.jackson.core.JsonProcessingException;

public class SfIntraCityCommunicationUtil{
    
    /**
     * 模拟post请求
     * 
     * @param post_data
     * @throws IOException
     */
    public static String request(String urlString ,String post_data,String sign) throws IOException{
        String url_with_sign = String.format("%s?sign=%s", urlString, sign);
        URL url = new URL(url_with_sign);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(post_data.getBytes());
        outputStream.flush();

        if (connection.getResponseCode() != 200){
            throw new RuntimeException("Failed : HTTP errorcode : " + connection.getResponseCode());
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String ret = null;
        StringBuilder builder = new StringBuilder();
        while ((ret = bufferedReader.readLine()) != null){
            builder.append(ret);   
        }
        connection.disconnect();
        return builder.toString();
    }

    /**
     * 生成sign值
     * 生成map再拼接
     * 
     * @param post_data
     * @param app_id
     * @param app_key
     * @return
     */

    public static String generateSign(String post_data,Integer app_id,String app_key){
        String ret = "";
        try{
            StringBuilder sb = new StringBuilder();
            sb.append(post_data);
            sb.append("&" + app_id + "&" + app_key);

            //generator 32 byte md5 
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5 = md.digest(sb.toString().getBytes("utf-8"));
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < md5.length; offset++){

                i = md5[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //base64 
            ret = Base64.encodeBase64String(buf.toString().getBytes("utf-8"));

        }catch (NoSuchAlgorithmException ex){
            ex.printStackTrace();

        }catch (UnsupportedEncodingException ex){
            ex.printStackTrace();
        }
        return ret;
    }

    /**
     * 将PostData对象转换成TreeMap，将属性按字母升序排列，剔除sign
     * 属性
     * 
     * @param obj
     * @return
     */
    public static Map<String, Object> transPostData2TreeMap(Object obj){
        if (obj == null){
            return null;
        }
        Map<String, Object> map = new TreeMap<String, Object>();
        try{
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors){
                String key = property.getName();
                // 过滤class属性和sign属性
                if (!key.equals("class") && !key.equals("sign") && !key.equals("order_detail") && !key.equals("Shop") && !key.equals("invoice") && !key.equals("receivce")){

                    // 得到property对应的getter⽅方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    if (value != null){
                        map.put(key, value);
                    }
                }
            }

            return map;
        }catch (Exception e){
            System.out.println("transBean2Map Error " + e);
            return null;
        }
    }
    


}
