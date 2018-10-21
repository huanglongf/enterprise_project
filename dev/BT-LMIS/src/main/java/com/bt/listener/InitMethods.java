package com.bt.listener;

import java.util.HashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.workOrder.service.DistributionService;

public class InitMethods implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
       new Thread(new tread()).start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("contextDestroyed");
	}

	
	public class tread implements Runnable{
		@Override
		public void run() {
//			// TODO Auto-generated method stub
			DistributionService service=(DistributionService)SpringUtils.getBean("distributionServiceImpl");
			service.automatic_distribution(new HashMap<String,Object>());
		}
	}
}
