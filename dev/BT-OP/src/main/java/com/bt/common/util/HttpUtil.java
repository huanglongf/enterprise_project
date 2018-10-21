package com.bt.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;


public class HttpUtil {

	public static String sendHttpRequest(String requestUrl,
			Map<String, Object> param) {
		String paramStr = null;
		if (param != null && param.size() > 0) {
			StringBuilder sb = new StringBuilder();
			int index = 0;
			for (String key : param.keySet()) {
				index++;
				sb.append(key).append("=").append(param.get(key));
				if (index != param.size()) {
					sb.append("&");
				}
			}
			paramStr = sb.toString();
			System.out.println(paramStr);
		}

		HttpURLConnection connection = null;
		OutputStreamWriter out = null;
		BufferedInputStream in = null;
		BufferedReader reader = null;
		try {
			URL url = new URL(requestUrl);
			System.out.println(url);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Accept-Charset", "utf-8");
			connection.setRequestProperty("contentType", "utf-8");
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setConnectTimeout(50000);
			connection.setReadTimeout(50000);

			out = new OutputStreamWriter(connection.getOutputStream());
			out.write(paramStr);
			out.flush();

			in = new BufferedInputStream(connection.getInputStream());
			reader = new BufferedReader(new InputStreamReader(in,"utf-8"));

			String s = null;
			StringBuilder rspBuilder = new StringBuilder();
			while (null != (s = reader.readLine())) {
				rspBuilder.append(s);
			}
			return rspBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				connection.disconnect();
			}
		}

	}
	
	
	public static String sendHttpRequestrfd(String requestUrl,
			Map<String, Object> param, String data) {
		String paramStr = null;
		if (param != null && param.size() > 0) {
			StringBuilder sb = new StringBuilder();
			int index = 0;
			for (String key : param.keySet()) {
				index++;
				sb.append(key).append("=").append(param.get(key));
				if (index != param.size()) {
					sb.append("&");
				}
			}
			paramStr = sb.toString();
			System.out.println(paramStr);
		}
		String url = requestUrl+"?"+paramStr;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		        headers.setContentType(type);
		        headers.add("Accept", MediaType.APPLICATION_JSON.toString());        
		        HttpEntity<String> formEntity = new HttpEntity<String>(data, headers);
		        System.out.println(formEntity.toString());
		String result = restTemplate.postForObject(url, formEntity, String.class);
		System.out.println("result: " + result);
		return result;
		
	}
}
