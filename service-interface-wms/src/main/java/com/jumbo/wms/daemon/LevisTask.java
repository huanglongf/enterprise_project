package com.jumbo.wms.daemon;

import com.jumbo.wms.manager.BaseManager;

public interface LevisTask extends BaseManager {

	public void downloadFile();

	public void createSkmrData();

	public void createStkrData();
	
	void generateInboundSta();
	
	/**
	 * levis童装主体切换-入库指令
	 */
	void downloadFtpDataAndCreateStaForLevisShoes();
	
	/**
	 * levis童装主体切换-入库反馈
	 */
	public void uploadLeivsData() ;

}
