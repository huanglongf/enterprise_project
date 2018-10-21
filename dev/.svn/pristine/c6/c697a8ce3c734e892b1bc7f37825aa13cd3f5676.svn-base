package com.bt.lmis.balance;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.bt.JUnitAbstract;
import com.bt.lmis.balance.settlement.ExpressFreightSettlement;
import com.bt.lmis.base.spring.SpringUtils;

/** 
 * @ClassName: ExpressExpenditureFreightSettlementDaoTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年7月5日 下午4:04:18 
 * 
 */
public class ExpressFreightSettlementTest extends JUnitAbstract {
	
	@Test
	// 标明此方法需使用事务  
    @Transactional
    // 标明使用完此方法后事务不回滚,true时为回滚
    @Rollback(false)
	public void test() {
		try {
			Integer[] conId=null;
			// conId= new Integer[] {180, 213, 210, 253};
			((ExpressFreightSettlement)SpringUtils.getBean("expressFreightSettlement")).expressExpenditureFreightSettlement(conId,"tb_warehouse_express_data",500000);
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
