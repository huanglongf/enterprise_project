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
import com.bt.lmis.model.Power;
import com.bt.lmis.service.MenuService;
import com.bt.lmis.service.PowerService;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/control/powerController")
public class PowerController extends BaseController{

	private static final Logger logger = Logger.getLogger(PowerController.class);
	
	@Resource(name="powerServiceImpl")
	private PowerService<Power>powerServiceImpl;
	@Resource(name = "menuServiceImpl")
	private MenuService<Menu> menuServiceImpl;
	
	/**
	 * 权限查询
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/powerList")
	public String getPowerList(Menu menu,ModelMap map, HttpServletRequest request) throws Exception{
		int toid = 0;
		int ret = 0;
		JSONArray jsonObject =JSONArray.fromObject(powerServiceImpl.getPowerList(menu));
		map.addAttribute("power",jsonObject);
		map.addAttribute("powerid",toid);
		map.addAttribute("ret",ret);
		map.addAttribute("findData",menu);
		return "/lmis/power_list";
	}
	
	/**
	 * 显示表单
	 * @return
	 */
	@RequestMapping("/toForm")
	public String toForm(ModelMap map, HttpServletRequest request){
		Menu menu = new Menu();
		menu.setFlag("0");
		if(null!=request.getParameter("id")&&!request.getParameter("id").equals("")){
			Integer id = Integer.valueOf(request.getParameter("id"));
			List<Map<String, Object>> mList = menuServiceImpl.getMenu(menu);
			Menu rmList;
			try {
				rmList = menuServiceImpl.selectById(id);
				for (int i = 0; i < mList.size(); i++) {
					if (mList.get(i).get("id")==Integer.valueOf(rmList.getPid())) {
						mList.get(i).put("checked", "true");
					}
				}
				JSONArray menuJson =JSONArray.fromObject(mList); 
				map.addAttribute("menu",menuJson);
				List<Map<String, Object>> power = powerServiceImpl.findPowerById(Integer.valueOf(request.getParameter("id")));
				map.addAttribute("power",power.get(0));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			JSONArray menuJson =JSONArray.fromObject(menuServiceImpl.getMenu(menu)); 
			map.addAttribute("menu",menuJson);
		}
		return "/lmis/power_form";
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
	@RequestMapping("/add")
	public void add(Menu menu,HttpServletRequest req,HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			menu.setPid(Integer.valueOf(req.getParameter("menuid")));
			menu.setFlag("1");
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
	* @Description: TODO(修改用户) 
	* @param @param employee
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
			menu.setPid(Integer.valueOf(req.getParameter("menuid")));
			menuServiceImpl.update(menu);
			out.write("操作成功!");
		} catch(Exception e){
			logger.error(e);
		}
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
			menuServiceImpl.batchDelete(ids);	
			out.write("true");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
			menuServiceImpl.upStatus(Integer.valueOf(req.getParameter("id")), Integer.valueOf(req.getParameter("status")));;
		} catch(Exception e){
			logger.error(e);
		}
		out.flush();
		out.close();
	}
	
}
