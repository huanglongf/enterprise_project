package com.jumbo.wms.daemon;

public interface JNJInvTask {

	/**
	 * 强生库存统计 23:00执行
	 */
	void totalInv();

	/**
	 * 将excel临时保存在服务器上
	 */
	boolean saveFile();

	/**
	 * 上传强生库存统计文件
	 * 
	 * @return
	 */
	public boolean uploadJNJInv();

	/**
	 * 备份文件
	 * 
	 * @return
	 */
	boolean backupsFile();
}
