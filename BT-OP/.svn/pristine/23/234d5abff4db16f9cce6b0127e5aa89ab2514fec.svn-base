package com.bt.sys.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bt.common.util.CommonUtil;
import com.bt.sys.model.Account;
import com.bt.sys.service.LoginService;
import com.bt.sys.util.SysUtil;

/**
 * @ClassName: LoginController
 * @Description: TODO(系统登录控制器)
 * @author Ian.Huang
 * @date 2017年4月27日 下午4:45:42
 * 
 */
@Controller
@RequestMapping("/loginController")
public class LoginController {

	private static final Logger logger = Logger.getLogger(LoginController.class);

	/**
	 * @Fields service : TODO(登录服务)
	 */
	@Resource(name = "loginServiceImpl")
	private LoginService<Account> service;

	/**
	 * @Title: toLogin
	 * @Description: TODO(跳转登录页面)
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午5:10:11
	 */
	@RequestMapping("/toLogin")
	public String toLogin() {
		return "/login";

	}

	/**
	 * @Title: rollback
	 * @Description: TODO(打回登录页面)
	 * @param request
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午9:43:21
	 */
	@RequestMapping("/rollback")
	public String rollback(HttpServletRequest request) {
		Map<String, ?> param = RequestContextUtils.getInputFlashMap(request);
		if (!CommonUtil.isNull(param)) {
			request.setAttribute("message", param.get("message"));

		}
		return "/login";

	}

	/**
	 * @Title: login
	 * @Description: TODO(登录)
	 * @param employee
	 * @param map
	 * @param request
	 * @param attributes
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午5:17:49
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, Account account, RedirectAttributes attributes) {
		return service.login(request, account, attributes);

	}

	/**
	 * @Title: main
	 * @Description: TODO(跳转首页)
	 * @param request
	 * @param attributes
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午10:56:37
	 */
	@RequestMapping("/main")
	public String main(HttpServletRequest request, RedirectAttributes attributes) {
		if (CommonUtil.isNull(RequestContextUtils.getInputFlashMap(request))) {
			// 无重定向传参
			Account temp = SysUtil.getAccountInSession(request);
			if (CommonUtil.isNull(temp)) {
				attributes.addFlashAttribute("message", "用户未登录");
				return "redirect:/loginController/rollback.do";

			}
			request.setAttribute("account", temp);

		}
		// 存在重定向传参
		
		return "/common/main";

	}

	/**
	 * @Title: outLogin
	 * @Description: TODO(登出)
	 * @param request
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年4月27日 下午5:12:41
	 */
	@RequestMapping("/logout")
	public String outLogin(HttpServletRequest request) {
		// 清空会话
		SysUtil.removeAccountInSession(request);
		// 跳转登录页面
		return "/login";

	}

}
