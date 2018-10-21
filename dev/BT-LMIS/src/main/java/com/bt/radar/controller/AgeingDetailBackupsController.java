package com.bt.radar.controller;




import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.bt.radar.model.*;
import com.bt.utils.BaseConst;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.AgeingDetailBackupsQueryParam;
import com.bt.radar.service.AgeingDetailBackupsService;


@Controller
@RequestMapping("/control/radar/ageingDetailBackupsController")
public class AgeingDetailBackupsController {


	private static final Logger logger = Logger.getLogger(AgeingDetailBackupsController.class);
	
	@Resource(name= "ageingDetailBackupsServiceImpl")
	private AgeingDetailBackupsService<AgeingDetailBackups> ageingDetailBackupsServiceImpl;

	
	@RequestMapping("/query")
	public String toForm(AgeingDetailBackupsQueryParam queryParam, HttpServletRequest request) throws Exception{
		QueryResult<AgeingDetailBackups> qr=null;
		PageView<AgeingDetailBackups> pageView = new PageView<AgeingDetailBackups>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		qr = ageingDetailBackupsServiceImpl.queryAgeingDetailBackups(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage()); 
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
	  return "/radar/ageingmaster/ageing_detail_backups";
	}
}
