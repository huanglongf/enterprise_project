package com.bt.lmis.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.bt.OSinfo;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.excel.XLSXCovertCSVReader;
import com.bt.lmis.controller.form.ExpertBillQueryParam;
import com.bt.lmis.controller.lucene.TestLucene;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.EmsTemplate;
import com.bt.lmis.model.ExpertBill;
import com.bt.lmis.model.ExpressagingDetail;
import com.bt.lmis.model.ImpModel;
import com.bt.lmis.model.SfTemplate;
import com.bt.lmis.model.StoTemplate;
import com.bt.lmis.model.YtoTemplate;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ContractBasicinfoService;
import com.bt.lmis.service.DifferenceMatchingService;
import com.bt.lmis.service.ExpertBillService;
import com.bt.radar.model.ReceiveDeadline;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.Constant;
import com.bt.utils.DateUtil;
import com.bt.utils.SessionUtils;

/** ｀
* @ClassName: ExportExcel 
* @Description: TODO(导入控制器) 
* @author Yuriy.Jiang
* @date 2016年12月22日 下午4:51:53 
*  
*/
@Controller
@RequestMapping("/control/differenceController")
public class DifferenceController  extends BaseController{

	private static final Logger logger = Logger.getLogger(DifferenceController.class);
	
	@Resource(name = "expertBillServiceImpl")
	private ExpertBillService<ExpertBill> expertBillServiceImpl;
	@Resource(name="contractBasicinfoServiceImpl")
	private ContractBasicinfoService<ContractBasicinfo> contractBasicinfoServiceImpl;
	@Resource(name="differenceMatchingServiceImpl")
	private DifferenceMatchingService differenceMatchingServiceImpl;
	
	@RequestMapping("/list")
	public String list(ExpertBillQueryParam ebQP,ModelMap map, HttpServletRequest request){
		if(null!=request.getParameter("reservation")){
			Map<String,String> start_end = spiltDateString(request.getParameter("reservation"));
			ebQP.setS_time(start_end.get("startDate"));
			ebQP.setE_time(start_end.get("endDate"));
		}
		String msg = request.getParameter("msg");
		if(null!=msg && !msg.equals("")){
			map.addAttribute("msg", msg);
		}
		Map<String, Date> datemap = DateUtil.getCurrentWeekMondaySunDay();
		map.addAttribute("sdate",DateUtil.formatDate(datemap.get("start")));
		map.addAttribute("edate",DateUtil.formatDate(datemap.get("end")));
		//根据条件查询合同集合
		PageView<ExpertBill> pageView = new PageView<ExpertBill>(ebQP.getPageSize()==0?BaseConst.pageSize:ebQP.getPageSize(), ebQP.getPage());
		ebQP.setFirstResult(pageView.getFirstResult());
		ebQP.setMaxResult(pageView.getMaxresult());
		QueryResult<ExpertBill> qr = expertBillServiceImpl.findAll(ebQP);
		pageView.setQueryResult(qr, ebQP.getPage());
		map.addAttribute("pageView", pageView);
		return "/lmis/export_excel/difference_list";
	}

	@RequestMapping("/imp_list")
	public String imp_list(ModelMap map, HttpServletRequest request){
		String id = request.getParameter("id");
		ExpertBill expertBill = null!=expertBillServiceImpl.finrByID(id) ?expertBillServiceImpl.finrByID(id).get(0):null;
		if(null!=expertBill){
			String bat_id = expertBill.getBatch_id();
			expertBillServiceImpl.up_eb(bat_id);
			expertBillServiceImpl.check_wh(bat_id);
			expertBillServiceImpl.check_exp(bat_id);
			expertBillServiceImpl.check_area_q(bat_id);
			expertBillServiceImpl.check_area_s(bat_id);
			expertBillServiceImpl.check_type(bat_id);
			expertBillServiceImpl.check_main(bat_id);
			List<Map<String, Object>> impList = expertBillServiceImpl.findImpByBatId(bat_id);
			map.addAttribute("impList", impList);
			map.addAttribute("id", id);
			map.addAttribute("bat_id", bat_id);
			map.addAttribute("size", impList.size());
		}
		return "/lmis/export_excel/imp_list";
	}
	
	@RequestMapping("/imp")
	public void imp(HttpServletRequest req,HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = new JSONObject();
		result.put("result_code", "500");
		result.put("result_content", "系统错误");
		try {
			out = res.getWriter();
			String bat_id = req.getParameter("id");
			expertBillServiceImpl.check_wh(bat_id);
			expertBillServiceImpl.check_exp(bat_id);
			expertBillServiceImpl.check_area_q(bat_id);
			expertBillServiceImpl.check_area_s(bat_id);
			expertBillServiceImpl.check_type(bat_id);
			expertBillServiceImpl.check_main(bat_id);
			List<Map<String, Object>> impList = expertBillServiceImpl.findImpByBatId(bat_id);
			if(0==impList.size()){
				List<Map<String, Object>> list = expertBillServiceImpl.query_main(bat_id);
				for (int i = 0; i < list.size(); i++) {
					String uuid = UUID.randomUUID().toString();
					ReceiveDeadline rdl = new ReceiveDeadline();
					rdl.setId(uuid);
					rdl.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
					rdl.setExpress_code(null!=list.get(i).get("transport_code")?list.get(i).get("transport_code").toString():"");
					rdl.setProducttype_code(null!=list.get(i).get("product_type_code")?list.get(i).get("product_type_code").toString():"");
					rdl.setWarehouse_code(null!=list.get(i).get("warehouse_code")?list.get(i).get("warehouse_code").toString():"");
					rdl.setProvince_code(null!=list.get(i).get("sarea")?list.get(i).get("sarea").toString():"");
					rdl.setCity_code(null!=list.get(i).get("qarea")?list.get(i).get("qarea").toString():"");
					rdl.setState_code(null!=list.get(i).get("farea")?list.get(i).get("farea").toString():"");
					rdl.setStreet_code(null!=list.get(i).get("garea")?list.get(i).get("garea").toString():"");
					rdl.setEmbrace_time(null!=list.get(i).get("embrace_time")?list.get(i).get("embrace_time").toString():"");
					rdl.setDl_flag(1);
					ImpModel imp = new ImpModel();
					imp.setBat_id(bat_id);
					imp.setField1(null!=list.get(i).get("transport_codes")?list.get(i).get("transport_codes").toString():"");
					imp.setField2(null!=list.get(i).get("product_type_codes")?list.get(i).get("product_type_codes").toString():"");
					imp.setField3(null!=list.get(i).get("warehouse_codes")?list.get(i).get("warehouse_codes").toString():"");
					imp.setField5(null!=list.get(i).get("sareas")?list.get(i).get("sareas").toString():"");
					imp.setField6(null!=list.get(i).get("qareas")?list.get(i).get("qareas").toString():"");
					List<ImpModel> impmodelList = expertBillServiceImpl.query_imp_del(imp);
					List<ExpressagingDetail> impLists = new ArrayList<>();
					for (int j = 0; j < impmodelList.size(); j++) {
						ExpressagingDetail ed = new ExpressagingDetail();
						ed.setId(UUID.randomUUID().toString());
						ed.setCreate_time(new Date());
						ed.setCreate_user(BaseConst.SYSTEM_JOB_CREATE);
						ed.setUpdate_time(new Date());
						ed.setUpdate_user(BaseConst.SYSTEM_JOB_CREATE);
						ed.setNumber(j+1);
						ed.setProvince_code(null!=list.get(i).get("sarea")?list.get(i).get("sarea").toString():"");
						ed.setCity_code(null!=list.get(i).get("qarea")?list.get(i).get("qarea").toString():"");
						ed.setState_code(null!=list.get(i).get("farea")?list.get(i).get("farea").toString():"");
						ed.setStreet_code(null!=list.get(i).get("garea")?list.get(i).get("garea").toString():"");
						ed.setEfficiency(new BigDecimal(impmodelList.get(j).getField10()));
						if(impmodelList.get(j).getField11().equals("D")){
							ed.setEfficiency_unit("1");
						}else{
							ed.setEfficiency_unit("0");
						}
						ed.setWarningtype_code(null!=impmodelList.get(j).getField9()?impmodelList.get(j).getField9():"");
						ed.setValibity(1);
						ed.setPid(uuid);
						ed.setDl_flag(1);
						impLists.add(ed);
					}
					expertBillServiceImpl.ins_imp(rdl,impLists);
				}
				result.put("result_code", "200");
				result.put("result_content", "成功");
			}else{
				result.put("result_code", "300");
				result.put("result_content", "参数错误");
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(result.toString());
		out.flush();
		out.close();
	}
	
	@ResponseBody
	@RequestMapping(value="/input_excel.do")
	public JSONObject input_excel(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse res) throws Exception{
		JSONObject obj= new JSONObject();
		try {
			String fileName= file.getOriginalFilename();
			if(differenceMatchingServiceImpl.judgeFileNameDuplicate(fileName) == 0) {
				String year= req.getParameter("year");
		        String month= req.getParameter("month");
		        String mb= req.getParameter("mb");
				// 判断文件是否为空  
		        if (!file.isEmpty() && CommonUtils.checkExistOrNot(year) && CommonUtils.checkExistOrNot(month) && CommonUtils.checkExistOrNot(mb)) {
	                // 文件保存路径
	    			String filePath= CommonUtils.getAllMessage("config", "BALANCE_DIFFERENCE_UPLOAD_BILL_" + OSinfo.getOSname()) + fileName;
	                // 转存文件
	                file.transferTo(new File(filePath));
	    			Employee user= SessionUtils.getEMP(req);
					int size= 0;
					List<SfTemplate> sf_list= new ArrayList<SfTemplate>();
					List<EmsTemplate> ems_list= new ArrayList<EmsTemplate>();
					List<YtoTemplate> yto_list= new ArrayList<YtoTemplate>();
					List<StoTemplate> sto_list= new ArrayList<StoTemplate>();
					List<ImpModel> imp_list= new ArrayList<ImpModel>();
					String bath_id= UUID.randomUUID().toString();
	    			try {
	    				if(mb.equals("1")) {
		    				/*List<String[]> list= XLSXCovertCSVReader.readerExcel(filePath, null, 60);  
		    				list.remove(list.get(0));
		    				for (String[] row: list) {
								SfTemplate sf= new SfTemplate(row,bath_id);
								sf_list.add(sf);
								size= size + 1;
								
		    				}*/
		    				
						} else if(mb.equals("2")) {
		    				List<String[]> list= XLSXCovertCSVReader.readerExcel(filePath, "Sheet1", 42);  
		    				list.remove(list.get(0));
		    				System.out.println(list.get(0));
		    				for (String[] row: list) {
								EmsTemplate ems= new EmsTemplate(row,bath_id);
								ems_list.add(ems);
								size= size + 1;
								
		    				}
		    				
						} else if(mb.equals("3")) {
		    				List<String[]> list= XLSXCovertCSVReader.readerExcel(filePath, "Sheet1", 12);  
		    				list.remove(list.get(0));
		    				for (String[] row: list) {
								YtoTemplate yto= new YtoTemplate(row,bath_id);
								yto_list.add(yto);
								size= size + 1;
								
		    				}
		    				
						} else if(mb.equals("4")) {
		    				List<String[]> list= XLSXCovertCSVReader.readerExcel(filePath, "Sheet1", 26);  
		    				list.remove(list.get(0));
		    				for (String[] row: list) {
								StoTemplate sto= new StoTemplate(row,bath_id);
								sto_list.add(sto);
								size= size + 1;
								
		    				}
		    				
						} else if(mb.equals("5")) {
		    				List<String[]> list= XLSXCovertCSVReader.readerExcel(filePath, null, 16);  
		    				list.remove(list.get(0));
		    				for (String[] row: list) {
		    					ImpModel imp= new ImpModel(row,bath_id,"0");
		    					imp_list.add(imp);
								size= size+ 1;
								
		    				}
		    				
						} else {
							logger.error("类型错误!");
							
						}
	    				
	    			} catch (Exception e) {
	    				e.printStackTrace();
	    				
	    			}
	    			if(mb.equals("1")) {
	    				//SF
	    			/*	for (int i = 0; i < sf_list.size(); i++) {
	    					expertBillServiceImpl.save_sf(sf_list.get(i));
	    					
						}*/
	    				
	    				List<String[]> list= XLSXCovertCSVReader.readerExcel(filePath, null, 60);  
	    				list.remove(list.get(0));
	    				/*for (String[] row: list) {
							SfTemplate sf= new SfTemplate(row,bath_id);
							sf_list.add(sf);
							size= size + 1;
							System.out.println(row);
	    				}*/
	    				int  flag_no=4000;
	    				int  size_no=list.size();
	    			    int  for_no=size_no%flag_no==0?size_no/flag_no:size_no/flag_no+1;
	    			  for(int i=0;i<for_no;i++){
	    				  List<String []> sflist=null;
	    				  sflist =new ArrayList<String []>();
	    				  if(i==for_no-1){
	    					  for(int j=i*flag_no;j<list.size();j++){
	    						  list.get(j)[0]=bath_id;
	    						  sflist.add(list.get(j));
			    				  size= size + 1;
		    				  }   
	    				  }else{
	    					  for(int j=i*flag_no;j<i*flag_no+flag_no;j++){
	    						  list.get(j)[0]=bath_id;  
	    						   sflist.add(list.get(j));
			    				  size= size + 1;
		    				  } 
	    				  }
	    				  expertBillServiceImpl.batchInsert(sflist);
	    			  }	
	    			
	    			
	    			
	    				
					} else if(mb.equals("2")) {
						//EMS
	    				for (int i = 0; i < ems_list.size(); i++) {
	    					expertBillServiceImpl.save_ems(ems_list.get(i));
	    					
						}
	    				
					} else if(mb.equals("3")) {
						//YTO
	    				for (int i = 0; i < yto_list.size(); i++) {
	    					expertBillServiceImpl.save_yto(yto_list.get(i));
						}
					} else if(mb.equals("4")) {
						//STO
	    				for (int i = 0; i < sto_list.size(); i++) {
	    					expertBillServiceImpl.save_sto(sto_list.get(i));
	    					
						}
	    				
					}else if(mb.equals("5")){
						//Imp
	    				for (int i = 0; i < imp_list.size(); i++) {
	    					expertBillServiceImpl.save_imp(imp_list.get(i));
	    					
						}
	    				
					}
	                ExpertBill eb= new ExpertBill();
	                eb.setCreate_time(new Date());
	                eb.setCreate_user(user.getUsername());
	                eb.setInput_time(new Date());
	                eb.setCycle(year+"-"+month);
	                eb.setTemplate(mb);
	                eb.setBatch_id(bath_id);
	                eb.setFile_name(fileName);
	                eb.setCount(size);
	                expertBillServiceImpl.save_eb(eb);
	            	obj.put("code", "200");
	            	obj.put("msg", "成功!");
	            	
		        } else {
		        	obj.put("code", "500");
		        	obj.put("msg", "必填参数为空!");
		        	
		        }
			
			} else {
				obj.put("code", "500");
				obj.put("msg", "同名文件已存在!");
			
			} 
        
        } catch(Exception e) {  
        	if(e.toString().indexOf("NullPointerException")>0){
				System.out.println("空指针!");
				
			}else{
				e.printStackTrace();
            	obj.put("code", "400");
            	obj.put("msg", "系统错误!");
            	
			}
        	
        }
        return obj;
        
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param req
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年3月24日下午8:58:49
	 */
	@RequestMapping("/del")
	public void delete(HttpServletRequest req, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		try {
			out= res.getWriter();
			String id= StringtoInt(req.getParameter("privIds"))[0].toString();
			ExpertBill eb= expertBillServiceImpl.finrByID(id).get(0);
			// 明细删除
			switch(eb.getTemplate()) {
			case "1": expertBillServiceImpl.del_sf(eb.getBatch_id());break;
			case "2": expertBillServiceImpl.del_ems(eb.getBatch_id());break;
			case "3": expertBillServiceImpl.del_yto(eb.getBatch_id());break;
			case "4": expertBillServiceImpl.del_sto(eb.getBatch_id());break;
			
			}
			// 主表删除
			expertBillServiceImpl.del_eb(id);
			// 文件删除
			String fileName= eb.getFile_name();
			String reportName= fileName.substring(0, fileName.length() - 5)+ "差异报表";
			File file= new File(CommonUtils.getAllMessage("config", "BALANCE_DIFFERENCE_EXPRESS_" + OSinfo.getOSname()) + reportName + Constant.SUFFIX_XLSX);
			file.delete();
			file= new File(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname()) + reportName + Constant.SUFFIX_XLSX);
			file.delete();
			out.write("true");
			
		} catch (Exception e) {
			logger.error(e);
			out.write("false");
			
		}

	}

	@RequestMapping("/create_index")
	public void create_index(ModelMap map, HttpServletRequest request){
		try {
			TestLucene.create_index("tb_warehouse_express_data_settlement limit 0,50000",OpenMode.CREATE);
			for (int i = 1; i < 100; i++) {
				TestLucene.create_index("tb_warehouse_express_data_settlement limit "+(i*50000)+",50000",OpenMode.APPEND);
//				System.out.println(i);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年2月22日下午4:04:05
	 */
	@RequestMapping("/differentMatching")
	public void differentMatching(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= differenceMatchingServiceImpl.differentMatching(request, result);
			
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
	* @Title: detailed 
	* @Description: TODO(差异明细) 
	* @param @param map
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping("/detailed")
	public String detailed(ExpertBill eb, ModelMap map, HttpServletRequest request){
		List<ExpertBill> list = expertBillServiceImpl.finrByID(eb.getId().toString());
		String code = "SF";
//		String tableName = "df_sf_template";
		if(null!=list && list.size()!=0){
			ExpertBill expertBill = list.get(0);
			if(expertBill.getTemplate().equals("1")){
				code="SF";
//				tableName = "df_sf_template";
			}else if(expertBill.getTemplate().equals("2")){
				code="EMS";
//				tableName = "df_ems_template";
			}else if(expertBill.getTemplate().equals("3")){
				code="YTO";
//				tableName = "df_yto_template";
			}else if(expertBill.getTemplate().equals("4")){
				code="STO";
//				tableName = "df_sto_template";
			}
			
		}
		return "/lmis/export_excel/difference_" + code + "_detailed";
		
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
