package com.bt.lmis.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.lmis.model.Menu;
import com.bt.lmis.model.Role;
import com.bt.lmis.service.MenuService;
import com.bt.lmis.service.RoleService;

import net.sf.json.JSONArray;

/** 
* @ClassName: RoleController 
* @Description: TODO(角色控制器) 
* @author Yuriy.Jiang
* @date 2016年5月27日 下午5:44:21 
*  
*/
@Controller
@RequestMapping("/control/roleController")
public class RoleController  extends BaseController{

	private static final Logger logger = Logger.getLogger(RoleController.class);

	/**
	 * 菜单服务类
	 */
	@Resource(name = "roleServiceImpl")
	private RoleService<Role> roleServiceImpl;
	@Resource(name = "menuServiceImpl")
	private MenuService<Menu> menuServiceImpl;
	
	
	/** 
	* @Title: list 
	* @Description: TODO(获取角色列表) 
	* @param @param map
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping("/list")
	public String list(Role role,ModelMap map, HttpServletRequest request){
		List<Map<String, Object>> list = roleServiceImpl.findByListSelective(role);
		map.addAttribute("role",list);
		map.addAttribute("roleSelective",role);
		return "/lmis/role_list";
	}
	

	/** 
	* @Title: toForm 
	* @Description: TODO(跳转编辑页面) 
	* @param @param map
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping("/toForm")
	public String toForm(ModelMap map, HttpServletRequest request){
		//根据ID判断是新增还说修改
		if(null!=request.getParameter("id")){
			Integer id = Integer.valueOf(request.getParameter("id"));
			List<Map<String, Object>> mList = menuServiceImpl.getMenu(new Menu());
			List<Map<String, Object>> rmList = menuServiceImpl.getRoleMenuTree(id);
			for (int i = 0; i < mList.size(); i++) {
				for (int j = 0; j < rmList.size(); j++) {
					if (mList.get(i).get("id")==rmList.get(j).get("menuid")) {
						mList.get(i).put("checked", "true");
					}
				}
			}
			JSONArray menuJson =JSONArray.fromObject(mList); 
			map.addAttribute("menu",menuJson);
			Role role = new Role();
			try {
				role = roleServiceImpl.selectById(id);
				map.addAttribute("role",role);
			} catch (Exception e) {
				logger.error(e);
			}
		}else{
			JSONArray menuJson =JSONArray.fromObject(menuServiceImpl.getMenu(new Menu())); 
			map.addAttribute("menu",menuJson);
		}
		return "/lmis/role_form";
	}
	
	/** 
	* @Title: del 
	* @Description: TODO(删除菜单) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/del")
	public void del(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Integer[] ids = StringtoInt(req.getParameter("privIds"));
			boolean flag = true;
			//判断选中角色是否正在使用
			for (int i = 0; i < ids.length; i++) {
				if(null!=ids[i]){
					List<Map<String, Object>> list = roleServiceImpl.findRoleEmployeeByRoleId(ids[i]);
					if(list.size()!=0){
						flag=false;
					}
				}
			}
			if(flag){
				roleServiceImpl.batchDelete(ids);
				out.write("true");
			}else{
				out.write("false");
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.flush();
		out.close();
	}

	/** 
	* @Title: upStatus 
	* @Description: TODO(修改订单状态) 
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
			roleServiceImpl.upStatus(Integer.valueOf(req.getParameter("id")), Integer.valueOf(req.getParameter("status")));
		} catch(Exception e){
			logger.error(e);
		}
		out.flush();
		out.close();
	}
	
	/** 
	* @Title: add 
	* @Description: TODO(添加角色) 
	* @param @param menu
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/add")
	public void add(Role role,HttpServletRequest req,HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Integer[] menuids = StringtoInt(req.getParameter("menuids"));
			roleServiceImpl.insertRoleMenu(role,menuids);
			out.write("操作成功!");	
		} catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			out.write("操作异常!");
		}
		out.flush();
		out.close();
	}
	

	/** 
	* @Title: update 
	* @Description: TODO(修改角色) 
	* @param @param menu
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/update")
	public void update(Role role,ModelMap map,HttpServletRequest req,HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Integer[] menuids = StringtoInt(req.getParameter("menuids"));
			roleServiceImpl.updateRoleMenu(role,role.getId(),menuids);
			out.write("操作成功!");
		} catch(Exception e){
			logger.error(e);
			out.write("操作异常!");
		}
		out.flush();
		out.close();
	}
	
}
