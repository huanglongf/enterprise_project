package com.bt.lmis.balance.task;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.bt.lmis.balance.dao.ContractMapper;
import com.bt.lmis.balance.service.impl.OperatingCostDetailCallable;
import com.bt.lmis.base.spring.SpringUtils;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/** 
 * @ClassName: ExpressExpenditureEstimateTask
 * @Description: TODO(操作费明细生成任务)
 * @author Ian.Huang
 * @date 2017年5月5日 下午1:43:42 
 * 
 */
public class OperatingCostDetailTask {

	// 定义日志
	private static final Logger logger=Logger.getLogger(OperatingCostDetailTask.class);
	
	//线程池大小
	private static final int threadPoolNum = 20;

    private ListenableFuture<String> listenableFuture;
    private ListeningExecutorService listeningExecutorService;

	
	private ContractMapper contractMapper = SpringUtils.getBean("contractMapper");
	
	
	public void invoke() {
		try {

			long startTimes = System.currentTimeMillis();
			
			final List<Integer> contractIdList = contractMapper.findAllId();
			
			//初始化线程池
			listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(threadPoolNum));
			
			if (contractIdList.size() <= threadPoolNum) {
				//小于线程池不应用回调
				for (int i = 0; i < contractIdList.size(); i++) {
					listenableFuture = listeningExecutorService.submit(
							new OperatingCostDetailCallable(contractIdList.get(i)));
				}
				//收尾
				//关闭线程池
				listeningExecutorService.shutdown();
				logger.info("线程池已关闭");
			} else {
				//大于线程池应用回调
				//初始化回调
				OperatingCostDetailFutureCallback operatingCostDetailFutureCallback = 
						new OperatingCostDetailFutureCallback(contractIdList, threadPoolNum);
				
				for (int i = 0; i < threadPoolNum; i++) {
					if (i < contractIdList.size()){
						listenableFuture = listeningExecutorService.submit(
								new OperatingCostDetailCallable(contractIdList.get(i)));
						//设置回调上下文
						operatingCostDetailFutureCallback.setListeningExecutorService(listeningExecutorService);
						operatingCostDetailFutureCallback
							.setOperatingCostDetailFutureCallback(operatingCostDetailFutureCallback);
						//应用回调
						Futures.addCallback(listenableFuture, operatingCostDetailFutureCallback);
					}
				}
			}

			//监视线程池状态
			while (true) {  
	            if (listeningExecutorService.isTerminated()) {  
	    			logger.info("操作费明细生成任务完成"); 
	    			long endTimes = System.currentTimeMillis();
	    			logger.info("任务耗时:times = " + (endTimes - startTimes));
	                break;  
	            }  
				Thread.sleep(2000);
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("异常中断，message：" + e.getMessage());
			e.printStackTrace();
			
		}
		
	}
	
	
	
	
}