package com.bt.lmis.balance;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bt.lmis.balance.service.BalanceService;
import com.bt.lmis.controller.form.DiffBilldeatilsQueryParam;
import com.bt.lmis.dao.DiffBilldeatilsMapper;
import com.bt.lmis.model.CollectionMaster;
import com.bt.lmis.model.DiffBilldeatils;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"/spring.xml","/spring-mvc.xml","/spring-mybatis.xml"})  //{"/spring.xml","/spring-mvc.xml","/spring-mybatis.xml"}
public class BalanceServiceTest2 extends AbstractTransactionalJUnit4SpringContextTests{
	
//	@Autowired
//	private BalanceService balanceService;
	@Resource(name = "balanceServiceImpl")
	private BalanceService balanceService;
	
	@Autowired
	private DiffBilldeatilsMapper<T> diffBilldeatilsMapper;
	
	@Test
//  @Transactional   //标明此方法需使用事务  
    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void test(){
		
		DiffBilldeatilsQueryParam queryParam=new DiffBilldeatilsQueryParam();
		queryParam.setWaybill("12222414719");
		List<DiffBilldeatils> list = diffBilldeatilsMapper.selectMasterId(queryParam);
		System.out.println("ori"+list.toString());
		List<DiffBilldeatils> res = balanceService.rebalance(list, 210);
		System.out.println("res:"+res.toString());
	}
	
	@Test
//  @Transactional   //标明此方法需使用事务  
	@Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void testdis(){
		Map<String,String> param = new HashMap<String, String>();
		param.put("table_name", "tb_diff_billdeatils");
		param.put("account_id", null);
		param.put("contract_id", "210");
		Map<String,Object> res = balanceService.getDiscount(param);
		System.out.println("res:"+res.toString());
	}
	
	@Test
//  @Transactional   //标明此方法需使用事务  
	@Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void testsum(){
		Map<String,String> param = new HashMap<String, String>();
		param.put("table_name", "tb_diff_billdeatils");
		param.put("account_id", null);
		param.put("contract_id", "210");
		List<CollectionMaster> res = balanceService.getSummary(param);
		System.out.println(res.toString());
	}

}
