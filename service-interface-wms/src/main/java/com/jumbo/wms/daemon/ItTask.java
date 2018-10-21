package com.jumbo.wms.daemon;

public interface ItTask {

	/**
	 * 抓取文件
	 */
	public void download();

	/**
	 * 上传
	 */
	public void upload();

	public void generateReceiveFile();

	/**
	 * 生成STA
	 */
	public void generateSta();
}
