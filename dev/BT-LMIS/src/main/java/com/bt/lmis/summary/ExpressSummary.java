package com.bt.lmis.summary;

import org.apache.log4j.Logger;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.service.ExpressBalanceService;

/**
 * @Title:ExpressSummary(快递汇总结算)
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年11月29日下午4:04:36
 */
public class ExpressSummary {
	// 定义日志
	private static final Logger logger = Logger.getLogger(ExpressBalance.class);
	
	/**
	 * 
	 * @Description: TODO(供应商账单)
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年11月29日下午4:05:35
	 */
	public void expressSummary(){
		try {
			((ExpressBalanceService)SpringUtils.getBean("expressBalanceServiceImpl")).expressBalanceSummary();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		
	}
	
}