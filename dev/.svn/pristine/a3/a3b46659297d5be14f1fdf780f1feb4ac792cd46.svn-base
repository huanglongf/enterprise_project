package com.bt.lmis.balance.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.balance.common.Constant;
import com.bt.lmis.balance.controller.form.BalanceErrorLogParam;
import com.bt.lmis.balance.controller.form.EstimateQueryParam;
import com.bt.lmis.balance.controller.param.Parameter;
import com.bt.lmis.balance.service.EstimateManagementService;
import com.bt.lmis.balance.service.EstimateService;
import com.bt.lmis.balance.task.OperatingCostDetailTask;
import com.bt.lmis.balance.util.CommonUtil;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.WarehouseExpressDataStoreSettlementQueryParam;
import com.bt.lmis.model.WarehouseExpressDataC;
import com.bt.lmis.model.WarehouseExpressDataSettlementC;
import com.bt.lmis.model.WarehouseExpressDataStoreSettlement;
import com.bt.lmis.model.YFSettlementVo;
import com.bt.lmis.page.PageView;
import com.bt.lmis.service.ExpressContractService;
import com.bt.lmis.service.WarehouseExpressDataCService;
import com.bt.lmis.service.WarehouseExpressDataSettlementCService;
import com.bt.lmis.service.WarehouseExpressDataStoreSettlementService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.ExcelExportUtil;

/** 
 * @ClassName: EstimateController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月19日 上午11:24:31 
 * 
 */
@Controller
@RequestMapping("/control/estimateController")
public class EstimateController extends BaseController{

	private static final Logger logger=Logger.getLogger(EstimateController.class);
	private static ResourceBundle res = ResourceBundle.getBundle("config");
	private static int excelCount = res.getString("EXCEL_COUNT")!=null&&res.getString("EXCEL_COUNT")!=""?Integer.valueOf(res.getString("EXCEL_COUNT")):0;
	
	@Resource(name="estimateManagementServiceImpl")
	private EstimateManagementService estimateManagementService;
	@Resource(name="consumerRevenueEstimateServiceImpl")
	private EstimateService consumerRevenueEstimateService;
	@Resource(name = "expressContractServiceImpl")
	ExpressContractService<T> expressContractService;
	@Resource(name = "warehouseExpressDataSettlementCServiceImpl")
	WarehouseExpressDataSettlementCService warehouseExpressDataSettlementCService;
	@Resource(name = "warehouseExpressDataCServiceImpl")
	WarehouseExpressDataCService warehouseExpressDataCService;
	@Resource(name = "warehouseExpressDataStoreSettlementServiceImpl")
	WarehouseExpressDataStoreSettlementService<T> warehouseExpressDataStoreSettlementService;
	/**
	 * @Title: query
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param param
	 * @param request
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年5月23日 下午7:30:23
	 */
	@RequestMapping("/query")
	public String query(EstimateQueryParam param, HttpServletRequest request) {
		//
		PageView<Map<String, Object>> pageView=new PageView<Map<String, Object>>(param.getPageSize()==0?BaseConst.pageSize:param.getPageSize(),param.getPage());
		param.setFirstResult(pageView.getFirstResult());
		param.setMaxResult(pageView.getMaxresult());
		pageView.setQueryResult(estimateManagementService.query(param),param.getPage()); 
		request.setAttribute("pageView",pageView);
		return "/balance/estimate/"+param.getPageName();
		
	}
	
	/**
	 * 明细
	 * @param param
	 * @param request
	 * @return
	 */
	@RequestMapping("/detail")
	public String detail(HttpServletRequest request) {
		return "/error/settlement_details";
	}
	
	@RequestMapping("/queryError")
	public String queryError(BalanceErrorLogParam param,HttpServletRequest request) {
		try {
			PageView<Map<String, Object>> pageView=new PageView<Map<String, Object>>(param.getPageSize()==0?BaseConst.pageSize:param.getPageSize(),param.getPage());
			param.setFirstResult(pageView.getFirstResult());
			param.setMaxResult(pageView.getMaxresult());
			pageView.setQueryResult(expressContractService.selectByPram(param),param.getPage()); 
			request.setAttribute("pageView",pageView);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(param.getError_type().equals("4")){
		return "/error/sosp/carrier_sr_error";
		}else if(param.getError_type().equals("1")){
		return "/error/sosp/carrier_zc_error";
		}else{
			return "";
		}
	}
	
	@RequestMapping("/queryUnsett")
	public String queryUnsett(WarehouseExpressDataC param,HttpServletRequest request) {
		try {
			PageView<WarehouseExpressDataC> pageView=new PageView<WarehouseExpressDataC>(param.getPageSize()==0?BaseConst.pageSize:param.getPageSize(),param.getPage());
			param.setFirstResult(pageView.getFirstResult());
			param.setMaxResult(pageView.getMaxresult());
			pageView.setQueryResult(warehouseExpressDataCService.findAll(param),param.getPage()); 
			request.setAttribute("pageView",pageView);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(param.getErrorType().equals("4")){
			return "/error/sosp/carrier_sr_unsettlement";
		}else if(param.getErrorType().equals("1")){
			return "/error/sosp/carrier_zc_unsettlement";
		}else{
			return "";
		}
	}
	@RequestMapping("/queryZCSettle")
	public String queryZCSettle(WarehouseExpressDataSettlementC param,HttpServletRequest request) {
		try {
			PageView<WarehouseExpressDataSettlementC> pageView=new PageView<WarehouseExpressDataSettlementC>(param.getPageSize()==0?BaseConst.pageSize:param.getPageSize(),param.getPage());
			param.setFirstResult(pageView.getFirstResult());
			param.setMaxResult(pageView.getMaxresult());
			pageView.setQueryResult(warehouseExpressDataSettlementCService.findAll(param),param.getPage()); 
			request.setAttribute("pageView",pageView);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/error/sosp/carrier_zc_settlemented";
	}
	
	@RequestMapping("/querySRSettle")
	public String querySRSettle(WarehouseExpressDataStoreSettlementQueryParam param,HttpServletRequest request) {
		try {
			PageView<WarehouseExpressDataStoreSettlement> pageView=new PageView<WarehouseExpressDataStoreSettlement>(param.getPageSize()==0?BaseConst.pageSize:param.getPageSize(),param.getPage());
			param.setFirstResult(pageView.getFirstResult());
			param.setMaxResult(pageView.getMaxresult());
			pageView.setQueryResult(warehouseExpressDataStoreSettlementService.findAll(param),param.getPage()); 
			request.setAttribute("pageView",pageView);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/error/sosp/carrier_sr_settlemented";
	}
	
	/**
	 * 导出支出Excel
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/exportZCYFExcel")
	public String exportZCYFExcel(YFSettlementVo map,HttpServletRequest request,HttpServletResponse resp){
		String tabMark =  map.getTabMark();
		if(tabMark==null){
			return "发生了一些怪怪的事情，没有收到tabMark";
		}
		try{
			if(tabMark.equals("sett")){
				//不需要errorType
				int getCount = warehouseExpressDataSettlementCService.getCount(map);
				if(getCount>excelCount){
					return "data number bigger than "+excelCount;
				}
				List<Map<String, Object>> qrs = warehouseExpressDataSettlementCService.queryExport(map);
				String[] headNames = {"合同名称","成本中心","店铺代码","店铺名称","仓库","运输公司代码","快递名称","发货指令","上位系统订单号","订单类型",
						"运单号","运输方向","产品类型代码","产品类型名称","运输时间","代收货款金额","订单金额","耗材SKU编码","实际重量","长",
						"宽","高","体积","始发地","省","市","区","街道","是否保价","是否cod"};
				String fileName = "支出运费已结算";
				ExcelExportUtil.exportExcelData(qrs, headNames, "sheet1", ExcelExportUtil.OFFICE_EXCEL_2003_POSTFIX,fileName,resp);
			}
			else if(tabMark.equals("unsett")){
				//需要errorType
				int getCount = warehouseExpressDataCService.getCount(map);
				if(getCount>excelCount){
					return "data number bigger than "+excelCount;
				}
				List<Map<String, Object>> qrs = warehouseExpressDataCService.queryExport(map);
				String[] headNames = {"成本中心","店铺代码","店铺名称","仓库","运输公司代码","快递名称","发货指令","上位系统订单号","订单类型",
						"运单号","运输方向","产品类型代码","产品类型名称","运输时间","代收货款金额","订单金额","耗材SKU编码","实际重量","长",
						"宽","高","体积","始发地","省","市","区","街道","详细地址","收件人","电话","是否保价","是否cod"};
				String fileName = "支出运费未结算";
				ExcelExportUtil.exportExcelData(qrs, headNames, "sheet1", ExcelExportUtil.OFFICE_EXCEL_2003_POSTFIX,fileName,resp);
			}
			else if(tabMark.equals("error")){
				//需要errorType
				int getCount = expressContractService.getCount(map);
				if(getCount>excelCount){
					return "data number bigger than "+excelCount;
				}
				List<Map<String, Object>> qrs = expressContractService.queryExport(map);
				String[] headNames = {"处理结果信息","合同名称","成本中心","店铺编码","店铺名称","仓库编码","仓库名称","承运商代码","承运商","平台订单号",
						"前置单据号","订单类型","运单号","运输方向","产品类型代码","产品类型名称","订单生成时间","代收货款金额","订单金额","耗材SKU编码","实际重量",
						"长","宽","高","体积","始发地","省","市","区","街道","详细地址","是否保价","是否cod"};
				String fileName = "支出运费异常信息";
				ExcelExportUtil.exportExcelData(qrs, headNames, "sheet1", ExcelExportUtil.OFFICE_EXCEL_2003_POSTFIX,fileName,resp);
			}
		}catch(Exception e){
			e.printStackTrace();
			return "exception";
		}
		return "success";
	} 
	/**
	 * 导出收入Excel
	 * @param queryParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/exportSRYFExcel")
	public String exportSRYFExcel(YFSettlementVo map,HttpServletRequest request,HttpServletResponse resp){
		String tabMark =  map.getTabMark();
		if(tabMark==null){
			return "发生了一些怪怪的事情，没有收到tabMark";
		}
		try{
			if(tabMark.equals("sett")){
				//不需要errorType
				int getCount = warehouseExpressDataStoreSettlementService.getCount(map);
				if(getCount>excelCount){
					return "data number bigger than "+excelCount;
				}
				List<Map<String, Object>> qrs = warehouseExpressDataStoreSettlementService.queryExport(map);
				String[] headNames = {"合同名称","成本中心","店铺代码","店铺名称","仓库","运输公司代码","快递名称","发货指令","上位系统订单号","订单类型",
						"运单号","运输方向","产品类型代码","产品类型名称","运输时间","代收货款金额","订单金额","耗材SKU编码","实际重量","长",
						"宽","高","体积","始发地","省","市","区","街道","计费重量","标准运费","折扣率","折后运费","保价费","是否保价","是否cod"};
				String fileName = "收入运费已结算";
				ExcelExportUtil.exportExcelData(qrs, headNames, "sheet1", ExcelExportUtil.OFFICE_EXCEL_2003_POSTFIX,fileName,resp);
			}else if(tabMark.equals("unsett")){
				//需要errorType
				int getCount = warehouseExpressDataCService.getCount(map);
				if(getCount>excelCount){
					return "data number bigger than "+excelCount;
				}
				List<Map<String, Object>> qrs = warehouseExpressDataCService.queryExport(map);
				String[] headNames = {"成本中心","店铺代码","店铺名称","仓库","运输公司代码","快递名称","发货指令","上位系统订单号","订单类型",
						"运单号","运输方向","产品类型代码","产品类型名称","运输时间","代收货款金额","订单金额","耗材SKU编码","实际重量","长",
						"宽","高","体积","始发地","省","市","区","街道","详细地址","收件人","电话","是否保价","是否cod"};
				String fileName = "收入运费未结算";
				ExcelExportUtil.exportExcelData(qrs, headNames, "sheet1", ExcelExportUtil.OFFICE_EXCEL_2003_POSTFIX,fileName,resp);
			}else 
				if(tabMark.equals("error")){
				//需要errorType
				int getCount = expressContractService.getCount(map);
				if(getCount>excelCount){
					return "data number bigger than "+excelCount;
				}
				List<Map<String, Object>> qrs = expressContractService.queryExport(map);
				String[] headNames = {"处理结果信息","合同名称","成本中心","店铺编码","店铺名称","仓库编码","仓库名称","承运商代码","承运商","平台订单号",
						"前置单据号","订单类型","运单号","运输方向","产品类型代码","产品类型名称","订单生成时间","代收货款金额","订单金额","耗材SKU编码","实际重量",
						"长","宽","高","体积","始发地","省","市","区","街道","详细地址","是否保价","是否cod"};
				String fileName = "收入运费异常信息";
				ExcelExportUtil.exportExcelData(qrs, headNames, "sheet1", ExcelExportUtil.OFFICE_EXCEL_2003_POSTFIX,fileName,resp);
			}
		}catch(Exception e){
			e.printStackTrace();
			return "exception";
		}
		return "success";
	} 
	
	/**
	 * @Title: shiftContractByType
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @param response
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月23日 下午7:30:27
	 */
	@RequestMapping("/shiftContractByType")
	public void shiftContractByType(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma","no-cache");
		response.setHeader("cache-control","no-cache");
		PrintWriter out=null;
		JSONObject result=new JSONObject();
		try {
			result.put("contract",estimateManagementService.shiftContractByType(CommonUtil.generateParameter(request)));
			result.put("result_code","SUCCESS");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result.put("result_code","ERROR");
			result.put("result_content","操作失败,失败原因:"+ e.getMessage());
			
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
	 * @Title: queryContract
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年5月24日 下午2:16:20
	 */
	@RequestMapping("/queryContract")
	public String queryContract(HttpServletRequest request) {
		try {
			request.setAttribute("contract", estimateManagementService.shiftContractByType(CommonUtil.generateParameter(request)));
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return "/balance/estimate/contract";
		
	}
	
	/**
	 * @Title: download
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @param response
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年5月24日 下午9:29:16
	 */
	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out=null;
		try {
			out=response.getWriter();
			out.write(CommonUtils.getAllMessage("config","COMMON_DOWNLOAD_MAP")+request.getParameter("batchNumber")+Constant.SYM_POINT+Constant.FILE_TYPE_SUFFIX_ZIP);
			out.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		} finally {
			out.close();
			
		}
		
	}
	
	@RequestMapping("/operateEstimate")
	public void operateEstimate(HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma","no-cache");
		response.setHeader("cache-control","no-cache");
		PrintWriter out=null;
		JSONObject result=new JSONObject();
		//
		Lock lock=new ReentrantLock(true);
		try {
			Parameter parameter=CommonUtil.generateParameter(request);
			if(lock.tryLock(15, TimeUnit.SECONDS)) {
				switch(parameter.getParam().get("order").toString()) {
				case Constant.ESTIMATE_OPERATION_ADD:
					result.put("retrunBatchNumber", estimateManagementService.add(parameter));
					break;
				case Constant.ESTIMATE_OPERATION_DEL:
					estimateManagementService.del(parameter);
					break;
				case Constant.ESTIMATE_OPERATION_CAN:
					estimateManagementService.cancel(parameter);
					break;
				case Constant.ESTIMATE_OPERATION_RES:
					estimateManagementService.restart(parameter);
				break;
				default:break;
				}
				
			}
			result.put("result_code","SUCCESS");
			result.put("result_content","操作成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result.put("result_code","ERROR");
			result.put("result_content","操作失败,失败原因:"+ e.getMessage());
			
		} finally {
			lock.unlock();
			
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

	@RequestMapping("/consumerRevenueEstimateServiceImplTest")
	public void consumerRevenueEstimateServiceImplTest(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("测试开始");
		
		OperatingCostDetailTask operatingCostDetailTask = new OperatingCostDetailTask();
		operatingCostDetailTask.invoke();
		
		
		System.out.println("测试结束");
	}
	
}