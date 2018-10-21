package com.bt.lmis.summary;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.bt.base.redis.RedisUtils;
import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.service.ExpressBalanceService;
import com.bt.lmis.service.ExpressUsedBalanceService;
import com.bt.lmis.utils.ExpressBalanceUtils;

/**
 * @Title:ExpressBalance
 * @Description: TODO(快递明细结算) 
 * @author Ian.Huang 
 * @date 2016年7月12日上午11:19:44
 */
public class ExpressBalance {
	// 定义日志
	private static final Logger logger = Logger.getLogger(ExpressBalance.class);
	
	// 定义线程池
	private ExecutorService pool= Executors.newCachedThreadPool();
	
	/**
	 * 
	 * @Description: TODO(快递明细结算)
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年7月19日下午5:41:22
	 */
	public void expressBalance(){
		try {
			if(RedisUtils.check_con()){
				// 清空redis缓存
				RedisUtils.flushDB();
				// 向redis缓存结算必要资源数据
				ExpressBalanceUtils.g2RedisCache();
				
			}
			Thread expressBalance= new Thread(new Runnable(){
				@Override
				public void run(){
					try {
						// 快递运单明细结算 支出
						((ExpressBalanceService)SpringUtils.getBean("expressBalanceServiceImpl"))
								.expressBalance(pool, 10, 10000);
						
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(e);
						
					}
					
				}
				
			});
			Thread expressUsedBalance= new Thread(new Runnable(){
				@Override
				public void run(){
					try {
						// 客户/店铺使用快递运单明细结算 收入
						((ExpressUsedBalanceService)SpringUtils.getBean("expressUsedBalanceServiceImpl"))
								.expressUsedBalance(pool, 10, 10000);
						
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(e);
						
					}
					
				}
				
			});
			// 运行
			expressBalance.start();
			expressUsedBalance.start();
			// 阻塞
			expressBalance.join();
			expressUsedBalance.join();
			// 销毁线程池
			pool.shutdown();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		
	}
	
}