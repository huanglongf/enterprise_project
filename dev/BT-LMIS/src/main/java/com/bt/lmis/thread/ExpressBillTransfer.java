package com.bt.lmis.thread;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.controller.form.ExpressbillBatchmasterQueryParam;
import com.bt.lmis.service.ExpressbillBatchmasterService;

public class ExpressBillTransfer extends Thread{

	

	private ExpressbillBatchmasterQueryParam qureyParam;
	
	
	public ExpressbillBatchmasterQueryParam getQureyParam() {
		return qureyParam;
	}


	public void setQureyParam(ExpressbillBatchmasterQueryParam qureyParam) {
		this.qureyParam = qureyParam;
	}


	@Override
	public void run(){
		//校验
		ExpressbillBatchmasterService service=
				(ExpressbillBatchmasterService)SpringUtils.getBean("expressbillBatchmasterServiceImpl");
				service.checkTemp(qureyParam);	
		
	}
	
}
