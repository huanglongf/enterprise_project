package com.bt.lmis.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.DiffBilldeatilsQueryParam;
import com.bt.lmis.controller.form.ExpressbillMasterQueryParam;
import com.bt.lmis.controller.form.ExpressbillMasterhxQueryParam;
import com.bt.lmis.model.CollectionMaster;
import com.bt.lmis.model.DiffBilldeatils;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.ExpressbillMaster;
import com.bt.lmis.model.ExpressbillMasterhx;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.DiffBilldeatilsService;
import com.bt.lmis.service.ExpressbillMasterService;
import com.bt.lmis.service.ExpressbillMasterhxService;
import com.bt.utils.BaseConst;
import com.bt.utils.BigExcelExport;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;

import net.sf.json.JSONObject;
/**
 * 核销的账单表控制器
 *
 */
@Controller
@RequestMapping("/control/lmis/expressbillMasterhxController")
public class ExpressbillMasterhxController extends BaseController {

	private static final Logger logger = Logger.getLogger(ExpressbillMasterhxController.class);
	
	/**
	 * 核销的账单表服务类
	 */
	@Resource(name = "expressbillMasterhxServiceImpl")
	private ExpressbillMasterhxService<ExpressbillMasterhx> expressbillMasterhxService;
	@Resource(name = "diffBilldeatilsServiceImpl")
	private DiffBilldeatilsService<DiffBilldeatils> diffBilldeatilsService;
	@Resource(name = "expressbillMasterServiceImpl")
	private ExpressbillMasterService<ExpressbillMaster> expressbillMasterService;

	@RequestMapping("goPage")
	public String tablist(HttpServletRequest request, HttpServletResponse res, ExpressbillMasterhxQueryParam queryParam) {
		QueryResult<ExpressbillMasterhx> qr = null;
		PageView<ExpressbillMasterhx> pageView = new PageView<ExpressbillMasterhx>(
				queryParam.getPageSize() == 0 ? BaseConst.pageSize : queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		qr = expressbillMasterhxService.findAll(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("totalSize", qr.getTotalrecord());
		request.setAttribute("queryParam", queryParam);
		return "lmis/verification/vfc_totalpage";
	}
	
	
	
	@ResponseBody
	@RequestMapping("closeAccount")
	public JSONObject closeAccount(HttpServletRequest request, HttpServletResponse res, ExpressbillMasterhxQueryParam queryParam) {
		JSONObject obj=new JSONObject();
		String master_id="";
		String con_id="";
		try{
			Map<String,String>param =new HashMap<String,String>();
			Employee user = SessionUtils.getEMP(request);
			param.put("user", Integer.toString(user.getId()));
			master_id=request.getParameter("master_id").toString();
			ExpressbillMasterQueryParam qr=new ExpressbillMasterQueryParam();
			QueryResult<ExpressbillMaster> dismap= expressbillMasterService.selectExpressBill(qr);
            param.put("contract_id", dismap.getResultlist().get(0).getCon_id().toString());
			param.put("master_id", master_id);
			expressbillMasterhxService.saveClose(param);
			obj.put("code", 1);
		}catch(Exception e){	
		  obj.put("code", 0);
		  e.printStackTrace();
		  return obj;
		}
		
		
		return obj;
	}
	@ResponseBody
	@RequestMapping("deleteClose")
	public JSONObject deleteAccount(HttpServletRequest request, HttpServletResponse res, ExpressbillMasterhxQueryParam queryParam) {
		JSONObject obj=new JSONObject();
		String ids="";
		try{
			
			Map<String,Object>param =new HashMap<String,Object>();
			Employee user = SessionUtils.getEMP(request);
			param.put("user", user.getId());
			ids=request.getParameter("ids").toString();
			String ids0[]=ids.split(";");
			List<String> ids1=Arrays.asList(ids0);
			param.put("ids", ids1);
			expressbillMasterhxService.deleteClose(param);
			obj.put("code", 1);
		}catch(Exception e){
			obj.put("code", 0);
			e.printStackTrace();
			return obj;
		}
		return obj;
	}
	
	
	@RequestMapping("uploade_expressbillmasterhx")
	public void uploade(HttpServletRequest request, HttpServletResponse res, ExpressbillMasterhxQueryParam queryParam) throws Exception {
		Map<String,String> result= new HashMap<String,String>();
		PrintWriter out = null;
		result.put("out_result", "0");
		result.put("out_result_reason","错误");
		int master_id = Integer.parseInt(request.getParameter("master_id"));
		ExpressbillMaster expressbillMaster = expressbillMasterService.selectById(master_id);
		ExpressbillMasterhx expressbillMasterhx = expressbillMasterhxService.selectByExpressbillMasterhxId(queryParam.getId());
		if(expressbillMasterhx.getFile_path()==null||expressbillMasterhx.getFile_path().equals(null)||expressbillMasterhx.getFile_path()==""){
			Date time =new Date();
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
			String file_name = date.format(time);
			try{
				diffBilldeatilsService.getUploadeExcel(expressbillMasterhx.getId(), expressbillMaster.getCon_id().toString(), file_name+"账单汇总明细表");
						
			}catch(Exception e){e.printStackTrace();}
			try{
				out = res.getWriter();
				result.put("out_result", "1");
				result.put("out_result_reason", "成功");
				result.put("path", CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP")+file_name+"账单汇总明细表.zip");
			}catch(Exception e){
				e.printStackTrace();
			}
			out.write(com.alibaba.fastjson.JSONObject.toJSON(result).toString());
			out.flush();
			out.close();
		}else{
			try{
				out = res.getWriter();
				result.put("out_result", "1");
				result.put("out_result_reason", "成功");
				result.put("path", CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP")+expressbillMasterhx.getFile_path()+".zip");
			}catch(Exception e){
				e.printStackTrace();
			}
			out.write(com.alibaba.fastjson.JSONObject.toJSON(result).toString());
			out.flush();
			out.close();
		}
		
			
	}
	
}
