package com.bt.lmis.balance;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.bt.lmis.service.ExpressBalanceService;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"/spring.xml","/spring-mvc.xml","/spring-mybatis.xml"})  //{"/spring.xml","/spring-mvc.xml","/spring-mybatis.xml"}
public class ExpressBalanceServiceTest extends AbstractTransactionalJUnit4SpringContextTests{
	
//	@Autowired
//	private ExpressBalanceService expressBalanceService;
	@Resource(name = "expressBalanceServiceImpl")
	private ExpressBalanceService expressBalanceService;
	
	@Test
//	@Transactional   //标明此方法需使用事务  
    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚
	public void test(){
		try {
			// 定义线程池
			ExecutorService pool= Executors.newCachedThreadPool();
			expressBalanceService.expressBalance(pool, 10, 10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
