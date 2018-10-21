package com.bt.lmis.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.DiffBilldeatilsQueryParam;
import com.bt.lmis.controller.form.ExpressbillBatchmasterQueryParam;
import com.bt.lmis.controller.form.ExpressbillMasterQueryParam;
import com.bt.lmis.dao.ExpressContractMapper;
import com.bt.lmis.model.DiffBilldeatils;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.ExpressbillBatchmaster;
import com.bt.lmis.model.ExpressbillDetail;
import com.bt.lmis.model.ExpressbillMaster;
import com.bt.lmis.model.ExpressbillMasterhx;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ContractBasicinfoService;
import com.bt.lmis.service.DiffBilldeatilsService;
import com.bt.lmis.service.ExpressbillBatchmasterService;
import com.bt.lmis.service.ExpressbillDetailService;
import com.bt.lmis.service.ExpressbillMasterService;
import com.bt.lmis.service.ExpressbillMasterhxService;
import com.bt.lmis.service.TransportVendorService;
import com.bt.utils.SessionUtils;
import com.bt.utils.BaseConst;
import com.alibaba.fastjson.JSONObject;

/**
 * 承运商账单主表控制器
 *
 */
@Controller
@RequestMapping("/control/lmis/expressbillMasterController")
public class ExpressbillMasterController extends BaseController {

	private static final Logger logger = Logger.getLogger(ExpressbillMasterController.class);

	@Resource(name = "transportVendorServiceImpl")
	private TransportVendorService<T> transportVendorService;
	/**
	 * 承运商账单主表服务类
	 */
	@Resource(name = "expressbillMasterhxServiceImpl")
	private ExpressbillMasterhxService<ExpressbillMasterhx> expressbillMasterhxService;
	@Resource(name = "expressbillMasterServiceImpl")
	private ExpressbillMasterService<ExpressbillMaster> expressbillMasterService;
	@Autowired
	private ExpressContractMapper<T> expressContractMapper;
	@Resource(name = "expressbillBatchmasterServiceImpl")
	private ExpressbillBatchmasterService<ExpressbillBatchmaster> expressbillBatchmasterService;
	@Resource(name = "expressbillDetailServiceImpl")
	private ExpressbillDetailService<ExpressbillDetail> expressbillDetailService;
	@Resource(name = "diffBilldeatilsServiceImpl")
	private DiffBilldeatilsService<DiffBilldeatils> diffBilldeatilsService;

	
	@RequestMapping("inite")
	public String init(HttpServletRequest request, HttpServletResponse res) {
		// 合同
		
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("contractType",1);
		List expressContract = expressContractMapper.findValidContract(param);
		// 承运商  contract_owner
		//List transportList = transportVendorService.getAllExpress();

		//request.setAttribute("transportVenders", transportList);

		request.setAttribute("expressContracts", expressContract);

		return "lmis/verification/vfc_billmaster";
	}

	@RequestMapping("pageQuery")
	public String pageQuery(HttpServletRequest request, HttpServletResponse res,
			ExpressbillMasterQueryParam queryParam) {
		QueryResult<ExpressbillMaster> qr = null;
		PageView<ExpressbillMaster> pageView = new PageView<ExpressbillMaster>(
				queryParam.getPageSize() == 0 ? BaseConst.pageSize : queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		qr = expressbillMasterService.selectExpressBill(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("totalSize", qr.getTotalrecord());
		request.setAttribute("queryParam", queryParam);
		return "lmis/verification/vfc_billmaster_page";
	}

	@ResponseBody
	@RequestMapping("add")
	public JSONObject add(HttpServletRequest request, HttpServletResponse res, ExpressbillMaster queryParam) {
		JSONObject obj = new JSONObject();
		try {
			Employee user = SessionUtils.getEMP(request);
			queryParam.setCreate_user(Integer.toString(user.getId()));
			expressbillMasterService.createExpressBill(queryParam);
			obj.put("code", "1");
		} catch (Exception e) {
			obj.put("code", "0");
		}
		return obj;
	}

	@RequestMapping("tablist")
	public String tablist(HttpServletRequest request, HttpServletResponse res, ExpressbillMasterQueryParam queryParam) {
		/*QueryResult<ExpressbillMaster> qr = null;
		PageView<ExpressbillMaster> pageView = new PageView<ExpressbillMaster>(
				queryParam.getPageSize() == 0 ? BaseConst.pageSize : queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		qr = expressbillMasterService.selectExpressBill(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("totalSize", qr.getTotalrecord());
		request.setAttribute("queryParam", queryParam);*/
		QueryResult<ExpressbillMaster> qr = null;
		qr = expressbillMasterService.selectExpressBill(queryParam);
		ExpressbillMaster master=qr.getResultlist().get(0);
		request.setAttribute("master", master);
		
		ExpressbillBatchmasterQueryParam QueryParam=new ExpressbillBatchmasterQueryParam();
		QueryParam.setMaster_id(master.getId().toString());
		QueryResult<ExpressbillBatchmaster> qr1 = null;
		PageView<ExpressbillBatchmaster> pageView = new PageView<ExpressbillBatchmaster>(
				QueryParam.getPageSize() == 0 ? BaseConst.pageSize : QueryParam.getPageSize(), QueryParam.getPage());
		QueryParam.setFirstResult(pageView.getFirstResult());
		QueryParam.setMaxResult(pageView.getMaxresult());
		qr1 = expressbillBatchmasterService.selectExpressBillBatch(QueryParam);
		pageView.setQueryResult(qr1, QueryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("totalSize", qr1.getTotalrecord());
		request.setAttribute("queryParam", QueryParam);
		List<Map<String,Object>> month_account=diffBilldeatilsService.getMonthAccount();
		request.setAttribute("monthAccount", month_account);
		return "lmis/verification/vfc_operation";
	}

	
	@ResponseBody
	@RequestMapping("/deletevfc")
	public  JSONObject deletevfc(ExpressbillMaster queryParam, HttpServletRequest request){
		JSONObject obj=new JSONObject();
		String id=request.getParameter("ids");
		try {
			if(id !=null){
				ExpressbillMaster expressbillMaster = expressbillMasterService.selectById(Integer.parseInt(id));
				if(expressbillMaster.getStatus()!=null){
					if(expressbillMaster.getStatus().equals("2")){
						obj.put("data", 1);
						return obj;
					}
				}
			}
		} catch (Exception e1) {
			obj.put("data", 2);
			return obj;
		}
		try{
			if(id !=null){
				expressbillBatchmasterService.deleteByMaster_id(id);
				expressbillDetailService.deleteByMaster_id(id);
				expressbillMasterService.deleteById(id);
				DiffBilldeatilsQueryParam dq=new DiffBilldeatilsQueryParam();
				dq.setMaster_id(id);
				diffBilldeatilsService.deleteByQuery(dq);
			}
			obj.put("data", 0);
		}catch(Exception e){
			//e.printStackTrace();
			obj.put("data", 2);
		}
		return obj;
	}
	
	@ResponseBody
	@RequestMapping("/close")
	public  JSONObject close(ExpressbillMaster queryParam, HttpServletRequest request){
		JSONObject obj=new JSONObject();
		Map<String,String> mp=new HashMap<String,String>();
		try {
			ExpressbillMaster em=	expressbillMasterService.selectById(queryParam.getId());
			em.setStatus("0");//关账
			em.setUpdate_time(new Date());

			Employee user = SessionUtils.getEMP(request);
			em.setUpdate_user(Integer.toString(user.getId()));
			expressbillMasterService.update(em);
			mp.put("contract_id", em.getCon_id().toString());
			mp.put("master_id", em.getId().toString());
			mp.put("table_name", "tb_diff_billdeatils");
			expressbillMasterhxService.saveClose(mp);
			obj.put("code", 1);
		} catch (Exception e1) {
			obj.put("code", 0);
			e1.printStackTrace();
			return obj;
		}
		
		return obj;
	}
	
}
