package com.bt.lmis.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.InvitationUseanmountDataQueryParam;
import com.bt.lmis.model.InvitationUseanmountData;
import com.bt.lmis.model.Store;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.InvitationUseanmountDataService;
import com.bt.lmis.service.StoreService;
import com.bt.utils.BaseConst;
/**
 * 耗材使用量原始数据控制器
 *
 */
@Controller
@RequestMapping("/control/lmis/invitationUseanmountDataController")
public class InvitationUseanmountDataController extends BaseController {

	private static final Logger logger = Logger.getLogger(InvitationUseanmountDataController.class);
	
	/**
	 * 耗材使用量原始数据服务类
	 */
	@Resource(name = "invitationUseanmountDataServiceImpl")
	private InvitationUseanmountDataService<InvitationUseanmountData> invitationUseanmountDataService;
	
	@Resource(name = "storeServiceImpl")
	private StoreService<Store> storeService;
	
	@RequestMapping("/list")
	public String query(InvitationUseanmountDataQueryParam queryParam,ModelMap map, HttpServletRequest request){
		
		try{
			//根据条件查询合同集合
			PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<Map<String,Object>> qr = invitationUseanmountDataService.findAllData(queryParam);
		
			List<Map<String, Object>> storeList = storeService.findAll();
			request.setAttribute("storeList", storeList);
			pageView.setQueryResult(qr, queryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			logger.error(e);
		}
		
		return "/lmis/invitation_useamount";
	}
}
