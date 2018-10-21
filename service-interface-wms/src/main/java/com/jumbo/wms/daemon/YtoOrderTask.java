package com.jumbo.wms.daemon;

/**
 * 圆通接口
 * 
 * @author sj
 * 
 */
public interface YtoOrderTask {

	/**
	 * 所有仓库调用圆通接口
	 */
	void ytoInterfaceAllWarehouse();

	/**
	 * 特定仓库调用圆通接口
	 * 
	 * @param whId
	 */
	void ytoInterfaceByWarehouse(Long whId);
}
