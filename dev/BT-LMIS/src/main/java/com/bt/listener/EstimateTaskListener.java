package com.bt.listener;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.bt.lmis.balance.task.EstimateTask;
import com.bt.lmis.base.spring.SpringUtils;

/** 
 * @ClassName: EstimateTaskListener
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月24日 下午8:27:42 
 * 
 */
public class EstimateTaskListener implements ServletContextListener{

	private static final String serviceMac = "18-66-DA-5B-43-13";
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
			if(serviceMac.equals(prop.getProperty("service_mac"))) {
				new Thread(new Runnable(){
					@Override
					public void run(){
						((EstimateTask)SpringUtils.getBean("estimateTask")).estimate();
					}
				}).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("contextDestroyed");
	}
	
}