package com.jumbo.wms.manager.task.shelfLife;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.baseinfo.BiChannel;

public interface ShelfLifeManager extends BaseManager {

    void updateShelfLifeStatus(BiChannel b) throws Exception;

    void updateSkuShelfLifeTime() throws Exception;
}
