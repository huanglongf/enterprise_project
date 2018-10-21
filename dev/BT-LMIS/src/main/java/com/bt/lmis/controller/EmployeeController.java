package com.bt.lmis.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bt.common.util.MailUtil;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.EmployeeInGroup;
import com.bt.lmis.model.Role;
import com.bt.lmis.service.DictService;
import com.bt.lmis.service.EmployeeInGroupService;
import com.bt.lmis.service.EmployeeService;
import com.bt.lmis.service.RoleService;
import com.bt.utils.CommonUtils;
import com.bt.utils.DateUtil;
import com.bt.utils.MD5Util;
import com.bt.utils.SessionUtils;
import com.bt.workOrder.model.Group;
import com.bt.workOrder.service.GroupService;

import net.sf.json.JSONArray;

/** 
* @ClassName: EmployeeController 
* @Description: TODO(用户管理控制器) 
* @author Yuriy.Jiang
* @date 2016年5月27日 下午5:42:45 
*  
*/
@Controller
@RequestMapping("/control/employeeController")
public class EmployeeController extends BaseController{

	private static final Logger logger = Logger.getLogger(EmployeeController.class);
	
	@Resource(name="employeeInGroupServiceImpl")
	private EmployeeInGroupService<EmployeeInGroup> employeeInGroupServiceImpl;
	
	@Resource(name="groupServiceImpl")
	private GroupService<Group> groupServiceImpl;
	
	@Resource(name="employeeServiceImpl")
	private EmployeeService<Employee> employeeServiceImpl;

	@Resource(name="roleServiceImpl")
	private RoleService<Role> roleServiceImpl;
	
	@Resource(name="dictServiceImpl")
	private DictService dictService;
	
	@RequestMapping("/employeeList")
	public String getEmployeeList(ModelMap map, HttpServletRequest request){
		int toid = 0;
		int ret = 0;
		Employee employee=new Employee();
		if(null!=request.getParameter("name") && !request.getParameter("name").equals("")){
			employee.setName(request.getParameter("name"));
		}
		if(null!=request.getParameter("email") && !request.getParameter("email").equals("")){
			employee.setEmail(request.getParameter("email"));
		}
		if(null!=request.getParameter("iphone") && !request.getParameter("iphone").equals("")){
			employee.setIphone(request.getParameter("iphone"));
		}
		if(null!=request.getParameter("username") && !request.getParameter("username").equals("")){
			employee.setUsername(request.getParameter("username"));
		}
		map.addAttribute("employee", JSONArray.fromObject(employeeServiceImpl.getEmployeeList(employee)));
		map.addAttribute("employeeid", toid);
		map.addAttribute("ret", ret);
		map.addAttribute("findData", employee);
		return "/lmis/employee_list";
		
	}
	
	/**
	 * 显示表单
	 * @return
	 */
	@RequestMapping("/toForm")
	public String toForm(ModelMap map, HttpServletRequest request){
		if(CommonUtils.checkExistOrNot(request.getParameter("id"))){
			Map<String,Object> employee= employeeServiceImpl.findEmpById(Integer.valueOf(request.getParameter("id")));
			map.addAttribute("employee",employee);
			List<Map<String, Object>> erMap = employeeServiceImpl.selectER(Integer.valueOf(request.getParameter("id")));
			if(erMap.size() !=0 )map.addAttribute("erMap",erMap.get(0));
			map.addAttribute("addedGroups", employeeInGroupServiceImpl.findGroups(Integer.valueOf(request.getParameter("id"))));
			
		}
		try {
			map.addAttribute("groups", groupServiceImpl.findAllGroups());
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		map.addAttribute("employee_project_id_list", dictService.findByType("s_employee_project_id"));
		map.addAttribute("menu", roleServiceImpl.findByList());
		return "/lmis/employee_form";
		
	}
	
	/** 
	* @Title: add 
	* @Description: TODO(添加用户) 
	* @param @param employee
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@ResponseBody
	@RequestMapping("/add")
	public JSONObject add(Employee employee,HttpServletRequest req,HttpServletResponse res){
		JSONObject obj=new JSONObject();
		try {
			Employee user=SessionUtils.getEMP(req);
			employee.setCreateby(user.getUsername());
			employee.setPassword(MD5Util.md5(employee.getPassword()));
			if(employeeServiceImpl.checkEmployeeNumber(employee) > 0){
				obj.put("msg","工号已存在，请重新输入！");
			} else if(employeeServiceImpl.checkUser(employee).longValue() >= 1L) {
				obj.put("msg","登录名已存在，请重新输入！");
			} else if(employeeServiceImpl.getCountByEmail(employee.getEmail())>0){
				obj.put("msg","邮箱已存在，请重新输入！");
			}else{
				employee.setToken("1");
				employeeServiceImpl.save(employee);
				employeeServiceImpl.insertER(employee.getId(), Integer.valueOf(req.getParameter("roleid")));
				obj.put("msg","操作成功!");
			}
		} catch(Exception e){
			obj.put("msg","异常："+e);
			logger.error(e);
		}
		return obj;
	}
	@ResponseBody
	@RequestMapping("/saveEmp")
	public JSONObject saveEmp(Employee employee,HttpServletRequest req,HttpServletResponse res){
		JSONObject obj=new JSONObject();
		try {
			Employee user=SessionUtils.getEMP(req);
			String oldName = user.getName();
			String oldEmail = user.getEmail();
			String oldFlag = user.getIsWoEmail();
			user.setName(employee.getName());
			user.setEmail(employee.getEmail());
			user.setIsWoEmail(employee.getIsWoEmail());
			int num1 = employeeServiceImpl.getByEmailNoId(employee.getEmail(),user.getId());
			if(num1>0){
				obj.put("code", "FAILURE");
				obj.put("msg", "邮箱已存在，请重新输入！");
				return obj;
			}
			int num = employeeServiceImpl.updateProfile(user);
			if(num>0){
				obj.put("code", 200);
				obj.put("msg", "修改成功");
				String date = DateUtil.format(new Date());
				StringBuffer content = new StringBuffer("登录名为【"+employee.getUsername()+"】的用户在"+date+"修改了用户资料：</br>");
				if(!oldName.equals(employee.getName())){
					content.append("-   将昵称【"+oldName+"】修改为【"+employee.getName()+"】。</br>");
				}
				if(!oldEmail.equals(employee.getEmail())){
					content.append("-   将邮箱【"+oldEmail+"】修改为【"+employee.getEmail()+"】。</br>");
				}
				if(!oldFlag.equals(employee.getIsWoEmail())){
					if(employee.getIsWoEmail().equals("1")){
						content.append("-   将邮箱提醒开关打开。</br>");
					}
					if(employee.getIsWoEmail().equals("0")){
						content.append("-   将邮箱提醒开关关闭。</br>");
					}
				}
				MailUtil.sendWorkOrderMail(employee.getEmail(), "用户资料修改提醒", content.toString());
				return obj;
			}else{
				obj.put("code", 400);
				obj.put("msg", "更新失败");
				return obj;
			}
		} catch(Exception e){
			obj.put("code", 500);
			obj.put("msg","异常："+e);
			e.printStackTrace();
			return obj;
		}
	}
	
	/** 
	* @Title: update 
	* @Description: TODO(修改用户) 
	* @param @param employee
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/update")
	public void update(Employee employee,ModelMap map,HttpServletRequest req,HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			out = res.getWriter();
			result = new JSONObject();
			Employee temp = employeeServiceImpl.selectByEmployeeNumber(employee.getEmployee_number());
			boolean flag = true;
			if(CommonUtils.checkExistOrNot(temp) && !temp.getId().equals(employee.getId())){
				// 工号重复
				result.put("result_code", "FAILURE");
				result.put("result_content", "工号已存在，请重新输入！");
				flag = false;
				
			}
			temp = employeeServiceImpl.selectByUserName(employee.getUsername());
			if(flag && CommonUtils.checkExistOrNot(temp) && !temp.getId().equals(employee.getId())){
				// 用户名账号重复
				result.put("result_code", "FAILURE");
				result.put("result_content", "用户名已存在，请重新输入！");
				flag = false;
			}
			String email = employee.getEmail();
			int num = employeeServiceImpl.getByEmailNoId(email,employee.getId());
			if(num>1){
				result.put("result_code", "FAILURE");
				result.put("result_content", "邮箱已存在，请重新输入！");
				flag = false;
			}
			if(flag){
				employee.setUpdateby(SessionUtils.getEMP(req).getEmployee_number());
//				employeeInGroupServiceImpl.addRelations(req, employee.getId());
				String roleId = req.getParameter("roleid");
				if(CommonUtils.checkExistOrNot(roleId)){
					employeeServiceImpl.updateER(employee, employee.getId(),  Integer.valueOf(roleId));
				}else{
					employeeServiceImpl.updateER(employee, employee.getId(), 0);
				}
				result.put("result_code", "SUCCESS");
				result.put("result_content", "更新用户成功！");
				
			}
			
		} catch(Exception e){
			result.put("result_code", "FAILURE");
			result.put("result_content","操作异常!"+e);
			out.write(result.toString());
		}
		out.write(result.toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 删除用户
	 */
	@RequestMapping("/del")
	public void delete(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Integer[]ids=StringtoInt(req.getParameter("privIds"));
			employeeServiceImpl.batchDelete(ids);	
			out.write("true");
		} catch (Exception e) {
			logger.error(e);
			out.write("false");
		}

	}
	
	/** 
	* @Title: upStatus 
	* @Description: TODO(修改用户状态) 
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/upStatus")
	public void upStatus(HttpServletRequest req,HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			employeeServiceImpl.upStatus(Integer.valueOf(req.getParameter("id")), Integer.valueOf(req.getParameter("status")));;
		} catch(Exception e){
			logger.error(e);
		}
		out.flush();
		out.close();
	}
	
}
