package com.bt.wms.controller;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.bt.wms.model.Container;
import com.bt.wms.model.ContainerQueryParam;
import com.bt.wms.model.Cut;
import com.bt.wms.model.ErrorLog;
import com.bt.wms.model.LowerRecord;
import com.bt.wms.model.RecordQueryParam;
import com.bt.wms.model.UpperRecord;
import com.bt.wms.model.User;
import com.bt.wms.service.RecordService;
import com.bt.wms.service.UserService;
import com.bt.wms.utils.BaseConst;
import com.bt.wms.utils.BigExcelExport;
import com.bt.wms.utils.PageView;
import com.bt.wms.utils.SessionUtils;

@Controller
@RequestMapping("/control/mainController")
public class MainController {

	private static final Logger logger = Logger.getLogger(MainController.class);

	@Resource(name = "userServiceImpl")
	private UserService userServiceImpl;

	@Resource(name = "recordServiceImpl")
	private RecordService recordServiceImpl;
	
	@RequestMapping("/main_list")
	public String main_list(ModelMap map, HttpServletRequest request){
		User iflogin = SessionUtils.getEMP(request);
		if(null!=iflogin){
			request.getSession().setAttribute("session_employee", iflogin);
			return "/main";
		}else{
			return "/login";
		}
	}

	@RequestMapping("/manage_list")
	public String manage_list(ModelMap map, HttpServletRequest request){
		User iflogin = SessionUtils.getEMP(request);
		request.getSession().setAttribute("session_employee", iflogin);
		return "/manage/index";
	}
	

	@RequestMapping("/a")
	public String a(ContainerQueryParam queryParam,ModelMap map, HttpServletRequest request){
		User iflogin = SessionUtils.getEMP(request);
		request.getSession().setAttribute("session_employee", iflogin);
		if(null==queryParam.getType() || queryParam.getType().equals("0")){
			queryParam.setType(null);
		}
		//根据条件查询合同集合
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(queryParam.getPageSize()==0? BaseConst.pageSize: queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		pageView.setQueryResult(recordServiceImpl.queryContainer(queryParam), queryParam.getPage()); 
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "/manage/a";
	}
	

	@RequestMapping("/b")
	public String b(RecordQueryParam queryParam,ModelMap map, HttpServletRequest request){
		User iflogin = SessionUtils.getEMP(request);
		request.getSession().setAttribute("session_employee", iflogin);
		if(null==queryParam.getType() || queryParam.getType().equals("0")){
			queryParam.setType(null);
		}
		String stime = request.getParameter("stime");
		String etime = request.getParameter("etime");
		if(null!=stime && null!=etime){
			queryParam.setCreate_time_s(stime);
			queryParam.setCreate_time_e(etime);
		}else{
			Map<String, Date> datemap = getCurrentWeekMondaySunDay();
			queryParam.setCreate_time_s(formatDate(datemap.get("start")));
			queryParam.setCreate_time_e(formatDate(datemap.get("end")));
		}
		//根据条件查询合同集合
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(queryParam.getPageSize()==0? BaseConst.pageSize: queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		pageView.setQueryResult(recordServiceImpl.queryRecord(queryParam), queryParam.getPage()); 
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "/manage/b";
	}
	
	/**
	 * 获取当前周的起始时间和结束时间
	 * @return  Map 
	 * 			startday 	起始时间
	 * 			endday 		结束时间
	 */
	public static Map<String,Date> getCurrentWeekMondaySunDay(){ 
		Map<String,Date> map = new HashMap<String,Date>();
		Calendar currentDate = new GregorianCalendar();   
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);  
		
		currentDate.set(Calendar.HOUR_OF_DAY, 0);  
		currentDate.set(Calendar.MINUTE, 0);  
		currentDate.set(Calendar.SECOND, 0);  
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		map.put("start", currentDate.getTime());
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);  
		currentDate.set(Calendar.HOUR_OF_DAY, 23);  
		currentDate.set(Calendar.MINUTE, 59);  
		currentDate.set(Calendar.SECOND, 59);  
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		map.put("end", currentDate.getTime());
		return map;
	}

	public static String formatDate(Date date){
		SimpleDateFormat da=new SimpleDateFormat("yyyy-MM-dd");
		return da.format(date);
	 }
	
	@RequestMapping("/c")
	public String c(RecordQueryParam queryParam,ModelMap map, HttpServletRequest request){
		User iflogin = SessionUtils.getEMP(request);
		request.getSession().setAttribute("session_employee", iflogin);
		if(null==queryParam.getType() || queryParam.getType().equals("0")){
			queryParam.setType(null);
		}
		String stime = request.getParameter("stime");
		String etime = request.getParameter("etime");
		if(null!=stime && null!=etime){
			queryParam.setCreate_time_s(stime);
			queryParam.setCreate_time_e(etime);
		}else{
			Map<String, Date> datemap = getCurrentWeekMondaySunDay();
			queryParam.setCreate_time_s(formatDate(datemap.get("start")));
			queryParam.setCreate_time_e(formatDate(datemap.get("end")));
		}
		//根据条件查询合同集合
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(queryParam.getPageSize()==0? BaseConst.pageSize: queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		pageView.setQueryResult(recordServiceImpl.queryRecordCut(queryParam), queryParam.getPage()); 
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "/manage/c";
	}
	

	@RequestMapping("/d")
	public String d(RecordQueryParam queryParam,ModelMap map, HttpServletRequest request){
		User iflogin = SessionUtils.getEMP(request);
		request.getSession().setAttribute("session_employee", iflogin);
		if(null==queryParam.getType() || queryParam.getType().equals("0")){
			queryParam.setType(null);
		}
		String stime = request.getParameter("stime");
		String etime = request.getParameter("etime");
		if(null!=stime && null!=etime){
			queryParam.setCreate_time_s(stime);
			queryParam.setCreate_time_e(etime);
		}else{
			Map<String, Date> datemap = getCurrentWeekMondaySunDay();
			queryParam.setCreate_time_s(formatDate(datemap.get("start")));
			queryParam.setCreate_time_e(formatDate(datemap.get("end")));
		}
		//根据条件查询合同集合
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(queryParam.getPageSize()==0? BaseConst.pageSize: queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		pageView.setQueryResult(recordServiceImpl.querylog(queryParam), queryParam.getPage()); 
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "/manage/d";
	}
	/** 
	* @Title: return_page 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param map
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping("/return_page")
	public String return_page(@ModelAttribute("return_page") String return_page,ModelMap map, HttpServletRequest request,RedirectAttributes redirectAttributes){
		User iflogin = SessionUtils.getEMP(request);
		request.getSession().setAttribute("session_employee", iflogin);
		if(null!=return_page && !return_page.equals("")){
			return return_page;
		}else{
			return "/main";
		}
	}
	
	/** 
	* @Title: toSetContainer 
	* @Description: TODO(1.扫描容器号) 
	* @param @param map
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping("/toSetContainer")
	public String toSetContainer(ModelMap map, HttpServletRequest request,HttpServletResponse res,RedirectAttributes redirectAttributes){
		
		try {
			String opertion_type = null!=request.getParameter("opertion_type")?request.getParameter("opertion_type").toString():null;
			String close = null!=request.getParameter("close")?request.getParameter("close").toString():null;
			//上下架判断
			if(null!=opertion_type){
				//重置用户操作信息
				if(null!=close){
					User iflogin = SessionUtils.getEMP(request);
					request.getSession().setAttribute("session_employee", iflogin);
					User user = userServiceImpl.query_user_by_id(iflogin.getId()).get(0);
					Container query_container_code = new Container();
					query_container_code.setContainer_code(user.getContainer_code());
					Container container_code_list = recordServiceImpl.findByContainerCode(query_container_code).get(0);
					if (container_code_list.getNum()==0) {
						Container up_container = new Container();
//						String record_uuid = UUID.randomUUID().toString();
						up_container.setBat_id("");
						up_container.setId(container_code_list.getId());
						up_container.setNum(0);
						up_container.setLower_bat_id("");
						up_container.setEmp_id("");
						//1.已释放2.下架中3.下架完成4.上架中
						up_container.setType(1);
						recordServiceImpl.updateContainer(up_container);
					}else{
						Container up_container = new Container();
						String record_uuid = UUID.randomUUID().toString();
						up_container.setBat_id(record_uuid);
						up_container.setId(container_code_list.getId());
						up_container.setNum(container_code_list.getNum());
						if(opertion_type.equals("1") && close.equals("close")){
							up_container.setLower_bat_id(container_code_list.getBat_id());
						}
						up_container.setEmp_id(user.getUsername());
						//1.已释放2.下架中3.下架完成4.上架中
						up_container.setType(3);
						recordServiceImpl.updateContainer(up_container);
						user.setContainer_code("");
						user.setLocalhost_code("");
						user.setOpertion_type(opertion_type);
						userServiceImpl.update(user);
					}
				}
				if(opertion_type.equals("1")){
					redirectAttributes.addFlashAttribute("opertion_type", opertion_type);
					redirectAttributes.addFlashAttribute("return_page", "lower_1");
					return "redirect:/control/mainController/return_page.do";
				}else{
					redirectAttributes.addFlashAttribute("opertion_type", opertion_type);
					redirectAttributes.addFlashAttribute("return_page", "upper_1");
					return "redirect:/control/mainController/return_page.do";
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return "/main";
	}
	
	/** 
	* @Title: setContainer 
	* @Description: TODO(2.扫描容器号To扫描货位号) 
	* @param @param map
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping("/setContainer")
	public String setContainer(ModelMap map, HttpServletRequest request,RedirectAttributes redirectAttributes){
		try {
			String container_code = null!=request.getParameter("container_code")?request.getParameter("container_code").toString():null;
			String opertion_type = null!=request.getParameter("opertion_type")?request.getParameter("opertion_type").toString():null;
			User iflogin = SessionUtils.getEMP(request);
			request.getSession().setAttribute("session_employee", iflogin);
			//[容器号/操作人] 不能为空
			if(null!=container_code && null!=iflogin && null!=opertion_type){
				if(container_code.equals("")){
					redirectAttributes.addFlashAttribute("opertion_type", opertion_type);
					redirectAttributes.addFlashAttribute("message", "容器为空!");
					redirectAttributes.addFlashAttribute("return_page", "lower_1");
					return "redirect:/control/mainController/return_page.do";
				}
				User user = iflogin;
				Container container_query = new Container();
				container_query.setContainer_code(container_code);
				Container container_info = 0!= recordServiceImpl.findByContainerCode(container_query).size() ? recordServiceImpl.findByContainerCode(container_query).get(0):null;
				if(null==container_info){
					//判断是否存在，不在新增
					Container container_new = new Container();
					container_new.setContainer_code(container_code);
					container_new.setEmp_id(iflogin.getUsername());
					container_new.setType(1);
					container_new.setNum(0);
					container_new.setBat_id(UUID.randomUUID().toString());
					recordServiceImpl.insertContainer(container_new);
					container_info = 0!= recordServiceImpl.findByContainerCode(container_query).size() ? recordServiceImpl.findByContainerCode(container_query).get(0):null;
				}
				if(opertion_type.equals("1")){
					if(container_info.getType()==1 || container_info.getType()==2){
						if( container_info.getType()==2 && !container_info.getEmp_id().equals(iflogin.getUsername())){
							redirectAttributes.addFlashAttribute("opertion_type", opertion_type);
							redirectAttributes.addFlashAttribute("message", "容器使用者["+container_info.getEmp_id()+"]");
							redirectAttributes.addFlashAttribute("return_page", "lower_1");
							return "redirect:/control/mainController/return_page.do";
						}
						container_info.setType(2);
						container_info.setEmp_id(iflogin.getUsername());
					}else{
						redirectAttributes.addFlashAttribute("opertion_type", opertion_type);
						redirectAttributes.addFlashAttribute("message", "容器不可用!");
						redirectAttributes.addFlashAttribute("return_page", "lower_1");
						return "redirect:/control/mainController/return_page.do";
					}
				}else{
					if(container_info.getType()==3 || container_info.getType()==4){
						if( container_info.getType()==4 && !container_info.getEmp_id().equals(iflogin.getUsername())){
							redirectAttributes.addFlashAttribute("opertion_type", opertion_type);
							redirectAttributes.addFlashAttribute("message", "容器使用者["+container_info.getEmp_id()+"]");
							redirectAttributes.addFlashAttribute("return_page", "upper_1");
							return "redirect:/control/mainController/return_page.do";
						}
						container_info.setType(4);
						container_info.setEmp_id(iflogin.getUsername());
					}else{
						redirectAttributes.addFlashAttribute("opertion_type", opertion_type);
						redirectAttributes.addFlashAttribute("message", "容器不可用!");
						redirectAttributes.addFlashAttribute("return_page", "upper_1");
						return "redirect:/control/mainController/return_page.do";
					}
				}
				recordServiceImpl.updateContainer(container_info);
				user.setContainer_code(container_info.getContainer_code());
				user.setOpertion_type(opertion_type);
				userServiceImpl.update(user);
				redirectAttributes.addFlashAttribute("container_code", container_code);
				redirectAttributes.addFlashAttribute("opertion_type", opertion_type);
				if(opertion_type.equals("1")){
					redirectAttributes.addFlashAttribute("return_page", "lower_2");
					return "redirect:/control/mainController/return_page.do";
				}else{
					redirectAttributes.addFlashAttribute("return_page", "upper_2");
					redirectAttributes.addFlashAttribute("num", container_info.getNum());
					return "redirect:/control/mainController/return_page.do";
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return "/main";
	}
	
	/** 
	* @Title: setLocalhost 
	* @Description: TODO(3.扫描货位号To扫描SKU) 
	* @param @param map
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping("/setLocalhost")
	public String setLocalhost(ModelMap map, HttpServletRequest request,RedirectAttributes redirectAttributes){
		try {
			String container_code = null!=request.getParameter("container_code")?request.getParameter("container_code").toString():null;
			String localhost_code = null!=request.getParameter("localhost_code")?request.getParameter("localhost_code").toString():null;
			String opertion_type = null!=request.getParameter("opertion_type")?request.getParameter("opertion_type").toString():null;
			User iflogin = SessionUtils.getEMP(request);
			request.getSession().setAttribute("session_employee", iflogin);
			int lower_number = 0;
			//[容器号/货位号/操作人] 不能为空
			if(null!=container_code && null!=localhost_code && null!=iflogin && null!=opertion_type){
				User user = iflogin;
				user.setContainer_code(container_code);
				user.setLocalhost_code(localhost_code);
				user.setOpertion_type(opertion_type);
				userServiceImpl.update(user);
				Container ifcontainer = new Container();
				ifcontainer.setContainer_code(container_code);
				List<Container> containercode = recordServiceImpl.findByContainerCode(ifcontainer);
				redirectAttributes.addFlashAttribute("container_number", containercode.get(0).getNum());
				redirectAttributes.addFlashAttribute("container_code", container_code);
				redirectAttributes.addFlashAttribute("localhost_code", localhost_code);
				if(opertion_type.equals("1")){
					redirectAttributes.addFlashAttribute("opertion_type", opertion_type);
					redirectAttributes.addFlashAttribute("lower_number", lower_number);
					redirectAttributes.addFlashAttribute("return_page", "lower_3");
					return "redirect:/control/mainController/return_page.do";
				}else{
					redirectAttributes.addFlashAttribute("opertion_type", opertion_type);
					redirectAttributes.addFlashAttribute("upper_number", lower_number);
					redirectAttributes.addFlashAttribute("return_page", "upper_3");
					return "redirect:/control/mainController/return_page.do";
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return "/2";
	}
	
	/** 
	* @Title: scanning_sku 
	* @Description: TODO(扫描SKU信息) 
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/scanning_sku")
	public String scanning_sku(ModelMap map,HttpServletRequest request,HttpServletResponse res,RedirectAttributes redirectAttributes){
		try {
			String container_code = null!=request.getParameter("container_code")?request.getParameter("container_code").toString():null;
			String localhost_code = null!=request.getParameter("localhost_code")?request.getParameter("localhost_code").toString():null;
			String opertion_type = null!=request.getParameter("opertion_type")?request.getParameter("opertion_type").toString():null;
			String sku = null!=request.getParameter("sku")?request.getParameter("sku").toString():null;
			User iflogin = SessionUtils.getEMP(request);
			request.getSession().setAttribute("session_employee", iflogin);
			int lower_number = 0;
			int upper_number = 0;
			int container_number = 0;
			//[容器号/货位号/操作人] 不能为空
			if(null!=container_code && null!=localhost_code && null!=iflogin && null!=opertion_type && null!=sku){
				Container ifcontainer = new Container();
				ifcontainer.setContainer_code(container_code);
				List<Container> containercode = recordServiceImpl.findByContainerCode(ifcontainer);
				if(null!=containercode && containercode.size()>=1){
					//修改用户当前操作容器和货位以及操作类型
					User user = iflogin;
					container_code=containercode.get(0).getContainer_code();
					String bat_id=containercode.get(0).getBat_id();
					user.setContainer_code(container_code);
					user.setLocalhost_code(localhost_code);
					user.setOpertion_type(opertion_type);
					userServiceImpl.update(user);
					if(opertion_type.equals("1")){
						//新增下架记录
						LowerRecord insertLower = new LowerRecord();
						insertLower.setCreate_user(iflogin.getUsername());
						insertLower.setCreate_time(new Date());
						insertLower.setContainer_code(container_code);
						insertLower.setLocation(localhost_code);
						insertLower.setNum(1);
						insertLower.setSku(sku);
						insertLower.setBat_id(bat_id);
						recordServiceImpl.insertLower(insertLower);
						//查询这个批次下架SKU数量
						LowerRecord query_lower = new LowerRecord();
						query_lower.setBat_id(bat_id);
						List<LowerRecord> container_number_list =  recordServiceImpl.findByLowerRecord(query_lower);
						query_lower = new LowerRecord();
						query_lower.setBat_id(bat_id);
						query_lower.setSku(sku);
						List<LowerRecord> lower_number_list =  recordServiceImpl.findByLowerRecord(query_lower);
						redirectAttributes.addFlashAttribute("sku", sku);
						redirectAttributes.addFlashAttribute("container_code", container_code);
						redirectAttributes.addFlashAttribute("localhost_code", localhost_code);
						redirectAttributes.addFlashAttribute("opertion_type", opertion_type);
						Container container = recordServiceImpl.findByContainerCode(ifcontainer).get(0);
						container.setNum(container.getNum()+1);
						recordServiceImpl.updateContainer(container);
						redirectAttributes.addFlashAttribute("return_page", "lower_3");
						redirectAttributes.addFlashAttribute("lower_number", null!=lower_number_list ?lower_number_list.size():lower_number);
						redirectAttributes.addFlashAttribute("container_number", null!=container_number_list ?container_number_list.size():container_number);
						return "redirect:/control/mainController/return_page.do";
					}else{
						String lower_bat_id = containercode.get(0).getLower_bat_id();
						List<Cut>  lrList = recordServiceImpl.findLowerRecordNumber(lower_bat_id, bat_id, sku);
						int cut = 0;
						if(null!=lrList && lrList.size()>0){
							cut = 0!=lrList.get(0).getCut()?lrList.get(0).getCut():0;
						}
						if(null!=lrList && lrList.size()>0 && cut>0){
							//新增上架记录
							UpperRecord upperLower = new UpperRecord();
							upperLower.setCreate_user(iflogin.getUsername());
							upperLower.setCreate_time(new Date());
							upperLower.setContainer_code(container_code);
							upperLower.setLocation(localhost_code);
							upperLower.setNum(1);
							upperLower.setSku(sku);
							upperLower.setBat_id(bat_id);
							recordServiceImpl.insertUpper(upperLower);
							//查询这个批次下架SKU数量
							Container container = recordServiceImpl.findByContainerCode(ifcontainer).get(0);
							if(container.getNum()-1==0){
								Container co = new Container();
								co.setType(1);
								co.setBat_id(UUID.randomUUID().toString());
								co.setNum(0);
								co.setLower_bat_id("");
								co.setId(containercode.get(0).getId());
								recordServiceImpl.updateContainer(co);
								redirectAttributes.addFlashAttribute("return_page", "upper_1");
							}else{
								container.setNum(container.getNum()-1);
								recordServiceImpl.updateContainer(container);
								redirectAttributes.addFlashAttribute("return_page", "upper_3");
								redirectAttributes.addFlashAttribute("container_number", null!=containercode ?containercode.get(0).getNum()-1:container_number);
							}
						}else{
							redirectAttributes.addFlashAttribute("message", "SKU不存在!");
							redirectAttributes.addFlashAttribute("return_page", "upper_3");
							redirectAttributes.addFlashAttribute("container_number", null!=containercode ?containercode.get(0).getNum():container_number);
						}
						UpperRecord query_lower = new UpperRecord();
						query_lower.setBat_id(bat_id);
						List<UpperRecord> upper_number_list =  recordServiceImpl.findByUpperRecord(query_lower);
						redirectAttributes.addFlashAttribute("opertion_type", opertion_type);
						redirectAttributes.addFlashAttribute("container_code", container_code);
						redirectAttributes.addFlashAttribute("localhost_code", localhost_code);
						redirectAttributes.addFlashAttribute("upper_number", null!=upper_number_list ?upper_number_list.size():upper_number);
						return "redirect:/control/mainController/return_page.do";
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return "/main";
	}
	

	@RequestMapping("/ajx_scanning_sku")
	public void ajx_scanning_sku(ModelMap map,HttpServletRequest request,HttpServletResponse res,RedirectAttributes redirectAttributes){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		JSONObject returnJson = new JSONObject();
		
		PrintWriter out = null;
		try {
			String container_code = null!=request.getParameter("container_code")?request.getParameter("container_code").toString():null;
			String localhost_code = null!=request.getParameter("localhost_code")?request.getParameter("localhost_code").toString():null;
			String opertion_type = null!=request.getParameter("opertion_type")?request.getParameter("opertion_type").toString():null;
			String sku = null!=request.getParameter("sku")?request.getParameter("sku").toString():null;

			User iflogin = SessionUtils.getEMP(request);
			request.getSession().setAttribute("session_employee", iflogin);
			int lower_number = 0;
			int upper_number = 0;
			int container_number = 0;
			returnJson.put("code",  "500");
			//[容器号/货位号/操作人] 不能为空
			if(null!=container_code && null!=localhost_code && null!=iflogin && null!=opertion_type && null!=sku){
				Container ifcontainer = new Container();
				ifcontainer.setContainer_code(container_code);
				List<Container> containercode = recordServiceImpl.findByContainerCode(ifcontainer);
				if(null!=containercode && containercode.size()>=1){
					//修改用户当前操作容器和货位以及操作类型
					User user = iflogin;
					container_code=containercode.get(0).getContainer_code();
					String bat_id=containercode.get(0).getBat_id();
					user.setContainer_code(container_code);
					user.setLocalhost_code(localhost_code);
					user.setOpertion_type(opertion_type);
					userServiceImpl.update(user);
					if(opertion_type.equals("1")){
						//新增下架记录
						LowerRecord insertLower = new LowerRecord();
						insertLower.setCreate_user(iflogin.getUsername());
						insertLower.setCreate_time(new Date());
						insertLower.setContainer_code(container_code);
						insertLower.setLocation(localhost_code);
						insertLower.setNum(1);
						insertLower.setSku(sku);
						insertLower.setBat_id(bat_id);
						recordServiceImpl.insertLower(insertLower);
						//查询这个批次下架SKU数量
						LowerRecord query_lower = new LowerRecord();
						query_lower.setBat_id(bat_id);
						List<LowerRecord> container_number_list =  recordServiceImpl.findByLowerRecord(query_lower);
						query_lower = new LowerRecord();
						query_lower.setBat_id(bat_id);
						query_lower.setSku(sku);
						List<LowerRecord> lower_number_list =  recordServiceImpl.findByLowerRecord(query_lower);
						redirectAttributes.addFlashAttribute("sku", sku);
						redirectAttributes.addFlashAttribute("container_code", container_code);
						redirectAttributes.addFlashAttribute("localhost_code", localhost_code);
						redirectAttributes.addFlashAttribute("opertion_type", opertion_type);
						Container container = recordServiceImpl.findByContainerCode(ifcontainer).get(0);
						container.setNum(container.getNum()+1);
						recordServiceImpl.updateContainer(container);
						returnJson.put("code",  "200");
						returnJson.put("lower_number",  null!=lower_number_list ?lower_number_list.size():lower_number);
						returnJson.put("return_page",  "lower_3");
						returnJson.put("container_number", null!=container_number_list ?container_number_list.size():container_number);
					}else{
						String lower_bat_id = containercode.get(0).getLower_bat_id();
						List<Cut>  lrList = recordServiceImpl.findLowerRecordNumber(lower_bat_id, bat_id, sku);
						int cut = 0;
						if(null!=lrList && lrList.size()>0){
							cut = 0!=lrList.get(0).getCut()?lrList.get(0).getCut():0;
						}
						if(null!=lrList && lrList.size()>0 && cut>0){
							//新增上架记录
							UpperRecord upperLower = new UpperRecord();
							upperLower.setCreate_user(iflogin.getUsername());
							upperLower.setCreate_time(new Date());
							upperLower.setContainer_code(container_code);
							upperLower.setLocation(localhost_code);
							upperLower.setNum(1);
							upperLower.setSku(sku);
							upperLower.setBat_id(bat_id);
							recordServiceImpl.insertUpper(upperLower);
							//查询这个批次下架SKU数量
							Container container = recordServiceImpl.findByContainerCode(ifcontainer).get(0);
							if(container.getNum()-1==0){
								Container co = new Container();
								co.setType(1);
								co.setBat_id(UUID.randomUUID().toString());
								co.setNum(0);
								co.setLower_bat_id("");
								co.setId(containercode.get(0).getId());
								recordServiceImpl.updateContainer(co);
								returnJson.put("return_page",  "upper_1");
								returnJson.put("code",  "201");
							}else{
								container.setNum(container.getNum()-1);
								recordServiceImpl.updateContainer(container);
								returnJson.put("code",  "200");
								returnJson.put("return_page",  "upper_3");
								returnJson.put("container_number", null!=containercode ?containercode.get(0).getNum()-1:container_number);
							}
						}else{
							returnJson.put("code",  "500");
							returnJson.put("message", "SKU不存在!");
							returnJson.put("return_page", "upper_3");
							returnJson.put("container_number", null!=containercode ?containercode.get(0).getNum():container_number);
						}
						UpperRecord query_lower = new UpperRecord();
						query_lower.setBat_id(bat_id);
						List<UpperRecord> upper_number_list =  recordServiceImpl.findByUpperRecord(query_lower);
						returnJson.put("opertion_type", opertion_type);
						returnJson.put("container_code", container_code);
						returnJson.put("localhost_code", localhost_code);
						returnJson.put("upper_number", null!=upper_number_list ?upper_number_list.size():upper_number);
					}
				}
			}
			out = res.getWriter();
			out.write(returnJson.toString());
		} catch (Exception e) {
			logger.error(e);
		}
		out.flush();
		out.close();
	}

	@RequestMapping("/export_a")
	public void export_a(ModelMap map,HttpServletRequest request,HttpServletResponse res,RedirectAttributes redirectAttributes){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		File file =new File("");
		try {
			out = res.getWriter();
			String code = null!=request.getParameter("container_code")?request.getParameter("container_code"):"";
			String type = 0!=Integer.valueOf(request.getParameter("type"))?request.getParameter("type"):"";
			List<Map<String, Object>> list = recordServiceImpl.queryC(code,type);
			LinkedHashMap<String, String>CMap=new LinkedHashMap<String,String>();
			CMap.put("code", "容器编码");
			CMap.put("user", "使用者");
			CMap.put("type", "状态");
			CMap.put("num", "剩余数量");
			file=BigExcelExport.excelDownLoadDatab(list,CMap,"容器.xls");
		} catch (Exception e) {
			logger.error(e);
		}
		out.write("/file/"+file.getName());
		out.flush();
		out.close();
	}
	

	@RequestMapping("/export_b")
	public void export_b(ModelMap map,HttpServletRequest request,HttpServletResponse res,RedirectAttributes redirectAttributes){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		File file =new File("");
		try {
			out = res.getWriter();
			String container_code =null!=request.getParameter("container_code")?request.getParameter("container_code").toString():"";
			String stime =null!=request.getParameter("stime")?request.getParameter("stime").toString():"";
			String etime=null!=request.getParameter("etime")?request.getParameter("etime").toString():"";
			String type=null!=request.getParameter("type")?request.getParameter("type").toString():"";
			String create_user=null!=request.getParameter("create_user")?request.getParameter("create_user").toString():"";
			String location=null!=request.getParameter("location")?request.getParameter("location").toString():"";
			String sku=null!=request.getParameter("sku")?request.getParameter("sku").toString():"";
			List<Map<String, Object>> list = recordServiceImpl.queryB(container_code, stime, etime, type, create_user, location, sku);
			LinkedHashMap<String, String>CMap=new LinkedHashMap<String,String>();
			CMap.put("create_time", "操作时间");
			CMap.put("create_user", "操作人");
			CMap.put("type", "操作类型");
			CMap.put("container_code", "容器号");
			CMap.put("location", "货位号");
			CMap.put("sku", "SKU条码");
			CMap.put("num", "数量");
			file=BigExcelExport.excelDownLoadDatab(list,CMap,"上下架记录.xls");
		} catch (Exception e) {
			logger.error(e);
		}
		out.write("/file/"+file.getName());
		out.flush();
		out.close();
	}
	
	@RequestMapping("/export_d")
	public void export_d(ModelMap map,HttpServletRequest request,HttpServletResponse res,RedirectAttributes redirectAttributes){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		File file =new File("");
		try {
			out = res.getWriter();
			String container_code =null!=request.getParameter("container_code")?request.getParameter("container_code").toString():"";
			String stime =null!=request.getParameter("stime")?request.getParameter("stime").toString():"";
			String etime=null!=request.getParameter("etime")?request.getParameter("etime").toString():"";
			String type=null!=request.getParameter("type")?request.getParameter("type").toString():"";
			String create_user=null!=request.getParameter("create_user")?request.getParameter("create_user").toString():"";
			String location=null!=request.getParameter("location")?request.getParameter("location").toString():"";
			String sku=null!=request.getParameter("sku")?request.getParameter("sku").toString():"";
			List<Map<String, Object>> list = recordServiceImpl.queryD(container_code, stime, etime, type, create_user, location, sku);
			LinkedHashMap<String, String>CMap=new LinkedHashMap<String,String>();
			CMap.put("create_time", "操作时间");
			CMap.put("create_user", "操作人");
			CMap.put("type", "操作类型");
			CMap.put("container_code", "容器号");
			CMap.put("location", "货位号");
			CMap.put("sku", "SKU条码");
			CMap.put("cut", "数量");
			file=BigExcelExport.excelDownLoadDatab(list,CMap,"上下架汇总记录查询.xls");
		} catch (Exception e) {
			logger.error(e);
		}
		out.write("/file/"+file.getName());
		out.flush();
		out.close();
	}
	

	@RequestMapping("/export_e")
	public void export_e(ModelMap map,HttpServletRequest request,HttpServletResponse res,RedirectAttributes redirectAttributes){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		File file =new File("");
		try {
			out = res.getWriter();
			String container_code =null!=request.getParameter("container_code")?request.getParameter("container_code").toString():"";
			String stime =null!=request.getParameter("stime")?request.getParameter("stime").toString():"";
			String etime=null!=request.getParameter("etime")?request.getParameter("etime").toString():"";
			String create_user=null!=request.getParameter("create_user")?request.getParameter("create_user").toString():"";
			String sku=null!=request.getParameter("sku")?request.getParameter("sku").toString():"";
			List<Map<String, Object>> list = recordServiceImpl.queryE(container_code, stime, etime, create_user, sku);
			LinkedHashMap<String, String>CMap=new LinkedHashMap<String,String>();
			CMap.put("create_time", "操作时间");
			CMap.put("create_user", "操作人");
			CMap.put("container_code", "容器号");
			CMap.put("sku", "SKU条码");
			CMap.put("num", "数量");
			file=BigExcelExport.excelDownLoadDatab(list,CMap,"异常记录汇总.xls");
		} catch (Exception e) {
			logger.error(e);
		}
		out.write("/file/"+file.getName());
		out.flush();
		out.close();
	}
	
	@RequestMapping("/close_c")
	public void close_c(ModelMap map,HttpServletRequest request,HttpServletResponse res,RedirectAttributes redirectAttributes){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		JSONObject returnJson = new JSONObject();
		
		PrintWriter out = null;
		try {
			out = res.getWriter();
			String container_code = null!=request.getParameter("container_code")?request.getParameter("container_code").toString():null;
			Container ifcontainer = new Container();
			ifcontainer.setContainer_code(container_code);
			List<Container> containercode = recordServiceImpl.findByContainerCode(ifcontainer);
			int id = containercode.get(0).getId();
			List<Map<String, Object>> cy_lower = recordServiceImpl.query_lower_cy(Integer.valueOf(id));
			List<Map<String, Object>> cy_upper = recordServiceImpl.query_upper_cy(Integer.valueOf(id));
			List<Map<String, Object>> returnList = new ArrayList<>();
			for (int i = 0; i < cy_lower.size(); i++) {
				Map<String, Object> returnMap = new HashMap<>();
				String sku = null!=cy_lower.get(i).get("sku")?cy_lower.get(i).get("sku").toString():"";
				String cut = null!=cy_lower.get(i).get("cut")?cy_lower.get(i).get("cut").toString():"";
				returnMap.put("sku", sku);
				returnMap.put("cut", cut);
				returnMap.put("container_code", container_code);
				for (int j = 0; j < cy_upper.size(); j++) {
					String sku_b = null!=cy_upper.get(j).get("sku")?cy_upper.get(j).get("sku").toString():"";
					String cut_b = null!=cy_upper.get(j).get("cut")?cy_upper.get(j).get("cut").toString():"";
					if(!sku.equals("")){
						if(sku.equals(sku_b)){
							int a = Integer.valueOf(cut);
							int b = Integer.valueOf(cut_b);
							int c = a-b;
							returnMap.put("cut", c);
						}
					}
				}
				returnList.add(returnMap);
			}
			for (int i = 0; i < returnList.size(); i++) {
				Map<String, Object> rMap = returnList.get(i);
				ErrorLog log = new ErrorLog();
				log.setCreate_time(new Date());
				log.setContainer_code(container_code);
				log.setCreate_user("管理员");
				log.setSku(null!=rMap.get("sku")?rMap.get("sku").toString():"");
				log.setNum(null!=rMap.get("cut")?rMap.get("cut").toString():"0");
				recordServiceImpl.insertlog(log);
			}
			Container co = new Container();
			co.setType(1);
			co.setBat_id(UUID.randomUUID().toString());
			co.setNum(0);
			co.setLower_bat_id("");
			co.setId(id);
			recordServiceImpl.updateContainer(co);
			returnJson.put("code", "200");
			redirectAttributes.addFlashAttribute("return_page", "upper_1");
			out.write(returnJson.toString());
		} catch (Exception e) {
			e.printStackTrace();
			returnJson.put("code", "500");
		}
		out.flush();
		out.close();
	}

	@RequestMapping("/a_dtl")
	public String a_dtl(ModelMap map, HttpServletRequest request){
		User iflogin = SessionUtils.getEMP(request);
		request.getSession().setAttribute("session_employee", iflogin);
		String id = request.getParameter("id");
		Container container = new Container();
		container.setId(Integer.valueOf(id));
		Container containers = recordServiceImpl.findByContainerCode(container).get(0);
		if (containers.getType()==1 || containers.getType()==2) {
			List<Map<String, Object>> cy_list = recordServiceImpl.queryCY(Integer.valueOf(id));
			request.setAttribute("containerList", cy_list);
		}else if(containers.getType()==3){
			List<Map<String, Object>> cy_list = recordServiceImpl.queryLCY(Integer.valueOf(id));
			request.setAttribute("containerList", cy_list);
		}else if (containers.getType()==4){
			List<Map<String, Object>> cy_lower = recordServiceImpl.query_lower_cy(Integer.valueOf(id));
			List<Map<String, Object>> cy_upper = recordServiceImpl.query_upper_cy(Integer.valueOf(id));
			List<Map<String, Object>> returnList = new ArrayList<>();
			for (int i = 0; i < cy_lower.size(); i++) {
				Map<String, Object> returnMap = new HashMap<>();
				String sku = null!=cy_lower.get(i).get("sku")?cy_lower.get(i).get("sku").toString():"";
				String cut = null!=cy_lower.get(i).get("cut")?cy_lower.get(i).get("cut").toString():"";
				returnMap.put("sku", sku);
				returnMap.put("cut", cut);
				returnMap.put("container_code", containers.getContainer_code());
				for (int j = 0; j < cy_upper.size(); j++) {
					String sku_b = null!=cy_upper.get(j).get("sku")?cy_upper.get(j).get("sku").toString():"";
					String cut_b = null!=cy_upper.get(j).get("cut")?cy_upper.get(j).get("cut").toString():"";
					if(!sku.equals("")){
						if(sku.equals(sku_b)){
							int a = Integer.valueOf(cut);
							int b = Integer.valueOf(cut_b);
							int c = a-b;
							returnMap.put("cut", c);
						}
					}
				}
				returnList.add(returnMap);
			}
			request.setAttribute("containerList", returnList);
		}
		return "/manage/a_1";
	}
	
	
	
	public Map<String,String>spiltDateString(String param){
		Map<String, String> map=new HashMap<String,String>();
		if(isEmpty(param)){
			return map;
		}
        String date[]=param.split(" - ");
        if(date.length<2){
        	return null;
        }
		map.put("startDate",date[0]);
		map.put("endDate",date[1]);
		return map;
	}

	public boolean isEmpty(String args){
		if(args==null || args==""){
			return true;
		}
		if(args!=null && args.equals("")){
			return true;
		}
		return false;
	}
}
