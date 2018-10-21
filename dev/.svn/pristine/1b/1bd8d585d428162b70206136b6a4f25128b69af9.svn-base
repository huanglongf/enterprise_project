package com.bt.lmis.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.bt.OSinfo;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.ExpressbillBatchmasterQueryParam;
import com.bt.lmis.controller.form.ExpressbillDetailQueryParam;
import com.bt.lmis.controller.form.ExpressbillMasterQueryParam;
import com.bt.lmis.model.DiffBilldeatils;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.ExpressbillBatchmaster;
import com.bt.lmis.model.ExpressbillDetail;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.DiffBilldeatilsService;
import com.bt.lmis.service.ExpressbillBatchmasterService;
import com.bt.lmis.service.ExpressbillDetailService;
import com.bt.lmis.thread.ExpressBillInput;
import com.bt.lmis.thread.ExpressBillTransfer;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;
import com.bt.utils.ThreadManageUtil;
/**
 * 承运商账单导入批次表控制器
 *
 */
@Controller
@RequestMapping("/control/lmis/expressbillBatchmasterController")
public class ExpressbillBatchmasterController extends BaseController {

	private static final Logger logger = Logger.getLogger(ExpressbillBatchmasterController.class);
	
	/**
	 * 承运商账单导入批次表服务类
	 */
	@Resource(name = "expressbillBatchmasterServiceImpl")
	private ExpressbillBatchmasterService<ExpressbillBatchmaster> expressbillBatchmasterService;
	@Resource(name = "expressbillDetailServiceImpl")
	private ExpressbillDetailService<ExpressbillDetail> expressbillDetailService;
	@Resource(name = "diffBilldeatilsServiceImpl")
	private DiffBilldeatilsService<DiffBilldeatils> diffBilldeatilsService;
	
	
	@ResponseBody
	@RequestMapping(value="/input_excel.do")
	public JSONObject input_excel(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse res,ExpressbillBatchmasterQueryParam param) throws Exception{
		JSONObject obj =new JSONObject();
		try {
		String fileName= file.getOriginalFilename();
		if (!file.isEmpty()) {
            // 文件保存路径
			String filePath= CommonUtils.getAllMessage("config", "BALANCE_DIFFERENCE_UPLOAD_BILL_" + OSinfo.getOSname()) + fileName;
            File Flag=new File(filePath);
			if(Flag.exists()){
				obj.put("code", 0);
				obj.put("msg", "文件名不能重复！");
				return obj;
			}
			// 转存文件
           
            ExpressbillBatchmaster eb=new ExpressbillBatchmaster();
            eb.setBatch_id(UUID.randomUUID().toString());
            eb.setCreate_time(new Date());
            Employee user = SessionUtils.getEMP(req);
            eb.setCreate_user(Integer.toString(user.getId()));
            eb.setFail_num(0);
            eb.setMaster_id(param.getMaster_id());
            eb.setSuccess_num(0);
            eb.setTotal_num(0);
            eb.setStatus("0");//初始值
            eb.setLocal_address(filePath);
            expressbillBatchmasterService.save(eb);
            file.transferTo(new File(filePath));
            ExecutorService singleThreadExecutor=ThreadManageUtil.getSingleThreadExecutor();
    		ExpressBillInput tt=new ExpressBillInput();
    		singleThreadExecutor.execute(tt);
		}
		}catch(Exception e){
			obj.put("code", 0);
			e.printStackTrace();
		}
		obj.put("code", 1);
		return obj;
	}

	@ResponseBody
	@RequestMapping(value="/input_exceldiff.do")
	public JSONObject input_excel1(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse res,ExpressbillBatchmasterQueryParam param) throws Exception{
		JSONObject obj =new JSONObject();
		Employee user = SessionUtils.getEMP(req);
		String master_id=req.getParameter("master_id").toString();
		try {
		String fileName= file.getOriginalFilename();
		if (!file.isEmpty()) {
            // 文件保存路径
			String filePath= CommonUtils.getAllMessage("config", "BALANCE_DIFFERENCE_UPLOAD_BILL_" + OSinfo.getOSname()) + fileName;
            File Flag=new File(filePath);
			if(Flag.exists()){
				obj.put("code", 0);
				obj.put("msg", "文件名不能重复！");
				return obj;
			}
			// 转存文件
            file.transferTo(new File(filePath));
    		Map<String,String> param0=new HashMap<String,String>();
    		param0.put("file",filePath );
    		param0.put("master_id", master_id);
    		param0.put("user", user.getId().toString());
    		
            diffBilldeatilsService.insertDiff(param0);
           
		}
		}catch(Exception e){
			obj.put("code", 0);
			e.printStackTrace();
		}
		obj.put("code", 1);
		return obj;
	}
	
	@RequestMapping("pageQuery")
	public String pageQuery(HttpServletRequest request, HttpServletResponse res,
			ExpressbillBatchmasterQueryParam queryParam) {
		QueryResult<ExpressbillBatchmaster> qr = null;
		PageView<ExpressbillBatchmaster> pageView = new PageView<ExpressbillBatchmaster>(
				queryParam.getPageSize() == 0 ? BaseConst.pageSize : queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		qr = expressbillBatchmasterService.selectExpressBillBatch(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("totalSize", qr.getTotalrecord());
		request.setAttribute("queryParam", queryParam);
		return "lmis/verification/vfc_operation_page";
	}
	@ResponseBody
	@RequestMapping("transfer")
	public JSONObject transfer(HttpServletRequest request, HttpServletResponse res,
			ExpressbillBatchmasterQueryParam queryParam){
		JSONObject obj =new JSONObject();
		 Employee user = SessionUtils.getEMP(request);
		 queryParam.setUpdate_user(user.getId().toString());
		 queryParam.setUpdate_time(new Date());
		try{
		JSONObject flag=	expressbillBatchmasterService.checkFlow(queryParam,"transfer");
		    if(flag.get("code").toString()=="0"||"0".equals(flag.get("code").toString())){
		    	return flag;
		    }
			ExecutorService singleThreadExecutor=ThreadManageUtil.getSingleThreadExecutorTransfer();
			ExpressBillTransfer Et=new ExpressBillTransfer();
			Et.setQureyParam(queryParam);
			singleThreadExecutor.execute(Et);	obj.put("code", 1);
		}catch(Exception e){
			obj.put("code", 0);
			e.printStackTrace();
		}
		return obj;
	}
	
	
	
	
	
	private void checkFlow(ExpressbillBatchmasterQueryParam queryParam, String string) {
		// TODO Auto-generated method stub
		
	}

	@ResponseBody
	@RequestMapping("/deletevfc")
	public  JSONObject deletevfc(ExpressbillBatchmaster queryParam, HttpServletRequest request){
		JSONObject obj=new JSONObject();
		String id=request.getParameter("ids");
		ExpressbillBatchmaster expressbillBatchmaster = null;
			try {
				if(id !=null){
					expressbillBatchmaster = expressbillBatchmasterService.selectById(Integer.parseInt(id));
					if(expressbillBatchmaster.getStatus()==null){
						//if(expressbillBatchmaster.getStatus().equals("2")){
							obj.put("data", 1);
							return obj;
						//}
					}
				}
			} catch (Exception e1) {
				obj.put("data", 2);
				return obj;
			}
		try{
			if(id !=null){
				expressbillBatchmasterService.delete(id);
				expressbillDetailService.deleteByBat_id(expressbillBatchmaster.getBatch_id());
			}
			obj.put("data", 0);
		}catch(Exception e){
			obj.put("data", 2);
		}
		return obj;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/input_result.do")
	public JSONObject input_result(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse res,ExpressbillBatchmasterQueryParam param) throws Exception{
		JSONObject obj =new JSONObject();
		Employee user = SessionUtils.getEMP(req);
		String master_id=req.getParameter("master_id").toString();
		File Flag=null;
		try {
		String fileName= file.getOriginalFilename();
		if (!file.isEmpty()) {
            // 文件保存路径
			String filePath= CommonUtils.getAllMessage("config", "BALANCE_DIFFERENCE_UPLOAD_BILL_" + OSinfo.getOSname()) + fileName;
             Flag=new File(filePath);
			if(Flag.exists()){
				obj.put("code", 0);
				obj.put("msg", "文件名不能重复！");
				return obj;
			}
			// 转存文件
            file.transferTo(new File(filePath));
    		Map<String,String> param0=new HashMap<String,String>();
    		param0.put("file",filePath );
    		param0.put("master_id", master_id);
    		param0.put("user", user.getId().toString());
    		
            diffBilldeatilsService.insertDiffResult(param0);
           
		}
		}catch(Exception e){
			obj.put("code", 0);
			Flag.delete();
			e.printStackTrace();
		}finally{
			Flag.delete();
		}
		obj.put("code", 1);
		return obj;
	}
	
	
	
	
	
}
