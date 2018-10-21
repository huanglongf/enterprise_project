package com.bt.orderPlatform.quartz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bt.common.util.CommonUtil;
import com.bt.orderPlatform.model.InterfaceRouteinfo;
import com.bt.orderPlatform.model.WaybilMasterDetail;
import com.bt.orderPlatform.model.WaybillMaster;
import com.bt.orderPlatform.service.InterfaceRouteinfoService;
import com.bt.orderPlatform.service.WaybillMasterService;

@Service
public class RouteQuartzSF {
	private static Logger logger = Logger.getLogger(RouteQuartzSF.class);

	@Resource(name = "waybillMasterServiceImpl")
	private WaybillMasterService<WaybillMaster> waybillMasterService;
	@Resource(name = "interfaceRouteinfoServiceImpl")
	private InterfaceRouteinfoService<InterfaceRouteinfo> interfaceRouteinfoService;
	
	HttpClient client = null;
	PostMethod postMethod = null;

	public void execute() throws Exception {
		logger.error("开始执行批量。。。...");
		List<WaybillMaster> waybillList = waybillMasterService.query2MonthAgoMaster();
		for (WaybillMaster waybill : waybillList) {
			insertWayBillInfo(waybill.getWaybill());
			waybillMasterService.updateRunTime(waybill.getWaybill());
		}
		logger.error("批量执行完毕。。。...");
	}

	public void insertWayBillInfo(String waybill) {
		try {
			client = new HttpClient();
			postMethod = new PostMethod(CommonUtil.getConfig("sf_radar_url"));
			postMethod.setParameter("express_code", "SF");
			postMethod.setParameter("waybill", waybill);
			int code = client.executeMethod(postMethod);
			if (code == HttpStatus.SC_OK) {
				String respStr = postMethod.getResponseBodyAsString();
				JSONObject jsonObject = JSONObject.parseObject(respStr);
				JSONArray jsonArray = (JSONArray) jsonObject.get("routeList");
				List<InterfaceRouteinfo> masterList = new ArrayList<InterfaceRouteinfo>();
		     	  Date dd=new Date();
				for (Iterator it = jsonArray.iterator(); it.hasNext();) {
					JSONObject job = (JSONObject) it.next();
					InterfaceRouteinfo master = new InterfaceRouteinfo();
					master.setCreate_user(job.getString("createUser"));
					master.setCreate_time(dd);
					master.setUpdate_time(dd);
					master.setUpdate_user(job.getString("updateUser"));
					master.setRoute_time(job.getDate("routeTime"));
					master.setRoute_remark(job.getString("routeRemark"));
					master.setWaybill(job.getString("waybill"));
					master.setTransport_code(job.getString("transportCode"));
					master.setRoute_opcode(job.getString("routeOpcode"));
					master.setFlag(0);
					master.setRoute_source("1");
					
					try {
						interfaceRouteinfoService.insertByObj(master);
					} catch (Exception e) {
//						e.printStackTrace();
					}
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}

}
