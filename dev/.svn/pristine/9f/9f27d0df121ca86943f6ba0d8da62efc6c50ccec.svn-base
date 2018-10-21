package com.bt.lmis.balance.settlement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.dao.ExpressExpenditureFreightSettlementMapper;
import com.bt.lmis.balance.model.ExpressExpenditureFreightSettlementRule;
import com.bt.lmis.balance.thread.ExpressExpenditureFreightSettlementRunnable;
import com.bt.utils.CommonUtils;

/** 
 * @ClassName: ExpressFreightSettlement
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年7月5日 下午4:43:26 
 * 
 */
@Service("expressFreightSettlement")
public class ExpressFreightSettlement {
	
	@Autowired
	private ExpressExpenditureFreightSettlementMapper<T> expressExpenditureFreightSettlementMapper;
	
	/**
	 * @Title: expressExpenditureFreightSettlement
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param rawDataTable
	 * @param conId
	 * @param batchNumber
	 * @throws Exception
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年7月6日 下午1:26:03
	 */
	public void expressExpenditureFreightSettlement(Integer[] conId,String rawDataTable,int batchNum) throws Exception {
		// 根据传参获取需要的合同规则报价
		List<ExpressExpenditureFreightSettlementRule> rule=expressExpenditureFreightSettlementMapper.ensureExpressExpenditureFreightSettlementRule("查询"+(CommonUtils.checkExistOrNot(conId)?"指定":"全部")+"有效快递合同规则报价",conId);
		//
		String deadline=null;
		Calendar now=Calendar.getInstance();
		switch(now.get(Calendar.HOUR_OF_DAY)) {
		case 3:
			deadline=new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(now.getTime());
			break;
		case 19:
			deadline=new SimpleDateFormat("yyyy-MM-dd 18:00:00").format(now.getTime());
			break;
		default:break;
		
		}
		// 转移数据到临时表
		expressExpenditureFreightSettlementMapper.cleanTempTable("清空临时表");
		expressExpenditureFreightSettlementMapper.move2TempTable(rawDataTable+"向结算临时表转移"+batchNum+"条数据"+(CommonUtils.checkExistOrNot(deadline)?"（截止时间："+deadline+"，不包含）":""),rawDataTable,deadline,batchNum);
		// 定义线程池
		ExecutorService pool=Executors.newSingleThreadExecutor();
		while(expressExpenditureFreightSettlementMapper.countTempTable("查询抓取到临时表数据量")!=0) {
			if(CommonUtils.checkExistOrNot(rule)) {
				for(int i=0;i<rule.size();i++) {
					pool.submit(new ExpressExpenditureFreightSettlementRunnable(rule.get(i)));
					
				}
				
			}
			//
			expressExpenditureFreightSettlementMapper.cleanTempTable("清空临时表");
//			expressExpenditureFreightSettlementMapper.move2TempTable(rawDataTable+"向结算临时表转移"+batchNum+"条数据"+(CommonUtils.checkExistOrNot(deadline)?"（截止时间："+deadline+"，不包含）":""),rawDataTable,deadline,batchNum);
			
		}
		// 销毁线程池
		pool.shutdown();
		
	}
	
	/**
	 * @Title: expressRevenueFreightSettlement
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param conId
	 * @param rawDataTable
	 * @param batchNumber
	 * @throws Exception
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年7月6日 下午3:16:27
	 */
	public void expressRevenueFreightSettlement(Integer conId,String rawDataTable,int batchNumber) throws Exception {
		
		
	}
	
}
