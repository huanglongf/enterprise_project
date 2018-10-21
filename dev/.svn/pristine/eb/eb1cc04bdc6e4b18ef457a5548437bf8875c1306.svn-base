package com.bt.lmis.balance.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.balance.controller.param.Parameter;
import com.bt.lmis.balance.dao.RecoverSettlementDataMapper;
import com.bt.lmis.balance.model.RecoverProcess;
import com.bt.lmis.balance.service.RecoverSettlementDataService;
import com.bt.utils.CommonUtils;

/** 
 * @ClassName: RecoverSettlementDataServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年7月11日 下午9:36:50 
 * 
 */
@Service("recoverSettlementDataServiceImpl")
public class RecoverSettlementDataServiceImpl implements RecoverSettlementDataService<T> {
	
	@Autowired
	private RecoverSettlementDataMapper<T> mapper;
	
	@Override
	public List<Map<String, Object>> ensureRecoverProcess() throws Exception {
		return mapper.ensureRecoverProcess();
		
	}
	
	@Override
	public JSONObject addRecoverTask(Parameter parameter) throws Exception {
		JSONObject result=new JSONObject();
		Map<String,String> recoverDate=CommonUtils.spiltDateString(parameter.getParam().get("recoverDate").toString());
		List<RecoverProcess> recoverProcess=new ArrayList<RecoverProcess>();
		String[] conId=(String[]) parameter.getParam().get("conId[]");
		for(int i=0; i<conId.length; i++) {
			recoverProcess.add(
					new RecoverProcess(
							parameter.getCurrentAccount().getId().toString(),
							Integer.parseInt(parameter.getParam().get("move").toString()),
							Integer.parseInt(conId[i]),
							recoverDate.get("startDate").toString(),
							recoverDate.get("endDate").toString()
							));
			
		}
		mapper.addRecoverTask(recoverProcess);
		// 判断是否存在正在处理状态的还原进程
		if(mapper.existProcessing() == 0) {
			final RecoverProcess temp=mapper.ensureNextProcessing();
			if(CommonUtils.checkExistOrNot(temp)) {
				new Thread(new Runnable(){
					@Override
					public void run(){
						startRecoverProcess(temp);
						
					}
					
				}).start();
				
			}
			
		}
		//
		result.put("result_code", "SUCCESS");
		result.put("result_content", "提交成功");
		return result;
	}
	
	@Override
	public void startRecoverProcess(RecoverProcess recoverProcess) {
		while(CommonUtils.checkExistOrNot(recoverProcess)) {
			// 开始处理
			mapper.updateProcess(1,  "开始处理", recoverProcess.getId());
			try {
				// 正常处理完成
				mapper.updateProcess(2,  recoverSettlementData(recoverProcess), recoverProcess.getId());
				
			} catch (Exception e) {
				// 发生异常
				mapper.updateProcess(-1, CommonUtils.getExceptionStack(e), recoverProcess.getId());
				e.printStackTrace();
				
			}
			recoverProcess=mapper.ensureNextProcessing();
			
		}
		
	}
	
	@Override
	public String recoverSettlementData(RecoverProcess recoverProcess) throws Exception {
		String log="";
		int contractType=mapper.ensureContractType(recoverProcess.getConId());
		// 手动提交事务
		WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager)contextLoader.getBean("transactionManager");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		// 事物隔离级别，开启新事务，这样会比较安全些。
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		// 获得事务状态
		TransactionStatus status=transactionManager.getTransaction(def);
		try {
			// 删除异常信息
			log+=
					"删除"
			+mapper.deleteErrorDetail(recoverProcess.getConId(), recoverProcess.getRecoverStartDate(), recoverProcess.getRecoverEndDate(), contractType)
			+"条异常数据；";
			//
			switch(contractType) {
			case 1:
				if(recoverProcess.getMove() == 1) {
					log+=
							"删除"
					+mapper.deleteSettlementDetail("tb_warehouse_express_data_settlement", recoverProcess.getConId(), recoverProcess.getRecoverStartDate(), recoverProcess.getRecoverEndDate())
					+"条结算数据；";
					
				}
				log+=
						"更新"
				+mapper.recoverSettleFlag(recoverProcess)
				+"条原始数据快递结算标志";
				break;
			case 4:
				if(recoverProcess.getMove() == 1) {
					log+=
							"删除"
					+mapper.deleteSettlementDetail("tb_warehouse_express_data_store_settlement", recoverProcess.getConId(), recoverProcess.getRecoverStartDate(), recoverProcess.getRecoverEndDate())
					+"条结算数据；";
					
				}
				log+=
						"更新"
				+mapper.recoverSettleClientFlag(recoverProcess)
				+"条原始数据客户结算标志";
				break;
			default:break;
			}
		    transactionManager.commit(status);
		    
		} catch (Exception e) {
		    transactionManager.rollback(status);
		    throw e;
		    
		}
		return log;

	}

}
