package com.jumbo.wms.manager.vmi.jdData;

import com.jumbo.wms.manager.BaseManager;


public interface JdManagerProxy extends BaseManager {
	void receiveBillCodeMq(String message);
	void receiveBillOrderMq(String message);

}
