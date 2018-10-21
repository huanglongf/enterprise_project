package com.jumbo.lf;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.net.URL;
import java.net.URLConnection;

import org.apache.xmlbeans.impl.tool.XSTCTester.TestCase;
import org.junit.Test;

import java.util.Date;

import com.jumbo.util.zip.AppSecretUtil;

import java.io.InputStreamReader;
import java.io.IOException;


public class LfTest extends TestCase {

	@Test
	public void idsInterfaceTest() {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";

		try {
			String type = "logistic_order_status";
			String data = "<?xml version='1.0' encoding='UTF-8'?><WMSSHP><BatchID>0004495496</BatchID><SHPHD><StorerKey>ANF</StorerKey><Facility>BS13</Facility><ExternOrderKey>S200045510661</ExternOrderKey><M_Company>910670853094084</M_Company><SOStatus>0</SOStatus><ShipperKey>SF</ShipperKey><LoadKey/><EffectiveDate>20150331213101</EffectiveDate></SHPHD></WMSSHP>";
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMDDHHMMss");
			String requestTime = sf.format(new Date());
			URL realUrl = new URL("http://10.8.12.151:8001/rest/ids.json");
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/xml");
			// conn.setRequestProperty("user-agent",
			// "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			String param1 = "Key=IDS_ANF_TEST&RequestTime=" + requestTime + "&Sign=0Mk4Li4Mu7Mn4Ca7Vn2Mx0Et7Sm4Sk8H&Version=1.0&ServiceType=" + type;
			String secretKey = AppSecretUtil.generateSecret(param1);
			String param = "Key=IDS_ANF_TEST&RequestTime=" + requestTime + "&Sign=" + secretKey + "&Version=1.0&ServiceType=" + type + "&Data=" + data;
			out.print(param);

			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			System.out.println("result" + result);
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
		}

		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
			}
		}
		System.out.println(result);

	}
}
