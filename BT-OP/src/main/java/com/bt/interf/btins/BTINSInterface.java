package com.bt.interf.btins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.bt.orderPlatform.model.WaybilDetail;
import com.bt.orderPlatform.model.WaybillMaster;
import com.bt.orderPlatform.service.WaybillMasterService;

import net.sf.json.JSONObject;



/** 
	* @Description: 
	* @author  Hanery:
*/
@Service
public class BTINSInterface {

    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(BTINSInterface.class);
	
	@Resource(name = "waybillMasterServiceImpl")
	private WaybillMasterService<WaybillMaster> waybillMasterService;
	   //正式环境
	private final static String url = "http://10.7.46.46:8081/bt-ins-wms/insToWms/sendData.do";
	
	private static HttpClient client;
        
//	//测试环境
	//private final static String url = "http://10.88.26.50:8081/bt-ins-wms/insToWms/sendData.do";
	//本地测试环境
	//private final static String url = "http://10.8.35.31:8080/bt-ins-wms/insToWms/sendData.do";
	
	private Map<String, Object> transEntity(WaybillMaster master, List<WaybilDetail> list) {
		Map<String, Object> map = new HashMap<>();
		map.put("store_code", master.getStore_code());
		map.put("store_name", master.getStore_code());
		map.put("warehouse_code", master.getFrom_orgnization());
		map.put("warehouse_name", master.getFrom_orgnization());
		map.put("upper_id", master.getId());
		if(master.getExpressCode().equals("SFSM")){
			map.put("transport_code", "SF");
			map.put("transport_name", "顺丰快递");
		}else if(master.getExpressCode().equals("SF")){
			map.put("transport_code", "SF");
			map.put("transport_name", "顺丰快递");
		}
		map.put("delivery_order", master.getOrder_id());
		map.put("epistatic_order", master.getOrder_id());
		map.put("order_type", "销售单");
		map.put("express_number", master.getWaybill());
		if(master.getStore_code().equals("VF")){
			map.put("transport_direction", 0);
		}else{
			map.put("transport_direction", 1);
		}
		map.put("itemtype_code", master.getProducttype_code());
		map.put("itemtype_name", master.getProducttype_code());
		map.put("transport_time", master.getStart_date());
		map.put("collection_on_delivery", 0);
		map.put("delivery_address", master.getFrom_address());
		map.put("order_amount", master.getTotal_amount());
		map.put("sku_qty", master.getTotal_qty());
		map.put("qty", master.getTotal_qty());
		map.put("weight", 0);
		map.put("length", 0);
		map.put("width", 0);
		map.put("higth", 0);
		map.put("volumn", 0);
		map.put("province", master.getTo_province());
		map.put("city", master.getTo_city());
		map.put("state", master.getTo_state());
		map.put("province_origin", master.getFrom_province());
		map.put("city_origin", master.getFrom_city());
		map.put("state_origin", master.getFrom_state());
		map.put("insurance_flag", master.getAmount_flag());
		if(master.getPay_path()=="3"){
			//map.put("cod_flag",1);
		}else {
			
		}
		map.put("cod_flag", 0);
		map.put("shiptoname", master.getTo_contacts());
		map.put("phone", master.getTo_phone());
		map.put("address", master.getTo_address());
		map.put("express_time", master.getStart_date());
		map.put("check_time", master.getStart_date());
		map.put("weight_time", master.getStart_date());
		map.put("create_date", master.getCreate_time());
		map.put("k_flag", 0);
		map.put("platform_mark", "2");
		map.put("cost_center", master.getCost_center());
		if(master.getFrom_orgnization_code().equals("3")||master.getFrom_orgnization_code().equals("166")||master.getFrom_orgnization_code().equals("167")){
			map.put("settle_flag", 1);
		}else{
			map.put("settle_flag", 3);
		}
		
		
		List<Map<String, Object>> comList = new ArrayList<>();
		for (WaybilDetail wd : list) {
			Map<String, Object> commodity = new HashMap<>();
			commodity.put("express_number", master.getWaybill());
			if(wd.getSku_name()!=null){ 
				commodity.put("sku_number", wd.getSku_name());
			}else{
				commodity.put("sku_number", " ");
			}
			if(wd.getSku_code()!=null){
				commodity.put("barcode", wd.getSku_code());
			}else{
				commodity.put("barcode", "");
			}
			if(wd.getSku_code()!=null){
				commodity.put("item_name", wd.getSku_name());
			}else{
				commodity.put("item_name", "");
			}
			if(wd.getSku_code()!=null){
				commodity.put("qty", wd.getQty());
			}else{
				commodity.put("qty", 0);
			}
			
			
			comList.add(commodity);
		}
		map.put("order_details", comList);
		return map;
	}
	
	
	@SuppressWarnings("unchecked")
	public int insertByObj(WaybillMaster master, List<WaybilDetail> list) {
		Map<String, Object> dataMap = transEntity(master, list);
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		list1.add(dataMap);
		System.out.println(dataMap.toString());
		List<NameValuePair> nvps = new ArrayList<NameValuePair>(); 
        nvps.add(new BasicNameValuePair("contractCode","BZ1001"));  
        nvps.add(new BasicNameValuePair("systemKey","Lzdc0328"));
        nvps.add(new BasicNameValuePair("data",JSONArray.toJSONString(list1))); 
        Map<String,String> result=sendPost2(url,nvps);
        String map = result.get("linkInfo");
        Map<String,Object> json =JSONObject.fromObject(map);
        if(json!=null&&json.get("reStatus")!=null){
        	String reStatus= (String) json.get("reStatus");
        	if("1".equals(reStatus)){
        		//System.out.println("成功");
        		  return 1;
        	}
        }
    	System.out.println(result);
    	return 0;
	}
	
	public static Map<String,String> sendPost2(String url, List<NameValuePair> nvps){
    	String result=null;
    	Map<String,String> res=new HashMap<String,String>();
    	try {
    	    if (client==null){
    	        System.out.println("运单结算发送客户端初始化_");
    	        client = HttpClientBuilder.create().build();
            }
           
            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
            
//            HttpEntity entity = new StringEntity(body, "utf-8");
//            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
              String resEntityStr = EntityUtils.toString(response.getEntity());
              result=resEntityStr;
              res.put("linkFlag", "1");
              res.put("linkInfo",result);
            }else{
              res.put("linkFlag", "0");
              res.put("linkInfo","接口连接失败,错误码:"+response.getStatusLine().getStatusCode()+"|地址:"+url);
            }		
		} catch (Exception e) {
            res.put("linkFlag", "0");
            res.put("linkInfo",e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
		}
    	System.out.println(res);
        return res;
    }
}
