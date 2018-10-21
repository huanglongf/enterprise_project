package com.bt.vims.controller;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bt.vims.model.User;
import com.bt.vims.service.UserService;
import com.bt.vims.utils.BaseConst;
import com.bt.vims.utils.MD5Util;
import com.bt.vims.utils.SessionUtils;


@Controller
@RequestMapping("/control/userController")
public class UserController {

	private static final Logger logger = Logger.getLogger(UserController.class);

	@Resource(name = "userServiceImpl")
	private UserService userServiceImpl;
	
	@RequestMapping("/login")
	public void login(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		try {
			res.setContentType("text/xml; charset=utf-8");
			res.setCharacterEncoding("utf-8");
			JSONObject returnJson = new JSONObject();
			PrintWriter out = null;
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String addressInfor = request.getParameter("addressInfor");
			SessionUtils.setAttr(request, "addressInfor", addressInfor); //session中设置地址信息
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			User iflogin = SessionUtils.getEMP(request);
			out = res.getWriter();
			if(null!=iflogin){
				map.addAttribute("username",iflogin.getUsername());
				map.addAttribute("password",iflogin.getPassword());
				request.getSession().setAttribute("session_user", iflogin);
				request.getSession().setMaxInactiveInterval(36000);
				returnJson.put("code", "200");
				returnJson.put("message", "[登录成功!]");
				out.write(returnJson.toString());
				out.flush();
				out.close();
				return ;
			}
			if(null==user.getUsername()||user.getUsername().equals("")||null==user.getPassword()||user.getPassword().equals("")){
				returnJson.put("code", "500");
				returnJson.put("message", "[用户名或密码不能为空]");
				returnJson.put("username", user.getUsername());
				out.write(returnJson.toString());
				out.flush();
				out.close();
				return ;
			}
			map.addAttribute("username",user.getUsername());
			map.addAttribute("password",MD5Util.md5(user.getPassword()));
			user.setPassword(MD5Util.md5(user.getPassword()));
			user.setUsername(user.getUsername());
			user = userServiceImpl.login_check(user);
			if (null==user||null==user.getActive()||user.getActive().equals("0")) {
				returnJson.put("code", "500");
				returnJson.put("message", "[用户不存在或停用!]");
				out.write(returnJson.toString());
				out.flush();
				out.close();
				return ;
			}
			try {
				request.getSession().setAttribute("session_user", user);
				request.getSession().setMaxInactiveInterval(36000);
				returnJson.put("code", "200");
				returnJson.put("message", "[登录成功!]");
				out.write(returnJson.toString());
				out.flush();
				out.close();
				return ;
			} catch (Exception e) {
				logger.error(e);
				returnJson.put("code", "500");
				returnJson.put("message", "[登录异常!]");
				out.write(returnJson.toString());
				out.flush();
				out.close();
				return ;
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}
	

	@RequestMapping("/outLogin")
	public String outLogin(HttpServletRequest request){
		request.getSession().removeAttribute(BaseConst.login_session);
		return "/login";
	}
}
