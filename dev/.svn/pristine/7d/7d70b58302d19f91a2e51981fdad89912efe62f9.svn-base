package com.bt.lmis.controller;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.TransOrderQueryParam;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.TransOrderBean;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.TransOrderService;
import com.bt.utils.BaseConst;
import com.bt.utils.SessionUtils;
/**
 * 
* @ClassName: TransOrderController 
* @Description: TODO(物流合同创建) 
* @author likun
* @date 2016年6月20日 下午4:46:14 
*
 */

@Controller
@RequestMapping("/control/transOrderController")
public class TransOrderController extends BaseController{

	
	@Resource(name="transOrderServiceImpl")
	private TransOrderService<TransOrderBean> transOrderServiceImpl;
	
 /**
  * 
 * @Title: getOrder 
 * @Description: TODO(跳转到合同维护页面) 
 * @param @param map
 * @param @param request
 * @param @return
 * @param @throws Exception    设定文件 
 * @author likun   
 * @return String    返回类型 
 * @throws
  */
	@RequestMapping("/orderDetail.do")
	public String getOrder(ModelMap map, HttpServletRequest request) throws Exception{
	
		return "/lmis/trans_order_detail";
	}
	
	/**
	 * 
	* @Title: save 
	* @Description: TODO(合同主信息保存) 
	* @param @param map
	* @param @param request
	* @param @return    设定文件 
	* @author likun   
	* @return String    返回类型 
	* @throws
	 */
	
	@RequestMapping("/saveMain.do")
	public void save(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			param.putAll(spiltDateString((String)param.get("reservation")));
			Employee user=SessionUtils.getEMP(request);
			param.put("createUser", user.getCreateby());
			param.put("contractType", "4");
			param.put("validity", "0");
			param.put("id", "");
			transOrderServiceImpl.saveMainData(param);
			map.addAttribute("contractId", param.get("id"));	
			map.put("result_flag", "1");
			map.put("result_reason", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("result_flag", "0");
			map.put("result_reason", "操作失败,失败原因:"+e.getMessage());	
		}
		out.write(JSONObject.toJSON(map).toString());
		out.flush();
	}
	
	
	
	/**
	 * 
	* @Title: saveDetail 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param map
	* @param @param request
	* @param @param res    设定文件 
	* @author likun   
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping("/saveDetail.do")
	public void saveDetail(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			transOrderServiceImpl.saveDetailData(param);
			map.put("result_flag", "1");
			map.put("result_reason", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("result_flag", "0");
			map.put("result_reason", "操作失败,失败原因:"+e.getMessage());	
		}
		out.write(JSONObject.toJSON(map).toString());
		out.flush();
	}
	
	
	
	@RequestMapping("/getCarriageInfo.do")
	public void getCarriageInfo(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			HashMap carriageInfo=transOrderServiceImpl.getCarriageInfo(param);
			out.write(JSONArray.toJSON(carriageInfo).toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
	
	@RequestMapping("/getWkData.do")
	public void getWkData(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			ArrayList<?> list=transOrderServiceImpl.getWkData(param);
			out.write(JSONArray.toJSON(list).toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getSpecial.do")
	public void getSpecial(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			HashMap specialInfo=transOrderServiceImpl.getSpecial(param);
			out.write(JSONArray.toJSON(specialInfo).toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 
	* @Title: getformula 
	* @Description: TODO(获取运费公式数据) 
	* @param @param map
	* @param @param request
	* @param @param res    设定文件 
	* @author likun   
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping("/getFormula_tab.do")
	public void getformula(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			ArrayList<?> list=transOrderServiceImpl.getFormulaTd(param);
			out.write(JSONArray.toJSON(list).toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	* @Title: saveDiscount 
	* @Description: TODO(保存区间折扣) 
	* @param @param map
	* @param @param request
	* @param @param res    设定文件 
	* @author likun   
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping("/saveDiscount.do")
	public void saveDiscount(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			transOrderServiceImpl.saveDiscount(param);
			map.put("result_flag", "1");
			map.put("result_reason", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("result_flag", "0");
			map.put("result_reason", "操作失败,失败原因:"+e.getMessage());	
		}
		out.write(JSONObject.toJSON(map).toString());
		out.flush();
	}

	/**
	 * 
	* @Title: getDiscount 
	* @Description: TODO(获取区间折扣数据) 
	* @param @param map
	* @param @param request
	* @param @param res    设定文件 
	* @author likun   
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping("/getDiscount_tab.do")
	public void getDiscount(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			ArrayList<?> list=transOrderServiceImpl.getDiscountTd(param);
			out.write(JSONArray.toJSON(list).toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	* @Title: delFormula 
	* @Description: TODO(获取运费合同收费数据) 
	* @param @param map
	* @param @param request
	* @param @param res    设定文件 
	* @author likun   
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping("/del_formula.do")
	public void delFormula(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			transOrderServiceImpl.delFormulaTd(param);
			map.put("result_flag", "1");
			map.put("result_reason", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("result_flag", "0");
			map.put("result_reason", "操作失败");
		}
		out.write(JSONObject.toJSON(map).toString());
		out.flush();
	}
	
	/**
	 * 
	* @Title: delDiscount 
	* @Description: TODO(删除折扣区间数据) 
	* @param @param map
	* @param @param request
	* @param @param res    设定文件 
	* @author likun   
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping("/del_discount.do")
	public void delDiscount(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			transOrderServiceImpl.delDiscountTd(param);
			map.put("result_flag", "1");
			map.put("result_reason", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("result_flag", "0");
			map.put("result_reason", "操作失败");
		}
		out.write(JSONObject.toJSON(map).toString());
		out.flush();
	}
  
	/**
	 * 
	* @Title: saveSpeci 
	* @Description: TODO(保存特殊服务费数据) 
	* @param @param map
	* @param @param request
	* @param @param res    设定文件 
	* @author likun   
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping("/saveSpecial.do")
	public void saveSpeci(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			transOrderServiceImpl.saveSpecial(param);
			map.put("specialId", param.get("id"));
			map.put("result_flag", "1");
			map.put("result_reason", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("result_flag", "0");
			map.put("result_reason", "操作失败");
		}
		out.write(JSONObject.toJSON(map).toString());
		out.flush();
	}
    /**
     * 
    * @Title: saveManager 
    * @Description: TODO(管理费) 
    * @param @param map
    * @param @param request
    * @param @param res    设定文件 
    * @author likun   
    * @return void    返回类型 
    * @throws
     */
	@RequestMapping("/saveManager.do")
	public void saveManager(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			transOrderServiceImpl.saveManager(param);
			map.put("id", param.get("id"));
			map.put("result_flag", "1");
			map.put("result_reason", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("result_flag", "0");
			map.put("result_reason", "操作失败");
		}
		out.write(JSONObject.toJSON(map).toString());
		out.flush();
	}
	
	
	
	
	@RequestMapping("/get_goodType.do")
	public void getGoodType(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			ArrayList<?> list=transOrderServiceImpl.getGoodType(param);
			out.write(JSONArray.toJSON(list).toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
	@RequestMapping("/getExpress.do")
	public void getExpress(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			List<Map<String, Object>>list=transOrderServiceImpl.getExpress();
			out.write(JSONArray.toJSON(list).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
	@RequestMapping("/addExpress.do")
	public void addExpress(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			transOrderServiceImpl.addExpress(param);
			map.put("result_flag", "1");
			map.put("result_reason", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("result_flag", "0");
			map.put("result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(map).toString());
		out.flush();
	}
	
	@RequestMapping("/delExpressTab.do")
	public void delExpressTab(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			transOrderServiceImpl.delExpressTab(param);
			map.put("result_flag", "1");
			map.put("result_reason", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("result_flag", "0");
			map.put("result_reason", "操作失败");
		}
		out.write(JSONObject.toJSON(map).toString());
		out.flush();
	}
	
	
	@RequestMapping("/getExpressTab.do")
	public void getExpressTab(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			System.out.println("==================");
			ArrayList<?>list=transOrderServiceImpl.getExpressTab(param);
			out.write(JSONArray.toJSON(list).toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/init_bj_data.do")
	public void init_bj_data(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			HashMap bj_data=transOrderServiceImpl.init_bj_data(param);
			out.write(JSONArray.toJSON(bj_data).toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/initData_glf.do")
	public void initData_glf(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			HashMap initData_glf=transOrderServiceImpl.initData_glf(param);
			out.write(JSONArray.toJSON(initData_glf).toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/updateCarriagelData.do")
	public void updateCarriagelData(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			transOrderServiceImpl.updateCarriagelData(param);
			map.put("result_flag", "1");
			map.put("result_reason", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("result_flag", "0");
			map.put("result_reason", "操作失败");
		}
		out.write(JSONObject.toJSON(map).toString());
		out.flush();
	}
	
	@RequestMapping("/savegoods.do")
	public void savegoods(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			transOrderServiceImpl.savegoods(param);
			map.put("result_flag", "1");
			map.put("result_reason", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("result_flag", "0");
			map.put("result_reason", "操作失败");
		}
		out.write(JSONObject.toJSON(map).toString());
		out.flush();
	}
	
	@RequestMapping("/getgoods.do")
	public void getgoods(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			List list=transOrderServiceImpl.initgoods(param);
			out.write(JSONArray.toJSON(list).toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/del_gs_tab.do")
	public void del_gs_tab(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			transOrderServiceImpl.delete_gs_tab(param);
			map.put("result_flag", "1");
			map.put("result_reason", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("result_flag", "0");
			map.put("result_reason", "操作失败");
		}
		out.write(JSONObject.toJSON(map).toString());
		out.flush();
	}
	
	
	
	@RequestMapping("/queryRawData.do")
	public String rawDataQury(TransOrderQueryParam queryParam, HttpServletRequest request){
		
		try{
			//根据条件查询合同集合
			PageView<TransOrderBean> pageView = new PageView<TransOrderBean>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			queryParam.setStoreName(request.getParameter("storeName"));
			queryParam.setTransportName(request.getParameter("transportName"));
			QueryResult<TransOrderBean> qr = transOrderServiceImpl.findAllData(queryParam);
			pageView.setQueryResult(qr, queryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/lmis/trans_raw_data";
	}
	
	
	
	
	@RequestMapping("/save_wk.do")
	public void save_wk(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			transOrderServiceImpl.saveWk(param);
			map.put("result_flag", "1");
			map.put("result_reason", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("result_flag", "0");
			map.put("result_reason", e.getMessage());
		}
		out.write(JSONObject.toJSON(map).toString());
		out.flush();
	}
	
	
	@RequestMapping("/del_wk.do")
	public void del_wk(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		PrintWriter out = null;
		try {
			out = res.getWriter();
			Map<String,Object>param=getParamMap(request);
			transOrderServiceImpl.del_wk(param);
			map.put("result_flag", "1");
			map.put("result_reason", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("result_flag", "0");
			map.put("result_reason", "操作失败");
		}
		out.write(JSONObject.toJSON(map).toString());
		out.flush();
	}
	
	
}
