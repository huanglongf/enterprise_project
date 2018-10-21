package com.jumbo.wms.manager.task.rfd;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.RfdConfirmOrderQueue;

public interface RfdOrderTaskManager extends BaseManager {
	
	void rfdInterfaceAllWarehouse();
	
	void exeRfdConfirmOrder(List<RfdConfirmOrderQueue> qList);
}
