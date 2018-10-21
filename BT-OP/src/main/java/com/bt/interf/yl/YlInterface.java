package com.bt.interf.yl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bt.common.util.CommonUtil;
import com.bt.common.util.DateUtil;
import com.bt.common.util.HttpUtil;
import com.bt.common.util.MD5Util;
import com.bt.orderPlatform.model.InterfaceRouteinfo;
import com.bt.orderPlatform.model.WaybilDetail;
import com.bt.orderPlatform.model.WaybillMaster;
import com.bt.orderPlatform.service.InterfaceRouteinfoService;
import com.bt.orderPlatform.service.WaybillDetailService;
import com.bt.orderPlatform.service.WaybillMasterService;
import com.sun.mail.util.MailSSLSocketFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class YlInterface {

	@Resource(name = "waybillMasterServiceImpl")
	private WaybillMasterService<WaybillMaster> waybillMasterService;
	@Resource(name = "waybillDetailServiceImpl")
	private WaybillDetailService<WaybilDetail> waybilDetailService;
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	//private final String shopName = "宝尊调货";
	private final String shopName = "宝尊";
	
	//private final String str = "d0880919a78e4f16ae02fa2761e8ed28";
	private final String str = "ED3A439896745807E040007F0200647D";
	
	
	@Resource(name = "interfaceRouteinfoServiceImpl")
	private InterfaceRouteinfoService<InterfaceRouteinfo> interfaceRouteinfoService;

	// private final String url = "http://210.22.91.77:24111/MerService/Baozun";
	//private final String url = "https://dhjt.chinaums.com/front_xgb/FrontService";
	private final String url = "http://210.22.91.77:24511/front_xgb/FrontService";

	private Map<String, Object> transEntity(WaybillMaster master, List<WaybilDetail> list) {
		Map<String, Object> map = new HashMap<>();
		// 商家 ID
		map.put("MemberID", "baozun");
		// 商家密码
		map.put("MemberPwd", "123456");
		// 快递公司编码
		map.put("ShipperCode", "HTKY");
		//门店编码
		map.put("StoreCode", master.getFrom_orgnization());
		// 订单编号
		map.put("OrderCode", master.getOrder_id());
		// 是否是电子面单(0 普通面单,1电子面单)
		/*map.put("IsAutoBill", master.getIsAutoBill());*/
		map.put("IsAutoBill", "1");
		// 邮费支付方式:1-现付， 2-到付， 3-月结， 4-第三方支付
		map.put("PayType", "3");
		// 快递类型： 1-标准快件 2-当日达(11 点前下单)
		map.put("ExpType", "4");
		map.put("SendCode", master.getSendcode());
		Map<String, Object> receiver = new HashMap<>();
		receiver.put("Company", master.getTo_address());
		receiver.put("Name", master.getTo_contacts());
		receiver.put("Mobile", master.getTo_phone());
		receiver.put("ProvinceName", master.getTo_province());
		receiver.put("CityName", master.getTo_city());
		receiver.put("ExpAreaName", master.getTo_state());
		receiver.put("Address", master.getTo_address());
		receiver.put("Tel", "123");

		map.put("Receiver", receiver);

		Map<String, Object> sender = new HashMap<>();
		sender.put("Company", master.getFrom_address());
		sender.put("Name", master.getFrom_contacts());
		sender.put("Mobile", master.getFrom_phone());
		sender.put("ProvinceName", master.getFrom_province());
		sender.put("CityName", master.getFrom_city());
		sender.put("ExpAreaName", master.getFrom_state());
		sender.put("Address", master.getFrom_address());
		sender.put("Tel", "123");

		map.put("Sender", sender);
		map.put("Quantity", master.getTotal_qty());

		List<Map<String, Object>> comList = new ArrayList<>();
		for (WaybilDetail wd : list) {
			Map<String, Object> commodity = new HashMap<>();
			commodity.put("GoodsName", wd.getSku_name());
			comList.add(commodity);
		}
		map.put("Commodity", comList);

		return map;
	}

	/**
	 * 创建订单
	 * 
	 * @param map
	 *            参数集合
	 * @return
	 * @throws Exception
	 */
	public int createOrder(WaybillMaster master, List<WaybilDetail> list) {
		Map<String, Object> dataMap = transEntity(master, list);

		JSONObject data = JSONObject.fromObject(dataMap);
		master.setOrder_time(new Date());
		Map<String, Object> map = getHead(data.toString(), "PB01");
		String rets = HttpUtil.sendHttpRequest(url, map);
		System.out.println(rets);
		if (rets == null) {
			return 0;
		}
		Map<String, Object> ret = JSON.parseObject(rets);
		System.out.println(ret);
		Map<String, Object> dataMap2 = JSON.parseObject((String) ret.get("data"));
		System.out.println(dataMap2);
		boolean flag = (boolean) dataMap2.get("Success");
		if (!flag) {
			return 0;
		}
		Map<String, Object> order = (Map<String, Object>) dataMap2.get("Order");
		master.setWaybill(order.get("LogisticCode").toString());
		master.setMark_destination(order.get("MarkDestination").toString());
		//master.setPackage_name(order.get("PackageName").toString());
		master.setPackage_code(order.get("PackageCode").toString());
		master.setStatus("2");
		master.setOrder_time(new Date());
		/*String productTypeCode = master.getProducttype_code();
		if (productTypeCode.equals("1")) {
			master.setProducttype_name("标准快件");
		}
		if (productTypeCode.equals("2")) {
			master.setProducttype_name("当日达(11 点前下单)");
		}*/
		master.setUpdate_time(new Date());
		waybillMasterService.updateByMaster(master);
		/*for (WaybilDetail wd : list) {
			wd.setStatus("1");
			wd.setCreate_user("test");
			wd.setUpdate_time(new Date());
			wd.setUpdate_user("test");
			wd.setStatus("0");
			wd.setOrder_id(master.getOrder_id());
			wd.setQty(10);
			wd.setCreate_time(new Date());
			wd.setWeight(new BigDecimal(500.23));
			wd.setVolumn(new BigDecimal(1234.93));
			wd.setAmount(new BigDecimal(303));
			waybilDetailService.insert(wd);
		}*/

		return 1;
	}

	/**
	 * 取消订单
	 * 
	 * @param shipperCode
	 *            快递公司编码 Y
	 * @param waybill
	 *            运单号 waybill 不为空
	 * @param umsbillNo
	 *            大华订单号
	 * @param isAutoBill
	 *            是否是电子面单 0 普通面单,1 电子面单 Y
	 * @param orderId
	 *            订单号
	 * @return 0失败 1成功
	 * @throws Exception
	 */
	public int cancelOrder(String shipperCode, String waybill, String umsbillNo, int isAutoBill,String orderId,String s_status) {
		if (CommonUtil.isNull(shipperCode) && CommonUtil.isNull(waybill) && CommonUtil.isNull(umsbillNo)) {
			return 0;
		}
		Map<String, Object> params = new HashMap<>();
		params.put("ShipperCode", "HTKY");
		params.put("OrderId", waybill);
		params.put("UmsbillNo", null);
		params.put("IsAutoBill", isAutoBill);
		String data = JSONObject.fromObject(params).toString();
		Map<String, Object> map = getHead(data, "PB02");
		String resultStr = HttpUtil.sendHttpRequest(url, map);
		System.out.println(resultStr);
		Map<String, Object> ret = JSON.parseObject(resultStr);
		System.out.println(ret);
		Map<String, Object> dataMap2 = JSON.parseObject((String) ret.get("data"));
		System.out.println(dataMap2);
		String messageCode = (String) dataMap2.get("MessageCode");
		if (messageCode.equals("00")) {
			/*// 修改状态
			WaybillMaster master = new WaybillMaster();
			//master.setOrder_id(orderId);
			master.setStatus("2");
			waybillMasterService.updateByObj(master);
			
			WaybilDetail wd = new WaybilDetail();
		//	wd.setOrder_id(orderId);
			wd.setStatus("2");
			waybilDetailService.updateByObj(wd);*/
			String status ="10";
			waybillMasterService.setstatus(orderId,status);
			return 1;
		}else{
			waybillMasterService.setstatus(orderId,s_status);
		}
		return 0;
	}

	private Map<String, Object> getHead(String data, String transType) {
		Map<String, Object> map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String date = sdf.format(new Date());
		String deviceType = "pc";
		map.put("device_type", deviceType);
		map.put("request_time", date);
		map.put("trans_type", transType);
		map.put("shop_name", shopName);
		map.put("data", data);
		
		String mac = MD5Util.md5(date + deviceType + transType + data + shopName + str);
		map.put("mac", mac);
		return map;
	}
	
	
	
	
	/*
	 * 路由查询
	 */
	public int  queryOrder(String shipperCode,String orderId) {
		  if(CommonUtil.isNull(shipperCode)&&CommonUtil.isNull(orderId)){
			  return 0;
		  }
		  Map<String,Object> dataMap = new HashMap<String, Object>();
			dataMap.put("ShipperCode", "HTKY");
			dataMap.put("waybillNo", orderId);
		  JSONObject data = JSONObject.fromObject(dataMap);
		  
		  Map<String,Object> map = new HashMap<>();
		  
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		  String date = sdf.format(new Date());
		  
		  map.put("device_type", "pc");
		  map.put("data", data);
		  String mac = MD5Util.md5(date+"pc"+"BO01"+data+shopName+str);
		  map.put("mac", mac);
		  map.put("trans_type", "BO01");
		  map.put("request_time", date);
		  map.put("shop_name", shopName);
		  String resultStr = HttpUtil.sendHttpRequest(url, map);
		  
		  
		  JSONObject ret = JSONObject.fromObject(resultStr);
			JsonNode jsonNode = null;
			try {
				jsonNode = MAPPER.readTree(resultStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		  JsonNode jsonNode2 = jsonNode.get("data");
		  JSONObject date2 = JSONObject.fromObject(jsonNode2.asText());
		if(ret != null && date2.get("messageCode")!=null && date2.get("messageCode").equals("00")){
			JSONArray wlInfoLists =(JSONArray) date2.get("multWlInfoList");
			if(wlInfoLists.size()<=0){
				return 0;
			}
			JSONObject obj = (JSONObject) wlInfoLists.get(0);
			JSONArray wlInfoLists1 =(JSONArray) obj.get("wlInfoList");
			if(wlInfoLists1.size()<=0){
				return 0;
			}
			InterfaceRouteinfo  master =new InterfaceRouteinfo();
			master.setWaybill(obj.get("waybillNo").toString());
			for (int i=0; i < wlInfoLists1.size(); i++) {
				obj = (JSONObject) wlInfoLists1.get(i);
				master.setCreate_time(new Date());
				master.setCreate_user("system");
				master.setUpdate_time(new Date());
				master.setUpdate_user("system");
				master.setTransport_code("BSB");
				master.setRoute_time(DateUtil.StrToYMDHMS(obj.get("signTime").toString()));
				master.setRoute_remark(obj.get("wlStatus").toString()+obj.get("signAddress").toString());
				if(obj.get("wlStatusNum")!=null){
					master.setRoute_opcode(obj.get("wlStatusNum").toString());
				}
				//master.setWaybill(null);
				try{
					interfaceRouteinfoService.insertByObj(master);
	        	}catch(Exception e){}
			}
			
			return 1;
		
		}
		return 0;
	}
	
	
	/**
	 * c端下单
	 * @param master
	 * @return
	 */
	
	public String insertByObj(WaybillMaster master) {
		Map<String,Object> dataMap = new HashMap<String, Object>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		dataMap.put("merOrderNo", master.getOrder_id());
		if(master.getExpressCode().equals("YUNDAC")){
			dataMap.put("shipperCode", "YUNDA");
		}else{
			dataMap.put("shipperCode", master.getExpressCode());
		}
		
		//寄件帐号
		dataMap.put("senderAccount", master.getCreate_user());
		/*//承运物流公司id
		if(master.getExpressCode().equals("YUNDAC")){
			dataMap.put("logisticsId", master.getExpressCode());
		}else{
			dataMap.put("logisticsId", master.getExpressCode());
		}
		*/
		//寄件人姓名
		dataMap.put("senderName", master.getFrom_contacts());
		//寄件人详细地址
		dataMap.put("senderAddress", master.getFrom_address());
		//寄件人电话
		dataMap.put("senderPhone", master.getFrom_phone());
		//寄件人地址编码
		dataMap.put("senderNum", master.getFrom_num());
		//寄件人所在省
		dataMap.put("senderProvince", master.getFrom_province());
		//寄件人所在市
		dataMap.put("senderCity", master.getFrom_city());
		//寄件人所在区县
		dataMap.put("senderArea", master.getFrom_state());
		//收件人姓名
		dataMap.put("receiverName", master.getTo_contacts());
		//收件人详细地址
		dataMap.put("receiverAddress", master.getTo_address());
		//收件人电话
		dataMap.put("receiverPhone", master.getTo_phone());
		//收件人所在省
		dataMap.put("receiverProvince", master.getTo_province());
		//收件人所在市
		dataMap.put("receiverCity", master.getTo_city());
		//收件人所在区县
		dataMap.put("receiverArea", master.getTo_state());
		//收件人地址编码
		dataMap.put("receiverNum", master.getTo_num());
		//货物类型
		dataMap.put("cargoType", master.getCargo_type());
		//备注
		dataMap.put("memo", master.getMemo());
		//预约日期
		/*dataMap.put("submitDate", sdf1.format(master.getCreate_time()));
		//预约时间
		dataMap.put("submitTime", sdf.format(master.getCreate_time()));*/
		//平台预估价格
		dataMap.put("cod", master.getTotal_amount());
		//收件人电话2
		dataMap.put("receiverPhone2", master.getTo_phone2());
		//货物重量
		dataMap.put("weight", master.getTotal_weight());
		//渠道来源
		dataMap.put("customerSource", master.getCustomer_source());
		//全民付注册手机号
		dataMap.put("customerMobile", master.getCustomer_phone());
		
	  JSONObject data = JSONObject.fromObject(dataMap);
	  
	  
	  Map<String,Object> map = new HashMap<>();
	  
	  SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddhhmmss");
	  String date = sdf2.format(new Date());
	  
	  map.put("device_type", "pc");
	  map.put("data", data);
	  String mac = MD5Util.md5(date+"pc"+"PGB2"+data+shopName+str);
	  map.put("mac", mac);
	  map.put("trans_type", "PGB2");
	  map.put("request_time", date);
	  map.put("shop_name", shopName);
	  String resultStr = HttpUtil.sendHttpRequest(url, map);
	  
	  
	  JSONObject ret = JSONObject.fromObject(resultStr);
	  System.out.println(ret);
	  String rets =ret.toString();
	  System.out.println(rets);
		JsonNode jsonNode = null;
		try {
			jsonNode = MAPPER.readTree(resultStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	  JsonNode jsonNode2 = jsonNode.get("data");
	  JSONObject date2 = JSONObject.fromObject(jsonNode2.asText());
	  System.out.println(date2);
	  System.out.println(date2.get("messageCode").equals("00"));
		if(ret != null && date2.get("messageCode")!=null && date2.get("messageCode").equals("00")){
			master.setOrder_id(date2.get("merOrderNo").toString());
			master.setStatus("2");
			master.setWaybill(date2.get("orderID").toString());
			master.setUpdate_time(new Date());
			master.setOrder_time(new Date());
			
			//master.setId(date2.get("orderID").toString());
			//master.setCreate_time(new Date());
			//master.setTotal_qty(1);
			//master.setUpdate_user(master.getCreate_user());
			//master.setFrom_orgnization("YUNDA");
			//master.setTo_orgnization(master.getTo_address());
			waybillMasterService.updateByMaster(master);
			return date2.get("message").toString();
		}
	  return date2.get("message").toString();
	  
	}
	
	
	/**
	 * c端订单取消
	 * @param logisticsId
	 * @param orderId
	 * @return
	 */
	
	public int  cancelOrder(String logisticsId,String orderId,String s_status) {
		  if(CommonUtil.isNull(logisticsId)&&CommonUtil.isNull(orderId)){
			  return 0;
		  }
		  Map<String,Object> dataMap = new HashMap<String, Object>();
			dataMap.put("logisticsId", logisticsId);
			dataMap.put("orderId", orderId);
		  JSONObject data = JSONObject.fromObject(dataMap);
		  
		  
		  Map<String,Object> map = new HashMap<>();
		  
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		  String date = sdf.format(new Date());
		  
		  map.put("device_type", "pc");
		  map.put("data", data);
		  String mac = MD5Util.md5(date+"pc"+"PGB3"+data+shopName+str);
		  map.put("mac", mac);
		  map.put("trans_type", "PGB3");
		  map.put("request_time", date);
		  map.put("shop_name", shopName);
		  String resultStr = HttpUtil.sendHttpRequest(url, map);
		  
		  
		  JSONObject ret = JSONObject.fromObject(resultStr);
		  String rets =ret.toString();
		  System.out.println(rets);
			JsonNode jsonNode = null;
			try {
				jsonNode = MAPPER.readTree(resultStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		  JsonNode jsonNode2 = jsonNode.get("data");
		  JSONObject date2 = JSONObject.fromObject(jsonNode2.asText());
		  System.out.println(date2);
		if(ret != null && date2.get("messageCode")!=null && date2.get("messageCode").equals("00")){
			WaybillMaster master = new WaybillMaster();
			//master.setOrder_id(orderId);
			String status ="10";
			waybillMasterService.setstatus(orderId,status);
			return 1;
			}else{
				waybillMasterService.setstatus(orderId,s_status);
			}
		return 0;
	}
	/*
	 * c端路由查询
	 */
	public int  queryCOrder(String waybillNo,String orderId) {
		  if(CommonUtil.isNull(waybillNo)&&CommonUtil.isNull(orderId)){
			  return 0;
		  }
		  Map<String,Object> dataMap = new HashMap<String, Object>();
			dataMap.put("waybillNo", waybillNo);
			dataMap.put("orderId", orderId);
		  JSONObject data = JSONObject.fromObject(dataMap);
		  
		  
		  Map<String,Object> map = new HashMap<>();
		  
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		  String date = sdf.format(new Date());
		  
		  map.put("device_type", "pc");
		  map.put("data", data);
		  String mac = MD5Util.md5(date+"pc"+"PGB0"+data+shopName+str);
		  map.put("mac", mac);
		  map.put("trans_type", "PGB0");
		  map.put("request_time", date);
		  map.put("shop_name", shopName);
		  String resultStr = HttpUtil.sendHttpRequest(url, map);
		  
		  
		  JSONObject ret = JSONObject.fromObject(resultStr);
		  System.out.println(ret);
			JsonNode jsonNode = null;
			try {
				jsonNode = MAPPER.readTree(resultStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		  JsonNode jsonNode2 = jsonNode.get("data");
		  JSONObject date2 = JSONObject.fromObject(jsonNode2.asText());
		if(ret != null && date2.get("messageCode")!=null && date2.get("messageCode").equals("00")){
			JSONArray wlInfoLists =(JSONArray) date2.get("wlInfoList");
			System.out.println(wlInfoLists);
			if(wlInfoLists.size()<=0){
				return 0;
			}
			JSONObject obj = (JSONObject) wlInfoLists.get(0);
			for (int i=0; i < wlInfoLists.size(); i++) {
				obj = (JSONObject) wlInfoLists.get(i);
				InterfaceRouteinfo  master =new InterfaceRouteinfo();
				master.setCreate_time(new Date());
				master.setCreate_user("system");
				master.setWaybill(obj.get("waybillNo").toString());
				master.setUpdate_time(new Date());
				master.setUpdate_user("system");
				master.setRoute_time(DateUtil.StrToYMDHMS(obj.get("createTime").toString()));
				master.setRoute_remark(obj.get("wlNote").toString());
				if(obj.get("wlStatus")!=null){
					master.setRoute_opcode(obj.get("wlStatus").toString());
				}
				master.setTransport_code("YUNDA");
				try{
					interfaceRouteinfoService.insertByObj(master);
	        	}catch(Exception e){}
			}
			return 1;
		}
		return 0;
	}

}
