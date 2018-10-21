package com.bt.lmis.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bt.lmis.balance.estimate.OperatingCostEstimate;
import com.bt.lmis.balance.model.Contract;
import com.bt.lmis.balance.model.EstimateParam;
import com.bt.lmis.balance.model.EstimateResult;
import com.bt.lmis.code.BaseController;

@Controller
@RequestMapping("/control/operatingCostEstimateController")
public class OperatingCostEstimateController extends BaseController  {

	@Autowired
	private OperatingCostEstimate operatingCostEstimate;
	
	
	
	@RequestMapping("estimate")
	@ResponseBody
	public String estimate(){

		EstimateParam estimateParam = new EstimateParam();
		
		Contract contract = new Contract();
		contract.setId(294);
		contract.setContractName("Nike官方旗舰店");
		contract.setContractOwner("Nikegfqjd");
		contract.setContractType("4");
		
		
		estimateParam.setBatchNumber(UUID.randomUUID().toString().trim().replaceAll("-", ""));
		estimateParam.setFromDate("2017-03-05");
		estimateParam.setToDate("2017-04-05");
		estimateParam.setContract(contract);
		
		
		EstimateResult estimateResult = operatingCostEstimate.estimate(estimateParam);
		
		if(estimateResult.isFlag()){
			System.out.println("===============================");
			System.out.println("===========操作费结算成功==========");
			System.out.println("===============================");
			return "操作费结算成功";
		} else {
			System.out.println("===============================");
			System.out.println("===========操作费结算异常==========");
			System.out.println("===============================");
			return "操作费结算异常";
		}
		
	}
	
	
	
	
	
	
	
	
}
