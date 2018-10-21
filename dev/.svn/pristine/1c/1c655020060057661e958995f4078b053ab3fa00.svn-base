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
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.Menu;
import com.bt.lmis.service.EmployeeService;
import com.bt.lmis.service.MenuService;

import net.sf.json.JSONArray;

/** 
* @ClassName: MenuController 
* @Description: TODO(菜单控制器) 
* @author Yuriy.Jiang
* @date 2016年5月23日 下午4:49:02 
*  
*/
@Controller
@RequestMapping("/control/menuController")
public class MenuController  extends BaseController{

	private static final Logger logger = Logger.getLogger(MenuController.class);

	/**
	 * 用户服务类
	 */
	@Resource(name = "employeeServiceImpl")
	private EmployeeService<Employee> employeeServiceImpl;
	
	/**
	 * 菜单服务类
	 */
	@Resource(name = "menuServiceImpl")
	private MenuService<Menu> menuServiceImpl;
	
	
	/** 
	* @Title: toList 
	* @Description: TODO(显示菜单列表) 
	* @param @param map
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping("/toList")
	public String toList(ModelMap map, HttpServletRequest request) {
		int toid = 0;
		int ret = 0;
		Menu menu = new Menu();
		if(null!=request.getParameter("toid")){
			toid=Integer.valueOf(request.getParameter("toid"));
		}
		if(null!=request.getParameter("ret")){
			ret=Integer.valueOf(request.getParameter("ret"));
		}
		//当前登录的用户节点为0的菜单
		menu.setDisplaypid(toid);
		if(null!=request.getParameter("name") && !request.getParameter("name").equals("")){
			menu.setName(request.getParameter("name"));
		}
		if(null!=request.getParameter("url") && !request.getParameter("url").equals("")){
			menu.setUrl(request.getParameter("url"));
		}
		if(null!=request.getParameter("icon") && !request.getParameter("icon").equals("")){
			menu.setIcon(request.getParameter("icon"));
		}
		if(null!=request.getParameter("remarks") && !request.getParameter("remarks").equals("")){
			menu.setRemarks(request.getParameter("remarks"));
		}
		JSONArray jsonObject =JSONArray.fromObject(menuServiceImpl.getMenuTree(menu)); 
		map.addAttribute("menu",jsonObject);
		map.addAttribute("menuid",toid);
		map.addAttribute("ret",ret);
		map.addAttribute("findMenu",menu);
		return "/lmis/menu_list";
	}


	/** 
	* @Title: toRet 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param map
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping("/toRet")
	public String toRet(ModelMap map, HttpServletRequest request) {
		int toid = 0;
		int ret = 0;
		Menu menu = new Menu();
		if(null!=request.getParameter("toid")){
			toid=Integer.valueOf(request.getParameter("toid"));
		}
		if(null!=request.getParameter("ret")){
			ret=Integer.valueOf(request.getParameter("ret"));
		}
		menu.setDisplaypid(ret);
		JSONArray jsonObject =JSONArray.fromObject(menuServiceImpl.getMenuTree(menu)); 
		Map<String,Object> maps = employeeServiceImpl.findMenuById(toid);
		if(0==(Integer)maps.get("pid")){
			menu.setDisplaypid(0);
			jsonObject =JSONArray.fromObject(menuServiceImpl.getMenuTree(menu)); 
			map.addAttribute("menu",jsonObject);
			map.addAttribute("menuid",0);
			map.addAttribute("ret",0);
		}else{
			maps = employeeServiceImpl.findMenuById((Integer)maps.get("pid"));
			map.addAttribute("menu",jsonObject);
			map.addAttribute("menuid",maps.get("id"));
			map.addAttribute("ret",maps.get("pid"));
		}
		return "/lmis/menu_list";
	}
	
	/**
	 * 显示表单
	 * @return
	 */
	@RequestMapping("/toForm")
	public String toForm(ModelMap map, HttpServletRequest request){
		int menuid = Integer.valueOf(request.getParameter("menuid"));
		map.addAttribute("menuid",menuid);
		if(null!=request.getParameter("id")&&!request.getParameter("id").equals("")){
			Map<String,Object> menu = employeeServiceImpl.findMenuById(Integer.valueOf(request.getParameter("id")));
			map.addAttribute("menu",menu);
		}
		return "/lmis/menu_form";
	}

	/** 
	* @Title: add 
	* @Description: TODO(添加菜单) 
	* @param @param menu
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/add")
	public void add(Menu menu,HttpServletRequest req,HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			menu.setFlag("0");
			menuServiceImpl.save(menu);
			out.write("操作成功!");	
		} catch(Exception e){
			logger.error(e);
		}
		out.flush();
		out.close();
	}
	

	/** 
	* @Title: update 
	* @Description: TODO(修改菜单) 
	* @param @param menu
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/update")
	public void update(Menu menu,ModelMap map,HttpServletRequest req,HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			menuServiceImpl.update(menu);
			out.write("操作成功!");
		} catch(Exception e){
			logger.error(e);
		}
		out.flush();
		out.close();
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
			for (int i = 0; i < ids.length; i++) {
				if(null!=ids[i]){
					Menu menu = new Menu();
					menu.setDisplaypid(ids[i]);
					List<Map<String, Object>> listByPid = menuServiceImpl.getMenuTree(menu);
					if(listByPid.size()!=0){
						flag=false;
					}
				}
			}
			if(flag){
				menuServiceImpl.batchDelete(ids);
				out.write("true");
			}else{
				out.write("false");
			}
		} catch(Exception e){
			e.printStackTrace();
			logger.error(e);
		}
		out.flush();
		out.close();
	}

	/** 
	* @Title: StringtoInt 
	* @Description: TODO(String[]转Integer[]) 
	* @param @param str
	* @param @return    设定文件 
	* @return Integer[]    返回类型 
	* @throws 
	*/
//	private Integer[] StringtoInt(String str) {  
//		Integer ret[] = new Integer[str.length()];   
//		StringTokenizer toKenizer = new StringTokenizer(str, ",");   
//	    int i = 0;  
//	    while (toKenizer.hasMoreElements()) {   
//	    	ret[i++] = Integer.valueOf(toKenizer.nextToken());  
//	    }   
//    	return ret;  
//	 }
	
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
			menuServiceImpl.upStatus(Integer.valueOf(req.getParameter("id")), Integer.valueOf(req.getParameter("status")));;
		} catch(Exception e){
			logger.error(e);
		}
		out.flush();
		out.close();
	}
}
