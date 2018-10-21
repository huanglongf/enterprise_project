package com.jumbo.wms.manager.vmi.itochuData;

import com.jumbo.wms.manager.BaseManager;

/*import com.jumbo.manager.BaseManager;*/

public interface ItochuUAManagerProxy extends BaseManager {
	void uaOutBoundRtnExecute();

	void uaInBoundRtnExecute();
}
