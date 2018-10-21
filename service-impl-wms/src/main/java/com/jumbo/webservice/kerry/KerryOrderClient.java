package com.jumbo.webservice.kerry;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.util.HttpClientUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.util.zip.AppSecretUtil;

public class KerryOrderClient {
    
    protected static final Logger log = LoggerFactory.getLogger(KerryOrderClient.class);
    
    public static String sendOrderToKerry(KerryOrderRequest request, String url, String clientId, String secret) {
        String reqJson = JsonUtil.obj2jsonStrIncludeAll(request);
        log.info("sendOrderToKerry send=>" + reqJson);
        String resultJson = HttpClientUtil.doPostWithJson(url, reqJson, getParams(reqJson, clientId, secret));
        log.info("sendOrderToKerry return :" + resultJson);
        return resultJson;
    }
    
    public static String sendCancelToKerry(KerryOrderCancelRequest request, String url, String clientId, String secret){
        String reqJson = JsonUtil.obj2jsonStrIncludeAll(request);
        log.info("sendOrderToKerry send=>" + reqJson);
        String resultJson = HttpClientUtil.doPostWithJson(url, reqJson, getParams(reqJson, clientId, secret));
        log.info("sendCancelToKerry return :" + resultJson);
        return resultJson;
    }
    
    // 获取签名和参数
    private static Map<String, String> getParams(String reqJson, String clientId, String secret){
        String signStr = "client_id=" + clientId + "&data=" + reqJson + "&secret=" + secret;
        String sign = AppSecretUtil.getMD5Str(signStr).toUpperCase();
        Map<String, String> params = new HashMap<String, String>();
        params.put("sign", sign);
        params.put("client_id", clientId);
        return params;
    }
    
}
