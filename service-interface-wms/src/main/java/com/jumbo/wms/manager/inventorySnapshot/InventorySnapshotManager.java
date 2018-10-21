package com.jumbo.wms.manager.inventorySnapshot;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.inventorySnapShot.InvWhFullInventory;

public interface InventorySnapshotManager extends BaseManager {

    void updateInventorySnapshotStatusById(List<InvWhFullInventory> inv, Integer status);

}
