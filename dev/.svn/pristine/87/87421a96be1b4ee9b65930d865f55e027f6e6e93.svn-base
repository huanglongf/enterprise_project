package com.bt.lmis.controller;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.excel.XLSXCovertCSVReader;
import com.bt.lmis.controller.form.ErrorAddressQueryParam;
import com.bt.lmis.controller.form.OperatorQueryParam;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.ErrorAddress;
import com.bt.lmis.model.JkErrorBean;
import com.bt.lmis.model.JkcountBean;
import com.bt.lmis.model.OperatorBean;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.JkErrorService;
import com.bt.lmis.utils.HttpRequestUtil;
import com.bt.thread.InsertData;
import com.bt.thread.ThreadUtil;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.ExcelExportUtil;
import com.bt.utils.SessionUtils;
/**
 * 
* @ClassName: AddressController 
* @Description: 地址参数维护
* @author Likun
* @date 2016年6月7日 上午10:24:22 
 */
@Controller
@RequestMapping("/control/JkErrorController")
public class JkErrorDataController extends BaseController{

	private static final Logger logger = Logger.getLogger(JkErrorDataController.class);
	
	@Resource(name="jkErrorServiceImpl")
	private JkErrorService  jkErrorServiceImpl;

	@RequestMapping("/errorlist.do")
	public String getErrorAddressInfo(ErrorAddressQueryParam queryParam, HttpServletRequest request) throws Exception{
		try{
			//根据条件查询合同集合
			PageView<JkErrorBean> pageView = new PageView<JkErrorBean>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<JkErrorBean> qrs = jkErrorServiceImpl.findAllErrorData(queryParam);
			pageView.setQueryResult(qrs, queryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			logger.error(e);
			
		}
		return "/lmis/jk_error_list";
	}
	
	@RequestMapping("/JKlist.do")
	public String getJkInfo(ErrorAddressQueryParam queryParam, HttpServletRequest request) throws Exception{
		try{
			//根据条件查询合同集合
			PageView<JkcountBean> pageView = new PageView<JkcountBean>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			queryParam.setIsError(request.getParameter("isError"));
			queryParam.setJkCode(request.getParameter("jkCode"));		
			System.out.println(request.getParameter("isError")+"/"+request.getParameter("jkCode"));
			QueryResult<JkcountBean> qrs = jkErrorServiceImpl.findjKData(queryParam);
			pageView.setQueryResult(qrs, queryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			logger.error(e);
			
		}
		return "/lmis/jk_count_list";
	}
	
	
	@RequestMapping("/wmsSearch.do")
	public void wmsSearch(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		Map<String,String> param= new HashMap<String,String>();
		PrintWriter out = null;
		Map<String,String> result=null;
		try {
			out = res.getWriter();
			Map<String,Object>par=getParamMap(request);
	        
	        String startDate=request.getParameter("startDate");
	        if("2".equals(par.get("flag"))){
	        	String start=par.get("startData").toString();
	        	String end=par.get("endData").toString();
	        	long c=CommonUtils.date_minus_gethour(start,end);
        		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        		SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd HH");
        		startDate=format.format(format2.parse(start));
	        	for(int i=0;i<c;i++){
	        		startDate=CommonUtils.dateAdd(startDate,1);
	        		param.put("flag","2");
	        		result=getLink(param,startDate);
	        	}
	        }else{	 
	        param.put("flag","0");
	        result=getLink(param,startDate);  
	        }
			param.put("out_result_reason",result.get("linkInfo"));	
		} catch (Exception e) {
			param.put("out_result", "0");
			param.put("out_result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(param).toString());
		out.flush();
		out.close();
	}

	public Map<String,String> getLink(Map<String,String> param,String startDate){
		String url="http://10.7.46.46:8081/bt-ins/insToWms/wmsSearch.do";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>(); 
        String end_date=CommonUtils.dateAdd(startDate,1);
        nvps.add(new BasicNameValuePair("startDate",startDate));  
        nvps.add(new BasicNameValuePair("endDate",end_date));
        nvps.add(new BasicNameValuePair("flag",param.get("flag").toString()));
		Map<String,String> result=HttpRequestUtil.sendPost2(url,nvps);
		if("1".equals(result.get("linkFlag"))){
			param.put("out_result", "1");
		}else{
			param.put("out_result", "0");
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getNew.do")
	public void getNew(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		Map<String,String> param= new HashMap<String,String>();
		PrintWriter out = null;
		String url="http://10.7.46.46:8081/bt-ins/insToWms/getData.do";
		try {
			out = res.getWriter();
			Map<String,Object>par=getParamMap(request);
	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
	        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHH");
	        String startDate=request.getParameter("countTimeParam");
	        String end_date=CommonUtils.dateAdd(startDate,1);
	        System.out.println(startDate+"/"+end_date);
	        nvps.add(new BasicNameValuePair("startDate",startDate));  
	        nvps.add(new BasicNameValuePair("endDate",end_date));
	        nvps.add(new BasicNameValuePair("method","getExpressWaybill"));
			param.put("status","2");
			param.put("jkCode","getOrderData");
			param.put("countTimeParam",request.getParameter("countTimeParam"));
			jkErrorServiceImpl.upStatus(param);
			Map<String,String> result=HttpRequestUtil.sendPost2(url,nvps);
			if("1".equals(result.get("linkFlag"))){
				param.put("out_result", "1");
			}else{
				param.put("out_result", "0");
			}
			param.put("out_result_reason",result.get("linkInfo"));	
		} catch (Exception e) {
			param.put("out_result", "0");
			param.put("out_result_reason", e.getMessage());
		}finally {
			param.put("status","0");
			jkErrorServiceImpl.upStatus(param);
		}
		out.write(JSONObject.toJSON(param).toString());
		out.flush();
		out.close();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateJkData.do")
	public void updateJkData(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		Map<String,String> param= new HashMap<String,String>();
		PrintWriter out = null;
		try {
			out = res.getWriter();
			param.put("out_result", "1");
			param.put("out_result_reason", "成功");
			param=jkErrorServiceImpl.dealjkDataPro(param);
		} catch (Exception e) {
			param.put("out_result", "1");
			param.put("out_result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(param).toString());
		out.flush();
		out.close();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/upStatus.do")
	public void upStatus(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		Map<String,String> param= new HashMap<String,String>();
		PrintWriter out = null;
		try {
			out = res.getWriter();
			param.put("out_result", "1");
			param.put("out_result_reason", "成功");
			param.put("jkCode","getOrderData");
			param.put("countTimeParam",request.getParameter("countTimeParam"));
			param.put("status",request.getParameter("status"));
			jkErrorServiceImpl.upStatus(param);
		} catch (Exception e) {
			param.put("out_result", "1");
			param.put("out_result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(param).toString());
		out.flush();
		out.close();
	}	
	
	
	@RequestMapping("/updateErrornData.do")
	public void reIntoTable(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		Map<String,String> param= new HashMap<String,String>();
		PrintWriter out = null;
		try {
			out = res.getWriter();
			param.put("out_result", "1");
			param.put("out_result_reason", "成功");
			param.put("id",(String)getParamMap(request).get("id"));
			param=jkErrorServiceImpl.updateErrorData(param);
		} catch (Exception e) {
			param.put("out_result", "1");
			param.put("out_result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(param).toString());
		out.flush();
		out.close();
	}
	
	
	
	
	
	/**接口操作费**/
	
	@RequestMapping("/erroroperlist.do")
	public String getErrorOperatorInfo(ErrorAddressQueryParam queryParam, HttpServletRequest request) throws Exception{
		try{
			//根据条件查询合同集合
			PageView<ErrorAddress> pageView = new PageView<ErrorAddress>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<ErrorAddress> qrs = jkErrorServiceImpl.findOperatorData(queryParam);
			pageView.setQueryResult(qrs, queryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			logger.error(e);
			
		}
		return "/lmis/jk_info/error_oper_list";
		
	}
	
	
	@RequestMapping("/exportExcel")
	public String exportExcel(OperatorQueryParam queryParam,HttpServletRequest request,HttpServletResponse resp){
		try{
			List<Map<String, Object>> qrs =jkErrorServiceImpl.query_export(queryParam);
			String[] headNames = {"主键(请勿操作)","wms系统主键","店铺编码","店铺名称",
					                                 "仓库编码","仓库名称","订单类型","作业单号","相关单号","作业类型名称",
					                                  "库位编码","入库数量","出库数量",
					                                  "商品编码","sku编码","货号","商品名称","商品大小","库存状态",
					                                  "是否为耗材 1：是，0：不是","品牌对接编码","条形码","操作费时间","平台订单号","操作人","仓库类型 0:自营仓 1:外包仓","异常原因"};
			String fileName = "操作费接口异常数据";
			ExcelExportUtil.exportExcelData(qrs, headNames, "sheet1", ExcelExportUtil.OFFICE_EXCEL_2010_POSTFIX,fileName,resp);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	} 
	
	@RequestMapping("/exportData")
	public String exportData(OperatorQueryParam queryParam,HttpServletRequest request,HttpServletResponse resp){
		try{
			List<Map<String, Object>> qrs =jkErrorServiceImpl.exportData(queryParam);
			String[] headNames = {"主键(请勿操作)","wms系统主键","店铺编码","店铺名称",
					                                 "仓库编码","仓库名称","订单类型","作业单号","相关单号","作业类型名称",
					                                  "库位编码","入库数量","出库数量",
					                                  "商品编码","sku编码","货号","商品名称","商品大小","库存状态",
					                                  "是否为耗材 1：是，0：不是","品牌对接编码","条形码","操作费时间","平台订单号","操作人","仓库类型 0:自营仓 1:外包仓","异常原因"};
			String fileName = "操作费接口异常数据";
			ExcelExportUtil.exportExcelData(qrs, headNames, "sheet1", ExcelExportUtil.OFFICE_EXCEL_2010_POSTFIX,fileName,resp);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	} 
	
	@RequestMapping("import_main")
	public String index(OperatorQueryParam queryParam, HttpServletRequest request) {
		try {
			PageView<ErrorAddress> pageView = new PageView<ErrorAddress>(queryParam.getPageSize() == 0 ? BaseConst.pageSize : queryParam.getPageSize(),queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			Employee user = SessionUtils.getEMP(request);
			QueryResult<ErrorAddress> qrs = jkErrorServiceImpl.getImportMainInfo(queryParam);
			pageView.setQueryResult(qrs, queryParam.getPage());
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/lmis/jk_info/oper_import_main";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/import", produces = "application/json;charset=UTF-8")
	public String imports(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse res) {
		int result_flag=1;
		Employee user = SessionUtils.getEMP(req);
		JSONObject obj = new JSONObject();
		if (null != user) {
			try {
				String fileName = file.getOriginalFilename();
				String prefix = fileName.split("[.]")[fileName.split("[.]").length - 1];
				obj.put("code", "400");
				obj.put("msg", "系统错误!");
				// 判断文件是否为空
				if (!file.isEmpty()) {
					if (prefix.equals("xlsx")) {
						// 文件保存路径
						String uuid = UUID.randomUUID().toString();
						String filePath = CommonUtils.getAllMessage("config", "BALANCE_DIFFERENCE_UPLOAD_BILL")
								+ (uuid + "." + prefix);
						// 转存文件
						file.transferTo(new File(filePath));
						// 文件内容集合
						List<OperatorBean> oo_list = new ArrayList<OperatorBean>();
						List<String[]> list = XLSXCovertCSVReader.readerExcel(filePath, null, 60);
						if (list == null || list.size() == 0) {
							obj.put("code", "400");
							obj.put("msg", "文件内容有误!");
							return obj.toString();
						}
						list.remove(list.get(0));
						for (String[] row : list) {
							OperatorBean oo = new OperatorBean(row, uuid, user);
							oo_list.add(oo);
						}
						System.out.println("开始调用多线程插入数据");
						int pageSize = 500;
						Map<Object, Object> th_param = new HashMap<Object, Object>();
						th_param.put("OperatorData", oo_list);
						int t_size = oo_list.size() % pageSize != 0 ? (oo_list.size() / pageSize + 1):oo_list.size() / pageSize;
						List<Future<?>> data_result = ThreadUtil.task(th_param, new InsertData(), "insertOperData", 1,t_size,6);
						for (Future<?> f : data_result) {
							 @SuppressWarnings("unchecked")
							Map<String,String>result=(Map<String,String>)net.sf.json.JSONObject.fromObject(f.get());
							 if(!"1".equals(result.get("out_result"))){
								 result_flag=0;
							 }
						}
						if(result_flag==1){
							jkErrorServiceImpl.checkImport(uuid);
						}else{
							obj.put("code", "500");
							obj.put("msg", "上传异常!");		
							return obj.toString();
						}
						obj.put("code", "200");
						obj.put("msg", "上传成功!");
					} else {
						obj.put("code", "500");
						obj.put("msg", "文件类型必须为[.xlsx],您上传的文件类型为[." + prefix + "]!");
						return obj.toString();
					}
				} else {
					obj.put("code", "500");
					obj.put("msg", "必填参数为空!");
					return obj.toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
				obj.put("code", "400");
				obj.put("msg", "系统错误!");
			}
		} else {
			obj.put("code", "400");
			obj.put("msg", "Session过期,请重新登陆后在操作!");
		}
		return obj.toString();
	}
	
	
	@RequestMapping("toUpload")
	public String detail(String bat_id,OperatorQueryParam queryParam, HttpServletRequest request) {
		try {
			PageView<ErrorAddress> pageView = new PageView<ErrorAddress>(queryParam.getPageSize() == 0 ? BaseConst.pageSize : queryParam.getPageSize(),queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			queryParam.setBat_id(bat_id);
			QueryResult<ErrorAddress> qrs = jkErrorServiceImpl.getImportDetailInfo(queryParam);
			pageView.setQueryResult(qrs, queryParam.getPage());
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/lmis/jk_info/oper_data_import";
	}
	
}
