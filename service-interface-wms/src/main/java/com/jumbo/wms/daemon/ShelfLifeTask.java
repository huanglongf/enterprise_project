package com.jumbo.wms.daemon;

/**
 * 项目名称：Wms 类名称：ShelfLifeTask 类描述：对保质期商品操作定时任务 创建人：bin.hu 创建时间：2014-6-12
 * 下午02:23:18
 * 
 * @version
 * 
 */
public interface ShelfLifeTask {

	/**
	 * 定时任务-每日1点执行，库存状态修改，自动将所有可销售库存且需要预警库存修改为库存状态“临近保质期”
	 */
	public void updateShelfLifeStatus();

	/**
	 * 定时任务-每10分钟一次，同步给OMS保质期商品修改信息
	 */
	public void updateSkuShelfLifeTime();
}
