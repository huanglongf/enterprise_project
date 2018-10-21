package com.bt.interf.sf;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.bt.common.util.CommonUtil;
import com.bt.common.util.CommonUtils;
import com.bt.common.util.HttpHelper;
import com.bt.common.util.MD5Util;
import com.bt.common.util.XmlUtil;
import com.bt.orderPlatform.model.AddedService;
import com.bt.orderPlatform.model.InterfaceInformation;
import com.bt.orderPlatform.model.InterfaceRouteinfo;
import com.bt.orderPlatform.model.OrganizationInformation;
import com.bt.orderPlatform.model.SfOrderZDServiceRequestBean;
import com.bt.orderPlatform.model.SfexpressServiceRequestBean;
import com.bt.orderPlatform.model.SfexpressServiceRequestBodyBean;
import com.bt.orderPlatform.model.SfexpressServiceResponseBean;
import com.bt.orderPlatform.model.SfexpressServiceResponseOrderBean;
import com.bt.orderPlatform.model.SfexpressServiceResponseRouteBean;
import com.bt.orderPlatform.model.WaybillMaster;
import com.bt.orderPlatform.model.WaybillZd;
import com.bt.orderPlatform.service.InterfaceInformationService;
import com.bt.orderPlatform.service.InterfaceRouteinfoService;
import com.bt.orderPlatform.service.OrganizationInformationService;
import com.bt.orderPlatform.service.WaybillMasterService;
import com.bt.orderPlatform.service.WaybillZdService;

import net.sf.json.JSONObject;

@Service
public class SfInterface {
	
    private static final Logger logger = Logger.getLogger(SfInterface.class);
    
    /**SF子单获取服务名**/
    private final static String SF_ORDERZD_SERVICE = "OrderZDService";
    
    /**SF子单获取最大数量**/
    private final static int MAX_ZD_NUM = 20;
    
    
	/**
	 * 订单取消接口
	 * @param waybill
	 * @return
	 * @throws Exception 
	 */
	
	/**
	 * 运单信息主表服务类
	 */
	@Resource(name = "waybillMasterServiceImpl")
	private WaybillMasterService<WaybillMaster> waybillMasterService;
	@Resource(name = "waybillZdServiceImpl")
    private WaybillZdService<WaybillZd> waybillZdService;
	@Resource(name = "interfaceRouteinfoServiceImpl")
	private InterfaceRouteinfoService<InterfaceRouteinfo> interfaceRouteinfoService;
	@Resource(name = "organizationInformationServiceImpl")
	private OrganizationInformationService<OrganizationInformation> organizationInformationServiceImpl;
	@Resource(name = "interfaceInformationServiceImpl")
	private InterfaceInformationService<InterfaceInformation> interfaceInformationServiceImpl;
	
	public int  CancelOrder(WaybillMaster waybillmaster) throws Exception{
			String orderId=waybillmaster.getOrder_id();
			String id=waybillmaster.getId();
			InterfaceInformation interfaceInformation = interfaceInformationServiceImpl.selectByCustid(waybillmaster.getCustid());
		    String requestXml= getCancelStr(orderId,interfaceInformation.getAccessCode());
		    Object[] result = null;
			// 动态生成客户端
			try{
				Client client = HttpHelper.getClient();
				// 设置超时单位为毫秒
			    HTTPConduit http = (HTTPConduit) client.getConduit();
			    // 响应超时
			    http.setClient(HttpHelper.setHttpClientPolicy());	
		        result = client.invoke("sfexpressService", new Object[] {requestXml, CommonUtil.encodeBase64(MD5Util.md5Encrypt(requestXml +interfaceInformation.getSecretKey()))});
		       String returnString="";
		        if (result != null) {
		        	for (Object robj : result) {
		        		returnString = robj.toString();
		        	}
	          };
			if(returnString.contains("res_status='2'")||returnString.contains("res_status=\"2\"")){
				//更新运单状态
				/*WaybillMaster master =new WaybillMaster();
				//master.setOrder_id(orderId);
				master.setStatus("2");
				waybillMasterService.CancelOrder(master);*/	
				String status ="10";
				waybillMasterService.confirmOrdersById(id,status);
				return 1;	
			 }else{
				 String status ="10";
				waybillMasterService.confirmOrdersById(id,status);
				return 1;
			 }
			}catch(Exception e){
				String status ="10";
				waybillMasterService.confirmOrdersById(id,status);
				return 1;
			}
	}
	
	public String getCancelStr(String orderId,String accessCode){
		String str="<?xml version='1.0' encoding='UTF-8'?><Request service='OrderConfirmService' lang='zh-CN'><Head>"
				+ accessCode
				+ "</Head><Body><OrderConfirm orderid='"+orderId+"' dealtype='2' ></OrderConfirm></Body></Request>";
		return str;
	}
	
	public int placeOrder(WaybillMaster master){

	//	OrganizationInformation organizationInformation = organizationInformationServiceImpl.selectById(master.getFrom_orgnization_code());
		Map<String,Object> paramMap = new HashMap<String,Object>();
		/*else{
			if(organizationInformation.getPay_path()!=null){
				master.setPay_path(organizationInformation.getPay_path());
			}
		}*/
		if(!CommonUtils.checkExistOrNot(master.getTotal_weight())){
			master.setTotal_weight(new BigDecimal(1));
		}
		if(master.getAmount_flag()!=null){
			if(master.getAmount_flag().equals("1")){
				AddedService addedService =new AddedService();
				addedService.setName("INSURE");
				addedService.setValue(master.getInsured());
				master.setAddedService(addedService);
			}
		}
		
		/**TEST***/
//        master.setCustid("9999999999");
	    /**TEST***/
		InterfaceInformation interfaceInformation = interfaceInformationServiceImpl.selectByCustid(master.getCustid());
		
		if(CommonUtils.checkExistOrNot(master.getPay_path())){
            if(master.getPay_path().equals("1") || master.getPay_path().equals("2")){
                master.setCustid(null);
            }else if(master.getPay_path().equals("4")){
                master.setPay_path("1");
            }
        }
		
		SfexpressServiceRequestBean sfexpressServiceRequestBean = SfexpressServiceRequestBean.buildRequest(master,interfaceInformation.getAccessCode());
		
		String xml = XmlUtil.writeObjectToXml(SfexpressServiceRequestBean.class, sfexpressServiceRequestBean);
		logger.error(xml);
		//System.out.println(interfaceInformation.getSecretKey());
		String verifyCode = CommonUtil.encodeBase64(
				MD5Util.md5Encrypt(xml 
						+interfaceInformation.getSecretKey()));	
		paramMap.put("xml", xml);
		paramMap.put("verifyCode", verifyCode);
		
		SfexpressServiceResponseBean response = null;	
//		/*********************TEST*************************/
//        try {
//            response = HttpHelper.fetchObjectByPost(SfexpressServiceResponseBean.class,
//                          "http://bspoisp.sit.sf-express.com:11080/bsp-oisp/sfexpressService",
//                          paramMap);            
//        } catch (Exception e) {
//           e.printStackTrace();
//        }
//      /*********************TEST*************************/     
        
		 //拉取顺丰数据
        response = sendDataToPlaceOrder(paramMap, interfaceInformation, 5);
      
            if(response != null && response.isSuccess()){
                SfexpressServiceResponseOrderBean sfexpressServiceResponseOrderBean = 
                                response.getResponseBody().getResponseOrder();
                String[] mailnos = sfexpressServiceResponseOrderBean.getMailNos();
                if (mailnos==null){
                    return 0;
                }
                master.setWaybill(mailnos[0]);
                /**含有子单**/
                if(mailnos.length > 1){
                    saveMailZD(mailnos, sfexpressServiceResponseOrderBean.getOrderid()); 
                }
                updateWaybillMasterForSucc(master, sfexpressServiceResponseOrderBean);
                return 1;
            }else {
                if(response == null){
                    waybillMasterService.updateplaceError(master.getOrder_id(), "11","通讯异常下单失败");
                    return 0;
                } 
                JSONObject ret = JSONObject.fromObject(response);
               // System.out.println(ret);
                @SuppressWarnings("unchecked")
               Map<String, Object> map = (Map<String, Object>) ret.get("error");
                String placeError = (String) map.get("value");
                if(placeError!=null){
                    //下单失败更新状态和失败原因
                    waybillMasterService.updateplaceError(master.getOrder_id(), "11",placeError);
                    //System.out.println(placeError);
                }
               
               //下单失败
              // waybillMasterService.confirmOrdersById(id, stauts);
               return 0;
           }
	}
	
	/**
	 * sf获取路由信息接口
	 * @param waybill
	 * @return
	 * @throws Exception 
	 */	
	public void  getRoute(List<WaybillMaster> expressinfoMasters) throws Exception{
		getRequestXmls(expressinfoMasters);
	}
	
	public  void getRequestXmls( List<WaybillMaster> expressinfoMasters) throws Exception {
		int cycle = 0;
		Integer waybill_number = 0;
		InterfaceInformation interfaceInformation =null;
		if(expressinfoMasters.size()>0){
			if(expressinfoMasters.get(0).getCustid()!=null){
				interfaceInformation = interfaceInformationServiceImpl.selectByCustid(expressinfoMasters.get(0).getCustid());
			}
			
		}
		try{
			waybill_number = Integer.parseInt(CommonUtil.getConfig("waybill_number_sf"));
		} catch(Exception e) {
			if(e instanceof NumberFormatException) {
//				cycle = 1;
				System.out.println("发送请求内包含运单数填写异常！默认为1。");
			}
		}
		// 如果不是整数（包括负数）
		if(waybill_number < 1) {
			cycle = 1;
		} else if(waybill_number > 10) {
			cycle = 10;
		} else {
			cycle = waybill_number;
		}
		String accessCode =null;
		String secretKey =null;
		if(interfaceInformation!=null){
			accessCode = interfaceInformation.getAccessCode();
			secretKey=interfaceInformation.getSecretKey();
		}
		String initailHead = 
				"<?xml version='1.0' encoding='UTF-8'?><Request service='RouteService' lang='zh-CN'><Head>"
				+ accessCode
				+ "</Head><Body><RouteRequest tracking_type='"
				+ CommonUtil.getConfig("tracking_type_sf")
				+ "' method_type='"
				+ CommonUtil.getConfig("method_type_sf")
				+ "' tracking_number='";
		List<String> requestXmls = new ArrayList<String>();
		StringBuffer requestXml = null;
		
		for(int i = 0; i < expressinfoMasters.size(); i++) {
			if(i%cycle == 0){
				if(i != 0){
					requestXml.append("' /></Body></Request>");
					requestXmls.add(requestXml.toString());
				}
				requestXml = new StringBuffer(initailHead);
			} else {
				requestXml.append(",");
			}
			String waybill=  expressinfoMasters.get(i).getWaybill();
			requestXml.append(waybill);
			if(i + 1 == expressinfoMasters.size()) {
				requestXml.append("' /></Body></Request>");
				requestXmls.add(requestXml.toString());
			}
		}
		// 读取配置文件
		try{
			/*System.out.println(requestXmls);*/
			if("Mg6zqXQtmYNZj6x6KN6YfdES2678rXkb".equals(secretKey)){
				String xmls[]=doPost(requestXmls,secretKey);
				for(String str:xmls){parseResponse(str);
				/*System.out.println(str);*/
				};
			}else{
				post(requestXmls,secretKey);
			}
			
		}catch(Exception e){
			//e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	public  String getRequestXmlsTest( List<WaybillMaster> expressinfoMasters) throws Exception {
		int cycle = 0;
		String  v="";
		Integer waybill_number = 0;
		InterfaceInformation interfaceInformation =null;
		if(expressinfoMasters.size()>0){
			if(expressinfoMasters.get(0).getCustid()!=null){
				interfaceInformation = interfaceInformationServiceImpl.selectByCustid(expressinfoMasters.get(0).getCustid());
			}
			
		}
		try{
			waybill_number = Integer.parseInt(CommonUtil.getConfig("waybill_number_sf"));
		} catch(Exception e) {
			if(e instanceof NumberFormatException) {
//				cycle = 1;
				System.out.println("发送请求内包含运单数填写异常！默认为1。");
			}
		}
		// 如果不是整数（包括负数）
		if(waybill_number < 1) {
			cycle = 1;
		} else if(waybill_number > 10) {
			cycle = 10;
		} else {
			cycle = waybill_number;
		}
		String accessCode =null;
		String secretKey =null;
		if(interfaceInformation!=null){
			accessCode = interfaceInformation.getAccessCode();
			secretKey=interfaceInformation.getSecretKey();
		}
		String initailHead = 
				"<?xml version='1.0' encoding='UTF-8'?><Request service='RouteService' lang='zh-CN'><Head>"
				+ accessCode
				+ "</Head><Body><RouteRequest tracking_type='"
				+ CommonUtil.getConfig("tracking_type_sf")
				+ "' method_type='"
				+ CommonUtil.getConfig("method_type_sf")
				+ "' tracking_number='";
		List<String> requestXmls = new ArrayList<String>();
		StringBuffer requestXml = null;
		
		for(int i = 0; i < expressinfoMasters.size(); i++) {
			if(i%cycle == 0){
				if(i != 0){
					requestXml.append("' /></Body></Request>");
					requestXmls.add(requestXml.toString());
				}
				requestXml = new StringBuffer(initailHead);
			} else {
				requestXml.append(",");
			}
			String waybill=  expressinfoMasters.get(i).getWaybill();
			requestXml.append(waybill);
			if(i + 1 == expressinfoMasters.size()) {
				requestXml.append("' /></Body></Request>");
				requestXmls.add(requestXml.toString());
			}
		}
		// 读取配置文件
		try{
			/*System.out.println(requestXmls);*/
			if("Mg6zqXQtmYNZj6x6KN6YfdES2678rXkb".equals(secretKey)){
				v=doPostTest(requestXmls,secretKey);
			}else{
				v=postTest(requestXmls,secretKey).toString();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return v;
	}
	
	
	public  String[]  doPost(List<String> requestXmls,String secretKey) throws Exception {

		String[] resultStr = new String [requestXmls.size()];
		Object[] result = null;
		Client client = HttpHelper.getClient();
		HTTPConduit http = (HTTPConduit) client.getConduit();
	    // 响应超时
	    http.setClient(HttpHelper.setHttpClientPolicy());
		for(int i = 0; i < requestXmls.size(); i++) {
	        result = client.invoke("sfexpressService", new Object[] {requestXmls.get(i).toString(), CommonUtil.encodeBase64(MD5Util.md5Encrypt(requestXmls.get(i).toString() + secretKey))});		   
	        resultStr[i]=result[0].toString();
        }
		http.close();
		return resultStr;
	}
	
	public  String  doPostTest(List<String> requestXmls,String secretKey) throws Exception {
		List<String> results = new ArrayList<String>();
		String[] resultStr = new String [requestXmls.size()];
		Object[] result = null;
		String str="";
		// 生成JAX-WS动态客户端工厂类实体
		JaxWsDynamicClientFactory jDCF = JaxWsDynamicClientFactory.newInstance();
		// 动态生成客户端
		Client client = jDCF.createClient(CommonUtil.getConfig("wsdl_url_sf"));
		// 设置超时单位为毫秒
	    HTTPConduit http = (HTTPConduit) client.getConduit();
	    // 响应超时
	    http.setClient(HttpHelper.setHttpClientPolicy());

	    for(int i = 0; i < requestXmls.size(); i++) {
		        result = client.invoke("sfexpressService", new Object[] {requestXmls.get(i).toString(), CommonUtil.encodeBase64(MD5Util.md5Encrypt(requestXmls.get(i).toString() + secretKey))});		   
		        str=str+result[0].toString();
	    }
        System.out.println("第一种："+resultStr[0]);
	    return str;
	}
	
	
	public void parseResponse(String response) throws Exception {
		Date d=new Date();
		String waybill="";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			if (!"".equals(response)&&response!=null) {
				    List<InterfaceRouteinfo>  listInterface = new ArrayList<InterfaceRouteinfo>();      
					org.dom4j.Document document = DocumentHelper.parseText(response); 
					Element root = document.getRootElement();  
					//Element bodyElement = root.element("Body").element("RouteResponse").element("Route");
					List<Element> list = root.element("Body").element("RouteResponse").elements();
					for(Element ele : list){
						String remark =  ele.attributeValue("remark");
						String acceptTime =  ele.attributeValue("accept_time");
						String acceptAddress =  ele.attributeValue("accept_address");
						String opcode =  ele.attributeValue("opcode");
						//获取运单号
						String  mailno = root.element("Body").element("RouteResponse").attributeValue("mailno");
						waybill=mailno;
						InterfaceRouteinfo interfaceRouteinfo = new InterfaceRouteinfo();
						interfaceRouteinfo.setCreate_time(new Date());
						interfaceRouteinfo.setCreate_user("system");
						interfaceRouteinfo.setUpdate_time(new Date());
						interfaceRouteinfo.setUpdate_user("system");
						interfaceRouteinfo.setTransport_code("SF");
						interfaceRouteinfo.setWaybill(mailno);
						interfaceRouteinfo.setRoute_time(df.parse(acceptTime));
						interfaceRouteinfo.setRoute_city(acceptAddress);
						interfaceRouteinfo.setFacility_no("");
						interfaceRouteinfo.setFacility_name("");
						interfaceRouteinfo.setRoute_remark(remark);
						interfaceRouteinfo.setRoute_opcode(opcode);
						interfaceRouteinfo.setFlag(0);
			        	try{
			        		interfaceRouteinfoService.insertByObj(interfaceRouteinfo);
			        	}catch(Exception e){}
					}
				}
		} catch (Exception e) {
		}
	}
	
	public  String[]  post(List<String> requestXmls,String secretKey) throws Exception {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		StringBuilder csvBuilder = new StringBuilder();
		  for(String city : requestXmls){
		    csvBuilder.append(city);
		  }
		  String csv = csvBuilder.toString();
		String verifyCode =CommonUtil.encodeBase64(MD5Util.md5Encrypt(csv +secretKey));
		paramMap.put("xml", csv);
	//	System.out.println(csv);
		paramMap.put("verifyCode", verifyCode);
		DefaultHttpClient httpClient = null;  
        try {  
        	SfexpressServiceResponseBean response = null;
    		try {
    		    for (int i = 0; i < 5; i++){
    		        try{
                        response = HttpHelper.fetchObjectByPost(SfexpressServiceResponseBean.class,
                                        CommonUtil.getConfig("wsdl_url_sf_http"),
                                        paramMap);
                        break;
                    }catch (Exception e){
                       logger.error(e.getMessage());
                    }
                }

    			if(response != null && response.getHead() != null && response.getHead().equals("OK")){
    				String mailno = response.getResponseBody().getRouteResponse().getMailno();
    				List<SfexpressServiceResponseRouteBean> route = response.getResponseBody().getRouteResponse().getRoute();
    				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    				for (SfexpressServiceResponseRouteBean sfexpressServiceResponseRouteBean : route) {
						InterfaceRouteinfo interfaceRouteinfo = new InterfaceRouteinfo();
						interfaceRouteinfo.setCreate_time(new Date());
						interfaceRouteinfo.setCreate_user("system");
						interfaceRouteinfo.setUpdate_time(new Date());
						interfaceRouteinfo.setUpdate_user("system");
						interfaceRouteinfo.setTransport_code("SF");
						interfaceRouteinfo.setWaybill(mailno);
						interfaceRouteinfo.setRoute_time(df.parse(sfexpressServiceResponseRouteBean.getAccept_time()));
						interfaceRouteinfo.setRoute_city(sfexpressServiceResponseRouteBean.getAccept_address());
						interfaceRouteinfo.setFacility_no("");
						interfaceRouteinfo.setFacility_name("");
						interfaceRouteinfo.setRoute_remark(sfexpressServiceResponseRouteBean.getRemark());
						interfaceRouteinfo.setRoute_opcode(sfexpressServiceResponseRouteBean.getOpcode());
						interfaceRouteinfo.setFlag(0);
			        	try{
			        		interfaceRouteinfoService.insertByObj(interfaceRouteinfo);
			        	}catch(Exception e){}
					}
    			}
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			//e1.printStackTrace();
    		}
              
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        
        
		return null;
	}
	

	/***
	 * 
	 * <b>方法名：</b>：orderZDservice<br>
	 * <b>功能说明：</b>：下单后获取子单运单号接口<br>
	 * @author <font color='blue'>chenkun</font> 
	 * @date  2018年3月22日 下午5:41:08
	 * @param master 母单
	 * @param num  新增的包裹数目（不包括母单） 
	 * @return 子单运单号数组
	 */
	public SfexpressServiceResponseBean orderZDservice(WaybillMaster master ,int num){
	    
	    if(num >MAX_ZD_NUM){
	        throw new RuntimeException("子母单包裹数目不能大于20  num："+num+";母订单号："+master.getOrder_id());
	    }
	    
	    InterfaceInformation interfaceInformation = interfaceInformationServiceImpl.selectByCustid(master.getCustid());
        
	    SfexpressServiceRequestBean sfexpressServiceRequestBean = SfexpressServiceRequestBean.initRequest(interfaceInformation.getAccessCode());
        
        //请求参数
	    SfexpressServiceRequestBodyBean requestBodyBean = sfexpressServiceRequestBean.getBody();
	    SfOrderZDServiceRequestBean orderZD = new SfOrderZDServiceRequestBean();
        orderZD.setOrderid(master.getOrder_id());
        orderZD.setParcel_quantity(num);
        
        requestBodyBean.setOrderZDServiceRequestBean(orderZD); 
        
        //设置字母单服务接口名
        sfexpressServiceRequestBean.setService(SF_ORDERZD_SERVICE);
        
        String xml = XmlUtil.writeObjectToXml(SfexpressServiceRequestBean.class, sfexpressServiceRequestBean);
        
        logger.warn(xml);
        
        String verifyCode = CommonUtil.encodeBase64(
                MD5Util.md5Encrypt(xml + interfaceInformation.getSecretKey()));

        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("xml", xml);
        paramMap.put("verifyCode", verifyCode);
        
        SfexpressServiceResponseBean response = null;
        
        try {
            /******************************test***********************/
            response = HttpHelper.fetchObjectByPostForSFOrderZD(SfexpressServiceResponseBean.class,
                          "http://bspoisp.sit.sf-express.com:11080/bsp-oisp/sfexpressService",
                          paramMap);  
            /******************************test***********************/
            
            if(interfaceInformation.getCustid().equals("0217928502")){
                response = HttpHelper.fetchObjectByPost(SfexpressServiceResponseBean.class,
                        CommonUtil.getConfig("bz_wsdl_url_sf_http"),
                        paramMap);
            }else{
                response = HttpHelper.fetchObjectByPost(SfexpressServiceResponseBean.class,
                        CommonUtil.getConfig("wsdl_url_sf_http"),
                        paramMap);
            }
            
        } catch (Exception e) {
           logger.error(e);
           throw new RuntimeException(e.getMessage());
        }
        return response;
	}
	
	/***
	 * 
	 * <b>方法名：</b>：updateWaybillMasterForSucc<br>
	 * <b>功能说明：</b>：下单成功后更新订单状态<br>
	 * @author <font color='blue'>chenkun</font> 
	 * @date  2018年3月23日 上午11:45:03
	 * @param master
	 * @param sfexpressServiceResponseOrderBean
	 */
	private void updateWaybillMasterForSucc(WaybillMaster master, SfexpressServiceResponseOrderBean sfexpressServiceResponseOrderBean){
	    
        Date now = new Date();
        master.setOrder_id(sfexpressServiceResponseOrderBean.getOrderid());
        master.setMark_destination(sfexpressServiceResponseOrderBean.getDestcode());
        master.setUpdate_time(now);
        master.setOrder_time(now);
        master.setStatus("2");
        waybillMasterService.updateByMaster(master);
	}
	
	/***
	 * 
	 * <b>方法名：</b>：saveMailZD<br>
	 * <b>功能说明：</b>：子运单存档<br>
	 * @author <font color='blue'>chenkun</font> 
	 * @date  2018年3月23日 上午11:45:28
	 * @param mailnos
	 * @param orderid
	 */
	private void saveMailZD(String[] mailnos,String orderid){
	    
	    WaybillZd waybillZd = new WaybillZd();
        waybillZd.setWaybill(mailnos[0]);
        waybillZd.setOrderId(orderid);
        waybillZd.setTotal(mailnos.length);
        
        for(int i = 1; i<mailnos.length; i++){
            waybillZd.setMailZdno(mailnos[i]);
            waybillZd.setOrdinal(i+1);
            waybillZdService.insert(waybillZd); 
        }
	}
	
	public  String  postTest(List<String> requestXmls,String secretKey) throws Exception {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		StringBuilder csvBuilder = new StringBuilder();
		  for(String city : requestXmls){
		    csvBuilder.append(city);
		  }
		  String csv = csvBuilder.toString();
		String verifyCode =CommonUtil.encodeBase64(MD5Util.md5Encrypt(csv +secretKey));
		paramMap.put("xml", csv);
		System.out.println(csv);
		paramMap.put("verifyCode", verifyCode);
		DefaultHttpClient httpClient = null;  
		String routeV="";
        try {  
        	SfexpressServiceResponseBean response = null;
    		try {
    			routeV = HttpHelper.fetchObjectByPostTest(
						CommonUtil.getConfig("wsdl_url_sf_http"),
						paramMap);
    			return routeV;
    		} catch (Exception e1) {
    			
    			e1.printStackTrace();
    		}
              
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        
        
		return null;
	}
	
	/**
     * 顺丰状态数据拉取
     * 
     * @param paramMap
     * @param interfaceInformation
     * @param num
     * @return
     */
    public SfexpressServiceResponseBean sendDataToPlaceOrder(Map<String, Object> paramMap,InterfaceInformation interfaceInformation,int num){
        SfexpressServiceResponseBean response = null;
       
            //循环5次拉取顺丰数据
            for (int i = 0; i < num; i++){
                try{
                    if (interfaceInformation.getCustid().equals("0217928502")){
                        response = HttpHelper.fetchObjectByPost(SfexpressServiceResponseBean.class, CommonUtil.getConfig("bz_wsdl_url_sf_http"), paramMap);
                    }else{
                        response = HttpHelper.fetchObjectByPost(SfexpressServiceResponseBean.class, CommonUtil.getConfig("wsdl_url_sf_http"), paramMap);
                    }
                    if (response != null)
                        break;
                }catch (Exception e){
                   // Log.error("拉取数据失败：", e);
                    logger.error(e.getMessage());
                }
            }
        return response;
    }
	
}
