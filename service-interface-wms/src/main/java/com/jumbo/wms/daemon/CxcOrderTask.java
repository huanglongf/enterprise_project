package com.jumbo.wms.daemon;

public interface CxcOrderTask {

    /**
     * 方法说明：设置CXC快递单号
     * @author LuYingMing
     * @date 2016年5月30日 下午12:26:56
     */
	void setCxcWarehouseTranckingNo();
	/**
	 * 方法说明：调用CXC物流接口
	 * @author LuYingMing
	 * @date 2016年6月2日 下午4:38:33
	 */
	void callCxcLogisticsProvidersInterface();

}
