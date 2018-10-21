package com.bt.lmis.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.controller.form.WarehouseExpressDataSettlementQueryParam;
import com.bt.lmis.model.WarehouseFeeRD;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.WarehouseExpressDataSettlementService;
import com.bt.lmis.service.WarehouseFeeRDService;
import com.bt.utils.BaseConst;

@Controller
@RequestMapping("/control/WarehouseExpressDataSettleController")

public class WarehouseExpressDataSettleController {

	
	@Resource(name="warehouseExpressDataSettlementServiceImpl")
	private WarehouseExpressDataSettlementService warehouseExpressDataSettlementServiceImpl;
	
	
	@RequestMapping("inite")
	public String inite(HttpServletRequest request, HttpServletResponse res){
		
		return "lmis/warehouse_express_data_settle";
	}
	
	
	
	@RequestMapping("pageQuery")
	public String pageQuery(HttpServletRequest request, HttpServletResponse res,WarehouseExpressDataSettlementQueryParam query){
		
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(query.getPageSize() == 0? BaseConst.pageSize:query.getPageSize(), query.getPage());
		query.setFirstResult(pageView.getFirstResult());
		query.setMaxResult(pageView.getMaxresult());
		QueryResult<Map<String, Object>> qr = warehouseExpressDataSettlementServiceImpl.findAll(query);
		pageView.setQueryResult(qr, query.getPage()); 
		request.setAttribute("pageView", pageView);
		request.setAttribute("WarehouseExpressDataSettlementQueryParam", query);
		
		
		return "lmis/warehouse_express_data_settle_page";
	}
	
	
}
