package com.bt.lmis.balance.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.balance.estimate.ValueAddedFeeEstimate;
import com.bt.lmis.balance.model.AddservicefeeBilldataEstimate;
import com.bt.lmis.balance.model.Contract;
import com.bt.lmis.balance.model.EstimateParam;
import com.bt.lmis.balance.service.AddservicefeeBilldataEstimateService;
/**
 * 增值服务费预估数据控制器
 *
 */
import com.bt.lmis.code.BaseController;
@Controller
@RequestMapping("/control/balance/addservicefeeBilldataEstimateController")
public class AddservicefeeBilldataEstimateController extends BaseController {

	private static final Logger logger = Logger.getLogger(AddservicefeeBilldataEstimateController.class);
	
	/**
	 * 增值服务费预估数据服务类
	 */
	@Resource(name = "addservicefeeBilldataEstimateServiceImpl")
	private AddservicefeeBilldataEstimateService<AddservicefeeBilldataEstimate> addservicefeeBilldataEstimateService;
	@Autowired
	private ValueAddedFeeEstimate valueAddedFeeEstimate;
	
	@RequestMapping("/valueAddedFeeEstimate")
	public String inite(HttpServletRequest request, HttpServletResponse res){
		EstimateParam param = new EstimateParam();
		param.setBatchNumber("1");
		Contract contract = new Contract();
		contract.setId(1);
		contract.setContractOwner("123");
		String fromDate = "2017-01-01";
		String toDate = "2017-05-05";
		param.setFromDate(fromDate);
		param.setToDate(toDate);
		param.setContract(contract);
		valueAddedFeeEstimate.estimate(param);
		
		return "lmis/warehouse_expressdata";
	}
}
