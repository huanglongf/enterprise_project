package com.bt.lmis.balance.task;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.bt.lmis.balance.service.impl.OperatingCostDetailCallable;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

/**
 * 操作费明细生成任务线程回调类， 负责回调逻辑、队列协调、收尾
 * @author jindong.lin
 *
 */
public class OperatingCostDetailFutureCallback implements FutureCallback<String> {

	// 定义日志
	private static final Logger logger=Logger.getLogger(OperatingCostDetailFutureCallback.class);
	
	private List<Integer> contractIdList;
	private ListenableFuture<String> listenableFuture;
	private ListeningExecutorService listeningExecutorService;
	private int contractIdListIndex;
	private OperatingCostDetailFutureCallback operatingCostDetailFutureCallback;
	
	public OperatingCostDetailFutureCallback(){
	}
	
	public OperatingCostDetailFutureCallback(List<Integer> contractIdList,
			int contractIdListIndex){
		this.contractIdList = contractIdList;
		this.contractIdListIndex = contractIdListIndex;
	}

	/**
	 * 线程池中线程执行完毕时(call方法)触发
	 */
	@Override
    public void onSuccess(String s) {
		logger.info(s);

		//队列协调
		int index = getNextIndex();
		
		if (index < contractIdList.size()) {
			//回调逻辑
			
			listenableFuture = listeningExecutorService.submit(
					new OperatingCostDetailCallable(contractIdList.get(index)));
			//应用回调
			Futures.addCallback(listenableFuture, operatingCostDetailFutureCallback);
			
		} else if (index == contractIdList.size()) {
			//收尾
			//关闭线程池
			listeningExecutorService.shutdown();
			logger.info("线程池已关闭");
			
		} 
		
    }


	/**
	 * 线程池中线程执行异常时(call方法)触发
	 */
    @Override
    public void onFailure(Throwable throwable) {
    	logger.error(throwable.getMessage());
    }

	private synchronized Integer getNextIndex(){
		return contractIdListIndex++;
	}

	public List<Integer> getContractIdList() {
		return contractIdList;
	}

	public void setContractIdList(List<Integer> contractIdList) {
		this.contractIdList = contractIdList;
	}

	public ListenableFuture<String> getListenableFuture() {
		return listenableFuture;
	}

	public void setListenableFuture(ListenableFuture<String> listenableFuture) {
		this.listenableFuture = listenableFuture;
	}

	public ListeningExecutorService getListeningExecutorService() {
		return listeningExecutorService;
	}

	public void setListeningExecutorService(ListeningExecutorService listeningExecutorService) {
		this.listeningExecutorService = listeningExecutorService;
	}

	public int getContractIdListIndex() {
		return contractIdListIndex;
	}

	public void setContractIdListIndex(int contractIdListIndex) {
		this.contractIdListIndex = contractIdListIndex;
	}

	public OperatingCostDetailFutureCallback getOperatingCostDetailFutureCallback() {
		return operatingCostDetailFutureCallback;
	}

	public void setOperatingCostDetailFutureCallback(
			OperatingCostDetailFutureCallback operatingCostDetailFutureCallback) {
		this.operatingCostDetailFutureCallback = operatingCostDetailFutureCallback;
	}
	
	
}
