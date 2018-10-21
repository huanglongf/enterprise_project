package com.bt.interf.rfd;

import java.security.MessageDigest;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.bt.common.util.MD5Util;

/**
 * @Description:
 * @author  Hanery:
 * @date 2017年7月6日 下午2:55:25
 */
public class Testone {


	@Test
	public void testUrl() throws Exception {
//		String jsonParam = "[{\"MerchantCode\": \"MerchantCode\",\"FormCode\": \"11111155\",\"PerFormCode\": \"11111156\",\"TotalAmount\": 100,"
//				+ "\"PaidAmount\": 100,\"ReceiveAmount\": 100,\"RefundAmount\": 0,\"InsureAmount\": 0, \"Weight\": 1.123, "
//				+ "\"FromName\": \"测试人\",\"FromAddress\": \"荷花池\", \"FromProvince\": \"四川\",\"FromCity\": \"成都市\","
//				+ "\"FromArea\": \"金牛区\",\"FromPostCode\": \"610000\",\"FromMobile\": null, \"FromPhone\": \"13568001000\", \"CashType\": \"现金\","
//				+ "\"ToName\": \"收件人姓名\",\"ToAddress\": \"华西坝\", \"ToProvince\": \"四川\",\"ToCity\": \"成都市\",\"ToArea\": \"武侯区\","
//				+ "\"ToPostCode\": \"10011\",\"ToMobile\": \"13586552111\",\"ToPhone\": null,\"ToEmail\": null,\"Comment\": \"\",\"ExtendData\": null,"
//				+ "\"BoxCount\": 0,\"GoodsProperty\": \"0\",\"GoodsCategory\": \"分类\", \"WaybillNo\": 0,\"OrderType\": 0,\"DeliverCode\": null,"
//				+ "\"CustomerLevel\": null,\"DistributionCode\": \"rfd\",\"CurrentDistributioncode\": \"\",\"SortingCenterId\": \"\",\"SortingCenterName\": \"\",\"WareHouseId\": \"\",\"WarehouseName\": null,\"OrderDetails\": ["
//				+ "{\"ProductName\": \"商品名称\",\"ProductCode\": \"1008611\",\"Count\": 10,\"Unit\": \"个\",\"SellPrice\": 12.91,\"Size\": \"M\"}]}]";
//
//		String key="935ee7f6-cc05-402b-8329-fe92b41cc62c";
//		String str = jsonParam + "|" + key;
//		System.out.println("jsonParam:" + jsonParam);
//
//
//		String md5Str = md5Encode(str);
//		String url = "http://editest.wuliusys.com/api/order/import?identity=0ba3ca4b-6897-489e-8b31-2738cc692030&token="+md5Str;
//
//		RestTemplate restTemplate = new RestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//		headers.setContentType(type);
//		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//		HttpEntity<String> formEntity = new HttpEntity<String>(jsonParam, headers);
//		System.out.println(formEntity);
//		String result = restTemplate.postForObject(url, formEntity, String.class);
//		System.out.println("result: " + result);
	}

	public static String md5Encode(String inStr) throws Exception {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}

		byte[] byteArray = inStr.getBytes("UTF-8");
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
}
