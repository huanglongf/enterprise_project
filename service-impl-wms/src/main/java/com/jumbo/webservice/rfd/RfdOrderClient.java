package com.jumbo.webservice.rfd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.baozun.bh.util.JSONUtil;

import com.jumbo.util.HttpClientUtil;
import com.jumbo.util.zip.AppSecretUtil;

public class RfdOrderClient {
	
	protected static final Logger log = LoggerFactory.getLogger(RfdOrderClient.class);
	
	/**
	 * 向如风达发起请求获取运单号
	 * @param order 订单
	 * @param url	路径
	 * @param identity 商家标识
	 * @param secret   密钥	
	 * @return
	 */
	public static String sendOrderToRfd(List<RfdCreateOrderRequest> orders, String url, String identity, String secret) {
		String reqJson = JSONUtil.beanToJson(orders);
		log.info("sendOrderToRfd send=>" + reqJson);
		url += "?identity=" + identity + "&token=" + getToken(reqJson, secret);
		String resultJson = HttpClientUtil.doPostWithJson(url, reqJson, null, 30000, 30000);
		log.info("sendOrderToRfd return :" + resultJson);
		return resultJson;
	}
	
	public static String sendRfdConfirmOrder(List<RfdOutStoreRequest> orders, String url, String identity, String secret) {
		String reqJson = JSONUtil.beanToJson(orders);
		log.info("sendOrderToRfd send=>" + reqJson);
		url += "?identity=" + identity + "&token=" + getToken(reqJson, secret);
        String resultJson = HttpClientUtil.doPostWithJson(url, reqJson, null, 30000, 30000);
        log.info("sendOrderToRfd return :" + resultJson);
		return resultJson;
	}
	
	private static String getToken(String reqJson, String secret){
		String signStr = reqJson + "|" + secret;
        String token = AppSecretUtil.getMD5Str(signStr);
		return token;
	}
	
}
