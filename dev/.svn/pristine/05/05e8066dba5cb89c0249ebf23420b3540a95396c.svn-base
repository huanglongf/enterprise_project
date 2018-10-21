package com.bt.lmis.controller;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.DiffBilldeatilsQueryParam;
import com.bt.lmis.model.CollectionDetail;
import com.bt.lmis.model.CollectionMaster;
import com.bt.lmis.model.DiffBilldeatils;
import com.bt.lmis.model.ExpressbillMaster;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.DiffBilldeatilsService;
import com.bt.lmis.service.ExpressbillMasterService;
import com.bt.utils.BaseConst;
import com.bt.utils.BigExcelExport;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;
import java.util.Arrays;
import net.sf.json.JSONObject;
import com.bt.lmis.model.Employee;
/**
 * 差异表控制器
 *
 */
@Controller
@RequestMapping("/control/lmis/diffBilldeatilsController")
public class DiffBilldeatilsController extends BaseController {

	private static final Logger logger = Logger.getLogger(DiffBilldeatilsController.class);
	
	/**
	 * 差异表服务类
	 */
	@Resource(name = "diffBilldeatilsServiceImpl")
	private DiffBilldeatilsService<DiffBilldeatils> diffBilldeatilsService;
	@Resource(name = "expressbillMasterServiceImpl")
	private ExpressbillMasterService<ExpressbillMaster> expressbillMasterService;
	
	
	@RequestMapping("page")
	public String tablist(HttpServletRequest request, HttpServletResponse res, DiffBilldeatilsQueryParam queryParam) throws Exception {
		request.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		QueryResult<DiffBilldeatils> qr = null;
		PageView<DiffBilldeatils> pageView = new PageView<DiffBilldeatils>(
				queryParam.getPageSize() == 0 ? BaseConst.pageSize : queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		qr = diffBilldeatilsService.selectMasterId(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("totalSize", qr.getTotalrecord());
		request.setAttribute("queryParam", queryParam);
		List<Map<String,Object>> monthAccount=diffBilldeatilsService.getMonthAccount();
		request.setAttribute("monthAccount", monthAccount);
		return "lmis/verification/vfc_diff_billdeatils_page";
	}
	
	@RequestMapping("pageQuery")
	public String pageQuery(HttpServletRequest request, HttpServletResponse res, DiffBilldeatilsQueryParam queryParam) {
		QueryResult<DiffBilldeatils> qr = null;
		PageView<DiffBilldeatils> pageView = new PageView<DiffBilldeatils>(
				queryParam.getPageSize() == 0 ? BaseConst.pageSize : queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		qr = diffBilldeatilsService.selectMasterId(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("totalSize", qr.getTotalrecord());
		request.setAttribute("queryParam", queryParam);
		return "lmis/verification/vfc_diff_billdeatils_page";
	}
	
	
	@SuppressWarnings("unused")
	@RequestMapping("uploade")
	public void uploade(HttpServletRequest request, HttpServletResponse res, DiffBilldeatilsQueryParam queryParam) throws Exception {
		String ids=request.getParameter("ids");
		String[] split = ids.split(";");
		Map<String,String> result= new HashMap<String,String>();
		PrintWriter out = null;
		result.put("out_result", "0");
		result.put("out_result_reason","错误");
		int id = Integer.parseInt(queryParam.getMaster_id());
		ExpressbillMaster expressbillMaster = expressbillMasterService.selectById(id);
		String uuid = UUID.randomUUID().toString();
		String contract_id=expressbillMaster.getCon_id().toString();
		//String contract_id="210";
		//queryParam.setDest_province("上海");
		if(split!=null && ids!=""){
			List<CollectionMaster> list = diffBilldeatilsService.uploadByIds(split, contract_id,uuid);
			diffBilldeatilsService.deleteDiffBilldeatilsTempByAccountId(uuid);
			
			Map<String, String> map = new HashMap<>();
			List<String> listsf = new ArrayList<String>();
			List<Map<String, Object>> data = new ArrayList<>();
			if(list.size()>0){
				if(list.get(0).getDetails().get(0).getProducttype_code()!=null||list.get(0).getDetails().get(0).getProducttype_code()!=""){
					for (CollectionMaster collectionMaster : list) {
						List<CollectionDetail> list2 = collectionMaster.getDetails();
						for (CollectionDetail collectionDetail : list2) {
							if(map.get(collectionDetail.getProducttype_code())==null||map.get(collectionDetail.getProducttype_code())==""){
								map.put(collectionDetail.getProducttype_code(), collectionDetail.getProducttype_code());
							}
						}
					}
					for (Map.Entry<String, String> entry : map.entrySet()) {
					   /* System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());*/
					    listsf.add(entry.getKey());
					}
				}
				for (int j = 0; j < list.size(); j++) {
					CollectionMaster collectionMaster = list.get(j);
					Map<String, Object> keyMap = new HashMap<>();		
					keyMap.put("cost_center", collectionMaster.getCost_center());
					keyMap.put("store_code", collectionMaster.getStore_code());
					keyMap.put("warehouse_code", collectionMaster.getWarehouse_code());
					keyMap.put("insurance_fee", collectionMaster.getInsurance_fee());
					keyMap.put("service_fee", collectionMaster.getService_fee());
					if(collectionMaster.getDetails().get(0).getProducttype_code()==null||list.get(0).getDetails().get(0).getProducttype_code()==""){
								keyMap.put("sum", collectionMaster.getDetails().get(0).getSum());
								keyMap.put("freight", collectionMaster.getDetails().get(0).getFreight());
								keyMap.put("favourable_price", collectionMaster.getDetails().get(0).getFavourable_price());
								keyMap.put("zfavourable_price", collectionMaster.getDetails().get(0).getFavourable_price_total());
					}else{
						List<CollectionDetail> details = collectionMaster.getDetails();
						for (CollectionDetail collectionDetail : details) {
								keyMap.put(collectionDetail.getProducttype_code()+"sum", collectionDetail.getSum());
								keyMap.put(collectionDetail.getProducttype_code()+"freight", collectionDetail.getFreight());
								keyMap.put(collectionDetail.getProducttype_code()+"favourable_price", collectionDetail.getFavourable_price());
								keyMap.put(collectionDetail.getProducttype_code()+"zfavourable_price", collectionDetail.getFavourable_price_total());
						}
					}
					data.add(keyMap);
				}
				try{
					out = res.getWriter();
					HashMap<String, String> cMap = new LinkedHashMap<String, String>();
					cMap.put("cost_center", "成本中心");
					cMap.put("store_code", "店铺");
					cMap.put("warehouse_code", "仓库");
					if(list.size()==0){
						cMap.put("sum", "单量");
						cMap.put("freight", "标准运费");
						cMap.put("insurance_fee", "保价费");
						cMap.put("service_fee", "服务费");
						cMap.put("favourable_price", "单运单优惠金额");
						cMap.put("zfavourable_price", "总运单优惠金额");
	/*				}else if(list.get(0).getDetails().get(0).getProducttype_code()==null||list.get(0).getDetails().get(0).getProducttype_code()==""){
						cMap.put("sum", "单量");
						cMap.put("freight", "标准运费");
						cMap.put("insurance_fee", "保价费");
						cMap.put("service_fee", "服务费");
						cMap.put("favourable_price", "单运单优惠金额");
						cMap.put("zfavourable_price", "总运单优惠金额");
						
	*/				}else{
						for (String string : listsf) {
							System.out.println(string!=null);
							System.out.println(string!="");
							System.out.println(string!=null||string!="");
							System.out.println(string!=null&&string!="");
							if(string!=null&&string!=""){
							    if(string.equals("SFCR")||string=="SFCR"){
							    	cMap.put(string+"sum", "顺丰次日单量");
							    }else if(string.equals("SFJR")||string=="SFJR"){
							    	cMap.put(string+"sum", "顺丰即日单量");
							    }else if(string.equals("SFCC")||string=="SFCC"){
							    	cMap.put(string+"sum", "顺丰次晨单量");
							    }else if(string.equals("SFGR")||string=="SFGR"){
							    	cMap.put(string+"sum", "顺丰隔日单量");
							    }else if(string.equals("YCCR")||string=="YCCR"){
									cMap.put(string+"sum", "云仓次日单量");
								}else if(string.equals("YCGR")||string=="YCGR"){
									cMap.put(string+"sum", "云仓隔日单量");
								}
							}else{
								cMap.put("sum", "单量");
							}
						}
						for (String string : listsf) {
							if(string!=null&&string!=""){
								if(string.equals("SFCR")||string=="SFCR"){
									cMap.put(string+"freight", "顺丰次日标准运费");
								}else if(string.equals("SFJR")||string=="SFJR"){
									cMap.put(string+"freight", "顺丰即日标准运费");
								}else if(string.equals("SFCC")||string=="SFCC"){
									cMap.put(string+"freight", "顺丰次晨标准运费");
								}else if(string.equals("SFGR")||string=="SFGR"){
									cMap.put(string+"freight", "顺丰隔日标准运费");
								}else if(string.equals("YCCR")||string=="YCCR"){
									cMap.put(string+"freight", "云仓次日标准运费");
								}else if(string.equals("YCGR")||string=="YCGR"){
									cMap.put(string+"freight", "云仓隔日标准运费");
								}
							}else{
								cMap.put("freight", "标准运费");
							}
						}
						cMap.put("insurance_fee", "保价费");
						cMap.put("service_fee", "服务费");
						for (String string : listsf) {
							if(string!=null&&string!=""){
								if(string.equals("SFCR")||string=="SFCR"){
									cMap.put(string+"favourable_price", "顺丰次日单运单优惠金额");
								}else if(string.equals("SFJR")||string=="SFJR"){
									cMap.put(string+"favourable_price", "顺丰即日单运单优惠金额");
								}else if(string.equals("SFCC")||string=="SFCC"){
									cMap.put(string+"favourable_price", "顺丰次晨单运单优惠金额");
								}else if(string.equals("SFGR")||string=="SFGR"){
									cMap.put(string+"favourable_price", "顺丰隔日单运单优惠金额");
								}else if(string.equals("YCCR")||string=="YCCR"){
									cMap.put(string+"favourable_price", "云仓次日单运单优惠金额");
								}else if(string.equals("YCGR")||string=="YCGR"){
									cMap.put(string+"favourable_price", "云仓隔日单运单优惠金额");
								}
							}else{
								cMap.put("favourable_price", "单运单优惠金额");
							}
						}
						for (String string : listsf) {
							if(string!=null&&string!=""){
								if(string.equals("SFCR")||string=="SFCR"){
									cMap.put(string+"zfavourable_price", "顺丰次日总运单优惠金额");
								}else if(string.equals("SFJR")||string=="SFJR"){
									cMap.put(string+"zfavourable_price", "顺丰即日总运单优惠金额");
								}else if(string.equals("SFCC")||string=="SFCC"){
									cMap.put(string+"zfavourable_price", "顺丰次晨总运单优惠金额");
								}else if(string.equals("SFGR")||string=="SFGR"){
									cMap.put(string+"zfavourable_price", "顺丰隔日总运单优惠金额");
								}else if(string.equals("YCCR")||string=="YCCR"){
									cMap.put(string+"zfavourable_price", "云仓次日总运单优惠金额");
								}else if(string.equals("YCGR")||string=="YCGR"){
									cMap.put(string+"zfavourable_price", "云仓隔日总运单优惠金额");
								}
							}else{
								cMap.put("zfavourable_price", "总运单优惠金额");
							}
						}
					}
				/*for(int k=0; k<i; k++){
					cMap.put("sum"+(k+1), "产品类型"+(k+1)+"单量");
				}
				for(int k=0; k<i; k++){
					cMap.put("freight"+(k+1), "产品类型"+(k+1)+"标准运费");
				}
				cMap.put("insurance_fee", "保价费");
				cMap.put("service_fee", "服务费");
				for(int k=0; k<i; k++){
					cMap.put("favourable_price"+(k+1), "产品类型"+(k+1)+"单运单优惠金额");
				}
				for(int k=0; k<i; k++){
					cMap.put("zfavourable_price"+(k+1), "产品类型"+(k+1)+"总运单优惠金额");
				}*/
				Date time =new Date();
				SimpleDateFormat date = new SimpleDateFormat("yyyymmddhhmmss");
				String string = date.format(time);
				BigExcelExport.excelDownLoadDatab(data, cMap, string+"预估汇总表.xlsx");
				result.put("out_result", "1");
				result.put("out_result_reason", "成功");
				result.put("path", CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP")+string+"预估汇总表.xlsx");
			}catch(Exception e){
				e.printStackTrace();
			}
			out.write(com.alibaba.fastjson.JSONObject.toJSON(result).toString());
			out.flush();
			out.close();
			}
		}else{
			List<CollectionMaster> list1 = diffBilldeatilsService.uploadByQueryParam(queryParam, contract_id,uuid);
			diffBilldeatilsService.deleteDiffBilldeatilsTempByAccountId(uuid);
			
			
			Map<String, String> map1 = new HashMap<>();
			List<String> listsf1 = new ArrayList<String>();
			if(list1.size()>0){
				if(list1.get(0).getDetails().get(0).getProducttype_code()!=null||list1.get(0).getDetails().get(0).getProducttype_code()!=""){
					for (CollectionMaster collectionMaster : list1) {
						List<CollectionDetail> list2 = collectionMaster.getDetails();
						for (CollectionDetail collectionDetail : list2) {
							if(map1.get(collectionDetail.getProducttype_code())==null||map1.get(collectionDetail.getProducttype_code())==""){
								map1.put(collectionDetail.getProducttype_code(), collectionDetail.getProducttype_code());
							}
						}
					}
					for (Map.Entry<String, String> entry : map1.entrySet()) {
					   /* System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());*/
					    listsf1.add(entry.getKey());
					}
				}
			}
			List<Map<String, Object>> data1 = new ArrayList<>();
			for (int j = 0; j < list1.size(); j++) {
				CollectionMaster collectionMaster = list1.get(j);
				Map<String, Object> keyMap = new HashMap<>();		
				keyMap.put("cost_center", collectionMaster.getCost_center());
				keyMap.put("store_code", collectionMaster.getStore_code());
				keyMap.put("warehouse_code", collectionMaster.getWarehouse_code());
				keyMap.put("insurance_fee", collectionMaster.getInsurance_fee());
				keyMap.put("service_fee", collectionMaster.getService_fee());
				if(collectionMaster.getDetails().get(0).getProducttype_code()==null||list1.get(0).getDetails().get(0).getProducttype_code()==""){
							keyMap.put("sum", collectionMaster.getDetails().get(0).getSum());
							keyMap.put("freight", collectionMaster.getDetails().get(0).getFreight());
							keyMap.put("favourable_price", collectionMaster.getDetails().get(0).getFavourable_price());
							keyMap.put("zfavourable_price", collectionMaster.getDetails().get(0).getFavourable_price_total());
				}else{
					List<CollectionDetail> details = collectionMaster.getDetails();
					for (CollectionDetail collectionDetail : details) {
							keyMap.put(collectionDetail.getProducttype_code()+"sum", collectionDetail.getSum());
							keyMap.put(collectionDetail.getProducttype_code()+"freight", collectionDetail.getFreight());
							keyMap.put(collectionDetail.getProducttype_code()+"favourable_price", collectionDetail.getFavourable_price());
							keyMap.put(collectionDetail.getProducttype_code()+"zfavourable_price", collectionDetail.getFavourable_price_total());
					}
				}
				data1.add(keyMap);
			}
			try{
				out = res.getWriter();
				HashMap<String, String> cMap = new LinkedHashMap<String, String>();
				cMap.put("cost_center", "成本中心");
				cMap.put("store_code", "店铺");
				cMap.put("warehouse_code", "仓库");
				if(list1.size()==0){
					cMap.put("sum", "单量");
					cMap.put("freight", "标准运费");
					cMap.put("insurance_fee", "保价费");
					cMap.put("service_fee", "服务费");
					cMap.put("favourable_price", "单运单优惠金额");
					cMap.put("zfavourable_price", "总运单优惠金额");
/*				}else if(list.get(0).getDetails().get(0).getProducttype_code()==null||list.get(0).getDetails().get(0).getProducttype_code()==""){
					cMap.put("sum", "单量");
					cMap.put("freight", "标准运费");
					cMap.put("insurance_fee", "保价费");
					cMap.put("service_fee", "服务费");
					cMap.put("favourable_price", "单运单优惠金额");
					cMap.put("zfavourable_price", "总运单优惠金额");
					
*/				}else{
					for (String string : listsf1) {
						if(string!=null&&string!=""){
						    if(string.equals("SFCR")||string=="SFCR"){
						    	cMap.put(string+"sum", "顺丰次日单量");
						    }else if(string.equals("SFJR")||string=="SFJR"){
						    	cMap.put(string+"sum", "顺丰即日单量");
						    }else if(string.equals("SFCC")||string=="SFCC"){
						    	cMap.put(string+"sum", "顺丰次晨单量");
						    }else if(string.equals("SFGR")||string=="SFGR"){
						    	cMap.put(string+"sum", "顺丰隔日单量");
						    }else if(string.equals("YCCR")||string=="YCCR"){
								cMap.put(string+"sum", "云仓次日单量");
							}else if(string.equals("YCGR")||string=="YCGR"){
								cMap.put(string+"sum", "云仓隔日单量");
							}
						}else{
							cMap.put("sum", "单量");
						}
					}
					for (String string : listsf1) {
						if(string!=null&&string!=""){
							if(string.equals("SFCR")||string=="SFCR"){
								cMap.put(string+"freight", "顺丰次日标准运费");
							}else if(string.equals("SFJR")||string=="SFJR"){
								cMap.put(string+"freight", "顺丰即日标准运费");
							}else if(string.equals("SFCC")||string=="SFCC"){
								cMap.put(string+"freight", "顺丰次晨标准运费");
							}else if(string.equals("SFGR")||string=="SFGR"){
								cMap.put(string+"freight", "顺丰隔日标准运费");
							}else if(string.equals("YCCR")||string=="YCCR"){
								cMap.put(string+"freight", "云仓次日标准运费");
							}else if(string.equals("YCGR")||string=="YCGR"){
								cMap.put(string+"freight", "云仓隔日标准运费");
							}
						}else{
							cMap.put("freight", "标准运费");
						}
					}
					cMap.put("insurance_fee", "保价费");
					cMap.put("service_fee", "服务费");
					for (String string : listsf1) {
						if(string!=null&&string!=""){
							if(string.equals("SFCR")||string=="SFCR"){
								cMap.put(string+"favourable_price", "顺丰次日单运单优惠金额");
							}else if(string.equals("SFJR")||string=="SFJR"){
								cMap.put(string+"favourable_price", "顺丰即日单运单优惠金额");
							}else if(string.equals("SFCC")||string=="SFCC"){
								cMap.put(string+"favourable_price", "顺丰次晨单运单优惠金额");
							}else if(string.equals("SFGR")||string=="SFGR"){
								cMap.put(string+"favourable_price", "顺丰隔日单运单优惠金额");
							}else if(string.equals("YCCR")||string=="YCCR"){
								cMap.put(string+"favourable_price", "云仓次日单运单优惠金额");
							}else if(string.equals("YCGR")||string=="YCGR"){
								cMap.put(string+"favourable_price", "云仓隔日单运单优惠金额");
							}
						}else{
							cMap.put("favourable_price", "单运单优惠金额");
						}
					}
					for (String string : listsf1) {
						if(string!=null&&string!=""){
							if(string.equals("SFCR")||string=="SFCR"){
								cMap.put(string+"zfavourable_price", "顺丰次日总运单优惠金额");
							}else if(string.equals("SFJR")||string=="SFJR"){
								cMap.put(string+"zfavourable_price", "顺丰即日总运单优惠金额");
							}else if(string.equals("SFCC")||string=="SFCC"){
								cMap.put(string+"zfavourable_price", "顺丰次晨总运单优惠金额");
							}else if(string.equals("SFGR")||string=="SFGR"){
								cMap.put(string+"zfavourable_price", "顺丰隔日总运单优惠金额");
							}else if(string.equals("YCCR")||string=="YCCR"){
								cMap.put(string+"zfavourable_price", "云仓次日总运单优惠金额");
							}else if(string.equals("YCGR")||string=="YCGR"){
								cMap.put(string+"zfavourable_price", "云仓隔日总运单优惠金额");
							}
						}else{
							cMap.put("zfavourable_price", "总运单优惠金额");
						}
					}
				}
			
			/*
			int i = 0;
			for (CollectionMaster collectionMaster : list) {
				int j = collectionMaster.getDetails().size();
				if(j>i){
					i=j;
				}
			}
			List<Map<String, Object>> data = new ArrayList<>();
			for (CollectionMaster collectionMaster : list) {
					Map<String, Object> keyMap = new HashMap<>();		
					keyMap.put("cost_center", collectionMaster.getCost_center());
					keyMap.put("store_code", collectionMaster.getStore_code());
					keyMap.put("warehouse_code", collectionMaster.getWarehouse_code());
					keyMap.put("insurance_fee", collectionMaster.getInsurance_fee());
					keyMap.put("service_fee", collectionMaster.getService_fee());
					if(collectionMaster.getDetails().get(0).getProducttype_code()==null){
						for(int k=0; k<i; k++){
							if(collectionMaster.getDetails().size()>k){
								keyMap.put("sum"+(k+1), collectionMaster.getDetails().get(k).getSum());
								keyMap.put("freight"+(k+1), collectionMaster.getDetails().get(k).getFreight());
								keyMap.put("favourable_price"+(k+1), "");
								keyMap.put("zfavourable_price"+(k+1), collectionMaster.getDetails().get(k).getFavourable_price());
							}else{
								keyMap.put("sum"+(k+1), "");
								keyMap.put("freight"+(k+1), "");
								keyMap.put("favourable_price"+(k+1), "");
								keyMap.put("zfavourable_price"+(k+1), "");
							}
						}
					}else{
						for(int k=0; k<i; k++){
							if(collectionMaster.getDetails().size()>k){
								keyMap.put("sum"+(k+1), collectionMaster.getDetails().get(k).getSum());
								keyMap.put("freight"+(k+1), collectionMaster.getDetails().get(k).getFreight());
								keyMap.put("favourable_price"+(k+1), collectionMaster.getDetails().get(k).getFavourable_price());
								keyMap.put("zfavourable_price"+(k+1), "");
							}else{
								keyMap.put("sum"+(k+1), "");
								keyMap.put("freight"+(k+1), "");
								keyMap.put("favourable_price"+(k+1), "");
								keyMap.put("zfavourable_price"+(k+1), "");
							}
						}
					}
					data.add(keyMap);
				}	
			try{
				out = res.getWriter();
				HashMap<String, String> cMap = new LinkedHashMap<String, String>();
				cMap.put("cost_center", "成本中心");
				cMap.put("store_code", "店铺");
				cMap.put("warehouse_code", "仓库");
				for(int k=0; k<i; k++){
					cMap.put("sum"+(k+1), "产品类型"+(k+1)+"单量");
				}
				for(int k=0; k<i; k++){
					cMap.put("freight"+(k+1), "产品类型"+(k+1)+"标准运费");
				}
				cMap.put("insurance_fee", "保价费");
				cMap.put("service_fee", "服务费");
				for(int k=0; k<i; k++){
					cMap.put("favourable_price"+(k+1), "产品类型"+(k+1)+"单运单优惠金额");
				}
				for(int k=0; k<i; k++){
					cMap.put("zfavourable_price"+(k+1), "产品类型"+(k+1)+"总运单优惠金额");
				}*/
				Date time =new Date();
				SimpleDateFormat date = new SimpleDateFormat("yyyymmddhhmmss");
				String string = date.format(time);
				BigExcelExport.excelDownLoadDatab(data1, cMap, string+"预估汇总表.xlsx");
				result.put("out_result", "1");
				result.put("out_result_reason", "成功");
				result.put("path", CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP")+string+"预估汇总表.xlsx");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		out.write(com.alibaba.fastjson.JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	
	
	@ResponseBody
	@RequestMapping("genAccount")
	public JSONObject genAccount(HttpServletRequest request, HttpServletResponse res) {
		JSONObject obj =new JSONObject();
		 Employee user = SessionUtils.getEMP(request);
		 Map<String,Object> param =new HashMap<String,Object>();
		 param.put("ids", request.getParameter("ids"));
		 param.put("user", user.getId());
		 param.put("master_id", request.getParameter("master_id").toString());
        try{
		  int flag=diffBilldeatilsService.saveAccount(param);
		  obj.put("code", flag);
        }catch(Exception e){
        	e.printStackTrace();
        	obj.put("code", 0);
        }
		return obj;
	}
	
	@ResponseBody
	@RequestMapping("genAccountSe")
	public JSONObject genAccountSe(HttpServletRequest request, HttpServletResponse res,DiffBilldeatilsQueryParam queryParam) {
		JSONObject obj =new JSONObject();
		 Employee user = SessionUtils.getEMP(request);
		 queryParam.setCreate_user(user.getId().toString());
        try{
		  int flag=diffBilldeatilsService.saveAccount(queryParam);
		  obj.put("code", flag);
        }catch(Exception e){
        	e.printStackTrace();
        	obj.put("code", 0);
        }
		return obj;
	}
	
	@ResponseBody
	@RequestMapping("hx")
	public JSONObject verification(HttpServletRequest request, HttpServletResponse res,DiffBilldeatilsQueryParam queryParam) {
		JSONObject obj =new JSONObject();
		 Employee user = SessionUtils.getEMP(request);
		 Map<String,Object> param =new HashMap<String,Object>();
		 param.put("user", user.getId());
        try{
        	queryParam.setIs_verification("0");
        	diffBilldeatilsService.verification(queryParam);
		  obj.put("code", 1);
        }catch(Exception e){
        	e.printStackTrace();
        	obj.put("code", 0);
        }
		return obj;
	}
	
	@ResponseBody
	@RequestMapping("hxids")
	public JSONObject hxids(HttpServletRequest request, HttpServletResponse res,DiffBilldeatilsQueryParam queryParam) {
		JSONObject obj =new JSONObject();
		String ids=request.getParameter("ids").toString();
		 Employee user = SessionUtils.getEMP(request);
		 Map<String,Object> param =new HashMap<String,Object>();
		 param.put("user", user.getId());
        try{
        	List idss=Arrays.asList(ids.split(";"));
        	diffBilldeatilsService.verification(idss);
		  obj.put("code", 1);
        }catch(Exception e){
        	e.printStackTrace();
        	obj.put("code", 0);
        }
		return obj;
	}
	
	
	@ResponseBody
	@RequestMapping("cancelhx")
	public JSONObject cancelhx(HttpServletRequest request, HttpServletResponse res,DiffBilldeatilsQueryParam queryParam) {
		JSONObject obj =new JSONObject();
		 Employee user = SessionUtils.getEMP(request);
		 Map<String,Object> param =new HashMap<String,Object>();
		 param.put("user", user.getId());
        try{
        	queryParam.setIs_verification("1");
        	diffBilldeatilsService.Cancelverification(queryParam);
		  obj.put("code", 1);
        }catch(Exception e){
        	e.printStackTrace();
        	obj.put("code", 0);
        }
		return obj;
	}
	
	
	@ResponseBody
	@RequestMapping("cancelhxids")
	public JSONObject cancelhxids(HttpServletRequest request, HttpServletResponse res) {
		JSONObject obj =new JSONObject();
		 Employee user = SessionUtils.getEMP(request);
		 String ids=request.getParameter("ids").toString();
		 Map<String,Object> param =new HashMap<String,Object>();
		 param.put("ids", ids);
		 param.put("user", user.getId());
		 param.put("is_verification", 1);
        try{
        List idss=Arrays.asList(ids.split(";"));
        	diffBilldeatilsService.Cancelverification(idss);
		  obj.put("code", 1);
        }catch(Exception e){
        	e.printStackTrace();
        	obj.put("code", 0);
        }
		return obj;
	}
	
	
	
	@ResponseBody
	@RequestMapping("downloadDetails")
	public JSONObject upload(HttpServletRequest request, HttpServletResponse res,DiffBilldeatilsQueryParam queryParam) {
		JSONObject obj =new JSONObject();

        try{
        String url=	diffBilldeatilsService.upload(queryParam);
		  obj.put("code", 1);
		  obj.put("url", url);
        }catch(Exception e){
        	e.printStackTrace();
        	obj.put("code", 0);
        }
		return obj;
	}
	@ResponseBody
	@RequestMapping("downloadDetailsIds")
	public JSONObject uploadIDs(HttpServletRequest request, HttpServletResponse res) {
		JSONObject obj =new JSONObject();
          String ids=request.getParameter("ids");
        try{
        String url=	diffBilldeatilsService.uploadIds(ids);
		  obj.put("code", 1);
		  obj.put("url", url);
        }catch(Exception e){
        	e.printStackTrace();
        	obj.put("code", 0);
        }
		return obj;
	}

}
