package com.bt.radar.controller;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bt.common.controller.result.Result;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.basis.model.Store;
import com.bt.lmis.basis.service.StoreManagerService;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.AgeingMasterQueryParam;
import com.bt.radar.model.AgeingMaster;
import com.bt.radar.model.ExpressinfoMaster;
import com.bt.radar.service.AgeingMasterService;
import com.bt.radar.service.ExpressinfoMasterService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;


@Controller
@RequestMapping("/control/radar/ageingMasterController")
public class AgeingMasterController {


	private static final Logger logger = Logger.getLogger(AgeingMasterController.class);
	
	@Resource(name = "ageingMasterServiceImpl")
	private AgeingMasterService<AgeingMaster> ageingMasterServiceImpl;
	@Resource(name="storeManagerServiceImpl")
	private StoreManagerService<T> storeManagerService;
	@Resource(name = "expressinfoMasterServiceImpl")
	private ExpressinfoMasterService<ExpressinfoMaster> expressinfoMasterService;
	
	//查询店铺时效查询
	@RequestMapping("/query")
	public String toForm(AgeingMasterQueryParam queryParam, HttpServletRequest request) throws Exception{
		String username = SessionUtils.getEMP(request).getUsername();
		queryParam.setCreateUser(username);
		QueryResult<AgeingMaster> qr=null;
		PageView<AgeingMaster> pageView = new PageView<AgeingMaster>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		qr = ageingMasterServiceImpl.queryAgeingMaster(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage()); 
		List stores=expressinfoMasterService.getStore(null);
		request.setAttribute("stores", stores);
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
	  return "/radar/ageingmaster/ageing_master";
	}
	
	
	//跳转新增页面
	@RequestMapping("/addAgeingMaster")
	public String addAgeingMaster(AgeingMasterQueryParam queryParam, HttpServletRequest request) throws Exception{
	List<Map<String,Object>> list = storeManagerService.findAll();
	request.setAttribute("store", list);
	  return "/radar/ageingmaster/addageing_master";
	}
	//跳转修改页面
	@RequestMapping("/toupdateAgeingMaster")
	public String toupdateAgeingMaster(AgeingMasterQueryParam queryParam, HttpServletRequest request) throws Exception{
		String ids = request.getParameter("ids");
		String[] split = ids.split(";");
		AgeingMaster ageingMaster = ageingMasterServiceImpl.queryAgeingMasterById(split[0]);
		List<Map<String,Object>> list = storeManagerService.findAll();
		request.setAttribute("store", list);
		request.setAttribute("queryParam", ageingMaster);
		return "/radar/ageingmaster/updateageing_master";
	}
	
	
	
	//新增
	@ResponseBody
	@RequestMapping("/submitAgeingMaster")
	public JSONObject submitAgeingMaster(AgeingMasterQueryParam queryParam, HttpServletRequest request,HttpServletResponse res) throws Exception{
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		JSONObject result = new JSONObject();
		AgeingMaster ageingMaster =ageingMasterServiceImpl.selectByAgeingName(queryParam.getAgeingName());
		if(CommonUtils.checkExistOrNot(ageingMaster)){
			result.put("result_code", "FAILURE");
			result.put("result_content", "新增失败,失败原因:时效名称已存在！");
			return result;
		}
		String username = SessionUtils.getEMP(request).getUsername();
		queryParam.setCreateTime(new Date());
		queryParam.setCreateUser(username);
		queryParam.setUpdateTime(new Date());
		queryParam.setUpdateUser(username);
		queryParam.setStatus("0");
		queryParam.setId(UUID.randomUUID().toString());
		Store store =storeManagerService.selectByStoreCode(queryParam.getStoreCode());
		queryParam.setStoreName(store.getStoreName());
		ageingMasterServiceImpl.insertAgeingMaster(queryParam);
		result.put("result_code", "SUCCESS");
		return result;
	}
	//更新
	@ResponseBody
	@RequestMapping("/updateAgeingMaster")
	public JSONObject updateAgeingMaster(AgeingMasterQueryParam queryParam, HttpServletRequest request,HttpServletResponse res) throws Exception{
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		JSONObject result = new JSONObject();
		Store store =storeManagerService.selectByStoreCode(queryParam.getStoreCode());
		queryParam.setStoreName(store.getStoreName());
		String username = SessionUtils.getEMP(request).getUsername();
		queryParam.setCreateTime(new Date());
		queryParam.setCreateUser(username);
		queryParam.setUpdateTime(new Date());
		queryParam.setUpdateUser(username);
		AgeingMaster ageingMaster =ageingMasterServiceImpl.selectByAgeingName(queryParam.getAgeingName());
		if(CommonUtils.checkExistOrNot(ageingMaster)){
			if(ageingMaster.getId().equals(queryParam.getId())){
				ageingMasterServiceImpl.updateAgeingMaster(queryParam);
				result.put("result_code", "SUCCESS");
				return result;
			}
			result.put("result_code", "FAILURE");
			result.put("result_content", "新增失败,失败原因:时效名称已存在！");
			return result;
		}
		ageingMasterServiceImpl.updateAgeingMaster(queryParam);
		result.put("result_code", "SUCCESS");
		return result;
	}
	/**
	 * 批量删除店铺时效
	 * */
	@RequestMapping("/delAgeingMaster")
	@ResponseBody
	public Result delAgeingMaster(HttpServletRequest request,AgeingMasterQueryParam queryParam){
		Result result;
		try{
			if(StringUtils.isBlank(queryParam.getId()))  return new Result(true,"params error");
			result=ageingMasterServiceImpl.delAgeingMaster(queryParam);
		}catch (Exception e){
			result=new Result(false,"AgeingMasterController delAgeingMaster  error");
			logger.error("AgeingMasterController delAgeingMaster  error",e);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/upStatus")
	public Result upStatus(HttpServletRequest request,AgeingMasterQueryParam queryParam){
		Result result;
		try{
			if(StringUtils.isBlank(queryParam.getId()))  return new Result(true,"params error");
			result=ageingMasterServiceImpl.upStatus(queryParam);
		}catch (Exception e){
			result=new Result(false,"AgeingMasterController upStatus  error");
			logger.error("AgeingMasterController upStatus  error",e);
			e.printStackTrace();
		}
		return result;
	}
}
