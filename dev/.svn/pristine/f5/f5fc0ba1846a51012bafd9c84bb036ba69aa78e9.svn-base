package com.bt.wms.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bt.wms.model.Container;
import com.bt.wms.model.ErrorLog;
import com.bt.wms.model.LowerRecord;
import com.bt.wms.model.UpperRecord;
import com.bt.wms.model.User;
import com.bt.wms.service.NewMainService;
import com.bt.wms.service.RecordService;
import com.bt.wms.service.UserService;
import com.bt.wms.utils.SessionUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/control/newMainController")
public class NewMainController {

	private static final Logger logger = Logger.getLogger(NewMainController.class);

	@Resource(name = "newMainServiceImpl")
	private NewMainService newMainServiceImpl;

	@Resource(name = "recordServiceImpl")
	private RecordService recordServiceImpl;

	@Resource(name = "userServiceImpl")
	private UserService userServiceImpl;
	/** 
	* @Title: return_localhost 
	* @Description: TODO(下架返回-提交数据返回状态成功返回货位页面) 
	* @param @param map
	* @param @param request
	* @param @param res
	* @param @param redirectAttributes    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/return_localhost")
	public void return_localhost(ModelMap map,HttpServletRequest request,HttpServletResponse res,RedirectAttributes redirectAttributes){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		JSONObject returnJson = new JSONObject();
		PrintWriter out = null;
		returnJson.put("code",  "500");
		returnJson.put("message", "系统异常!");
		try {
			User iflogin = SessionUtils.getEMP(request);
			out = res.getWriter();
			String container_code = null!=request.getParameter("container_code")?request.getParameter("container_code").toString():null;
			String localhost_code = null!=request.getParameter("localhost_code")?request.getParameter("localhost_code").toString():null;
			String opertion_type = null!=request.getParameter("opertion_type")?request.getParameter("opertion_type").toString():null;
			String main_body = null!=request.getParameter("main_body")?request.getParameter("main_body"):"";
			if(!main_body.equals("")){
				Container ifcontainer = new Container();
				ifcontainer.setContainer_code(container_code);
				List<Container> containercode = recordServiceImpl.findByContainerCode(ifcontainer);
				if(null!=containercode && containercode.size()>=1){
					String bat_id=containercode.get(0).getBat_id();
					JSONArray myJsonObject = JSONArray.fromObject(main_body);
					User user = iflogin;
					container_code=containercode.get(0).getContainer_code();
					user.setContainer_code(container_code);
					user.setLocalhost_code(localhost_code);
					user.setOpertion_type(opertion_type);
					userServiceImpl.update(user);
					int sumnum = 0;
					List<LowerRecord> list = new ArrayList<>();
					for (int i = 0; i < myJsonObject.size(); i++) {
						JSONObject job = myJsonObject.getJSONObject(i);
						String sku = job.getString("sku");
						int num = job.getInt("num");
						for (int j = 0; j < num; j++) {
							LowerRecord insertLower = new LowerRecord();
							insertLower.setCreate_user(iflogin.getUsername());
							insertLower.setCreate_time(new Date());
							insertLower.setContainer_code(container_code);
							insertLower.setLocation(localhost_code);
							insertLower.setNum(1);
							insertLower.setSku(sku);
							insertLower.setBat_id(bat_id);
							list.add(insertLower);
							sumnum=sumnum+1;
						}
					}
					recordServiceImpl.insertLowers(list);
					Container container = recordServiceImpl.findByContainerCode(ifcontainer).get(0);
					container.setNum(container.getNum()+sumnum);
					recordServiceImpl.updateContainer(container);
				}
			}
			returnJson.put("code", "200");
			out.write(returnJson.toString());
		} catch (Exception e) {
			logger.error(e);
		}
		out.flush();
		out.close();
	}
	

	@RequestMapping("/return_localhost_upper")
	public void return_localhost_upper(ModelMap map,HttpServletRequest request,HttpServletResponse res,RedirectAttributes redirectAttributes){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		JSONObject returnJson = new JSONObject();
		PrintWriter out = null;
		returnJson.put("code",  "500");
		returnJson.put("message", "系统异常!");
		try {
			User iflogin = SessionUtils.getEMP(request);
			out = res.getWriter();
			String container_code = null!=request.getParameter("container_code")?request.getParameter("container_code").toString():null;
			String localhost_code = null!=request.getParameter("localhost_code")?request.getParameter("localhost_code").toString():null;
			String opertion_type = null!=request.getParameter("opertion_type")?request.getParameter("opertion_type").toString():null;
			String main_body = null!=request.getParameter("main_body")?request.getParameter("main_body"):"";
			if(!main_body.equals("")){
				Container ifcontainer = new Container();
				ifcontainer.setContainer_code(container_code);
				List<Container> containercode = recordServiceImpl.findByContainerCode(ifcontainer);
				if(null!=containercode && containercode.size()>=1){
					String bat_id=containercode.get(0).getBat_id();
					JSONArray myJsonObject = JSONArray.fromObject(main_body);
					User user = iflogin;
					container_code=containercode.get(0).getContainer_code();
					user.setContainer_code(container_code);
					user.setLocalhost_code(localhost_code);
					user.setOpertion_type(opertion_type);
					userServiceImpl.update(user);
					int sumnum = 0;
					List<UpperRecord> list = new ArrayList<>();
					for (int i = 0; i < myJsonObject.size(); i++) {
						JSONObject job = myJsonObject.getJSONObject(i);
						String sku = job.getString("sku");
						int num = job.getInt("num");
						for (int j = 0; j < num; j++) {
							UpperRecord upperLower = new UpperRecord();
							upperLower.setCreate_user(iflogin.getUsername());
							upperLower.setCreate_time(new Date());
							upperLower.setContainer_code(container_code);
							upperLower.setLocation(localhost_code);
							upperLower.setNum(1);
							upperLower.setSku(sku);
							upperLower.setBat_id(bat_id);
							sumnum=sumnum-1;
							list.add(upperLower);
						}
					}
					recordServiceImpl.insertUppers(list);
					Container container = recordServiceImpl.findByContainerCode(ifcontainer).get(0);
					if(container.getNum()+sumnum==0){
						Container co = new Container();
						co.setType(1);
						co.setBat_id(UUID.randomUUID().toString());
						co.setNum(0);
						co.setLower_bat_id("");
						co.setId(containercode.get(0).getId());
						recordServiceImpl.updateContainer(co);
						returnJson.put("code",  "201");
					}else{
						container.setNum(container.getNum()+sumnum);
						recordServiceImpl.updateContainer(container);
						returnJson.put("code",  "200");
					}
				}
			}
			out.write(returnJson.toString());
		} catch (Exception e) {
			logger.error(e);
		}
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
			User iflogin = SessionUtils.getEMP(request);
			String container_code = null!=request.getParameter("container_code")?request.getParameter("container_code").toString():null;
			String localhost_code = null!=request.getParameter("localhost_code")?request.getParameter("localhost_code").toString():null;
			out = res.getWriter();
			String main_body = null!=request.getParameter("main_body")?request.getParameter("main_body"):"";
			Container ifcontainer = new Container();
			ifcontainer.setContainer_code(container_code);
			List<Container> containercode = recordServiceImpl.findByContainerCode(ifcontainer);
			if(!main_body.equals("")){
				if(null!=containercode && containercode.size()>=1){
					String bat_id=containercode.get(0).getBat_id();
					JSONArray myJsonObject = JSONArray.fromObject(main_body);
					int sumnum = 0;
					List<UpperRecord> list = new ArrayList<>();
					for (int i = 0; i < myJsonObject.size(); i++) {
						JSONObject job = myJsonObject.getJSONObject(i);
						String sku = job.getString("sku");
						int num = job.getInt("num");
						for (int j = 0; j < num; j++) {
							UpperRecord upperLower = new UpperRecord();
							upperLower.setCreate_user(iflogin.getUsername());
							upperLower.setCreate_time(new Date());
							upperLower.setContainer_code(container_code);
							upperLower.setLocation(localhost_code);
							upperLower.setNum(1);
							upperLower.setSku(sku);
							upperLower.setBat_id(bat_id);
							sumnum=sumnum-1;
							list.add(upperLower);
						}
					}
					recordServiceImpl.insertUppers(list);
				}
			}
			
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
				log.setCreate_user(iflogin.getUsername());
				log.setSku(null!=rMap.get("sku")?rMap.get("sku").toString():"");
				log.setNum(null!=rMap.get("cut")?rMap.get("cut").toString():"0");
				log.setLocalhost_code(localhost_code);
				if(!log.getNum().equals("0")){
					recordServiceImpl.insertlog(log);
				}
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

				int id = containercode.get(0).getId();
				List<Map<String, Object>> cy_lower = recordServiceImpl.query_lower_cy(Integer.valueOf(id));
				List<Map<String, Object>> cy_upper = recordServiceImpl.query_upper_cy(Integer.valueOf(id));
				JSONArray returnList = new JSONArray();
				
				for (int i = 0; i < cy_lower.size(); i++) {
					JSONObject returnMap = new JSONObject();
					String sku = null!=cy_lower.get(i).get("sku")?cy_lower.get(i).get("sku").toString():"";
					String cut = null!=cy_lower.get(i).get("cut")?cy_lower.get(i).get("cut").toString():"";
					returnMap.put("sku", sku);
					returnMap.put("num", cut);
					for (int j = 0; j < cy_upper.size(); j++) {
						String sku_b = null!=cy_upper.get(j).get("sku")?cy_upper.get(j).get("sku").toString():"";
						String cut_b = null!=cy_upper.get(j).get("cut")?cy_upper.get(j).get("cut").toString():"";
						if(!sku.equals("")){
							if(sku.equals(sku_b)){
								int a = Integer.valueOf(cut);
								int b = Integer.valueOf(cut_b);
								int c = a-b;
								returnMap.put("num", c);
							}
						}
					}
					returnList.add(returnMap);
				}
				
				redirectAttributes.addFlashAttribute("container_number", containercode.get(0).getNum());
				redirectAttributes.addFlashAttribute("container_code", container_code);
				redirectAttributes.addFlashAttribute("localhost_code", localhost_code);
				redirectAttributes.addFlashAttribute("opertion_type", opertion_type);
				redirectAttributes.addFlashAttribute("upper_number", lower_number);
				redirectAttributes.addFlashAttribute("main_body", returnList);
				redirectAttributes.addFlashAttribute("return_page", "upper_3");
				return "redirect:/control/mainController/return_page.do";
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return "/main";
	}
}
