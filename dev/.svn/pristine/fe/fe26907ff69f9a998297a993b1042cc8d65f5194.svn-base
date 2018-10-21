package com.bt.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.poi.ss.formula.functions.T;

import com.bt.lmis.balance.dao.RecoverSettlementDataMapper;
import com.bt.lmis.base.spring.SpringUtils;

/** 
 * @ClassName: RecoverTaskListener
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年7月14日 下午2:26:56 
 * 
 */
public class RecoverTaskListener implements ServletContextListener{

	@SuppressWarnings("unchecked")
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("重新结算任务清空");
		((RecoverSettlementDataMapper<T>)SpringUtils.getBean("RecoverSettlementDataMapper")).cleanRecover();
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("contextDestroyed");
	}
	
}