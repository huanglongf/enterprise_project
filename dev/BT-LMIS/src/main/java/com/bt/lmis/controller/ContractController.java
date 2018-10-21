package com.bt.lmis.controller;
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

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.ContractBasicinfoQueryParam;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.SearchBean;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ContractBasicinfoService;
import com.bt.utils.BaseConst;
import com.bt.utils.SessionUtils;

/** 
* @ClassName: ContractController 
* @Description: TODO(合同控制器) 
* @author Yuriy.Jiang
* @date 2016年6月20日 上午11:56:51 
*  
*/
@Controller
@RequestMapping("/control/contractController")
public class ContractController extends BaseController {

	private static final Logger logger = Logger.getLogger(ContractController.class);

	@Resource(name="contractBasicinfoServiceImpl")
	private ContractBasicinfoService<ContractBasicinfo> contractBasicinfoServiceImpl;
	
	@RequestMapping("/list")
	public String list(ContractBasicinfoQueryParam queryParam,ModelMap map, HttpServletRequest request){
		try{
			//根据条件查询合同集合
			PageView<ContractBasicinfo> pageView = new PageView<ContractBasicinfo>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			
			ArrayList search=contractBasicinfoServiceImpl.getSearchParam(this.getParamMap(request));
			ArrayList show=contractBasicinfoServiceImpl.getShowParam(this.getParamMap(request));
			QueryResult<ContractBasicinfo> qr = contractBasicinfoServiceImpl.findAll(queryParam);
			pageView.setQueryResult(qr, queryParam.getPage());
			request.setAttribute("pageView", pageView);
			request.setAttribute("field",show);
			request.setAttribute("search",search);
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return "/lmis/contract_list";
	}

	@RequestMapping("/toUpdate")
	public String toUpdate(ModelMap map, HttpServletRequest request){
		int status = 0;
		try {
			ContractBasicinfo cb = contractBasicinfoServiceImpl.findById(Integer.valueOf(request.getParameter("id")));
			status=Integer.valueOf(cb.getContract_type());
		} catch (NumberFormatException e) {
			logger.error(e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		if(status==1 || status ==2){
			//快递物流
			return "redirect:/control/expressContractController/form.do?id="+request.getParameter("id");
		}
		//品牌店铺
		return "redirect:/control/sospController/toForm.do?id="+request.getParameter("id");
	}
	
	/**
	 * 删除合同
	 */
	@RequestMapping("/del")
	public void delete(ModelMap map, HttpServletRequest req, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Integer[] ids=StringtoInt(req.getParameter("privIds"));
			contractBasicinfoServiceImpl.batchDelete(ids);	
			out.write("true");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			out.write("false");
		}

	}
	
	

	@RequestMapping("/toSetPage")
	public String toSetPage(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		HashMap param=new HashMap();
		try {
			out = res.getWriter();
			String pageId=(String)req.getParameter("page_id");
			Employee user = SessionUtils.getEMP(req);
			param.put("page_id",pageId);
			param.put("user_id",user.getId());
			ArrayList<ContractBasicinfo> all_param=contractBasicinfoServiceImpl.getPageInfo(param);
			ArrayList<SearchBean>current_param=contractBasicinfoServiceImpl.getCurrentParam(param);	
			ArrayList<SearchBean>current_param_search=contractBasicinfoServiceImpl.getCurrentParamForSearch(param);	
			req.setAttribute("all_param", all_param);
			req.setAttribute("current_param", current_param);
			req.setAttribute("current_param_search", current_param_search);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return "/lmis/page_form";
	}
	
	@RequestMapping("/addParam")
	public void addParam(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		HashMap result=new HashMap();
		try {
			out = res.getWriter();
            Map param=getParamMap(req);
			Employee user = SessionUtils.getEMP(req);
			param.put("user_id",user.getId());
			contractBasicinfoServiceImpl.addParam(param);	
			result.put("out_result", "1");
			result.put("result_reason", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result.put("out_result", "0");
			result.put("result_reason", "操作失败,失败原因:"+e.getMessage());
		}
		out.write(JSONObject.toJSON(result).toString());
	}
	
	/**
	 * 删除合同
	 */
	@RequestMapping("/delParam")
	public void delParam(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		Map param=new HashMap();
		try {
			out = res.getWriter();
			String id=req.getParameter("id");
			param.put("id", id);
			contractBasicinfoServiceImpl.delParam(param);	
			param.put("out_result", "1");
			param.put("result_reason", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			param.put("out_result", "0");
			param.put("result_reason", "操作失败,失败原因:"+e.getMessage());
		}
		out.write(JSONObject.toJSON(param).toString());
	}
	
	@RequestMapping("/upParam")
	public void upParam(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		Map param=new HashMap();
		try {
			out = res.getWriter();
			Employee user = SessionUtils.getEMP(req);
			String id=req.getParameter("id");
			param.put("id", id);
			param.put("user_id", user.getId());
			param.put("value",req.getParameter("value"));
			contractBasicinfoServiceImpl.upParam(param);	
			param.put("out_result", "1");
			param.put("result_reason", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			param.put("out_result", "0");
			param.put("result_reason", "操作失败,失败原因:"+e.getMessage());
		}
		out.write(JSONObject.toJSON(param).toString());
	}
	
	@RequestMapping("/goReport")
	public String goReport(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		return "/report/CrystalReport1-viewer";
	}
}
