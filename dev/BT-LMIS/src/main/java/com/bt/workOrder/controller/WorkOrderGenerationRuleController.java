package com.bt.workOrder.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.WarninginfoMaintainMasterQueryParam;
import com.bt.radar.dao.WarninginfoMaintainMasterMapper;
import com.bt.radar.dao.WarninglevelListMapper;
import com.bt.radar.model.WarninginfoMaintainMaster;
import com.bt.radar.model.WarninglevelList;
import com.bt.utils.BaseConst;
import com.bt.utils.SessionUtils;
import com.bt.workOrder.controller.form.GenerationRuleQueryParam;
import com.bt.workOrder.controller.form.WkTypeQueryParam;
import com.bt.workOrder.dao.GenerationRuleMapper;
import com.bt.workOrder.dao.WkLevelMapper;
import com.bt.workOrder.dao.WkTypeMapper;
import com.bt.workOrder.model.GenerationRule;
import com.bt.workOrder.model.WkType;
import com.bt.workOrder.service.GenerationRuleService;

@Controller
@RequestMapping("/controller/workOder")
public class WorkOrderGenerationRuleController {
	@Autowired
	WarninginfoMaintainMasterMapper warninginfoMaintainMasterMapper;
	@Autowired
	WkTypeMapper  wktMapper;
	@Autowired
	WkLevelMapper  wklMapper;
	@Autowired
	GenerationRuleMapper  grMapper;
	@Autowired
	WarninglevelListMapper WarninglevelListMapper;
	@Resource(name = "generationRuleServiceImpl")
	private GenerationRuleService<GenerationRule> generationRuleService;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
    
	@RequestMapping("/workOrderRulQuery")
	public String query(GenerationRuleQueryParam queryParam,HttpServletRequest request){
		//查找工单类型代码
		WkTypeQueryParam wktquery=new WkTypeQueryParam();
		wktquery.setFlag(1);
		List<WkType> listwt=wktMapper.findAll(wktquery);
		if(listwt!=null&&listwt.size()!=0)request.setAttribute("wtType", listwt);
		//查找与预警类型代码
		List<WarninginfoMaintainMaster>  listW=	warninginfoMaintainMasterMapper.findAllData(null);
		request.setAttribute("warningtype", listW);
		//查询级别
		if(queryParam.getEw_type_code()!=null&&!"".equals(queryParam.getEw_type_code())){
			//查询预警级数
			WarninglevelList wl=new WarninglevelList();
			wl.setWarningtype_code(queryParam.getEw_type_code());
			wl.setDl_flag(1);//1:有效
		List<WarninglevelList>	list=WarninglevelListMapper.selectRecords(wl);
		request.setAttribute("WarninglevelList",  list);
		
		WarninginfoMaintainMasterQueryParam wq=new WarninginfoMaintainMasterQueryParam();
		wq.setWarningtype_code(queryParam.getEw_type_code());
		wq.setDl_flag(1);
		list=  warninginfoMaintainMasterMapper.findAllData(wq);
		    request.setAttribute("warningnameSelect",  list.get(0));
		}
		if(queryParam.getWk_type()!=null&&!"".equals(queryParam.getWk_type())){
			//查询工单级数
			Map<String,String> param =new HashMap<String,String> ();
			String wk_type=request.getParameter("wk_type");
			param.put("wk_type", wk_type);
			request.setAttribute("wk_level_list",wklMapper.findAllData(param));
		}
		//查找所有工单规则信息
		PageView<GenerationRule> pageView = new PageView<GenerationRule>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<GenerationRule> qr=new QueryResult<>();//generationRuleService.findAllData(queryParam);
		pageView.setQueryResult( qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "/work_order/work_order_rule_query";
	}
	
	@RequestMapping("/workOrderRulQueryPage")
	public String workOrderRulQueryPage(GenerationRuleQueryParam queryParam,HttpServletRequest request){
		//查找所有工单规则信息
		PageView<GenerationRule> pageView = new PageView<GenerationRule>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<GenerationRule> qr=generationRuleService.findAllData(queryParam);
		pageView.setQueryResult( qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "/work_order/work_order_rule_page";
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public JSONObject  add(GenerationRuleQueryParam queryParam,HttpServletRequest request){
		JSONObject obj=new JSONObject();
		try{
			obj=generationRuleService.addRule(queryParam, request);
		}catch(Exception e){
			e.printStackTrace();
			obj.put("code", 0);
			obj.put("msg", "系统错误!");
			return obj;
		}
		return obj;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public JSONObject  update(GenerationRuleQueryParam queryParam,HttpServletRequest request){
		JSONObject obj=new JSONObject();
		try{
			if(generationRuleService.updateRule(queryParam, request)){
				obj.put("code", 1);
			}else{
				obj.put("code", 0);
				return  obj;
			}
		}catch(Exception e){
			obj.put("code", 0);
			return obj;
		}
		obj.put("code", 1);
		return obj;
	}
	@ResponseBody
	@RequestMapping("/updateEwflag")
	public JSONObject  updateEwflag(GenerationRuleQueryParam queryParam,HttpServletRequest request){
		JSONObject obj=new JSONObject();
		try{
			GenerationRuleQueryParam qr=new GenerationRuleQueryParam();
			qr.setId(queryParam.getId());
			
		List<GenerationRule> list=	grMapper.findGenerationRule(qr);
		list.get(0).setEw_flag(queryParam.getEw_flag());
		list.get(0).setUpdate_time(new Date());
		list.get(0).setUpdate_user(SessionUtils.getEMP(request).getId().toString());
		GenerationRule re=list.get(0);
		generationRuleService.update(re);
		}catch(Exception e){
			obj.put("code", 0);
			e.printStackTrace();
			return obj;
		}
		obj.put("code", 1);
		return obj;
	}
	
	@ResponseBody
	@RequestMapping("/toUpdate")
	public JSONObject  toUpdate(GenerationRuleQueryParam queryParam,HttpServletRequest request){
		JSONObject obj=new JSONObject();
		//通过id 查找需要修改的记录
	try{	
		QueryResult qr=generationRuleService.findAllData(queryParam);
		Map<String,Object> rule=(Map<String, Object>) qr.getResultlist().get(0);
		obj.put("rule",rule);
	   //查找参数列表
		
		//预警类型
		WarninginfoMaintainMasterQueryParam wq=new WarninginfoMaintainMasterQueryParam();
		wq.setDl_flag(1);
	List<T>	list=  warninginfoMaintainMasterMapper.findAllData(wq);
	   obj.put("warningnameSelect",  list);
		
		//预警级别
	   WarninglevelList wl=new WarninglevelList();
		wl.setWarningtype_code(rule.get("ew_type_code").toString());
		wl.setDl_flag(1);//1:有效
	list=WarninglevelListMapper.selectRecords(wl);
	 obj.put("WarninglevelList",  list);
		obj.put("code", 1);
		
		//工单类型
		WkTypeQueryParam wtp=new WkTypeQueryParam();
		wtp.setFlag(1);
	list=wktMapper.findAll(wtp);
	obj.put("workTypeList",  list);
	
	   //工单级别
	Map<String,String> param =new HashMap<String,String> ();
	String wk_type=rule.get("wk_type_code").toString();
	param.put("wk_type", wk_type);
	obj.put("wk_level_list",wklMapper.findAllData(param));
	obj.put("code", 1);
	}catch(Exception e){
		obj.put("code", 0);
	}
	
		return obj;
	}
	
	
	@ResponseBody
	@RequestMapping("/del")
	public JSONObject  del(GenerationRuleQueryParam queryParam,HttpServletRequest request){
		JSONObject obj=new JSONObject();
		try{
			obj=
			generationRuleService.deleteRule(queryParam);
		}catch(Exception e){
		   obj.put("code", 0);
		   obj.put("msg", "系统错误！");
		}
		return obj;
	}
	
	
	
	@ResponseBody
	@RequestMapping("/findWarningTypeLevel")
	public JSONObject  findWarningTypeLevel(HttpServletRequest request){
		JSONObject obj=new JSONObject();
		String warningtype_code=request.getParameter("warningtype_code");
		WarninglevelList wl=new WarninglevelList();
		wl.setWarningtype_code(warningtype_code);
		wl.setDl_flag(1);//1:有效
	List<WarninglevelList>	list=WarninglevelListMapper.selectRecords(wl);
		obj.put("data", list);
		return obj;
	}
	
	@ResponseBody
	@RequestMapping("/findWkTypeLevel")
	public JSONObject  findWkTypeLevel(HttpServletRequest request){
		JSONObject obj=new JSONObject();
		Map<String,String> param =new HashMap<String,String> ();
		String wk_type=request.getParameter("wk_type");
		param.put("wk_type", wk_type);
		obj.put("data", wklMapper.findAllData(param));
		return obj;
	}
}
