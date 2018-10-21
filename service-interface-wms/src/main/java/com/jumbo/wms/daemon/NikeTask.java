package com.jumbo.wms.daemon;

public interface NikeTask {

	/**
	 * 创建NIKE作业单
	 */
	public void generateStaByQueue();

	public void uploadNikeData();

	public void downloadNikeData();
	/**
	 * 根据中间表插入转店数据
	 */
	public void insertNikeStockReceive();

    /**
     * 上传入库反馈给HUB
     */
    public void uploadInboundNikeDataToHub();



}
