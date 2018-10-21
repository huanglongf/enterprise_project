package com.bt.vims.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bt.vims.model.AddressInfor;
import com.bt.vims.model.AddressQueryParam;
import com.bt.vims.page.PageView;
import com.bt.vims.page.QueryResult;
import com.bt.vims.service.AddressInforService;
import com.bt.vims.utils.BaseConst;

import common.Contents;

/**
 * 地址信息控制类
 * 
 * @author liuqingqiang
 * @date 2018-05-11
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/control/addressControl")
public class AddressControl {
	private static final Logger logger = Logger.getLogger(AddressControl.class);

	@Resource(name = "addressInforServiceImpl")
	private AddressInforService addressInforService;

	/**
	 * 地址信息列表页
	 * 
	 * @param queryParam (设置查询参数)
	 * @param request （http请求参数实例）
	 * @return 查询数据实例
	 */
	@RequestMapping(value = "/addressList.do")
	public String addressList(AddressQueryParam queryParam, ModelMap modelMap, HttpServletRequest request) {
		try {
			PageView<AddressInfor> pageView = new PageView<AddressInfor>(
					queryParam.getPageSize() == 0 ? BaseConst.pageSize : queryParam.getPageSize(),
					queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			queryParam.setIsDeleted(Contents.IS_DELETED);
			QueryResult<AddressInfor> qrs = addressInforService.findAllAddressInfor(queryParam);
			pageView.setQueryResult(qrs, queryParam.getPage());
			modelMap.addAttribute("pageView", pageView).addAttribute("queryParam", queryParam);
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error("addressList();" + e.getMessage());
		}
		return "/address/addressList";
	}

	/**
	 * 根据条件查询方法
	 * 
	 * @param queryParam (设置查询参数)
	 * @param request （http请求参数实例）
	 * @return 查询数据实例
	 */
	@RequestMapping(value = "/pageList.do", method = RequestMethod.POST)
	@ResponseBody
	public PageView<AddressInfor> pageList(AddressQueryParam queryParam, HttpServletRequest request) {
		PageView<AddressInfor> pageView = null;
		try {
			pageView = new PageView<AddressInfor>(
					queryParam.getPageSize() == 0 ? BaseConst.pageSize : queryParam.getPageSize(),
					queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			queryParam.setIsDeleted(Contents.IS_DELETED); //只查询isDeleted标志位为0的数据
			QueryResult<AddressInfor> qrs = addressInforService.findAllAddressInfor(queryParam);
			pageView.setQueryResult(qrs, queryParam.getPage());
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error("pageList();" + e.getMessage());
		}
		return pageView;
	}
	
	/**
	 * 查询登陆页面所有的地点信息
	 * @return（地点信息集合）
	 */
	@RequestMapping(value = "/findAllInfor", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findAllInfor(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<AddressInfor> addressInforLists = addressInforService.getAllAddressInfor();
			map.put("addressInforLists", addressInforLists);
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error("findAllInfor();" + e.getMessage());
		}
		return map;
	}

	/**
	 * 新增信息时验证地址编码不重复
	 * 
	 * @param addressCode
	 *            (地址编码)
	 * @return
	 */
	@RequestMapping(value = "/validateCode.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> validateCode(String addressCode) {
		Map<String, Object> map = new HashMap<>();
		boolean flag = addressInforService.validateCode(addressCode);
		if (!flag) {
			map.put("msg", "error");
		}
		return map;
	}

	/**
	 * 新增地址信息页面
	 */
	@RequestMapping(value = "/addInfor.do", method = RequestMethod.GET)
	public String addInfor() {
		return "/address/addInfor";
	}

	/**
	 * 保存新增地址信息
	 * @param addressInfor (新录入地址信息)
	 * @param request （http请求参数实例）
	 * @return 成功失败信息
	 */
	@RequestMapping(value = "/insertAddressInfor.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAddressInfor(AddressInfor addressInfor, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			addressInforService.insertAddressInfor(addressInfor, request);
			map.put("msg", "success");
		} catch (RuntimeException e) {
			map.put("msg", "error");
			e.printStackTrace();
			logger.error("insertAddressInfor()执行失败" + e.getMessage());
		}
		return map;
	}

	/**
	 * * 进入修改页面方法
	 * 
	 * @param id （修改的数据id）
	 * @param modelMap （（绑定实例至页面））
	 * @return 相应修改页面
	 */
	@RequestMapping(value = "/updatePage.do", method = RequestMethod.GET)
	public String updatePage(String id, ModelMap modelMap) {
		try {
			if (!StringUtils.isEmpty(id)) {
				AddressInfor addressInfor = addressInforService.findSingleAddressInfor(Integer.parseInt(id)); // 根据id查找方法
				modelMap.addAttribute("addressInfor", addressInfor);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error("updatePage()执行失败" + e.getMessage());
		}
		return "/address/updateInfor";
	}

	/**
	 * 保存修改页面信息
	 * 
	 * @param id（老数据id，根据id来进行修改数据）
	 * @param newAddressInfor (页面传过来的新实例对象)
	 * @return 成功或失败信息
	 */
	@RequestMapping(value = "/updateAddressInfor.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateAddressInfor(String id, AddressInfor newAddressInfor, HttpServletRequest request) {
		Map<String, String> map = new HashMap<>();
		try {
			if (!StringUtils.isEmpty(id)) {
				addressInforService.updateAddressInfor(id, newAddressInfor, request);
				map.put("msg", "success");
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			map.put("msg", "error");
			logger.error("updateAddressInfor()执行失败" + e.getMessage());
		}
		return map;
	}

	/**
	 * 删除页面数据信息
	 * 
	 * @param id（老数据id，根据id来进行修改数据）
	 * @param newAddressInfor
	 *            (页面传过来的新实例对象)
	 * @return 成功或失败信息
	 */
	@RequestMapping(value = "/deleteAddressInfor.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> deleteAddressInfor(String[] idArray, HttpServletRequest request) {
		Map<String, String> map = new HashMap<>();
		try {
			if (!StringUtils.isEmpty(idArray)) {
				addressInforService.logicDeleteAddressInfor(idArray, request);
				map.put("msg", "success");
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			map.put("msg", "error");
			logger.error("deleteAddressInfor()执行失败" + e.getMessage());
		}
		return map;
	}
}
