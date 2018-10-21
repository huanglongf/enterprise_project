package com.bt.lmis.balance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.bt.lmis.balance.estimate.StorageFeeEstimate;
import com.bt.lmis.balance.model.Contract;
import com.bt.lmis.balance.model.EstimateParam;
import com.bt.lmis.balance.model.EstimateResult;
import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.service.ContractBasicinfoService;

/**
 * 
 * @author jindong.lin
 *
 */
public class StorageFeeEstimateTest extends EstimateAbstract {

	@Autowired
	private StorageFeeEstimate storageFeeEstimate;
	// 合同服务类
	@Resource(name = "contractBasicinfoServiceImpl")
	private ContractBasicinfoService<ContractBasicinfo> contractBasicinfoService;
		

	/**
	 * 仓储费预估记录生成(批量)
	 */
	@Test
    @Transactional   //标明此方法需使用事务  
    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void storageFeeEstimateBatchTest(){

		System.out.println("进入到了测试方法");

		// 合同信息
		List<ContractBasicinfo> cbList = contractBasicinfoService.find_by_cb();
		
		List<String> messageList = new ArrayList<String>();
		
		int successNum = 0;
		int errorNum = 0;
		
		for (int i = 0; i < cbList.size(); i++) {
			ContractBasicinfo cb = cbList.get(i);

			
			EstimateParam estimateParam = new EstimateParam();
			
			Contract contract = new Contract();
			contract.setId(Integer.valueOf(cb.getId()));
			contract.setContractName(cb.getContract_name());
			contract.setContractOwner(cb.getContract_owner());
			contract.setContractType(cb.getContract_type());
			contract.setContractStart(cb.getContract_start());
			contract.setContractEnd(cb.getContract_end());
			
			Map<String,Object> param = new HashMap<String,Object>();
			
			param.put("logTitle","仓储费预估");
			

			estimateParam.setFromDate("2016-04-01");
			estimateParam.setToDate("2017-04-30");
			estimateParam.setParam(param);
			
			estimateParam.setBatchNumber(UUID.randomUUID().toString().trim().replaceAll("-", ""));
			estimateParam.setContract(contract);
			
			EstimateResult estimateResult = storageFeeEstimate.estimate(estimateParam);
	
			if(estimateResult.isFlag()){
				successNum++;
				messageList.add("uuid:" + estimateParam.getBatchNumber() + "仓储费预估成功"+estimateResult.getMsg().get("msg"));
			} else {
				errorNum++;
				messageList.add("uuid:" + estimateParam.getBatchNumber() + "仓储费预估异常"+estimateResult.getMsg().get("msg"));
			}
		}

		System.out.println("===============================");
		for (int i = 0; i < messageList.size(); i++) {
			System.out.println("===========" + "第" + (i+1) + "个记录," + messageList.get(i) + "==========");
		}
		System.out.println("=======成功数量：" + successNum + "失败数量：" + errorNum + "=========");
		System.out.println("===============================");
		
		System.out.println("测试方法结束");
		
	}
	
	

	/**
	 * 仓储费预估记录生成(单条)
	 */
	@Test
    @Transactional   //标明此方法需使用事务  
    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void storageFeeEstimateTest(){

		System.out.println("进入到了测试方法");
		
		List<String> messageList = new ArrayList<String>();

		EstimateParam estimateParam = new EstimateParam();
		
		Map<String,Object> param = new HashMap<String,Object>();
		
		int successNum = 0;
		int errorNum = 0;
		
		param.put("logTitle","仓储费预估");

		estimateParam.setParam(param);
		
		ContractBasicinfo cb = contractBasicinfoService.findById(309);
		
		Contract contract = new Contract();
		contract.setId(Integer.valueOf(cb.getId()));
		contract.setContractName(cb.getContract_name());
		contract.setContractOwner(cb.getContract_owner());
		contract.setContractType(cb.getContract_type());
		contract.setContractStart(cb.getContract_start());
		contract.setContractEnd(cb.getContract_end());

		estimateParam.setFromDate("2017-03-01");
		estimateParam.setToDate("2017-05-01");
		
		estimateParam.setBatchNumber(UUID.randomUUID().toString().trim().replaceAll("-", ""));
		estimateParam.setContract(contract);
		
		EstimateResult estimateResult = storageFeeEstimate.estimate(estimateParam);

		if(estimateResult.isFlag()){
			successNum++;
			messageList.add("uuid:" + estimateParam.getBatchNumber() + "仓储费预估成功"+estimateResult.getMsg().get("msg"));
		} else {
			errorNum++;
			messageList.add("uuid:" + estimateParam.getBatchNumber() + "仓储费预估异常"+estimateResult.getMsg().get("msg"));
		}

		System.out.println("===============================");
		for (int i = 0; i < messageList.size(); i++) {
			System.out.println("===========" + "第" + (i+1) + "个记录," + messageList.get(i) + "==========");
		}
		System.out.println("=======成功数量：" + successNum + "失败数量：" + errorNum + "=========");
		System.out.println("===============================");
		
		System.out.println("测试方法结束");
		
	}
	
	
	
	
	
	
}
