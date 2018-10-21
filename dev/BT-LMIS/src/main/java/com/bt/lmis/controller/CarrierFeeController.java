package com.bt.lmis.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.model.CarrierFeeFlag;
import com.bt.lmis.service.CarrierFeeFlagService;

@Controller
@RequestMapping("/control/carrierFeeController")
public class CarrierFeeController extends BaseController {

	private static final Logger logger = Logger.getLogger(CarrierFeeController.class);
	
	@Resource(name = "carrierFeeFlagServiceImpl")
	private CarrierFeeFlagService<CarrierFeeFlag> carrierFeeFlagServiceImpl;
	
	/**
	 * 
	 * @Description: TODO(保存快递合同主表数据)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年6月22日下午5:52:56
	 */
	@RequestMapping("/saveCarrierFeeFlag")
	public void saveCarrierFeeFlag(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try{
			result = carrierFeeFlagServiceImpl.saveCarrierFeeFlag(request, result);
		} catch(Exception e) {
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
