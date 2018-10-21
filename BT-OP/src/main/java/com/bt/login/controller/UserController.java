package com.bt.login.controller;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.base.BaseConstant;
import com.bt.login.controller.form.UserQueryParam;
import com.bt.login.model.User;
import com.bt.login.service.UserService;
import com.bt.orderPlatform.model.OrganizationInformation;
import com.bt.orderPlatform.page.PageView;
import com.bt.orderPlatform.page.QueryResult;
import com.bt.orderPlatform.service.OrganizationInformationService;
import com.bt.sys.model.Account;
import com.bt.sys.util.SysUtil;

import net.sf.json.JSONObject;
/**
 * 用户表控制器
 *
 */
@Controller
@RequestMapping("/control/login/userController")
public class UserController{

	private static final Logger logger = Logger.getLogger(UserController.class);
	
	
	/**
	 * 用户表服务类
	 */
	@Resource(name = "userServiceImpl")
	private UserService<User> userService;
	@Resource(name = "organizationInformationServiceImpl")
	private OrganizationInformationService<OrganizationInformation> organizationInformationServiceImpl;
	
	@RequestMapping("/user")
	public String user(UserQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				queryParam.getPageSize() == 0 ? BaseConstant.pageSize : queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<Map<String, Object>> qr = userService.findUser(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage());
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		return "login/user";
	}
	@RequestMapping("/adduser")
	public String adduser(UserQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		Account temp = SysUtil.getAccountInSession(request);
		List<OrganizationInformation> orgs = organizationInformationServiceImpl.queryAllBypid(temp.getOrgid());
		request.setAttribute("org", orgs);
		return "login/adduser";
	}
	
	@RequestMapping("/insertUser")
	public JSONObject insertUser(UserQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		JSONObject obj = new JSONObject();
		try {
		queryParam.setCreate_time(new Date());
		queryParam.setCreate_by(null);
		queryParam.setId(UUID.randomUUID().toString());
		queryParam.setIs_deleted(0);
		queryParam.setIs_disabled(0);
		queryParam.setVersion(1);
		userService.insertUser(queryParam);
		} catch (Exception e) {
			obj.put("data", 1);
			return obj;
		}
		obj.put("data", 0);
		return obj;
	}
	@RequestMapping("/updateuser")
	public JSONObject updateuser(UserQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		JSONObject obj = new JSONObject();
		try {
			queryParam.setCreate_time(new Date());
			queryParam.setCreate_by(null);
			queryParam.setIs_deleted(0);
			queryParam.setIs_disabled(0);
			queryParam.setVersion(1);
			userService.updateuser(queryParam);
		} catch (Exception e) {
			obj.put("data", 1);
			return obj;
		}
		obj.put("data", 0);
		return obj;
	}
	
	
	@RequestMapping("/updateUser")
	public String updateUser(UserQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		User user =userService.selectById(queryParam.getId());
		request.setAttribute("queryParam", user);
		Account temp = SysUtil.getAccountInSession(request);
		List<OrganizationInformation> orgs = organizationInformationServiceImpl.queryAllBypid(temp.getOrgid());
		request.setAttribute("org", orgs);
		return "login/updateuser";
	}
	@RequestMapping("/deleteUser")
	public String deleteUser(UserQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		
		return "login/updateuser";
	}
	@RequestMapping("/updateUserpassword")
	public String updateUserpassword(UserQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		String ids = request.getParameter("ids");
		String[] split = ids.split(";");
		User user =userService.selectById(split[0]);
		request.setAttribute("queryParam", user);
		return "login/updatepassword";
	}
	@RequestMapping("/stopUser")
	public String stopUser(UserQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		String ids = request.getParameter("ids");
		String[] split = ids.split(";");
		User user =userService.selectById(split[0]);
		request.setAttribute("queryParam", user);
		return "login/updateuser";
	}
	@RequestMapping("/setUserRole")
	public String setUserRole(UserQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		String ids = request.getParameter("ids");
		String[] split = ids.split(";");
		User user =userService.selectById(split[0]);
		request.setAttribute("queryParam", user);
		return "login/setuserrole";
	}
	@RequestMapping("/setUserPower")
	public String setUserPower(UserQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		String ids = request.getParameter("ids");
		String[] split = ids.split(";");
		User user =userService.selectById(split[0]);
		request.setAttribute("queryParam", user);
		return "login/setpower";
	}
	@RequestMapping("/openUser")
	public String openUser(UserQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		
		return "login/updateuser";
	}
	@RequestMapping("/exportUser")
	public String exportUser(UserQueryParam queryParam, HttpServletRequest request,HttpServletResponse response) {
		
		return "login/updateuser";
	}
	
}
