package com.jumbo.wms.manager.warehouse;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.command.CheckInventoryResaultCommand;


public interface QstaManagerExecute extends BaseManager {
	  void createsta(List<CheckInventoryResaultCommand> maps);


}
