package com.bt.lmis.balance.thread;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.transaction.annotation.Transactional;

import com.bt.lmis.balance.dao.ExpressExpenditureFreightSettlementMapper;
import com.bt.lmis.balance.model.ExpressExpenditureFreightSettlementRule;
import com.bt.lmis.base.spring.SpringUtils;

/** 
 * @ClassName: ExpressExpenditureFreightSettlementRunnable
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年7月6日 下午3:36:42 
 * 
 */
@Transactional
public class ExpressExpenditureFreightSettlementRunnable implements Runnable {
	
	@SuppressWarnings("unchecked")
	private ExpressExpenditureFreightSettlementMapper<T> expressExpenditureFreightSettlementMapper=(ExpressExpenditureFreightSettlementMapper<T>)SpringUtils.getBean(ExpressExpenditureFreightSettlementMapper.class);
	/** 
	 * @Fields rule : TODO(用一句话描述这个变量表示什么) 
	 */
	private ExpressExpenditureFreightSettlementRule rule;
	
	public ExpressExpenditureFreightSettlementRunnable() {}
	
	public ExpressExpenditureFreightSettlementRunnable(ExpressExpenditureFreightSettlementRule rule) {
		this.rule = rule;
		
	}

	public ExpressExpenditureFreightSettlementRule getRule() {
		return rule;
	}

	public void setRule(ExpressExpenditureFreightSettlementRule rule) {
		this.rule = rule;
	}

	@Override
	public void run() {
		// 规则报价校验
		if(checkRule(rule)) {
			// 赋予报价
			expressExpenditureFreightSettlementMapper.endowOffer("批量更新运单报价{"+rule.getOffer()+"}", rule);
			
		}
		
	}
	
	private boolean checkRule(ExpressExpenditureFreightSettlementRule rule) {
		// 首重开关必须为开启状态
		if(!rule.getSzxzSwitch()) {
			
			
		}
		return true;
		
	}

}
