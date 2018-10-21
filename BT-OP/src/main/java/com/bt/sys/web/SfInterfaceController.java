package com.bt.sys.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bt.base.spring.SpringUtil;
import com.bt.common.util.CommonUtil;
import com.bt.common.util.HttpHelper;
import com.bt.common.util.MD5Util;
import com.bt.interf.sf.SfInterface;
import com.bt.sys.service.SfService;



@Controller
@RequestMapping("/control/sf/SfInterfaceController")
public class SfInterfaceController {

	
	@ResponseBody
	@RequestMapping("/cancelOrderDemo")
	public JSONObject cancelOrder(ModelMap map, HttpServletRequest request) throws Exception{
		        JSONObject obj=new JSONObject();
					List<String> results = new ArrayList<String>();
					Object[] result = null;
					// 生成JAX-WS动态客户端工厂类实体
					JaxWsDynamicClientFactory jDCF = JaxWsDynamicClientFactory.newInstance();
					// 动态生成客户端
					Client client = jDCF.createClient(CommonUtil.getConfig("wsdl_url_sf_test"));
					// 设置超时单位为毫秒
				    HTTPConduit http = (HTTPConduit) client.getConduit();
				    getStr();
				    // 响应超时
				    http.setClient(HttpHelper.setHttpClientPolicy());		
			        result = client.invoke("sfexpressService", new Object[] {getStr(), CommonUtil.encodeBase64(MD5Util.md5Encrypt(getStr() +CommonUtil.getConfig("checkword_sf")))});
			       String returnString="";
			        if (result != null) {
			        	for (Object robj : result) {
			        		returnString = robj.toString();
			        	}
			        	results.add(returnString);
			        	System.out.println(returnString);
			         obj.put("routeINfo", result);
					
		};
	
		return  obj;
	}
	
	
	@ResponseBody
	@RequestMapping("/test")
	public JSONObject test(ModelMap map, HttpServletRequest request) throws Exception{
		        JSONObject obj=new JSONObject();
		        SfInterface ss= SpringUtil.getBean("sfInterface");
		        //ss.CancelOrder("test123test");
		        return  obj;
	}
	
	
	public String getStr(){
		String str="<?xml version='1.0' encoding='UTF-8'?><Request service='OrderConfirmService' lang='zh-CN'><Head>"
				+ "j8DzkIFgmlomPt0aLuwU"
				+ "</Head><Body><OrderConfirm orderid='testtest' dealtype='2 ></OrderConfirm></Body></Request>";
		return str;
	}
}
