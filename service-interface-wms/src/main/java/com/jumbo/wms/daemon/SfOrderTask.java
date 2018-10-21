package com.jumbo.wms.daemon;

/**
 * 顺丰接口
 * 
 * @author jinlong.ke
 * 
 */
public interface SfOrderTask {

	/**
	 * 所有仓库调用顺丰接口
	 */
	void sfInterfaceAllWarehouse();

	/**
	 * 特定仓库调用顺丰接口
	 * 
	 * @param whId
	 */
	void sfInterfaceByWarehouse(Long whId);
}
