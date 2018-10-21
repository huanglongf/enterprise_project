package com.bt.lmis.balance;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.bt.lmis.balance.dao.ContractMapper;
import com.bt.lmis.balance.estimate.PackageFeeEstimate;
import com.bt.lmis.balance.estimate.ValueAddedFeeEstimate;
import com.bt.lmis.balance.model.Contract;
import com.bt.lmis.balance.model.EstimateParam;
import com.bt.lmis.balance.model.EstimateResult;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ContractBasicinfoService;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"/spring.xml","/spring-mvc.xml","/spring-mybatis.xml"})  //{"/spring.xml","/spring-mvc.xml","/spring-mybatis.xml"}
public class EstimateTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private ValueAddedFeeEstimate valueAddedFeeEstimate;
	
	@Autowired
	private PackageFeeEstimate packageFeeEstimate;
	
	@Autowired
	private ContractMapper mapper;
	
	@Test
//  @Transactional   //标明此方法需使用事务  
    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void testValueAdded(){
		EstimateParam param = new EstimateParam();
		param.setBatchNumber("123123");
//		Contract con= mapper.selectByPrimaryKey(259);
//		System.out.println(con.toString());
		Contract con= mapper.selectByPrimaryKey(256);
		System.out.println(con.getId());
		param.setContract(con);
		
//		Contract contract = new Contract();
//		contract.setContractName("adidas官方旗舰店");
//		contract.setContractOwner("AD");//AD
//		contract.setContractType("4");//4
//		contract.setId(250);
		
		Map<String,Object> param1 = new HashMap<String,Object>();
		param1.put("logTitle", "增值服务费");
		param.setParam(param1);
		param.setFromDate("2016-11-02 00:00:00");
		param.setToDate("2017-05-01 00:00:00");
		EstimateResult estimateResult = valueAddedFeeEstimate.estimate(param);
		System.out.println("============="+estimateResult.isFlag()+"===========");
	}
	@Test
//	@Transactional   //标明此方法需使用事务  
	@Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void testPackage(){
		EstimateParam param = new EstimateParam();
		param.setBatchNumber("33");
//		List<Contract> list = mapper.findAll();
//		for(Contract c:list){
//	for(int i=39;i<40;i++){
//		Contract c = list.get(i);
		Contract con= mapper.selectByPrimaryKey(254);
		System.out.println(con.getId());
		param.setContract(con);
		
		Map<String,Object> map = new HashMap<>();
		param.setFromDate("2017-04-01 00:00:00");
		param.setToDate("2017-05-01 00:00:00");
		
		map.put("logTitle", "打包费");
		param.setParam(map);
		
		EstimateResult estimateResult = packageFeeEstimate.estimate(param);
		System.out.println("============="+estimateResult.isFlag()+"===========");
//		}
		System.out.println("===============================G");
	}
	
	
}
