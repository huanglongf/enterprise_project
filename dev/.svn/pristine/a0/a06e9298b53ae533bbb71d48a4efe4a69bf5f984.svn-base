package com.bt.interf.rfd;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bt.common.util.DateUtil;
import com.bt.common.util.HttpUtil;
import com.bt.common.util.MD5Util;
import com.bt.orderPlatform.model.InterfaceRouteinfo;
import com.bt.orderPlatform.model.WaybilDetail;
import com.bt.orderPlatform.model.WaybillMaster;
import com.bt.orderPlatform.service.InterfaceRouteinfoService;
import com.bt.orderPlatform.service.WaybillMasterService;

import net.sf.json.JSONObject;

/** 
	* @Description: 
	* @author  Hanery:
 	* @date 2017年7月6日 上午10:40:42  
*/
@Service
public class RFDInterface {
	
	@Resource(name = "interfaceRouteinfoServiceImpl")
	private InterfaceRouteinfoService<InterfaceRouteinfo> interfaceRouteinfoService;
	@Resource(name = "waybillMasterServiceImpl")
	private WaybillMasterService<WaybillMaster> waybillMasterService;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private final String url = "http://editest.wuliusys.com/api/order/import";
	private final String queryurl = "http://editest.wuliusys.com/api/order/Track";
	private final String cancelurl = "http://editest.wuliusys.com/api/order/Cancel";
	
	private Map<String, Object> transEntity(WaybillMaster master, List<WaybilDetail> list) {
		Map<String, Object> map = new HashMap<>();
		// 商家 ID
		map.put("MerchantCode", "bzds001");
		map.put("FormCode", master.getOrder_id());
		// 快递公司编码
		map.put("PerFormCode", "rfd");
		// 订单编号
		map.put("WaybillNo", "0");
		map.put("MainCode", null);
		map.put("DeliverCode", null);
		map.put("ToName", master.getTo_contacts());
		map.put("ToAddress", master.getTo_address());
		map.put("ToProvince", master.getTo_province());
		map.put("ToCity", master.getTo_city());
		map.put("ToArea", master.getTo_state());
		map.put("ToPostCode", null);
		map.put("ToMobile", master.getTo_phone());
		map.put("ToPhone", null);
		/*订单类型,定义如下：0:普通订单 1:上门换货 2:上门退货 3:签单返回 */
		map.put("OrderType", "0");
		map.put("TotalAmount", null);
		map.put("PaidAmount", null);
		map.put("ReceiveAmount", "0");
		map.put("RefundAmount", "0");
		map.put("Weight", master.getTotal_weight());
		map.put("Comment", master.getMemo());
		map.put("FromName", master.getFrom_contacts());
		map.put("FromAddress", master.getFrom_address());
		map.put("FromProvince", master.getFrom_province());
		map.put("FromCity ", master.getFrom_city());
		map.put("FromArea ", master.getFrom_state());
		map.put("FromPostCode ", null);
		map.put("FromMobile ", master.getFrom_phone());
		map.put("FromPhone ", null);
		/*货物属性，定义如下： 0:普货 1:禁航 2:易碎品 */
		map.put("GoodsProperty ", "0");
		map.put("GoodsCategory ", null);
		map.put("PackageCount ", master.getTotal_qty());
		map.put("DistributionCode ", null);
		map.put("CurrentDistributioncode ", null);
		map.put("WareHouseId ", null);
		map.put("WarehouseName ", null);
		map.put("SortingCenterId ", null);
		map.put("SortingCenterName ", null);
		/*支付方式,定义如下：1:现金 2:POS机 */
		map.put("CashType ", "1");
		map.put("ExtendData ", null);
		map.put("SendTimeType ", null);
		map.put("DeliveryStartDate ", null);
		map.put("DeliveryEndDate ", null);
		map.put("RequireFetchTime ", null);

		List<Map<String, Object>> comList = new ArrayList<>();
		for (WaybilDetail wd : list) {
			Map<String, Object> commodity = new HashMap<>();
			commodity.put("ProductName", wd.getSku_name());
			commodity.put("Count", wd.getQty());
			commodity.put("Unit", "件");
			commodity.put("SellPrice", wd.getAmount());
			commodity.put("Size", wd.getVolumn());
			commodity.put("ProductCode", wd.getSku_code());
			commodity.put("ExtendData", null);
			comList.add(commodity);
		}
		map.put("OrderDetails", comList);
		return map;
	}
	
	
	private Map<String, Object> getHead(String data) {
		Map<String, Object> map = new HashMap<>();
		String identity = "0ba3ca4b-6897-489e-8b31-2738cc692030";
		map.put("identity", identity);
		String mac = MD5Util.md5(data + "|" + "935ee7f6-cc05-402b-8329-fe92b41cc62c");
		map.put("token", mac);
		return map;
	}
	
	
	/**
	 * 创建订单
	 * @param master
	 * @param list
	 * @return
	 */
	public String insertByObj(WaybillMaster master, List<WaybilDetail> list) {
		/*String[] array = new String[list.size()];
		for(int i=0;i<list.size(); i++){
			array[i] = list.get(i).toString();
		}*/
		Map<String, Object> dataMap = transEntity(master, list);
		JSONObject data = JSONObject.fromObject(dataMap);
		Map<String, Object> map = getHead("["+data.toString()+"]");
		String rets = HttpUtil.sendHttpRequestrfd(url, map,"["+data.toString()+"]");
		if (rets == null) {
			return "";
		}
		Map<String, Object> ret = JSON.parseObject(rets);
		System.out.println(ret);
		
		boolean flag = (boolean) ret.get("IsSuccess");
		if (!flag) {
			return "";
		}
		JSONArray order =(JSONArray) ret.get("ResultData");
		com.alibaba.fastjson.JSONObject obj =  (com.alibaba.fastjson.JSONObject) order.get(0);
		System.out.println(order);
		/*"ResultData":[{"WaybillNo":11707066633595,
		"FormCode":"123456",
		"ResultCode":"01",
		"ResultMessage":"",
		"DistributionCode":"rfd",
		"DistributionName":"如风达快递有限公司",
		"StationId":4512,
		"StationName":"上海静安站",
		"Message":null,
		"MnemonicCode":null,
		"MnemonicName":null,
		"CityNumber":"2000",
		"SiteNo":"B028",
		"LineNumber":null}]*/
		master.setWaybill(obj.get("WaybillNo").toString());
		master.setMark_destination(obj.get("SiteNo").toString());
		master.setPackage_name(obj.get("StationName").toString());
		master.setPackage_code(obj.get("StationId").toString());
		master.setStatus("2");
		master.setOrder_time(new Date());
		//waybillMasterService.updateByMaster(master);
		System.out.println(rets);
	  return "1";
	}
	
	/**
	 * 
	 * @param waybill物流公司订单号
	 * @return
	 */
	public String  queryOrder(String waybill) {
		Map<String, Object> dataMap = new HashMap<>();
		// 商家 ID
		dataMap.put("OrderType", "0");
		dataMap.put("Orders", waybill);
		JSONObject data = JSONObject.fromObject(dataMap);
		Map<String, Object> map = getHead(data.toString());
		String rets = HttpUtil.sendHttpRequestrfd(queryurl, map,data.toString());
		if (rets == null) {
			return "";
		}
		Map<String, Object> ret = JSON.parseObject(rets);
		System.out.println(ret);
		boolean flag = (boolean) ret.get("IsSuccess");
		if (!flag) {
			return "";
		}
		JSONArray order =(JSONArray) ret.get("ResultData");
		if(order.size()<=0){
			return "";
		}
		com.alibaba.fastjson.JSONObject obj =  (com.alibaba.fastjson.JSONObject) order.get(0);
		System.out.println(order);
		for (int i=0; i < order.size(); i++) {
			obj = (com.alibaba.fastjson.JSONObject) order.get(i);
			InterfaceRouteinfo  master =new InterfaceRouteinfo();
			master.setCreate_time(new Date());
			master.setCreate_user("system");
			master.setWaybill(obj.get("WaybillNo").toString());
			master.setUpdate_time(new Date());
			master.setUpdate_user("system");
			master.setTransport_code("rfd");
			String s = obj.get("OperateTime").toString();
			Date date = DateUtil.StrToYMDHMSDate(obj.get("OperateTime").toString());
			System.out.println(date);
			master.setRoute_time(DateUtil.StrToYMDHMSDate(obj.get("OperateTime").toString()));
			master.setRoute_remark(obj.get("OperateMessage").toString());
			if(obj.get("Status")!=null){
				master.setRoute_opcode(obj.get("Status").toString());
			}
			try{
				interfaceRouteinfoService.insertByObj(master);
        	}catch(Exception e){}
			
		}
		return "";
	}
	
	
	/**
	 * 
	 * @param order_id系统生成的流水号
	 * @return
	 */
	public String  cancelOrder(String order_id) {
		Map<String, Object> dataMap = new HashMap<>();
		// 商家 ID
		dataMap.put("OrderType", "1");
		dataMap.put("OrderNo", order_id);
		dataMap.put("Message", "任性");
		JSONObject data = JSONObject.fromObject(dataMap);
		Map<String, Object> map = getHead("["+data.toString()+"]");
		String rets = HttpUtil.sendHttpRequestrfd(cancelurl, map,"["+data.toString()+"]");
		if (rets == null) {
			return "0";
		}
		Map<String, Object> ret = JSON.parseObject(rets);
		System.out.println(ret);
		boolean flag = (boolean) ret.get("IsSuccess");
		if (!flag) {
			return "0";
		}
		JSONArray order =(JSONArray) ret.get("ResultData");
		if(order.size()<=0){
			return "0";
		}
		com.alibaba.fastjson.JSONObject obj =  (com.alibaba.fastjson.JSONObject) order.get(0);
		System.out.println(obj);
		Map<String, Object> ob = JSON.parseObject(obj.toString());
		boolean flag1 = (boolean) ob.get("IsSuccess");
		if(flag1){
			String status ="10";
			waybillMasterService.setstatus(order_id,status);
			return "1";
		}
		return "0";
	}

}
