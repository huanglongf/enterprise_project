package com.bt.common.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.utils.CommonUtils;

/**
 * @Title:CommonController
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2017年3月28日下午2:05:58
 */
@Controller
@RequestMapping("/control/commonController")
public class CommonController  extends BaseController{

	private static final Logger logger = Logger.getLogger(CommonController.class);
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年3月28日下午2:22:57
	 */
	@RequestMapping("/nginxURL")
	public void nginxURL(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try {
			result= new JSONObject();
			result.put("FileUpload", CommonUtils.getAllMessage("config", "NGINX_FILE_UPLOAD"));
			result.put("FileDown", CommonUtils.getAllMessage("config", "NGINX_FILE_DOWNLOAD"));
			
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());
			
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		out.close();
		
	}
	
}