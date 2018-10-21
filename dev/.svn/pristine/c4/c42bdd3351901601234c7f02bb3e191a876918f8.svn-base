package com.bt.lmis.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.bt.OSinfo;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.WareImportErrorQueryParam;
import com.bt.lmis.controller.form.WareImportTaskQueryParam;
import com.bt.lmis.controller.form.WareParkQueryParam;
import com.bt.lmis.controller.form.WareRelationQueryParam;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.Store;
import com.bt.lmis.model.WareImportTask;
import com.bt.lmis.model.WarePark;
import com.bt.lmis.model.WareRelation;
import com.bt.lmis.model.Warehouse;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.StoreService;
import com.bt.lmis.service.WareImportErrorService;
import com.bt.lmis.service.WareImportTaskService;
import com.bt.lmis.service.WareParkService;
import com.bt.lmis.service.WareRelationService;
import com.bt.lmis.service.WarehouseService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.ExcelExportUtil;
import com.bt.utils.SessionUtils;
import com.bt.utils.XLSXCovertCSVReader;

@Controller
@RequestMapping("/control/wareParkController")
public class WareParkController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(WareParkController.class);
	
	@Resource(name = "wareParkServiceImpl")
	private WareParkService wareParkService;
	
	@Resource(name = "wareRelationServiceImpl")
	private WareRelationService wareRelationService;
	
	@Resource(name = "storeServiceImpl")
	private StoreService<T> storeService;

	@Resource(name = "warehouseServiceImpl")
	private WarehouseService<T> warehouseService;

	@Resource(name = "wareImportTaskServiceImpl")
	private WareImportTaskService wareImportTaskService;

	@Resource(name = "wareImportErrorServiceImpl")
	private WareImportErrorService wareImportErrorService;
	
	/**
	 * Title: getList
	 * Description: 园区列表分页查询
	 * @param queryParam
	 * @param map
	 * @param request
	 * @return
	 * @author jindong.lin
	 * @date 2017年12月20日
	 */
	@RequestMapping("list")
	public String getList(WareParkQueryParam queryParam,ModelMap map, HttpServletRequest request){
		if(queryParam == null){
			queryParam = new WareParkQueryParam();
		}
		try{
			//根据条件查询合同集合
			PageView<WareParkQueryParam> pageView = new PageView<WareParkQueryParam>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<WareParkQueryParam> qr = wareParkService.findAll(queryParam);
			pageView.setQueryResult(qr, queryParam.getPage());
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return "/lmis/ware_park/ware_park";
	}
	
	/**
	 * Title: delByIds
	 * Description: 批量删除
	 * @param ids
	 * @param model
	 * @return
	 * @author jindong.lin
	 * @date 2017年12月21日
	 */
	@RequestMapping("delByIds")
	@ResponseBody
	public String delByIds(String ids,Model model){
		
		Map<String,String> result = new HashMap<String,String>();
		
		try {
			
			int r = wareParkService.delByIds(ids);
			
			if(r>0){
				result.put("out_result", "1");
				result.put("out_result_reason","删除成功");
			} else {
				result.put("out_result", "0");
				result.put("out_result_reason","删除失败");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason","删除异常");
		}

		return JSONObject.toJSONString(result);
	}

	/**
	 * Title: delDetailByIds
	 * Description: 明细数据批量删除
	 * @param ids
	 * @param model
	 * @return
	 * @author jindong.lin
	 * @date 2017年12月21日
	 */
	@RequestMapping("delDetailByIds")
	@ResponseBody
	public String delDetailByIds(String ids,Model model){
		
		Map<String,String> result = new HashMap<String,String>();
		
		try {

			int r = wareRelationService.delByIds(ids);
			
			if(r>0){
				result.put("out_result", "1");
				result.put("out_result_reason","删除成功");
			} else {
				result.put("out_result", "0");
				result.put("out_result_reason","删除失败");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason","删除异常");
		}

		return JSONObject.toJSONString(result);
	}
	
	/**
	 * Title: delImportTaskByIds
	 * Description: 导入任务历史记录批量删除
	 * @param ids
	 * @param model
	 * @return
	 * @author jindong.lin
	 * @date 2017年12月25日
	 */
	@RequestMapping("delImportTaskByIds")
	@ResponseBody
	public String delImportTaskByIds(String ids,Model model){
		
		Map<String,String> result = new HashMap<String,String>();

		try {

			int r = wareImportTaskService.delByIds(ids);
			
			if(r>0){
				result.put("out_result", "1");
				result.put("out_result_reason","删除成功");
			} else {
				result.put("out_result", "0");
				result.put("out_result_reason","删除失败");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason","删除异常");
		}


		return JSONObject.toJSONString(result);
	}
	
	/**
	 * Title: toShowDetail
	 * Description: 园区明细页跳转
	 * @param wareParkQueryParam
	 * @param model
	 * @return
	 * @author jindong.lin
	 * @date 2017年12月20日
	 */
	@RequestMapping("toShowDetail")
	public String toShowDetail(WareParkQueryParam wareParkQueryParam,Model model){
		
		if(wareParkQueryParam != null){
			wareParkQueryParam = wareParkService.selectByPrimaryKey(wareParkQueryParam.getId());
			model.addAttribute("model",wareParkQueryParam);
			model.addAttribute("parkId",wareParkQueryParam.getId());
		} else {
			model.addAttribute("model",new WareParkQueryParam());
			model.addAttribute("parkId","");
		}

		return "/lmis/ware_park/ware_park_detail";
	}
	
	/**
	 * Title: toSaveForm
	 * Description: 
	 * @param wareParkQueryParam
	 * @param model
	 * @return
	 * @author jindong.lin
	 * @date 2017年12月20日
	 */
	@RequestMapping("toSaveForm")
	public String toSaveForm(WareParkQueryParam wareParkQueryParam,Model model){
		
		if(wareParkQueryParam != null && StringUtils.isNotBlank(wareParkQueryParam.getId())){
			wareParkQueryParam = wareParkService.selectByPrimaryKey(wareParkQueryParam.getId());
			model.addAttribute("model",wareParkQueryParam);
			model.addAttribute("parkId",wareParkQueryParam.getId());
		} else {
			model.addAttribute("model",new WareParkQueryParam());
			model.addAttribute("parkId","");
		}

		return "/lmis/ware_park/ware_park_save";
	}

	/**
	 * Title: loadWarehouseAndStore
	 * Description: 加载店铺、仓库下拉框
	 * @param wareParkQueryParam
	 * @param model
	 * @return
	 * @author jindong.lin
	 * @date 2017年12月25日
	 */
	@RequestMapping("loadWarehouseAndStore")
	@ResponseBody
	public String loadWarehouseAndStore(WareParkQueryParam wareParkQueryParam,Model model){
		
		Map<String,List<Map<String, Object>>> result = new HashMap<String,List<Map<String, Object>>>();
		
		result.put("storeList", storeService.findAll());
		result.put("warehouseList", warehouseService.findAll());

		return JSONObject.toJSONString(result);
	}

	/**
	 * Title: doSaveForm
	 * Description: 保存记录
	 * @param warePark
	 * @param model
	 * @return
	 * @author jindong.lin
	 * @date 2017年12月20日
	 */
	@RequestMapping("doSaveForm")
	@ResponseBody
	public String doSaveForm(WarePark warePark, HttpServletRequest request){
		
		Map<String,String> result = new HashMap<String,String>();
		
		if(warePark != null){
			//校验
			Map<String,BigDecimal> resultRow = wareParkService.checkWareParkParam(warePark);
			
			if(resultRow == null){
				resultRow = new HashMap<String,BigDecimal>();
				resultRow.put("parkCodeResult", new BigDecimal(0));
				resultRow.put("parkNameResult", new BigDecimal(0));
				resultRow.put("parkCostCenterResult", new BigDecimal(0));
			}
			
			if(resultRow.get("parkCodeResult").compareTo(new BigDecimal(0))== 0 &&
					resultRow.get("parkNameResult").compareTo(new BigDecimal(0)) == 0 &&
						resultRow.get("parkCostCenterResult").compareTo(new BigDecimal(0)) == 0){

				int r = 0;
				if(StringUtils.isNotBlank(warePark.getId())){
					r = wareParkService.updateByPrimaryKeySelective(warePark);
				} else {
					Employee user = SessionUtils.getEMP(request);
					warePark.setId(UUID.randomUUID().toString().replace("-", ""));
					warePark.setCreateTime(new Date());
					warePark.setCreateUser(user.getId().toString());
					r = wareParkService.insertSelective(warePark);
				}
				
				if(r>0){
					result.put("out_result", "1");
					result.put("out_result_reason","保存成功");
				} else {
					result.put("out_result", "0");
					result.put("out_result_reason","保存失败");
				}
			} else if (resultRow.get("parkCodeResult").compareTo(new BigDecimal(0)) > 0) {
				result.put("out_result", "0");
				result.put("out_result_reason","园区代码已存在");
			} else if (resultRow.get("parkNameResult").compareTo(new BigDecimal(0)) > 0) {
				result.put("out_result", "0");
				result.put("out_result_reason","园区名称已存在");
			} else if (resultRow.get("parkCostCenterResult").compareTo(new BigDecimal(0)) > 0) {
				result.put("out_result", "0");
				result.put("out_result_reason","园区成本中心代码已存在");
			} else {
				result.put("out_result", "0");
				result.put("out_result_reason","未通过校验");
			}
		} else {
			result.put("out_result", "0");
			result.put("out_result_reason","参数异常");
		}

		return JSONObject.toJSONString(result);
	}
	
	/**
	 * Title: doSaveDetail
	 * Description: 园区关系保存(添加修改)
	 * @param warePark
	 * @param request
	 * @return
	 * @author jindong.lin
	 * @date 2017年12月21日
	 */
	@RequestMapping("doSaveDetail")
	@ResponseBody
	public String doSaveDetail(WareRelation wareRelation, HttpServletRequest request){
		
		Map<String,String> result = new HashMap<String,String>();
		
		if(wareRelation != null){
			//校验
			Map<String, Long> resultRow = wareRelationService.checkWareRelationParam(wareRelation);
			
			if(resultRow != null){
				if(resultRow.get("wareRelationResult").compareTo(0l) == 0){
					
					if(StringUtils.isNotBlank(wareRelation.getStoreCode())){
						Store store = new Store();
						store.setStore_code(wareRelation.getStoreCode());
						store = storeService.selectBySelective(store);
						wareRelation.setClientId(store.getClient_id());
					}
					
					int r = 0;
					if(wareRelation.getId() != null){
						r = wareRelationService.updateByPrimaryKeySelective(wareRelation);
					} else {
						Employee user = SessionUtils.getEMP(request);
						wareRelation.setCreateTime(new Date());
						wareRelation.setCreateUser(user.getId().toString());
						r = wareRelationService.insertSelective(wareRelation);
					}
					
					if(r>0){
						result.put("out_result", "1");
						result.put("out_result_reason","保存成功");
					} else {
						result.put("out_result", "0");
						result.put("out_result_reason","保存失败");
					}
				} else if (resultRow.get("wareRelationResult").compareTo(0l) > 0) {
					result.put("out_result", "0");
					result.put("out_result_reason","相同的仓-店铺组合已存在");
				} else {
					result.put("out_result", "0");
					result.put("out_result_reason","未通过校验");
				}
			} else {
				result.put("out_result", "0");
				result.put("out_result_reason","未通过校验");
			}
		} else {
			result.put("out_result", "0");
			result.put("out_result_reason","参数异常");
		}
		return JSONObject.toJSONString(result);
	}
	
	/**
	 * Title: wareParkDetail
	 * Description: 园区关联系统仓、店铺分页查询
	 * @param queryParam
	 * @param isShow
	 * @param request
	 * @return
	 * @author jindong.lin
	 * @date 2017年12月20日
	 */
	@RequestMapping("wareParkWarehouseStoreList")
	public String wareParkDetail(WareRelationQueryParam queryParam,Boolean isShow, HttpServletRequest request){
		if(queryParam == null){
			queryParam = new WareRelationQueryParam();
		}
		try{
			//根据条件查询合同集合
			PageView<WareRelationQueryParam> pageView = new PageView<WareRelationQueryParam>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<WareRelationQueryParam> qr = wareRelationService.findAll(queryParam);
			pageView.setQueryResult(qr, queryParam.getPage());
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
			request.setAttribute("isShow", isShow);
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return "/lmis/ware_park/ware_park_warehouse_store_list";
	}
	
	/**
	 * Title: toImportTaskList
	 * Description: 查询上传任务记录
	 * @param queryParam
	 * @param request
	 * @return
	 * @author jindong.lin
	 * @date 2017年12月22日
	 */
	@RequestMapping("toImportTaskList")
	public String toImportTaskList(WareImportTaskQueryParam queryParam, HttpServletRequest request){
		if(queryParam == null){
			queryParam = new WareImportTaskQueryParam();
		}
		try {
			PageView<WareImportTaskQueryParam> pageView = new PageView<WareImportTaskQueryParam>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			Employee user = SessionUtils.getEMP(request);
			queryParam.setCreateUser(user.getId().toString());
			QueryResult<WareImportTaskQueryParam> qrs = wareImportTaskService.getList(queryParam);
			pageView.setQueryResult(qrs,queryParam.getPage());
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/lmis/ware_park/import_task_list";
	}
	
	/**
	 * Title: toShowImportTaskDetail
	 * Description: 导入详情查看
	 * @param queryParam
	 * @param request
	 * @return
	 * @author jindong.lin
	 * @date 2017年12月22日
	 */
	@RequestMapping("toShowImportTaskDetail")
	public String toShowImportTaskDetail(WareImportErrorQueryParam queryParam, HttpServletRequest request){
		if(queryParam == null){
			queryParam = new WareImportErrorQueryParam();
		}
		try {
			
			if(StringUtils.isNotBlank(queryParam.getTwitId())){
				WareImportTaskQueryParam wareImportTaskQueryParam = wareImportTaskService.selectByPrimaryKey(queryParam.getTwitId());
				request.setAttribute("model", wareImportTaskQueryParam);
			}
			
			PageView<WareImportErrorQueryParam> pageView = new PageView<WareImportErrorQueryParam>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			Employee user = SessionUtils.getEMP(request);
			queryParam.setCreateUser(user.getId().toString());
			QueryResult<WareImportErrorQueryParam> qrs = wareImportErrorService.getList(queryParam);
			pageView.setQueryResult(qrs,queryParam.getPage());
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/lmis/ware_park/import_task_detail_list";
	}

	/**
	 * Title: importExcel
	 * Description: 园区关系批量导入
	 * @param file
	 * @param request
	 * @return
	 * @author jindong.lin
	 * @date 2017年12月26日
	 */
	@RequestMapping("importExcel")
	@ResponseBody
	public String importExcel(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request){
		Map<String,String>param=new HashMap<String,String>();
		try{
    		
    		String fileName = file.getOriginalFilename();
			String prefix = fileName.split("\\.")[fileName.split("\\.").length-1];
    		param.put("out_result","0");
    		param.put("out_result_reason","系统错误");
			// 判断文件是否为空   
	        if (!file.isEmpty()) {
	        	if(prefix.equals("xlsx")){
	        		// 文件保存路径
	        		String uuid = UUID.randomUUID().toString().replace("-", "");
	    			String filePath= CommonUtils.getAllMessage("config", "BALANCE_UPLOAD_RAWDATA_"+OSinfo.getOSname()) + uuid + "." + prefix;
	    			File baseFile = new File(filePath);
	                // 转存文件
	                file.transferTo(baseFile);
	                final List<String[]> list= XLSXCovertCSVReader.readerExcel(filePath, null, 11);  
	                if(list==null || list.size()==0 || list.size()==1){
			    		param.put("out_result","0");
			    		param.put("out_result_reason","文件内容有误!");
			    		return JSONObject.toJSONString(param).toString();
	                }
    				list.remove(0);
    	    		final Employee user = SessionUtils.getEMP(request);
    	    		final WareImportTask wareImportTask = new WareImportTask();
    	    		wareImportTask.setId(uuid);
    	    		wareImportTask.setFileName(fileName);
    	    		wareImportTask.setTotalCount(list.size());
    	    		wareImportTask.setCreateTime(new Date());
    	    		wareImportTask.setCreateUser(user.getId().toString());
    	    		wareImportTask.setUpdateTime(new Date());
    	    		wareImportTask.setUpdateUser(user.getId().toString());
    	    		wareImportTask.setStatus("-1");
    	    		wareImportTaskService.insertSelective(wareImportTask);
		    		
    	    		//异步处理
    	    		Thread myThread = new Thread(new Runnable() {
						
						@Override
						public void run() {
							wareImportTaskService.imoprtWarePark(list,wareImportTask,user);
							
						}
					});
    	    		myThread.start();

		    		param.put("out_result","1");
		    		param.put("out_result_reason","导入任务已创建");
	        	}else{
		    		param.put("out_result","0");
		    		param.put("out_result_reason","文件类型必须为[.xlsx],您上传的文件类型为[."+prefix+"]!");
	        	}
	        } else {
	    		param.put("out_result","0");
	    		param.put("out_result_reason","上传文件不能为空");
	        }
    	}catch(Exception e){
    		e.printStackTrace();
    		param.put("out_result","0");
    		param.put("out_result_reason","异常");
    	}
    	return JSONObject.toJSONString(param).toString();
	}

	/**
	 * Title: exportExcel
	 * Description: 园区关系批量导出
	 * @param queryParam
	 * @param map
	 * @param request
	 * @return
	 * @author jindong.lin
	 * @date 2017年12月25日
	 */
	@RequestMapping("exportExcel")
	public String exportExcel(WareParkQueryParam queryParam,ModelMap map,HttpServletResponse resp){
		try{
			
			String[] headNames =  new String[]{"仓库编码","仓库名称","店铺编码","店铺名称","店铺成本中心","品牌名称",
						"园区编码","园区名称","园区成本中心",
						//"仓","配","是否由物流部出具品牌账单","是否出具店铺收入",
						"备注"};
			String fileName = "园区关系批量导出.xlsx";
			ExcelExportUtil.exportExcelData(wareParkService.exportList(queryParam), headNames, "sheet1", ExcelExportUtil.OFFICE_EXCEL_2010_POSTFIX,fileName,resp);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 
	 * 
	 * @param downloadTemplete
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping("downloadTemplete")
	public String downloadTemplete(String downloadTemplete,HttpServletRequest request,HttpServletResponse resp){
		try{
			String[] headNames = null;
			String fileName = null;
			if (StringUtils.isNotBlank(downloadTemplete)) {
				if (downloadTemplete.trim().equals("wareParkRelation")) {
					headNames = new String[]{"序号","仓库编码","店铺编码","园区编码"};
					fileName = "园区关系批量导入模版.xlsx";
				} else {
					return null;
				}
				ExcelExportUtil.exportExcelData(new ArrayList<Map<String,Object>>(), headNames, "sheet1", ExcelExportUtil.OFFICE_EXCEL_2010_POSTFIX,fileName,resp);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
