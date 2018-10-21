package com.jumbo.wms.daemon;

/**
 * 
 * 项目名称：scm-wms 类名称：AutoPickingTask 类描述：自动创建配货清单定时任务 创建人：bin.hu 创建时间：2014-9-18
 * 上午09:27:35
 * 
 * @version
 * 
 */
public interface AutoPickingTask {

	/**
	 * 通用创建
	 */
	public void autoPicking();

	/**
	 * 单独定制任务
	 * 
	 * @param strWhOu
	 */
	public void autoPickingByWh(String strWhOu);

	/**
	 * 单独合并订单 对于没有配置过自动创建配货清单仓库
	 * 
	 * @param strWhOu
	 */
	public void createMergeOrder();
}
