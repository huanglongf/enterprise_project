package com.bt.lmis.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.InvitationQueryParam;
import com.bt.lmis.model.InvitationBean;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.InvitationDataService;
import com.bt.utils.BaseConst;

@Controller
@RequestMapping("/control/invitationController")
public class InvitationDataController extends BaseController {

	private static final Logger logger = Logger.getLogger(EmployeeController.class);
	
	@Resource(name="invitationDataServiceImpl")
	private InvitationDataService<InvitationBean> invitationDataServiceImpl;
	
	@RequestMapping("/list")
	public String getTestList(InvitationQueryParam queryParam, ModelMap map, HttpServletRequest request){
		try{
			//根据条件查询合同集合
			PageView<InvitationBean> pageView = new PageView<InvitationBean>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			queryParam.setStoreName(request.getParameter("storeName"));
			queryParam.setHcNo(request.getParameter("hcNo"));
			QueryResult<InvitationBean> qr = invitationDataServiceImpl.findAll(queryParam);
			pageView.setQueryResult(qr, queryParam.getPage());
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
		}
		return "/lmis/invitation_list";
	}
	
}
