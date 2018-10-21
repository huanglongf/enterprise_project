package com.bt.lmis.balance.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.bt.EPlatform;
import com.bt.OSinfo;
import com.bt.lmis.balance.common.Constant;
import com.bt.lmis.balance.controller.form.EstimateQueryParam;
import com.bt.lmis.balance.controller.param.Parameter;
import com.bt.lmis.balance.dao.EstimateMapper;
import com.bt.lmis.balance.model.Estimate;
import com.bt.lmis.balance.model.EstimateContract;
import com.bt.lmis.balance.service.EstimateManagementService;
import com.bt.lmis.balance.task.EstimateTask;
import com.bt.lmis.balance.util.EstimateUtil;
import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.page.QueryResult;
import com.bt.utils.CommonUtils;
import com.bt.utils.FileUtil;

/** 
 * @ClassName: EstimateManagementServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年5月24日 上午10:34:58 
 * 
 */
@Service("estimateManagementServiceImpl")
public class EstimateManagementServiceImpl implements EstimateManagementService {

	@Autowired
	private EstimateMapper<T> estimateMapper;
	
	/**
	 * @Title: getContract
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param contract
	 * @return: String
	 * @author: Ian.Huang
	 * @date: 2017年5月23日 下午7:14:52
	 */
	private String getContract(List<String> contract) {
		String result="";
		for(int i=0; i<contract.size(); i++) {
			result+=contract.get(i);
			if(i+1!=contract.size()) {
				result+="，";
				
			}
			
		}
		return result;
		
	}
	
	@Override
	public QueryResult<Map<String, Object>> query(EstimateQueryParam param) {
		QueryResult<Map<String,Object>> qr=new QueryResult<Map<String,Object>>();
		if(CommonUtils.checkExistOrNot(param.getContract_in_estimate())) {
			List<String> estimateId=estimateMapper.queryEstimateByContract(param.getContract_in_estimate());
			if(CommonUtils.checkExistOrNot(estimateId)) {
				param.setEstimate_id(estimateId);
				
			}
			
		}
		List<Map<String, Object>> estimate=estimateMapper.queryEstimate(param);
		for(int i=0; i<estimate.size(); i++) {
			estimate.get(i).put("contract", getContract(estimateMapper.queryContractByEstimate(estimate.get(i).get("id").toString())));
			
		}
		qr.setResultlist(estimate);
		qr.setTotalrecord(estimateMapper.countEstimate(param));
		return qr;
		
	}
	
	@Override
	public List<Map<String, Object>> shiftContractByType(Parameter parameter) throws Exception {
		return estimateMapper.shiftContractByType(parameter);
		
	}

	@Override
	public String add(Parameter parameter) throws Exception {
		// 主表
		Estimate estimate=new Estimate();
		estimate.setId(UUID.randomUUID().toString());
		estimate.setCreateBy(parameter.getCurrentAccount().getId().toString());
		Map<String, String> range=CommonUtils.spiltDateString(parameter.getParam().get("dateDomain").toString());
		estimate.setDomainFrom(range.get("startDate"));
		estimate.setDomainTo(range.get("endDate"));
		estimate.setBatchNumber(EstimateUtil.batchNumberGenerator(estimate.getDomainFrom(), estimate.getDomainTo()));
		estimate.setBatchStatus(Constant.BATCH_STATUS_WAI);
		estimate.setEstimateType(Integer.parseInt(parameter.getParam().get("estimateType").toString()));
		estimate.setRemark(parameter.getParam().get("remark").toString());
		// 子表
		List<EstimateContract> contract=new ArrayList<EstimateContract>();
		String[] contractId=(String[]) parameter.getParam().get("contractId[]");
		for(int i=0; i<contractId.length; i++) {
			contract.add(new EstimateContract(UUID.randomUUID().toString(), parameter.getCurrentAccount().getId().toString(), estimate.getId(), Integer.parseInt(contractId[i])));
			
		}
		// 手动提交事务
	    WebApplicationContext contextLoader=ContextLoader.getCurrentWebApplicationContext();
	    DataSourceTransactionManager transactionManager=(DataSourceTransactionManager)contextLoader.getBean("transactionManager");
	    DefaultTransactionDefinition def=new DefaultTransactionDefinition();
	    // 事物隔离级别，开启新事务，这样会比较安全些。
	    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
	    // 获得事务状态
	    TransactionStatus status=transactionManager.getTransaction(def);
		try {
			Integer rank=estimateMapper.ensureMaxRank();
			estimate.setRank(CommonUtils.checkExistOrNot(rank)?rank+1:1);
	    	estimateMapper.add(estimate);
			estimateMapper.addContract(contract);
			transactionManager.commit(status);
			
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
			
		}
		if(estimate.getRank()==1) {
			new Thread(new Runnable(){
				@Override
				public void run(){
					((EstimateTask)SpringUtils.getBean("estimateTask")).estimate();
					
				}
				
			}).start();
			
		}
		return estimate.getBatchNumber();
		
	}

	@Override
	public void del(Parameter parameter) throws Exception {
		// 查询结算预估记录
		Estimate estimate=estimateMapper.ensureEstimateByBatchNumber(parameter.getParam().get("batchNumber").toString());
		switch(estimate.getBatchStatus()) {
		case Constant.BATCH_STATUS_FIN:;
		case Constant.BATCH_STATUS_CAN:;
		case Constant.BATCH_STATUS_ERR:
			// 删除结算预估记录
			estimateMapper.delEstimateById(estimate.getId());
			// 删除结算预估文件
			// 下载文件夹内文件删除
			FileUtil.isExistFile(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname())+estimate.getBatchNumber()+Constant.SYM_POINT+Constant.FILE_TYPE_SUFFIX_ZIP);
			// lmis文件夹内文件及文件夹删除
			String estimateTypePath="BALANCE_ESTIMATE_";
			if(estimate.getEstimateType()==0) {
				estimateTypePath+="EXPRESS";
				
			}
			if(estimate.getEstimateType()==1) {
				estimateTypePath+="CUSTOMER";
				
			}
			estimateTypePath+="_";
			FileUtil.isExistFile(CommonUtils.getAllMessage("config", estimateTypePath+OSinfo.getOSname())+estimate.getBatchNumber()+Constant.SYM_POINT+Constant.FILE_TYPE_SUFFIX_ZIP);
			String dir=CommonUtils.getAllMessage("config", estimateTypePath+OSinfo.getOSname())+estimate.getBatchNumber();
			if(OSinfo.getOSname().equals(EPlatform.Linux) || OSinfo.getOSname().equals(EPlatform.Mac_OS_X)) {
				dir+="/";
				
			} else if(OSinfo.getOSname().equals(EPlatform.Windows)) {
				dir+="\\";
				
			}
			FileUtil.deleteDir(new File(dir));
			break;
		case Constant.BATCH_STATUS_WAI:;
		case Constant.BATCH_STATUS_ING:
			throw new Exception("预估批次状态异常，无法删除");
		default:break;
		
		}
		
	}

	@Override
	public void cancel(Parameter parameter) throws Exception {
		// 查询结算预估记录
		Estimate estimate=estimateMapper.ensureEstimateByBatchNumber(parameter.getParam().get("batchNumber").toString());
		switch(estimate.getBatchStatus()) {
		case Constant.BATCH_STATUS_WAI:
			estimateMapper.canEstimate(estimate.getRank(), estimate.getBatchNumber());
			break;
		case Constant.BATCH_STATUS_ING:;
		case Constant.BATCH_STATUS_FIN:;
		case Constant.BATCH_STATUS_CAN:;
		case Constant.BATCH_STATUS_ERR:
			throw new Exception("预估批次状态异常，无法取消");
		default:break;
		
		}
		
	}

	@Override
	public void restart(Parameter parameter) throws Exception {
		// 查询结算预估记录
		Estimate estimate=estimateMapper.ensureEstimateByBatchNumber(parameter.getParam().get("batchNumber").toString());
		switch(estimate.getBatchStatus()) {
		case Constant.BATCH_STATUS_CAN:;
		case Constant.BATCH_STATUS_ERR:
			Lock lock=new ReentrantLock(true);
			try {
				if(lock.tryLock(15, TimeUnit.SECONDS)) {
					Integer rank=estimateMapper.ensureMaxRank();
					rank=CommonUtils.checkExistOrNot(rank)?rank+1:1;
			    	estimateMapper.resEstimate(rank,estimate.getBatchNumber());
					if(rank==1) {
						new Thread(new Runnable(){
							@Override
							public void run(){
								((EstimateTask)SpringUtils.getBean("estimateTask")).estimate();
								
							}
							
						}).start();
						
					}
					
				} else {
					throw new Exception("查询排位超时");
					
				}
				
			} catch (Exception e) {
				throw e;
				
			} finally {
				lock.unlock();
				
			}
			break;
		case Constant.BATCH_STATUS_WAI:;
		case Constant.BATCH_STATUS_ING:;
		case Constant.BATCH_STATUS_FIN:
			throw new Exception("预估批次状态异常，无法重启");
		default:break;
		
		}
		
	}

}