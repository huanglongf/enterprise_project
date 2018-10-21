package com.bt.lmis.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bt.common.base.LoadingType;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.excel.XLSXCovertCSVReader;
import com.bt.lmis.controller.form.AddressQueryParam;
import com.bt.lmis.controller.form.ErrorAddressQueryParam;
import com.bt.lmis.controller.form.JkErrorQuery;
import com.bt.lmis.controller.form.WorkInfoQueryParam;
import com.bt.lmis.model.AddressBean;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.ErrorAddress;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.AddressService;
import com.bt.lmis.service.ContractBasicinfoService;
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
import com.bt.workOrder.bean.WorkBaseInfoBean;
@Controller
@RequestMapping("/control/addressController")
public class AddressController extends BaseController{

	private static final Logger logger = Logger.getLogger(AddressController.class);
	
	@Resource(name="addressServiceImpl")
	private AddressService<AddressBean> addressServiceImpl;

	@Resource(name="addressServiceImpl")
	private AddressService<ErrorAddress> addressErrorServiceImpl;
	
	@Resource(name = "contractBasicinfoServiceImpl")
	private ContractBasicinfoService<ContractBasicinfo> contractBasicinfoServiceImpl;
	
	
	
	@RequestMapping("/load.do")
	public String load(ErrorAddressQueryParam queryParam, HttpServletRequest request) throws Exception{
		try{
			Map<String,Object>param=new HashMap<String,Object>();
			 param.put("level", "1");
			 ArrayList<Map<String,String>>list_province=addressServiceImpl.get_system_address(param); //省
			 param.put("level", "2");
			 ArrayList<Map<String,String>>list_city=addressServiceImpl.get_system_address(param); //市
			 param.put("level", "3");
			 ArrayList<Map<String,String>>list_state=addressServiceImpl.get_system_address(param); //区
			request.setAttribute("list_province", list_province);
			request.setAttribute("list_city", list_city);
			request.setAttribute("list_state", list_state);
		}catch(Exception e){
			logger.error(e);
		}
		return "/lmis/jk_info/error_list";
	}
	
	@RequestMapping("/toload.do")
	public String toload(String bat_id,ErrorAddressQueryParam queryParam, HttpServletRequest request) throws Exception{
		try{
			queryParam.setBat_id(bat_id);
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			logger.error(e);
		}
		return "/lmis/jk_info/data_import";
	}
	
	
	@RequestMapping("/errorlist.do")
	public String getErrorAddressInfo(JkErrorQuery queryParam, HttpServletRequest request) throws Exception{
		try{
			//根据条件查询合同集合
			PageView<ErrorAddress> pageView = new PageView<ErrorAddress>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<ErrorAddress> qrs = addressErrorServiceImpl.findAllErrorData(queryParam);
			pageView.setQueryResult(qrs, queryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			logger.error(e);
		}
		return "/lmis/jk_info/order_page";
	}
	
	
	@RequestMapping("/exportExcel")
	public String exportExcel(JkErrorQuery queryParam,HttpServletRequest request,HttpServletResponse resp){
		try{
			List<Map<String, Object>> qrs =addressServiceImpl.query_export(queryParam);
			String[] headNames = {"主键(请勿操作)","wms系统主键","店铺编码","店铺名称",
					                                 "仓库编码","仓库名称","承运商编码","承运商名称","平台订单号","上位系统订单号",
					                                  "订单类型［作业单类型]","运单号","运输方向(0:正向(发货);1:逆向(退货))",
					                                  "产品类型代码","产品类型名称","运单出库时间","代收货款金额","订单金额","耗材SKU编码",
					                                  "商品数量","实际重量","长","宽","高","体积","始发地","目的地省份","目的地城市","目的地区县","是否报价(0：否；1：是)","是否COD（0：否；1：是)",
					                                  "收件人","联系电话","详细地址","快递交接时间","复核时间","称重时间","异常原因"};
			String fileName = "运费接口异常数据";
			ExcelExportUtil.exportExcelData(qrs, headNames, "sheet1", ExcelExportUtil.OFFICE_EXCEL_2010_POSTFIX,fileName,resp);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	} 
	
	
	@RequestMapping("/exportData")
	public String exportData(JkErrorQuery queryParam,HttpServletRequest request,HttpServletResponse resp){
		try{
			List<Map<String, Object>> qrs =addressServiceImpl.exportData(queryParam);
			String[] headNames = {"主键(请勿操作)","wms系统主键","店铺编码","店铺名称",
					                                 "仓库编码","仓库名称","承运商编码","承运商名称","平台订单号","上位系统订单号",
					                                  "订单类型［作业单类型]","运单号","运输方向(0:正向(发货);1:逆向(退货))",
					                                  "产品类型代码","产品类型名称","运单出库时间","代收货款金额","订单金额","耗材SKU编码",
					                                  "商品数量","实际重量","长","宽","高","体积","始发地","目的地省份","目的地城市","目的地区县","是否报价(0：否；1：是)","是否COD（0：否；1：是)",
					                                  "收件人","联系电话","详细地址","快递交接时间","复核时间","称重时间","异常原因"};
			String fileName = "转换异常数据";
			ExcelExportUtil.exportExcelData(qrs, headNames, "sheet1", ExcelExportUtil.OFFICE_EXCEL_2010_POSTFIX,fileName,resp);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	} 
	
	
	@RequestMapping("import_main")
	public String index(ErrorAddressQueryParam queryParam, HttpServletRequest request) {
		try {
			PageView<ErrorAddress> pageView = new PageView<ErrorAddress>(queryParam.getPageSize() == 0 ? BaseConst.pageSize : queryParam.getPageSize(),queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			Employee user = SessionUtils.getEMP(request);
			QueryResult<ErrorAddress> qrs = addressErrorServiceImpl.getImportMainInfo(queryParam);
			pageView.setQueryResult(qrs, queryParam.getPage());
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/lmis/jk_info/data_import_main";
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
						List<ErrorAddress> oo_list = new ArrayList<ErrorAddress>();
						List<String[]> list = XLSXCovertCSVReader.readerExcel(filePath, null, 60);
						if (list == null || list.size() == 0) {
							obj.put("code", "400");
							obj.put("msg", "文件内容有误!");
							return obj.toString();
						}
						list.remove(list.get(0));
						for (String[] row : list) {
							ErrorAddress oo = new ErrorAddress(row, uuid, user);
							oo_list.add(oo);
						}
						System.out.println("开始调用多线程插入数据");
						int pageSize = 500;
						Map<Object, Object> th_param = new HashMap<Object, Object>();
						th_param.put("orderData", oo_list);
						int t_size = oo_list.size() % pageSize != 0 ? (oo_list.size() / pageSize + 1):oo_list.size() / pageSize;
						List<Future<?>> data_result = ThreadUtil.task(th_param, new InsertData(), "insertData", 1,t_size,6);
						for (Future<?> f : data_result) {
							 @SuppressWarnings("unchecked")
							Map<String,String>result=(Map<String,String>)net.sf.json.JSONObject.fromObject(f.get());
							 if(!"1".equals(result.get("out_result"))){
								 result_flag=0;
							 }
						}
						if(result_flag==1){
							addressServiceImpl.checkImport(uuid);
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
	
	@ResponseBody
	@RequestMapping(value = "/transformation", produces = "application/json;charset=UTF-8")
	public String transformation(String bat_id, HttpServletRequest req) {
		try {
			Map<String, String> param = new HashMap<String, String>();
			Employee user = SessionUtils.getEMP(req);
			param.put("bat_id", bat_id);
			param.put("user_param", user.getName());
			addressServiceImpl.transDataInfo(param);
			return net.sf.json.JSONObject.fromObject(param).toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteImport",produces = "application/json;charset=UTF-8")
	public String deleteImport(String record, HttpServletRequest request) {
		Map<String,String>param=new HashMap<String,String>();
		try{
			
			if (record!=null){
				String[] ids = record.split(",");
				for(String bat_id:ids){
					if(bat_id==null||bat_id.length()==0){
						continue;
					}
					addressServiceImpl.deleteImport(bat_id);
				}
			}
			param.put("out_result","1");
			param.put("out_result_reason","成功");	
		}catch(Exception e){
			e.printStackTrace();
			param.put("out_result","0");
			param.put("out_result_reason","异常");
		}
		return net.sf.json.JSONObject.fromObject(param).toString();
	}	
	
	@RequestMapping("toUpload")
	public String detail(String bat_id,JkErrorQuery queryParam, HttpServletRequest request) {
		try {
			PageView<ErrorAddress> pageView = new PageView<ErrorAddress>(queryParam.getPageSize() == 0 ? BaseConst.pageSize : queryParam.getPageSize(),queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			queryParam.setBat_id(bat_id);
			QueryResult<ErrorAddress> qrs = addressErrorServiceImpl.getImportDetailInfo(queryParam);
			pageView.setQueryResult(qrs, queryParam.getPage());
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/lmis/jk_info/error_page";
	}
	
//	@RequestMapping("toUpload")
//	public String toUpload(String bat_id, HttpServletRequest request) {
//		List<ErrorAddress> list = addressServiceImpl.getImportInfo(bat_id);
//		request.setAttribute("result", list);
//		return "/location/location_import";
//	}
	
	@RequestMapping("/getTab.do")
	public void getTab(ModelMap map, HttpServletRequest request,HttpServletResponse res) throws Exception{
		//合同主数据
		PrintWriter out= res.getWriter();
		out.write(JSONArray.toJSON(addressServiceImpl.getTabData(getParamMap(request))).toString());
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("/saveXzMain.do")
	public void saveXzMain(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		Map<String, Object> result= new HashMap<String, Object>();
		PrintWriter out= null;
		try {
			out= res.getWriter();
			addressServiceImpl.updateXzMainData(getParamMap(request));
			result.put("result_flag", "1");
			result.put("result_reason", "操作成功");
			  
		} catch (Exception e) {
			result.put("result_flag", "1");
			result.put("result_reason", e.getMessage());
			
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("/updateTable.do")
	public void updateTable(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		Map<String, Object> result= new HashMap<String, Object>();
		PrintWriter out = null;
		try {
			 out= res.getWriter();
			 addressServiceImpl.updateTable(getParamMap(request));
			 result.put("result_flag", "1");
			 result.put("result_reason", "操作成功");
			  
		} catch (Exception e) {
			result.put("result_flag", "1");
			result.put("result_reason", e.getMessage());
			
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("/delTabData.do")
	public void delTabData(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		Map<String, Object> result= new HashMap<String, Object>();
		PrintWriter out = null;
		try {
			 out= res.getWriter();
			 Map<String, Object> param= getParamMap(request);
			 addressServiceImpl.delTabData(param);
			 result.put("result_flag", "1");
			 result.put("result_reason", "操作成功");
			  
		} catch (Exception e) {
			result.put("result_flag", "1");
			result.put("result_reason", e.getMessage());
			
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("/updateErrornData.do")
	public void updateErrornData(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		Map<String,String> result= new HashMap<String,String>();
		PrintWriter out = null;
		try {
			out = res.getWriter();
			result=addressErrorServiceImpl.updateErrorData(getParamMap(request));
		} catch (Exception e) {
			result.put("out_result", "1");
			result.put("out_result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param queryParam
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年12月19日下午7:58:09
	 */
	@RequestMapping("/export.do")
	public void export(AddressQueryParam queryParam, HttpServletRequest request, HttpServletResponse response){
		PrintWriter out= null;
		File excel= null;
		try {
			out= response.getWriter();
			excel= addressServiceImpl.exportAddress(queryParam, request);
			out.write(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP")+ excel.getName());
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			out.close();
		}
	}
	
	
	@RequestMapping("/anysis.do")
	public void anysis(AddressQueryParam queryParam, HttpServletRequest request, HttpServletResponse res){
		HashMap<String,String> result= new HashMap<String,String>();
		PrintWriter out = null;
		try {
			out = res.getWriter();
		 	addressErrorServiceImpl.anysisData(new HashMap<String,String>());
		 	result.put("result_flag", "1");
		 	result.put("result_reason", "操作成功");
			  
		} catch (Exception e) {
			result.put("result_flag", "1");
			result.put("result_reason", e.getMessage());
			
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("/add_address.do")
	public void add_address(AddressQueryParam queryParam, HttpServletRequest request, HttpServletResponse res){
		HashMap<String,String> result= new HashMap<String,String>();
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			addressErrorServiceImpl.add_error_address(param);
		 	result.put("result_flag", "1");
		 	result.put("result_reason", "操作成功");
		} catch (Exception e) {
			result.put("result_flag", "1");
			result.put("result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	
	
	@RequestMapping("/refresh_tranaction.do")
	public void refresh_tranaction(String priv_ids,HttpServletRequest request, HttpServletResponse res){
		HashMap<String,String> result= new HashMap<String,String>();
		PrintWriter out = null;
		int success=0;
		try {
			out = res.getWriter();
            String[]ids=priv_ids.split(",");	
            for(int i=0;i<ids.length;i++){
            	Map<String,String>param=new HashMap<String,String>();
            	param.put("id", ids[i]);
            	addressErrorServiceImpl.refresh_tranaction(param);
            	if("1".equals(param.get("out_result"))){
            		success=success+1;
            	}
            }
		 	result.put("result_flag", "1");
		 	result.put("result_reason", "总条数:"+ids.length+"条  "+"成功:"+success+"条");
		} catch (Exception e) {
			result.put("result_flag", "1");
			result.put("result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	
	
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @throws Exception
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年12月19日下午4:33:01
	 */
	@RequestMapping("/toForm.do")
	public String toForm(HttpServletRequest request) throws Exception{
		request.setAttribute("mian_data", addressServiceImpl.getAddress(request));
		return "/lmis/origin_destination_param/origin_destination_form";
	  
	}
	
	@RequestMapping("/updateMainData.do")
	public void updateMainData(HttpServletRequest request, HttpServletResponse res){
		Map<String, String> result= new HashMap<String, String>();
		PrintWriter out= null;
		try {
			 out= res.getWriter();
			 addressServiceImpl.updateMainData(getParamMap(request));
			 result.put("result_flag", "1");
			 result.put("result_reason", "操作成功");
		} catch (Exception e) {
			result.put("result_flag", "1");
			result.put("result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年12月19日下午3:50:50
	 */
	@RequestMapping("/del")
	public void delete(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result= addressServiceImpl.delete(request, result);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result= new JSONObject();
			result.put("result_code", "ERROR");
			result.put("result_content", "操作异常，异常原因:" + CommonUtils.getExceptionStack(e));
			
		}
		
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		out.close();

	}
	
	@RequestMapping("/queryList.do")
	public String queryList(AddressQueryParam queryParam, HttpServletRequest request) {
		try{
			//根据条件查询合同集合
			PageView<AddressBean> pageView= new PageView<AddressBean>(queryParam.getPageSize() == 0? BaseConst.pageSize: queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<AddressBean> qr= addressServiceImpl.query(queryParam);
			pageView.setQueryResult(qr, queryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			
		}
		return "/lmis/origin_destination_param/origin_destination_list_div";
	}
	
	
	
	@RequestMapping("/if_exist_address.do")
	public void getOrderBaseInfo(HttpServletRequest request,HttpServletResponse res) throws Exception{
		res.setContentType("application/json; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try{
			out= res.getWriter();
			Map<String,Object>param=getParamMap(request);
            ArrayList<Map<String,String>>list=addressServiceImpl.get_system_address(param);
            param.put("result", JSONObject.toJSON(list).toString());
    		out.write(JSONObject.toJSON(param).toString());
    		out.flush();
    		out.close();
		}catch(Exception e){
			logger.error(e);
		}

	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param queryParam
	 * @param request
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年12月16日下午12:14:02
	 */
	@RequestMapping("/list.do")
	public String query(AddressQueryParam queryParam, HttpServletRequest request) {
		try{
			request= addressServiceImpl.queryParam(request);
			//根据条件查询合同集合
			PageView<AddressBean> pageView= new PageView<AddressBean>(queryParam.getPageSize() == 0? BaseConst.pageSize: queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<AddressBean> qr= addressServiceImpl.query(queryParam);
			pageView.setQueryResult(qr, queryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			
		}
		return "/lmis/origin_destination_param/origin_destination_list";
		
	}
	
	@RequestMapping("/get_code.do")
	public void query( HttpServletRequest request,HttpServletResponse res) {
		res.setContentType("text; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try{
			out= res.getWriter();
			Map<String,Object>param=getParamMap(request);
            String result=addressServiceImpl.get_business_code();
            System.out.println(result);
            param.put("serial_code", result);
    		out.write(JSONObject.toJSON(param).toString());
    		out.flush();
    		out.close();
		}catch(Exception e){
			logger.error(e);
		}
	}
	
}
