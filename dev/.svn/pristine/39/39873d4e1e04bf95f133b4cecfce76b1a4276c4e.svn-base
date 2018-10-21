package com.bt.interf.zto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bt.common.util.DateUtil;
import com.bt.common.util.HttpUtil;
import com.bt.common.util.MD5Util;
import com.bt.orderPlatform.model.InterfaceRouteinfo;
import com.bt.orderPlatform.model.WaybilDetail;
import com.bt.orderPlatform.model.WaybillMaster;
import com.bt.orderPlatform.service.InterfaceRouteinfoService;
import com.bt.orderPlatform.service.WaybillDetailService;
import com.bt.orderPlatform.service.WaybillMasterService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
	* @Description: 
	* @author  Hanery:
 	* @date 2017年10月16日 下午2:26:53  
*/
@Service
public class ZTOInterface {

	@Resource(name = "waybillMasterServiceImpl")
	private WaybillMasterService<WaybillMaster> waybillMasterService;
	@Resource(name = "waybillDetailServiceImpl")
	private WaybillDetailService<WaybilDetail> waybilDetailService;
	@Resource(name = "interfaceRouteinfoServiceImpl")
	private InterfaceRouteinfoService<InterfaceRouteinfo> interfaceRouteinfoService;
	
	
	private final String url = "http://test235weixin.abh58.net/doWaybillWMS/doWaybill";
	private final String luyouurl = "http://test235weixin.abh58.net/doWaybillWMS/queryWaybills";
	//private final String url = "http://way.tunnel.qydev.com/doWaybillWMS/doWaybill";
	
	
	
	private final String app_secret = "abf4cbaeb90688c4ed5006622c971844";
	
	private final String app_key = "api_online";
	
	private final String customerNm = "baozun";
	
	private final String method = "com.abh.waybill.save";
	
	
	/**
	 * 中通下单、取消接口（falg传0为下单，传2为取消）
	 * @param master
	 * @return
	 */
	public int createOrder(WaybillMaster master, List<WaybilDetail> list,int falg) {
		Map<String, Object> dataMap = transEntity(master, list,falg);
		
		JSONObject data = JSONObject.fromObject(dataMap);
		
		Map<String, Object> map = getHead(data.toString());
		System.out.println(map);		
		String resultStr = HttpUtil.sendHttpRequest(url, map);
		
		System.out.println(resultStr);
		
		return 0;
	}
	/**
	 * 中通路由查询接口
	 * @param master
	 * @return
	 */
	public int queryOrder(WaybillMaster master) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("outWaybillNum", master.getWaybill());
		
		JSONArray data=JSONArray.fromObject(dataMap);
		
		Map<String, Object> map = getHead(data.toString());
				
		String resultStr = HttpUtil.sendHttpRequest(luyouurl, map);
		
		System.out.println(resultStr);
		if (resultStr == null) {
			return 0;
		}
		Map<String, Object> ret = JSON.parseObject(resultStr);
		System.out.println(ret);
		Integer code = (Integer) ret.get("code");
		if(code==0){
			List list = (List) ret.get("message");
			if(list.size()>0){
				for (int i=0; i < list.size(); i++) {
					Map<String, Object> object = (Map<String, Object>) list.get(i);
					String outWaybillNum = object.get("outWaybillNum").toString();
					InterfaceRouteinfo  master1 =new InterfaceRouteinfo();
					master1.setWaybill(outWaybillNum);
					master1.setRoute_opcode(object.get("status").toString());
					List traces = (List) object.get("traces");
					if(traces.size()>0){
						for (int j=0; j < traces.size(); j++) {
							Map<String, Object> tracesobj = (Map<String, Object>) traces.get(j);
							master1.setRoute_time(DateUtil.StrToYMDHMS(tracesobj.get("operDate").toString()));
							master1.setRoute_remark(tracesobj.get("remark").toString());
							master1.setCreate_time(new Date());
							master1.setCreate_user("system");
							master1.setUpdate_time(new Date());
							master1.setUpdate_user("system");
							master1.setTransport_code("ZTO");
							String remark = tracesobj.get("remark").toString();     
							System.out.println(remark);
							try{
								interfaceRouteinfoService.insertByObj(master1);
								if(master1.getRoute_opcode().equals("1")||master1.getRoute_opcode().equals("0")||master1.getRoute_opcode().equals("13")){
									waybillMasterService.setstatus(master1.getWaybill(),"2");
								}else if(master1.getRoute_opcode().equals("2")||master1.getRoute_opcode().equals("3")){
									waybillMasterService.setstatus(master1.getWaybill(),"5");
								}else if(master1.getRoute_opcode().equals("4")||master1.getRoute_opcode().equals("5")||master1.getRoute_opcode().equals("6")||master1.getRoute_opcode().equals("35")){
									waybillMasterService.setstatus(master1.getWaybill(),"6");
								}else if(master1.getRoute_opcode().equals("7")||master1.getRoute_opcode().equals("8")||master1.getRoute_opcode().equals("9")||master1.getRoute_opcode().equals("11")||master1.getRoute_opcode().equals("17")){
									waybillMasterService.setstatus(master1.getWaybill(),"7");
								}
				        	}catch(Exception e){}
						}
					}
				}
			}
			
			
		}
		return 0;
	}
	
	

	private Map<String, Object> getHead(String data) {
		Map<String, Object> map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestamp = sdf.format(new Date());
		System.out.println(app_secret+"app_key"+app_key+"data"+data + "method" +method+ "timestamp" +timestamp+ app_secret);
		String sign = MD5Util.md5(app_secret+"app_key"+app_key+"data"+data + "method" +method+ "timestamp" +timestamp+ app_secret);
		sign = sign.toUpperCase();
		map.put("sign", sign);
		map.put("method", method);
		map.put("timestamp", timestamp);
		map.put("app_key", app_key);
		map.put("data", data);
		return map;
	}

	private Map<String, Object> transEntity(WaybillMaster master, List<WaybilDetail> list,int falg) {
		Map<String, Object> map = new HashMap<>();
		//收货人
		Map<String, Object> receiver = new HashMap<>();
		receiver.put("customerName", master.getTo_contacts());//收货客户名称
		receiver.put("customerCode", "001");//收货客户编号
		receiver.put("contract", master.getTo_contacts()); //收件人名称
		receiver.put("phone", master.getTo_phone()); //电话
		receiver.put("tel", master.getTo_phone2()); //电话
		//省市区县 //省市区县用逗号隔开
		receiver.put("distination", master.getTo_province()+","+master.getTo_city()+","+master.getTo_state());
		receiver.put("address", master.getTo_address());//详细地址
		
		//发货人
		Map<String, Object> sender = new HashMap<>();
		sender.put("customerName", master.getFrom_contacts());//发货客户名称
		sender.put("customerCode", "");//发货客户编号
		sender.put("contract", master.getFrom_contacts());//发件人名称
		sender.put("tel", master.getFrom_phone());//手机
		sender.put("phone", master.getFrom_phone());//手机
		//省市区县 //省市区县用逗号隔开
		sender.put("distination", master.getFrom_province()+","+master.getFrom_city()+","+master.getFrom_state());
		sender.put("address", master.getFrom_address());//详细地址
		
		List<Map<String, Object>> comList = new ArrayList<>();
		for (WaybilDetail wd : list) {
			Map<String, Object> commodity = new HashMap<>();
			commodity.put("cargoName", wd.getSku_name());////货物名称
			if(wd.getSku_name()==null||wd.getSku_name()==""){
				commodity.put("cargoName", "0");//件数
			}
			commodity.put("cargoType", wd.getSku_code());//货物型号
			if(wd.getSku_code()!=null){
				if(wd.getSku_code().equals(null)||wd.getSku_code().equals("")){
					commodity.put("cargoType", "0");//件数
				}
			}
			commodity.put("count", wd.getQty());//件数
			if(wd.getQty()==null){
				commodity.put("count", "1");//件数
			}
			
			commodity.put("bulk", wd.getVolumn());//体积
			if(wd.getVolumn()==null){
				commodity.put("bulk", "1");//体积
			}
			commodity.put("weight", wd.getWeight());//重量
			if(wd.getWeight()==null){
				commodity.put("weight", "1");//重量
			}
			//commodity.put("cargoClass", null);//货物类别
			commodity.put("cargoClass", "0");//货物类别
			comList.add(commodity);
		}
		//货物明细
		map.put("detaillist", comList);
		//发货人
		map.put("sendAddressInfo", sender);
		//收货人
		map.put("receiveAddressInfo", receiver);
		
		map.put("customerNm", customerNm); //客户编号
		map.put("stockNm", "001");//仓库编号
		map.put("shopCode", "001");//店铺编码
		map.put("stockPosition", "001");//仓位
		map.put("orderNo", master.getOrder_id());//订单号
		map.put("packageNo", master.getOrder_id());//包裹号
		map.put("omNumber", "0");//OM订单号
		map.put("transporter", "0"); //运输商编号
		map.put("waybillNo", "");//运单号
		if(master.getWaybill()!=null){
			map.put("waybillNo", master.getWaybill());//运单号
		}
		
		map.put("totalWeight", master.getTotal_weight());//总重量(Kg) 
		if(master.getTotal_weight()==null){
			map.put("totalWeight", "1");
		}
		//map.put("totalWeight", "0");//总重量(Kg) 
		map.put("totalBulk", master.getTotal_volumn()); //总体积(m³)
		if(master.getTotal_volumn()==null){
			map.put("totalBulk", "1");
		}
		//map.put("totalBulk", "0"); //总体积(m³)
		map.put("multi", "1-1");//标识是否一单多条
		map.put("remark", master.getMemo()); //运单备注信息
		map.put("status", falg);//订单在WMS系统的状态,0:进入云端, 1:发货, 2:作废
		
		return map;
	}
	
	public int queryOrdertest(WaybillMaster master) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("sign", "sign");
		
		JSONObject data = JSONObject.fromObject(dataMap);
		
		Map<String, Object> map = getHead(data.toString());
				
		String resultStr = HttpUtil.sendHttpRequest("http://127.0.0.1:8081/BT-OP/anbInterfance/doWaybill/getabhWaybillNum.do", map);
		
		System.out.println(resultStr);
		return 0;
	}
	
	
}
