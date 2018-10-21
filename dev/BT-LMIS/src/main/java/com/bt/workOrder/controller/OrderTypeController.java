package com.bt.workOrder.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.ErrorAddressQueryParam;
import com.bt.lmis.controller.form.WorkInfoQueryParam;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.utils.BaseConst;
import com.bt.workOrder.bean.WorkBaseInfoBean;
import com.bt.workOrder.service.WorkBaseInfoService;
/**
 * 
* @ClassName: AddressController 
* @Description: 地址参数维护
* @author Likun
* @date 2016年6月7日 上午10:24:22 
 */
@Controller
@RequestMapping("/control/OrderTypeController")
public class OrderTypeController extends BaseController{
	private static final Logger logger = Logger.getLogger(OrderTypeController.class);
	
	@Resource(name="workBaseInfoServiceImpl")
	private WorkBaseInfoService<WorkBaseInfoBean> workBaseInfoServiceImpl;

	@RequestMapping("/typelist.do")
	public String getOrderBaseInfo(WorkInfoQueryParam queryParam, HttpServletRequest request) throws Exception{
		try{
			PageView<WorkBaseInfoBean> pageView = new PageView<WorkBaseInfoBean>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<WorkBaseInfoBean> qrs = workBaseInfoServiceImpl.findAllData(queryParam);
			pageView.setQueryResult(qrs,queryParam.getPage()); 
            ArrayList<?>list=workBaseInfoServiceImpl.getAllType();
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
			request.setAttribute("allType", list);
		}catch(Exception e){
			logger.error(e);
		}
//		return "/jk_analysis/jk_analysis";
		return "/work_order/work_type_list2";
	}
	
	
	@RequestMapping("/toForm.do")
	public String toForm(ErrorAddressQueryParam queryParam, HttpServletRequest request) throws Exception{
		try{
		}catch(Exception e){
			logger.error(e);
		}
		return "/work_order/work_type_add";
	}
	
	
	@RequestMapping("/add_main.do")
	public void addMain(HttpServletRequest request,HttpServletResponse res) throws Exception{
		Map<String,String> result= new HashMap<String,String>();
		PrintWriter out = null;
		try{
			out = res.getWriter();
			workBaseInfoServiceImpl.addType(this.getParamMap(request));
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
		}catch(Exception e){
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason", "失败");
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	
	
	@RequestMapping("/add_type_main.do")
	public String getType(HttpServletRequest request,HttpServletResponse res) throws Exception{
		Map<String,String> result= new HashMap<String,String>();
		PrintWriter out = null;
		try{
			out = res.getWriter();
			Map<String,Object> pram=this.getParamMap(request);
			
			if (pram.get("type") == null || pram.get("type").equals("")) {
				throw new RuntimeException("工单类型为空");
			}
			
			if(pram.get("opType").equals("1")){
		    Map<String,Object> resu= workBaseInfoServiceImpl.addType(pram);
			result.put("main_id",(String)resu.get("code"));
			}else{
			workBaseInfoServiceImpl.updateType(pram);
			}
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
		}catch(Exception e){
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason",e.getMessage());
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
		return "/work_order/work_type_add";
	}
	
	
	@RequestMapping("/getTypeTab.do")
	public void getTypeTabs(HttpServletRequest request,HttpServletResponse res) throws Exception{
		PrintWriter out = null;
		try{
			out = res.getWriter();
			ArrayList<?> list=workBaseInfoServiceImpl.getTypeTab(getParamMap(request));
			if(list==null){
				list=new ArrayList();
			}
			out.write(JSONArray.toJSON(list).toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getReasonTab.do")
	public void getReasonTabs(HttpServletRequest request,HttpServletResponse res) throws Exception{
		Map<String,String> result= new HashMap<String,String>();
		PrintWriter out = null;
		try{
			out = res.getWriter();
			ArrayList<?> list=workBaseInfoServiceImpl.getReasonTab(getParamMap(request));
			if(list==null){
				list=new ArrayList();
			}
			out.write(JSONArray.toJSON(list).toString());
		}catch(Exception e){
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason", "失败");
		}
	}
	
	@RequestMapping("/getErrorTab.do")
	public void getErrorTab(HttpServletRequest request,HttpServletResponse res) throws Exception{
		Map<String,String> result= new HashMap<String,String>();
		PrintWriter out = null;
		try{
			out = res.getWriter();
			ArrayList<?> list=workBaseInfoServiceImpl.getErrorTab(getParamMap(request));
			if(list==null){
				list=new ArrayList();
			}
			out.write(JSONArray.toJSON(list).toString());
		}catch(Exception e){
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason", "失败");
		}
	}	
	
	
	@RequestMapping("/add_type_kid.do")
	public void addType(HttpServletRequest request,HttpServletResponse res) throws Exception{
		Map<String,String> result= new HashMap<String,String>();
		PrintWriter out = null;
		try{
			out = res.getWriter();
			if(this.getParamMap(request).get("opType").equals("1")){
			workBaseInfoServiceImpl.add_type_kid(this.getParamMap(request));
			}else{
			workBaseInfoServiceImpl.up_type_kid(this.getParamMap(request));
			}
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
		}catch(Exception e){
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason",e.getMessage());
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("/add_reason_kid.do")
	public void add_reason_kid(HttpServletRequest request,HttpServletResponse res) throws Exception{
		Map<String,String> result= new HashMap<String,String>();
		PrintWriter out = null;
		try{
			out = res.getWriter();
			if(this.getParamMap(request).get("opType").equals("1")){
			workBaseInfoServiceImpl.add_reason_kid(this.getParamMap(request));
			}else{
			workBaseInfoServiceImpl.up_reason_kid(this.getParamMap(request));
			}
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
		}catch(Exception e){
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason", "失败");
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("/add_error_kid.do")
	public void add_error_kid(HttpServletRequest request,HttpServletResponse res) throws Exception{
		Map<String,String> result= new HashMap<String,String>();
		PrintWriter out = null;
		try{
			out = res.getWriter();
			if(this.getParamMap(request).get("opType").equals("1")){
			workBaseInfoServiceImpl.add_error_kid(this.getParamMap(request));
			}else{
			workBaseInfoServiceImpl.up_error_kid(this.getParamMap(request));
			}
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
		}catch(Exception e){
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason", "失败");
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}	

	@RequestMapping("/del")
	public void delete(ModelMap map, HttpServletRequest req, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		Map<String,String> result= new HashMap<String,String>();
		try {
			out = res.getWriter();
			Integer[] ids=StringtoInt(req.getParameter("privIds"));
			Integer count=workBaseInfoServiceImpl.checkStatus(ids);
			if(count!=0){
				result.put("out_result", "0");
				result.put("out_result_reason", "删除失败，数据中存在有效的工单类型，无法删除!");
			}else{
			    workBaseInfoServiceImpl.batchDelete(ids);	
				result.put("out_result", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result.put("out_result", "0");
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}

	@RequestMapping("/delType.do")
	public void delType(ModelMap map, HttpServletRequest req, HttpServletResponse res){
		Map<String,String> result= new HashMap<String,String>();
		res.setContentType("text/xml;charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			workBaseInfoServiceImpl.delType(getParamMap(req));	
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason", "失败");
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}

	@RequestMapping("/delReason.do")
	public void delReason(ModelMap map, HttpServletRequest req, HttpServletResponse res){
		Map<String,String> result= new HashMap<String,String>();
		res.setContentType("text/xml;charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			workBaseInfoServiceImpl.delReason(getParamMap(req));	
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason", "失败");
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("/delError.do")
	public void delError(ModelMap map, HttpServletRequest req, HttpServletResponse res){
		Map<String,String> result= new HashMap<String,String>();
		res.setContentType("text/xml;charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			workBaseInfoServiceImpl.delError(getParamMap(req));	
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("out_result", "0");
			result.put("out_result_reason", "失败");
		}
		out.write(JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("/updateStatus")
	public void updateStatus(ModelMap map, HttpServletRequest req, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object> map1 = getParamMap(req);
			String ids = map1.get("priv_ids").toString();
			String idstr = ids.substring(1, ids.length());
			map1.put("priv_ids", idstr);
			workBaseInfoServiceImpl.batchUpdate(map1);	
			out.write("true");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			out.write("false");
		}

	}
	
	@RequestMapping("/updateInfo.do")
	public String updateInfo( HttpServletResponse res, HttpServletRequest request){
		try{
			request.setAttribute("obj", getParamMap(request));
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
		}
		return "/work_order/work_type_add";
	}
	
	@RequestMapping("/getAllLevel.do")
	public void getAllLevel( HttpServletResponse res, HttpServletRequest request){
		PrintWriter out = null;
		ArrayList<?>list=null;
		try {
			out = res.getWriter();
			list=workBaseInfoServiceImpl.getAllLevel(getParamMap(request));
			 if(list==null){
				 list=new ArrayList();
			 }			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		out.write(JSONArray.toJSON(list).toString());
		out.flush();
		out.close();
	}
	
}







