package com.bt.lmis.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bt.OSinfo;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.model.Client;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.model.OperationSettlementRule;
import com.bt.lmis.model.PerationfeeDataSettlement;
import com.bt.lmis.model.StorageDataGroup;
import com.bt.lmis.model.Store;
import com.bt.lmis.model.Summary;
import com.bt.lmis.model.ValueAddedServiceDetail;
import com.bt.lmis.service.CarrierUsedBalanceService;
import com.bt.lmis.service.ClientService;
import com.bt.lmis.service.ContractBasicinfoService;
import com.bt.lmis.service.ExpressUsedBalanceService;
import com.bt.lmis.service.OperationSettlementRuleService;
import com.bt.lmis.service.PerationfeeDataSettlementService;
import com.bt.lmis.service.StorageDataGroupService;
import com.bt.lmis.service.StoreService;
import com.bt.lmis.service.SummaryService;
import com.bt.lmis.service.ValueAddedServiceDetailService;
import com.bt.utils.BaseConst;
import com.bt.utils.BigExcelExport;
import com.bt.utils.CommonUtils;
import com.bt.utils.DateUtil;
import com.bt.utils.IntervalValidationUtil;

/** 
* @ClassName: SummaryController 
* @Description: TODO(汇总记录表) 
* @author Yuriy.Jiang
* @date 2016年7月11日 下午7:06:06 
*  
*/
@Controller
@RequestMapping("/control/summaryController")
public class SummaryController extends BaseController{

	private static final Logger logger = Logger.getLogger(SummaryController.class);
	
	@Resource(name = "carrierUsedBalanceServiceImpl")
	private CarrierUsedBalanceService carrierUsedBalanceService;
	
	@Resource(name = "expressUsedBalanceServiceImpl")
	private ExpressUsedBalanceService expressUsedBalanceService;
	
	/**
	 * 汇总表服务类
	 */
	@Resource(name = "summaryServiceImpl")
	private SummaryService<Summary> summaryService;
	
	@Resource(name = "clientServiceImpl")
	private ClientService<Client> clientService;

	@Resource(name = "storeServiceImpl")
	private StoreService<Store> storeService;

	@Resource(name = "contractBasicinfoServiceImpl")
	private ContractBasicinfoService<ContractBasicinfo> contractBasicinfoService;

	/**
	 * 仓储费明细汇总表服务类
	 */
	@Resource(name = "storageDataGroupServiceImpl")
	private StorageDataGroupService<StorageDataGroup> storageDataGroupService;


	@Resource(name = "operationSettlementRuleServiceImpl")
	private OperationSettlementRuleService<OperationSettlementRule> operationSettlementRuleService;
	
	@Resource(name = "valueAddedServiceDetailServiceImpl")
	private ValueAddedServiceDetailService<ValueAddedServiceDetail> valueAddedServiceDetailService;

	@Resource(name = "perationfeeDataSettlementServiceImpl")
	private PerationfeeDataSettlementService<PerationfeeDataSettlement> perationfeeDataSettlementService;
	
	/**
	 * 
	 * @Description: TODO(客户结算报表导出)
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年4月1日下午1:29:20
	 */
	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out= null;
		JSONObject result= new JSONObject();
		try {
			String fileName= request.getParameter("year") + "年"+ request.getParameter("month")+ "月客户结算报表.zip";
			if(!new File(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname()) + fileName).exists()){
				result.put("result_code", "FAILURE");
				result.put("result_content", "账单不存在！");
				
			} else {
				result.put("result_code", "SUCCESS");
				result.put("result_content", CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP") + fileName);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result= new JSONObject();
			result.put("result_code", "ERROR");
			result.put("result_content", "操作失败,失败原因:"+ e.getMessage());
			
		}
		try {
			out= response.getWriter();
			out.write(result.toString());
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
			
		} finally {
			out.close();
			
		}
		
	}
	
	/** 
	* @Title: list 
	* @Description: TODO(汇总年份列表) 
	* @param @param map
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping("/list")
	public String list(ModelMap map, HttpServletRequest request){
		try{
			int year = DateUtil.getYear(new Date());
			List<Map<String, Integer>> years = new ArrayList<>();
			for (int i = year ; i >= 2016; i--) {
				Map<String, Integer> yearMap = new HashMap<String, Integer>();
				yearMap.put("years", i);
				years.add(yearMap);
			}
			map.addAttribute("years",years);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
		}
		return "/lmis/summary_list";
	}

	/** 
	* @Title: lists 
	* @Description: TODO(汇总客户列表) 
	* @param @param map
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping("/lists")
	public String lists(ModelMap map, HttpServletRequest request){
		try{
			String clientname = request.getParameter("clientname");
			if(null!=clientname){
				Client client = new Client();
				client.setClient_name(clientname);
				List<Client> clients = clientService.findByClient(client);
				map.addAttribute("client",clients);
			}else{
				List<Map<String, Object>> client = clientService.findAll();
				map.addAttribute("client",client);
			}
			String ym = request.getParameter("ym");
			map.addAttribute("ym",ym);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
		}
		return "/lmis/summary_client_list";
	}

	/** 
	* @Title: summaryList 
	* @Description: TODO(周期－客户结算明细) 
	* @param @param map
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping("/summaryList")
	public String summaryList(ModelMap map, HttpServletRequest request){
		String con_id= "";
		String ym = request.getParameter("ym");
		//周期 如2016-09
		map.addAttribute("ym", ym);
		if(null!=request.getParameter("clientid") && !request.getParameter("clientid").equals("")){
			Integer clientid = Integer.valueOf(request.getParameter("clientid"));
			Client client = new Client();
			client.setId(clientid);
			List<Client> cList= clientService.findByClient(client);
			con_id= contractBasicinfoService.CsToCBID(cList.get(0).getClient_code()).size() == 0 ? "" : contractBasicinfoService.CsToCBID(cList.get(0).getClient_code()).get(0).getId();
			//客户信息
			map.addAttribute("client", cList.get(0));
			map.addAttribute("clientid", request.getParameter("clientid"));
		}else if(null!=request.getParameter("storeid") && !request.getParameter("storeid").equals("")){
			try {
				Integer storeid= Integer.valueOf(request.getParameter("storeid"));
				Store store= storeService.selectById(storeid);
				con_id= contractBasicinfoService.CsToCBID(store.getStore_code()).size()==0?"":contractBasicinfoService.CsToCBID(store.getStore_code()).get(0).getId();
				map.addAttribute("storeid", request.getParameter("storeid"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(!con_id.equals("")){
			//1.操作费
			PerationfeeDataSettlement dataSettlement = new PerationfeeDataSettlement();
			dataSettlement.setSettle_period(ym);
			dataSettlement.setContract_id(Integer.valueOf(con_id));
			List<PerationfeeDataSettlement> plist =  perationfeeDataSettlementService.findByList(dataSettlement);
			if(plist.size()!=0){
				if(plist.get(0).getBtb_fee().compareTo(new BigDecimal(0.00))!=0 ||
					plist.get(0).getBtc_fee().compareTo(new BigDecimal(0.00))!=0 ||
					plist.get(0).getReturn_fee().compareTo(new BigDecimal(0.00))!=0 ||
					plist.get(0).getIb_fee().compareTo(new BigDecimal(0.00))!=0 ){
					BigDecimal sjsy_fee = plist.get(0).getBtb_fee().add(plist.get(0).getBtc_fee()).add(plist.get(0).getReturn_fee()).add(plist.get(0).getIb_fee());
					map.addAttribute("sjsy_fee",sjsy_fee);
				}
			}
			map.addAttribute("plist",plist.size()==0?null:plist.get(0));
			//2.耗材费小计
			List<Map<String, Object>> hcf_list = contractBasicinfoService.findHCFList(ym, con_id);
			map.addAttribute("hcf_list",hcf_list);
			//3.仓储费
			List<Map<String, Object>> ccf_list = contractBasicinfoService.findCCFList(ym, con_id);
			map.addAttribute("ccf_list",ccf_list.size()!=0?ccf_list.get(0):null);
			//4.增值服务费
			List<Map<String, Object>> zzfwf_list = contractBasicinfoService.findZZFWFList(ym, con_id);
			BigDecimal amount = new BigDecimal(0.00);
			BigDecimal qty = new BigDecimal(0.00);
			for (int i = 0; i < zzfwf_list.size(); i++) {
				BigDecimal amounts = new BigDecimal(zzfwf_list.get(i).get("amount").toString());
				amount = amount.add(amounts);
				qty = qty.add(new BigDecimal(zzfwf_list.get(i).get("qty").toString()));
			}
			//获取操作费规则
			if(zzfwf_list.size()!=0){
				String cbid = zzfwf_list.get(0).get("contract_id").toString();
				OperationSettlementRule oSR = operationSettlementRuleService.findByCBID(cbid).size()!=0?operationSettlementRuleService.findByCBID(cbid).get(0):null;
				String osr_zzfwf_status = oSR.getOsr_zzfwf_status();
				String osr_zzfwf_detail = oSR.getOsr_zzfwf_detail();
				//阶梯
				if(null!=osr_zzfwf_status && osr_zzfwf_status.equals("0")){
					
				}else if(null!=osr_zzfwf_status && osr_zzfwf_status.equals("1")){
					
				}else if(null!=osr_zzfwf_status && osr_zzfwf_status.equals("2")){
					List<Map<String, Object>> list = valueAddedServiceDetailService.findByCBID(osr_zzfwf_detail);
					BigDecimal zk = countALLData(list, "vasd_section", "vasd_value", amount, oSR);
					map.addAttribute("zzfwf_hz",amount.multiply(zk));
					map.addAttribute("zzfwf_qty",qty);
				}
			}
			map.addAttribute("zzfwf_list",zzfwf_list);
			//5.打包费
			List<Map<String, Object>> dbf_list = contractBasicinfoService.set_SQL("select * from tb_package_charage_summary where date='"+ym+"' and cbid='"+con_id+"'");

			BigDecimal dbf_qty = new BigDecimal(0.00);
			BigDecimal dbf = new BigDecimal(0.00);
			BigDecimal bjf = new BigDecimal(0.00);
			BigDecimal dbf_all = new BigDecimal(0.00);
			if(dbf_list.size()!=0){
				Map<String, Object> dbf_map = dbf_list.get(0);
				dbf_qty=new BigDecimal(dbf_map.get("dbf_qty").toString());
				dbf=new BigDecimal(dbf_map.get("dbf_price").toString());
				bjf=new BigDecimal(dbf_map.get("bjf_price").toString());
				dbf_all=new BigDecimal(dbf_map.get("total_price").toString());
			}
			map.addAttribute("dbf_qty",dbf_qty);
			map.addAttribute("dbf_fee",dbf);
			map.addAttribute("bfj_fee",bjf);
			map.addAttribute("bfj_je","");
			map.addAttribute("dbf_all",dbf_all);
			//5.运费
			//--物流
			List<Map<String,Object>>wl_pool=contractBasicinfoService.findWlPoolList(ym, con_id);
			map.addAttribute("wl_pool",wl_pool);
			//--快递
			// con_id为合同id
			map.addAttribute("bSUEs", expressUsedBalanceService.selectRecordsBySubject(con_id, ym));
			// 6.总运费折扣及管理费
			List<Map<String, Object>> cUSs = carrierUsedBalanceService.getRecords(con_id, ym);
			if(CommonUtils.checkExistOrNot(cUSs)){
				map.addAttribute("cUS", cUSs);
			} else {
				map.addAttribute("cUS", null);
			}
		}
		return "/lmis/ccf_data_settlement";
	}
	
	private boolean compareTo(String str1,String str2){
		Map<String,Integer> sectionMap1 = IntervalValidationUtil.strToMap(str1);
		Map<String,Integer> sectionMap2 = IntervalValidationUtil.strToMap(str2);
		if(sectionMap2.get("endNum")>sectionMap1.get("endNum")){
			return true;
		}else{
			return false;
		}
	}

	private BigDecimal countALLData(List<Map<String, Object>> list,String section,String price,BigDecimal multiply,OperationSettlementRule oSR){
		String sectionA = "";
		String priceA = "";
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> map = list.get(i);
			String sections = map.get(section).toString();
			String prices = map.get(price).toString();
			Map<String,Integer> sectionMap = IntervalValidationUtil.strToMap(sections);
			if(sectionMap.get("type")==0){
				//大于OR小于
			}else{
				//区间
				Integer startSymbol = sectionMap.get("startSymbol");
				Integer startNum = sectionMap.get("startNum");
				Integer endSymbol = sectionMap.get("endSymbol");
				Integer endNum = sectionMap.get("endNum");
				BigDecimal a1 = multiply;
				BigDecimal b1 = new BigDecimal(startNum);
				BigDecimal b2 = new BigDecimal(endNum);
				//小括号开始
				if(startSymbol==0){
					//小括号结束
					if(endSymbol==0){
						if(a1.compareTo(b1)==1 && a1.compareTo(b2)==-1){
							if(sectionA.equals("") && priceA.equals("")){
								sectionA=sections;
								priceA=prices;
							}else{
								if(compareTo(sectionA, sections)){
//									sectionA=sections;
//									priceA=prices;
								}
							}
						}
					}else{
						//中括号结束
						if(a1.compareTo(b1)==1 && a1.compareTo(b2)<=0){
							if(sectionA.equals("") && priceA.equals("")){
								sectionA=sections;
								priceA=prices;
							}else{
								if(compareTo(sectionA, sections)){
//									sectionA=sections;
//									priceA=prices;
								}
							}
						}
					}
				}
				//中括号开始
				if(startSymbol==1){
					//中括号结束
					if(endSymbol==0){
						if(a1.compareTo(b1)>=0 && a1.compareTo(b2)<=0){
							if(sectionA.equals("") && priceA.equals("")){
								sectionA=sections;
								priceA=prices;
							}else{
								if(compareTo(sectionA, sections)){
//									sectionA=sections;
//									priceA=prices;
								}
							}
						}
					}else{
						//小括号结束
						if(a1.compareTo(b1)>=0 && a1.compareTo(b2)==-1){
							if(sectionA.equals("") && priceA.equals("")){
								sectionA=sections;
								priceA=prices;
							}else{
								if(compareTo(sectionA, sections)){
//									sectionA=sections;
//									priceA=prices;
								}
							}
						}
					}
				}
			}
		}
		if(priceA.equals("")){
			return new BigDecimal(0.00);
		}else{
			return new BigDecimal(priceA);
		}
	}

	@RequestMapping("/excel_detail.do")
	public void excel_detail(HttpServletRequest request,HttpServletResponse response){
		try {
			//查询配置表
			List<Map<String, Object>> sqllist = contractBasicinfoService.set_SQL("select * from s_export_sql order by id");
			StringBuffer sb = new StringBuffer();
			sb.append("select c.id client_id,c.client_name ,count(1) storeNum,cb.id cbid from tb_contract_basicinfo cb ");
			sb.append("left join tb_client c on cb.contract_owner=c.client_code ");
			sb.append("left join tb_store s on c.id=s.client_id ");
			sb.append("where cb.contract_type =4 ");
			sb.append("group by c.client_name order by cbid;");
			List<Map<String, Object>> cbList = contractBasicinfoService.set_SQL(sb.toString());
			for (int j = 0; j < 1; j++) {
				for (int i = 0; i < 1; i++) {
					//文件名
					String title = null!=sqllist.get(i).get("title")?sqllist.get(i).get("title").toString():"";
					title=title.replace("${client_name}", cbList.get(j).get("client_name").toString());
					String storeNameList = "";
					if(null!=cbList.get(j).get("storeNum") && !cbList.get(j).get("storeNum").equals("")){
						List<Map<String, Object>> store_list = contractBasicinfoService.set_SQL("select store_name from tb_store where client_id="+cbList.get(j).get("client_id"));
						for (int k = 0; k < store_list.size(); k++) {
							storeNameList=storeNameList+"'"+store_list.get(k).get("store_name")+"'";
							if(k+1!=store_list.size()){
								storeNameList=storeNameList+",";
							}
							
						}
					}
					//SQL
					String sql_data = null!=sqllist.get(i).get("sql_data")?sqllist.get(i).get("sql_data").toString():"";
					String sql_count = null!=sqllist.get(i).get("sql_count")?sqllist.get(i).get("sql_count").toString():"";
					if(!storeNameList.equals("")){
						sql_data=sql_data.replace("${store_name}", storeNameList);
						sql_count=sql_count.replace("${store_name}", storeNameList);
					}
					Integer maxCount = null!=sqllist.get(i).get("maxCount")?Integer.valueOf(sqllist.get(i).get("maxCount").toString()):1000000;
					if(!title.equals("") && !sql_data.equals("") && !sql_count.equals("") && sql_data.indexOf("${")==-1 && sql_count.indexOf("${")==-1){
						export_SC(title,sql_data,sql_count,maxCount);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 
	* @Title: export_SC 
	* @Description: TODO(导出) 
	* @param @param date
	* @param @param contract_id
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public int export_SC(String fileName,String sql,String sql_count,int maxCount){
	   //title
	   LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
	   int max = 0;
	   int allCount = contractBasicinfoService.get_count(sql_count);
	   if(allCount>maxCount){
		   if(0==allCount%maxCount){
			   max = Integer.valueOf(allCount/maxCount);
		   }else{
			   max = Integer.valueOf(allCount/maxCount)+1;
		   } 
	   }else{
		   max=allCount;
	   }
	  
	   int startI = 0;
	   for (int j = 1; j < max+1; j++) {
		   List<Map<String, Object>> sqllist  = contractBasicinfoService.set_SQL(sql+"limit "+startI+","+(j*maxCount));
		   startI=j*maxCount;
		   if(sqllist.size()!=0){
			   Set<String> keys = sqllist.get(0).keySet();
			   //data
			   List<Map<String, Object>> data = new ArrayList<>();
			   //sheet
			   for (int i = 0; i < sqllist.size(); i++) {
				   Map<String, Object> keyMap = new HashMap<>();
				   for (String SKeys: sortByValue(keys)) {
					   if(null==title.get(SKeys)){
						   title.put(SKeys, SKeys);
					   }
					   keyMap.put(SKeys, sqllist.get(i).get(SKeys));
				   }
				   data.add(keyMap);
			   } 
			   try {
				   BigExcelExport.excelDownLoadDatab(data, title, fileName+"_"+(j)+".xlsx");
			   }catch (IOException e) {
				   e.printStackTrace();
			   }
		   }
	   }
	   return BaseConst.SUCCESS;
	}
	
	public static Set<String> sortByValue(Set<String> set){  
        List<String> setList= new ArrayList<String>(set);  
        Collections.sort(setList, new Comparator<String>() {  
            @Override  
            public int compare(String o1, String o2) {  
                return o1.toString().compareTo(o2.toString());  
            }  
              
        });  
        set = new LinkedHashSet<String>(setList); 
        return set;  
    }
}
