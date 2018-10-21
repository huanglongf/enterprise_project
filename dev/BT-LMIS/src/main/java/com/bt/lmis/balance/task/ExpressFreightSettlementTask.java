package com.bt.lmis.balance.task;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;

import com.bt.base.redis.RedisUtils;
import com.bt.lmis.balance.settlement.ExpressFreightSettlement;
import com.bt.lmis.utils.ExpressBalanceUtils;

/** 
 * @ClassName: ExpressFreightSettlementTask
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年7月5日 下午5:18:39 
 * 
 */
public class ExpressFreightSettlementTask {

	// 定义日志
	private static final Logger logger=Logger.getLogger(ExpressFreightSettlement.class);
	
	/**
	 * @Title: invoke
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年6月21日 下午5:24:36
	 */
	public void invoke() {
		if(RedisUtils.check_con()){
			// 清空redis缓存
			RedisUtils.flushDB();
			// 向redis缓存结算必要资源数据
			try {
				ExpressBalanceUtils.g2RedisCacheSE();
			} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
		Thread expressExpenditureFreightSettlement=new Thread(new Runnable(){
			@Override
			public void run(){
				// 快递运单明细结算
				try {
//					((ExpressFreightSettlement)SpringUtils.getBean("expressFreightSettlement")).expressExpenditureFreightSettlement(null,"",500000);
				} catch (BeansException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Thread expressRevenueFreightSettlement=new Thread(new Runnable(){
			@Override
			public void run(){
				// 客户/店铺使用快递运单明细结算
				try {
//					((ExpressFreightSettlement)SpringUtils.getBean("expressFreightSettlement")).expressRevenueFreightSettlement(null,"",500000);
				} catch (BeansException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		// 运行
		expressExpenditureFreightSettlement.start();
		expressRevenueFreightSettlement.start();
		// 阻塞
		try {
			expressExpenditureFreightSettlement.join();
			expressRevenueFreightSettlement.join();
		} catch (InterruptedException e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}
	
}
