package com.bt.radar.controller;




import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.AgeingDetailUplodaeResultQueryParameter;
import com.bt.radar.model.AgeingDetailUplodaeResult;
import com.bt.radar.service.AgeingDetailUplodaeResultService;
import com.bt.utils.BaseConst;



@Controller
@RequestMapping("/control/radar/ageingDetailUplodaeResultController")
public class AgeingDetailUplodaeResultController {


	private static final Logger logger = Logger.getLogger(AgeingDetailUplodaeResultController.class);
	
	@Resource(name = "ageingDetailUplodaeResultServiceImpl")
	private AgeingDetailUplodaeResultService<AgeingDetailUplodaeResult> ageingDetailUplodaeResultServiceImpl;
	
	@RequestMapping("/query")
	public String toForm(AgeingDetailUplodaeResultQueryParameter queryParam, HttpServletRequest request) throws Exception{
		QueryResult<AgeingDetailUplodaeResult> qr=null;
		PageView<AgeingDetailUplodaeResult> pageView = new PageView<AgeingDetailUplodaeResult>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		qr = ageingDetailUplodaeResultServiceImpl.queryAgeingDetailUplodaeResult(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage()); 
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
	  return "/radar/ageingmaster/ageingDetailUploadResult";
	}
	
}
