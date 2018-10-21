package com.bt.interf.ems;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.bt.orderPlatform.model.WaybilDetail;
import com.bt.orderPlatform.model.WaybillMaster;
import com.bt.orderPlatform.service.WaybillMasterService;








/** 
	* @Description: 
	* @author  Hanery:
 	* @date 2017年7月17日 上午10:31:53  
*/
@Service
public class EMSInterface {

	
	@Resource(name = "waybillMasterServiceImpl")
	private WaybillMasterService<WaybillMaster> waybillMasterService;
	
	private final static String url = "http://111.200.216.140:9999/esb/api/webservice/baozun/bztolms";
	
	public int createOrder(WaybillMaster master , List<WaybilDetail> list) throws Exception {
		String actionCode = "10";
		Map<String, Object> map = getMap(master, list, actionCode);
		String mapToXML = callMapToXML(map);
		
		DefaultHttpClient httpClient = null;  
        try {  
            httpClient = new DefaultHttpClient();  
            //webservice服务地址  
            HttpPost httppost = new HttpPost(url); 
            //String soapRequestData = getRequestXml(); //soap协议的格式，定义了方法和参数  
            HttpEntity re = new StringEntity(mapToXML,HTTP.UTF_8);  
            httppost.setHeader("Content-Type","application/soap+xml; charset=utf-8");    
            httppost.setEntity(re);   
            HttpResponse response = httpClient.execute(httppost); //调用接口  
            //输出的xml  
            //返回是否成功的状态  
            String xmlString = EntityUtils.toString(response.getEntity());
            if(response.getStatusLine().getStatusCode() == 200) {  
            	Map<String,Object> map1 = new HashMap<String,Object>();  
                Map<String,Object> map2 = new HashMap<String,Object>();  
                Document doc;  
                    doc = DocumentHelper.parseText(xmlString);  
                    Element el = doc.getRootElement();  
                    map1 = recGetXmlElementValue(el,map1);  
                    doc = DocumentHelper.parseText(map1.get("out").toString());  
                    el = doc.getRootElement();
                    map2 = recGetXmlElementValue(el,map2);
                    if(!map2.get("RspCode").equals("") || map2.get("RspCode").equals("0000")){
                    	master.setWaybill(map2.get("EmsNo").toString());
        				Date now = new Date();
        				master.setOrder_id(map2.get("BusBillCOde").toString());
        				master.setUpdate_time(now);
        				master.setOrder_time(now);
        				master.setStatus("2");
        				waybillMasterService.updateByMaster(master);
                    }
            }  
              
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            httpClient.getConnectionManager().shutdown(); //关闭连接  
        }  
		
		return 0;
	}
	
	public int CancelOrder(WaybillMaster master , List<WaybilDetail> list) throws Exception {
		String actionCode = "30";
		Map<String, Object> map = getMap(master, list, actionCode);
		String mapToXML = callMapToXML(map);
		
		DefaultHttpClient httpClient = null;  
        try {  
            httpClient = new DefaultHttpClient();  
            //webservice服务地址  
            HttpPost httppost = new HttpPost(url); 
            //String soapRequestData = getRequestXml(); //soap协议的格式，定义了方法和参数  
            HttpEntity re = new StringEntity(mapToXML,HTTP.UTF_8);  
            httppost.setHeader("Content-Type","application/soap+xml; charset=utf-8");    
            httppost.setEntity(re);   
            System.out.println(re);
            HttpResponse response = httpClient.execute(httppost); //调用接口  
            //输出的xml  
            //返回是否成功的状态  
            String xmlString = EntityUtils.toString(response.getEntity());
            System.out.println(response.getStatusLine().getStatusCode());
            System.out.println(xmlString);
            if(response.getStatusLine().getStatusCode() == 200) {  
            	Map<String,Object> map1 = new HashMap<String,Object>();  
                Map<String,Object> map2 = new HashMap<String,Object>();  
                Document doc;  
                    doc = DocumentHelper.parseText(xmlString);  
                    Element el = doc.getRootElement();  
                    map1 = recGetXmlElementValue(el,map1);  
                    doc = DocumentHelper.parseText(map1.get("out").toString());  
                    el = doc.getRootElement();
                    map2 = recGetXmlElementValue(el,map2);
                    if(!map2.get("RspCode").equals("") || map2.get("RspCode").equals("0000")){
        				String status ="10";
        				waybillMasterService.setstatus(master.getOrder_id(),status);
                    }
            }  
              
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            httpClient.getConnectionManager().shutdown(); //关闭连接  
        }  
		
		return 0;
	}
	
	
	
	
	
	public static String callMapToXML(Map<String, Object> map) {  
        StringBuffer sb = new StringBuffer();  
        SimpleDateFormat da=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       // sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><ContractRoot>");  
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope "
        		+ "xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
        		+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\""
        		+ " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
        sb.append("<soapenv:Body><exchange xmlns=\"http://webservices.cmeb.itf.scm.org/\">"
        		+ "<inputXml xmlns=\"\">");
        sb.append("<![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        		+ "<ContractRoot>"
        		+ "<TcpCont>"
        		+ "<ServiceCode>pickuporder</ServiceCode>"
        		+ "<TransactionID>03b8d8ad40734403a99c68596eb98f87</TransactionID>"
        		+ "<SrcSysID>OMS0000007</SrcSysID>"
        		+ "<RecSysID>OMS0000001</RecSysID>"
        		+ "<ReqTime>"+da.format(new Date())+"</ReqTime>"
        		+ "</TcpCont>"
        		+ "<SvcCont>");
        mapToXMLTest2(map, sb);  
       // sb.append("</ContractRoot>");  
        sb.append("</SvcCont>"
        		+ "</ContractRoot>]]>"
        		+ "</inputXml>"
        		+ "</exchange>"
        		+ "</soapenv:Body>"
        		+ "</soapenv:Envelope>");  
        try {  
            return sb.toString();  
        } catch (Exception e) {  
        }  
        return null;  
    }  
  
    private static void mapToXMLTest2(Map<String, Object> map, StringBuffer sb) {  
        Set set = map.keySet();  
        for (Iterator it = set.iterator(); it.hasNext();) {  
            String key = (String) it.next();  
            Object value = map.get(key);  
            if (null == value)  
                value = "";  
            if (value.getClass().getName().equals("java.util.ArrayList")) {  
                ArrayList list = (ArrayList) map.get(key);  
                for (int i = 0; i < list.size(); i++) {  
                	sb.append("<" + key + ">");  
                    HashMap hm = (HashMap) list.get(i);  
                    mapToXMLTest2(hm, sb);  
                    sb.append("</" + key + ">"); 
                }  
  
            } else { 
                if (value instanceof HashMap) {  
                    sb.append("<" + key + ">");  
                    mapToXMLTest2((HashMap) value, sb);  
                    sb.append("</" + key + ">");  
                } else {  
                    sb.append("<" + key + ">" + value + "</" + key + ">");  
                }  
  
            }  
  
        }  
    } 
    
    public Map<String, Object> getMap(WaybillMaster master , List<WaybilDetail> list,String actionCode){
    	SimpleDateFormat da=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("SvcCont", "");
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("OrderCode", master.getOrder_id()+"1");
		//动作标识   新增=10
		map1.put("ActionCode", actionCode);
		//业务类型
		map1.put("BusType", "10");
		map1.put("BusBillCOde", master.getOrder_id());
		
		map1.put("ExternalOrderCode", "");
		//发货交付方式：01派送
		map1.put("DeliveryType", "01");
		//预定发货日期
		map1.put("PlanShippingDate", da.format(new Date()));
		map1.put("CustCode", "KH051");
		map1.put("StartOrgCode", "");
		map1.put("StartProjectCode", "");
		map1.put("StartOrgPropertyCode", "");
		map1.put("ReceCode", "");
		//发货联系电话
		map1.put("StartOrgTel", master.getFrom_phone());
		//发货联系人
		map1.put("StartOrgAttn", master.getFrom_contacts());
		//发货地址
		map1.put("StartOrgAddress", master.getFrom_address());
		//发货省市区县code
		map1.put("StartCityCode", master.getFrom_state_code());
		//发货省code
		map1.put("StartProvinceCode", master.getFrom_province_code());
		
		map1.put("EndProjectCode", "");
		map1.put("EndOrgPropertyCode", "");
		//收货省code
		map1.put("EndProvinceCode", master.getTo_province_code());
		//收货省市区县code
		map1.put("EndCityCode", master.getTo_state_code());
		map1.put("FactoryCode", "");
		//收货地址
		map1.put("EngOrgAddress", master.getTo_address());
		//收货联系人
		map1.put("EngOrgAttn", master.getTo_contacts());
		//收货联系电话
		map1.put("EngOrgTel", master.getTo_phone());
		//接口时间
		
		map1.put("OrderDate", da.format(new Date()));
		//是否补货标志：0否 1是
		map1.put("ReSendFlag", "0");
		
		map1.put("DOCode", "");
		map1.put("POCode", "");
		map1.put("Tokubeitu", "");
		//描述
		map1.put("remark", "");
		map1.put("Def1", "");
		map1.put("Def2", "");
		map1.put("Def3", "");
		map1.put("Def4", "");
		map1.put("Def5", "");
		map1.put("Def6", "");
		map1.put("Def7", "");
		map1.put("Def8", "");
		map1.put("Def9", "");
		map1.put("Def10", "");
		map1.put("Def11", "");
		map1.put("Def12", "");
		List<Map<String, Object>> comList = new ArrayList<>();
		for (int i =0;i<list.size() ;i++) {
			WaybilDetail waybilDetail = list.get(i);
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("DetailCode", waybilDetail.getId()+"1");
			//商品编码
			map2.put("ProductCode", "BZPT_0000");
			//品次
			map2.put("QualityType", "");
			//数量
			map2.put("Quantity", waybilDetail.getQty());
			map2.put("SerialCount", "");
			//备注
			map2.put("Comments", "");
			map2.put("Def1", "");
			map2.put("Def2", "");
			map2.put("Def3", "");
			map2.put("Def4", "");
			map2.put("Def5", "");
			Map<String, Object> map3 = new HashMap<String, Object>();
			map3.put("SerialCode", "");
			map3.put("BoxCode", "");
			map3.put("Def1", "");
			map3.put("Def2", "");
			map3.put("Def3", "");
			map3.put("Def4", "");
			map3.put("Def5", "");
			map2.put("InvoiceItems", map3);
			comList.add(map2);
		}
		map1.put("Items", comList);
		map.put("OrderInfo", map1);
		return map;
    }
    
    private static Map<String, Object> recGetXmlElementValue(Element ele, Map<String, Object> map){  
        List<Element> eleList = ele.elements();  
        if (eleList.size() == 0) {  
            map.put(ele.getName(), ele.getTextTrim());  
            return map;  
        } else {  
            for (Iterator<Element> iter = eleList.iterator(); iter.hasNext();) {  
                Element innerEle = iter.next();  
                recGetXmlElementValue(innerEle, map);  
            }  
            return map;  
        }  
    }  
}
