package com.bt.lmis.balance;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.bt.lmis.balance.dao.ContractMapper;
import com.bt.lmis.balance.model.BatchEstimate;
import com.bt.lmis.balance.service.EstimateService;

public class ConsumerRevenueEstimateServiceImplTest extends EstimateAbstract {

	@Resource(name = "consumerRevenueEstimateServiceImpl")
	private EstimateService estimateService;
	@Autowired
	private ContractMapper contractMapper;
	
	@Test
	@Transactional
	@Rollback(false)
	public void consumerRevenueEstimateServiceImplTest(){
		
		System.out.println("测试开始");
		
		BatchEstimate batchEstimate = new BatchEstimate();
		
		List<Integer> cbidList = new ArrayList<Integer>();
//		cbidList.add(309);
//		cbidList.add(263);
//		cbidList.add(294);//Nike官方旗舰店
//		cbidList.add(256);
//		cbidList.add(243);
//		cbidList.add(254);
//		cbidList.add(229);
//		cbidList.add(200);//傲胜
//		cbidList.add(264);//ADIDAS
//		cbidList.add(234);//cathkidston
//		cbidList.add(305);//Speedo天猫旗舰店
//		cbidList.add(262);
//		cbidList.add(229);//aldo
//		cbidList.add(311);//腾讯体育
//		cbidList.add(312);//Aldo唯品会
//		cbidList.add(317);//联合利华
//		cbidList.add(254);//Aape
//		cbidList.add(257);//百威
//		cbidList.add(207);//曼富图
//		cbidList.add(256);//tonymoly
		cbidList.add(236);//converse
//		cbidList.add(245);//博世
		String batchNumber = UUID.randomUUID().toString().trim().replaceAll("-", "");
		batchEstimate.setBatchNumber(batchNumber);
		batchEstimate.setContractId(cbidList);
		batchEstimate.setDomainFrom("2017-09-01");
		batchEstimate.setDomainTo("2017-09-30");
		
		try {
			
			estimateService.batchEstimate(batchEstimate);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("测试结束,batchNumber = " + batchNumber);
		
	}

	
	@Test
	@Transactional
	@Rollback(false)
	public void consumerRevenueEstimateServiceImplBathTest(){
		System.out.println("测试开始");
		try {
			BatchEstimate batchEstimate = new BatchEstimate();
			batchEstimate.setBatchNumber(UUID.randomUUID().toString().trim().replaceAll("-", ""));
			batchEstimate.setContractId(contractMapper.findAllId());
//			batchEstimate.setDomainFrom("2017-04-01");
//			batchEstimate.setDomainTo("2017-04-30");
			batchEstimate.setDomainFrom("2017-09-01");
			batchEstimate.setDomainTo("2017-09-30");
			
			estimateService.batchEstimate(batchEstimate);
//			sendMail("账单成功输出");
			System.out.println("测试结束,batchNumber = " + batchEstimate.getBatchNumber());
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}

	}
	
}
