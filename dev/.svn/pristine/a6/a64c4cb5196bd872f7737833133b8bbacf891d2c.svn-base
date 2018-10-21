package com.bt.workOrder.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bt.EPlatform;
import com.bt.OSinfo;
import com.bt.common.base.LoadingType;
import com.bt.common.controller.param.Parameter;
import com.bt.common.util.ExcelExportUtil;
import com.bt.lmis.balance.common.Constant;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.DateUtil;
import com.bt.utils.FileUtil;
import com.bt.utils.SessionUtils;
import com.bt.utils.UUIDUtils;
import com.bt.utils.ZipUtils;
import com.bt.utils.cache.CacheManager;
import com.bt.workOrder.bean.Enclosure;
import com.bt.workOrder.controller.param.WorkOrderParam;
import com.bt.workOrder.dao.WorkOrderManagementMapper;
import com.bt.workOrder.service.WorkOrderManagementService;

/** 
 * @ClassName: WorkOrderManagementController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年4月24日 下午4:28:14 
 * 
 */
@Controller
@RequestMapping("/control/workOrderManagementController")
public class WorkOrderManagementController extends BaseController{

	private static final Logger logger = Logger.getLogger(WorkOrderManagementController.class);
	
	@Autowired 
	private WorkOrderManagementMapper<T>  workOrderManagementMapper;
	@Resource(name= "workOrderManagementServiceImpl")
	private WorkOrderManagementService<T> workOrderManagementServiceImpl;
	
	/**
	 * 
	 * @Description: TODO
	 * @param workOrderParam
	 * @param request
	 * @return: JSONObject  
	 * @author Ian.Huang 
	 * @date 2017年4月13日下午3:08:22
	 */
	@ResponseBody
	@RequestMapping("/getWaybillDetailByWaybill")
	public JSONObject getWaybillDetailByWaybill(WorkOrderParam workOrderParam, HttpServletRequest request) {
		JSONObject obj=new JSONObject();
		try{
			obj.put("data", workOrderManagementServiceImpl.getWaybillDetailByWaybill(request.getParameter("waybill").toString()));
			
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		return  obj;
		
	}
	
	/** 
	 * @Title: judgePower 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param request
	 * @param response
	 * @return void 返回类型
	 * @author Ian.Huang
	 * @throws
	 * @date 2017年4月24日 下午4:29:49
	 */
	@RequestMapping("/judgePower.do")
	public void judgePower(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= workOrderManagementServiceImpl.judgePower(request, result);
			
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
	
	@RequestMapping("/updateWorkOrder.do")
	public void updateWorkOrder(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;

		try {
			result= workOrderManagementServiceImpl.updateWorkOrder(request, result);
			
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
    @RequestMapping("/zfWorkOrder.do")
    public void zfWorkOrder(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        PrintWriter out =null;
        JSONObject result= null;

        try {
            System.out.println(request.getParameter("event"));
            System.out.println(request.getParameter("wo_ids"));
             String [] woids = request.getParameter("wo_ids").split(",");
             for(int i=0;i<woids.length;i++) {
                 request.setAttribute("wo_id", woids[i]);
                 result = workOrderManagementServiceImpl.updateWorkOrder(request, result);

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
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年3月8日下午9:41:49
	 */
	@RequestMapping("/shiftGroups.do")
	public void shiftGroups(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= workOrderManagementServiceImpl.shiftGroups(request, result);
			
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
	 * likun 2017/08/15
	 * @param map
	 * @param request
	 * @param res
	 */
	@RequestMapping("/get_wo_level.do")
	public void get_wo_level(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			ArrayList<?> list=workOrderManagementServiceImpl.get_wo_level(param);
			out.write(JSONArray.toJSON(list).toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/toTimeLine")
	public String toTimeLine(Parameter parameter, HttpServletRequest req){
		req.setAttribute("woId", req.getParameter("woId"));
		return "/work_order/wo_management/timeline";
	}
	
	/**
	 * 加载附件
	 */
	@RequestMapping("loadingEnclosure")
	@ResponseBody
	public JSONObject loadingEnclosure(HttpServletRequest request) {
		JSONObject s = new JSONObject();
		try {
			List<Enclosure> elist = workOrderManagementServiceImpl.selectAllByWoNo(request.getParameter("woId"));
			s.put("code", "200");
			s.put("enclosure", elist);
		} catch (Exception e) {
			e.printStackTrace();
			s.put("code", "400");
		}
		return s;
	}
	
	@RequestMapping("OneKeyDownload")
	@ResponseBody
	public JSONObject OneKeyDownload(HttpServletRequest request,HttpServletResponse response) {
		JSONObject s = new JSONObject();
		try {
			String woNo = request.getParameter("woId");
			String time = DateUtil.formatSS(new Date());
			String filePathDir = CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname())+woNo+"_"+time;
			String filePath = "";
			if(OSinfo.getOSname().equals(EPlatform.Linux) || OSinfo.getOSname().equals(EPlatform.Mac_OS_X)) {
				filePath=filePathDir+"/";
			} else if(OSinfo.getOSname().equals(EPlatform.Windows)) {
				filePath=filePathDir+"\\";
			}
			String root = CommonUtils.getAllMessage("config", "NGINX_FILE_DOWNLOAD");
			String accessoryStr = request.getParameter("accessory_list");
			String[] picNames = accessoryStr.split("_");
			for(String picNameF:picNames){
				//f9e20ada-1c0f-4f2d-ba36-7180abbc45fe.PNG#7#.PNG
				if(CommonUtils.checkExistOrNot(picNameF)){
					String[] picNameFs = picNameF.split("1a1b1c1d3e");
					String url = root+picNameFs[0];
					String trueName = filePath+picNameFs[1]+picNameFs[2];
					//从服务器下载图片到指定文件夹
					FileUtil.createImage(url,trueName);
				}
			}
			//将文件夹打包成zip
			ZipUtils.zip(filePathDir,CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname()));
			FileUtil.deleteDir(new File(filePathDir));
			FileUtil.downloadFile(filePathDir+Constant.SYM_POINT+Constant.FILE_TYPE_SUFFIX_ZIP,woNo+"_"+time+Constant.SYM_POINT+Constant.FILE_TYPE_SUFFIX_ZIP,response);
			s.put("code", "200");
		} catch (Exception e) {
		    String message = e.getMessage();
            if (message.startsWith("ClientAbortException")) {
                
            } else {
                e.printStackTrace();
            }
			s.put("code", "400");
		}
		return s;
	}
	
	/**
	 * 
	    * <p>Description: </p>
	    * <p>Company: </p>
	    * @author:Administrator
	    * @date:2017年8月16日
	 */
	@RequestMapping("/get_error_type.do")
	public void get_error_typel(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			ArrayList<?> list=workOrderManagementServiceImpl.get_error_type(param);
			out.write(JSONArray.toJSON(list).toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	    * <p>Description: </p>
	    * <p>Company: </p>
	    * @author:Administrator
	    * @date:2017年8月16日
	 */
	@RequestMapping("/get_reason_info.do")
	public void get_reason_info(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			ArrayList<?> list=workOrderManagementServiceImpl.get_reason_info(param);
			out.write(JSONArray.toJSON(list).toString());
		} catch (Exception e) {
			// TODO: handle exception
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
	 * @date 2017年2月2日下午9:33:51
	 */
	@RequestMapping("/saveOperation.do")
	public void saveOperation(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= workOrderManagementServiceImpl.saveOperation(request, result);
			// 刷新日志
			result= workOrderManagementServiceImpl.refreshWoem(request, result);
			
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
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年2月3日下午4:49:00
	 */
	@RequestMapping("/monitoringStatus.do")
	public void monitoringStatus(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		PrintWriter out =null;
		JSONObject result= null;
		try {
			result= workOrderManagementServiceImpl.monitoringStatus(request, result);
			
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
	
	@ResponseBody
	@RequestMapping("/refreshClaimDetail.do")
	public JSONObject refreshClaimDetail(HttpServletRequest request, HttpServletResponse response){
		JSONObject obj=new JSONObject();
		String wo_id=request.getParameter("wo_id");
		try{
			obj.put("data", workOrderManagementMapper.getClaimDetailByWoId(wo_id));
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
		}
		return obj;
	}
	
	@RequestMapping("exportWorkOrder")
    public void exportWorkOrder(WorkOrderParam param,HttpServletRequest request, HttpServletResponse response) {
	    // 当前处理者赋值
        param.setProcessor_id(SessionUtils.getEMP(request).getId().toString());
        List<Map<String, Object>> result=workOrderManagementServiceImpl.exportWO(param);
        
        LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
        title.put("wo_no","工单号");
        title.put("express_number","快递单号");
        title.put("platform_number","平台订单号");
        title.put("related_number","相关单据号");
        title.put("warehouse_display","发货仓库");
        title.put("store_display","店铺");
        title.put("carrier_display","物流服务商");
        title.put("remaining_levelup_time","升级剩余时间（分钟）");
        title.put("warningDays","预警天数");
        title.put("wo_type_display","工单类型");
        title.put("exception_type","异常类型");
        title.put("processor_display","处理者");
        title.put("estimated_time_of_completion","计划完成时间");
        title.put("wo_process_status_display","工单处理状态");
        title.put("wo_alloc_status_display","工单分配状态");
        title.put("wo_level_display","工单级别");
        title.put("transport_time","出库时间");
        title.put("query_person","查询人");
        title.put("create_by_display","创建者");
        title.put("create_time","创建时间");
        title.put("wo_source","创建来源");
        title.put("allocated_by_display","分配者");
        title.put("standard_manhours","标准工时（分钟）");
        title.put("actual_manhours","实际工时（分钟）");
        title.put("claim_status","索赔状态");
        title.put("claim_number","索赔编码");
        title.put("claim_type","索赔分类");
        title.put("total_applied_claim_amount","申请索赔金额");
		title.put("failure_reason","索赔失败原因");
		title.put("recipient","收件人");
		title.put("phone","联系电话");
		title.put("address","联系地址");
        try {
            ExcelExportUtil.exportExcelByStream("工单导出" + "_" + SessionUtils.getEMP(request).getName() + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), ExcelExportUtil.OFFICE_EXCEL_2010_POSTFIX, "工单导出", title, result, response);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }
	/**
	 * 
	 * @Description: TODO
	 * @param workOrderParam
	 * @param request
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年1月10日下午8:50:27
	 */
	@RequestMapping("/query")
	public String query(WorkOrderParam param, HttpServletRequest request) {
		String url=null;
		try{
			// 当前处理者赋值
			param.setProcessor_id(SessionUtils.getEMP(request).getId().toString());
			//
			PageView<Map<String, Object>> pageView=new PageView<Map<String, Object>>(param.getPageSize()==0?BaseConst.pageSize:param.getPageSize(),param.getPage());
			param.setFirstResult(pageView.getFirstResult());
			param.setMaxResult(pageView.getMaxresult());
			//
			if(param.getLoadingType().equals(LoadingType.MAIN)) {
				// 工单处理平台查询条件初始化
				request=workOrderManagementServiceImpl.initialize(request);
				//
				url="/work_order/wo_management/wo_management_platform";
				
			} else if(param.getLoadingType().equals(LoadingType.DATA)) {
				url="/work_order/wo_management/wo_management_platform_list";
				
			}
			//
			QueryResult<Map<String, Object>> result=null;
			if(param.getIsQuery()) {
				//根据条件查询合同集合
				result=workOrderManagementServiceImpl.query(param);
				
			} else {
				//
				result=new QueryResult<Map<String, Object>>();
				
			}
			pageView.setQueryResult(result,param.getPage()); 
			request.setAttribute("pageView",pageView);
			// 缓存查询条件
			if(!CommonUtils.checkExistOrNot(param.getUuid())) {
				param.setUuid("QP-"+new SimpleDateFormat("yyMMddHHmmss").format(new Date())+"-"+UUIDUtils.getUUID8L());
				
			}
            // SessionUtils.setAttr(request,param.getUuid(),param,1800);
			CacheManager.putValue(param.getUuid(), param, 1800);
			//
			request.setAttribute("queryParam",param);
			// 区分查询页面与处理页面
			request.setAttribute("role","query");
			
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		return url;
		
	}
	
	/** 
	 * @Title: back 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param request
	 * @return String 返回类型
	 * @author Ian.Huang
	 * @throws
	 * @date 2017年4月25日 下午1:48:18
	 */
	@RequestMapping("/back")
	public String back(HttpServletRequest request) {
		// WorkOrderParam param=(WorkOrderParam)SessionUtils.getAttr(request, request.getParameter("uuid").toString());
		WorkOrderParam param=(WorkOrderParam)CacheManager.getValue(request.getParameter("uuid").toString());
		// 返回为全局加载
		param.setLoadingType(LoadingType.MAIN);
		return query(param, request);
		
	}
	
}