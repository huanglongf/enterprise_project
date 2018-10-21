package com.bt.lmis.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.alibaba.fastjson.JSONObject;
import com.bt.base.redis.RedisUtils;
import com.bt.common.util.MailUtil;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.model.Employee;
import com.bt.lmis.service.EmployeeService;
import com.bt.utils.CommonUtils;
import com.bt.utils.MD5Util;
import com.bt.utils.SessionUtils;

import net.sf.json.JSONArray;

/** 
* @ClassName: LoginController 
* @Description: TODO(登录控制) 
* @author Yuriy.Jiang
* @date 2016年5月23日 上午10:49:46 
*  
*/
@Controller
@RequestMapping("/loginController")
public class LoginController extends BaseController{

	private static final Logger logger = Logger.getLogger(LoginController.class);

	/**
	 *登录服务类
	 */
	@Resource(name = "employeeServiceImpl")
	private EmployeeService<Employee> employeeServiceImpl;

	/**
	 * 显示登录
	 * @return
	 */
	@RequestMapping("/toLogin")
	public String toLogin() {
		return "/lmis_login";
		
	}
	
	/**
	 * 退出
	 */
	@RequestMapping("/outLogin")
	public String outLogin(HttpServletRequest request){
		request.getSession().removeAttribute("session_employee");
		return "/lmis_login";
		
	}
	
	/**
	 * 登录
	 * @param user
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String login(Employee employee, ModelMap map, HttpServletRequest request,RedirectAttributes attributes) {
		Employee iflogin = SessionUtils.getEMP(request);
		if(CommonUtils.checkExistOrNot(iflogin)){
			if(!iflogin.getId().equals(employee.getId())) {
				request.getSession().removeAttribute("session_employee");
			}
		}
		if(!CommonUtils.checkExistOrNot(employee.getUsername())||!CommonUtils.checkExistOrNot(employee.getPassword())){
			attributes.addFlashAttribute("message", "用户名或密码不能为空"); 
			return "redirect:/loginController/lmis_login.do";
		}
		String password=employee.getPassword();
		map.addAttribute("username",employee.getUsername());
		map.addAttribute("password",MD5Util.md5(employee.getPassword()));
		employee.setPassword(MD5Util.md5(employee.getPassword()));
		employee.setUsername(employee.getUsername());
		employee = employeeServiceImpl.loginCheck(employee);
		//Integer ownGroupCount=employeeServiceImpl.getOwnGroupCount(employee);
	/*	if(ownGroupCount>0) {
			employee.setOwnGroupCount(ownGroupCount);
		}*/
		if (null==employee) {
			attributes.addFlashAttribute("message", "用户不存在!"); 
			return "redirect:/loginController/lmis_login.do";
		}
		if(!MD5Util.md5(password).equals(employee.getPassword())){
			attributes.addFlashAttribute("message", "登陆密码错误!"); 
			return "redirect:/loginController/lmis_login.do";					
		}
		if(null==employee.getStatus()||employee.getStatus().equals("0")){
			attributes.addFlashAttribute("message", "用户已停用!"); 
			return "redirect:/loginController/lmis_login.do";			
		}
		
		
		try {
			request.getSession().setAttribute("session_employee", employee);
			request.getSession().setAttribute("session_power", employeeServiceImpl.findEMR(employee.getId()));
			request.getSession().setAttribute("ipAddr", getIpAddr(request));
			//是否临时密码账户
			if(employee.getTmpStatus().equals("1")){
				return "/reset";
			}
			JSONArray jsonObject =JSONArray.fromObject(getTree(employee.getId(),0)); 
			Map<String,String> menu_role=new HashMap<String,String>();
			List<Map<String, String>>menu= employeeServiceImpl.findMenu(employee.getId());
			for(int k=0;k<menu.size();k++){
				Map<String,String> mens=menu.get(k);
				menu_role.put(mens.get("key"), mens.get("value"));
			}
			request.getSession().setAttribute("menu_role", menu_role);
			attributes.addFlashAttribute("menuTree", jsonObject); 
			//更新登陆人的token标记
			Map<String,String>loginMap=new HashMap<String,String>();
			String token=UUID.randomUUID().toString();
			loginMap.put("token", token);
			loginMap.put("emp_id", String.valueOf(employee.getId()));
			Map<String,String>task_mark=employeeServiceImpl.get_token_mark(loginMap);
			RedisUtils.set( String.valueOf(employee.getId()).concat("_toke_mark"), token);
			request.getSession().setAttribute("token_mark", token);
			request.getSession().setAttribute("task_mark", task_mark.get("token"));
			return "redirect:/loginController/index.do";
			
		} catch (Exception e) {
			logger.error(e);
			attributes.addFlashAttribute("message", "登录异常!"); 
			return "redirect:/loginController/lmis_login.do";
			
		}
		
	}
	
	/** 
	* @Title: index 
	* @Description: TODO(跳转首页) 
	* @param @param employee
	* @param @param map
	* @param @param request
	* @param @param attributes
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping("/index")
	public String index(Employee employee, ModelMap map, HttpServletRequest request,RedirectAttributes attributes) {
		if(null!=RequestContextUtils.getInputFlashMap(request)){
			JSONArray menuTree = (JSONArray)RequestContextUtils.getInputFlashMap(request).get("menuTree");
			map.addAttribute("menuTree",menuTree);
		}else{
			Employee session_employee = SessionUtils.getEMP(request);
			
			if(null!=session_employee){
				JSONArray jsonObject =JSONArray.fromObject(getTree(session_employee.getId(),0)); 
				map.addAttribute("menuTree",jsonObject);
				Map<String,String> menu_role=new HashMap<String,String>();
				List<Map<String, String>>menu= employeeServiceImpl.findMenu(session_employee.getId());
				for(int k=0;k<menu.size();k++){
					Map<String,String> mens=menu.get(k);
					menu_role.put(mens.get("key"), mens.get("value"));
				}
				request.getSession().setAttribute("menu_role", menu_role);
			}else{
				attributes.addFlashAttribute("message", "用户不存在或停用!"); 
				return "redirect:/loginController/lmis_login.do";
			}
			
		}
		return "/lmis/center";
	}

	/** 
	* @Title: lmis_login 
	* @Description: TODO(登录) 
	* @param @param employee
	* @param @param map
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping("/lmis_login")
	public String lmis_login(Employee employee, ModelMap map, HttpServletRequest request) {
		if(null!=RequestContextUtils.getInputFlashMap(request)){
			map.addAttribute("message", RequestContextUtils.getInputFlashMap(request).get("message"));
		}
		return "/lmis_login";
	}
	
	@RequestMapping("/toProfile")
	public String toProfile(HttpServletRequest request) {
		Employee employee = SessionUtils.getEMP(request);
		request.setAttribute("user", employee);
		return "/lmis/profile";
	}
	
	/** 
	* @Title: getTree 
	* @Description: TODO(通过用户ID和父节点ID获取树) 
	* @param @param id
	* @param @param pid
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	private List<Map<String, Object>> getTree(int id,int pid){
		List<Map<String, Object>> tree = employeeServiceImpl.getMenuTree(id,pid);
		for (int i = 0; i < tree.size(); i++) {
			List<Map<String, Object>> tree1 = employeeServiceImpl.getMenuTree(id,(Integer)tree.get(i).get("id"));
			if(tree.size()!=0){
				tree.get(i).put("tree1", tree1);
			}
			for (int j = 0; j < tree1.size(); j++) {
				List<Map<String, Object>> tree2 = employeeServiceImpl.getMenuTree(id,(Integer)tree1.get(j).get("id"));
				if(tree1.size()!=0){
					tree1.get(j).put("tree2", tree2);
				}
			}
		} 
		return tree;
	}
    @ResponseBody
	@RequestMapping("/resetPass")
	public JSONObject resetPass(Employee employee, ModelMap map, HttpServletRequest request) {
    	JSONObject obj=new JSONObject();
    	try{
    		employee=SessionUtils.getEMP(request);
        	employee.setPassword(MD5Util.md5(request.getParameter("oldPass").toString()));
    		employee.setUsername(employee.getUsername());
    		employee = employeeServiceImpl.loginCheck(employee);
    		if (null==employee||null==employee.getStatus()||employee.getStatus().equals("0")) {
                 obj.put("code", 0);
                 return obj;
    		}
    		employee.setPassword(MD5Util.md5(request.getParameter("newPassword").toString()));
    		employee.setUpdatetime(new Date());
    		employeeServiceImpl.update(employee);
    		obj.put("code", 1);
    	}catch(Exception e){
    		 obj.put("code", 0);
    	}
		return obj;
	}
    
    @ResponseBody
    @RequestMapping("/resetPassword")
    public JSONObject resetPassword(Employee employee, ModelMap map, HttpServletRequest request) {
    	JSONObject obj=new JSONObject();
    	try{
    		employee=SessionUtils.getEMP(request);
    		employee.setPassword(MD5Util.md5(request.getParameter("newPassword").toString()));
    		employee.setTmpStatus("0");
    		int i = employeeServiceImpl.updatePW(employee);
    		if(i<1){
    			obj.put("code", 0);
    		}else{
    			obj.put("code", 1);
    		}
    		
    	}catch(Exception e){
    		obj.put("code", 0);
    	}
    	return obj;
    }
	
    @ResponseBody
	@RequestMapping("/forgetPassword")
	public JSONObject forgetPassword(HttpServletRequest request) {
    	JSONObject result = new JSONObject();
    	try{
    		String email = request.getParameter("email");
    		//根据邮箱查询用户
    		Employee emp = employeeServiceImpl.getEmpByEmail(email);
    		if(CommonUtils.checkExistOrNot(emp)){
    			String code = "";
    			Random rand = new Random();// 生成随机数
    			for (int a = 0; a < 6; a++) {
    				code += rand.nextInt(10);// 生成6位密码
    			}
    			String tmpPW = MD5Util.md5(code);
    			emp.setPassword(tmpPW);
    			emp.setTmpStatus("1");
    			//更新用户状态
    			int i = employeeServiceImpl.updatePW(emp);
    			if(i<1){
    				result.put("flag", false);
            		result.put("msg", "更新临时密码失败！");
    			}else{
    				//发邮件
    				MailUtil.sendMail(email,code);
    				result.put("flag", true);
    	    		result.put("msg", "临时密码已发送到邮箱，请注意查收");
    			}
    		}else{
    			result.put("flag", false);
        		result.put("msg", "该邮箱未注册或者账户已停用！");
        		return result;
    		}
    	}catch(Exception e){
    		result.put("flag", false);
    		result.put("msg", "出现异常e:"+e);
    		e.printStackTrace();
    	}
		return result;
	}
	
	public static void main(String[]args){
		System.out.println(MD5Util.md5("123456"));//D301282FD5103B13F2CA10A717768E23
	}
	
}
