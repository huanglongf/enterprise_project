package com.jumbo.wms.daemon;

/**
 * 申通接口
 * 
 * @author jinlong.ke
 * 
 */
public interface StoOrderTask {

	/**
	 * 特定仓库调用STO接口
	 * 
	 * @param whId
	 */
	public void stoInterfaceByWarehouse(Long whId);

	/**
	 * 所有仓库调用STO接口
	 */
	public void stoInterfaceAllWarehouse();
}
