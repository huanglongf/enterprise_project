package com.bt.lmis.basis.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bt.common.controller.BaseController;
import com.bt.lmis.basis.service.ConsumerManagerService;

/** 
 * @ClassName: ConsumerController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年8月1日 上午11:01:39 
 * 
 */
@Controller
@RequestMapping("/control/consumerController")
public class ConsumerController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(ConsumerController.class);
	
	@Resource(name="consumerManagerServiceImpl")
	private ConsumerManagerService<T> consumerManagerService;
	
	/**
	 * @Title: listValidConsumer
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @param response
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年8月1日 上午11:06:01
	 */
	@RequestMapping("/listValidConsumer.do")
	public void listValidConsumer(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		try {
			JSONObject result= new JSONObject();
			result.put("consumer", consumerManagerService.listValidConsumer());
			out= response.getWriter();
			out.write(result.toString());
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
			
		} finally {
			out.close();
			
		}
		
	}
	
}
