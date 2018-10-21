package com.bt.lmis.api.service;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InsToWmsApi {

    public  void test(){
        List list1= Lists.newArrayList();
        String  url = "http://10.88.26.50:8081/bt-ins/insToWms/sendData.do";
        List<NameValuePair> nvps = Lists.newArrayList();
        nvps.add(new BasicNameValuePair("contractCode","BZ1001"));
        nvps.add(new BasicNameValuePair("systemKey","Lzdc0328"));
        nvps.add(new BasicNameValuePair("data", JSONArray.toJSONString(list1)));
        Map<String,String> result=sendPost2(url,nvps);

    }
    /**
     * 接口表导入 转换正是表接口调用
     * */
    public static Map<String, String> sendPost2(String url, List<NameValuePair> nvps) {
        String result = null;
        Map<String, String> res = new HashMap<String, String>();
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

//            HttpEntity entity = new StringEntity(body, "utf-8");
//            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                String resEntityStr = EntityUtils.toString(response.getEntity());
                result = resEntityStr;
                res.put("linkFlag", "1");
                res.put("linkInfo", result);
            } else {
                res.put("linkFlag", "0");
                res.put("linkInfo", "接口连接失败,错误码:" + response.getStatusLine().getStatusCode() + "|地址:" + url);
            }
        } catch (Exception e) {

        }
        return  res;
    }

}
