package com.jumbo.wms.daemon;

import java.util.Map;

public interface TaskItochu {

	/**
	 * Etam 商品主档
	 */
	public void etamSku();

	/**
	 * 读主档文件
	 * 
	 */
	/**
	 * 读FTP商品文件到数据库
	 */
	public boolean readSKUFileIntoDB(String localFileDir, String bakFileDir);

	/**
	 * 退货入库通知、出库通知
	 */
	public void inOutBoundNotify();

	/**
	 * 出库反馈，入库反馈，退货入库反馈，退仓
	 */
	public void inOutBoundRtn();

	public void outBoundRtn0();

	public void inBoundRtn(Map<String, String> cfg);

	public void createInbound();

	/**
	 * 读文件数据 （伊藤忠库存数据）
	 * 
	 */
	public void readItochuRtnInvToDB();
}
