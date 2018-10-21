package com.jumbo.wms.daemon;

public interface JdOrderTask {

	public void toHubGetJdTransNo();

	// 设置JD单据号
	public void jdInterfaceByWarehouse();

	public void jdReceiveOrder();

}
