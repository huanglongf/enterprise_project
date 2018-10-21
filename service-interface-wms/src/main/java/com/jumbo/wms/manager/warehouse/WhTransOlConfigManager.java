package com.jumbo.wms.manager.warehouse;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.WhTransOlConfig;

public interface WhTransOlConfigManager extends BaseManager{

    WhTransOlConfig findTransOlConfig(String lpCode, String departure);
}
