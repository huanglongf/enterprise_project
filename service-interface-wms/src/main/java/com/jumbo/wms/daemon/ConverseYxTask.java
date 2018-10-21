package com.jumbo.wms.daemon;

/**
 * Converse永兴定时任务入库
 * 
 * @author jinlong.ke
 * 
 */
public interface ConverseYxTask {

	/**
	 * 1、下载FTP文件到本地<br/>
	 * 2、 解析本地文件保存到数据库并备份<br/>
	 * 3、创单
	 */
	void downloadFtpDataAndCreateStaForConverseYx();

	/**
	 * Converse永兴根据反馈中间表写数据到文件并上传FTP
	 */
	void uploadConverseYxData();
}
