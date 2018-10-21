package com.bt.radar.controller;



import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.AgeingDetailQueryParam;
import com.bt.radar.controller.form.AgeingDetailUploadQueryParam;
import com.bt.radar.model.AgeingDetail;
import com.bt.radar.model.AgeingDetailUpload;
import com.bt.radar.model.Area;
import com.bt.radar.service.AgeingDetailUploadService;
import com.bt.utils.BaseConst;



@Controller
@RequestMapping("/control/radar/ageingDetailUploadController")
public class AgeingDetailUploadController {


	private static final Logger logger = Logger.getLogger(AgeingDetailUploadController.class);
	
	@Resource(name = "ageingDetailUploadServiceImpl")
	private AgeingDetailUploadService<AgeingDetailUpload> ageingDetailUploadServiceImpl;
	
	@RequestMapping("/query")
	public String toForm(AgeingDetailUploadQueryParam queryParam, HttpServletRequest request) throws Exception{
		QueryResult<AgeingDetailUpload> qr=null;
		PageView<AgeingDetailUpload> pageView = new PageView<AgeingDetailUpload>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		qr = ageingDetailUploadServiceImpl.queryAgeingDetailUpload(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage()); 
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
	  return "/radar/ageingmaster/ageingDetailUploadResult";
	}
	
}
