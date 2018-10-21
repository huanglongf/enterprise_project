package com.bt.vims.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bt.vims.service.VimsDataService;
import com.bt.vims.service.impl.VimsDataServiceImpl;
import com.bt.vims.utils.CommonUtils;

/**
 * 读取客户端访客信息并进行操作控制类
 * 
 * @author liuqingqiang
 * @date 2018-05-07
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/control/vimsDataController")
public class VimsDataController {
	
	private static final Logger logger = Logger.getLogger(VimsDataServiceImpl.class);
	
	@Resource(name = "vimsDataServiceImpl")
	private VimsDataService vimsDataServiceImpl;

	private Map<String, Object> messageCode = null;


	@RequestMapping(value = "/PostToLmis.do", produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8", method = RequestMethod.POST)
	@ResponseBody
	public String responseSocket(HttpServletRequest request, String sign, @RequestBody String body_param) {
		String encoding = CommonUtils.getEncoding(body_param);
		logger.error("字符编码："+encoding+"与"+sign + "与" + body_param);
		if (body_param.contains("&")) { //js端请求时过滤掉sign内容,主要解决js端存在请求跨域问题
			String[] param = body_param.split("[&]");
			if (param[1].endsWith("=")) { //过滤掉springMVC接收参数时加上的=号
				String bodyParam = CommonUtils.getStringFromBrowser(param[1].substring(0, param[1].length()-1),encoding); // 转换浏览器信息得到json数组
				messageCode = vimsDataServiceImpl.responseSocket(sign, bodyParam); // 返回成功失败信息给予客户端
			} else {
				String bodyParam = CommonUtils.getStringFromBrowser(param[1],encoding); // 转换浏览器信息得到json数组
				messageCode = vimsDataServiceImpl.responseSocket(sign, bodyParam); // 返回成功失败信息给予客户端
			}
		} else { // PEPPER机器人请求返回成功失败信息给予客户端
			messageCode = vimsDataServiceImpl.responseSocket(sign, body_param); 
		}
		// List<VisitorInfomation> visitorInfomations =
		// (List<VisitorInfomation>)messageCode.get("visitorInfomations");
		// if(visitorInfomations != null && visitorInfomations.size() > 0){
		// messageCode
		// =vimsDataServiceImpl.callPrintInterface(visitorInfomations);
		// //调用打印机接口
		// }
		return JSON.toJSONString(messageCode);
	}
	

}
