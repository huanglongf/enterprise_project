package com.bt.orderPlatform.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bt.common.util.MD5Util;
import com.bt.orderPlatform.controller.form.AreaQueryParam;
import com.bt.orderPlatform.model.Area;
import com.bt.orderPlatform.model.WaybillMaster;
import com.bt.orderPlatform.service.AreaService;
import com.bt.orderPlatform.service.WaybillMasterService;

import net.sf.json.JSONArray;

/**
 * 城市信息主表控制器
 *
 */
@Controller
@RequestMapping("/anbInterfance/doWaybill")
public class ZTOController {

	private static final Logger logger = Logger.getLogger(ZTOController.class);
	
	private final String interface_name = "9DDDD5CE1B1375BC497FEEB871842D4B";
	
	private final String ahb_key = "8B49A45A14FCBEB70F0B6782DC4E9059";
	@Resource(name = "waybillMasterServiceImpl")
	private WaybillMasterService<WaybillMaster> waybillMasterService;
	
	/**
	 * 物流回传
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getabhWaybillNum")
	public String toForm(ModelMap map, HttpServletRequest request) throws Exception{
		request.setCharacterEncoding("utf-8");
		String flag ="ERROR";
		String sign = request.getParameter("sign");
		String request_time = request.getParameter("request_time");
		String data = request.getParameter("data");
		System.out.println(ahb_key+"interface_name"+interface_name+"request_time" +request_time+"data"+data+ahb_key);
		String sign1 = MD5Util.md5(ahb_key+"interface_name"+interface_name+"request_time" +request_time+"data"+data+ahb_key);
		sign1 = sign1.toUpperCase();
		WaybillMaster master = new WaybillMaster();
		if(sign!=null&&sign!=""){
			if(sign.equals(sign1)){
				flag="OK";
				try {
					Map<String, Object> dataMap2 = JSON.parseObject((String) request.getParameter("data"));
					if((Integer) dataMap2.get("code")==0){
						//下单回传
						if(((String) dataMap2.get("status")).equals("0")){
							//获取系统单号
							master.setOrder_id((String) dataMap2.get("orderNo"));
							//获取中通回传单号
							master.setWaybill((String) dataMap2.get("outWaybill"));
							//获取大头笔
							master.setMark_destination((String) dataMap2.get("mark"));
							
							//master.setMark_destination((String) dataMap2.get("mark"));
							//获取集包地名称
							if((String) dataMap2.get("bagAddr")!=null){
								master.setPackage_name((String) dataMap2.get("bagAddr"));
								master.setPackage_code((String) dataMap2.get("bagAddr"));
							}
							//设置下单成功状态
							master.setStatus("2");
							//更新订单状态
							waybillMasterService.updateByMaster(master);
						}else if(((String) dataMap2.get("status")).equals("2")){//取消回传
							master.setOrder_id((String) dataMap2.get("orderNo"));
							master.setWaybill((String) dataMap2.get("outWaybill"));
							//设置取消成功状态
							master.setStatus("10");
							//订单状态更新为取消
							waybillMasterService.updateByMaster(master);
						}
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
		
	}
	
}
