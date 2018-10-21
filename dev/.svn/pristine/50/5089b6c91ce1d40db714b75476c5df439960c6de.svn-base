package com.bt.radar.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.model.Employee;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.RoutecodeQueryParam;
import com.bt.radar.model.Routecode;
import com.bt.radar.model.RoutecodeBean;
import com.bt.radar.service.RoutecodeService;
import com.bt.utils.BaseConst;
import com.bt.utils.SessionUtils;
/**
 * 快递状态代码控制器
 *
 */
@Controller
@RequestMapping("/control/radar/routecodeController")
public class RoutecodeController extends BaseController {

	private static final Logger logger = Logger.getLogger(RoutecodeController.class);
	
	/**
	 * 快递状态代码服务类
	 */
	@Resource(name = "routecodeServiceImpl")
	private RoutecodeService<Routecode> routecodeServiceImpl;
	
	/**
	 * 查询快递状态代码
	 */
	@RequestMapping("/query")
	public String queryStatus(RoutecodeQueryParam queryParam, HttpServletRequest request){
		try{
		PageView<RoutecodeBean> pageView = new PageView<RoutecodeBean>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<RoutecodeBean> qr =routecodeServiceImpl.findAllData(queryParam);
		List list=routecodeServiceImpl.selectTransport_vender();
		request.setAttribute("trans_names", list);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			logger.error(e);
		}
		return "/radar/express_code_query";
	}
	
	
	/**
	 * 删除快递状态代码
	 */
	
	@ResponseBody
	@RequestMapping("/delete")
	public  JSONObject  delete(RoutecodeQueryParam queryParam, HttpServletRequest request){
		JSONObject obj =new JSONObject();
		//删除记录前 先检验是否有运单已使用这条记录
		
		try {
			routecodeServiceImpl.delete(queryParam.getId());
			obj.put("code", 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			obj.put("code", 0);
			logger.error(e);
		}
		
		return obj;
	}
	
	
	@RequestMapping("/addEdit")
	public String addEdit(RoutecodeQueryParam queryParam, HttpServletRequest request){
		List list=routecodeServiceImpl.selectTransport_vender();
		request.setAttribute("trans_names", list);
		return   "/radar/express_code_add";
	}
	
	
	/**
	 * 新增快递状态代码
	 */
	
	@ResponseBody
	@RequestMapping("/add")
	public  JSONObject  add(Routecode entity, HttpServletRequest request){
		JSONObject obj =new JSONObject();
		entity.setId(UUID.randomUUID().toString());
		Date d=new Date();
		entity.setCreate_time(d);
		entity.setUpdate_time(d);
		entity.setDl_flag(1);
		Employee user = SessionUtils.getEMP(request);
		entity.setCreate_user(user.getName());
		entity.setUpdate_user(user.getName());
		try {
			routecodeServiceImpl.save(entity);
			obj.put("code", 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			obj.put("code", 0);
			logger.error(e);
		}
		
		return obj;
	}
	
	
	@RequestMapping("/toupdate")
	public String toupdate(RoutecodeQueryParam queryParam, HttpServletRequest request){
		List l=routecodeServiceImpl.findAllRecord(queryParam);
		System.out.println(l.get(0));
        request.setAttribute("record", l.get(0));
        List list=routecodeServiceImpl.selectTransport_vender();
		request.setAttribute("trans_names", list);
		return   "/radar/express_code_edit";
	}

	@SuppressWarnings("deprecation")
	@ResponseBody
	@RequestMapping("/update")
	public JSONObject updateRoute(RoutecodeQueryParam queryParam, HttpServletRequest request) throws ParseException{
		JSONObject obj=new JSONObject();
		Routecode entity=new Routecode();
		entity.setId(queryParam.getId());
		entity.setDl_flag(1);
		entity.setRemark(queryParam.getRemark());
		entity.setStatus(queryParam.getStatus());
		entity.setStatus_code(queryParam.getStatus_code());
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		    Date date = sdf.parse(queryParam.getCreate_time());  
		entity.setCreate_time(date);
		entity.setFlag(queryParam.getFlag());
		entity.setTransport_code(queryParam.getTransport_code());
		Employee user = SessionUtils.getEMP(request);
		entity.setUpdate_user(user.getName());
		entity.setUpdate_time(new Date());
		entity.setCreate_user(queryParam.getCreate_user());
		try {
			routecodeServiceImpl.update(entity);
			obj.put("code", 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			obj.put("code", 0);
			e.printStackTrace();
		}
		
		return  obj;
	}
}
