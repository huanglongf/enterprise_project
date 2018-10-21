package com.bt.lmis.thread;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.service.ExpressbillBatchmasterService;

public class ExpressBillInput extends Thread{

	
	private String SFname;
	public String getSFname() {
		return SFname;
	}


	public void setSFname(String sFname) {
		SFname = sFname;
	}


	@Override
	public void run(){
		ExpressbillBatchmasterService service=
		(ExpressbillBatchmasterService)SpringUtils.getBean("expressbillBatchmasterServiceImpl");
		service.insertDetail();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
